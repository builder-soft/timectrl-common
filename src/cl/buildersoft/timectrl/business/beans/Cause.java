package cl.buildersoft.timectrl.business.beans;

import cl.buildersoft.framework.beans.BSBean;

public class Cause extends BSBean {
	private static final long serialVersionUID = -6539584620758826622L;
	@SuppressWarnings("unused")
	private String TABLE = "tLicenseCause";
	private String name = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
