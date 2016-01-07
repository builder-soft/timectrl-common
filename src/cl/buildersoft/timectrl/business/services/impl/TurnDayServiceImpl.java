package cl.buildersoft.timectrl.business.services.impl;

import java.sql.Connection;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.framework.database.BSmySQL;
import cl.buildersoft.framework.util.BSDateTimeUtil;
import cl.buildersoft.framework.util.BSUtils;
import cl.buildersoft.timectrl.business.beans.TurnDay;
import cl.buildersoft.timectrl.business.services.TurnDayService;

public class TurnDayServiceImpl implements TurnDayService {
	private static final Logger LOG = Logger.getLogger(TurnDayServiceImpl.class.getName());
	Map<Long, TurnDay> turnDayList = new HashMap<Long, TurnDay>();

	public TurnDayServiceImpl(Connection conn) {
		BSBeanUtils bu = new BSBeanUtils();
		@SuppressWarnings("unchecked")
		List<TurnDay> turnDayList = (List<TurnDay>) bu.listAll(conn, new TurnDay());
		for (TurnDay td : turnDayList) {
			this.turnDayList.put(td.getId(), td);
		}
	}

	@Override
	public TurnDay markAndUserToTurnDayId(Connection conn, Calendar markTime, Long employeeId, Integer tolerance, Boolean flexible) {
		BSmySQL mysql = new BSmySQL();
		TurnDay out = null;
		LOG.log(Level.FINEST, "markAndUserToTurnDayId: {0}", BSDateTimeUtil.calendar2String(markTime, "yyyy-MM-dd HH:mm:ss.S"));

		List<Object> params = BSUtils.array2List(markTime, employeeId, tolerance, flexible);

		String id = mysql.callFunction(conn, "fMarkAndUserToTurnDayId4", params);

		mysql.closeSQL();
		if (id != null) {
			out = new TurnDay();
			BSBeanUtils bu = new BSBeanUtils();
			out.setId(Long.parseLong(id));

			if (!bu.search(conn, out)) {
				out = null;
			}
		}
		return out;
	}

	@Override
	public Boolean isBusinessDay(TurnDay turnDay) {
		Boolean out = null;
		if (turnDay == null) {
			out = false;
		} else {
			out = this.turnDayList.get(turnDay.getId()).getBusinessDay();
		}
		return out;
	}

}
