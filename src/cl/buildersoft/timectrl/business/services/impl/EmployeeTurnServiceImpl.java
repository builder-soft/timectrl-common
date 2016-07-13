package cl.buildersoft.timectrl.business.services.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cl.buildersoft.framework.exception.BSDataBaseException;
import cl.buildersoft.framework.util.BSDataUtils;
import cl.buildersoft.framework.util.BSDateTimeUtil;
import cl.buildersoft.timectrl.business.beans.EmployeeTurn;
import cl.buildersoft.timectrl.business.services.EmployeeTurnService;

public class EmployeeTurnServiceImpl implements EmployeeTurnService {

	@Override
	public List<EmployeeTurn> listAllEmployeeTurns(Connection conn, Long employee) {
		BSDataUtils du = new BSDataUtils();

		String sql = "SELECT r.cId, r.cTurn, r.cStartDate, r.cEndDate, t.cName AS cTurnName, r.cException AS cException ";
		sql += "FROM tR_EmployeeTurn AS r ";
		sql += "LEFT JOIN tTurn AS t ON r.cTurn = t.cId ";
		sql += "WHERE cEmployee=? ORDER BY r.cStartDate";

		ResultSet turnsRS = du.queryResultSet(conn, sql, employee);

		List<EmployeeTurn> out = new ArrayList<EmployeeTurn>();

		try {
			EmployeeTurn employeeTurn = null;

			while (turnsRS.next()) {
				employeeTurn = new EmployeeTurn();
				employeeTurn.setId(turnsRS.getLong("cId"));
				employeeTurn.setEmployee(employee);
				employeeTurn.setTurn(turnsRS.getLong("cTurn"));
				employeeTurn.setTurnName(turnsRS.getString("cTurnName"));
				employeeTurn.setStartDate(BSDateTimeUtil.date2Calendar(turnsRS.getDate("cStartDate")));
				employeeTurn.setEndDate(BSDateTimeUtil.date2Calendar(turnsRS.getDate("cEndDate")));
				employeeTurn.setException(turnsRS.getBoolean("cException"));
				out.add(employeeTurn);

			}
		} catch (SQLException e) {
			throw new BSDataBaseException(e);
		} finally {
			du.closeSQL(turnsRS);
			du.closeSQL();
		}

		return out;
	}

	@Override
	public void appendNew(Connection conn, EmployeeTurn employeeTurn) {
		String sql = "INSERT INTO tR_EmployeeTurn (cEmployee, cTurn, cStartDate, cEndDate, cException) ";
		sql += "VALUES(?,?,?,?,?);";

		List<Object> params = new ArrayList<Object>();
		params.add(employeeTurn.getEmployee());
		params.add(employeeTurn.getTurn());
		params.add(employeeTurn.getStartDate());
		params.add(employeeTurn.getEndDate());
		params.add(employeeTurn.getException());

		BSDataUtils du = new BSDataUtils();
		du.update(conn, sql, params);
		du.closeSQL();

	}

	@Override
	public void delete(Connection conn, Long employeeTurn) {

	}

	@Override
	public void update(Connection conn, EmployeeTurn employeeTurn) {
		String sql = "UPDATE tR_EmployeeTurn SET cTurn=?, cStartDate=?, cEndDate=? ";
		sql += "WHERE cId=?;";

		List<Object> params = new ArrayList<Object>();
		params.add(employeeTurn.getTurn());
		params.add(employeeTurn.getStartDate());
		params.add(employeeTurn.getEndDate());
		params.add(employeeTurn.getId());

		BSDataUtils du = new BSDataUtils();
		du.update(conn, sql, params);
		du.closeSQL();

	}

}
