package cl.buildersoft.timectrl.business.process.impl;

import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.framework.exception.BSConfigurationException;
import cl.buildersoft.framework.exception.BSDataBaseException;
import cl.buildersoft.framework.exception.BSUserException;
import cl.buildersoft.timectrl.api._zkemProxy;
import cl.buildersoft.timectrl.business.beans.AttendanceLog;
import cl.buildersoft.timectrl.business.beans.Machine;
import cl.buildersoft.timectrl.business.process.AbstractProcess;
import cl.buildersoft.timectrl.business.process.ExecuteProcess;
import cl.buildersoft.timectrl.business.services.MachineService2;
import cl.buildersoft.timectrl.business.services.impl.MachineServiceImpl2;

public class ReadMarks extends AbstractProcess implements ExecuteProcess {
	private static final Logger LOG = Logger.getLogger(ReadMarks.class.getName());
	private String[] validArguments = { "DOMAIN", "DELETE_MARKS_OF_MACHINE" };

	@Override
	protected String[] getArguments() {
		return this.validArguments;
	}

	public static void main(String[] args) {
		ReadMarks readMarks = new ReadMarks();
		readMarks.doExecute(args);
	}

	@Override
	public void doExecute(String[] args) {
		LOG.entering(ReadMarks.class.getName(), "doExecute", args);
//		this.init();
		Connection conn = getConnection(getDomainByBatabase(args[0]));

		validateArguments(args);

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
				LOG.log(Level.SEVERE, "Can not connect to machine", e);
			}

			if (api != null) {
				LOG.log(Level.INFO, "Procesing {0}", machine.toString());

				List<AttendanceLog> attendanceLog = null;
				try {
					attendanceLog = service.listAttendence(conn, api, machine);
					LOG.log(Level.INFO, "{0} marks found.", attendanceLog.size());
					 
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
		LOG.exiting(ReadMarks.class.getName(), "doExecute");
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
					LOG.log(Level.SEVERE, "Fail to save  " + attendance.toString(), e);
					// log("Fail to save " + attendance.toString() + " Detail:"
					// + e.toString());
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