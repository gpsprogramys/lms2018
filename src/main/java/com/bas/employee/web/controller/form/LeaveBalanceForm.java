package com.bas.employee.web.controller.form;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 
 * @author xxxxxxxxx
 *
 */
public class LeaveBalanceForm {

	private String empNo;
	private Date leaveMonth;
	private float sl;
	private float cl;
	private float el;
	private float co;
	private float study;
	private float od;
	private float wv;
	private float sv;
	private Timestamp doe;
	private Timestamp dom;
	private String name;
	
	public float getSl() {
		return sl;
	}

	public void setSl(float sl) {
		this.sl = sl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String description;
	private String designation;
    private String department;
    private String type;
	private String entryType;
	private String modifiedBy;
	

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public Date getLeaveMonth() {
		return leaveMonth;
	}

	public void setLeaveMonth(Date leaveMonth) {
		this.leaveMonth = leaveMonth;
	}

	public float getCl() {
		return cl;
	}

	public void setCl(float cl) {
		this.cl = cl;
	}

	public float getEl() {
		return el;
	}

	public void setEl(float el) {
		this.el = el;
	}

	public float getCo() {
		return co;
	}

	public void setCo(float co) {
		this.co = co;
	}

	public float getStudy() {
		return study;
	}

	public void setStudy(float study) {
		this.study = study;
	}

	public float getOd() {
		return od;
	}

	public void setOd(float od) {
		this.od = od;
	}

	public float getWv() {
		return wv;
	}

	public void setWv(float wv) {
		this.wv = wv;
	}

	public float getSv() {
		return sv;
	}

	public void setSv(float sv) {
		this.sv = sv;
	}

	public Timestamp getDoe() {
		return doe;
	}

	public void setDoe(Timestamp doe) {
		this.doe = doe;
	}

	public Timestamp getDom() {
		return dom;
	}

	public void setDom(Timestamp dom) {
		this.dom = dom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEntryType() {
		return entryType;
	}

	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Override
	public String toString() {
		return "LeaveBalanceForm [empNo=" + empNo + ", leaveMonth="
				+ leaveMonth + ", cl=" + cl + ", el=" + el + ", co=" + co
				+ ", study=" + study + ", od=" + od + ", wv=" + wv + ", sv="
				+ sv + ", doe=" + doe + ", dom=" + dom + ", description="
				+ description + ", designation=" + designation
				+ ", department=" + department + ", type=" + type
				+ ", entryType=" + entryType + ", modifiedBy=" + modifiedBy
				+ "]";
	}
}
