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
import cl.buildersoft.timectrl.business.beans.Area;
import cl.buildersoft.timectrl.business.beans.Employee;
import cl.buildersoft.timectrl.business.beans.Post;

@WebServlet("/servlet/dalea/web/GetEmployeeInfo")
public class GetEmployeeInfo extends BSHttpServlet_ {
	private static final Logger LOG = LogManager.getLogger(GetEmployeeInfo.class);
	private static final long serialVersionUID = -490966795587603364L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * Este servlet retorna el html en duro por que en caso contrario,
		 * tendria que copiar el archivo "employee-info2.jsp" en todos los war
		 * que muestran la informacion del empleado como cabecera
		 */
		LOG.entry();

		PrintWriter w = response.getWriter();

		Employee employee = getEmployee(request);
		Post post = getPost(request);
		Area area = getArea(request);

		String key = employee != null ? employee.getKey() : "";
		String rut = employee != null ? employee.getRut() : "";
		String name = employee != null ? employee.getName() : "";
		String postName = post != null ? post.getName() : "";
		String areaName = area != null ? area.getName() : "";		
		
		w.println("<div class=\"row well\">");
		w.println("  <div class=\"row\">");
		w.println("   <div class=\"col-sm-4\"><label>Lleve:&nbsp;&nbsp; </label>" + key+"</div>");
		w.println("   <div class=\"col-sm-4\"><label>RUT:&nbsp;&nbsp; </label>" + rut+"</div>");
		w.println("   <div class=\"col-sm-4\"><label>Nombre:&nbsp;&nbsp; </label>" + name+"</div>");
		w.println("  </div>");
		w.println("  <div class=\"row\">");
		w.println("   <div class=\"col-sm-4\"><label>Cargo:&nbsp;&nbsp; </label>" + postName+"</div>");
		w.println("   <div class=\"col-sm-4\"><label>Área:&nbsp;&nbsp; </label>" + areaName+"</div>");
		w.println("   <div class=\"col-sm-offset-4\"></div>");
		w.println("  </div>");
		w.println("</div>");
	//	w.println("</div>");

		w.flush();
		w.close();

		// forward(request, response,
		// "/WEB-INF/jsp/employee/employee-info2.jsp", false);
		LOG.exit();
	}

	private Post getPost(HttpServletRequest request) {
		Post post = null;
		Object mayBePost = request.getAttribute("Post");
		if (mayBePost != null && mayBePost instanceof Post) {
			post = (Post) mayBePost;
		}
		return post;
	}

	private Area getArea(HttpServletRequest request) {
		Area area = null;
		Object mayBeArea = request.getAttribute("Area");
		if (mayBeArea != null && mayBeArea instanceof Area) {
			area = (Area) mayBeArea;
		}
		return area;
	}

	private Employee getEmployee(HttpServletRequest request) {
		Employee employee = null;
		Object mayBeEmployee = request.getAttribute("Employee");
		if (mayBeEmployee != null && mayBeEmployee instanceof Employee) {
			employee = (Employee) mayBeEmployee;
		}
		return employee;
	}

}
