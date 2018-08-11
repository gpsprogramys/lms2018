package com.bas.common.dao.entity;

/**
 * 
 * @author xxxxxxxxx
 *
 */
public class SuggestionOptionEntity {
	private String empid;
	private String name;
	private String designation;
	private String department;
	//by Mayur
	private String email;
	private String phoneNumber;

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmpid() {
		return empid;
	}

	public void setEmpid(String empid) {
		this.empid = empid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "SuggestionOptionEntity [empid=" + empid + ", name=" + name
				+ ", designation=" + designation + ", department=" + department
				+ "]";
	}
}