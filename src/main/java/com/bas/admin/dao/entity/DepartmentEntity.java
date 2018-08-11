package com.bas.admin.dao.entity;

import java.util.Date;

/**
 * 
 * @author nagendra.yadav
 * 
 */
public class DepartmentEntity {

	private int departmentId;
	private String departmentName;
	private String departmentShortName;
	private String description;
	private Date doe;
	private Date dom;
	private String entryBy;

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentShortName() {
		return departmentShortName;
	}

	public void setDepartmentShortName(String departmentShortName) {
		this.departmentShortName = departmentShortName;
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
		return "DepartmentEntity [departmentId=" + departmentId
				+ ", departmentName=" + departmentName
				+ ", departmentShortName=" + departmentShortName
				+ ", description=" + description + ", doe=" + doe + ", dom="
				+ dom + ", entryBy=" + entryBy + "]";
	}

}
