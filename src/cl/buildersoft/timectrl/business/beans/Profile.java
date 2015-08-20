package cl.buildersoft.timectrl.business.beans;

import cl.buildersoft.framework.beans.BSBean;

public class Profile extends BSBean {
	private static final long serialVersionUID = -7720519574923823260L;
	private String TABLE = "tProfile";
	private String name = null;
	private Long costCenter = null;
	private Double companyCost = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(Long costCenter) {
		this.costCenter = costCenter;
	}

	public Double getCompanyCost() {
		return companyCost;
	}

	public void setCompanyCost(Double companyCost) {
		this.companyCost = companyCost;
	}

	@Override
	public String toString() {
		return "Profile [name=" + name + ", costCenter=" + costCenter
				+ ", companyCost=" + companyCost + "]";
	}

}
