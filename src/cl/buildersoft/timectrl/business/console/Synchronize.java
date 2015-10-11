package cl.buildersoft.timectrl.business.console;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.timectrl.api._zkemProxy;
import cl.buildersoft.timectrl.business.beans.Employee;
import cl.buildersoft.timectrl.business.beans.Machine;
import cl.buildersoft.timectrl.business.services.MachineService2;
import cl.buildersoft.timectrl.business.services.PrivilegeService;
import cl.buildersoft.timectrl.business.services.impl.MachineServiceImpl2;
import cl.buildersoft.timectrl.business.services.impl.PrivilegeServiceImpl;

public class Synchronize extends AbstractConsoleService {

	public static void main(String[] args) {
		Synchronize synchronize = new Synchronize();
		synchronize.init();

		synchronize.doAction();

	}

	private void doAction() {
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
		Connection conn = getConnection();
		Long group = null;
		List<Machine> machineList = listMachines(conn);
		List<Employee> employeeDBList = null;
		List<Employee> employeeMchList = null;
		Map<String, Employee> employeeDBMap = null;
		Map<String, Employee> employeeMchMap = null;
		Employee employeeMch = null;
		Employee employeeDB = null;
		String[] keys = null;
		MachineService2 machineService = new MachineServiceImpl2();
		_zkemProxy connMch = null;
		PrivilegeService ps = new PrivilegeServiceImpl();

		for (Machine machine : machineList) {
			group = machine.getGroup();
			employeeDBList = listEmployeeByGroup(conn, group);
			employeeDBMap = listToEmployeeMap(employeeDBList);

			connMch = connectMachine(conn, machine, machineService);

			// Cargar listado de empleados en un Map<key, employee>MACHINE
			employeeMchList = machineService.listEmployees(conn, connMch);
			employeeMchMap = listToEmployeeMap(employeeMchList);

			// Cargar solo los key desde map->DB en un arreglo
			keys = listKeysFromList(employeeDBList);
			for (String key : keys) {
				employeeMch = employeeMchMap.get(key);
				employeeDB = employeeDBMap.get(key);

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
				if (employeeMch != null) {
					Employee merged = machineService.mergeEmployee(employeeMch, employeeDBMap.get(key), ps);
					saveEmployeeToDB(conn, merged);
					machineService.updateEmployeeToDevice(conn, ps ,connMch, merged);
					employeeMchMap.remove(key);
				} else {
					machineService.addEmployee(conn, ps, connMch, employeeDB);
				}
				// Retirar empleado de Map->DB
				employeeDBMap.remove(key);
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
			for (Map.Entry<String, Employee> employeeEntry : employeeMchMap.entrySet()) {
				employeeMch = employeeEntry.getValue();
				employeeMch.setGroup(group);
				if (existsInDatabase(conn, employeeMch)) {
					machineService.deleteEmployee(connMch, employeeMch.getKey());
				} else {
					saveToDatabase(conn, employeeMch);
				}
			}
			machineService.disconnect(connMch);
		}
	}

	private void saveEmployeeToDB(Connection conn, Employee merged) {
		BSBeanUtils bu = new BSBeanUtils();
		bu.update(conn, merged);
	}

	private void saveToDatabase(Connection conn, Employee employee) {
		BSBeanUtils bu = new BSBeanUtils();
		bu.save(conn, employee);
	}

	private boolean existsInDatabase(Connection conn, Employee employeeMch) {
		BSBeanUtils bu = new BSBeanUtils();
		Employee employee = new Employee();
		return bu.search(conn, employee, "cKey=?", employeeMch.getKey());
	}

	private String[] listKeysFromList(List<Employee> employeeDBList) {
		String[] out = new String[employeeDBList.size()];
		Integer i = 0;
		for (Employee employee : employeeDBList) {
			out[i++] = employee.getKey();
		}
		return out;
	}

	private _zkemProxy connectMachine(Connection conn, Machine machine, MachineService2 machineService) {
		return machineService.connect(conn, machine);
	}

	private Map<String, Employee> listToEmployeeMap(List<Employee> employeeList) {
		Map<String, Employee> out = new HashMap<String, Employee>();
		for (Employee employee : employeeList) {
			out.put(employee.getKey(), employee);
		}
		return out;
	}

	@SuppressWarnings("unchecked")
	private List<Employee> listEmployeeByGroup(Connection conn, Long group) {
		BSBeanUtils bu = new BSBeanUtils();
		return (List<Employee>) bu.list(conn, new Employee(), "cGroup=?", group);
	}

	@SuppressWarnings("unchecked")
	private List<Machine> listMachines(Connection conn) {
		BSBeanUtils bu = new BSBeanUtils();
		return (List<Machine>) bu.listAll(conn, new Machine());
	}
}
