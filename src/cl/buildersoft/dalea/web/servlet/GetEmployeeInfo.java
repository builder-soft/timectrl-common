package cl.buildersoft.dalea.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cl.buildersoft.framework.web.servlet.BSHttpServlet_;

@WebServlet("/servlet/dalea/web/EmployeeInfo")
public class GetEmployeeInfo extends BSHttpServlet_ {
	private static final long serialVersionUID = -490966795587603364L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		forwardOrRedirect(request, response, "/WEB-INF/jsp/employee/employee-info2.jsp");
	}

}
