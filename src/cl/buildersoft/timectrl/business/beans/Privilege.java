package cl.buildersoft.timectrl.business.beans;

import cl.buildersoft.framework.beans.BSBean;

public class Privilege extends BSBean {
	private static final long serialVersionUID = 8174974744831164038L;
	@SuppressWarnings("unused")
	private String TABLE = "tPrivilege";

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
}
