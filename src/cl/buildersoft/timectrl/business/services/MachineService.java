package cl.buildersoft.timectrl.business.services;

import java.sql.Connection;
import java.util.List;

import cl.buildersoft.timectrl.api.com4j.IZKEM;
import cl.buildersoft.timectrl.api.impl.IZKEMException;
import cl.buildersoft.timectrl.business.beans.AttendanceLog;
import cl.buildersoft.timectrl.business.beans.Employee;
import cl.buildersoft.timectrl.business.beans.Machine;

public interface MachineService {
	public IZKEM connect(Connection conn, Machine machine);

	public void disconnect(IZKEM api);

	public List<AttendanceLog> listAttendence(Connection conn, IZKEM api, Machine machine) throws IZKEMException;

	public List<Employee> listEmployees(Connection conn, IZKEM api);

	public void addEmployees(IZKEM api, List<Employee> employees);

	public void deleteEmployees(IZKEM api, String[] employees);

	public void syncEmployees(Connection conn, IZKEM api, String[] employees);

	public String readSerial(IZKEM api);

}
