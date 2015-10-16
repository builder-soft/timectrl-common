package cl.buildersoft.timectrl.business.process.impl;

/**  
 * Este programa toma los registros de la tabla tAttendanceLog y los clasifica para volcarlos en la tabla tCrewProcess para obtener posteriormente el reporte de Dotaciones.
 Los registros que son procesados, quedan manrcados en la tabla tCrewLog.
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.framework.database.BSmySQL;
import cl.buildersoft.framework.exception.BSDataBaseException;
import cl.buildersoft.framework.util.BSDateTimeUtil;
import cl.buildersoft.framework.util.BSUtils;
import cl.buildersoft.timectrl.business.beans.CrewProcess;
import cl.buildersoft.timectrl.business.beans.Employee;
import cl.buildersoft.timectrl.business.beans.TurnDay;
import cl.buildersoft.timectrl.business.process.AbstractProcess;
import cl.buildersoft.timectrl.business.process.ExecuteProcess;
import cl.buildersoft.timectrl.business.services.TurnDayService;
import cl.buildersoft.timectrl.business.services.impl.TurnDayServiceImpl;

public class LoadCrewTable extends AbstractProcess implements ExecuteProcess {
	/**
	 * <code>
	SET vTolerance = fGetTolerance();
	SET vHoursWorkday = fGetHoursWorkday();
	dates = Obtener fechas que no esten en tCrewLog
	FOR(date : dates)
		employees = Obtener empleados para una fecha a la vez.
		FOR(empleado : empleados)
			SET vFlexible = fIsFlexible(vCurrent, vEmployeeId);
			
			IF(vFlexible IS NULL) THEN
				// Sin Turno
			ELSE
				IF(vFlexible) THEN
					SET vStartMark = fStartMark(vEmployeeKey, vTolerance, vCurrent, NULL, TRUE, NULL);
					SET vTurnDayId = fMarkAndUserToTurnDayId4(vStartMark, vEmployeeId, vTolerance, TRUE);
					SET vBusinessDay =  (SELECT cBusinessDay FROM tTurnDay WHERE cId = vTurnDayId);
					
					DIA_CONTRATADO = "SI";
					
				ELSE
					SET vTurnDayId = fMarkAndUserToTurnDayId4(vCurrent, vEmployeeId, vTolerance, FALSE);
					SET vBusinessDay =  (SELECT cBusinessDay FROM tTurnDay WHERE cId = vTurnDayId);
					SET vStartMark = fStartMark(vEmployeeKey, vTolerance, vCurrent, vBusinessDay, FALSE, vTurnDayId);
				END IF
				IF(vTurnDayId == null)
					DIA_CONTRATADO = "NO";
				ELSE
					DIA_CONTRATADO = "SI";
				END IF;
				
			END IF
			SET vEndMark = fEndMark(vEmployeeKey, vStartMark, vHoursWorkday, vCurrent, vTolerance, vBusinessDay, vTurnDayId);
			
			IF(vStartMark!=null AND vEndMark!=null)
				workedTime = Horas_trabajadas(vStartMark, vEndMark) // Calcula la diferencia entre ambos horarios 
				Presente = "SI";
			ELSE
				workedTime = 0;
				Presente = "NO";
			END IF;
			
			Iniciar Transaccion:
				Grabar en tCrewProcess(La informacion recopilada, validando que exista previamente para la fecha/empleado)
				Grabar en tCrewLog (Considerar todos los ID's para la fecha/empleado)
			Fin Transacción
			
			Limpiar las variables que se utilizaron(vFlexible, vStartMark, vTurnDayId, etc)
			
		FIN FOR
	FIN FOR

	cerrar la coneccion a la base de datos
	</code>
	 */

	private String[] validArguments = { "DOMAIN" };

	@Override
	protected String[] getArguments() {
		return this.validArguments;
	}

	public static void main(String[] args) {
		LoadCrewTable lct = new LoadCrewTable();
		lct.doExecute(args);
	}

	@Override
	public void doExecute(String[] args) {
		validateArguments(args);
		Connection conn = getConnection(getDomainByBatabase(args[0]));

		System.out.println("Begin Process!!!");
		Boolean flexible = null;
		Boolean hiredDay = null;
		Double workedTime = null;
		Boolean attend = null;
		Calendar startMark = null;
		Calendar endMark = null;
		TurnDay turnDay = null;
		Boolean businessDay = null;
		Calendar calendar = null;
		TurnDayService tds = new TurnDayServiceImpl(conn);

		BSmySQL mysql = new BSmySQL();
		Integer tolerance = Integer.parseInt(mysql.callFunction(conn, "fGetTolerance", null));
		Integer hoursWorkday = Integer.parseInt(mysql.callFunction(conn, "fGetHoursWorkday", null));

		List<Date> dateList = listDateUnprocessed(conn);

		for (Date date : dateList) {
			calendar = BSDateTimeUtil.date2Calendar(date);
			System.out.println("----------" + BSDateTimeUtil.date2String(date, "yyyy-MM-dd") + "----------");

			List<Employee> employeeList = listEmployeeByDate(conn, date);
			for (Employee employee : employeeList) {
				System.out.println(employee.toString());
				flexible = isFlexible(conn, mysql, date, employee);

				// System.out.println(employee.getName() + " " + flexible);

				if (flexible == null) {
					hiredDay = false;
					workedTime = 0D;
					attend = false;
				} else {
					if (flexible) {
						startMark = getStartMark(conn, tds, employee, tolerance, date, null, true, null);
						// SET vStartMark = fStartMark(vEmployeeKey, vTolerance,
						// vCurrent, NULL, TRUE, NULL);

						turnDay = getTurnDay(conn, tds, calendar, employee.getId(), tolerance, true);
						if (turnDay != null) {
							businessDay = tds.isBusinessDay(turnDay);
							hiredDay = true;
						}
					} else {
						turnDay = getTurnDay(conn, tds, calendar, employee.getId(), tolerance, false);
						businessDay = tds.isBusinessDay(turnDay);

						startMark = getStartMark(conn, tds, employee, tolerance, date, businessDay, false, turnDay);
						// SET vStartMark = fStartMark(vEmployeeKey, vTolerance,
						// vCurrent, vBusinessDay, FALSE, vTurnDayId);

					}
					hiredDay = turnDay != null;

				}
				endMark = getEndMark(conn, employee, startMark, hoursWorkday, date, tolerance, businessDay, turnDay);

				if (startMark != null && endMark != null) {
					// Calcula la diferencia entre ambos horarios
					workedTime = getWorkedTime(startMark, endMark);
					attend = true;
				} else {
					workedTime = 0D;
					attend = false;
				}

				// Iniciar Transaccion:
				saveToCrewProcess(conn, date, employee, workedTime, attend, hiredDay);
				saveToCrewLog(conn, date, employee);
				// Grabar en tCrewProcess(La informacion recopilada, validando
				// que exista previamente para la fecha/empleado)
				// Grabar en tCrewLog (Considerar todos los ID's para la
				// fecha/empleado)
				// Fin Transacción
				// Limpiar las variables que se utilizaron(vFlexible,
				// vStartMark, vTurnDayId, etc)

				/**
				 * <code>
				flexible = null;
				hiredDay = null;
				workedTime = null;
				attend = null;
				startMark = null;
				endMark = null;
				turnDay = null;
				businessDay = null;
</code>
				 */
			}
		}

		mysql.closeConnection(conn);
		/**
		 * <code>
FOR(date : dates)
	employees = Obtener empleados para una fecha a la vez.
	FOR(empleado : empleados)
		SET vFlexible = fIsFlexible(vCurrent, vEmployeeId);
		
		IF(vFlexible IS NULL) THEN
			DIA_CONTRATADO = "NO";
			workedTime = 0;
			Presente = "NO";
		ELSE
			IF(vFlexible) THEN
				SET vStartMark = fStartMark(vEmployeeKey, vTolerance, vCurrent, NULL, TRUE, NULL);
				SET vTurnDayId = fMarkAndUserToTurnDayId4(vStartMark, vEmployeeId, vTolerance, TRUE);
				SET vBusinessDay =  (SELECT cBusinessDay FROM tTurnDay WHERE cId = vTurnDayId);
				
				DIA_CONTRATADO = "SI";
				
			ELSE
				SET vTurnDayId = fMarkAndUserToTurnDayId4(vCurrent, vEmployeeId, vTolerance, FALSE);
				SET vBusinessDay =  (SELECT cBusinessDay FROM tTurnDay WHERE cId = vTurnDayId);
				SET vStartMark = fStartMark(vEmployeeKey, vTolerance, vCurrent, vBusinessDay, FALSE, vTurnDayId);
			END IF
			IF(vTurnDayId == null)
				DIA_CONTRATADO = "NO";
			ELSE
				DIA_CONTRATADO = "SI";
			END IF;
			
		END IF
		SET vEndMark = fEndMark(vEmployeeKey, vStartMark, vHoursWorkday, vCurrent, vTolerance, vBusinessDay, vTurnDayId);
		
		IF(vStartMark!=null AND vEndMark!=null)
			workedTime = Horas_trabajadas(vStartMark, vEndMark) // Calcula la diferencia entre ambos horarios 
			Presente = "SI";
		ELSE
			workedTime = 0;
			Presente = "NO";
		END IF;
		
		Iniciar Transaccion:
			Grabar en tCrewProcess(La informacion recopilada, validando que exista previamente para la fecha/empleado)
			Grabar en tCrewLog (Considerar todos los ID's para la fecha/empleado)
		Fin Transacción
		
		Limpiar las variables que se utilizaron(vFlexible, vStartMark, vTurnDayId, etc)
		
	FIN FOR
FIN FOR
</code>
		 */

	}

	private void saveToCrewLog(Connection conn, Date date, Employee employee) {
		BSmySQL mysql = new BSmySQL();
		String sql = "INSERT INTO tCrewLog(cAttendanceLog, cWhen) ";
		sql += "SELECT cId,NOW() FROM tAttendanceLog WHERE DATE(cDate)=? AND cEmployeeKey=?";

		List<Object> params = BSUtils.array2List(date, employee.getKey());

		mysql.update(conn, sql, params);
		mysql.closeSQL();
	}

	private void saveToCrewProcess(Connection conn, Date date, Employee employee, Double workedTime, Boolean attend,
			Boolean hiredDay) {
		BSBeanUtils bu = new BSBeanUtils();

		CrewProcess crewProcess = new CrewProcess();
		if (bu.search(conn, crewProcess, "cDate=? AND cEmployee=?", date, employee.getId())) {
			crewProcess.setHoursWorked(crewProcess.getHoursWorked() + workedTime);
			// crewProcess.setWorked(attend);
			// crewProcess.setHired(hiredDay);
		} else {
			crewProcess.setDate(date);
			crewProcess.setEmployee(employee.getId());
			crewProcess.setHoursWorked(workedTime);
			crewProcess.setWorked(attend);
			crewProcess.setHired(hiredDay);
		}

		bu.save(conn, crewProcess);
		bu.closeSQL();
	}

	private Double getWorkedTime(Calendar startMark, Calendar endMark) {
		long diff = endMark.getTimeInMillis() - startMark.getTimeInMillis();

		// Calendar calendar = Calendar.getInstance( );
		// calendar.setTimeInMillis(diff);

		return (double) ((diff / 1000) / 3600);
	}

	private Calendar getEndMark(Connection conn, Employee employee, Calendar startMark, Integer hoursWorkday, Date date,
			Integer tolerance, Boolean businessDay, TurnDay turnDay) {

		// SET vEndMark = fEndMark(vEmployeeKey, vStartMark, vHoursWorkday,
		// vCurrent, vTolerance, vBusinessDay, vTurnDayId);

		BSmySQL mysql = new BSmySQL();
		Calendar out = null;

		String endMarkFromDB = null;

		if (startMark != null && businessDay != null && turnDay != null) {
			List<Object> parameters = BSUtils.array2List(employee.getKey(), startMark, hoursWorkday, date, tolerance,
					businessDay, turnDay.getId());
			endMarkFromDB = mysql.callFunction(conn, "fEndMark", parameters);
			mysql.closeSQL();
		}

		if (endMarkFromDB != null) {
			out = BSDateTimeUtil.string2Calendar(endMarkFromDB, "yyyy-MM-dd HH:mm:ss.S");
		}
		return out;

	}

	private Boolean isBusinessDay(TurnDayService tds, TurnDay turnDay) {
		return tds.isBusinessDay(turnDay);
	}

	private Calendar getStartMark(Connection conn, TurnDayService tds, Employee employee, Integer tolerance, Date date,
			Boolean businessDay, Boolean flexible, TurnDay turnDay) {
		/**
		 * CREATE FUNCTION fStartMark(vEmployeeKey VARCHAR(20), vTolerance
		 * INTEGER, vCurrent DATE, vBusinessDay BOOLEAN, vFlexible BOOLEAN,
		 * vTurnDayId BIGINT(20)) RETURNS TIMESTAMP
		 */

		BSmySQL mysql = new BSmySQL();
		Calendar out = null;

		String startMarkFromDB = mysql.callFunction(conn, "fStartMark", BSUtils.array2List(employee.getKey(), tolerance, date,
				businessDay, flexible, turnDay != null ? turnDay.getId() : turnDay));
		mysql.closeSQL();
		if (startMarkFromDB != null) {
			out = BSDateTimeUtil.string2Calendar(startMarkFromDB, "yyyy-MM-dd hh:mm:ss.S");
		}
		return out;
	}

	private TurnDay getTurnDay(Connection conn, TurnDayService tds, Calendar date, Long employeeId, Integer tolerance,
			Boolean flexible) {
		TurnDay out = tds.markAndUserToTurnDayId(conn, date, employeeId, tolerance, flexible);
		return out;
	}

	private Boolean isFlexible(Connection conn, BSmySQL mysql, Date date, Employee employee) {
		Boolean out = null;
		String flexible = mysql.callFunction(conn, "fIsFlexible", BSUtils.array2List(date, employee.getId()));
		mysql.closeSQL();
		if ("1".equalsIgnoreCase(flexible)) {
			out = true;
		} else if ("0".equalsIgnoreCase(flexible)) {
			out = false;
		}
		return out;
	}

	private List<Employee> listEmployeeByDate(Connection conn, Date date) {
		BSBeanUtils bu = new BSBeanUtils();
		Employee employee = null;
		Long employeeId = null;
		String sql = "SELECT DISTINCT c.cId ";
		sql += "FROM tAttendanceLog AS a ";
		sql += "LEFT JOIN tCrewLog AS b ON a.cId = b.cAttendanceLog ";
		sql += "LEFT JOIN tEmployee AS c ON a.cEmployeeKey = c.cKey ";
		sql += "WHERE DATE(cDate) = ? AND b.cid IS NULL AND NOT c.cId IS NULL;";

		BSmySQL mysql = new BSmySQL();

		ResultSet rs = mysql.queryResultSet(conn, sql, date);
		List<Employee> out = new ArrayList<Employee>();

		try {
			while (rs.next()) {
				employeeId = rs.getLong(1);

				employee = new Employee();
				employee.setId(employeeId);

				if (bu.search(conn, employee)) {
					out.add(employee);
				}
				bu.closeSQL();

			}
		} catch (SQLException e) {
			throw new BSDataBaseException(e);
		} finally {
			mysql.closeSQL(rs);
			mysql.closeSQL();
		}

		return out;
	}

	private List<Date> listDateUnprocessed(Connection conn) {
		BSmySQL mysql = new BSmySQL();
		// String sql =
		// "SELECT DISTINCT DATE(cDate) AS cDate FROM tAttendanceLog AS a ";
		// sql += "LEFT JOIN tCrewLog AS b ON a.cId = b.cAttendanceLog ";
		// sql += "WHERE b.cid IS NULL ORDER BY cDate;";

		String sql = "SELECT DISTINCT DATE(cDate) AS cDate ";
		sql += "FROM tAttendanceLog AS a ";
		sql += "LEFT JOIN tCrewLog AS b ON a.cId = b.cAttendanceLog ";
		sql += "LEFT JOIN tEmployee AS c ON a.cEmployeeKey = c.cKey ";
		sql += "WHERE b.cid IS NULL AND c.cId IS NOT NULL ";
		// sql += " and year(cdate)>2014 ";
		sql += "ORDER BY cDate";

		List<Date> out = new ArrayList<Date>();

		ResultSet rs = mysql.queryResultSet(conn, sql, null);

		try {
			while (rs.next()) {
				out.add(rs.getDate(1));
			}
		} catch (SQLException e) {
			throw new BSDataBaseException(e);
		} finally {
			mysql.closeSQL(rs);
			mysql.closeSQL();
		}

		return out;
	}
}
