package cl.buildersoft.timectrl.business.beans;

import cl.buildersoft.framework.beans.BSBean;

public class ReportType extends BSBean {
	private static final long serialVersionUID = 925612356427887115L;
	private String TABLE = "tReportType";
	private String key = null;
	private String name = null;
	private String javaClass = null;

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

	public String getJavaClass() {
		return javaClass;
	}

	public void setJavaClass(String javaClass) {
		this.javaClass = javaClass;
	}

	@Override
	public String toString() {
		return "ReportType [TABLE=" + TABLE + ", key=" + key + ", name=" + name + ", javaClass=" + javaClass + ", getId()="
				+ getId() + "]";
	}

}
