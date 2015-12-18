package cl.buildersoft.timectrl.business.console;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import cl.buildersoft.framework.exception.BSConfigurationException;
import cl.buildersoft.framework.util.BSConfig;
import cl.buildersoft.framework.util.BSDataUtils;
import cl.buildersoft.framework.util.BSUtils;
import cl.buildersoft.timectrl.util.LicenseValidationUtil;

public abstract class AbstractConsoleService {
	private static final Logger LOG = Logger.getLogger(AbstractConsoleService.class.getName());
	private final static String FILE_NAME = "ConsoleService.properties";
	// private String propertyFileName = null;
	// private final String propertyFileName = System.getProperty("user.dir")
	// + File.separator + FILE_NAME;
	// private final Boolean VALIDATE_LICENSE = false;
	private Connection conn = null;
	private String driver = null;
	private String serverName = null;
	private String database = null;
	private String user = null;
	private String password = null;
	private String webPath = null;
	private String logPath = null;
	private String port = null;
	private String validateLicense = null;

	protected void init() {
		BSConfig config = new BSConfig();
		String path = System.getenv("BS_PATH");

		if (path == null) {
			throw new BSConfigurationException("Undefined enviroment variable BS_PATH");
		}
		String propertyFileName = config.fixPath(path) + "lib" + BSConfig.getFileSeparator() + FILE_NAME;
		LOG.log(Level.CONFIG, "Reading {0} file", propertyFileName);

		Properties prop = new Properties();
		InputStream inputStream;
		try {
			inputStream = new FileInputStream(propertyFileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new BSConfigurationException(e);
		}

		try {
			prop.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
			throw new BSConfigurationException(e);
		}

		readProperties(prop);
		validateProperties();
		// getAppPath();

		logPropertyValues(prop);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void logPropertyValues(Properties prop) {
		Enumeration<Object> propList = prop.keys();
		while (propList.hasMoreElements()) {
			Object o = propList.nextElement();
			LOG.log(Level.CONFIG, "{0} : {1}", BSUtils.array2ObjectArray(o.toString(), prop.getProperty(o.toString())));
		}
	}

	protected String getWebPath() {
		return this.webPath;
	}

	private String getLicenseFileDatPath() {
		BSConfig config = new BSConfig();
		String out = config.fixPath(getWebPath()) + "WEB-INF" + File.separator + "LicenseFile.dat";
		return out;
	}

	private void readProperties(Properties prop) {
		this.webPath = prop.getProperty("webPath");
		this.logPath = prop.getProperty("logPath");
		this.database = prop.getProperty("database");
		this.driver = prop.getProperty("driver");
		this.password = prop.getProperty("password");
		this.serverName = prop.getProperty("server");
		this.user = prop.getProperty("user");
		this.port = prop.getProperty("port");
		this.validateLicense = prop.getProperty("validateLicense");
	}

	private void validateProperties() {
		validateVariable(this.webPath, "webPath");
		validateVariable(this.logPath, "logPath");
		validateVariable(this.database, "database");
		validateVariable(this.driver, "driver");
		validateVariable(this.password, "password");
		validateVariable(this.serverName, "serverName");
		validateVariable(this.user, "user");
		validateVariable(this.port, "port");
		validateVariable(this.validateLicense, "validateLicense");

	}

	private void validateVariable(String value, String name) {
		if (value == null) {
			String msg = "La variable '" + name + "' no se ha configurado apropiadamente, revise el archivo '" + FILE_NAME + "'";
			throw new BSConfigurationException(msg);
		}
	}

	protected Connection getConnection() {
		if (this.conn == null) {
			BSDataUtils du = new BSDataUtils();
			this.conn = du.getConnection(this.driver, this.serverName + ":" + this.port, this.database, this.password, this.user);
		}
		return this.conn;
	}

	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	protected Boolean licenseValidation(Connection conn) {
		Boolean out = true;
		if (Boolean.parseBoolean(this.validateLicense)) {
			LicenseValidationUtil lv = new LicenseValidationUtil();
			out = lv.licenseValidation(conn, lv.readFile(getLicenseFileDatPath()));
		}
		return out;
	}

	protected Boolean isNumeric(String value) {
		Boolean out = true;
		try {
			Long.parseLong(value);
		} catch (NumberFormatException nfe) {
			out = false;
		}
		return out;
	}
}
