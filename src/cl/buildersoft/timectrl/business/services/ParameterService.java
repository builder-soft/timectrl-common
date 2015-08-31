package cl.buildersoft.timectrl.business.services;

import java.sql.Connection;

import cl.buildersoft.timectrl.business.beans.ReportParameterBean;

public interface ParameterService {
	public Object getParameterData(Connection conn, ReportParameterBean reportParameterBean);
}
