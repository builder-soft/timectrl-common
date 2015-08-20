package cl.buildersoft.timectrl.business.beans;

import cl.buildersoft.framework.beans.BSBean;

public class LicenseCause extends BSBean {
	private static final long serialVersionUID = 3667045134018515982L;
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
