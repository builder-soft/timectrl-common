package cl.buildersoft.timectrl.business.beans;

import java.util.Date;

import cl.buildersoft.framework.beans.BSBean;

public class File extends BSBean {
	private static final long serialVersionUID = 6724771737264269906L;
	@SuppressWarnings("unused")
	private String TABLE = "tFile";
	private String fileName = null;
	private Date dateTime = null;
	private Long size = null;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

}
