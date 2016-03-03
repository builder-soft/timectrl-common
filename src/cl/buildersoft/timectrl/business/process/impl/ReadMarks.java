package cl.buildersoft.timectrl.business.process.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.framework.database.BSmySQL;
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

/**
 * <code>
   Para borrar las marcas de prueba, ejecutar: 
   delete from tattendancelog where cemployeekey like '77%';
  
 </code>
 */
public class ReadMarks extends AbstractProcess implements ExecuteProcess {
	private static final Logger LOG = Logger.getLogger(ReadMarks.class.getName());
	private String[] validArguments = { "DOMAIN", "DELETE_MARKS_OF_MACHINE" };
//	private String dsName = null;

	@Override
	protected String[] getArguments() {
		return this.validArguments;
	}

	public static void main(String[] args) {
		ReadMarks readMarks = new ReadMarks();
		readMarks.doExecute(args);
	}

	@Override
	public List<String> doExecute(String[] args) {
		// LOG.entering(ReadMarks.class.getName(), "doExecute", args);
		List<String> out = new ArrayList<String>();
		// this.init();
		validateArguments(args);
		
		this.setDSName(args[0]);
		Connection conn = getConnection(getDomainByBatabase(args[0]));

		init();
		if(!licenseValidation(conn)){
			throw new BSConfigurationException("License validation fail");
		}
		

		LOG.logp(Level.INFO, this.getClass().getName(), "doExecute", "Starting Method", args);
		Boolean deleteMarksAtEnd = deleteMarksAtEnd(args);
		BSBeanUtils bu = new BSBeanUtils();
		@SuppressWarnings("unchecked")
		List<Machine> machines = (List<Machine>) bu.listAll(conn, new Machine());
		MachineService2 service = null;// new MachineServiceImpl2();

		_zkemProxy api = null;
		for (Machine machine : machines) {
			try {
				service = new MachineServiceImpl2();
				api = service.connect(conn, machine);
			} catch (BSConfigurationException e) {
				LOG.log(Level.SEVERE, "Can not connect to machine", e);
			}

			if (api != null) {
				LOG.log(Level.INFO, "Procesing {0}", machine.toString());

				List<AttendanceLog> attendanceLog = null;
				try {
					attendanceLog = service.listAttendence(conn, api, machine);
					LOG.log(Level.INFO, "There are {0} marks.", attendanceLog.size());

					Long start = System.currentTimeMillis();
					saveToDataBase(conn, service, attendanceLog);
					Long end = System.currentTimeMillis();
					LOG.log(Level.INFO, "Insert record was in {0} miliseconds", (end - start));
				} catch (Exception e) {
					LOG.log(Level.SEVERE, e.getMessage(), e);
					deleteMarksAtEnd = false;
				} finally {
					attendanceLog.clear();
				}
				if (deleteMarksAtEnd) {
					deleteMarks(api);
				}
				service.disconnect(api);
				service = null;
			}
			api = null;
			deleteMarksAtEnd = deleteMarksAtEnd(args);

		}

		BSmySQL mysql = new BSmySQL();
		mysql.closeConnection(conn);

		LOG.logp(Level.INFO, this.getClass().getName(), "doExecute", "Ending Method");

		// LOG.exiting(ReadMarks.class.getName(), "doExecute");
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
		return out;
	}

	private void deleteMarks(_zkemProxy api) {
		Integer dwMachineNumber = 1;
		api.enableDevice(dwMachineNumber, false);
		api.clearGLog(dwMachineNumber);
		api.enableDevice(dwMachineNumber, true);

	}

	private void saveToDataBase(Connection conn, MachineService2 service,   List<AttendanceLog> attendanceList) {
		/**
		 * <code>
		service.saveAttendanceLog(conn, attendanceList);
</code>
		 */
		
		for (AttendanceLog attendance : attendanceList) {
			if (!service.existsAttendanceLog(conn, attendance)) {
				try {
					service.saveAttendanceLog(conn, attendance);
				} catch (BSDataBaseException e) {
					LOG.log(Level.SEVERE, "Fail to save  " + attendance.toString(), e);
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

/** 
  <code>
	@Override
	public void setDSName(String dsName) {
		this.dsName = dsName;

	}
	</code>
	 */

}