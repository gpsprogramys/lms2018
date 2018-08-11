package com.bas.admin.dao.entity;

import java.util.Date;
/**
 * 
 * @author Amogh
 *
 */

public class EmployeeTypeEntity {
	private int employeeTypeId;
	private String employeeTypeName;
	private String description;
	private Date doe;
	private Date dom;
	private String entryBy;
	public int getEmployeeTypeId() {
		return employeeTypeId;
	}
	public void setEmployeeTypeId(int employeeTypeId) {
		this.employeeTypeId = employeeTypeId;
	}
	public String getEmployeeTypeName() {
		return employeeTypeName;
	}
	public void setEmployeeTypeName(String employeeTypeName) {
		this.employeeTypeName = employeeTypeName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDoe() {
		return doe;
	}
	public void setDoe(Date doe) {
		this.doe = doe;
	}
	public Date getDom() {
		return dom;
	}
	public void setDom(Date dom) {
		this.dom = dom;
	}
	public String getEntryBy() {
		return entryBy;
	}
	public void setEntryBy(String entryBy) {
		this.entryBy = entryBy;
	}
	@Override
	public String toString() {
		return "EmployeeTypeEntity [employeeTypeId=" + employeeTypeId
				+ ", employeeTypeName=" + employeeTypeName + ", description="
				+ description + ", doe=" + doe + ", dom=" + dom + ", entryBy="
				+ entryBy + "]";
	}
		
}
