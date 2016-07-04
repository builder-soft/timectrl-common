package cl.buildersoft.timectrl.api.impl;

import java.sql.Connection;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.framework.util.BSConnectionFactory;
import cl.buildersoft.timectrl.business.beans.Machine;

public class ZKProxy2EventsTest {

	private static final String EMPLOYEE_KEY = "100";
	private static final String OSSA = "ossa";

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testOnAttTransactionEx1() {
		Machine machine = getMachine(OSSA, 6L);
		ZKProxy2Events let = new ZKProxy2Events(OSSA, machine);

		let.onAttTransactionEx(EMPLOYEE_KEY, 0, 1, 1, 2016, 7, 4, 14, 31, 28, 1);

		assertTrue(true);
	}

	@Test
	public void testOnAttTransactionEx2() {
		Machine machine = getMachine(OSSA, 6L);
		ZKProxy2Events let = new ZKProxy2Events(OSSA, machine);

		Calendar now = Calendar.getInstance();

		let.onAttTransactionEx(EMPLOYEE_KEY, 0, 1, 1, now.get(Calendar.YEAR), now.get(Calendar.MONTH)+1, now.get(Calendar.DAY_OF_MONTH),
				now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), now.get(Calendar.SECOND), 1);

		assertTrue(true);
	}

	private Machine getMachine(String domain, Long machineId) {
		BSConnectionFactory cf = new BSConnectionFactory();
		Connection conn = cf.getConnection(domain);
		BSBeanUtils bu = new BSBeanUtils();

		Machine out = new Machine();
		out.setId(machineId);
		bu.search(conn, out);

		cf.closeConnection(conn);

		return out;
	}

	@Test
	public void testOnAlarm1() {
		Machine machine = getMachine(OSSA, 6L);
		ZKProxy2Events let = new ZKProxy2Events(OSSA, machine);

		let.onAlarm(1, 100, 1);

		assertTrue(true);
	}
	
}
