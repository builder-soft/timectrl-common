package cl.buildersoft.timectrl.business.services.impl;

import java.sql.Connection;
import java.util.List;

import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.framework.util.BSTreeNode;
import cl.buildersoft.timectrl.business.beans.Area;
import cl.buildersoft.timectrl.business.services.AreaService;

public class AreaServiceImpl implements AreaService {

	@Override
	public BSTreeNode getAsTree(Connection conn) {
		BSTreeNode out = new BSTreeNode();

		BSBeanUtils bu = new BSBeanUtils();
		List<Area> areaList = (List<Area>) bu.list(conn, new Area(), "cParent=null");
		BSTreeNode node = null;
		for (Area area : areaList) {
			node = new BSTreeNode();
			node.setValue(area);
			out.addChildren(node);
		}
		return out;
	}

}
