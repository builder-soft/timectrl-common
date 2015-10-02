package cl.buildersoft.timectrl.test;

import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cl.buildersoft.framework.database.BSmySQL;
import cl.buildersoft.timectrl.business.beans.TurnDay;

public class TestListAttendanceReport extends AbstractTestReport {

	private static final int JUEVES = 4;
	private static final int MARTES = 2;
	private static final int MIERCOLES = 3;
	private static final int LUNES = 1;
	private static final int VIERNES = 5;

	private static final long SOPORTE_TURN = 5L;
	private static final String SIN_MARCA = "";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("setUpBeforeClass");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("tearDownAfterClass");
	}

	@Before
	public void setUp() throws Exception {
		this.mysql = new BSmySQL();
		if (conn == null) {
			this.init();
			this.conn = getConnection();
		}
	}

	@After
	public void tearDown() throws Exception {
		// System.out.println("tearDown");
		new BSmySQL().closeConnection(this.conn);
		this.mysql = null;
		this.testNumber = null;
	}

	@Test
	public void test0() {
		assertTrue(rutToId("16606036-2") == 2L);
	}

	@Test
	public void test1() {
		// flagTest(1);
		List<Object> prm = getParameters("8796638-0", "2015-02-05");
		ResultSet rs = execute(mysql, prm);
		String success = validate(rs, prm, "00:00:49", "08:24:44", "-1", "25", "");

		mysql.closeSQL(rs);
		assertTrue(success, success.length() == 0);
	}

	@Test
	public void test2() {
		// flagTest(2);
		List<Object> prm = getParameters("8796638-0", "2015-02-06");
		ResultSet rs = execute(mysql, prm);
		String success = validate(rs, prm, "23:53:51", "08:15:31", "6", "16", "");

		mysql.closeSQL(rs);
		assertTrue(success, success.length() == 0);
	}

	@Test
	public void test3() {
		// System.out.println("\nTest2\n");
		List<Object> prm = getParameters("12927407-7", "2015-01-22");
		ResultSet rs = execute(mysql, prm);
		String success = validate(rs, prm, "07:53:19", "20:07:41", "7", "128", "");

		mysql.closeSQL(rs);
		assertTrue(success, success.length() == 0);
	}

	@Test
	public void test4() {
		List<Object> prm = getParameters("12927407-7", "2015-02-13");
		ResultSet rs = execute(mysql, prm);
		String success = validate(rs, prm, "08:08:26", "18:23:43", "-8", "24", "");

		mysql.closeSQL(rs);
		assertTrue(success, success.length() == 0);
	}

	@Test
	public void test5() {
		List<Object> prm = getParameters("9569677-5", "2014-12-12");
		ResultSet rs = execute(mysql, prm);
		String success = validate(rs, prm, "07:51:11", "16:30:45", "9", "-89", "");

		mysql.closeSQL(rs);
		assertTrue(success, success.length() == 0);
	}

	@Test
	public void test6() {
		// flagTest(6);
		List<Object> prm = getParameters("9569677-5", "2014-12-19");
		ResultSet rs = execute(mysql, prm);
		String success = validate(rs, prm, "07:55:01", "", "(5)", SIN_MARCA, "Sin salida");

		mysql.closeSQL(rs);
		assertTrue(success, success.length() == 0);
	}

	@Test
	public void test7() {
		// flagTest(7);
		List<Object> prm = getParameters("9569677-5", "2015-01-16");
		ResultSet rs = execute(mysql, prm);
		String success = validate(rs, prm, "", "", SIN_MARCA, SIN_MARCA, "Sin marcas");
		mysql.closeSQL(rs);
		assertTrue(success, success.length() == 0);
	}

	@Test
	public void test8() {
		// flagTest(8);
		List<Object> prm = getParameters("12927407-7", "2015-02-23");
		ResultSet rs = execute(mysql, prm);
		String success = validate(rs, prm, "", "18:23:47", SIN_MARCA, "(24)", "Sin entrada");

		mysql.closeSQL(rs);
		assertTrue(success, success.length() == 0);
	}

	@Test
	public void test9() {
		// flagTest(9);
		List<Object> prm = getParameters("13109449-3", "2014-12-02");
		ResultSet rs = execute(mysql, prm);
		String success = validate(rs, prm, "23:56:51", "08:03:09", "3", "3", "");
		mysql.closeSQL(rs);
		assertTrue(success, success.length() == 0);
	}

	@Test
	public void test10() {
		// flagTest(10);
		List<Object> prm = getParameters("13109449-3", "2014-12-10");
		ResultSet rs = execute(mysql, prm);
		String success = validate(rs, prm, "07:50:58", "16:10:29", "9", "10", "");
		mysql.closeSQL(rs);
		assertTrue(success, success.length() == 0);
	}

	@Test
	public void test11() {
		// flagTest(11);
		List<Object> prm = getParameters("13109449-3", "2014-12-17");
		ResultSet rs = execute(mysql, prm);
		String success = validate(rs, prm, "15:55:44", "00:06:16", "4", "6", "");
		mysql.closeSQL(rs);
		assertTrue(success, success.length() == 0);
	}

	/**
	 * <code>
 	public void test12() {
		flagTest(12);
		List<Object> prm = getParameters("13109449-3", "2014-12-18");

		setToleranceTime(180);

		ResultSet rs = execute(mysql, prm);
		String success = validate(rs, prm, "19:57:27", "08:06:14", "", "", "Fuera de Rango"); // String
		success = validate(rs, prm, "19:57:27", "08:06:14", -237, 6);
		mysql.closeSQL(rs);
		assertTrue(success, success.length() == 0);
	}	  
	 *       </code>
	 */

	@Test
	public void test13() {
		// flagTest(13);
		List<Object> prm = getParameters("13109449-3", "2014-12-28");
		ResultSet rs = execute(mysql, prm);
		String success = validate(rs, prm, "23:52:27", "07:59:39", "8", "0", "");
		mysql.closeSQL(rs);
		assertTrue(success, success.length() == 0);
	}

	@Test
	public void test14() {
		// flagTest(14);
		List<Object> prm = getParameters("9492875-3", "2015-02-20");

		Integer tolerance = getToleranceTime();
		setToleranceTime(4 * 60);

		ResultSet rs = execute(mysql, prm);
		String success = validate(rs, prm, "11:52:56", "16:27:11", "-203", "-3", "");

		setToleranceTime(tolerance);

		mysql.closeSQL(rs);
		assertTrue(success, success.length() == 0);
	}

	@Test
	public void test15() {
		// flagTest(15);
		List<Object> prm = getParameters("13038862-0", "2015-02-10");

		Integer tolerance = getToleranceTime();
		setToleranceTime(4 * 60);
		ResultSet rs = execute(mysql, prm);
		setToleranceTime(tolerance);

		String success = validate(rs, prm, "08:33:54", "22:26:16", "-4", "" + ((3 * 60) + 41), "");
		mysql.closeSQL(rs);
		assertTrue(success, success.length() == 0);
	}

	@Test
	public void test16() {
		// flagTest(16);
		List<Object> prm = getParameters("13038862-0", "2015-02-16");
		ResultSet rs = execute(mysql, prm);
		String success = validate(rs, prm, "", "20:51:59", SIN_MARCA, "(127)", "Sin entrada");
		mysql.closeSQL(rs);
		assertTrue(success, success.length() == 0);
	}

	@Test
	public void test17() {
		// flagTest(17);
		List<Object> prm = getParameters("12927407-7", "2015-02-21");

		// Integer tolerance = getToleranceTime();
		// setToleranceTime(300);

		ResultSet rs = execute(mysql, prm);
		String success = validate(rs, prm, "07:51:23", "22:19:40", "0", "" + (((21 - 7) * 60) - 52 + 80));
		// setToleranceTime(tolerance);

		mysql.closeSQL(rs);
		assertTrue(success, success.length() == 0);
	}

	@Test
	public void test18() {
		// flagTest(18);
		List<Object> prm = getParameters("12927407-7", "2015-02-22");

		// Integer tolerance = getToleranceTime();
		// setToleranceTime(300);

		ResultSet rs = execute(mysql, prm);
		String success = validate(rs, prm, "07:54:28", "", "(0)", "", "Sin salida");
		// setToleranceTime(tolerance);

		mysql.closeSQL(rs);
		assertTrue(success, success.length() == 0);
	}

	@Test
	public void test19() {
		// flagTest(19);
		List<Object> prm = getParameters("12915285-0", "2014-10-25");

		ResultSet rs = execute(mysql, prm);
		String success = validate(rs, prm, "15:49:49", "00:06:41", "0", "" + ((9 * 60) - 50 + 7));

		mysql.closeSQL(rs);
		assertTrue(success, success.length() == 0);
	}

	@Test
	public void test20() {
		// flagTest(20);
		List<Object> prm = getParameters("12915285-0", "2014-10-26");

		ResultSet rs = execute(mysql, prm);
		String success = validate(rs, prm, "16:02:19", "00:04:05", "0", "482");

		mysql.closeSQL(rs);
		assertTrue(success, success.length() == 0);
	}

	@Test
	public void test21() {
		// flagTest(21);
		List<Object> prm = getParameters("15951968-6", "2015-03-04");

		ResultSet rs = execute(mysql, prm);
		String success = validate(rs, prm, "07:57:29", "20:12:01", "3", "252");

		mysql.closeSQL(rs);
		assertTrue(success, success.length() == 0);
	}

	@Test
	public void test22() {
		// flagTest(22);
		List<Object> prm = getParameters("13388762-8", "2014-11-07");

		// Integer hoursWorkday = getHoursWorkday();
		// setHoursWorkday(17);

		ResultSet rs = execute(mysql, prm);
		String success = validate(rs, prm, "07:47:26", "", "(13)", "", "Sin salida");
		// setHoursWorkday(hoursWorkday);

		mysql.closeSQL(rs);
		assertTrue(success, success.length() == 0);
	}

	@Test
	public void test23() {
		// flagTest(23);
		List<Object> prm = getParameters("13388762-8", "2014-11-09");

		ResultSet rs = execute(mysql, prm);
		String success = validate(rs, prm, "07:42:37", "20:16:29", "17", "16");

		mysql.closeSQL(rs);
		assertTrue(success, success.length() == 0);
	}

	@Test
	public void test24() {
		// flagTest(24);
		List<Object> prm = getParameters("13388762-8", "2014-11-10");

		ResultSet rs = execute(mysql, prm);
		String success = validate(rs, prm, "07:47:39", "19:35:31", "12", "-24");

		mysql.closeSQL(rs);
		assertTrue(success, success.length() == 0);
	}

	@Test
	public void test25() {
		// flagTest(25);
		List<Object> prm = getParameters("13388762-8", "2014-11-18");

		ResultSet rs = execute(mysql, prm);
		String success = validate(rs, prm, "08:15:26", "20:08:51", "-15", "9");

		mysql.closeSQL(rs);
		assertTrue(success, success.length() == 0);
	}

	@Test
	public void test26() {
		// flagTest(26);
		List<Object> prm = getParameters("10436881-6", "2014-11-19");

		ResultSet rs = execute(mysql, prm);
		String success = validate(rs, prm, "19:43:07", "08:06:34", "17", "7");

		mysql.closeSQL(rs);
		assertTrue(success, success.length() == 0);
	}

	@Test
	public void test27() {
		// flagTest(27);
		List<Object> prm = getParameters("8796638-0", "2015-03-23");

		ResultSet rs = execute(mysql, prm);
		String success = validate(rs, prm, "", "", "", "", "");

		mysql.closeSQL(rs);
		assertTrue(success, success.length() == 0);
	}

	@Test
	public void test28() {
		List<Object> prm = getParameters("16912006-4", "2015-03-03");

		TurnDay turnDayBackup = getTurnDay(SOPORTE_TURN, MARTES);
		TurnDay turnDay = getTurnDay(SOPORTE_TURN, MARTES);
		turnDay.setEdgePostOut(10);
		saveTurn(turnDay);

		ResultSet rs = execute(mysql, prm);
		String success = validate(rs, prm, "08:59:24", "14:45:54", "1", "106");

		saveTurn(turnDayBackup);

		mysql.closeSQL(rs);
		assertTrue(success, success.length() == 0);
	}

	@Test
	public void test29() {
		List<Object> prm = getParameters("16912006-4", "2015-03-04");

		TurnDay turnDayBackup = getTurnDay(SOPORTE_TURN, MIERCOLES);
		TurnDay turnDay = getTurnDay(SOPORTE_TURN, MIERCOLES);
		setToleranceRange(turnDay, 0, 10, 0, 0);

		saveTurn(turnDay);

		ResultSet rs = execute(mysql, prm);
		String success = validate(rs, prm, "09:07:28", "", "(0)", "", "Sin salida");

		saveTurn(turnDayBackup);

		mysql.closeSQL(rs);
		assertTrue(success, success.length() == 0);
	}

	@Test
	public void test30() {
		List<Object> prm = getParameters("16912006-4", "2015-03-05");

		TurnDay turnDayBackup = getTurnDay(SOPORTE_TURN, JUEVES);
		TurnDay turnDay = getTurnDay(SOPORTE_TURN, JUEVES);
		setToleranceRange(turnDay, 0, 5, 0, 150);

		saveTurn(turnDay);

		ResultSet rs = execute(mysql, prm);
		String success = validate(rs, prm, "09:07:43", "15:11:46", "-8", "0", "");

		saveTurn(turnDayBackup);

		mysql.closeSQL(rs);
		assertTrue(success, success.length() == 0);
	}

	@Test
	public void test31() {
		List<Object> prm = getParameters("16912006-4", "2015-03-07");

		// TurnDay turnDayBackup = getTurnDay(SOPORTE_TURN, JUEVES);
		// TurnDay turnDay = getTurnDay(SOPORTE_TURN, JUEVES);
		// setToleranceRange(turnDay, 0, 5, 0, 150);

		// saveTurn(turnDay);

		ResultSet rs = execute(mysql, prm, "pListAttendance3");
		String success = validate(rs, prm, "", "", "", "", "");

		// saveTurn(turnDayBackup);

		mysql.closeSQL(rs);
		assertTrue(success, success.length() == 0);
	}
}
