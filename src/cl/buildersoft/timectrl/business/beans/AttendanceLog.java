package cl.buildersoft.timectrl.business.beans;

import cl.buildersoft.framework.beans.BSBean;

public class AttendanceLog extends BSBean {
	private static final long serialVersionUID = 79625782898180471L;
	@SuppressWarnings("unused")
	private String TABLE = "tAttendanceLog";
	private Long machine = null;
	private String employeeKey = null;
	private Long markType = null;
	private Integer year = null;
	private Integer month = null;
	private Integer day = null;
	private Integer hour = null;
	private Integer minute = null;
	private Integer second = null;

	public Long getMachine() {
		return machine;
	}

	public void setMachine(Long machine) {
		this.machine = machine;
	}

	public String getEmployeeKey() {
		return employeeKey;
	}

	public void setEmployeeKey(String employeeKey) {
		this.employeeKey = employeeKey;
	}

	public Long getMarkType() {
		return markType;
	}

	public void setMarkType(Long markType) {
		this.markType = markType;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

	public Integer getMinute() {
		return minute;
	}

	public void setMinute(Integer minute) {
		this.minute = minute;
	}

	public Integer getSecond() {
		return second;
	}

	public void setSecond(Integer second) {
		this.second = second;
	}

	@Override
	public String toString() {
		return "AttendanceLog [machine=" + machine + ", employeeKey=" + employeeKey + ", markType=" + markType + ", year=" + year
				+ ", month=" + month + ", day=" + day + ", hour=" + hour + ", minute=" + minute + ", second=" + second + "]";
	}
}
