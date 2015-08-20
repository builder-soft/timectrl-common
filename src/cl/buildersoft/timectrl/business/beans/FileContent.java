package cl.buildersoft.timectrl.business.beans;

import cl.buildersoft.framework.beans.BSBean;

public class FileContent extends BSBean {
	private static final long serialVersionUID = 3438021530294669457L;
	@SuppressWarnings("unused")
	private String TABLE = "tFileContent";
	private Long file=null;
	private String line=null;
	private Integer order=null;
	
	public Long getFile() {
		return file;
	}
	public void setFile(Long file) {
		this.file = file;
	}
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	@Override
	public String toString() {
		return "FileContent [file=" + file + ", line=" + line + ", order=" + order + "]";
	}
	
}
