package cl.buildersoft.timectrl.business.services.impl;

import java.sql.Connection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.framework.exception.BSProgrammerException;
import cl.buildersoft.framework.util.BSHttpServlet;
import cl.buildersoft.timectrl.business.beans.Area;
import cl.buildersoft.timectrl.business.beans.Employee;
import cl.buildersoft.timectrl.business.beans.Post;
import cl.buildersoft.timectrl.business.services.EmployeeService;

public class EmployeeServiceImpl extends BSHttpServlet implements EmployeeService {
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
				throw new BSProgrammerException("No hay parametro de ID de empleado");
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
		return getEmployee(getConnection(request), id);
	}

	@Override
	public Employee getEmployeeByKey(HttpServletRequest request, String key) {
		BSBeanUtils bu = new BSBeanUtils();
		Employee employee = new Employee();
		Connection conn = getConnection(request);
		// Connection conn = bu.getConnection(request);

		bu.search(conn, employee, "cKey=?", key);

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

}
