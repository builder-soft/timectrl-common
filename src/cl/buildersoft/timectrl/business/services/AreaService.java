package cl.buildersoft.timectrl.business.services;

import java.sql.Connection;

import cl.buildersoft.framework.util.BSTreeNode;

public interface AreaService {
	public BSTreeNode getAsTree(Connection conn);

}
