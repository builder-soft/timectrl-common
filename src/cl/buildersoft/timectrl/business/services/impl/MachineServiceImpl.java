package cl.buildersoft.timectrl.business.services.impl;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.framework.util.BSConfig;
import cl.buildersoft.timectrl.api.ClassFactory;
import cl.buildersoft.timectrl.api.IZKEM;
import cl.buildersoft.timectrl.api.IZKEMException;
import cl.buildersoft.timectrl.business.beans.AttendanceLog;
import cl.buildersoft.timectrl.business.beans.Employee;
import cl.buildersoft.timectrl.business.beans.Machine;
import cl.buildersoft.timectrl.business.beans.Privilege;
import cl.buildersoft.timectrl.business.services.MachineService;
import cl.buildersoft.timectrl.business.services.PrivilegeService;

import com4j.Holder;

public class MachineServiceImpl implements MachineService {
	private Boolean windows8compatible = null;

	@Override
	public IZKEM connect(Connection conn, Machine machine) {
		BSBeanUtils bu = new BSBeanUtils();
		IZKEM api = ClassFactory.createCZKEM(conn);

		Boolean connected = api.connect_Net(machine.getIp(), machine.getPort());
		if (connected) {
			machine.setLastAccess(new Timestamp(System.currentTimeMillis()));
			bu.update(conn, machine);
		} else {
			api = null;
		}

		return api;
	}

	@Override
	public List<AttendanceLog> listAttendence(Connection conn, IZKEM api, Machine machine) throws IZKEMException {
		List<AttendanceLog> out = new ArrayList<AttendanceLog>();
		AttendanceLog attendance = null;

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
//		Timestamp date = null;

		api.enableDevice(dwMachineNumber, false);
		if (api.readGeneralLogData(dwMachineNumber)) {
			while (readRecordFromMachine(conn, api, dwMachineNumber, dwEnrollNumber, dwVerifyMode, dwInOutMode, dwYear, dwMonth,
					dwDay, dwHour, dwMinute, dwSecond, dwWorkCode)) {
				attendance = new AttendanceLog();

//				Calendar calendar = Calendar.getInstance();
				// System.out.println(dwYear.value);
//				calendar.set(dwYear.value, dwMonth.value, dwDay.value, dwHour.value, dwMinute.value, dwSecond.value);
//				calendar.set(Calendar.MILLISECOND, 0);
//				date = new Timestamp(calendar.getTimeInMillis());

//				attendance.setDate(date);
				
				attendance.setYear(dwYear.value);
				attendance.setMonth(dwMonth.value);
				attendance.setDay(dwDay.value);
				attendance.setHour(dwHour.value);
				attendance.setMinute(dwMinute.value);
				attendance.setSecond(dwMinute.value);
				
				attendance.setEmployeeKey(dwEnrollNumber.value);
				attendance.setMachine(machine.getId());
				attendance.setMarkType((long) dwInOutMode.value);

				out.add(attendance);
			}
		} else {
			Holder<Integer> error = new Holder<Integer>(0);
			api.getLastError(error);
			throw new IZKEMException(error.value);
		}

		api.enableDevice(dwMachineNumber, true);
		return out;
	}

	private boolean readRecordFromMachine(Connection conn, IZKEM api, Integer dwMachineNumber, Holder<String> dwEnrollNumber,
			Holder<Integer> dwVerifyMode, Holder<Integer> dwInOutMode, Holder<Integer> dwYear, Holder<Integer> dwMonth,
			Holder<Integer> dwDay, Holder<Integer> dwHour, Holder<Integer> dwMinute, Holder<Integer> dwSecond,
			Holder<Integer> dwWorkCode) {

		Boolean out = null;
		
		if (this.windows8compatible == null) {
			BSConfig config = new BSConfig();
			this.windows8compatible = config.getBoolean(conn, "W8COMPATIBLE");
			this.windows8compatible = false;
		}

		if (!windows8compatible) {
			out = api.ssR_GetGeneralLogData(dwMachineNumber, dwEnrollNumber, dwVerifyMode, dwInOutMode, dwYear, dwMonth, dwDay,
					dwHour, dwMinute, dwSecond, dwWorkCode);
		} else {
			Holder<Integer> dwTMachineNumber = new Holder<Integer>();
			Holder<Integer> dwEMachineNumber = new Holder<Integer>();
			Holder<Integer> dwEnrollNumberI = new Holder<Integer>();

			out = api.getGeneralLogData(dwMachineNumber.intValue(), dwTMachineNumber, dwEnrollNumberI, dwEMachineNumber,
					dwVerifyMode, dwInOutMode, dwYear, dwMonth, dwDay, dwHour, dwMinute);

			dwEnrollNumber.value = "" + dwEnrollNumberI.value;
		}
		return out;
	}

	@Override
	public List<Employee> listEmployees(Connection conn, IZKEM api) {
		List<Employee> out = new ArrayList<Employee>();
		Employee employee = new Employee();
		Integer dwMachineNumber = 1;
		api.enableDevice(dwMachineNumber, false);

		api.readAllUserID(dwMachineNumber);

		while (employee != null) {
			employee = readEmployeeFromDevice(conn, api, null);
			if (employee != null) {
				out.add(employee);
			}
		}

		/**
		 * <code>
		Holder<String> dwEnrollNumber = new Holder<String>("");
		Holder<String> name = new Holder<String>("");
		Holder<String> password = new Holder<String>("");

		Holder<Integer> privilege = new Holder<Integer>(0);
		Holder<Boolean> enabled = new Holder<Boolean>(false);

		while (readUserinfo(api, dwMachineNumber, dwEnrollNumber, name, password, privilege, enabled)) {
			employee = new Employee();
			employee.setKey(dwEnrollNumber.value);
			employee.setName(name.value);
			employee.setPrivilege(((long) (privilege.value + 1)));
			employee.setEnabled(enabled.value);

			out.add(employee);
		}
		</code>
		 */

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

	private Employee readEmployeeFromDevice(Connection conn, IZKEM api, String enrollNumber) {
		Employee out = null;

		int dwMachineNumber = 1;
		Holder<String> dwEnrollNumber = new Holder<String>(enrollNumber == null ? "" : enrollNumber);
		Holder<String> name = new Holder<String>();
		Holder<String> password = new Holder<String>();
		Holder<Integer> privilege = new Holder<Integer>(0);
		Holder<Boolean> enabled = new Holder<Boolean>();

		Boolean found = false;

		if (enrollNumber != null) {
			found = api.ssR_GetUserInfo(dwMachineNumber, enrollNumber, name, password, privilege, enabled);
		} else {
			found = api.ssR_GetAllUserInfo(dwMachineNumber, dwEnrollNumber, name, password, privilege, enabled);
		}

		if (found) {
			out = new Employee();
			out.setEnabled(enabled.value);
			out.setKey(dwEnrollNumber.value);
			out.setName(name.value);
			out.setPrivilege(getPrivilege(conn, privilege));

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

	private Long getPrivilege(Connection conn, Holder<Integer> priv) {
		PrivilegeService ps = new PrivilegeServiceImpl();
		Privilege privilege = ps.searchByKey(conn, priv.value);
		Long out = null;
		if (privilege != null) {
			out = privilege.getId();
		}
		return out;
	}

	@Override
	public void addEmployees(IZKEM api, List<Employee> employees) {
		Integer dwMachineNumber = 1;
		Integer updateFlag = 1;
		api.enableDevice(dwMachineNumber, false);

		if (api.beginBatchUpdate(dwMachineNumber, updateFlag)) {
			for (Employee employee : employees) {
				saveEmployeeToDevice(api, dwMachineNumber, employee);
			}
		}
		api.batchUpdate(dwMachineNumber);
		api.refreshData(dwMachineNumber);
		api.enableDevice(dwMachineNumber, true);
	}

	private void saveEmployeeToDevice(IZKEM api, Integer dwMachineNumber, Employee employee) {
		String dwEnrollNumber = employee.getKey();
		String name = employee.getName();
		String password = "";
		int privilege = employee.getPrivilege().intValue();
		boolean enabled = employee.getEnabled();
		String fingerPrint = employee.getFingerPrint();
		fingerPrint = (fingerPrint == null ? "" : fingerPrint);
		Integer flag = employee.getFlag();
		flag = (flag == null ? 0 : flag);
		Integer dwFingerIndex = employee.getFingerIndex();
		dwFingerIndex = (dwFingerIndex == null ? 0 : dwFingerIndex);
		if (api.ssR_SetUserInfo(dwMachineNumber, dwEnrollNumber, name, password, privilege, enabled)) {
			api.setUserTmpExStr(dwMachineNumber, dwEnrollNumber, dwFingerIndex, flag, fingerPrint);
		}
	}

	@Override
	public void deleteEmployees(IZKEM api, String[] keys) {
		Integer dwMachineNumber = 1;

		api.enableDevice(dwMachineNumber, false);
		for (String key : keys) {
			api.ssR_DeleteEnrollData(dwMachineNumber, key, 0);
		}
		api.enableDevice(dwMachineNumber, true);

	}

	@Override
	public void disconnect(IZKEM api) {
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
	public void syncEmployees(Connection conn, IZKEM api, String[] keys) {
		BSBeanUtils bu = new BSBeanUtils();
		Employee employeeDB = new Employee();
		Employee employeeDevice = null;
		Integer dwMachineNumber = 1;

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
		for (String key : keys) {
			employeeDevice = readEmployeeFromDevice(conn, api, key);
			if (!bu.search(conn, employeeDB, "cKey=?", key)) {
				bu.save(conn, employeeDevice);
			} else {
				Employee employeeMerged = mergeEmployee(employeeDevice, employeeDB);

				updateEmployeeToDevice(api, employeeMerged);
				bu.save(conn, employeeMerged);
			}
		}
		api.enableDevice(dwMachineNumber, true);
	}

	private Employee mergeEmployee(Employee employeeDevice, Employee employeeDB) {
		Employee employee = new Employee();

		employee.setEnabled(employeeDB.getEnabled() == null ? employeeDevice.getEnabled() : employeeDB.getEnabled());
		employee.setFingerIndex(employeeDB.getFingerIndex() == null ? employeeDevice.getFingerIndex() : employeeDB
				.getFingerIndex());
		employee.setFingerPrint(employeeDB.getFingerPrint() == null ? employeeDevice.getFingerPrint() : employeeDB
				.getFingerPrint());
		employee.setFlag(employeeDB.getFlag() == null ? employeeDevice.getFlag() : employeeDB.getFlag());
		employee.setId(employeeDB.getId() == null ? employeeDevice.getId() : employeeDB.getId());
		employee.setKey(employeeDB.getKey() == null ? employeeDevice.getKey() : employeeDB.getKey());
		employee.setName(employeeDB.getName() == null ? employeeDevice.getName() : employeeDB.getName());
		employee.setPost(employeeDB.getPost() == null ? employeeDevice.getPost() : employeeDB.getPost());
		employee.setPrivilege(employeeDB.getPrivilege() == null ? employeeDevice.getPrivilege() : employeeDB.getPrivilege());
		employee.setRut(employeeDB.getRut() == null ? employeeDevice.getRut() : employeeDB.getRut());

		return employee;
	}

	private void updateEmployeeToDevice(IZKEM api, Employee employee) {
		Integer dwMachineNumber = 1;
		String password = "";
		api.ssR_SetUserInfo(dwMachineNumber, employee.getKey(), employee.getName(), password, employee.getPrivilege().intValue(),
				employee.getEnabled());
	}

	@Override
	public String readSerial(IZKEM api) {
		Integer dwMachineNumber = 1;
		Holder<String> dwSerialNumber = new Holder<String>();
		api.getSerialNumber(dwMachineNumber, dwSerialNumber);
		return dwSerialNumber.value;
	}

	/**
	 * <code>
	private void updateName(IZKEM api, Employee employee) {
		Integer dwMachineNumber = 1;
		String password = "";
		api.ssR_SetUserInfo(dwMachineNumber, employee.getKey(), concatName(employee), password, employee.getPrivilege()
				.intValue(), employee.getEnabled());
	}

	private boolean equalsNames(Employee employee, String name) {
		String fullName = concatName(employee);
		return name.equals(fullName);
	}
</code>
	 */
	/**
	 * <code>
	 * 
	 * @Deprecated private Integer readFingerprint(IZKEM api, int
	 *             dwMachineNumber, String dwEnrollNumber, Holder<Integer> flag,
	 *             Holder<String> fingerPrint, Holder<Integer> fingerIndex) {
	 *             Holder<Integer> tmpLength = new Holder<Integer>(); Integer
	 *             dwFingerIndex = 0;
	 * 
	 *             for (dwFingerIndex = 0; dwFingerIndex < 10; dwFingerIndex++)
	 *             { if (api.getUserTmpExStr(dwMachineNumber, dwEnrollNumber,
	 *             dwFingerIndex, flag, fingerPrint, tmpLength)) { break; } }
	 *             return dwFingerIndex; }
	 * @Deprecated private Employee saveEmployeeToDB(Connection conn, IZKEM api,
	 *             BSBeanUtils bu, int dwMachineNumber, Holder<String> name,
	 *             Holder<Integer> privilege, Holder<Boolean> enabled, String
	 *             key, Integer fingerIndex) { Employee employee; employee = new
	 *             Employee(); employee.setEnabled(enabled.value);
	 *             employee.setKey(key);
	 * 
	 *             if (name.value != null && name.value.length() > 0) {
	 *             employee.setName(name.value);
	 *             employee.setLastName1(name.value);
	 *             employee.setLastName2(name.value); } else {
	 *             employee.setName(""); employee.setLastName1("");
	 *             employee.setLastName2(""); } employee.setPrivilege((long)
	 *             privilege.value + 1);
	 * 
	 *             Holder<Integer> flag = new Holder<Integer>(); Holder<String>
	 *             fingerPrint = new Holder<String>();
	 * 
	 *             saveFingerprint(api, dwMachineNumber, key, employee, flag,
	 *             fingerPrint, fingerIndex);
	 * 
	 *             bu.save(conn, employee); return employee; }
	 * @Deprecated private void saveFingerprint(IZKEM api, int dwMachineNumber,
	 *             String key, Employee employee, Holder<Integer> flag,
	 *             Holder<String> fingerPrint, Integer fingerIndex) { // Integer
	 *             fingerIndex = readFingerprint(api, dwMachineNumber, key, //
	 *             flag, fingerPrint); employee.setFlag(flag.value);
	 *             employee.setFingerIndex(fingerIndex);
	 *             employee.setFingerPrint(fingerPrint.value); } </code>
	 */
}
