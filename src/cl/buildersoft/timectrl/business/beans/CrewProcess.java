package cl.buildersoft.timectrl.business.beans;

import java.util.Date;

import cl.buildersoft.framework.beans.BSBean;

public class CrewProcess extends BSBean {
	private static final long serialVersionUID = 1566429547119117125L;
	@SuppressWarnings("unused")
	private String TABLE = "tCrewProcess";
	private Date date = null;
	private Long employee = null;
	private Double hoursWorked = null;
	private Boolean worked = null;
	private Boolean hired = null;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Long getEmployee() {
		return employee;
	}
	public void setEmployee(Long employee) {
		this.employee = employee;
	}
	public Double getHoursWorked() {
		return hoursWorked;
	}
	public void setHoursWorked(Double hoursWorked) {
		this.hoursWorked = hoursWorked;
	}
	public Boolean getWorked() {
		return worked;
	}
	public void setWorked(Boolean worked) {
		this.worked = worked;
	}
	public Boolean getHired() {
		return hired;
	}
	public void setHired(Boolean hired) {
		this.hired = hired;
	}
	@Override
	public String toString() {
		return "CrewProcess [date=" + date + ", employee=" + employee + ", hoursWorked=" + hoursWorked + ", worked=" + worked
				+ ", hired=" + hired + ", Id=" + getId() + "]";
	}

}
