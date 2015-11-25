package cl.buildersoft.timectrl.test.loadCrewTable;

import java.sql.Connection;
import java.util.Calendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cl.buildersoft.timectrl.business.beans.TurnDay;
import cl.buildersoft.timectrl.business.process.impl.LoadCrewTable;
import cl.buildersoft.timectrl.business.services.TurnDayService;
import cl.buildersoft.timectrl.business.services.impl.TurnDayServiceImpl;
import cl.buildersoft.timectrl.test.AbstractTestReport;

public class GetTurnDay extends AbstractTestReport {
	private Connection conn = null;
	LoadCrewTable lct = null;

	@Before
	public void setUp() throws Exception {
		lct = new LoadCrewTable();
		this.init();
		this.conn = getConnection();
	}

	@Test
	public void testGetTurnDay1() {
		TurnDayService tds = new TurnDayServiceImpl(this.conn);
		Calendar c = Calendar.getInstance();
		c.set(2015, 9, 3);
		TurnDay turnDay = lct.getTurnDay(conn, tds, c, 31L, 150, false);
		System.out.println(turnDay);

		Assert.assertTrue(turnDay.getId() == 14);
	}

	@Test
	public void testGetTurnDay2() {
		TurnDayService tds = new TurnDayServiceImpl(this.conn);
		Calendar c = Calendar.getInstance();
		c.set(2015, 9, 3);
		TurnDay turnDay = lct.getTurnDay(conn, tds, c, 362L, 150, false);
		System.out.println(turnDay);

		Assert.assertTrue(turnDay.getId() == 14);
	}

}
