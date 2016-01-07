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
import cl.buildersoft.framework.util.BSConnectionFactory;
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

	private String dsName = null;

	private String logPath = null;
	private String webPath = null;
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

		// readProperties(prop);
		// validateProperties();
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

	private void validateVariable(String value, String name) {
		if (value == null) {
			String msg = "La variable '" + name + "' no se ha configurado apropiadamente, revise el archivo '" + FILE_NAME + "'";
			throw new BSConfigurationException(msg);
		}
	}

	protected Connection getConnection() {
		if (this.conn == null) {
			BSConnectionFactory cf = new BSConnectionFactory();
			this.conn = cf.getConnection(this.dsName);
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

	public String getDSName() {
		return dsName;
	}

	public void setDSName(String dsName) {
		this.dsName = dsName;
	}

}
