package cl.buildersoft.timectrl.business.services;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import cl.buildersoft.framework.beans.BSBean;
import cl.buildersoft.timectrl.business.beans.ReportParameterBean;

public interface ParameterService {
	public Map<String, Object> getParameterData(Connection conn, ReportParameterBean reportParameterBean);
//	public List<List<? extends BSBean>> getParameterData(Connection conn, ReportParameterBean reportParameterBean);
}
