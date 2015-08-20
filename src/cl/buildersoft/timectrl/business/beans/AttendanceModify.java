package cl.buildersoft.timectrl.business.beans;

import java.sql.Timestamp;

import cl.buildersoft.framework.beans.BSBean;

public class AttendanceModify extends BSBean {
	private static final long serialVersionUID = -859248389764718378L;
	/**
	 * <code>
	 	cWho           | bigint(20)
		cWhen          | timestamp
		cEmployee      | bigint(20)
		cAttendanceLog | bigint(20)
		cOldMachine    | bigint(20)
		cNewMachine    | bigint(20)
		cOldDate       | timestamp
		cNewDate       | timestamp
		cOldMarkType   | bigint(20)
		cNewMarkType   | bigint(20)
</code>
	 */
	@SuppressWarnings("unused")
	private String TABLE = "tAttendanceModify";
	private Long who = null;
	private Timestamp when = null;
	private Long employee = null;
	private Long attendanceLog = null;
	private Long oldMachine = null;
	private Long newMachine = null;
	private Timestamp oldDate = null;
	private Timestamp newDate = null;
	private Long oldMarkType = null;
	private Long newMarkType = null;

	public Long getWho() {
		return who;
	}

	public void setWho(Long who) {
		this.who = who;
	}

	public Timestamp getWhen() {
		return when;
	}

	public void setWhen(Timestamp when) {
		this.when = when;
	}

	public Long getEmployee() {
		return employee;
	}

	public void setEmployee(Long employee) {
		this.employee = employee;
	}

	public Long getOldMachine() {
		return oldMachine;
	}

	public void setOldMachine(Long oldMachine) {
		this.oldMachine = oldMachine;
	}

	public Long getNewMachine() {
		return newMachine;
	}

	public void setNewMachine(Long newMachine) {
		this.newMachine = newMachine;
	}

	public Timestamp getOldDate() {
		return oldDate;
	}

	public void setOldDate(Timestamp oldDate) {
		this.oldDate = oldDate;
	}

	public Timestamp getNewDate() {
		return newDate;
	}

	public void setNewDate(Timestamp newDate) {
		this.newDate = newDate;
	}

	public Long getOldMarkType() {
		return oldMarkType;
	}

	public void setOldMarkType(Long oldMarkType) {
		this.oldMarkType = oldMarkType;
	}

	public Long getNewMarkType() {
		return newMarkType;
	}

	public void setNewMarkType(Long newMarkType) {
		this.newMarkType = newMarkType;
	}

	public Long getAttendanceLog() {
		return attendanceLog;
	}

	public void setAttendanceLog(Long attendanceLog) {
		this.attendanceLog = attendanceLog;
	}

}
