package cl.buildersoft.timectrl.business.beans;

import cl.buildersoft.framework.beans.BSBean;

public class Employee extends BSBean {
	private static final long serialVersionUID = 5015716040873333572L;
	@SuppressWarnings("unused")
	private String TABLE = "tEmployee";
	private String key = null;
	private String rut = null;
	private String name = null;
	/**
	 * private String lastName1 = null; private String lastName2 = null;
	 */
	private Long post = null;
	private Long area = null;
	private Long privilege = null;
	private Boolean enabled = null;

	/**
	 * private String fingerPrint = null; private Integer flag = null; private
	 * Integer fingerIndex = null; private String cardNumber = null;
	 */

	private Long group = null;
	private String mail = null;

	/**
	 * <code>
	private Date birthDate = null;
	private String address = null;
	private Long genere = null;
	private Long comuna = null;
	private Long country = null;
	private String phone = null;
	private Long maritalStatus = null;
	private String movil = null;
	</code>
	 */
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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

	public Long getPost() {
		return post;
	}

	public void setPost(Long post) {
		this.post = post;
	}

	public Long getArea() {
		return area;
	}

	public void setArea(Long area) {
		this.area = area;
	}

	public Long getPrivilege() {
		return privilege;
	}

	public void setPrivilege(Long privilege) {
		this.privilege = privilege;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Long getGroup() {
		return group;
	}

	public void setGroup(Long group) {
		this.group = group;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	@Override
	public String toString() {
		return "Employee [Id=" + getId() + ", key=" + key + ", rut=" + rut + ", name=" + name + ", enabled=" + enabled + "]";
	}

}
