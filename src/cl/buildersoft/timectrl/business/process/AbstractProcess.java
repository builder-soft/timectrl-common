package cl.buildersoft.timectrl.business.process;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.buildersoft.framework.beans.Domain;
import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.framework.exception.BSConfigurationException;
import cl.buildersoft.framework.exception.BSDataBaseException;
import cl.buildersoft.framework.exception.BSException;
import cl.buildersoft.framework.util.BSConfig;
import cl.buildersoft.framework.util.BSConnectionFactory;
import cl.buildersoft.framework.util.BSUtils;
import cl.buildersoft.timectrl.util.LicenseValidationUtil;

public abstract class AbstractProcess {
	static private final Logger LOG = LogManager.getLogger(AbstractProcess.class.getName());
	// static private final Level LEVEL_FOR_THIS_CLASS = Level.FINE;

	static private final String FILE_NAME = "license.properties";
	// private String logPath = null;
	private String dsName = null;
	private Boolean validateLicense = null;
	private Connection conn = null;
	private String webInfPath = null;
	private Boolean runFromConsole = false;

	// private final static Logger LOGGER =
	// Logger.getLogger(AbstractProcess.class.getName());
	// private static final Logger log =
	// Logger.getLogger(AbstractProcess.class.getName());
	/**
	 * { try { LogManager.getLogManager().readConfiguration(new
	 * FileInputStream(System.getenv("BS_PATH") + "\\logger.properties")); }
	 * catch (SecurityException e) { e.printStackTrace(); } catch
	 * (FileNotFoundException e) { e.printStackTrace(); } catch (IOException e)
	 * { e.printStackTrace(); } }
	 */
	abstract protected String[] getArguments();

	public Boolean getRunFromConsole() {
		return runFromConsole;
	}

	public void setRunFromConsole(Boolean runFromConsole) {
		this.runFromConsole = runFromConsole;
	}

	private void AbstractProcessBuilder(Connection conn) {
		this.conn = conn;
	}

	private void AbstractProcessBuilder() {
		init();
		this.conn = getConnection();
		Boolean success = licenseValidation(this.conn);
		if (!success) {
			LOG.fatal("License invalid");
			throw new BSConfigurationException("Licencia no es válida");
		}
	}

	protected Connection getConnection() {
		if (this.conn == null) {
			BSConnectionFactory cf = new BSConnectionFactory();
			this.conn = cf.getConnection(this.dsName);
		}
		return this.conn;
	}

	protected void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				if (!conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				throw new BSDataBaseException(e);
			}
		}
	}

	@SuppressWarnings("unchecked")
	protected Connection getConnection(Domain domain) {
		Connection conn = null;

		BSConnectionFactory cf = new BSConnectionFactory();
		conn = cf.getConnection(domain.getDatabase());

		return conn;
	}

	protected void init() {
		BSConfig config = new BSConfig();
		this.webInfPath = System.getenv("BS_PATH");

		LOG.info(String.format("Value of 'BS_PATH' is %s", this.webInfPath));

		if (this.webInfPath == null) {
			throw new BSConfigurationException("Undefined enviroment variable BS_PATH");
		}

		this.webInfPath = config.fixPath(this.webInfPath);
		String propertyFileName = this.webInfPath + FILE_NAME;
		Properties prop = new Properties();
		// String file = this.webInfPath+ "license.properties";

		InputStream inputStream;
		try {
			LOG.info(String.format("Reading file %s", propertyFileName));
			inputStream = new FileInputStream(propertyFileName);
		} catch (FileNotFoundException e) {
			LOG.fatal(String.format("File not found '%s', %s ", propertyFileName, e.getMessage()));
			throw new BSConfigurationException(e);
		}

		try {
			prop.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
			throw new BSConfigurationException(e);
		}

		Enumeration propList = prop.keys();

		while (propList.hasMoreElements()) {
			Object o = propList.nextElement();
			LOG.info(String.format("Property: %s=%s ", o.toString(), prop.getProperty(o.toString())));
		}

		// ----------------------

		this.validateLicense = Boolean.parseBoolean(prop.get("bsframework.license.validate." + dsName).toString());

		// try {
		// Thread.sleep(100);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
	}

	protected Boolean licenseValidation(Connection conn) {
		Boolean out = true;

		if (this.validateLicense) {
			LicenseValidationUtil lv = new LicenseValidationUtil();
			out = lv.licenseValidation(conn, lv.readFile(getLicenseFileDatPath()));
		}
		return out;
	}

	private String getLicenseFileDatPath() {
		// BSConfig config = new BSConfig();
		String out = this.webInfPath + "license." + dsName + ".dat";
		return out;
	}

	protected void validateArguments(String[] args) {
		validateArguments(args, true);
	}

	protected void validateArguments(String[] args, Boolean validateLen) {
		String[] validArgumentList = getArguments();

		if (validateLen) {
			if (args.length != validArgumentList.length) {
				String msg = "Number of arguments not valid. Received " + args.length + ", expected " + validArgumentList.length
						+ ".";
				BSException e = new BSConfigurationException(msg);
				LOG.fatal(msg, e);
				throw e;
			}
		}

		Integer index = 0;
		Boolean valid = true;
		for (String argumentName : validArgumentList) {
			if ("DOMAIN".equalsIgnoreCase(argumentName)) {
				valid = validateDomain(args[index]);
			}
			if ("DELETE_MARKS_OF_MACHINE".equalsIgnoreCase(argumentName)) {
				try {
					Boolean.parseBoolean(args[index]);
				} catch (Exception e) {
					valid = false;
				}

			}
			if ("MACHINE_ID".equalsIgnoreCase(argumentName)) {
				try {
					Boolean.parseBoolean(args[index]);
				} catch (Exception e) {
					valid = false;
				}

			}
			index++;
			if (!valid) {
				throw new BSConfigurationException("Argument '" + argumentName + "' is'n valid");
			}
		}

	}

	protected Domain getDomainByBatabase(String database) {
		BSConnectionFactory cf = new BSConnectionFactory();
		Connection bsConn = cf.getConnection();
		BSBeanUtils bu = new BSBeanUtils();
		Domain domain = new Domain();

		bu.search(bsConn, domain, "cDatabase=?", database);
		cf.closeConnection(bsConn);
		return domain;
	}

	private boolean validateDomain(String databaseName) {
		Domain domain = getDomainByBatabase(databaseName);

		return domain != null;
	}

	/**
	 * <code>
	private void translateDateField() {
		this.logPath = this.logPath.replaceAll("\\x7BDate\\x7D",
				BSDateTimeUtil.calendar2String(Calendar.getInstance(), "yyyy-MM-dd"));

	}

	private String concatenateMessage(String message) {
		return BSDateTimeUtil.calendar2String(Calendar.getInstance(), "yyyy-M-dd hh:mm:ss") + " : " + message;
	}
	</code>
	 */

	public String getDSName() {
		return this.dsName;
	}

	public final void setDSName(String dsName) {
		this.dsName = dsName;
	}

}
