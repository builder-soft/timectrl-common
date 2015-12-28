package cl.buildersoft.timectrl.business.process.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.framework.database.BSmySQL;
import cl.buildersoft.framework.exception.BSConfigurationException;
import cl.buildersoft.framework.exception.BSProgrammerException;
import cl.buildersoft.framework.util.BSConnectionFactory;
import cl.buildersoft.timectrl.business.beans.Employee;
import cl.buildersoft.timectrl.business.beans.Report;
import cl.buildersoft.timectrl.business.beans.ReportParameterBean;
import cl.buildersoft.timectrl.business.beans.ReportPropertyBean;
import cl.buildersoft.timectrl.business.beans.ReportType;
import cl.buildersoft.timectrl.business.process.AbstractProcess;
import cl.buildersoft.timectrl.business.process.ExecuteProcess;
import cl.buildersoft.timectrl.business.services.EmployeeService;
import cl.buildersoft.timectrl.business.services.ParameterService;
import cl.buildersoft.timectrl.business.services.ReportService;
import cl.buildersoft.timectrl.business.services.impl.EmployeeServiceImpl;

public class BuildReport4 extends AbstractProcess  implements ExecuteProcess {
	private static final String NOT_FOUND = "' not found.";
	private static final Logger LOG = Logger.getLogger(BuildReport4.class.getName());
	private Boolean runFromConsole = false;
	private String dsName = null;

	private String[] validArguments = { "DOMAIN", "REPORT_KEY" };

	public static void main(String[] args) {
		BuildReport4 br4 = new BuildReport4();
		// buildReport.init();
		br4.doExecute(args);
		System.exit(0);
	}

	@Override
	public void doExecute(String[] args) {
//		this.init();

		this.dsName = args[0];
		String key = args[1];
		String[] target = new String[args.length - 2];
		System.arraycopy(args, 2, target, 0, target.length);
		
		
//		validateArguments(args, false);
		/**
		 * <code>
		if (args.length < 1) {
			throw new BSUserException("Arguments not enough");
		}
		</code>
		 */
		
		BSConnectionFactory cf = new BSConnectionFactory();
		Connection conn = cf.getConnection(dsName);
		
		
		this.runFromConsole = true;
	
		
		List<String> responseList = null;

		Long reportId = keyToReportId(conn, key);

		responseList = doBuild(conn, reportId, target);

		if (responseList != null) {
			for (String response : responseList) {
				LOG.log(Level.INFO, response);
			}
		}
	}

	private Long keyToReportId(Connection conn, String key) {
		BSBeanUtils bu = new BSBeanUtils();
		Report report = new Report();

		if (!bu.search(conn, report, "cKey=?", key)) {
			throw new BSConfigurationException("Report '" + key + NOT_FOUND);
		}

		return report.getId();
	}

	@Override
	protected String[] getArguments() {
		return validArguments;
	}

	/**
	 * <code>
	   
	
	private List<String> doBuild(String reportKey, String[] target) {
//		Connection conn = getConnection();

		BSBeanUtils bu = new BSBeanUtils();
		Report report = new Report();

		if (!bu.search(conn, report, "cKey=?", reportKey)) {
			throw new BSConfigurationException("Report '" + reportKey + NOT_FOUND);
		}

		// setConnection(conn);
		List<String> out = doBuild(report.getId(), target);

		BSmySQL mysql = new BSmySQL();
		mysql.closeConnection(conn);

		return out;
	}
	</code>
	 */
	private List<String> doBuild(Connection conn, Long id, String[] target) {
		List<String> out = new ArrayList<String>();
		try {
			out = execute2(conn, id, arrayToList(target));
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Error at process report", e);
		} finally {
			new BSmySQL().closeConnection(conn);
		}
		return out;
	}

	private List<String> arrayToList(String[] target) {
		List<String> out = new ArrayList<String>();
		for (String s : target) {
			out.add(s);
		}

		return out;
	}

	/**
	 * <code>
	 * 
	 * @Deprecated private List<String> execute(Connection conn, Long id,
	 *             List<String> target) { BSBeanUtils bu = new BSBeanUtils();
	 *             Report report = getReport(conn, id); ReportType reportType =
	 *             getReportType(conn, report);
	 * 
	 *             ReportService reportService = getInstance(conn, report);
	 * 
	 *             List<ReportPropertyBean> reportPropertyList =
	 *             reportService.loadReportProperties(conn, id);
	 *             List<ReportParameterBean> parameters =
	 *             reportService.loadParameter(conn, id);
	 * 
	 *             if (parameters.size() != target.size()) { throw new
	 *             BSConfigurationException
	 *             ("Amount of parameters do not match"); }
	 *             reportService.fillParameters(parameters, target);
	 * 
	 *             List<String> out = reportService.execute(conn,
	 *             report.getId(), reportType, reportPropertyList, parameters);
	 * 
	 *             return out; } </code>
	 */

	private List<String> execute2(Connection conn, Long reportId, List<String> parameters) {
		Report report = getReport(conn, reportId);
		ReportType reportType = getReportType(conn, report);

		ReportService reportService = getInstance(conn, report);

		List<ReportParameterBean> reportParameterList = reportService.loadParameter(conn, reportId);
		// List<String> parameters = readParametersFromPage(reportParameterList,
		// request);

		List<ReportPropertyBean> reportPropertyList = reportService.loadReportProperties(conn, reportId);
		reportService.fillParameters(reportParameterList, parameters);

		List<String> responseList = new ArrayList<String>();

		// ********************************************************
		ReportParameterBean bossId = reportService.getReportParameter(reportParameterList, "BOSS_LIST");

		if (bossId != null && "0".equalsIgnoreCase(bossId.getValue())) {
			EmployeeService es = new EmployeeServiceImpl();
			List<Employee> bossList = es.listBoss(conn);

			List<ReportParameterBean> parameterListBackup = cloneParameterList(reportParameterList);

			for (Employee boss : bossList) {
				bossId.setValue(boss.getId().toString());
				responseList.addAll(executeReport(conn, reportId, reportType, reportService, reportParameterList,
						reportPropertyList));
				reportParameterList = cloneParameterList(parameterListBackup);
				bossId = reportService.getReportParameter(reportParameterList, "BOSS_LIST");
			}
		} else {
			responseList = executeReport(conn, reportId, reportType, reportService, reportParameterList, reportPropertyList);
		}

		// ********************************************************

		// BSmySQL mysql = new BSmySQL();
		// mysql.closeConnection(conn);

		if (reportService.runAsDetachedThread()) {
			responseList.clear();
			responseList.add("La solicitud se esta procesando de manera desatendida.");
		}

		return responseList;

		/**
		 * <code>		
		Map<Integer, String> responseMap = new HashMap<Integer, String>();
		Integer index = 0;

		for (String responseString : responseList) {
			responseMap.put(index++, responseString);
		}

		request.setAttribute("ResponseMap", responseMap);
		request.getSession().setAttribute("ResponseMap", responseMap);

		forward(request, response, "/WEB-INF/jsp/timectrl/report/execute/show-resonse.jsp");
</code>
		 */
	}

	private List<String> executeReport(Connection conn, Long reportId, ReportType reportType, ReportService reportService,
			List<ReportParameterBean> reportParameterList, List<ReportPropertyBean> reportPropertyList) {
		List<String> responseList;
		if (!runFromConsole && reportService.runAsDetachedThread()) {
			reportService.setConnectionData(this.dsName);

			reportService.setReportId(reportId);
			reportService.setReportParameterList(reportParameterList);
			reportService.setReportPropertyList(reportPropertyList);
			reportService.setReportType(reportType);

			Thread thread = new Thread(reportService, reportService.getClass().getName());
			thread.start();
			responseList = new ArrayList<String>();
			responseList.add("La solicitud se esta procesando de manera desatendida.");
		} else {
			responseList = reportService.execute(conn, reportId, reportType, reportPropertyList, reportParameterList);
		}
		return responseList;
	}

	private List<ReportParameterBean> cloneParameterList(List<ReportParameterBean> sourceList) {
		List<ReportParameterBean> out = new ArrayList<ReportParameterBean>(sourceList.size());

		for (ReportParameterBean item : sourceList) {
			try {
				out.add((ReportParameterBean) item.clone());
			} catch (CloneNotSupportedException e) {
				LOG.log(Level.SEVERE, e.getMessage(), e);
			}
		}

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

	private Report getReport(Connection conn, Long reportId) {
		Report report = new Report();
		BSBeanUtils bu = new BSBeanUtils();
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
