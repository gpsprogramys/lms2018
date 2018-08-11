package com.bas.common.web.controller.form;

public class EmployeeLeaveDetailVO {
	private String eid;
	private String name;
	private String type;
	private String designation;
	private String department;
	private String paddress;
	private String mobile;
	private String cl;
	private String el;
	private String sl;
	private String slDate;
	private String od;
	private String managerId;
	private String managerName;
	private String hrid;
	private String doj;
	private String month;
	private String pel;
	
	
	
	public String getHrid() {
		return hrid;
	}
	public void setHrid(String hrid) {
		this.hrid = hrid;
	}
	public String getPel() {
		return pel;
	}
	public void setPel(String pel) {
		this.pel = pel;
	}
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getPaddress() {
		return paddress;
	}
	public void setPaddress(String paddress) {
		this.paddress = paddress;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCl() {
		return cl;
	}
	public void setCl(String cl) {
		this.cl = cl;
	}
	public String getEl() {
		return el;
	}
	public void setEl(String el) {
		this.el = el;
	}
	public String getSl() {
		return sl;
	}
	public void setSl(String sl) {
		this.sl = sl;
	}
	public String getSlDate() {
		return slDate;
	}
	public void setSlDate(String slDate) {
		this.slDate = slDate;
	}
	public String getOd() {
		return od;
	}
	public void setOd(String od) {
		this.od = od;
	}
	public String getManagerId() {
		return managerId;
	}
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getDoj() {
		return doj;
	}
	public void setDoj(String doj) {
		this.doj = doj;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	@Override
	public String toString() {
		return "EmployeeLeaveDetailVO [eid=" + eid + ", name=" + name
				+ ", type=" + type + ", designation=" + designation
				+ ", department=" + department + ", paddress=" + paddress
				+ ", mobile=" + mobile + ", cl=" + cl + ", el=" + el + ", sl="
				+ sl + ", slDate=" + slDate + ", od=" + od + ", managerId="
				+ managerId + ", managerName=" + managerName + ", doj=" + doj
				+ ", month=" + month + ", pel=" + pel + "]";
	}
	

}
