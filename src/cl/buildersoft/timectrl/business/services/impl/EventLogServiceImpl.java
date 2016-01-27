package cl.buildersoft.timectrl.business.services.impl;

import java.sql.Connection;
import java.util.Calendar;
import java.util.List;

import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.framework.exception.BSConfigurationException;
import cl.buildersoft.timectrl.business.beans.Event;
import cl.buildersoft.timectrl.business.beans.EventType;
import cl.buildersoft.timectrl.business.services.EventLogService;

public class EventLogServiceImpl implements EventLogService {

	@Override
	public void writeEntry(Connection conn, Long user, String eventTypeKey, String what, Object... params) {
		BSBeanUtils bu = new BSBeanUtils();
		Event event = new Event();
		EventType eventType = new EventType();

		if (!bu.search(conn, eventType, "cKey=?", eventTypeKey)) {
			throw new BSConfigurationException("Event type not found, it was " + eventTypeKey);
		}

		event.setEventType(eventType.getId());
		event.setWhen(Calendar.getInstance());
		event.setUser(user);
		event.setWhat(String.format(what, params));

		bu.insert(conn, event);

	}

	@Override
	public List<Event> list(Connection conn, Calendar startDate, Calendar endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> list(Connection conn, Calendar startDate, Calendar endDate, Long eventType, Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> listByEventType(Connection conn, Calendar startDate, Calendar endDate, Long eventType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> listByUser(Connection conn, Calendar startDate, Calendar endDate, Long user) {
		// TODO Auto-generated method stub
		return null;
	}

}
