package cl.buildersoft.timectrl.business.console;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.framework.database.BSmySQL;
import cl.buildersoft.framework.exception.BSConfigurationException;
import cl.buildersoft.framework.exception.BSProgrammerException;
import cl.buildersoft.framework.exception.BSUserException;
import cl.buildersoft.timectrl.business.beans.Report;
import cl.buildersoft.timectrl.business.beans.ReportParameterBean;
import cl.buildersoft.timectrl.business.beans.ReportPropertyBean;
import cl.buildersoft.timectrl.business.beans.ReportType;
import cl.buildersoft.timectrl.business.services.ParameterService;
import cl.buildersoft.timectrl.business.services.ReportService;

public class BuildReport3 extends AbstractConsoleService {
	private static final String NOT_FOUND = "' not found.";
	private static final Logger LOG = Logger.getLogger(BuildReport3.class.getName());

	public static void main(String[] args) {
		BuildReport3 buildReport = new BuildReport3();
		buildReport.init();
		buildReport.mainFromConsole(args);
		System.exit(0);
	}

	public void mainFromConsole(String[] args) {
		if (args.length < 1) {
			throw new BSUserException("Arguments not enough");
		}
		String key = args[0];
		String[] target = new String[args.length - 1];
		System.arraycopy(args, 1, target, 0, target.length);
		List<String> responseList = null;

		if (isNumeric(key)) {
			Long id = Long.parseLong((String) key);
			responseList = doBuild(id, target);
		} else {
			responseList = doBuild(key, target);
		}
		if (responseList != null) {
			for (String response : responseList) {
				LOG.log(Level.INFO, response);
			}
		}
	}

	public List<String> doBuild(String reportKey, String[] target) {
		Connection conn = getConnection();

		BSBeanUtils bu = new BSBeanUtils();
		Report report = new Report();

		if (!bu.search(conn, report, "cKey=?", reportKey)) {
			throw new BSConfigurationException("Report '" + reportKey + NOT_FOUND);
		}

		setConnection(conn);
		List<String> out = doBuild(report.getId(), target);

		BSmySQL mysql = new BSmySQL();
		mysql.closeConnection(conn);

		return out;

		// return execute(conn, report.getId(), arrayToList(target));
	}

	public List<String> doBuild(Long id, String[] target) {
		Connection conn = getConnection();
		List<String> out = execute(conn, id, arrayToList(target));
		new BSmySQL().closeConnection(conn);
		return out;
	}

	private List<String> arrayToList(String[] target) {
		List<String> out = new ArrayList<String>();
		for (String s : target) {
			out.add(s);
		}

		return out;
	}

	private List<String> execute(Connection conn, Long id, List<String> target) {
		BSBeanUtils bu = new BSBeanUtils();
		Report report = getReport(id, bu, conn);
		ReportType reportType = getReportType(conn, report);

		ReportService reportService = getInstance(conn, report);

		List<ReportPropertyBean> reportPropertyList = reportService.loadReportProperties(conn, id);
		List<ReportParameterBean> parameters = reportService.loadParameter(conn, id);

		if (parameters.size() != target.size()) {
			throw new BSConfigurationException("Amount of parameters do not match");
		}
		reportService.fillParameters(parameters, target);

		List<String> out = reportService.execute(conn, report.getId(), reportType, reportPropertyList, parameters);

		return out;
	}

	public ReportService getInstance(Connection conn, Report report) {
		ReportService out = null;
		if (report.getJavaClass() == null) {
			ReportType reportType = getReportType(conn, report);
			out = getInstance(reportType.getJavaClass());
		} else {
			out = getInstance(report.getJavaClass());
		}
		return out;
	}

	@SuppressWarnings("unchecked")
	private ReportService getInstance(String javaClassName) {
		ReportService instance = null;
		try {
			Class<ReportService> javaClass = (Class<ReportService>) Class.forName(javaClassName);
			instance = (ReportService) javaClass.newInstance();
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Class of ReportService not found", e);
			throw new BSProgrammerException(e);
		}
		return instance;
	}

	public ParameterService getInstanceOfParameter(ReportParameterBean reportParameter) {
		ParameterService instance = null;
		try {
			@SuppressWarnings("unchecked")
			Class<ParameterService> javaClass = (Class<ParameterService>) Class.forName(reportParameter.getTypeSource());
			instance = (ParameterService) javaClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BSProgrammerException(e);
		}
		return instance;

	}

	private Report getReport(Long reportId, BSBeanUtils bu, Connection conn) {
		Report report = new Report();
		report.setId(reportId);
		if (!bu.search(conn, report)) {
			throw new BSProgrammerException("Report '" + reportId + NOT_FOUND);
		}
		return report;
	}

	private ReportType getReportType(Connection conn, Report report) {
		BSBeanUtils bu = new BSBeanUtils();

		ReportType reportType = new ReportType();
		reportType.setId(report.getType());
		if (!bu.search(conn, reportType)) {
			throw new BSProgrammerException("Report type '" + report.getType() + NOT_FOUND);
		}
		return reportType;
	}
}
