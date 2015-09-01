package cl.buildersoft.timectrl.business.services;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cl.buildersoft.timectrl.business.beans.Area;
import cl.buildersoft.timectrl.business.beans.Employee;
import cl.buildersoft.timectrl.business.beans.Post;

public interface EmployeeService {
	public Employee getEmployee(Connection conn, Long id);

	public Employee getEmployee(HttpServletRequest request, Long id);

	public Employee getEmployeeByKey(HttpServletRequest request, String key);

	public Employee getEmployee(Connection conn, HttpServletRequest request);

	public Post readPostOfEmployee(Connection conn, Employee employee);

	public Area readAreaOfEmployee(Connection conn, Employee employee);

	public List<Employee> listBoss(Connection conn);

}
