package cl.buildersoft.timectrl.business.services.impl;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.timectrl.business.beans.Privilege;
import cl.buildersoft.timectrl.business.services.PrivilegeService;

public class PrivilegeServiceImpl implements PrivilegeService {
	private Map<Integer, Privilege> orderByKey = null;
	private Map<Long, Privilege> orderById = null;

	@Override
	public Privilege searchById(Connection conn, Long id) {
		BSBeanUtils bu = new BSBeanUtils();
		Privilege out = null;

		if (this.orderById == null) {
			List<Privilege> list = listAllPrivilege(conn, bu);
			this.orderById = new HashMap<Long, Privilege>();
			for (Privilege privilege : list) {
				this.orderById.put(privilege.getId(), privilege);
			}
		}
		out = this.orderById.get(id);

		/**
		 * <code>
		Privilege privilege = new Privilege();

		privilege.setId(id);
		if (bu.search(conn, privilege)) {
			out = privilege;
		}
		</code>
		 */

		return out;
	}

	private List<Privilege> listAllPrivilege(Connection conn, BSBeanUtils bu) {
		@SuppressWarnings("unchecked")
		List<Privilege> list = (List<Privilege>) bu.listAll(conn, new Privilege());
		return list;
	}

	@Override
	public Privilege searchByKey(Connection conn, Integer key) {
		BSBeanUtils bu = new BSBeanUtils();
		Privilege out = null;

		if (this.orderByKey == null) {
			List<Privilege> list = listAllPrivilege(conn, bu);
			this.orderByKey = new HashMap<Integer, Privilege>();
			for (Privilege privilege : list) {
				this.orderByKey.put(privilege.getKey(), privilege);
			}
		}
		out = this.orderByKey.get(key);
		/**
		 * <code>
		BSBeanUtils bu = new BSBeanUtils();
		Privilege out = null;
		Privilege privilege = new Privilege();

		if (bu.search(conn, privilege, "cKey=?", key)) {
			out = privilege;
		}
</code>
		 */
		return out;
	}

}
