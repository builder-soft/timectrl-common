package cl.buildersoft.timectrl.business.beans;

import cl.buildersoft.framework.beans.BSBean;

public class Turn extends BSBean {
	private static final long serialVersionUID = 9171367573577263234L;
	@SuppressWarnings("unused")
	private String TABLE = "tTurn";
	private String name = null;
	private Boolean flexible = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getFlexible() {
		return flexible;
	}

	public void setFlexible(Boolean flexible) {
		this.flexible = flexible;
	}

}
