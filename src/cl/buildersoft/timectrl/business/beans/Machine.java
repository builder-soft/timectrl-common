package cl.buildersoft.timectrl.business.beans;

import java.sql.Timestamp;

import cl.buildersoft.framework.beans.BSBean;

public class Machine extends BSBean {
	private static final long serialVersionUID = -4369287066058412163L;
	@SuppressWarnings("unused")
	private String TABLE = "tMachine";
	private String name = null;
	private String ip = null;
	private Integer port = null;
	private Timestamp lastAccess = null;
	private String serial = null;
	private Long group = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Timestamp getLastAccess() {
		return lastAccess;
	}

	public void setLastAccess(Timestamp lastAccess) {
		this.lastAccess = lastAccess;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	@Override
	public int hashCode() {
		return super.getId().hashCode();
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public Long getGroup() {
		return group;
	}

	public void setGroup(Long group) {
		this.group = group;
	}

	@Override
	public String toString() {
		return "Machine [Id=" + getId() + ", IP=" + ip + ", port=" + port + ", name=" + name + ", lastAccess=" + lastAccess
				+ ", serial=" + serial + ", group=" + group + "]";
	}

}
