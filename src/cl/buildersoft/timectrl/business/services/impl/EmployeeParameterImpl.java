package cl.buildersoft.timectrl.business.services.impl;

import java.sql.Connection;

import cl.buildersoft.timectrl.business.beans.ReportParameterBean;
import cl.buildersoft.timectrl.business.services.ParameterService;

public class EmployeeParameterImpl implements ParameterService {

	@Override
	public Object getParameterData(Connection conn, ReportParameterBean reportParameterBean) {
	 /**
	  * Este metodo debe retornara lo siguiente:
	  * - Lista de empleados.
	  * - Lista de areas.
	  * - Lista de empleados que son jefes.
	  * 
	  * 
	  * */
		return null;
	}

}
