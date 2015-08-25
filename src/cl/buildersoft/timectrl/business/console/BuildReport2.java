package cl.buildersoft.timectrl.business.console;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.framework.database.BSmySQL;
import cl.buildersoft.framework.exception.BSConfigurationException;
import cl.buildersoft.framework.exception.BSDataBaseException;
import cl.buildersoft.framework.exception.BSProgrammerException;
import cl.buildersoft.framework.util.BSConfig;
import cl.buildersoft.framework.util.BSDateTimeUtil;
import cl.buildersoft.timectrl.business.beans.Report;
import cl.buildersoft.timectrl.business.beans.ReportInputParameterBean;
import cl.buildersoft.timectrl.business.beans.ReportPropertyBean;
import cl.buildersoft.timectrl.business.beans.ReportPropertyType;
import cl.buildersoft.timectrl.business.beans.ReportType;
import cl.buildersoft.timectrl.report.FileReport;

@Deprecated
public class BuildReport2 extends AbstractConsoleService {
	private String fileName = null;

	public static void main(String[] args) {
		BuildReport2 buildReport = new BuildReport2();
		buildReport.init();
		try {
			String arg1 = args[0];
			Long id = null;
			String[] target = new String[args.length - 1];
			System.arraycopy(args, 1, target, 0, target.length);

			if (!buildReport.isNumeric(arg1)) {
				buildReport.doBuild(arg1, target);
			} else {
				id = Long.parseLong((String) arg1);
				buildReport.doBuild(id, target);
			}

			System.out.println("Done!");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Fail!");
		}

	}

	public String doBuild(String reportKey, String[] args) {
		Connection conn = getConnection();
		BSBeanUtils bu = new BSBeanUtils();
		Report report = new Report();

		bu.search(conn, report, "cKey=?", reportKey);

		return doBuild(conn, report.getId(), args);
	}

	@Deprecated
	public String doBuild(Long idReport, String[] args) {
		Connection conn = getConnection();
		String out = doBuild(conn, idReport, args);
		new BSmySQL().closeConnection(conn);
		return out;
	}

	private String doBuild(Connection conn, Long idReport, String[] args) {
		String out = null;

		BSBeanUtils bu = new BSBeanUtils();
		Report report = new Report();
		report.setId(idReport);
		bu.search(conn, report);

		List<ReportInputParameterBean> reportParamList = null; // validParams(conn,
																// bu, report,
																// args);
		List<ReportPropertyType> reportOutValue = readReportOutValue(conn, bu, report);

		ReportType reportOutType = getReportType(conn, bu, report);

		// TODO: Esto se debe cambiar por el llamado a una clase que realiza la
		// exportacion, este nombre de clase debe quedar configurable en la
		// tabla tReportType
		if ("PLAIN_EXCEL".equalsIgnoreCase(reportOutType.getKey())) {
			doPlainExcel(conn, bu, report, reportParamList, reportOutValue, args);
		} else {
			FileReport reportBuilder = new FileReport();
			reportBuilder.setFileName(this.fileName);
			out = reportBuilder.doBuild(conn, bu, report, reportParamList, reportOutValue);
		}
		return out;
	}

	@SuppressWarnings("unchecked")
	private List<ReportPropertyType> readReportOutValue(Connection conn, BSBeanUtils bu, Report report) {
		/*
		 * <code> List<ReportOutValue> out = new ArrayList<ReportOutValue>();
		 * out = (List<ReportOutValue>) bu.list(conn, new ReportOutValue(),
		 * "cReport=?", report.getId());
		 * 
		 * return out; </code>
		 */
		return (List<ReportPropertyType>) bu.list(conn, new ReportPropertyType(), "cReport=?", report.getId());
	}

	private void doPlainExcel(Connection conn, BSBeanUtils bu, Report report, List<ReportInputParameterBean> reportParamList,
			List<ReportPropertyType> reportOutValue, String[] args) {
		BSmySQL mysql = new BSmySQL();
		String sp = null; // getSPName(reportParamList);
		List<Object> params = null; // paramsToList(args, reportParamList);
		ResultSet rs = mysql.callSingleSP(conn, sp, params);
		this.fileName = getFileName(conn, reportOutValue);

		resultSetToFile(conn, rs);

		new BSmySQL().closeSQL(rs);
		System.out.println("Report written in " + this.fileName);
	}

	private void resultSetToFile(Connection conn, ResultSet rs) {
		ResultSetMetaData metaData;
		try {
			metaData = rs.getMetaData();
			Integer colCount = metaData.getColumnCount();
			String[] colNames = new String[colCount];
			Integer i = 0;
			Integer j = 0;
			for (j = 1; j <= colCount; j++) {
				colNames[j - 1] = metaData.getColumnLabel(j);
			}

			// FileInputStream file = new FileInputStream(new File(fileName));

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("Listado");

			HSSFRow row = sheet.createRow(i++);
			HSSFCell cell = null;
			j = 0;
			for (String colName : colNames) {
				cell = row.createCell(j++);
				cell.setCellValue(colName);
			}

			Object value = null;
			while (rs.next()) {
				row = sheet.createRow(i++);

				for (j = 0; j < colCount; j++) {
					cell = row.createCell(j);

					value = rs.getObject(j + 1);
					if (value == null) {
						cell.setCellValue("");
					} else {
						switch (metaData.getColumnType(j + 1)) {
						case -5:
							cell.setCellValue(rs.getLong(j + 1));
							break;
						case 12:
						case -1:
							cell.setCellValue(rs.getString(j + 1));
							break;
						case 4:
							cell.setCellValue(rs.getInt(j + 1));
							break;
						case -7:
							cell.setCellValue(rs.getBoolean(j + 1));
							break;
						default:
							cell.setCellValue(rs.getObject(j + 1).toString());
							break;
						}
					}
				}
			}

			FileOutputStream out = new FileOutputStream(new File(this.fileName));
			workbook.write(out);
			workbook.close();
			out.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getFileName(Connection conn, List<ReportPropertyType> reportParamList) {
		String out = null;

		if (this.fileName == null) {
			BSConfig config = new BSConfig();

			BSBeanUtils bu = new BSBeanUtils();
			for (ReportPropertyType param : reportParamList) {
				ReportPropertyBean outParam = getParamType(conn, bu, param);
				/**
				 * <code>
				if (outParam.getKey().equals("OUTPUT_FILE")) {
					this.fileName = param.getValue();
					break;
				}
				</code>
				 */
			}

			String path = config.getString(conn, "OUTPUT_REPORT");
			File file = new File(path);
			if (!file.exists()) {
				if (!file.mkdirs()) {
					throw new BSConfigurationException("Can't access to " + path);
				}
			}

			out = config.fixPath(path) + parseData(this.fileName);
			// "ReportePlano_" +
			// BSDateTimeUtil.calendar2String(Calendar.getInstance(),
			// "yyyy-MM-dd") + ".xls";
		} else {
			out = this.fileName;
		}
		return out;
	}

	private String parseData(String data) {
		String out = data.replaceAll("\\x7BDate\\x7D", BSDateTimeUtil.calendar2String(Calendar.getInstance(), "yyyy-MM-dd"));

		return out;
	}

	private ReportPropertyBean getParamType(Connection conn, BSBeanUtils bu, ReportPropertyType param) {
		ReportPropertyBean outParam = new ReportPropertyBean();
		// outParam.setId(param.getParam());
		// bu.search(conn, outParam);
		return outParam;
	}

	public String getFileName(Connection conn) {
		String out = null;
		if (this.fileName == null) {
			BSConfig config = new BSConfig();

			String path = config.getString(conn, "OUTPUT_REPORT");
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			/** TODO: Urgente, parametrizar este dat */
			out = config.fixPath(path) + "ReportePlano_" + BSDateTimeUtil.calendar2String(Calendar.getInstance(), "yyyy-MM-dd")
					+ ".xls";
		} else {
			out = this.fileName;
		}
		return out;
	}

	private String getSPName(List<ReportInputParameterBean> reportParamList) {
		String out = null;
		for (ReportInputParameterBean reportParam : reportParamList) {
			// if (3L == reportParam.getType()) {
			// out = reportParam.getValue();
			// break;
			// }
		}
		return out;
	}

	private List<Object> paramsToList(String[] args, List<ReportInputParameterBean> reportParamList) {
		List<Object> out = new ArrayList<Object>();
		Integer i = 0;
		for (ReportInputParameterBean reportParam : reportParamList) {
			// if (reportParam.getFromUser()) {
			// out.add(args[i++]);
			// }
		}
		return out;
	}

	private ReportType getReportType(Connection conn, BSBeanUtils bu, Report report) {
		Long id = report.getType();
		ReportType reportType = new ReportType();
		reportType.setId(id);
		bu.search(conn, reportType);
		return reportType;
	}

	private List<ReportInputParameterBean> validParams(Connection conn, BSBeanUtils bu, Report report, String[] args) {
		List<ReportInputParameterBean> out = new ArrayList<ReportInputParameterBean>();
		ReportInputParameterBean temp = null;
		BSmySQL mysql = new BSmySQL();
		ResultSet rs = mysql.callSingleSP(conn, "pListReportParams", report.getId());
		Integer countFromUserParam = 0;
		Integer i = 0;

		try {
			while (rs.next()) {
				temp = new ReportInputParameterBean();
				temp.setId(rs.getLong("cId"));

				// bu.search(conn, temp);

				// System.out.println(temp.getName() + " = " + temp.getValue() +
				// (temp.getFromUser() ? " (Input by user)" : ""));

				// if (temp.getFromUser()) {
				// countFromUserParam++;
				//
				// temp.setValue(args[i++]);
				// }

				out.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BSDataBaseException(e);
		}

		if (countFromUserParam != args.length) {
			throw new BSProgrammerException("Parameters count are wrong, are " + args.length + ", expected " + countFromUserParam);
		}
		return out;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;

	}
}
