package cl.buildersoft.timectrl.web.filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import cl.buildersoft.framework.beans.Domain;
import cl.buildersoft.framework.exception.BSConfigurationException;
import cl.buildersoft.framework.exception.BSDataBaseException;
import cl.buildersoft.framework.util.BSConfig;
import cl.buildersoft.framework.util.BSConnectionFactory;
import cl.buildersoft.timectrl.util.LicenseValidationUtil;

// @ WebFilter(urlPatterns = { "/servlet/*" }, dispatcherTypes = { DispatcherType.REQUEST })
public class LicenseValidationFilter implements Filter {
	private static final Logger LOG = Logger.getLogger(LicenseValidationFilter.class.getName());
	private Map<String, Boolean> activeFilter = null;

	public void init(FilterConfig filterConfig) throws ServletException {
		if (activeFilter == null) {
			this.activeFilter = new HashMap<String, Boolean>();
			LOG.log(Level.FINEST, "Loading license list");
			ServletContext context = filterConfig.getServletContext();
			BSConfig config = new BSConfig();

			String path = System.getenv("BS_PATH");  // context.getRealPath("/WEB-INF");
			path = config.fixPath(path);
			LOG.log(Level.INFO, path);

			InputStream in = null;
			try {
				in = new FileInputStream(path + "license.properties");

				Properties prop = new Properties();
				prop.load(in);

				Enumeration<?> e = prop.propertyNames();
				while (e.hasMoreElements()) {
					String key = (String) e.nextElement();
					this.activeFilter.put(key, Boolean.parseBoolean(prop.getProperty(key)));
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public void destroy() {
		LOG.log(Level.FINEST, "Clearing license list");
		this.activeFilter.clear();
		this.activeFilter = null;
	}

	public void doFilter(ServletRequest rq, ServletResponse rs, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) rq;
		LOG.log(Level.FINE, "In License Filter");
		Boolean success = true;

		Domain domain = (Domain) request.getSession(false).getAttribute("Domain");
		Boolean activeFilter = this.activeFilter.get("bsframework.license.validate." + domain.getDatabase());

		if (activeFilter == null || activeFilter) {
			try {
				success = licenseValidation(request, domain.getDatabase());
			} catch (Exception e) {
				LOG.log(Level.SEVERE, "Can not validate license", e);
				success = false;
			}
		}

		if (success) {
			chain.doFilter(rq, rs);
		} else {
			request.getSession(false).invalidate();
			throw new BSConfigurationException("Some configuration files are wrong!");
		}
	}

	private Boolean licenseValidation(HttpServletRequest request, String domainKey) {
		Connection conn = null;
		Boolean out = null;
		BSConnectionFactory cf = new BSConnectionFactory();
		try {
			conn = cf.getConnection(request);
		} catch (Exception e) {
			conn = null;
		}

		try {
			if (conn == null || closedConnection(conn)) {
				out = true;
			} else {
				String pathFile = request.getSession(false).getServletContext().getRealPath("/") + "WEB-INF" + File.separator
						+ "license." + domainKey + ".dat";

				LicenseValidationUtil lv = new LicenseValidationUtil();
				String fileContent = lv.readFile(pathFile);
				out = lv.licenseValidation(conn, fileContent);
			}
		} finally {
			cf.closeConnection(conn);
		}
		return out;
	}

	private boolean closedConnection(Connection conn) throws BSDataBaseException {
		try {
			return conn.isClosed();
		} catch (SQLException e) {
			throw new BSDataBaseException(e);
		}
	}

}
