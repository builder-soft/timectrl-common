package cl.buildersoft.timectrl.business.beans;

import java.util.Calendar;

import cl.buildersoft.framework.beans.BSBean;

public class Event extends BSBean {
	private static final long serialVersionUID = 4290597945192392259L;

	private String TABLE = "tEvent";

	private Long eventType = null;
	private Calendar when = null;
	private Long user = null;
	private String what = null;

	public Long getEventType() {
		return eventType;
	}

	public void setEventType(Long eventType) {
		this.eventType = eventType;
	}

	public Calendar getWhen() {
		return when;
	}

	public void setWhen(Calendar when) {
		this.when = when;
	}

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}

	public String getWhat() {
		return what;
	}

	public void setWhat(String what) {
		this.what = what;
	}

	@Override
	public String toString() {
		return "Event [Id=" + getId() + ", TABLE=" + TABLE + ", eventType=" + eventType + ", when=" + when + ", user=" + user
				+ ", what=" + what + "]";
	}

}
