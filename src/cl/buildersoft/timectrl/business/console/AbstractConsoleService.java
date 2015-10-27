package cl.buildersoft.timectrl.business.console;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Properties;

import cl.buildersoft.framework.exception.BSConfigurationException;
import cl.buildersoft.framework.util.BSConfig;
import cl.buildersoft.framework.util.BSDataUtils;
import cl.buildersoft.framework.util.BSDateTimeUtil;
import cl.buildersoft.timectrl.util.LicenseValidationUtil;

public abstract class AbstractConsoleService {
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
		// System.out.println("Reading " + propertyFileName + " file");
		BSConfig config = new BSConfig();
		String path = System.getenv("BS_PATH");

		if (path == null) {
			throw new BSConfigurationException("Undefined enviroment variable BS_PATH");
		}
		String propertyFileName = config.fixPath(path) + "lib" + BSConfig.getFileSeparator() + FILE_NAME;

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
		/**
		 * <code>
		Enumeration<Object> propList = prop.keys();
		while (propList.hasMoreElements()) {
			Object o = propList.nextElement();
			System.out.println(o.toString() + " = " + prop.getProperty(o.toString()));
		}
</code>
		 */
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
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

	protected void log(String message) {
		System.out.println(message);

		File file = new File(this.logPath);
		try {
			FileWriter fileWritter = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(concatenateMessage(message) + "\n");
			bufferWritter.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(this.logPath);
			System.exit(1);
		}
	}

	private String concatenateMessage(String message) {
		return BSDateTimeUtil.calendar2String(Calendar.getInstance(), "yyyy-M-dd hh:mm:ss") + " : " + message;
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
