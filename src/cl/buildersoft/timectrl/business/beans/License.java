package cl.buildersoft.timectrl.business.beans;

import java.util.Date;

import cl.buildersoft.framework.beans.BSBean;

public class License extends BSBean {
	private static final long serialVersionUID = -1480562996292957806L;
	@SuppressWarnings("unused")
	private String TABLE = "tLicense";
	private Long employee = null;
	private Date startDate = null;
	private Date endDate = null;
	private Long licenseCause = null;
	private String document = null;

	public Long getEmployee() {
		return employee;
	}

	public void setEmployee(Long employee) {
		this.employee = employee;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getLicenseCause() {
		return licenseCause;
	}

	public void setLicenseCause(Long licenseCause) {
		this.licenseCause = licenseCause;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

}
