package cl.buildersoft.timectrl.business.beans;

import cl.buildersoft.framework.beans.BSBean;

public class Report extends BSBean {
	private static final long serialVersionUID = 3117440700912474308L;
	@SuppressWarnings("unused")
	private String TABLE = "tReport";
	private String key = null;
	private String name = null;
	private Long type = null;
	private String javaClass = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getJavaClass() {
		return javaClass;
	}

	public void setJavaClass(String javaClass) {
		this.javaClass = javaClass;
	}

	@Override
	public String toString() {
		return "Report [key=" + key + ", name=" + name + ", type=" + type + ", javaClass=" + javaClass + ", Id=" + getId() + "]";
	}

}
