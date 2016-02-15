package cl.buildersoft.timectrl.business.services.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.framework.database.BSmySQL;
import cl.buildersoft.framework.exception.BSConfigurationException;
import cl.buildersoft.framework.exception.BSDataBaseException;
import cl.buildersoft.framework.util.BSDateTimeUtil;
import cl.buildersoft.framework.util.BSUtils;
import cl.buildersoft.timectrl.business.beans.Event;
import cl.buildersoft.timectrl.business.beans.EventBean;
import cl.buildersoft.timectrl.business.beans.EventType;
import cl.buildersoft.timectrl.business.services.EventLogService;

public class EventLogServiceImpl implements EventLogService {
	private static final Logger LOG = Logger.getLogger(EventLogServiceImpl.class.getName());

	@Override
	public void writeEntry(Connection conn, Long user, String eventTypeKey, String what, Object... params) {
		BSBeanUtils bu = new BSBeanUtils();
		Event event = new Event();
		EventType eventType = new EventType();

		if (!bu.search(conn, eventType, "cKey=?", eventTypeKey)) {
			eventType.setKey(eventTypeKey);
			eventType.setName(eventTypeKey);
			bu.insert(conn, eventType);

			LOG.log(Level.WARNING,
					"Record not found in table tEventType. The record is created with key {0}, this description should be updated.",
					eventTypeKey);
		}

		event.setEventType(eventType.getId());
		event.setWhen(Calendar.getInstance());
		event.setUser(user);
		event.setWhat(String.format(what, params));

		bu.insert(conn, event);

	}

	@Override
	public List<EventBean> list(Connection conn, Calendar startDate, Calendar endDate) {
		List<Object> params = BSUtils.array2List(BSDateTimeUtil.calendar2Date(startDate), BSDateTimeUtil.calendar2Date(endDate));
		return executeSP(conn, "pListEventsByDates", params);

	}

	@Override
	public List<EventBean> list(Connection conn, Calendar startDate, Calendar endDate, Long eventType, Long userId) {
		List<Object> params = BSUtils.array2List(BSDateTimeUtil.calendar2Date(startDate), BSDateTimeUtil.calendar2Date(endDate),
				eventType, userId);
		return executeSP(conn, "pListEventsByDatesUserAndEventType", params);
	}

	@Override
	public List<EventBean> listByEventType(Connection conn, Calendar startDate, Calendar endDate, Long eventType) {
		List<Object> params = BSUtils.array2List(BSDateTimeUtil.calendar2Date(startDate), BSDateTimeUtil.calendar2Date(endDate),
				eventType);
		return executeSP(conn, "pListEventsByDatesAndEventType", params);
	}

	@Override
	public List<EventBean> listByUser(Connection conn, Calendar startDate, Calendar endDate, Long userId) {
		List<Object> params = BSUtils.array2List(BSDateTimeUtil.calendar2Date(startDate), BSDateTimeUtil.calendar2Date(endDate),
				userId);
		return executeSP(conn, "pListEventsByDatesAndUser", params);
	}

	private List<EventBean> executeSP(Connection conn, String spName, List<Object> params) {
		BSmySQL mysql = new BSmySQL();

		LOG.log(Level.FINE, "Parameters for search are: {0}", params);
		List<EventBean> out = new ArrayList<EventBean>();
		ResultSet rs = mysql.callSingleSP(conn, spName, params);

		rsToList(rs, out);

		mysql.closeSQL(rs);
		mysql.closeSQL();

		return out;
	}

	private void rsToList(ResultSet rs, List<EventBean> out) {
		try {
			EventBean eventBean = null;

			while (rs.next()) {
				eventBean = new EventBean();

				eventBean.setEventType(rs.getLong("cEventType"));
				eventBean.setEventTypeName(rs.getString("cEventTypeName"));
				eventBean.setUser(rs.getLong("cUser"));
				eventBean.setUserMail(rs.getString("cUserMail"));
				eventBean.setUserName(rs.getString("cUserName"));
				eventBean.setWhat(replaceEnterByBR(rs.getString("cWhat")));

				eventBean.setWhen(BSDateTimeUtil.timestamp2Calendar(rs.getTimestamp("cWhen")));

				out.add(eventBean);

			}
		} catch (SQLException e) {
			throw new BSDataBaseException(e);
		}

	}

	private String replaceEnterByBR(String str) {
		return str.replaceAll("(\r\n|\n)", "<br/>");
	}

}
