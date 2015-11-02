package cl.buildersoft.timectrl.business.services.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.framework.util.BSTreeNode;
import cl.buildersoft.timectrl.business.beans.Area;
import cl.buildersoft.timectrl.business.services.AreaService;

public class AreaServiceImpl implements AreaService {

	@Override
	public List<BSTreeNode> getAsTree(Connection conn) {
		List<BSTreeNode> out = new ArrayList<BSTreeNode>();

		List<Area> areaList = listChilds(conn, null);
		BSTreeNode node = null;
		for (Area area : areaList) {
			node = new BSTreeNode();
			node.setValue(area);
			// out.addChildren(node);
			addChils(conn, node);
			out.add(node);
		}

		return out;
	}

	private void addChils(Connection conn, BSTreeNode node) {
		BSTreeNode temp = null;
		Area currentArea = (Area) node.getValue();
		List<Area> areaList = listChilds(conn, currentArea);
		for (Area area : areaList) {
			temp = new BSTreeNode();
			temp.setValue(area);
			addChils(conn, temp);
			node.addChildren(temp);
		}
	}

	private List<Area> listChilds(Connection conn, Area area) {
		BSBeanUtils bu = new BSBeanUtils();
		Object[] prms = null;

		String where = null;
		if (area == null) {
			where = "cParent IS NULL";
		} else {
			where = "cParent=?";
			prms = new Object[1];
			prms[0] = area.getId();
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
