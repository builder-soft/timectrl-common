package cl.buildersoft.timectrl.business.services.impl;

import cl.buildersoft.timectrl.business.beans.Employee;
import cl.buildersoft.timectrl.business.beans.Fingerprint;

public class EmployeeAndFingerprint {

	private Employee employee = null;
	private	Fingerprint fingerprint = null;

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Fingerprint getFingerprint() {
		return fingerprint;
	}

	public void setFingerprint(Fingerprint fingerprint) {
		this.fingerprint = fingerprint;
	}

	@Override
	public String toString() {
		return "EmployeeAndFingerprint [employeeId=" + employee.getId() + ", Name=" + employee.getName() + ", Key="
				+ employee.getKey() + ", Fingerprint.employee=" + fingerprint.getEmployee() + ", fingerprint.id=" + fingerprint.getId() + "]";
	}
}
