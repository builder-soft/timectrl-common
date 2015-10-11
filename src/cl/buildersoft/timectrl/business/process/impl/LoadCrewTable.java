package cl.buildersoft.timectrl.business.process.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.framework.database.BSmySQL;
import cl.buildersoft.framework.exception.BSDataBaseException;
import cl.buildersoft.framework.util.BSDateTimeUtil;
import cl.buildersoft.framework.util.BSUtils;
import cl.buildersoft.timectrl.business.beans.Employee;
import cl.buildersoft.timectrl.business.process.AbstractProcess;
import cl.buildersoft.timectrl.business.process.ExecuteProcess;

public class LoadCrewTable extends AbstractProcess implements ExecuteProcess {
	private String[] validArguments = { "DOMAIN" };

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

		BSmySQL mysql = new BSmySQL();
		Integer tolerance = Integer.parseInt(mysql.callFunction(conn, "fGetTolerance", null));
		Integer hoursWorkday = Integer.parseInt(mysql.callFunction(conn, "fGetHoursWorkday", null));

		List<Date> dateList = listDateUnprocessed(conn);

		for (Date date : dateList) {
			System.out.println("----------" + BSDateTimeUtil.date2String(date, "yyyy-MM-dd") + "----------");

			List<Employee> employeeList = listEmployeeByDate(conn, date);
			for (Employee employee : employeeList) {

				flexible = isFlexible(conn, mysql, date, employee);

				System.out.println(employee.getName() + " " + flexible);

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
</code>
		 */

	}

	private Boolean isFlexible(Connection conn, BSmySQL mysql, Date date, Employee employee) {
		Boolean out = null;
		String flexible = mysql.callFunction(conn, "fIsFlexible", BSUtils.array2List(date, employee.getId()));
		if ("1".equalsIgnoreCase(flexible)) {
			out = true;
		} else if ("0".equalsIgnoreCase(flexible)) {
			out = false;
		}

		System.out.println(flexible);
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
		sql += "WHERE DATE(cDate) = ? AND b.cid IS NULL;";

		BSmySQL mysql = new BSmySQL();

		ResultSet rs = mysql.queryResultSet(conn, sql, date);
		List<Employee> out = new ArrayList<Employee>();

		try {
			while (rs.next()) {
				employeeId = rs.getLong(1);

				employee = new Employee();
				employee.setId(employeeId);

				bu.search(conn, employee);

				out.add(employee);
			}
		} catch (SQLException e) {
			throw new BSDataBaseException(e);
		}

		return out;
	}

	private List<Date> listDateUnprocessed(Connection conn) {
		BSmySQL mysql = new BSmySQL();
		String sql = "SELECT DISTINCT DATE(cDate) AS cDate FROM tAttendanceLog AS a ";
		sql += "LEFT JOIN tCrewLog AS b ON a.cId = b.cAttendanceLog ";
		sql += "WHERE b.cid IS NULL ORDER BY cDate;";

		List<Date> out = new ArrayList<Date>();

		ResultSet rs = mysql.queryResultSet(conn, sql, null);

		try {
			while (rs.next()) {
				out.add(rs.getDate(1));
			}
		} catch (SQLException e) {
			throw new BSDataBaseException(e);
		}

		return out;
	}

}
