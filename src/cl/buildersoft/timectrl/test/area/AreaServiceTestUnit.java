package cl.buildersoft.timectrl.test.area;

import java.sql.Connection;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import cl.buildersoft.framework.database.BSmySQL;
import cl.buildersoft.framework.util.BSConsole;
import cl.buildersoft.framework.util.BSTreeNode;
import cl.buildersoft.timectrl.business.console.AbstractConsoleService;
import cl.buildersoft.timectrl.business.services.AreaService;
import cl.buildersoft.timectrl.business.services.impl.AreaServiceImpl;

public class AreaServiceTestUnit extends AbstractConsoleService {
	private Connection conn = null;
	private BSmySQL mysql = null;

	@Before
	public void setUp() throws Exception {
		this.mysql = new BSmySQL();
		if (conn == null) {
			this.init();
			this.conn = getConnection();
		}
	}

	@Test
	public void testGetAsTree() {
		AreaService as = new AreaServiceImpl();

		List<BSTreeNode> tree = as.getAsTree(conn);
		BSConsole.println(tree.toString());
		Assert.assertTrue(true);
	}

}
