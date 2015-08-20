package cl.buildersoft.timectrl.business.services;

import java.sql.Connection;
import java.util.List;

import cl.buildersoft.timectrl.business.beans.EmployeeTurn;
 

public interface EmployeeTurnService {
	public List<EmployeeTurn> listAllEmployeeTurns(Connection conn, Long employeeId);

	public void appendNew(Connection conn, EmployeeTurn employeeTurn);

	public void delete(Connection conn, Long employeeTurn);
}
