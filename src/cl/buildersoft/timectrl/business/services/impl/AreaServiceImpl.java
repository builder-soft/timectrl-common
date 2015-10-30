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

		List<Area> areaList = listChilds(conn, null);
		BSTreeNode node = null;
		for (Area area : areaList) {
			node = new BSTreeNode();
			node.setValue(area);
//			out.addChildren(node);
			addChils(conn, node, area.getId());
		}

		return out;
	}

	private void addChils(Connection conn, BSTreeNode node, Long id) {
		BSTreeNode temp = null;
		List<Area> areaList = listChilds(conn, id);
		for (Area area : areaList) {
			temp = new BSTreeNode();
//			temp.setValue(area);
			node.addChildren(temp);
			addChils(conn, temp, area.getId());
		}
	}

	private List<Area> listChilds(Connection conn, Object object) {
		BSBeanUtils bu = new BSBeanUtils();
		Object[] prms = null;

		String where = null;
		if (object == null) {
			where = "cParent IS NULL";
		} else {
			where = "cParent=?";
			prms = new Object[1];
			prms[0] = object;
		}
		List<Area> areaList = (List<Area>) bu.list(conn, new Area(), where, prms);

		// BSTreeNode node = null;
		// for (Area area : areaList) {
		// node = new BSTreeNode();
		// node.setValue(area);
		// out.addChildren(node);
		// }
		return areaList;
	}

}
