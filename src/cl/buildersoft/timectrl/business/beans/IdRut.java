package cl.buildersoft.timectrl.business.beans;

public class IdRut {
	private Integer id = null;
	private String key = null;
	private String rut = null;
	private String name = null;
	private String costCenter = null;
	private String username = null;
	private String mail = null;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRut() {
		return rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "IdRut [id=" + id + ", key=" + key + ", rut=" + rut + ", name=" + name + ", costCenter=" + costCenter
				+ ", username=" + username + "]";
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
}
