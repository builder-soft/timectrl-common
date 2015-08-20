package cl.buildersoft.timectrl.business.beans;

import cl.buildersoft.framework.beans.BSBean;

public class ReportProperty extends BSBean {
	private static final long serialVersionUID = -1938952831898711663L;
	@SuppressWarnings("unused")
	private String TABLE = "tReportProperty";

	private Long propertyType = null;
	private Long report = null;
	private String value = null;

	public Long getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(Long propertyType) {
		this.propertyType = propertyType;
	}

	public Long getReport() {
		return report;
	}

	public void setReport(Long report) {
		this.report = report;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ReportProperty [propertyType=" + propertyType + ", report=" + report + ", value=" + value + ", getId()="
				+ getId() + "]";
	}

}
