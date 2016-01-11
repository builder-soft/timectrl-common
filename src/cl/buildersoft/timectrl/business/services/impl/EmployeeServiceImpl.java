package cl.buildersoft.timectrl.business.services.impl;

import java.sql.Connection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.framework.exception.BSProgrammerException;
import cl.buildersoft.framework.util.BSConnectionFactory;
import cl.buildersoft.framework.util.BSHttpServlet;
import cl.buildersoft.timectrl.business.beans.Area;
import cl.buildersoft.timectrl.business.beans.Employee;
import cl.buildersoft.timectrl.business.beans.Post;
import cl.buildersoft.timectrl.business.services.EmployeeService;

public class EmployeeServiceImpl extends BSHttpServlet implements EmployeeService {
	private static final Logger LOG = Logger.getLogger(EmployeeServiceImpl.class.getName());
	private static final String IS_BOSS = "cId IN (SELECT DISTINCT(cBoss) FROM tEmployee WHERE NOT cBoss IS NULL)";
	private static final long serialVersionUID = 9126047546816667626L;

	@Override
	public Employee getEmployee(Connection conn, HttpServletRequest request) {
		String idAsParameter = request.getParameter("cId");
		String employeeId = null;

		if (idAsParameter != null) {
			employeeId = idAsParameter;
		} else {
			Object idAsAttribute = request.getAttribute("cId");
			if (idAsAttribute == null) {
				String errorMsg = "There is not Parameter or Attribute of Employee Id";
				BSProgrammerException e = new BSProgrammerException(errorMsg);
				LOG.logp(Level.SEVERE, EmployeeServiceImpl.class.getName(), "getEmployee", errorMsg);
				throw e;
			} else {
				employeeId = idAsAttribute.toString();
			}
		}

		Long id = Long.parseLong(employeeId);
		return getEmployee(conn, id);
	}

	@Override
	public Employee getEmployee(Connection conn, Long id) {
		Employee out = new Employee();
		BSBeanUtils bu = new BSBeanUtils();
		out.setId(id);
		bu.search(conn, out);
		return out;
	}

	@Override
	public Employee getEmployee(HttpServletRequest request, Long id) {
		BSConnectionFactory cf = new BSConnectionFactory();
		Connection conn = cf.getConnection(request);
		Employee out = getEmployee(conn, id);
		cf.closeConnection(conn);
		return out;
	}

	@Override
	public Employee getEmployeeByKey(HttpServletRequest request, String key) {
		BSBeanUtils bu = new BSBeanUtils();
		Employee employee = new Employee();

		BSConnectionFactory cf = new BSConnectionFactory();
		Connection conn = cf.getConnection(request);

		bu.search(conn, employee, "cKey=?", key);
		cf.closeConnection(conn);
		return employee;
	}

	@Override
	public Post readPostOfEmployee(Connection conn, Employee employee) {
		Post out = new Post();
		BSBeanUtils bu = new BSBeanUtils();
		out.setId(employee.getPost());
		bu.search(conn, out);
		return out;
	}

	@Override
	public Area readAreaOfEmployee(Connection conn, Employee employee) {
		Area out = new Area();
		BSBeanUtils bu = new BSBeanUtils();
		out.setId(employee.getArea());
		bu.search(conn, out);
		return out;
	}

	@Override
	public List<Employee> listEmployeeByBoss(Connection conn, Long bossId) {
		BSBeanUtils bu = new BSBeanUtils();
		return (List<Employee>) bu.list(conn, new Employee(), "cBoss = ?", bossId);
	}

	@Override
	public List<Employee> listBoss(Connection conn) {
		BSBeanUtils bu = new BSBeanUtils();
		return (List<Employee>) bu.list(conn, new Employee(), IS_BOSS);
	}

	@Override
	public void sortByName(List<Employee> employeeList) {
		Collections.sort(employeeList, new Comparator<Employee>() {
			@Override
			public int compare(final Employee object1, final Employee object2) {
				return object1.getName().compareTo(object2.getName());
			}
		});

	}

	@Override
	public void sortByRut(List<Employee> employeeList) {
		Collections.sort(employeeList, new Comparator<Employee>() {
			@Override
			public int compare(final Employee object1, final Employee object2) {
				return object1.getRut().compareTo(object2.getRut());
			}
		});

	}

	@Override
	public List<Employee> listEmployeeByArea(Connection conn, Long areaId) {
		BSBeanUtils bu = new BSBeanUtils();
		return (List<Employee>) bu.list(conn, new Employee(), "cArea = ?", areaId);
	}

}
