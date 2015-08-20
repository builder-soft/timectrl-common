package cl.buildersoft.timectrl.business.services;

import java.sql.Connection;
import java.util.List;

import cl.buildersoft.timectrl.business.beans.ReportInputParameterBean;
import cl.buildersoft.timectrl.business.beans.ReportPropertyBean;
import cl.buildersoft.timectrl.business.beans.ReportType;

public interface ReportService {
	public List<ReportInputParameterBean> loadInputParameter(Connection conn, Long idReport);

	public List<ReportPropertyBean> loadReportProperties(Connection conn, Long idReport);

	public List<String> execute(Connection conn, Long idReport, ReportType reportType, List<ReportPropertyBean> reportPropertyList,
			List<ReportInputParameterBean> reportInputParameterList);

	public void fillInputParameters(List<ReportInputParameterBean> reportInParamList, List<String> values);

	public ReportService getInstance(ReportType reportType);
}
