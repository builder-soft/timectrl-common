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
			throw new BSConfigurationException("Event type not found, it was " + eventTypeKey);
		}

		event.setEventType(eventType.getId());
		event.setWhen(Calendar.getInstance());
		event.setUser(user);
		event.setWhat(String.format(what, params));

		bu.insert(conn, event);

	}

	@Override
	public List<EventBean> list(Connection conn, Calendar startDate, Calendar endDate) {
		BSmySQL mysql = new BSmySQL();

		List<Object> params = BSUtils.array2List(BSDateTimeUtil.calendar2Date(startDate), BSDateTimeUtil.calendar2Date(endDate));
		LOG.log(Level.FINE, "Parameters for search are: {0}", params);
		List<EventBean> out = new ArrayList<EventBean>();
		ResultSet rs = mysql.callSingleSP(conn, "pListEventsByDates", params);

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
				eventBean.setWhat(rs.getString("cWhat"));

			
				eventBean.setWhen(BSDateTimeUtil.timestamp2Calendar(rs.getTimestamp("cWhen")));

				out.add(eventBean);
 
			}
		} catch (SQLException e) {
			throw new BSDataBaseException(e);
		}

	}

	@Override
	public List<EventBean> list(Connection conn, Calendar startDate, Calendar endDate, Long eventType, Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EventBean> listByEventType(Connection conn, Calendar startDate, Calendar endDate, Long eventType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EventBean> listByUser(Connection conn, Calendar startDate, Calendar endDate, Long user) {
		// TODO Auto-generated method stub
		return null;
	}

}
