package com.bas.common.dao.entity;

/**
 * 
 * @author Nitin C
 *
 */
public class SuggestionManagerOptionEntity {
	private String empid;
	private String name;
	private String designation;
	private String department;
	//private byte[] picture;


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
				+ ", designation=" + designation + ", department=" + department + "]";
	}
}
