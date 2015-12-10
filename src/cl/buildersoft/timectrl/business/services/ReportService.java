package cl.buildersoft.timectrl.business.services;

import java.sql.Connection;
import java.util.List;

import cl.buildersoft.timectrl.business.beans.ReportParameterBean;
import cl.buildersoft.timectrl.business.beans.ReportPropertyBean;
import cl.buildersoft.timectrl.business.beans.ReportType;

public interface ReportService extends Runnable {
	public List<ReportParameterBean> loadParameter(Connection conn, Long reportId);

	public List<ReportPropertyBean> loadReportProperties(Connection conn, Long reportId);

	public List<String> execute(Connection conn, Long reportId, ReportType reportType,
			List<ReportPropertyBean> reportPropertyList, List<ReportParameterBean> reportParameterList);

	public void fillParameters(List<ReportParameterBean> reportParameterList, List<String> values);

	public ReportService getInstance(ReportType reportType);

	public Boolean runAsDetachedThread();

	public void setConnectionData(String driverName, String serverName, String database, String password, String username);

	public void setReportId(Long reportId);

	public void setReportType(ReportType reportType);

	public void setReportPropertyList(List<ReportPropertyBean> reportPropertyList);

	public void setReportParameterList(List<ReportParameterBean> reportParameterList);

}
