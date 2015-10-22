package cl.buildersoft.timectrl.business.beans;

import java.util.Calendar;

import cl.buildersoft.framework.beans.BSBean;

public class EmployeeTurn extends BSBean {
	private static final long serialVersionUID = -4855245848460737690L;
	@SuppressWarnings("unused")
	private String TABLE = "tR_EmployeeTurn";
	private Long employee = null;
	private Long turn = null;
	private String turnName = null;
	private Calendar startDate = null;
	private Calendar endDate = null;

	public Long getEmployee() {
		return employee;
	}

	public void setEmployee(Long employee) {
		this.employee = employee;
	}

	public Long getTurn() {
		return turn;
	}

	public void setTurn(Long turn) {
		this.turn = turn;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	public String getTurnName() {
		return turnName;
	}

	public void setTurnName(String turnName) {
		this.turnName = turnName;
	}

	@Override
	public String toString() {
		return "EmployeeTurn [employee=" + employee + ", turn=" + turn + ", turnName=" + turnName + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", Id=" + getId() + "]";
	}

}
