package cl.buildersoft.timectrl.business.services;

import java.sql.Connection;
import java.util.Calendar;
import java.util.List;

import cl.buildersoft.timectrl.business.beans.EventBean;

public interface EventLogService {
	public void writeEntry(Connection conn, Long userId, String eventTypeKey, String what, Object... params);

	public List<EventBean> list(Connection conn, Calendar startDate, Calendar endDate);

	public List<EventBean> list(Connection conn, Calendar startDate, Calendar endDate, Long eventType, Long userId);

	public List<EventBean> listByEventType(Connection conn, Calendar startDate, Calendar endDate, Long eventType);

	public List<EventBean> listByUser(Connection conn, Calendar startDate, Calendar endDate, Long userId);

}
