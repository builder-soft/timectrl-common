package cl.buildersoft.timectrl.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.framework.database.BSmySQL;
import cl.buildersoft.framework.util.BSConfig;
import cl.buildersoft.timectrl.business.beans.Employee;
import cl.buildersoft.timectrl.business.console.AbstractConsoleService;

public class AbstractTestReport extends AbstractConsoleService {
	private static final String APOSTROPHE = "'";
	private static final String EXPECTED_MESSAGE = "', expected='";
	private static final String TOLERANCE_INFERENCE = "TOLERANCE_INFERENCE";
	private static final String HOURS_WORKDAY = "HOURS_WORKDAY";
	Connection conn = null;
	BSmySQL mysql = null;
	Integer testNumber = null;

	protected String validate(ResultSet rs, List<Object> prm, String startMark, String endMark, String startDiffI,
			String endDiffI, String comment) {
		return validate(rs, prm, startMark, endMark, startDiffI, endDiffI);
	}

	protected String validate(ResultSet rs, List<Object> prm, String startMark, String endMark, String startDiffI, String endDiffI) {
		String out = "";
		String startMarkRS = null;
		String endMarkRS = null;
		String startDiffIRS = null;
		String endDiffIRS = null;
		String commentRS = null;
		String comment = "";

		try {
			if (rs.next()) {
				startMarkRS = rs.getString("cStartMark");
				endMarkRS = rs.getString("cEndMark");
				startDiffIRS = rs.getString("cStartDiffI");
				endDiffIRS = rs.getString("cEndDiffI");
				commentRS = rs.getString("cComment");

				if (this.testNumber != null) {
					System.out.println("Employee Key='" + idToKey(prm.get(0)) + "', Employee Id='" + prm.get(0) + APOSTROPHE);
					System.out.println("Fecha inicio: " + prm.get(1) + " Fecha termino: " + prm.get(2));
					System.out.println("cStartMark: '" + startMarkRS + APOSTROPHE);
					System.out.println("cEndMark: '" + endMarkRS + APOSTROPHE);
					System.out.println("cStartDiffI: '" + startDiffIRS + APOSTROPHE);
					System.out.println("cEndDiffI: '" + endDiffIRS + APOSTROPHE);
					System.out.println("cComment: '" + commentRS + APOSTROPHE);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			out = e.getMessage();
		}

		if (out.length() == 0) {
			if (!startMarkRS.equals(startMark)) {
				out = "Start Mark='" + startMarkRS + EXPECTED_MESSAGE + startMark + APOSTROPHE;
			} else if (!endMarkRS.equals(endMark)) {
				out = "End Mark='" + endMarkRS + EXPECTED_MESSAGE + endMark + APOSTROPHE;
			} else if (!startDiffIRS.equals(startDiffI)) {
				out = "Start Diff In='" + startDiffIRS + EXPECTED_MESSAGE + startDiffI + APOSTROPHE;
			} else if (!endDiffIRS.equals(endDiffI)) {
				out = "End Diff Out='" + endDiffIRS + EXPECTED_MESSAGE + endDiffI + APOSTROPHE;
			}
			// if (comment.length() > 0) {
			if (startMark.length() == 0 && endMark.length() == 0) {
				comment = "Sin marcas";
			} else if (startMark.length() == 0) {
				comment = "Sin entrada";
			} else if (endMark.length() == 0) {
				comment = "Sin salida";
			}

			Integer indexOf = commentRS.indexOf(comment);
			if (indexOf == -1) {
				out = "Comment='" + commentRS + EXPECTED_MESSAGE + comment + APOSTROPHE;
			}

			if (out.length() > 0) {
				out += " Employee Key='" + idToKey(prm.get(0)) + "', Employee Id='" + prm.get(0) + APOSTROPHE;
			}
		}

		return out;
	}

	protected List<Object> getParameters(String rut, String date) {
		return getParameters(rut, date, date);
	}

	protected List<Object> getParameters(String rut, String startDate, String endDate) {
		List<Object> out = new ArrayList<Object>();
		out.add(rutToId(rut));
		out.add(startDate);
		out.add(endDate);
		return out;
	}

	protected Long rutToId(String rut) {
		BSBeanUtils bu = new BSBeanUtils();
		Employee employee = new Employee();
		bu.search(conn, employee, "cRut=?", rut);
		// System.out.println("Rut:" + rut + " => " + employee.getId() +
		// " => Key:" + employee.getKey());
		return employee.getId();
	}

	private String idToKey(Object id) {
		BSBeanUtils bu = new BSBeanUtils();
		Employee employee = new Employee();
		employee.setId((Long) id);
		bu.search(conn, employee);
		// System.out.println("Rut:" + employee.getId() + " => Key:" +
		// employee.getKey());
		return employee.getKey();
	}

	protected ResultSet execute(BSmySQL mysql, List<Object> prm) {
		return mysql.callSingleSP(conn, "pListAttendance3", prm);
	}

	protected void flagTest(int testNumber) {
		this.testNumber = testNumber;
		System.out.println("Test " + this.testNumber);

	}

	protected Integer getToleranceTime() {
		BSConfig config = new BSConfig();
		Integer out = config.getInteger(conn, TOLERANCE_INFERENCE);
		return out;
	}

	protected void setToleranceTime(Integer value) {
		BSConfig config = new BSConfig();
		config.setInteger(conn, TOLERANCE_INFERENCE, value);
		// Integer out = config.getInteger(conn, "TOLERANCE_INFERENCE");

	}

	protected Integer getHoursWorkday() {
		BSConfig config = new BSConfig();
		Integer out = config.getInteger(conn, HOURS_WORKDAY);
		return out;
	}

	protected void setHoursWorkday(Integer value) {
		BSConfig config = new BSConfig();
		config.setInteger(conn, HOURS_WORKDAY, value);
		// Integer out = config.getInteger(conn, "TOLERANCE_INFERENCE");

	}
	
}
