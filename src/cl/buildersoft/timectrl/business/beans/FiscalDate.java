package cl.buildersoft.timectrl.business.beans;

import cl.buildersoft.framework.beans.BSBean;

public class FiscalDate extends BSBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5758421276681861269L;
	@SuppressWarnings("unused")
	private String TABLE = "tFiscalDate";
	private String date = null;
	private String reason = null;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
