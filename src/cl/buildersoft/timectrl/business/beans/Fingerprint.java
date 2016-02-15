package cl.buildersoft.timectrl.business.beans;

import cl.buildersoft.framework.beans.BSBean;

public class Fingerprint extends BSBean {
	private static final long serialVersionUID = 6729666154838467937L;
	@SuppressWarnings("unused")
	private String TABLE = "tFingerprint";

	private Long employee = null;
	private String fingerprint = null;
	private Integer flag = null;
	private Integer fingerIndex = null;
	private String cardNumber = null;

	public Long getEmployee() {
		return employee;
	}

	public void setEmployee(Long employee) {
		this.employee = employee;
	}

	public String getFingerprint() {
		return fingerprint;
	}

	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getFingerIndex() {
		return fingerIndex;
	}

	public void setFingerIndex(Integer fingerIndex) {
		this.fingerIndex = fingerIndex;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	@Override
	public String toString() {
		return "FingerPrint {Id=" + getId() + ", employee=" + employee + ", fingerprint=" + fingerprint + ", flag=" + flag
				+ ", fingerIndex=" + fingerIndex + ", cardNumber=" + cardNumber + "}";
	}

}
