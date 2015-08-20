package cl.buildersoft.timectrl.business.beans;

import java.util.Date;

import cl.buildersoft.framework.beans.BSBean;

public class TimeCtrl extends BSBean {
	private static final long serialVersionUID = 2818822310440063689L;
	@SuppressWarnings("unused")
	private String TABLE = "tTimeCtrl";
	private String employeeKey = null;
	private Long file = null;
	private Date date = null;
	private String turn = null;
	private String onDuty = null;
	private String offDuty = null;
	private String clockIn = null;
	private String clockOut = null;
	private String late = null;
	private String early = null;
	private String workTime = null;
	private Boolean weekEnd = null;
	private Boolean holiday = null;

	public String getEmployeeKey() {
		return employeeKey;
	}

	public void setEmployeeKey(String employeeKey) {
		this.employeeKey = employeeKey;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTurn() {
		return turn;
	}

	public void setTurn(String turn) {
		this.turn = turn;
	}

	public String getOnDuty() {
		return onDuty;
	}

	public void setOnDuty(String onDuty) {
		this.onDuty = onDuty;
	}

	public String getOffDuty() {
		return offDuty;
	}

	public void setOffDuty(String offDuty) {
		this.offDuty = offDuty;
	}

	public String getClockIn() {
		return clockIn;
	}

	public void setClockIn(String clockIn) {
		this.clockIn = clockIn;
	}

	public String getClockOut() {
		return clockOut;
	}

	public void setClockOut(String clockOut) {
		this.clockOut = clockOut;
	}

	public String getLate() {
		return late;
	}

	public void setLate(String late) {
		this.late = late;
	}

	public String getEarly() {
		return early;
	}

	public void setEarly(String early) {
		this.early = early;
	}

	public String getWorkTime() {
		return workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}

	public Boolean getWeekEnd() {
		return weekEnd;
	}

	public void setWeekEnd(Boolean weekEnd) {
		this.weekEnd = weekEnd;
	}

	public Boolean getHoliday() {
		return holiday;
	}

	public void setHoliday(Boolean holiday) {
		this.holiday = holiday;
	}

	public Long getFile() {
		return file;
	}

	public void setFile(Long file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "TimeCtrl [employee=" + employeeKey + ", file=" + file + ", date=" + date + ", turn=" + turn + ", onDuty=" + onDuty
				+ ", offDuty=" + offDuty + ", clockIn=" + clockIn + ", clockOut=" + clockOut + ", late=" + late + ", early="
				+ early + ", workTime=" + workTime + ", weekEnd=" + weekEnd + ", holiday=" + holiday + "]";
	}
}
