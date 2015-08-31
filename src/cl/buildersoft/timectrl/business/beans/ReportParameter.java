package cl.buildersoft.timectrl.business.beans;

import cl.buildersoft.framework.beans.BSBean;

public class ReportParameter extends BSBean {
	private static final long serialVersionUID = -4771733332204632893L;

	@SuppressWarnings("unused")
	private String TABLE = "tReportParameter";

	private Long report = null;
	private String name = null;
	private String label = null;
	private Long type = null;
	private Integer order = null;
	public Long getReport() {
		return report;
	}
	public void setReport(Long report) {
		this.report = report;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}

}
