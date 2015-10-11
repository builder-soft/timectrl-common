package cl.buildersoft.timectrl.business.services.impl;

import java.sql.Connection;
import java.util.Calendar;

import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.framework.database.BSmySQL;
import cl.buildersoft.framework.util.BSUtils;
import cl.buildersoft.timectrl.business.beans.TurnDay;
import cl.buildersoft.timectrl.business.services.TurnDayService;

public class TurnDayServiceImpl implements TurnDayService {

	@Override
	public TurnDay markAndUserToTurnDayId(Connection conn, Calendar markTime, Long employeeId, Integer tolerance, Boolean flexible) {
		BSmySQL mysql = new BSmySQL();
		// CREATE FUNCTION fMarkAndUserToTurnDayId4(vMarkTime TIMESTAMP,
		// vEmployeeId BIGINT(20),
		// vTolerance INTEGER, vFlexible BOOLEAN) RETURNS BIGINT(20)
		TurnDay out = null;

		String id = mysql.callFunction(conn, "fMarkAndUserToTurnDayId4",
				BSUtils.array2List(markTime, employeeId, tolerance, flexible));
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

}
