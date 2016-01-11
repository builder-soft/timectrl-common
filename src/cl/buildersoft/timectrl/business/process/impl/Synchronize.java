package cl.buildersoft.timectrl.business.process.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.framework.util.BSConnectionFactory;
import cl.buildersoft.timectrl.api._zkemProxy;
import cl.buildersoft.timectrl.business.beans.Employee;
import cl.buildersoft.timectrl.business.beans.Fingerprint;
import cl.buildersoft.timectrl.business.beans.Machine;
import cl.buildersoft.timectrl.business.process.AbstractProcess;
import cl.buildersoft.timectrl.business.process.ExecuteProcess;
import cl.buildersoft.timectrl.business.services.MachineService2;
import cl.buildersoft.timectrl.business.services.PrivilegeService;
import cl.buildersoft.timectrl.business.services.impl.EmployeeAndFingerprint;
import cl.buildersoft.timectrl.business.services.impl.MachineServiceImpl2;
import cl.buildersoft.timectrl.business.services.impl.PrivilegeServiceImpl;

public class Synchronize extends AbstractProcess implements ExecuteProcess {
	private String[] validArguments = { "DOMAIN" };

	public static void main(String[] args) {
		Synchronize synchronize = new Synchronize();
		// synchronize.init();

		synchronize.doExecute(args);

	}

	@Override
	protected String[] getArguments() {
		return this.validArguments;
	}

	@Override
	public List<String> doExecute(String[] args) {
		/**
		 * <code>
Buscar listdo de machines de la DB
Recorrer listado maquinas
   por cada maquina:
   leer numero de grupo
   
   buscar listado de empleados para el grupo leido
   Cargar listado de empleados en un Map<key, employee>DB
   
   conectar a la maquina
   Cargar listado de empleados en un Map<key, employee>MACHINE
   
   Cargar solo los key desde map->DB en un arreglo
   
   Recorrer listado de key recien cargados (From DB)
   	  buscar empleado en map->MACHINE
   	  
   	  Si Esta en map->MACHINE 
   	     Sincronizar empleado map->Machine + map->DB
   	     Retirar empleado de Map->MACHINE
   	  en caso contrario
         Agregar empleado a Machine
   	  Sigue
   	  
   	  Retirar empleado de Map->DB
   	  
   continuar
   
   Recorrer lista map->MACHINE
   	  Si empleado Esta en Base de datos
         borrar empleado del reloj
      en caso contrario
         grabar empleado en base de datos.
      continuar
   continuar
      
   desconectar a la maquina
siguiente maquina
 </code>
		 */
		BSConnectionFactory cf = new BSConnectionFactory();

		Connection conn = cf.getConnection(args[0]);
		Long group = null;
		List<Machine> machineList = listMachines(conn);
		List<EmployeeAndFingerprint> eafDBList = null;
		List<EmployeeAndFingerprint> eafMchList = null;
		Map<String, EmployeeAndFingerprint> eafDBMap = null;
		Map<String, EmployeeAndFingerprint> eafMchMap = null;
		EmployeeAndFingerprint eafMch = null;
		EmployeeAndFingerprint eafDB = null;
		String[] keys = null;
		MachineService2 machineService = new MachineServiceImpl2();
		_zkemProxy connMch = null;
		PrivilegeService ps = new PrivilegeServiceImpl();

		for (Machine machine : machineList) {
			group = machine.getGroup();
			eafDBList = listEmployeeByGroup(conn, group);
			eafDBMap = listToEmployeeMap(eafDBList);

			connMch = connectMachine(conn, machine, machineService);

			// Cargar listado de empleados en un Map<key, employee>MACHINE
			eafMchList = machineService.listEmployees(conn, connMch);
			eafMchMap = listToEmployeeMap(eafMchList);

			// Cargar solo los key desde map->DB en un arreglo
			keys = listKeysFromList(eafDBList);
			for (String key : keys) {
				eafMch = eafMchMap.get(key);
				eafDB = eafDBMap.get(key);

				/**
				 * <code>
  Si Esta en map->MACHINE 
     Sincronizar empleado map->Machine + map->DB
     Retirar empleado de Map->MACHINE
  en caso contrario
     Agregar empleado a Machine
  Sigue
</code>
				 */
				if (eafMch != null) {
					EmployeeAndFingerprint merged = machineService.mergeEmployee(eafMch, eafDBMap.get(key), ps);
					saveEmployeeToDB(conn, merged);
					machineService.updateEmployeeToDevice(conn, ps, connMch, merged);
					eafMchMap.remove(key);
				} else {
					machineService.addEmployee(conn, ps, connMch, eafDB);
				}
				// Retirar empleado de Map->DB
				eafDBMap.remove(key);
			}
			/**
			 * <code>			
  Recorrer lista map->MACHINE
   	  Si empleado Est� en Base de datos
         borrar empleado del reloj
      en caso contrario
         grabar empleado en base de datos.
      continuar
   continuar
<code>
			 */
			for (Map.Entry<String, EmployeeAndFingerprint> eafEntry : eafMchMap.entrySet()) {
				eafMch = eafEntry.getValue();
				eafMch.getEmployee().setGroup(group);
				if (existsInDatabase(conn, eafMch.getEmployee())) {
					machineService.deleteEmployee(connMch, eafMch.getEmployee().getKey());
				} else {
					saveToDatabase(conn, eafMch);
				}
			}
			machineService.disconnect(connMch);
		}
		cf.closeConnection(conn);
		return null;
	}

	private void saveEmployeeToDB(Connection conn, EmployeeAndFingerprint merged) {
		BSBeanUtils bu = new BSBeanUtils();

		bu.update(conn, merged.getEmployee());
		bu.update(conn, merged.getFingerprint());
	}

	private void saveToDatabase(Connection conn, EmployeeAndFingerprint eaf) {
		BSBeanUtils bu = new BSBeanUtils();

		bu.save(conn, eaf.getEmployee());
		bu.save(conn, eaf.getFingerprint());
	}

	private boolean existsInDatabase(Connection conn, Employee employeeMch) {
		BSBeanUtils bu = new BSBeanUtils();
		Employee employee = new Employee();
		return bu.search(conn, employee, "cKey=?", employeeMch.getKey());
	}

	private String[] listKeysFromList(List<EmployeeAndFingerprint> eafDBList) {
		String[] out = new String[eafDBList.size()];
		Integer i = 0;
		for (EmployeeAndFingerprint eaf : eafDBList) {
			out[i++] = eaf.getEmployee().getKey();
		}
		return out;
	}

	private _zkemProxy connectMachine(Connection conn, Machine machine, MachineService2 machineService) {
		return machineService.connect(conn, machine);
	}

	private Map<String, EmployeeAndFingerprint> listToEmployeeMap(List<EmployeeAndFingerprint> eafList) {
		Map<String, EmployeeAndFingerprint> out = new HashMap<String, EmployeeAndFingerprint>();
		for (EmployeeAndFingerprint eaf : eafList) {
			out.put(eaf.getEmployee().getKey(), eaf);
		}
		return out;
	}

	@SuppressWarnings("unchecked")
	private List<EmployeeAndFingerprint> listEmployeeByGroup(Connection conn, Long group) {
		BSBeanUtils bu = new BSBeanUtils();

		List<EmployeeAndFingerprint> out = new ArrayList<EmployeeAndFingerprint>();
		EmployeeAndFingerprint eaf = null;
		Fingerprint fingerprint = null;

		List<Employee> employeeList = (List<Employee>) bu.list(conn, new Employee(), "cGroup=?", group);

		for (Employee employee : employeeList) {
			fingerprint = new Fingerprint();
			bu.search(conn, fingerprint, "cEmployee=?", employee.getId());

			eaf = new EmployeeAndFingerprint();
			eaf.setEmployee(employee);
			eaf.setFingerprint(fingerprint);

			out.add(eaf);
		}

		return out;
	}

	@SuppressWarnings("unchecked")
	private List<Machine> listMachines(Connection conn) {
		BSBeanUtils bu = new BSBeanUtils();
		return (List<Machine>) bu.listAll(conn, new Machine());
	}

}
