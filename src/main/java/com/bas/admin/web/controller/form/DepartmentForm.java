package com.bas.admin.web.controller.form;

import java.util.Date;

/**
 * 
 * @author nagendra.yadav
 * 
 */
public class DepartmentForm {

	private int departmentId;
	private String departmentName;
	private String departmentShortName;
	private String description;
	private Date doe;
	private Date dom;
	private String sdoe;
	private String sdom;
	private String entryBy;

	public String getSdoe() {
		return sdoe;
	}

	public void setSdoe(String sdoe) {
		this.sdoe = sdoe;
	}

	public String getSdom() {
		return sdom;
	}

	public void setSdom(String sdom) {
		this.sdom = sdom;
	}

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
		return "DepartmentForm [departmentId=" + departmentId + ", departmentName=" + departmentName
				+ ", departmentShortName=" + departmentShortName + ", description=" + description + ", doe=" + doe
				+ ", dom=" + dom + ", sdoe=" + sdoe + ", sdom=" + sdom + ", entryBy=" + entryBy + "]";
	}

}
