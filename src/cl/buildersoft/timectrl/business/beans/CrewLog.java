package cl.buildersoft.timectrl.business.beans;

import java.util.Calendar;

import cl.buildersoft.framework.beans.BSBean;

public class CrewLog extends BSBean {
	private static final long serialVersionUID = -9063821924165416468L;

	@SuppressWarnings("unused")
	private String TABLE = "tCrewLog";
	private Long attendanceLog = null;
	private Calendar when = null;

	public Long getAttendanceLog() {
		return attendanceLog;
	}

	public void setAttendanceLog(Long attendanceLog) {
		this.attendanceLog = attendanceLog;
	}

	public Calendar getWhen() {
		return when;
	}

	public void setWhen(Calendar when) {
		this.when = when;
	}

}
