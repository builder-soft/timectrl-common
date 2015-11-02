package cl.buildersoft.timectrl.business.services;

import java.sql.Connection;
import java.util.List;

import cl.buildersoft.framework.util.BSTreeNode;

public interface AreaService {
	public List<BSTreeNode> getAsTree(Connection conn);

}
