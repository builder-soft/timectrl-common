package cl.buildersoft.timectrl.business.process.impl;

/**  
 * Este programa toma los registros de la tabla tAttendanceLog y los clasifica para volcarlos en la tabla tCrewProcess para obtener posteriormente el reporte de Dotaciones.
 Los registros que son procesados, quedan manrcados en la tabla tCrewLog.
 */

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.framework.database.BSmySQL;
import cl.buildersoft.framework.exception.BSDataBaseException;
import cl.buildersoft.framework.util.BSDateTimeUtil;
import cl.buildersoft.framework.util.BSUtils;
import cl.buildersoft.timectrl.business.beans.CrewProcess;
import cl.buildersoft.timectrl.business.beans.IdRut;
import cl.buildersoft.timectrl.business.beans.TurnDay;
import cl.buildersoft.timectrl.business.process.AbstractProcess;
import cl.buildersoft.timectrl.business.process.ExecuteProcess;
import cl.buildersoft.timectrl.business.services.TurnDayService;
import cl.buildersoft.timectrl.business.services.impl.TurnDayServiceImpl;

public class LoadCrewTable extends AbstractProcess implements ExecuteProcess {
	private static final Logger LOG = Logger.getLogger(LoadCrewTable.class.getName());
	private static final String DATE_TIME_FORMAT_CONST = "yyyy-MM-dd HH:mm:ss.S";
	private Map<Long, IdRut> idRutMap = new HashMap<Long, IdRut>();
	private String[] validArguments = { "DOMAIN" };
	private String dsName =null;

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

	public static void main(String[] args) {
		LoadCrewTable lct = new LoadCrewTable();
		lct.doExecute(args);
	}

	@Override
	public List<String> doExecute(String[] args) {
		LOG.entering(this.getClass().getName(), "doExecute", args);
		this.init();
		List<String> out = new ArrayList<String>();
		out.add("Process Done");
		validateArguments(args);
		Connection conn = getConnection(getDomainByBatabase(args[0]));

		LOG.log(Level.INFO, "Begin Process...");

		Boolean flexible = null;
		Boolean hiredDay = null;
		Double workedTime = null;
		Boolean attend = null;
		Calendar startMark = null;
		Calendar endMark = null;
		TurnDay turnDay = null;
		Boolean businessDay = null;
		Calendar calendar = null;
		BSmySQL mysql = new BSmySQL();

		try {
			TurnDayService tds = new TurnDayServiceImpl(conn);

			Integer tolerance = Integer.parseInt(mysql.callFunction(conn, "fGetTolerance", null));
			Integer hoursWorkday = Integer.parseInt(mysql.callFunction(conn, "fGetHoursWorkday", null));

			List<Date> dateList = listDateUnprocessed(conn);
			List<IdRut> employeeList = employeeList(conn);

			for (Date date : dateList) {
				calendar = BSDateTimeUtil.date2Calendar(date);
				LOG.log(Level.FINE, "----------" + BSDateTimeUtil.date2String(date, "yyyy-MM-dd") + "----------");

				for (IdRut employee : employeeList) {
					flexible = isFlexible(conn, mysql, date, (long) employee.getId());
					LOG.log(Level.FINE, "Employee: {0}, Flexible: {1}, Date: {2}",
							BSUtils.array2ObjectArray(employee, flexible, date));

					if (employee.getKey().equals("386") || employee.getKey().equals("192")) {
						LOG.log(Level.FINE, "Employee {0}", employee);
					}

					if (flexible == null) {
						hiredDay = false;
						workedTime = 0D;
						attend = false;
					} else {
						if (flexible) {
							startMark = getStartMark(conn, tds, employee.getKey(), tolerance, date, null, true, null);

							turnDay = getTurnDay(conn, tds, calendar, (long) employee.getId(), tolerance, true);
							if (turnDay != null) {
								businessDay = tds.isBusinessDay(turnDay);
							}
							hiredDay = true;

						} else {
							turnDay = getTurnDay(conn, tds, calendar, (long) employee.getId(), tolerance, false);
							businessDay = tds.isBusinessDay(turnDay);

							startMark = getStartMark(conn, tds, employee.getKey(), tolerance, date, businessDay, false, turnDay);
							// hiredDay = turnDay != null;
							hiredDay = businessDay;

							/**
							 * <code>
							 * 
							 * </code>
							 */

						}

					}
					endMark = getEndMark(conn, employee.getKey(), startMark, hoursWorkday, date, tolerance, businessDay, turnDay);

					if (startMark != null && endMark != null) {
						workedTime = getWorkedTime(startMark, endMark);
						attend = true;
					} else {
						workedTime = 0D;
						attend = false;
					}

					saveToCrewProcess(conn, date, (long) employee.getId(), workedTime, attend, hiredDay);

				}

				/**
				 * <code>
			if (employeeList.size() == 0) {
				saveToCrewProcess(conn, date, null, 0D, false, false);
				saveToCrewLog(conn, date, null);
			}</code>
				 */

				/**
				 * <code>
			try {
				Integer seconds = 5;
				LOG.log(Level.FINE, "Waiting {0} seconds", seconds);
				Thread.sleep(seconds * 1000);
			} catch (InterruptedException e) {
				LOG.log(Level.SEVERE, "InterruptedException", e);
			}
			</code>
				 */
			}
		} finally {
			mysql.closeConnection(conn);
		}
		LOG.log(Level.INFO, "Process Done!");
		return out;
	}

	@Override
	protected String[] getArguments() {
		return this.validArguments;
	}

	/**
	 * <code>
	private void saveToCrewLog(Connection conn, Date date, String employeeKey) {
		LOG.log(Level.FINE, "At save tCrewLog, parameters are Date:{0} and EmployeeKey: {1}",
				BSUtils.array2ObjectArray(date, employeeKey));
		BSmySQL mysql = new BSmySQL();
		String sql = "INSERT INTO tCrewLog(cAttendanceLog, cWhen) ";
		sql += "SELECT cId, NOW() FROM tAttendanceLog WHERE DATE(cDate)=? AND cEmployeeKey=?";

		List<Object> params = BSUtils.array2List(date, employeeKey);

		Integer counter = mysql.update(conn, sql, params);
		// if(counter==0){
		// sql = "INSERT INTO tCrewLog(cAttendanceLog, cWhen) VALUES();";
		// sql +=
		// "SELECT null, NOW() FROM tAttendanceLog WHERE DATE(cDate)=? AND cEmployeeKey=?";
		//
		// }
		mysql.closeSQL();
	}
</code>
	 */
	private void saveToCrewProcess(Connection conn, Date date, Long employeeId, Double workedTime, Boolean attend,
			Boolean hiredDay) {
		LOG.entering(this.getClass().getName(), "saveToCrewProcess",
				BSUtils.array2ObjectArray(date, employeeId, workedTime, attend, hiredDay));
		BSBeanUtils bu = new BSBeanUtils();

		CrewProcess crewProcess = new CrewProcess();
		if (bu.search(conn, crewProcess, "cDate=? AND cEmployee=?", date, employeeId)) {
			crewProcess.setHoursWorked(crewProcess.getHoursWorked() + workedTime);
		} else {
			crewProcess.setDate(date);
			crewProcess.setEmployee(employeeId);
			crewProcess.setHoursWorked(workedTime);
			crewProcess.setWorked(attend);
			crewProcess.setHired(hiredDay);
		}

		bu.save(conn, crewProcess);
		bu.closeSQL();
	}

	private Double getWorkedTime(Calendar startMark, Calendar endMark) {
		LOG.entering(this.getClass().getName(), "getWorkedTime",
				BSUtils.array2ObjectArray(BSDateTimeUtil.calendar2String(startMark), BSDateTimeUtil.calendar2String(endMark)));
		Double out = 0D;
		long diff = endMark.getTimeInMillis() - startMark.getTimeInMillis();

		BigDecimal secs = new BigDecimal((diff) / 1000);
		BigDecimal number3600 = new BigDecimal("3600");
		BigDecimal hours = secs.divide(number3600, 2, RoundingMode.HALF_UP);
		out = hours.doubleValue();

		LOG.exiting(this.getClass().getName(), "getWorkedTime", out);
		return out;
	}

	private Calendar getEndMark(Connection conn, String employeeKey, Calendar startMark, Integer hoursWorkday, Date date,
			Integer tolerance, Boolean businessDay, TurnDay turnDay) {

		BSmySQL mysql = new BSmySQL();
		Calendar out = null;

		String endMarkFromDB = null;

		if (startMark != null && businessDay != null && turnDay != null) {
			List<Object> parameters = BSUtils.array2List(employeeKey, startMark, hoursWorkday, date, tolerance, businessDay,
					turnDay.getId());
			endMarkFromDB = mysql.callFunction(conn, "fEndMark", parameters);
			mysql.closeSQL();
		}

		if (endMarkFromDB != null) {
			out = BSDateTimeUtil.string2Calendar(endMarkFromDB, DATE_TIME_FORMAT_CONST);
		}
		return out;

	}

	/**
	 * <code>
	private Boolean isBusinessDay(TurnDayService tds, TurnDay turnDay) {
		return tds.isBusinessDay(turnDay);
	}
</code>
	 */
	private Calendar getStartMark(Connection conn, TurnDayService tds, String employeeKey, Integer tolerance, Date date,
			Boolean businessDay, Boolean flexible, TurnDay turnDay) {
		BSmySQL mysql = new BSmySQL();
		Calendar out = null;

		String startMarkFromDB = mysql.callFunction(conn, "fStartMark", BSUtils.array2List(employeeKey, tolerance, date,
				businessDay, flexible, turnDay != null ? turnDay.getId() : turnDay));
		mysql.closeSQL();
		if (startMarkFromDB != null) {
			out = BSDateTimeUtil.string2Calendar(startMarkFromDB, "yyyy-MM-dd hh:mm:ss.S");
		}
		return out;
	}

	public TurnDay getTurnDay(Connection conn, TurnDayService tds, Calendar date, Long employeeId, Integer tolerance,
			Boolean flexible) {
		TurnDay out = tds.markAndUserToTurnDayId(conn, date, employeeId, tolerance, flexible);
		return out;
	}

	private Boolean isFlexible(Connection conn, BSmySQL mysql, Date date, Long employeeId) {
		Boolean out = null;
		String flexible = mysql.callFunction(conn, "fIsFlexible", BSUtils.array2List(date, employeeId));
		mysql.closeSQL();
		if ("1".equalsIgnoreCase(flexible)) {
			out = true;
		} else if ("0".equalsIgnoreCase(flexible)) {
			out = false;
		}
		return out;
	}

	private List<IdRut> employeeList(Connection conn) {
		IdRut idRut = null;
		Long employeeId = null;
		String sql = "SELECT DISTINCT c.cId ";
		sql += "FROM tAttendanceLog AS a ";
		sql += "LEFT JOIN tCrewLog AS b ON a.cId = b.cAttendanceLog ";
		sql += "LEFT JOIN tEmployee AS c ON a.cEmployeeKey = c.cKey ";
		sql += "WHERE DATE(cDate) = ? AND b.cid IS NULL AND NOT c.cId IS NULL;";

		sql = "SELECT DISTINCT c.cId FROM tAttendanceLog AS a LEFT JOIN tCrewLog AS b ON a.cId = b.cAttendanceLog AND b.cid IS NULL LEFT JOIN tEmployee AS c ON a.cEmployeeKey = c.cKey AND NOT c.cId IS NULL WHERE DATE(cDate) = ?  AND c.cId IS NOT NULL ORDER BY c.cId;";
		sql = "SELECT cId FROM tEmployee ORDER BY cKey;";

		LOG.log(Level.FINEST, "SQL for get Employees by Date is: {0}", sql);

		BSmySQL mysql = new BSmySQL();

		ResultSet rs = mysql.queryResultSet(conn, sql, null);
		List<IdRut> out = new ArrayList<IdRut>();
		String key = null;
		try {
			sql = "SELECT cKey FROM tEmployee WHERE cId=?";
			while (rs.next()) {
				employeeId = rs.getLong(1);
				idRut = idRutMap.get(employeeId);
				if (idRut == null) {
					key = mysql.queryField(conn, sql, employeeId);
					idRut = new IdRut();
					idRut.setId(employeeId.intValue());
					idRut.setKey(key);
					idRutMap.put(employeeId, idRut);
				}
				out.add(idRut);
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
		List<Date> out = new ArrayList<Date>();
		/**
		 * <code>
		Calendar maxDate = getMaxDate(conn);
		Calendar minDate = getMinDate(conn);

		LOG.log(Level.FINE, "Date range is {0} and {1}",
				BSUtils.array2ObjectArray(BSDateTimeUtil.calendar2String(minDate), BSDateTimeUtil.calendar2String(maxDate)));

		for (Date date = minDate.getTime(); minDate.before(maxDate); minDate.add(Calendar.DATE, 1), date = minDate.getTime()) {
			// Do your job here with `date`.
			out.add(date);
		}

		// getMaxDate(conn);
		
		</code>
		 */

		BSmySQL mysql = new BSmySQL();

		String sql = "SELECT DISTINCT DATE(cDate) AS cDate ";
		sql += "FROM tAttendanceLog AS a ";
		sql += "LEFT JOIN tCrewLog AS b ON a.cId = b.cAttendanceLog ";
		sql += "LEFT JOIN tEmployee AS c ON a.cEmployeeKey = c.cKey ";
		sql += "WHERE b.cid IS NULL AND c.cId IS NOT NULL ";
		sql += "ORDER BY cDate DESC;";

		// sql =
		// "SELECT DISTINCT DATE(cDate) AS cDate FROM tAttendanceLog AS a LEFT JOIN tCrewLog AS b ON a.cId = b.cAttendanceLog LEFT JOIN tEmployee AS c ON a.cEmployeeKey = c.cKey where c.cId IS NOT NULL ;";

		sql = "SELECT DISTINCT DATE(cDate) AS cDate FROM tAttendanceLog AS a LEFT JOIN tCrewLog AS b ON a.cId = b.cAttendanceLog AND b.cid IS NULL LEFT JOIN tEmployee AS c ON a.cEmployeeKey = c.cKey AND c.cId IS NOT NULL ORDER BY cDate DESC;";
		sql = "select DISTINCT DATE(a.cDate) AS cDate from tAttendanceLog as a left join tcrewprocess as b on date(a.cdate) = b.cdate and b.cid is null ORDER BY a.cDate DESC;";
		sql = "select DISTINCT DATE(a.cDate) from tAttendanceLog as a left join tcrewprocess as b on date(a.cdate) = b.cdate and b.cid is null left join temployee as c on a.cemployeeKey = c.ckey where c.cid is not null ORDER BY a.cDate DESC;";
		sql = "select DISTINCT DATE(a.cDate) from tAttendanceLog as a left join tcrewprocess as b on date(a.cdate) = date(b.cdate) left join temployee as c on a.cemployeeKey = c.ckey where c.cid is not null and b.cid is null ORDER BY a.cDate DESC;";
		sql = "select DISTINCT DATE(a.cDate) from tAttendanceLog as a left join temployee as c on a.cemployeeKey = c.ckey where date(a.cdate) not in (select distinct(cdate) from tcrewprocess) and c.cid is not null ORDER BY a.cDate DESC;";

		/**
		 * <code>
select a.*, b.*, c.cid, c.ckey # DISTINCT DATE(a.cDate), count(a.cdate) AS abc
from tAttendanceLog as a 
left join tcrewprocess as b on date(a.cdate) = date(b.cdate) #and b.cid is null 
left join temployee as c on a.cemployeeKey = c.ckey 
where c.cid is not null 
and b.cid is null 
and date(a.cdate) in ('2015-10-31', '2015-10-30')  
and a.cemployeekey='192'
#group by date(a.cDate)
ORDER BY a.cDate DESC;
</code>
		 */
		LOG.log(Level.CONFIG, "SQL for get Dates is: {0}", sql);

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
	/**
	 * <code>
	private Calendar getMaxDate(Connection conn) {
		return getLimitDate(conn, true);
	}

	private Calendar getMinDate(Connection conn) {
		return getLimitDate(conn, false);
	}

	private Calendar getLimitDate(Connection conn, boolean max) {
		Calendar out = null;
		String sql = "SELECT " + (max ? "MAX" : "MIN") + "(cDate) FROM tAttendanceLog;";
		BSmySQL mysql = new BSmySQL();
		ResultSet rs = mysql.queryResultSet(conn, sql, null);

		try {
			if (rs.next()) {
				out = BSDateTimeUtil.date2Calendar(rs.getDate(1));
			}
		} catch (SQLException e) {
			throw new BSDataBaseException(e);
		} finally {
			mysql.closeSQL(rs);
			mysql.closeSQL();
		}
		return out;
	}
	</code>
	 */

	@Override
	public void setDSName(String dsName) {
		this.dsName=dsName;
		
	}

	 
}
