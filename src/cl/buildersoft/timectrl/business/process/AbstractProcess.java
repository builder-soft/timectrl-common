package cl.buildersoft.timectrl.business.process;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import cl.buildersoft.framework.beans.Domain;
import cl.buildersoft.framework.beans.DomainAttribute;
import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.framework.exception.BSConfigurationException;
import cl.buildersoft.framework.exception.BSException;
import cl.buildersoft.framework.util.BSConfig;
import cl.buildersoft.framework.util.BSDataUtils;
import cl.buildersoft.framework.util.BSUtils;
import cl.buildersoft.timectrl.util.LicenseValidationUtil;

public abstract class AbstractProcess {
	private final static String FILE_NAME = "Process.properties";
	// private String logPath = null;
	private String database = null;
	private String driver = null;
	private String password = null;
	private String serverName = null;
	private String user = null;
	private String port = null;
	private String validateLicense = null;
	private Connection conn = null;
	private String webInfPath = null;

	private static final Logger LOG = Logger.getLogger(AbstractProcess.class.getName());

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

	public AbstractProcess(Connection conn) {
		this.conn = conn;
	}

	public AbstractProcess() {
		init();
		this.conn = getConnection();
		Boolean success = licenseValidation(this.conn);
		if (!success) {
			LOG.log(Level.SEVERE, "License invalid");
			throw new BSConfigurationException("Licencia no es válida");
		}
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

	public void init() {
		BSConfig config = new BSConfig();
		this.webInfPath = System.getenv("BS_PATH");

		LOG.log(Level.CONFIG, "Value of 'BS_PATH' is {0}", this.webInfPath);

		if (this.webInfPath == null) {
			throw new BSConfigurationException("Undefined enviroment variable BS_PATH");
		}
		this.webInfPath = config.fixPath(this.webInfPath);
		String propertyFileName = this.webInfPath + FILE_NAME;
		Properties prop = new Properties();
		InputStream inputStream;
		try {
			inputStream = new FileInputStream(propertyFileName);
		} catch (FileNotFoundException e) {
			LOG.log(Level.SEVERE, "File not found '" + propertyFileName + "'", e);
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
			LOG.log(Level.CONFIG, "Property: {0}= {1}", BSUtils.array2ObjectArray(o.toString(), prop.getProperty(o.toString())));
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected Boolean licenseValidation(Connection conn) {
		Boolean out = true;
		if (Boolean.parseBoolean(this.validateLicense)) {
			LicenseValidationUtil lv = new LicenseValidationUtil();
			out = lv.licenseValidation(conn, lv.readFile(getLicenseFileDatPath()));
		}
		return out;
	}

	private String getLicenseFileDatPath() {
		// BSConfig config = new BSConfig();
		String out = this.webInfPath + "LicenseFile.dat";
		return out;
	}

	private void readProperties(Properties prop) {
		// this.logPath = prop.getProperty("logPath");
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
				if ("DELETE_MARKS_OF_MACHINE".equalsIgnoreCase(argumentName)) {
					try{
						Boolean.parseBoolean(args[index]);
					}catch(Exception e)
					{
						valid = false;
					}
					 
				}

				if (!valid) {
					throw new BSConfigurationException("Argument '" + argumentName + "' is'n valid");
				}
			}
		} else {
			String msg = "Number of arguments not valid. Received " + args.length + ", expected " + validArgumentList.length
					+ ".";
			BSException e = new BSConfigurationException(msg);
			LOG.log(Level.SEVERE, msg, e);
			throw e;
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
}
