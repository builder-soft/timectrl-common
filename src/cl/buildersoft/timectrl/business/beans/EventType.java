package cl.buildersoft.timectrl.business.beans;

import cl.buildersoft.framework.beans.BSBean;

public class EventType extends BSBean {

	private static final long serialVersionUID = -1452373027003766340L;

	private String TABLE = "tEventType";

	private String key = null;
	private String name = null;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "EventType [Id=" + getId() + ", key=" + key + ", name=" + name + "]";
	}

}
