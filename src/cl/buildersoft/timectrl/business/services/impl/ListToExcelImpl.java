package cl.buildersoft.timectrl.business.services.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.framework.database.BSmySQL;
import cl.buildersoft.framework.exception.BSProgrammerException;
import cl.buildersoft.timectrl.business.beans.ReportParameterBean;
import cl.buildersoft.timectrl.business.beans.ReportPropertyBean;
import cl.buildersoft.timectrl.business.beans.ReportType;
import cl.buildersoft.timectrl.business.services.ReportService;

public class ListToExcelImpl extends AbstractReportService implements ReportService {
	private final static Logger LOG = Logger.getLogger(ListToExcelImpl.class.getName());
	private String outputPath = null;
	private String outputFile = null;
	private String spName = null;
	private String outputFileAndPath = null;
	private String[] keyValues = {};

	@Override
	public List<String> execute(Connection conn, Long idReport, ReportType reportType,
			List<ReportPropertyBean> reportPropertyList, List<ReportParameterBean> reportInputParameterList) {
		List<String> out = new ArrayList<String>();

		readProperties(conn, reportPropertyList);

		this.outputPath = parsePropertes(this.outputPath, keyValues);
		createPathIfNotExists(this.outputPath);
		this.outputFileAndPath = fixPath(this.outputPath) + parsePropertes(this.outputFile, keyValues);

		BSmySQL mysql = new BSmySQL();
		List<Object> params = getReportParams(conn, reportInputParameterList);
		ResultSet rs = mysql.callSingleSP(conn, this.spName, params);

		resultSetToFile(conn, rs);

		out.add(this.outputFileAndPath);

		return out;
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

			FileOutputStream out = new FileOutputStream(new File(this.outputFileAndPath));
			workbook.write(out);
			// workbook.close();
			out.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected List<Object> getReportParams(Connection conn, List<ReportParameterBean> inParamList) {
		List<Object> out = new ArrayList<Object>();
		Object value = null;
		BSBeanUtils bu = new BSBeanUtils();
		for (ReportParameterBean reportParam : inParamList) {
			value = getParameterType(conn, reportParam.getJavaType(), bu, reportParam.getValue());

			// if (reportParam.getFromUser()) {
			out.add(value);
			// }
		}
		return out;
	}

	@Override
	protected Boolean getPropertyValue(String key, String value) {
		Boolean out = true;
		if ("OUTPUT_FILE".equalsIgnoreCase(key)) {
			this.outputFile = value;
		} else if ("OUTPUT_PATH".equalsIgnoreCase(key)) {
			this.outputPath = value;
		} else if ("SP".equalsIgnoreCase(key)) {
			this.spName = value;
		}
		return out;
	}

	@Override
	protected String parseCustomVariable(String key) {

		return null;
	}

	@Override
	public Boolean runAsDetachedThread() {
		return false;
	}

	@Override
	public void setConnectionData(String driverName, String serverName, String database, String password, String username) {
		throw new BSProgrammerException("This report run as same thread of container");
	}

	@Override
	public void setReportId(Long reportId) {
		throw new BSProgrammerException("This report run as same thread of container");
	}

	@Override
	public void setReportType(ReportType reportType) {
		throw new BSProgrammerException("This report run as same thread of container");
	}

	@Override
	public void setReportPropertyList(List<ReportPropertyBean> reportPropertyList) {
		throw new BSProgrammerException("This report run as same thread of container");
	}

	@Override
	public void setReportParameterList(List<ReportParameterBean> reportParameterList) {
		throw new BSProgrammerException("This report run as same thread of container");
	}

	@Override
	public void run() {
		LOG.log(Level.SEVERE, "This class dont run as single thread {0}", ListToExcelImpl.class.getName());

	}

	@Override
	public void waitBeforeRun(Integer seconds) {
		LOG.log(Level.SEVERE, "This class dont run as single thread {0}", ListToExcelImpl.class.getName());
	}

}
