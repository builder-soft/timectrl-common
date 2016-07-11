package cl.buildersoft.dalea.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.buildersoft.framework.web.servlet.BSHttpServlet_;

@WebServlet("/servlet/dalea/web/GetEmployeeInfo")
public class GetEmployeeInfo extends BSHttpServlet_ {
	private static final Logger LOG = LogManager.getLogger(GetEmployeeInfo.class);
	private static final long serialVersionUID = -490966795587603364L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOG.entry();

		PrintWriter w = response.getWriter();
		w.print("Hola mundo");
		w.flush();
		w.close();

		// forwardOrRedirect(request, response,
		// "/WEB-INF/jsp/employee/employee-info2.jsp");
		LOG.exit();
	}

}
