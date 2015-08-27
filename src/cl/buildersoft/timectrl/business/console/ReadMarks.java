package cl.buildersoft.timectrl.business.console;

import java.sql.Connection;
import java.util.List;

import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.framework.exception.BSConfigurationException;
import cl.buildersoft.framework.exception.BSDataBaseException;
import cl.buildersoft.framework.exception.BSUserException;
import cl.buildersoft.timectrl.api._zkemProxy;
import cl.buildersoft.timectrl.business.beans.AttendanceLog;
import cl.buildersoft.timectrl.business.beans.Machine;
import cl.buildersoft.timectrl.business.services.MachineService2;
import cl.buildersoft.timectrl.business.services.impl.MachineServiceImpl2;

public class ReadMarks extends AbstractConsoleService {

	public static void main(String[] args) {
		ReadMarks readMarks = new ReadMarks();
		readMarks.init();

		readMarks.doAction(args);
	}

	public void doAction(String[] args) {
		Connection conn = this.getConnection();

		Boolean success = this.licenseValidation(conn);

		if (!success) {
			log("License invalid");
		} else {
			Boolean deleteMarksAtEnd = deleteMarksAtEnd(args);
			BSBeanUtils bu = new BSBeanUtils();
			@SuppressWarnings("unchecked")
			List<Machine> machines = (List<Machine>) bu.listAll(conn, new Machine());
			MachineService2 service = new MachineServiceImpl2();

			_zkemProxy api = null;
			for (Machine machine : machines) {
				try {
					api = service.connect(conn, machine);
				} catch (BSConfigurationException e) {
					log(e.getMessage());
				}

				if (api != null) {
					log("Procesing " + machine.toString());

					List<AttendanceLog> attendanceLog = null;
					try {
						attendanceLog = service.listAttendence(conn, api, machine);
						log("" + attendanceLog.size() + " marks found.");
						writeToLogFile(attendanceLog);
						saveToDataBase(conn, service, bu, attendanceLog);
					} catch (Exception e) {
						e.printStackTrace();
						deleteMarksAtEnd = false;
					} finally {
						attendanceLog.clear();
					}
					if (deleteMarksAtEnd) {
						deleteMarks(api);
					}
					service.disconnect(api);
				}
				api = null;
				deleteMarksAtEnd = deleteMarksAtEnd(args);
			}

		}
		log("Done!");
		/**
		 * <code>
		 * conectar a la base de datos.
		 * vaildar licencia
		 * if(licenciaIncorrecta){
		 * 		notificarLicenciaExpirada()
		 * } else {
		 * 		recorrer listado de relojes.
		 * 		for(relojes){
		 * 			leer marcas
		 * 			cargar marcas a la base de datos
		 * 			if(flagLimpiarReloj=true){
		 * 				reloj.limpiarMarcas
		 * 			}
		 * 		}
		 * }
		 * </code>
		 */
	}

	private void writeToLogFile(List<AttendanceLog> attendanceList) {
		for (AttendanceLog attendance : attendanceList) {
			log(attendance.toString());
		}
	}

	private void deleteMarks(_zkemProxy api) {
		Integer dwMachineNumber = 1;
		api.enableDevice(dwMachineNumber, false);
		api.clearGLog(dwMachineNumber);
		api.enableDevice(dwMachineNumber, true);

	}

	private void saveToDataBase(Connection conn, MachineService2 service, BSBeanUtils bu, List<AttendanceLog> attendanceList) {
		for (AttendanceLog attendance : attendanceList) {
			if (!service.existsAttendanceLog(conn, attendance)) {
				try {
					service.saveAttendanceLog(conn, attendance);
					// bu.save(conn, attendance);
				} catch (BSDataBaseException e) {
					log("Fail to save " + attendance.toString() + " Detail:" + e.toString());
				}
			}
		}
	}

	private Boolean deleteMarksAtEnd(String[] args) {
		if (args.length == 0) {
			throw new BSUserException("No se indico parametro para definir el borrado de marcas al final");
		}
		return Boolean.parseBoolean(args[0]);
	}
}