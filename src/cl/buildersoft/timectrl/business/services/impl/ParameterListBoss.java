package cl.buildersoft.timectrl.business.services.impl;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.timectrl.business.beans.Post;
import cl.buildersoft.timectrl.business.beans.ReportParameterBean;
import cl.buildersoft.timectrl.business.services.EmployeeService;
import cl.buildersoft.timectrl.business.services.ParameterService;

public class ParameterListBoss implements ParameterService {

	@Override
	public Map<String, Object> getParameterData(Connection conn, ReportParameterBean reportParameterBean) {
		Map<String, Object> out = new HashMap<String, Object>();
		EmployeeService es = new EmployeeServiceImpl();
		out.put("BOSS_LIST", es.listBoss(conn));

		BSBeanUtils bu = new BSBeanUtils();
		List<Post> posts = (List<Post>) bu.listAll(conn, new Post());
		out.put("POST_LIST", posts);

		return out;
	}

}
