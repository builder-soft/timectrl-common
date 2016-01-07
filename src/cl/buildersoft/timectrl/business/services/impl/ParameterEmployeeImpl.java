package cl.buildersoft.timectrl.business.services.impl;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import cl.buildersoft.timectrl.business.beans.ReportParameterBean;
import cl.buildersoft.timectrl.business.services.AreaService;
import cl.buildersoft.timectrl.business.services.ParameterService;

public class ParameterEmployeeImpl implements ParameterService {

	@Override
	public Map<String, Object> getParameterData(Connection conn, ReportParameterBean reportParameterBean) {
		/**
		 * <code>
		   Este metodo debe retornara lo siguiente: 
		   - Lista de empleados. 
		   - Lista de areas. 
		   - Lista de empleados que son jefes.
		   
		   
		   </code>
		 */
		Map<String, Object> out = new HashMap<String, Object>();
//		BSBeanUtils bu = new BSBeanUtils();
//		EmployeeService es = new EmployeeServiceImpl();
		AreaService as = new AreaServiceImpl();

//		out.put("EMPLOYEE_LIST", bu.listAll(conn, new Employee()));
//		out.put("AREA_LIST", bu.listAll(conn, new Area()));
//		out.put("BOSS_LIST", es.listBoss(conn));
		out.put("BOSS_TREE", as.getAsTree(conn));

		return out;
	}

}
