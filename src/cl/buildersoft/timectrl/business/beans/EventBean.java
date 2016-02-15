package cl.buildersoft.timectrl.business.beans;

import java.util.Calendar;

import cl.buildersoft.framework.util.BSDateTimeUtil;

public class EventBean {
	private Calendar when = null;
	private Long user = null;
	private String userName = null;
	private String userMail = null;
	private Long eventType = null;
	private String eventTypeName = null;
	private String what = null;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public Long getEventType() {
		return eventType;
	}

	public void setEventType(Long eventType) {
		this.eventType = eventType;
	}

	public String getEventTypeName() {
		return eventTypeName;
	}

	public void setEventTypeName(String eventTypeName) {
		this.eventTypeName = eventTypeName;
	}

	public String getWhat() {
		return what;
	}

	public void setWhat(String what) {
		this.what = what;
	}

	@Override
	public String toString() {
		return "EventBean [when=" + BSDateTimeUtil.calendar2String(when, "yyyy-MM-dd hh:mm:ss") + ", user=" + user
				+ ", userName=" + userName + ", userMail=" + userMail + ", eventType=" + eventType + ", eventTypeName="
				+ eventTypeName + ", what=" + what + "]";
	}

}
