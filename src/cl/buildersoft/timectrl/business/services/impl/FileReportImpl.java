package cl.buildersoft.timectrl.business.services.impl;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.framework.exception.BSProgrammerException;
import cl.buildersoft.timectrl.business.beans.IdRut;
import cl.buildersoft.timectrl.business.beans.ReportParameterBean;
import cl.buildersoft.timectrl.business.beans.ReportPropertyBean;
import cl.buildersoft.timectrl.business.beans.ReportType;
import cl.buildersoft.timectrl.business.services.ReportService;

public class FileReportImpl extends AbstractReportService implements ReportService {
	private String jasperPath = null;
	private String jasperFile = null;
	private String outputPath = null;
	private String outputFile = null;
	private String format = null;
	private String[] keyValues = { "EmployeeKey", "EmployeeRut", "EmployeeId", "EmployeeName", "CostCenter", "UserName" };
	private IdRut idRut = null;

	@Override
	public List<String> execute(Connection conn, Long idReport, ReportType reportType,
			List<ReportPropertyBean> reportPropertyList, List<ReportParameterBean> reportInputParameter) {
		List<String> out = null;

		// showProperties(conn, reportPropertyList);
		readProperties(conn, reportPropertyList);

		ReportParameterBean employeeParameter = getEmployeeParameter(reportInputParameter);
		List<IdRut> employeeList = null;
		if (employeeParameter != null) {
			employeeList = getEmployeeList(conn, employeeParameter.getValue());
			out = new ArrayList<String>(employeeList.size());
			for (IdRut idRut : employeeList) {
				this.idRut = idRut;

				updateEmployeeId(reportInputParameter, idRut.getId());

				processJasper(conn, reportInputParameter, out);
			}
		} else {
			out = new ArrayList<String>(1);
			processJasper(conn, reportInputParameter, out);
		}
		return out;
	}

	private void processJasper(Connection conn, List<ReportParameterBean> reportInputParameter, List<String> out) {
		String outputPath = parsePropertes(this.outputPath, keyValues);
		createPathIfNotExists(outputPath);

		String outputFileAndPath = fixPath(outputPath) + parsePropertes(this.outputFile, keyValues);
		String jasperFileAndPath = fixPath(this.jasperPath) + this.jasperFile;

		Map<String, Object> params = getReportParams(conn, reportInputParameter);

		try {
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(new File(jasperFileAndPath));
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);
			JRExporter exporter = getExporter(this.format);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new File(outputFileAndPath));
			exporter.exportReport();
			out.add(outputFileAndPath);
		} catch (JRException e) {
			e.printStackTrace();
			throw new BSProgrammerException(e);
		}
	}

	private Map<String, Object> getReportParams(Connection conn, List<ReportParameterBean> inParamList) {
		Map<String, Object> out = new HashMap<String, Object>();
		Object value = null;
		BSBeanUtils bu = new BSBeanUtils();
		for (ReportParameterBean reportParam : inParamList) {
			value = getParameterType(conn, reportParam.getJavaType(), bu, reportParam.getValue());

			// if (reportParam.getFromUser()) {
			out.put(reportParam.getName(), value);
			// }
		}
		return out;
	}

	protected String parseCustomVariable(String key) {
		String out = "";
		if (this.idRut != null) {
			if ("EmployeeKey".equalsIgnoreCase(key)) {
				out = this.idRut.getKey();
			} else if ("EmployeeRut".equalsIgnoreCase(key)) {
				out = this.idRut.getRut();
			} else if ("EmployeeId".equalsIgnoreCase(key)) {
				out = this.idRut.getId().toString();
			} else if ("EmployeeName".equalsIgnoreCase(key)) {
				out = this.idRut.getName();
			} else if ("CostCenter".equalsIgnoreCase(key)) {
				out = this.idRut.getCostCenter();
			} else if ("UserName".equalsIgnoreCase(key)) {
				out = this.idRut.getUsername();
			}
		}

		return out;

	}

	protected Boolean getPropertyValue(String key, String value) {
		Boolean out = true;
		if ("JASPER_FILE".equalsIgnoreCase(key)) {
			this.jasperFile = value;
		} else if ("JASPER_PATH".equalsIgnoreCase(key)) {
			this.jasperPath = value;
		} else if ("OUTPUT_FILE".equalsIgnoreCase(key)) {
			this.outputFile = value;
		} else if ("OUTPUT_PATH".equalsIgnoreCase(key)) {
			this.outputPath = value;
		} else if ("FORMAT".equalsIgnoreCase(key)) {
			this.format = value;
		}
		return out;
	}

}
