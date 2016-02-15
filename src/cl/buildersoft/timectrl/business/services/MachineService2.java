package cl.buildersoft.timectrl.business.services;

import java.sql.Connection;
import java.util.List;

import cl.buildersoft.timectrl.api.IZKEMException;
import cl.buildersoft.timectrl.api._zkemProxy;
import cl.buildersoft.timectrl.business.beans.AttendanceLog;
import cl.buildersoft.timectrl.business.beans.Machine;
import cl.buildersoft.timectrl.business.services.impl.EmployeeAndFingerprint;

public interface MachineService2 {
	public _zkemProxy connect(Connection conn, Machine machine);

	public void disconnect(_zkemProxy api);

	public List<AttendanceLog> listAttendence(Connection conn, _zkemProxy api, Machine machine) throws IZKEMException;

	public List<EmployeeAndFingerprint> listEmployees(Connection conn, _zkemProxy api);

	public void addEmployees(Connection conn, PrivilegeService ps, _zkemProxy api, List<EmployeeAndFingerprint> employees);

	public void addEmployee(Connection conn, PrivilegeService ps, _zkemProxy api, EmployeeAndFingerprint eaf);

	public void deleteEmployees(_zkemProxy api, String[] employees);

	public void deleteEmployee(_zkemProxy api, String key);

	public void syncEmployees(Connection conn, _zkemProxy api, String[] employees);

	public String readSerial(_zkemProxy api);

	public Boolean existsAttendanceLog(Connection conn, AttendanceLog attendance);

	public void saveAttendanceLog(Connection conn, AttendanceLog attendance);
	public void saveAttendanceLog(Connection conn, List<AttendanceLog> attendanceList);

	public EmployeeAndFingerprint mergeEmployee(EmployeeAndFingerprint employeeDevice, EmployeeAndFingerprint employeeDB, PrivilegeService ps);

	public void updateEmployeeToDevice(Connection conn, PrivilegeService ps, _zkemProxy api, EmployeeAndFingerprint eaf);

	public Long getDefaultGroup(Connection conn);
}
