package com.bas.admin.web.controller.form;

import java.util.List;

public class EmployeeMonthAttendanceVO {
	private String name;
	private String eid;
	private String designation;
	private float totalDaysWorked;
	private String department;
	private String month;
	private String cdate;
	private List<String> dayStatusList;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public float getTotalDaysWorked() {
		return totalDaysWorked;
	}
	public void setTotalDaysWorked(float totalDaysWorked) {
		this.totalDaysWorked = totalDaysWorked;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getCdate() {
		return cdate;
	}
	public void setCdate(String cdate) {
		this.cdate = cdate;
	}
	public List<String> getDayStatusList() {
		return dayStatusList;
	}
	public void setDayStatusList(List<String> dayStatusList) {
		this.dayStatusList = dayStatusList;
	}
	@Override
	public String toString() {
		return "EmployeeMonthAttendanceVO [name=" + name + ", eid=" + eid + ", designation=" + designation
				+ ", totalDaysWorked=" + totalDaysWorked + ", department=" + department + ", month=" + month
				+ ", cdate=" + cdate + ", dayStatusList=" + dayStatusList + "]";
	}
}
