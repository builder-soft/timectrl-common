package cl.buildersoft.timectrl.business.beans;

import cl.buildersoft.framework.beans.BSBean;

public class MarkType extends BSBean {
	private static final long serialVersionUID = -8653715320608062434L;
	@SuppressWarnings("unused")
	private String TABLE = "tMarkType";
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

}
