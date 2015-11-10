package cl.buildersoft.timectrl.report;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mysql.jdbc.log.Log;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.framework.exception.BSConfigurationException;
import cl.buildersoft.framework.util.BSConfig;
import cl.buildersoft.framework.util.BSDateTimeUtil;
import cl.buildersoft.timectrl.business.beans.Report;
import cl.buildersoft.timectrl.business.beans.ReportParameterBean;
import cl.buildersoft.timectrl.business.beans.ReportPropertyBean;
import cl.buildersoft.timectrl.business.beans.ReportPropertyType;
import cl.buildersoft.timectrl.business.beans.ReportParameterType;

@SuppressWarnings({ "deprecation", "rawtypes" })
public class FileReport {
	private static final Logger LOG = Logger.getLogger(FileReport.class.getName());
	private String fileName = null;

	public String doBuild(Connection conn, BSBeanUtils bu, Report report, List<ReportParameterBean> reportParamList,
			List<ReportPropertyType> reportOutValues) {

		String folder = null;
		if (this.fileName == null) {
			folder = getReportOutParam(conn, bu, reportOutValues, "OUTPUT_FOLDER");
		}
		String fileName = getFileName(conn, bu, reportOutValues);

		LOG.log(Level.INFO, "Filename: {0}", fileName);
		return buildReportFile(conn, fileName, folder, reportParamList, report);

	}

	private String buildReportFile(Connection conn, String fileName, String folder, List<ReportParameterBean> reportParamList,
			Report report) {
		String outputFileAndPath = (folder == null ? "" : fixPath(folder)) + fileName;
		String jasperFileAndPath = getJasperFile(conn, report);

		// File outFile = new File(outputFileAndPath);
		JasperReport jasperReport = null;
		try {
			jasperReport = (JasperReport) JRLoader.loadObject(new File(jasperFileAndPath));
		} catch (JRException e) {
			LOG.log(Level.SEVERE, "Finding " + jasperFileAndPath, e);
			throw new BSConfigurationException(e);
		}

		Map<String, Object> params = getParams(conn, reportParamList);

		JasperPrint jasperPrint = null;
		try {
			jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);
		} catch (JRException e) {
			e.printStackTrace();
			throw new BSConfigurationException(e);
		}

		JRExporter exporter = new JRPdfExporter();
		// String extention = ".pdf";
		/**
		 * <code>
		if (format.equalsIgnoreCase("pdf")) {
			exporter = new JRPdfExporter();
		} else if (format.equalsIgnoreCase("Excel")) {
			exporter = new JRXlsxExporter();
			extention = ".xls";
		}
		</code>
		 */

		// fileName += extention;
		LOG.log(Level.INFO, "File to build '{0}", outputFileAndPath);

		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new File(outputFileAndPath));

		try {
			exporter.exportReport();
		} catch (JRException e) {
			e.printStackTrace();
			throw new BSConfigurationException(e);
		}
		return outputFileAndPath;
	}

	private String getJasperFile(Connection conn, Report report) {
		BSConfig config = new BSConfig();
		String out = "";
		// String out = fixPath(config.getString(conn, "JASPER_FOLDER")) +
		// report.getJasperFile();
		// throw new BSProgrammerException("Under construction");
		return out;
	}

	private Map<String, Object> getParams(Connection conn, List<ReportParameterBean> reportParams) {
		Map<String, Object> out = new HashMap<String, Object>();
		Object value = null;
		BSBeanUtils bu = new BSBeanUtils();
		for (ReportParameterBean reportParam : reportParams) {
			// value = getType(conn, reportParam.getType(), bu,
			// reportParam.getValue());
			//
			// if (reportParam.getFromUser()) {
			// out.put(reportParam.getName(), value);
			// }
		}

		/**
		 * <code>
		BSDateTimeUtil dtu = new BSDateTimeUtil();

		Date startDateDate = dtu.calendar2Date(dtu.string2Calendar(startDate, "yyyy-MM-dd"));
		Date endDateDate = dtu.calendar2Date(dtu.string2Calendar(endDate, "yyyy-MM-dd"));

		out.put("UserId", employee);
		out.put("StartDate", startDateDate);
		out.put("EndDate", endDateDate);
</code>
		 */

		return out;
	}

	private Object getType(Connection conn, Long type, BSBeanUtils bu, String value) {
		ReportParameterType paramType = new ReportParameterType();
		Object out = null;
		paramType.setId(type);
		bu.search(conn, paramType);

		String key = paramType.getKey();
		if ("INTEGER".equals(key)) {
			out = Integer.parseInt(value);
		} else if ("LONG".equals(key)) {
			out = Long.parseLong(value);
		} else if ("DATE".equals(key)) {
			out = BSDateTimeUtil.string2Calendar(value, BSDateTimeUtil.getFormatDate(conn));
		} else {
			out = value;
		}

		return out;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.buildersoft.timectrl.report.BuildReportService#getReportOutParam(java
	 * .sql.Connection, cl.buildersoft.framework.database.BSBeanUtils,
	 * java.util.List, java.lang.String)
	 */

	public String getReportOutParam(Connection conn, BSBeanUtils bu, List<ReportPropertyType> reportOutValues, String key) {
		String out = null;
		ReportPropertyBean outParam = getReportOutParam(conn, bu, key);
		for (ReportPropertyType reportOutValue : reportOutValues) {
			/**
			 * <code>
			if (reportOutValue.getParam().equals(outParam.getId())) {
				out = reportOutValue.getValue();
				break;
			}
			</code>
			 */
		}
		return out;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.buildersoft.timectrl.report.BuildReportService#getFileName(java.sql
	 * .Connection, cl.buildersoft.framework.database.BSBeanUtils,
	 * java.util.List)
	 */

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName(Connection conn, BSBeanUtils bu, List<ReportPropertyType> reportOutValues) {
		/**
		 * <code>
		ReportOutParam outParam = getReportOutParam(conn, bu, "OUTPUT_FILE");
		String out = null;
		for (ReportOutValue reportOutValue : reportOutValues) {
			if (reportOutValue.getType().equals(outParam.getId())) {
				out = reportOutValue.getValue();
				break;
			}
		}
</code>
		 */

		String out = null;
		if (this.fileName == null) {
			this.fileName = getReportOutParam(conn, bu, reportOutValues, "OUTPUT_FILE");
		}
		out = this.fileName;

		// out = getReportOutParam(conn, bu, reportOutValues, "OUTPUT_FILE");
		return out;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.buildersoft.timectrl.report.BuildReportService#getFormat(java.sql.
	 * Connection, cl.buildersoft.framework.database.BSBeanUtils,
	 * java.util.List)
	 */

	public String getFormat(Connection conn, BSBeanUtils bu, List<ReportPropertyType> reportOutValues) {
		/**
		 * <code>
		ReportOutParam outParam = getReportOutParam(conn, bu, "FORMAT");
		String out = null;
		for (ReportOutValue reportOutValue : reportOutValues) {
			if (reportOutValue.getType().equals(outParam.getId())) {
				out = reportOutValue.getValue();
				break;
			}
		}
		return out;
		</code>
		 */
		return getReportOutParam(conn, bu, reportOutValues, "FORMAT");
	}

	private ReportPropertyBean getReportOutParam(Connection conn, BSBeanUtils bu, String key) {
		ReportPropertyBean out = new ReportPropertyBean();
		// bu.search(conn, out, "cKey=?", key);
		return out;
	}

	private String fixPath(String folderName) {
		String out = null;
		if (!folderName.endsWith(File.separator)) {
			out = folderName + File.separator;
		} else {
			out = folderName;
		}
		return out;
	}
	/*
	 * public String getFileName() { return fileName; }
	 * 
	 * public void setFileName(String fileName) { this.fileName = fileName; }
	 */
}
