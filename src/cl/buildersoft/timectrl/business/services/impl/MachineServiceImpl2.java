package cl.buildersoft.timectrl.business.services.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.framework.database.BSmySQL;
import cl.buildersoft.framework.exception.BSConfigurationException;
import cl.buildersoft.framework.exception.BSDataBaseException;
import cl.buildersoft.timectrl.api.ClassFactory;
import cl.buildersoft.timectrl.api.IZKEMException;
import cl.buildersoft.timectrl.api._zkemProxy;
import cl.buildersoft.timectrl.business.beans.AttendanceLog;
import cl.buildersoft.timectrl.business.beans.Employee;
import cl.buildersoft.timectrl.business.beans.Fingerprint;
import cl.buildersoft.timectrl.business.beans.Machine;
import cl.buildersoft.timectrl.business.beans.MarkType;
import cl.buildersoft.timectrl.business.beans.Privilege;
import cl.buildersoft.timectrl.business.services.MachineService2;
import cl.buildersoft.timectrl.business.services.PrivilegeService;

import com4j.Holder;

public class MachineServiceImpl2 implements MachineService2 {
	private static final Logger LOG = Logger.getLogger(MachineServiceImpl2.class.getName());
	private final Map<Long, MarkType> markTypeMap = new HashMap<Long, MarkType>();

	private long readMarkType(Connection conn, Holder<Integer> dwInOutMode) {
		Long inOut = (long) dwInOutMode.value;
		Long out = null;

		MarkType markType = markTypeMap.get(inOut);

		if (markType != null) {
			out = markType.getId();
		} else {
			BSBeanUtils bu = new BSBeanUtils();
			markType = new MarkType();
			bu.search(conn, markType, "cKey=?", inOut);
			markTypeMap.put(inOut, markType);
			out = markType.getId();

		}

		return out;
	}

	@Override
	public _zkemProxy connect(Connection conn, Machine machine) {
		BSBeanUtils bu = new BSBeanUtils();
		_zkemProxy api = ClassFactory.createzkemProxy(conn);

		Boolean connected = api.connect_Net(machine.getIp(), machine.getPort().shortValue());
		if (connected) {
			machine.setLastAccess(new Timestamp(System.currentTimeMillis()));
			bu.update(conn, machine);
		} else {
			throw new BSConfigurationException("No se pudo conectar con la maquina " + machine.getName() + "[" + machine.getIp()
					+ ":" + machine.getPort() + "]");
		}

		return api;
	}

	@Override
	public List<AttendanceLog> listAttendence(Connection conn, _zkemProxy api, Machine machine) throws IZKEMException {
		List<AttendanceLog> out = new ArrayList<AttendanceLog>();
		AttendanceLog attendance = null;
		Boolean found = null;

		Integer dwMachineNumber = 1;
		Holder<String> dwEnrollNumber = new Holder<String>("");
		Holder<Integer> dwVerifyMode = new Holder<Integer>(0);
		Holder<Integer> dwInOutMode = new Holder<Integer>(0);
		Holder<Integer> dwYear = new Holder<Integer>(0);
		Holder<Integer> dwMonth = new Holder<Integer>(0);
		Holder<Integer> dwDay = new Holder<Integer>(0);
		Holder<Integer> dwHour = new Holder<Integer>(0);
		Holder<Integer> dwMinute = new Holder<Integer>(0);
		Holder<Integer> dwSecond = new Holder<Integer>(0);
		Holder<Integer> dwWorkCode = new Holder<Integer>(0);

		api.enableDevice(dwMachineNumber, false);
		if (api.readGeneralLogData(dwMachineNumber)) {
			while (readRecordFromMachine(conn, api, dwMachineNumber, dwEnrollNumber, dwVerifyMode, dwInOutMode, dwYear, dwMonth,
					dwDay, dwHour, dwMinute, dwSecond, dwWorkCode)) {
				attendance = new AttendanceLog();

				LOG.log(Level.FINE, "Year: {0}", dwYear.value.toString());

				attendance.setYear(dwYear.value);
				attendance.setMonth(dwMonth.value);
				attendance.setDay(dwDay.value);
				attendance.setHour(dwHour.value);
				attendance.setMinute(dwMinute.value);
				attendance.setSecond(dwSecond.value);

				attendance.setEmployeeKey(dwEnrollNumber.value);
				attendance.setMachine(machine.getId());
				attendance.setMarkType(readMarkType(conn, dwInOutMode));

				found = existsAttendanceLog(conn, attendance);
				if (!found) {
					out.add(attendance);
				}
				writeToConsole(attendance, found);

			}
		} else {
			Holder<Integer> error = new Holder<Integer>(0);
			api.getLastError(error);
			throw new IZKEMException(error.value);
		}

		api.enableDevice(dwMachineNumber, true);
		return out;
	}

	private void writeToConsole(AttendanceLog attendance, Boolean found) {
		LOG.log(Level.INFO, (found ? "Found: {0}" : "Not Found: {0}"), attendance.toString());
	}

	private boolean readRecordFromMachine(Connection conn, _zkemProxy api, int dwMachineNumber, Holder<String> dwEnrollNumber,
			Holder<Integer> dwVerifyMode, Holder<Integer> dwInOutMode, Holder<Integer> dwYear, Holder<Integer> dwMonth,
			Holder<Integer> dwDay, Holder<Integer> dwHour, Holder<Integer> dwMinute, Holder<Integer> dwSecond,
			Holder<Integer> dwWorkCode) {

		Boolean out = null;

		out = api.ssR_GetGeneralLogData(dwMachineNumber, dwEnrollNumber, dwVerifyMode, dwInOutMode, dwYear, dwMonth, dwDay,
				dwHour, dwMinute, dwSecond, dwWorkCode);

		return out;
	}

	@Override
	public List<EmployeeAndFingerprint> listEmployees(Connection conn, _zkemProxy api) {
		List<EmployeeAndFingerprint> out = new ArrayList<EmployeeAndFingerprint>();
		EmployeeAndFingerprint employee = null;
		Integer dwMachineNumber = 1;
		api.enableDevice(dwMachineNumber, false);

		api.readAllUserID(dwMachineNumber);
		PrivilegeService ps = new PrivilegeServiceImpl();
		while (employee != null) {
			employee = readEmployeeFromDevice(conn, ps, api, null);
			if (employee != null) {
				out.add(employee);
			}
		}

		api.enableDevice(dwMachineNumber, true);

		return out;
	}

	private EmployeeAndFingerprint readEmployeeFromDevice(Connection conn, PrivilegeService ps, _zkemProxy api,
			String enrollNumber) {
		Employee employee = null;
		Fingerprint fingerprint = null;
		EmployeeAndFingerprint out = null;

		int dwMachineNumber = 1;
		Holder<String> dwEnrollNumber = new Holder<String>(enrollNumber == null ? "" : enrollNumber);
		Holder<String> name = new Holder<String>();
		Holder<String> password = new Holder<String>();
		Holder<Integer> privilege = new Holder<Integer>(0);
		Holder<Boolean> enabled = new Holder<Boolean>();
		Holder<String> cardNumber = new Holder<String>();

		Boolean found = false;

		if (enrollNumber != null) {
			found = api.ssR_GetUserInfo(dwMachineNumber, dwEnrollNumber, name, password, privilege, enabled);
		} else {
			found = api.ssR_GetAllUserInfo(dwMachineNumber, dwEnrollNumber, name, password, privilege, enabled);
		}

		if (found) {
			employee = new Employee();
			fingerprint = new Fingerprint();
			out = new EmployeeAndFingerprint();

			employee.setEnabled(enabled.value);
			employee.setKey(dwEnrollNumber.value);
			employee.setName(name.value);

			employee.setPrivilege(getPrivilegeKeyToId(conn, ps, privilege.value));

			api.getStrCardNumber(cardNumber);
			fingerprint.setCardNumber(cardNumber.value);

			for (int dwFingerIndex = 0; dwFingerIndex < 10; dwFingerIndex++) {
				Holder<Integer> flag = new Holder<Integer>();
				Holder<String> fingerPrint = new Holder<String>();
				Holder<Integer> length = new Holder<Integer>();
				if (api.getUserTmpExStr(dwMachineNumber, employee.getKey(), dwFingerIndex, flag, fingerPrint, length)) {
					fingerprint.setFingerIndex(dwFingerIndex);
					fingerprint.setFlag(flag.value);
					fingerprint.setFingerprint(fingerPrint.value);
					break;
				}
			}

			out.setEmployee(employee);
			out.setFingerprint(fingerprint);
		}
		return out;
	}

	private Integer getPrivilegeIdToKey(Connection conn, PrivilegeService ps, Long priv) {
		Privilege privilege = ps.searchById(conn, priv);
		Integer out = null;
		if (privilege != null) {
			out = privilege.getKey();
		}
		return out;
	}

	private Long getPrivilegeKeyToId(Connection conn, PrivilegeService ps, Integer priv) {
		Privilege privilege = ps.searchByKey(conn, priv);
		Long out = null;
		if (privilege != null) {
			out = privilege.getId();
		}
		return out;
	}

	@Override
	public void addEmployees(Connection conn, PrivilegeService ps, _zkemProxy api,
			List<EmployeeAndFingerprint> employeeAndFingerprintList) {
		Integer dwMachineNumber = 1;
		Integer updateFlag = 1;
		api.enableDevice(dwMachineNumber, false);

		if (api.beginBatchUpdate(dwMachineNumber, updateFlag)) {
			for (EmployeeAndFingerprint employeeAndFingerprint : employeeAndFingerprintList) {
				saveEmployeeToDevice(conn, ps, api, dwMachineNumber, employeeAndFingerprint.getEmployee(),
						employeeAndFingerprint.getFingerprint());
				api.refreshData(dwMachineNumber);
			}
		}
		api.batchUpdate(dwMachineNumber);
		api.refreshData(dwMachineNumber);
		api.enableDevice(dwMachineNumber, true);
	}

	@Override
	public void addEmployee(Connection conn, PrivilegeService ps, _zkemProxy api, Employee employee, Fingerprint fingerprint) {
		Integer dwMachineNumber = 1;
		Integer updateFlag = 1;
		api.enableDevice(dwMachineNumber, false);

		if (api.beginBatchUpdate(dwMachineNumber, updateFlag)) {
			saveEmployeeToDevice(conn, ps, api, dwMachineNumber, employee, fingerprint);
			api.refreshData(dwMachineNumber);

		}
		api.batchUpdate(dwMachineNumber);
		api.refreshData(dwMachineNumber);
		api.enableDevice(dwMachineNumber, true);
	}

	private void saveEmployeeToDevice(Connection conn, PrivilegeService ps, _zkemProxy api, Integer dwMachineNumber,
			Employee employee, Fingerprint fingerprint) {
		String dwEnrollNumber = employee.getKey();
		String name = employee.getName();
		String password = "";
		int privilege = getPrivilegeIdToKey(conn, ps, employee.getPrivilege());
		boolean enabled = employee.getEnabled();
		String fingerPrint = fingerprint.getFingerprint();
		fingerPrint = (fingerPrint == null ? "" : fingerPrint);
		Integer flag = fingerprint.getFlag();
		flag = (flag == null ? 0 : flag);
		Integer dwFingerIndex = fingerprint.getFingerIndex();
		dwFingerIndex = (dwFingerIndex == null ? 0 : dwFingerIndex);

		Holder<String> cardNumber = new Holder<String>();
		cardNumber.value = fingerprint.getCardNumber();

		api.setStrCardNumber(cardNumber);

		if (api.ssR_SetUserInfo(dwMachineNumber, dwEnrollNumber, name, password, privilege, enabled)) {
			api.setUserTmpExStr(dwMachineNumber, dwEnrollNumber, dwFingerIndex, flag, fingerPrint);
		}
	}

	@Override
	public void deleteEmployees(_zkemProxy api, String[] keys) {
		Integer dwMachineNumber = 1;

		api.enableDevice(dwMachineNumber, false);
		for (String key : keys) {
			api.ssR_DeleteEnrollData(dwMachineNumber, key, 0);
		}
		api.enableDevice(dwMachineNumber, true);
	}

	@Override
	public void deleteEmployee(_zkemProxy api, String key) {
		Integer dwMachineNumber = 1;

		api.enableDevice(dwMachineNumber, false);

		api.ssR_DeleteEnrollData(dwMachineNumber, key, 12);

		api.enableDevice(dwMachineNumber, true);

	}

	@Override
	public void disconnect(_zkemProxy api) {
		api.disconnect();
	}

	@Override
	/**
	 * El método recibe los ID's de empleados registrados 
	 * en el reloj, con cada ID debe sincronizar con la 
	 * información de la base de datos.
	 * 
	 * RECORRER ID's
	  	  buscar_en_DB
			si no existe?
					crear en DB
					grabar fingerprint
			else
					si no tiene fingerprint en DB 
					   grabar fingerprint
					fin si
					SI nombre <> reloj.nombre
						grabar nombre en reloj
					Fin Si
					actualiza enabled
					actualiza privilegie
			fin si
	 * FIN RECORRIDO
	 * 
	 * */
	public void syncEmployees(Connection conn, _zkemProxy api, String[] keys) {
		BSBeanUtils bu = new BSBeanUtils();
		EmployeeAndFingerprint employeeDB = new EmployeeAndFingerprint();
		EmployeeAndFingerprint employeeAndFingerprintDevice = null;
		Integer dwMachineNumber = 1;
		PrivilegeService ps = new PrivilegeServiceImpl();

		api.enableDevice(dwMachineNumber, false);

		for (String key : keys) {
			employeeAndFingerprintDevice = readEmployeeFromDevice(conn, ps, api, key);
			if (!bu.search(conn, employeeDB.getEmployee(), "cKey=?", key)) {
				employeeAndFingerprintDevice.getEmployee().setGroup(getDefaultGroup(conn));
				bu.save(conn, employeeAndFingerprintDevice.getEmployee());
			} else {
				EmployeeAndFingerprint employeeMerged = mergeEmployee(employeeAndFingerprintDevice, employeeDB, ps);

				updateEmployeeToDevice(conn, ps, api, employeeMerged.getEmployee(), employeeMerged.getFingerprint());
				api.refreshData(dwMachineNumber);

				bu.save(conn, employeeMerged.getEmployee());
				bu.save(conn, employeeMerged.getFingerprint());

			}
		}
		api.enableDevice(dwMachineNumber, true);
	}

	@Override
	public EmployeeAndFingerprint mergeEmployee(EmployeeAndFingerprint employeeAndFingerprinDevice,
			EmployeeAndFingerprint employeeAndFingerprinDB, PrivilegeService ps) {

		EmployeeAndFingerprint out = new EmployeeAndFingerprint();

		Employee outEmployee = new Employee();
		Fingerprint outFingerprint = new Fingerprint();

		Employee employeeDB = employeeAndFingerprinDB.getEmployee();
		Fingerprint fingerprintDB = employeeAndFingerprinDB.getFingerprint();
		Employee employeeDev = employeeAndFingerprinDevice.getEmployee();
		Fingerprint fingerprintDev = employeeAndFingerprinDevice.getFingerprint();

		outEmployee.setEnabled(employeeDB.getEnabled() == null ? employeeDev.getEnabled() : employeeDB.getEnabled());

		outFingerprint.setFingerIndex(fingerprintDB.getFingerIndex() == null ? fingerprintDev.getFingerIndex() : fingerprintDB
				.getFingerIndex());

		outFingerprint.setFingerprint(fingerprintDB.getFingerprint() == null ? fingerprintDev.getFingerprint() : fingerprintDB
				.getFingerprint());

		outFingerprint.setFlag(fingerprintDB.getFlag() == null ? fingerprintDev.getFlag() : fingerprintDB.getFlag());

		outEmployee.setId(employeeDB.getId());

		outEmployee.setKey(employeeDB.getKey() == null ? employeeDev.getKey() : employeeDB.getKey());

		outEmployee.setName(employeeDB.getName() == null ? employeeDev.getName() : employeeDB.getName());

		outEmployee.setPost(employeeDB.getPost() == null ? employeeDev.getPost() : employeeDB.getPost());

		outEmployee.setPrivilege(employeeDB.getPrivilege() == null ? employeeDev.getPrivilege() : employeeDB.getPrivilege());

		outEmployee.setRut(employeeDB.getRut() == null ? employeeDev.getRut() : employeeDB.getRut());

		outFingerprint.setCardNumber(fingerprintDB.getCardNumber() == null ? fingerprintDev.getCardNumber() : fingerprintDB
				.getCardNumber());

		outEmployee.setArea(employeeDB.getArea());
		outEmployee.setGroup(employeeDB.getGroup());

		out.setEmployee(outEmployee);
		out.setFingerprint(outFingerprint);

		return out;
	}

	@Override
	public void updateEmployeeToDevice(Connection conn, PrivilegeService ps, _zkemProxy api, Employee employee,
			Fingerprint fingerprint) {
		Integer dwMachineNumber = 1;
		String password = "";
		Holder<String> cardNumber = new Holder<String>();

		cardNumber.value = fingerprint.getCardNumber();
		api.setStrCardNumber(cardNumber);

		api.ssR_SetUserInfo(dwMachineNumber, employee.getKey(), employee.getName(), password,
				getPrivilegeIdToKey(conn, ps, employee.getPrivilege()), employee.getEnabled());

	}

	@Override
	public String readSerial(_zkemProxy api) {
		Integer dwMachineNumber = 1;
		Holder<String> dwSerialNumber = new Holder<String>();
		api.getSerialNumber(dwMachineNumber, dwSerialNumber);
		return dwSerialNumber.value;
	}

	@Override
	public Boolean existsAttendanceLog(Connection conn, AttendanceLog attendance) {
		BSmySQL mysql = new BSmySQL();
		List<Object> params = new ArrayList<Object>();

		setParameters(attendance, params);

		ResultSet rs = mysql.callSingleSP(conn, "pExistsAttendanceLog", params);

		Boolean out = false;
		try {
			if (rs.next()) {
				out = rs.getInt(1) == 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			out = false;
		} finally {
			mysql.closeSQL(rs);
			mysql.closeSQL();
			params.clear();
		}

		return out;
	}

	@Override
	public void saveAttendanceLog(Connection conn, AttendanceLog attendance) {
		BSmySQL mysql = new BSmySQL();
		List<Object> params = new ArrayList<Object>();

		setParameters(attendance, params);

		ResultSet rs = null;
		try {
			rs = mysql.callSingleSP(conn, "pSaveAttendanceLog", params);
			if (rs.next())
				attendance.setId(rs.getLong(1));
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Error at executing SP pSaveAttendanceLog", e);
		} finally {
			mysql.closeSQL(rs);
			mysql.closeSQL();
			params.clear();
		}

	}

	private void setParameters(AttendanceLog attendance, List<Object> params) {
		params.add(attendance.getEmployeeKey());
		params.add(attendance.getMachine());
		params.add(attendance.getMarkType());
		params.add(attendance.getYear());
		params.add(attendance.getMonth());
		params.add(attendance.getDay());
		params.add(attendance.getHour());
		params.add(attendance.getMinute());
		params.add(attendance.getSecond());
	}

	@Override
	public Long getDefaultGroup(Connection conn) {
		BSBeanUtils bu = new BSBeanUtils();
		String sql = "SELECT MIN(cId) AS cMin FROM tGroup;";
		String min = bu.queryField(conn, sql, null);
		return Long.parseLong(min);
	}

	@Override
	public void saveAttendanceLog(Connection conn, List<AttendanceLog> attendanceList) {
		CallableStatement stmt = null;
		try {
			stmt = conn.prepareCall("call pSaveAttendanceLog2(?,?,?,?,?,?,?,?,?)");
			conn.setAutoCommit(false);

			for (AttendanceLog attendance : attendanceList) {
				Integer i = 1;
				stmt.setString(i++, attendance.getEmployeeKey());
				stmt.setLong(i++, attendance.getMachine());
				stmt.setLong(i++, attendance.getMarkType());
				stmt.setInt(i++, attendance.getYear());
				stmt.setInt(i++, attendance.getMonth());
				stmt.setInt(i++, attendance.getDay());
				stmt.setInt(i++, attendance.getHour());
				stmt.setInt(i++, attendance.getMinute());
				stmt.setInt(i++, attendance.getSecond());

				stmt.addBatch();
			}
			stmt.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new BSDataBaseException(e1);
			}
		} finally {
			try {
				if (!stmt.isClosed()) {
					stmt.clearBatch();
					stmt.close();
				}
			} catch (SQLException e) {
				throw new BSDataBaseException(e);
			}
		}
	}
}
