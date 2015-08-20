package cl.buildersoft.timectrl.business.beans;

import cl.buildersoft.framework.beans.BSBean;

public class ReportParamType extends BSBean {
	private static final long serialVersionUID = 6848729776582794355L;
	@SuppressWarnings("unused")
	private String TABLE = "tReportParamType";
	private String key = null;
	private String name = null;
	private String htmlFile = null;
	private String source = null;

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

	public String getHtmlFile() {
		return htmlFile;
	}

	public void setHtmlFile(String htmlFile) {
		this.htmlFile = htmlFile;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Override
	public String toString() {
		return "ReportParamType [key=" + key + ", name=" + name + ", htmlFile=" + htmlFile + ", source=" + source + ", getId()="
				+ getId() + "]";
	}

}
