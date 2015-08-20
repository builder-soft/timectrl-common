package cl.buildersoft.timectrl.business.beans;

import cl.buildersoft.framework.beans.BSBean;

public class ReportPropertyType extends BSBean {
	private static final long serialVersionUID = 6985317558117420817L;
	@SuppressWarnings("unused")
	private String TABLE = "tReportPropertyType";
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
		return "ReportPropertyType [key=" + key + ", name=" + name + ", getId()=" + getId() + "]";
	}

}
