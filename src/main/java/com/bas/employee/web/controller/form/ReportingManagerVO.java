package com.bas.employee.web.controller.form;

public class ReportingManagerVO {

	private long id;
	private String name;
	private String designation;
	private String department;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
		return "ReportingManagerVO [id=" + id + ", name=" + name
				+ ", designation=" + designation + ", department=" + department
				+ "]";
	}

}
