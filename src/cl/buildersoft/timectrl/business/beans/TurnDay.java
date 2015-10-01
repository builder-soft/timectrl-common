package cl.buildersoft.timectrl.business.beans;

import cl.buildersoft.framework.beans.BSBean;

public class TurnDay extends BSBean {
	private static final long serialVersionUID = 6787092951724895895L;
	@SuppressWarnings("unused")
	private String TABLE = "tTurnDay";

	private Long turn = null;
	private Integer day = null;
	private Boolean businessDay = null;

	private Integer edgePrevIn = null;
	private String startTime = null;
	private Integer edgePostIn = null;

	private Integer edgePrevOut = null;
	private String endTime = null;
	private Integer edgePostOut = null;

	public Long getTurn() {
		return turn;
	}

	public void setTurn(Long turn) {
		this.turn = turn;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Boolean getBusinessDay() {
		return businessDay;
	}

	public void setBusinessDay(Boolean businessDay) {
		this.businessDay = businessDay;
	}

	public Integer getEdgePrevIn() {
		return edgePrevIn;
	}

	public void setEdgePrevIn(Integer edgePrevIn) {
		this.edgePrevIn = edgePrevIn;
	}

	public Integer getEdgePostIn() {
		return edgePostIn;
	}

	public void setEdgePostIn(Integer edgePostIn) {
		this.edgePostIn = edgePostIn;
	}

	public Integer getEdgePrevOut() {
		return edgePrevOut;
	}

	public void setEdgePrevOut(Integer edgePrevOut) {
		this.edgePrevOut = edgePrevOut;
	}

	public Integer getEdgePostOut() {
		return edgePostOut;
	}

	public void setEdgePostOut(Integer edgePostOut) {
		this.edgePostOut = edgePostOut;
	}

	@Override
	public String toString() {
		return "TurnDay [id=" + this.getId() + " turn=" + turn + ", day=" + day + ", businessDay=" + businessDay
				+ ", edgePrevIn=" + edgePrevIn + ", startTime=" + startTime + ", edgePostIn=" + edgePostIn + ", edgePrevOut="
				+ edgePrevOut + ", endTime=" + endTime + ", edgePostOut=" + edgePostOut + "]";
	}

}
