package cl.buildersoft.timectrl.business.process;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import cl.buildersoft.framework.beans.Domain;
import cl.buildersoft.framework.beans.DomainAttribute;
import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.framework.exception.BSConfigurationException;
import cl.buildersoft.framework.util.BSConfig;
import cl.buildersoft.framework.util.BSDataUtils;
import cl.buildersoft.framework.util.BSDateTimeUtil;

public abstract class AbstractProcess {
	private final static String FILE_NAME = "Process.properties";
	private String logPath = null;
	private String database = null;
	private String driver = null;
	private String password = null;
	private String serverName = null;
	private String user = null;
	private String port = null;
	private String validateLicense = null;
	private Connection conn = null;

	abstract protected String[] getArguments();

	public AbstractProcess(Connection conn) {
		this.conn = conn;
	}

	public AbstractProcess() { 
		init();
		 
		this.conn = getConnection();
	}

	protected Connection getConnection() {
		if (this.conn == null) {
			BSDataUtils du = new BSDataUtils();
			this.conn = du.getConnection(this.driver, this.serverName + ":" + this.port, this.database, this.password, this.user);
		}
		return this.conn;
	}

	@SuppressWarnings("unchecked")
	protected Connection getConnection(Domain domain) {
		Connection conn = null;
		DomainAttribute da = new DomainAttribute();

		BSBeanUtils bu = new BSBeanUtils();
		List<DomainAttribute> daList = (List<DomainAttribute>) bu.list(getConnection(), da, "cDomain=?", domain.getId());

		conn = bu.getConnection(getAttribute(daList, "database.driver"), getAttribute(daList, "database.server"),
				getAttribute(daList, "database.database"), getAttribute(daList, "database.password"),
				getAttribute(daList, "database.username"));

		return conn;
	}

	private String getAttribute(List<DomainAttribute> domainAttributeList, String key) {
		String out = null;
		for (DomainAttribute domainAttribute : domainAttributeList) {
			if (domainAttribute.getKey().equalsIgnoreCase(key)) {
				out = domainAttribute.getValue();
				break;
			}
		}
		return out;
	}

	private void init() {
		BSConfig config = new BSConfig();
		String path = System.getenv("BS_PATH");
		System.out.println("Value of 'BS_PATH' is '" + path + "'");

		if (path == null) {
			throw new BSConfigurationException("Undefined enviroment variable BS_PATH");
		}
		String propertyFileName = config.fixPath(path) + FILE_NAME;
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

		Enumeration<Object> propList = prop.keys();
		while (propList.hasMoreElements()) {
			Object o = propList.nextElement();
			System.out.println(o.toString() + " = " + prop.getProperty(o.toString()));
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void readProperties(Properties prop) {
		this.logPath = prop.getProperty("logPath");
		this.database = prop.getProperty("database");
		this.driver = prop.getProperty("driver");
		this.password = prop.getProperty("password");
		this.serverName = prop.getProperty("server");
		this.user = prop.getProperty("user");
		this.port = prop.getProperty("port");
		this.validateLicense = prop.getProperty("validateLicense");

	}

	protected void validateArguments(String[] args) {
		String[] validArgumentList = getArguments();
		if (args.length == validArgumentList.length) {
			Integer index = 0;
			Boolean valid = true;
			for (String argumentName : validArgumentList) {
				if ("DOMAIN".equalsIgnoreCase(argumentName)) {
					valid = validateDomain(args[index]);
				}

				if (!valid) {
					throw new BSConfigurationException("Argument '" + argumentName + "' is'n valid");
				}
			}
		} else {
			throw new BSConfigurationException("Number of arguments not valid. Received " + args.length + ", expected "
					+ validArgumentList.length + ".");
		}

	}

	protected Domain getDomainByBatabase(String database) {
		Connection bsConn = getConnection();
		BSBeanUtils bu = new BSBeanUtils();
		Domain domain = new Domain();

		bu.search(bsConn, domain, "cDatabase=?", database);
		return domain;
	}

	private boolean validateDomain(String databaseName) {
		Domain domain = getDomainByBatabase(databaseName);

		return domain != null;
	}

	protected void log(String message) {
		System.out.println(message);

		translateDateField();
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

	private void translateDateField() {
		this.logPath = this.logPath.replaceAll("\\x7BDate\\x7D",
				BSDateTimeUtil.calendar2String(Calendar.getInstance(), "yyyy-MM-dd"));

	}

	private String concatenateMessage(String message) {
		return BSDateTimeUtil.calendar2String(Calendar.getInstance(), "yyyy-M-dd hh:mm:ss") + " : " + message;
	}
}
