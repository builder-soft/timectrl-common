package cl.buildersoft.timectrl.business.services.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import cl.buildersoft.timectrl.business.beans.Employee;
import cl.buildersoft.timectrl.business.beans.ReportParameterBean;
import cl.buildersoft.timectrl.business.beans.ReportPropertyBean;
import cl.buildersoft.timectrl.business.beans.ReportType;
import cl.buildersoft.timectrl.business.services.EmployeeService;

public class ReportToAllBossImpl extends SendReportByMailImpl {
	@Override
	public List<String> execute(Connection conn, Long idReport, ReportType reportType,
			List<ReportPropertyBean> reportPropertyList, List<ReportParameterBean> reportParameterList) {
		readProperties(conn, reportPropertyList);
		ReportParameterBean bossId = getReportParameter(reportParameterList, "BOSS_LIST");
		ReportPropertyBean destiny = getReportProperty(reportPropertyList, "DESTINY");

		List<String> out = null;
		if ("BOSS_ONLY".equalsIgnoreCase(destiny.getPropertyValue()) && "0".equalsIgnoreCase(bossId.getValue())
				&& "SEND_BY_MAIL".equalsIgnoreCase(reportType.getKey())) {
			out = new ArrayList<String>();

			EmployeeService es = new EmployeeServiceImpl();
			List<Employee> bossList = es.listBoss(conn);
			// readProperties(conn, reportPropertyList);
			for (Employee employee : bossList) {
				bossId.setValue(employee.getId().toString());
				out.addAll(super.execute(conn, idReport, reportType, reportPropertyList, reportParameterList));
			}

		} else {
			out = super.execute(conn, idReport, reportType, reportPropertyList, reportParameterList);
		}

		return out;
	}
}
