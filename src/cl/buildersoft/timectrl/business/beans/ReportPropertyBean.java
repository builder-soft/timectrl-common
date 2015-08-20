package cl.buildersoft.timectrl.business.beans;

public class ReportPropertyBean {
	private Long propertyId = null;
	private Long propertyType = null;
	private Long propertyReport = null;
	private String propertyValue = null;
	private Long propertyTypeId = null;
	private String propertyTypeKey = null;
	private String propertyTypeName = null;

	public Long getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(Long propertyId) {
		this.propertyId = propertyId;
	}

	public Long getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(Long propertyType) {
		this.propertyType = propertyType;
	}

	public Long getPropertyReport() {
		return propertyReport;
	}

	public void setPropertyReport(Long propertyReport) {
		this.propertyReport = propertyReport;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public Long getPropertyTypeId() {
		return propertyTypeId;
	}

	public void setPropertyTypeId(Long propertyTypeId) {
		this.propertyTypeId = propertyTypeId;
	}

	public String getPropertyTypeKey() {
		return propertyTypeKey;
	}

	public void setPropertyTypeKey(String propertyTypeKey) {
		this.propertyTypeKey = propertyTypeKey;
	}

	public String getPropertyTypeName() {
		return propertyTypeName;
	}

	public void setPropertyTypeName(String propertyTypeName) {
		this.propertyTypeName = propertyTypeName;
	}

	@Override
	public String toString() {
		return "ReportPropertyBean [propertyId=" + propertyId + ", propertyType=" + propertyType + ", propertyReport="
				+ propertyReport + ", propertyValue=" + propertyValue + ", propertyTypeId=" + propertyTypeId
				+ ", propertyTypeKey=" + propertyTypeKey + ", propertyTypeName=" + propertyTypeName + "]";
	}

}
