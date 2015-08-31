package cl.buildersoft.timectrl.business.services;

import java.sql.Connection;
import java.util.List;

import cl.buildersoft.timectrl.business.beans.ReportParameterBean;
import cl.buildersoft.timectrl.business.beans.ReportPropertyBean;
import cl.buildersoft.timectrl.business.beans.ReportType;

public interface ReportService {
	public List<ReportParameterBean> loadParameter(Connection conn, Long idReport);

	public List<ReportPropertyBean> loadReportProperties(Connection conn, Long idReport);

	public List<String> execute(Connection conn, Long idReport, ReportType reportType,
			List<ReportPropertyBean> reportPropertyList, List<ReportParameterBean> reportParameterList);

	public void fillParameters(List<ReportParameterBean> reportParameterList, List<String> values);

	public ReportService getInstance(ReportType reportType);

	public Object getParameterData(Connection conn, ReportParameterBean reportParameterBean);
}
