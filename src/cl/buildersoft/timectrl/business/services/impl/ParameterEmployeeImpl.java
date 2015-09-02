package cl.buildersoft.timectrl.business.services.impl;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cl.buildersoft.framework.beans.BSBean;
import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.timectrl.business.beans.Area;
import cl.buildersoft.timectrl.business.beans.Employee;
import cl.buildersoft.timectrl.business.beans.ReportParameterBean;
import cl.buildersoft.timectrl.business.services.EmployeeService;
import cl.buildersoft.timectrl.business.services.ParameterService;

public class ParameterEmployeeImpl implements ParameterService {

	@Override
	public Map<String, List<? extends BSBean>> getParameterData(Connection conn, ReportParameterBean reportParameterBean) {
		/**
		 * <code>
		   Este metodo debe retornara lo siguiente: 
		   - Lista de empleados. 
		   - Lista de areas. 
		   - Lista de empleados que son jefes.
		   
		   
		   </code>
		 */
		Map<String, List<? extends BSBean>> out = new HashMap<String, List<? extends BSBean>>();
		BSBeanUtils bu = new BSBeanUtils();
		EmployeeService es = new EmployeeServiceImpl();

		out.put("EMPLOYEE_LIST", bu.listAll(conn, new Employee() ));
		out.put("AREA_LIST", bu.listAll(conn, new Area() ));
		out.put("BOSS_LIST", es.listBoss(conn ));

		return out;
	}

}
