package cl.buildersoft.timectrl.business.services.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.framework.database.BSmySQL;
import cl.buildersoft.framework.exception.BSConfigurationException;
import cl.buildersoft.framework.exception.BSDataBaseException;
import cl.buildersoft.framework.exception.BSException;
import cl.buildersoft.timectrl.api.ClassFactory;
import cl.buildersoft.timectrl.api.IZKEMException;
import cl.buildersoft.timectrl.api._zkemProxy;
import cl.buildersoft.timectrl.business.beans.AttendanceLog;
import cl.buildersoft.timectrl.business.beans.Employee;
import cl.buildersoft.timectrl.business.beans.Machine;
import cl.buildersoft.timectrl.business.beans.MarkType;
import cl.buildersoft.timectrl.business.beans.Privilege;
import cl.buildersoft.timectrl.business.process.AbstractProcess;
import cl.buildersoft.timectrl.business.services.MachineService2;
import cl.buildersoft.timectrl.business.services.PrivilegeService;
import com4j.Holder;

public class MachineServiceImpl2 implements MachineService2 {
	// private Boolean windows8compatible = null;

	private static final Logger LOG = Logger.getLogger(MachineServiceImpl2.class
			.getName());
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
		// Timestamp date = null;
		// Calendar calendar = null;

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
		LOG.log(Level.INFO, (found ? "Exists: {0}" : "Absent: {0}") , attendance.toString());
	}

	private long readMarkType(Connection conn, Holder<Integer> dwInOutMode) {
		Long inOut = (long) dwInOutMode.value;
		BSBeanUtils bu = new BSBeanUtils();

		MarkType markType = new MarkType();
		bu.search(conn, markType, "cKey=?", inOut);

		return markType.getId();
	}

	private boolean readRecordFromMachine(Connection conn, _zkemProxy api, int dwMachineNumber, Holder<String> dwEnrollNumber,
			Holder<Integer> dwVerifyMode, Holder<Integer> dwInOutMode, Holder<Integer> dwYear, Holder<Integer> dwMonth,
			Holder<Integer> dwDay, Holder<Integer> dwHour, Holder<Integer> dwMinute, Holder<Integer> dwSecond,
			Holder<Integer> dwWorkCode) {

		Boolean out = null;
		/**
		 * <code>
		if (this.windows8compatible == null) {
			BSConfig config = new BSConfig();
			this.windows8compatible = config.getBoolean(conn, "W8COMPATIBLE");
			this.windows8compatible = false;
		}
</code>
		 */
		// if (!windows8compatible) {
		out = api.ssR_GetGeneralLogData(dwMachineNumber, dwEnrollNumber, dwVerifyMode, dwInOutMode, dwYear, dwMonth, dwDay,
				dwHour, dwMinute, dwSecond, dwWorkCode);
		/**
		 * <code>
		} else {
			Holder<Integer> dwTMachineNumber = new Holder<Integer>();
			Holder<Integer> dwEMachineNumber = new Holder<Integer>();
			Holder<Integer> dwEnrollNumberI = new Holder<Integer>();

			out = api.getGeneralLogData(dwMachineNumber, dwTMachineNumber, dwEnrollNumberI, dwEMachineNumber,
					dwVerifyMode, dwInOutMode, dwYear, dwMonth, dwDay, dwHour, dwMinute);

			dwEnrollNumber.value = "" + dwEnrollNumberI.value;
		}
		</code>
		 */
		return out;
	}

	@Override
	public List<Employee> listEmployees(Connection conn, _zkemProxy api) {
		List<Employee> out = new ArrayList<Employee>();
		Employee employee = new Employee();
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

	/**
	 * <code>
	 * 
	 * @Deprecated private boolean readUserinfo(IZKEM api, Integer
	 *             dwMachineNumber, Holder<String> dwEnrollNumber,
	 *             Holder<String> name, Holder<String> password, Holder<Integer>
	 *             privilege, Holder<Boolean> enabled) { return
	 *             api.ssR_GetAllUserInfo(dwMachineNumber, dwEnrollNumber, name,
	 *             password, privilege, enabled); } </code>
	 */

	private Employee readEmployeeFromDevice(Connection conn, PrivilegeService ps, _zkemProxy api, String enrollNumber) {
		Employee out = null;

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
			out = new Employee();
			out.setEnabled(enabled.value);
			out.setKey(dwEnrollNumber.value);
			out.setName(name.value);
			out.setPrivilege(getPrivilegeKeyToId(conn, ps, privilege.value));

			api.getStrCardNumber(cardNumber);
			out.setCardNumber(cardNumber.value);

			for (int dwFingerIndex = 0; dwFingerIndex < 10; dwFingerIndex++) {
				Holder<Integer> flag = new Holder<Integer>();
				Holder<String> fingerPrint = new Holder<String>();
				Holder<Integer> length = new Holder<Integer>();
				if (api.getUserTmpExStr(dwMachineNumber, out.getKey(), dwFingerIndex, flag, fingerPrint, length)) {
					out.setFingerIndex(dwFingerIndex);
					out.setFlag(flag.value);
					out.setFingerPrint(fingerPrint.value);
					break;
				}
			}
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
	public void addEmployees(Connection conn, PrivilegeService ps, _zkemProxy api, List<Employee> employees) {
		Integer dwMachineNumber = 1;
		Integer updateFlag = 1;
		api.enableDevice(dwMachineNumber, false);

		if (api.beginBatchUpdate(dwMachineNumber, updateFlag)) {
			for (Employee employee : employees) {
				saveEmployeeToDevice(conn, ps, api, dwMachineNumber, employee);
				api.refreshData(dwMachineNumber);
			}
		}
		api.batchUpdate(dwMachineNumber);
		api.refreshData(dwMachineNumber);
		api.enableDevice(dwMachineNumber, true);
	}

	@Override
	public void addEmployee(Connection conn, PrivilegeService ps, _zkemProxy api, Employee employee) {
		Integer dwMachineNumber = 1;
		Integer updateFlag = 1;
		api.enableDevice(dwMachineNumber, false);

		if (api.beginBatchUpdate(dwMachineNumber, updateFlag)) {
			saveEmployeeToDevice(conn, ps, api, dwMachineNumber, employee);
			api.refreshData(dwMachineNumber);

		}
		api.batchUpdate(dwMachineNumber);
		api.refreshData(dwMachineNumber);
		api.enableDevice(dwMachineNumber, true);
	}

	private void saveEmployeeToDevice(Connection conn, PrivilegeService ps, _zkemProxy api, Integer dwMachineNumber,
			Employee employee) {
		String dwEnrollNumber = employee.getKey();
		String name = employee.getName();
		String password = "";
		int privilege = getPrivilegeIdToKey(conn, ps, employee.getPrivilege());
		boolean enabled = employee.getEnabled();
		String fingerPrint = employee.getFingerPrint();
		fingerPrint = (fingerPrint == null ? "" : fingerPrint);
		Integer flag = employee.getFlag();
		flag = (flag == null ? 0 : flag);
		Integer dwFingerIndex = employee.getFingerIndex();
		dwFingerIndex = (dwFingerIndex == null ? 0 : dwFingerIndex);

		Holder<String> cardNumber = new Holder<String>();
		cardNumber.value = employee.getCardNumber();

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
		Employee employeeDB = new Employee();
		Employee employeeDevice = null;
		Integer dwMachineNumber = 1;
		PrivilegeService ps = new PrivilegeServiceImpl();

		/**
		 * <code>
		Holder<String> dwEnrollNumber = new Holder<String>("");
		Holder<String> name = new Holder<String>("");
		Holder<String> password = new Holder<String>("");
		Holder<Integer> privilege = new Holder<Integer>(0);
		Holder<Boolean> enabled = new Holder<Boolean>(false);
		Holder<Integer> fingerIndex = new Holder<Integer>(0);
		</code>
		 */

		api.enableDevice(dwMachineNumber, false);
		// api.readAllUserID(dwMachineNumber);
		// PrivilegeService ps = new PrivilegeServiceImpl();
		for (String key : keys) {
			employeeDevice = readEmployeeFromDevice(conn, ps, api, key);
			if (!bu.search(conn, employeeDB, "cKey=?", key)) {
				employeeDevice.setGroup(getDefaultGroup(conn));
				bu.save(conn, employeeDevice);
			} else {
				Employee employeeMerged = mergeEmployee(employeeDevice, employeeDB, ps);

				updateEmployeeToDevice(conn, ps, api, employeeMerged);
				api.refreshData(dwMachineNumber);
				bu.save(conn, employeeMerged);
			}
		}
		api.enableDevice(dwMachineNumber, true);
	}

	@Override
	public Employee mergeEmployee(Employee employeeDevice, Employee employeeDB, PrivilegeService ps) {
		//
		// }
		// public Employee mergeEmployee(Employee employeeDevice, Employee
		// employeeDB) {
		Employee employee = new Employee();

		employee.setEnabled(employeeDB.getEnabled() == null ? employeeDevice.getEnabled() : employeeDB.getEnabled());
		employee.setFingerIndex(employeeDB.getFingerIndex() == null ? employeeDevice.getFingerIndex() : employeeDB
				.getFingerIndex());
		employee.setFingerPrint(employeeDB.getFingerPrint() == null ? employeeDevice.getFingerPrint() : employeeDB
				.getFingerPrint());
		employee.setFlag(employeeDB.getFlag() == null ? employeeDevice.getFlag() : employeeDB.getFlag());
		employee.setId(employeeDB.getId());
		employee.setKey(employeeDB.getKey() == null ? employeeDevice.getKey() : employeeDB.getKey());
		employee.setName(employeeDB.getName() == null ? employeeDevice.getName() : employeeDB.getName());
		employee.setPost(employeeDB.getPost() == null ? employeeDevice.getPost() : employeeDB.getPost());

		employee.setPrivilege(employeeDB.getPrivilege() == null ? employeeDevice.getPrivilege() : employeeDB.getPrivilege());

		employee.setRut(employeeDB.getRut() == null ? employeeDevice.getRut() : employeeDB.getRut());

		employee.setCardNumber(employeeDB.getCardNumber() == null ? employeeDevice.getCardNumber() : employeeDB.getCardNumber());

		employee.setArea(employeeDB.getArea());
		employee.setGroup(employeeDB.getGroup());

		return employee;
	}

	@Override
	public void updateEmployeeToDevice(Connection conn, PrivilegeService ps, _zkemProxy api, Employee employee) {
		Integer dwMachineNumber = 1;
		String password = "";
		Holder<String> cardNumber = new Holder<String>();

		cardNumber.value = employee.getCardNumber();
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

		params.add(attendance.getEmployeeKey());
		params.add(attendance.getMachine());
		params.add(attendance.getMarkType());
		params.add(attendance.getYear());
		params.add(attendance.getMonth());
		params.add(attendance.getDay());
		params.add(attendance.getHour());
		params.add(attendance.getMinute());
		params.add(attendance.getSecond());

		ResultSet rs = mysql.callSingleSP(conn, "pExistsAttendanceLog", params);

		Boolean out = false;
		try {
			if (rs.next()) {
				out = rs.getInt(1) == 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			out = false;
		}

		/**
		 * <code>
		String sql = "SELECT count(cId) FROM tAttendanceLog WHERE cEmployeeKey=? AND cMachine=? AND cDate=? AND cMarkType=?";
		List<Object> params = new ArrayList<Object>();
		params.add(attendance.getEmployeeKey());
		params.add(attendance.getMachine());
//		params.add(attendance.getDate());
		params.add(attendance.getMarkType());

		BSmySQL mysql = new BSmySQL();
		String count = mysql.queryField(conn, sql, params);

		return Integer.parseInt(count) > 0;
	</code>
		 */
		return out;
	}

	@Override
	public void saveAttendanceLog(Connection conn, AttendanceLog attendance) {
		BSmySQL mysql = new BSmySQL();
		List<Object> params = new ArrayList<Object>();

		params.add(attendance.getEmployeeKey());
		params.add(attendance.getMachine());
		params.add(attendance.getMarkType());
		params.add(attendance.getYear());
		params.add(attendance.getMonth());
		params.add(attendance.getDay());
		params.add(attendance.getHour());
		params.add(attendance.getMinute());
		params.add(attendance.getSecond());

		ResultSet rs = null;
		try {
			rs = mysql.callSingleSP(conn, "pSaveAttendanceLog", params);
			if (rs.next())
				attendance.setId(rs.getLong(1));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mysql.closeSQL(rs);
		}

	}

	@Override
	public Long getDefaultGroup(Connection conn) {
		BSBeanUtils bu = new BSBeanUtils();
		String sql = "SELECT MIN(cId) AS cMin FROM tGroup;";
		String min = bu.queryField(conn, sql, null);
		return Long.parseLong(min);
	}
}
