package cl.buildersoft.timectrl.business.beans;

import cl.buildersoft.framework.beans.BSBean;

public class Area extends BSBean {
	private static final long serialVersionUID = 1001461489903596983L;
	@SuppressWarnings("unused")
	private String TABLE = "tArea";
	private String key = null;
	private String name = null;
	private String costCenter = null;

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

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
}
