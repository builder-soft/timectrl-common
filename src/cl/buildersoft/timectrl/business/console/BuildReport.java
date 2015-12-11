package cl.buildersoft.timectrl.business.console;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import cl.buildersoft.framework.exception.BSConfigurationException;
import cl.buildersoft.framework.exception.BSSystemException;
import cl.buildersoft.framework.util.BSConfig;
import cl.buildersoft.framework.util.BSConsole;
import cl.buildersoft.framework.util.BSDataUtils;
import cl.buildersoft.framework.util.BSDateTimeUtil;
import cl.buildersoft.timectrl.business.beans.IdRut;

@Deprecated
public class BuildReport {
	private Connection conn = null;
	private String format = "pdf";
	private static final Logger LOG = Logger.getLogger(BuildReport.class.getName());

	public static void main(String[] args) {
		if (args.length != 9) {
			showHelp();
		} else {
			BuildReport buildReport = new BuildReport();
			buildReport.doBuild(args);
			LOG.log(Level.INFO, Thread.class.getName() + " is done!");

		}
	}

	private static void showHelp() {
		String example = "BuildReports.cmd timecontrol root admin D:\\workspace\\timectrl-web\\WebContent\\WEB-INF\\sql\\timecontrol\\report-weekly.jasper D:\\temp\\4 2013-01-01 2014-12-01 0 false";
		BSConsole.println("\nComando:");
		BSConsole
				.println("$> BuildReports <DataDaseName> <User> <Password> <ReportFile> <FolderOutput> <FechaInicio> <FechaTermino> <EmpleadoId> <UseUsername>\n");
		BSConsole.println("DataDaseName: Nombre de la base de datos 'timecontrol'");
		BSConsole.println("Usuario: Usuario de la base de datos");
		BSConsole.println("Password: Password de la base de datos");
		BSConsole.println("ReportFile: Path y nombre del archivo de reporte, archivo de extencion '.jasper'");
		BSConsole.println("FolderOutput: Carpeta donde quedaran los archivos generados");
		BSConsole.println("Fecha Inicio y Fecha Termino: Es el rango de fechas del reporte. Deben ser con formato yyyy-mm-dd");
		BSConsole.println("EmpleadoId: Key del usuario, mirar tabla tEmployee, si el valor es 0, asume todos los empleados");
		BSConsole
				.println("Usando username: es un valor logico (true o false) para indicar que se dejar�n los archivos utilizando el username de la base de datos");
		// D:\temp\4\remote-files

		BSConsole.println("");
		BSConsole.println("Ejemplo:");
		BSConsole.println(example);
		BSConsole.println("");
	}

	public void doBuild(String[] args) {
		String dataBaseName = args[0];
		String user = args[1];
		String password = args[2];
		String report = args[3];
		String outputFolder = args[4];
		String startDate = args[5];
		String endDate = args[6];
		String idEmploye = args[7];
		String useUsernameString = args[8];
		String fileName = null;

		showParams(dataBaseName, user, password, report, outputFolder, startDate, endDate, idEmploye, useUsernameString);

		Boolean useUsername = Boolean.parseBoolean(useUsernameString);
		String rut = null;

		try {
			conn = getConnection(dataBaseName, user, password);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BSConfigurationException(e);
		}

		List<IdRut> employeeList = getEmployeeList(conn, idEmploye);

		for (IdRut idRut : employeeList) {
			Map<String, Object> params = getParams(idRut.getId(), startDate, endDate);

			String folder = validateFolder(outputFolder, idRut, useUsername);

			rut = idRut.getRut() == null ? "NO-RUT" : idRut.getRut();

			fileName = folder
					+ (useUsername ? "Report-" + BSDateTimeUtil.calendar2String(Calendar.getInstance(), "yyyy-MM-dd") : rut + "."
							+ idRut.getId());

			File file = new File(report);
			JasperReport reporte;
			try {
				reporte = (JasperReport) JRLoader.loadObject(file);
			} catch (JRException e) {
				LOG.log(Level.SEVERE, "Error loading JRLoader", e);
				throw new BSConfigurationException(e);
			}
			JasperPrint jasperPrint;
			try {
				jasperPrint = JasperFillManager.fillReport(reporte, params, conn);
			} catch (JRException e) {
				e.printStackTrace();
				throw new BSConfigurationException(e);
			}

			@SuppressWarnings("rawtypes")
			JRExporter exporter = null;
			String extention = ".pdf";
			if (this.format.equalsIgnoreCase("pdf")) {
				exporter = new JRPdfExporter();
			} else if (this.format.equalsIgnoreCase("Excel")) {
				exporter = new JRXlsxExporter();
				extention = ".xls";
			}

			fileName += extention;
			LOG.log(Level.INFO, "Creating file {1}", fileName);

			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new File(fileName));

			try {
				exporter.exportReport();
			} catch (JRException e) {
				e.printStackTrace();
				throw new BSConfigurationException(e);
			}
		}
		// } catch (Exception e) {
		// e.printStackTrace();
		// throw new BSConfigurationException(e);
		// } finally {
		// new BSmySQL().closeConnection(conn);
		// }
	}

	public String validateFolder(String outputFolder, IdRut idRut, Boolean useUsername) {
		String out = null;
		if (!useUsername) {
			String date = BSDateTimeUtil.calendar2String(Calendar.getInstance(), "yyyy-MM-dd");

			out = fixPath(outputFolder) + idRut.getCostCenter() + File.separator + date;

		} else {
			String[] months = { "ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sep", "oct", "nov", "dic" };
			String year = BSDateTimeUtil.calendar2String(Calendar.getInstance(), "yyyy");
			String month = BSDateTimeUtil.calendar2String(Calendar.getInstance(), "MM");
			month = months[Integer.parseInt(month) - 1];
			out = fixPath(outputFolder + File.separator + idRut.getUsername() + File.separator + year + File.separator + month);
		}
		File folder = new File(out);
		if (!folder.exists()) {
			if (!folder.mkdirs()) {
				throw new BSSystemException("No se pudo crear la carpeta [" + out + "]");
			}
		}
		return fixPath(out);
	}

	private String fixPath(String folderName) {
		String out = null;
		if (!folderName.endsWith(File.separator)) {
			out = folderName + File.separator;
		} else {
			out = folderName;
		}
		return out;
	}

	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	public Connection getConnection(String dataBaseName, String user, String password) throws Exception {
		if (this.conn == null) {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dataBaseName, user, password);
		}
		return conn;
	}

	public List<IdRut> getEmployeeList(Connection conn, String idEmploye) {
		List<IdRut> out = new ArrayList<IdRut>();
		IdRut data = null;
		Boolean allEmployee = idEmploye.equals("0");
		String sql = null;
		List<Object> param = null;

		/**
		 * <code>
		 * SELECT UserId, SSN, Name, DEFAULTDEPTID, tArea.cCostCenter
FROM userinfo LEFT JOIN tArea ON DEFAULTDEPTID = cKey
ORDER BY SSN
</code>
		 */
		/**
		 * TODO: aqui falta incorporar el username para la generacion del
		 * archivo
		 */

		/**
		 * SELECT UserId, SSN, Name, tArea.cCostCenter AS DEFAULTDEPTID,
		 * tEmployee.cUsername FROM userinfo LEFT JOIN tArea ON DEFAULTDEPTID =
		 * cKey LEFT JOIN tEmployee ON UserId = tEmployee.cKey ORDER BY SSN
		 */

		/**
		 * <code>
		sql = "SELECT UserId, SSN, Name, tArea.cCostCenter AS DEFAULTDEPTID, tEmployee.cUsername AS cUsername ";
		sql += "FROM userinfo ";
		sql += "LEFT JOIN tArea ON DEFAULTDEPTID = cKey ";
		sql += "LEFT JOIN tEmployee ON UserId = tEmployee.cKey ";
		sql += allEmployee ? "" : "WHERE UserId=? ";
		sql += "ORDER BY SSN;";
</code>
		 */

		sql = "SELECT tEmployee.cId AS UserId, tEmployee.cKey AS cKey, tEmployee.cRut AS SSN, tEmployee.cName AS Name, tArea.cCostCenter AS DEFAULTDEPTID, tEmployee.cUsername AS cUsername ";
		sql += "FROM tEmployee ";
		sql += "LEFT JOIN tArea ON tEmployee.cArea = tArea.cId ";
		sql += allEmployee ? "" : "WHERE tEmployee.cId=? ";
		sql += getOrderSQL(conn);

		if (!allEmployee) {
			param = new ArrayList<Object>();
			param.add(idEmploye);
		}

		LOG.log(Level.FINE, sql);

		BSDataUtils du = new BSDataUtils();
		ResultSet rs = du.queryResultSet(conn, sql, param);
		try {
			while (rs.next()) {
				data = new IdRut();
				data.setId(rs.getInt("UserId"));
				data.setKey(rs.getString("cKey"));
				data.setRut(rs.getString("SSN"));
				data.setName(rs.getString("Name"));
				data.setCostCenter(rs.getString("DEFAULTDEPTID"));
				data.setUsername(rs.getString("cUsername"));

				// data.add("" + rs.getInt(1));
				// data.add(rs.getString(2));

				out.add(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BSConfigurationException(e);
		}
		return out;
	}

	private String getOrderSQL(Connection conn) {
		String out = "ORDER BY tEmployee.cRut;";

		BSConfig config = new BSConfig();
		String tempOut = config.getString(conn, "EMPLOYEE_ORDER");
		if (tempOut != null && tempOut.length() > 0) {
			out = "ORDER BY " + tempOut;
		}

		return out;
	}

	private Map<String, Object> getParams(Integer employee, String startDate, String endDate) {
		Map<String, Object> out = new HashMap<String, Object>();

		// Integer idEmployeInteger = Integer.parseInt(employee);
//		BSDateTimeUtil dtu = new BSDateTimeUtil();

		Date startDateDate = BSDateTimeUtil.calendar2Date(BSDateTimeUtil.string2Calendar(startDate, "yyyy-MM-dd"));
		Date endDateDate = BSDateTimeUtil.calendar2Date(BSDateTimeUtil.string2Calendar(endDate, "yyyy-MM-dd"));

		out.put("UserId", employee);
		out.put("StartDate", startDateDate);
		out.put("EndDate", endDateDate);

		return out;
	}

	private void showParams(String dataBaseName, String user, String password, String report, String outputFolder,
			String startDate, String endDate, String idEmploye, String useUsername) {
		LOG.log(Level.CONFIG, "DataBaseName: {0}", dataBaseName);
		LOG.log(Level.CONFIG, "User: {0}", user);
		LOG.log(Level.CONFIG, "Password: {0}", password);
		LOG.log(Level.CONFIG, "Report: {0}", report);
		LOG.log(Level.CONFIG, "Output Folder: {0}", outputFolder);
		LOG.log(Level.CONFIG, "Start Date: {0}", startDate);
		LOG.log(Level.CONFIG, "End Date: {0}", endDate);
		LOG.log(Level.CONFIG, "Id Employee: {0}", idEmploye);
		LOG.log(Level.CONFIG, "Use username: {0}", useUsername);

	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

}
