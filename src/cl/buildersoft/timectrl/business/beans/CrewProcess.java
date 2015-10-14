package cl.buildersoft.timectrl.business.beans;

import java.util.Date;

import cl.buildersoft.framework.beans.BSBean;

public class CrewProcess extends BSBean {
	private static final long serialVersionUID = 1566429547119117125L;
	@SuppressWarnings("unused")
	private String TABLE = "tCrewProcess";
	private Date date = null;
	private Long employee = null;
	private Integer hoursWorked = null;
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
	public Integer getHoursWorked() {
		return hoursWorked;
	}
	public void setHoursWorked(Integer cHoursWorked) {
		this.hoursWorked = cHoursWorked;
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

}
