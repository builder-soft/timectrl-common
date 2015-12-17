package cl.buildersoft.timectrl.business.beans;

public class ReportParameterBean implements Cloneable {
	private Long id = null;
	private Long report = null;
	private String name = null;
	private String label = null;
	private Integer order = null;
	private Long typeId = null;
	private String typeKey = null;
	private String typeName = null;
	private String htmlFile = null;
	private String typeSource = null;
	private String value = null;
	private String javaType = null;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getTypeKey() {
		return typeKey;
	}

	public void setTypeKey(String typeKey) {
		this.typeKey = typeKey;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getHtmlFile() {
		return htmlFile;
	}

	public void setHtmlFile(String htmlFile) {
		this.htmlFile = htmlFile;
	}

	public String getTypeSource() {
		return typeSource;
	}

	public void setTypeSource(String source) {
		this.typeSource = source;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ReportParameterBean [id=" + id + ", report=" + report + ", name=" + name + ", label=" + label + ", order="
				+ order + ", typeId=" + typeId + ", typeKey=" + typeKey + ", typeName=" + typeName + ", htmlFile=" + htmlFile
				+ ", typeSource=" + typeSource + ", value=" + value + "]";
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		ReportParameterBean out = new ReportParameterBean();
		out.setId(getId());
		out.setReport(getReport());
		out.setName(getName());
		out.setLabel(getLabel());
		out.setOrder(getOrder());
		out.setTypeId(getTypeId());
		out.setTypeKey(getTypeKey());
		out.setTypeName(getTypeName());
		out.setHtmlFile(getHtmlFile());
		out.setTypeSource(getTypeSource());
		out.setValue(getValue());
		out.setJavaType(getJavaType());

		return super.clone();
	}

}
