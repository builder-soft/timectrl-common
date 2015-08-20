package cl.buildersoft.timectrl.business.beans;

import cl.buildersoft.framework.beans.BSBean;

public class Group extends BSBean {
	private static final long serialVersionUID = -2735995661853498150L;

	@SuppressWarnings("unused")
	private String TABLE = "tGroup";

	private Integer key = null;
	private String name = null;

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
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
		return "Group [key=" + key + ", name=" + name + ", getId()=" + getId() + "]";
	}
}
