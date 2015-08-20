package cl.buildersoft.timectrl.business.services;

import java.sql.Connection;

import cl.buildersoft.timectrl.business.beans.Privilege;

public interface PrivilegeService {
	public Privilege searchById(Connection conn, Long id);

	public Privilege searchByKey(Connection conn, Integer key);
}
