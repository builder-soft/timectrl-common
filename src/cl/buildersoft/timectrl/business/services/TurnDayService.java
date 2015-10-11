package cl.buildersoft.timectrl.business.services;

import java.sql.Connection;
import java.util.Calendar;

import cl.buildersoft.timectrl.business.beans.TurnDay;

public interface TurnDayService {
	/**
	 * <code>
	 CREATE FUNCTION fMarkAndUserToTurnDayId4(vMarkTime TIMESTAMP, vEmployeeId BIGINT(20), 
						vTolerance INTEGER, vFlexible BOOLEAN) RETURNS BIGINT(20)
</code>
	 */
	public TurnDay markAndUserToTurnDayId(Connection conn, Calendar markTime, Long employeeId, Integer tolerance, Boolean flexible);

}
