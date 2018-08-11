package com.bas.employee.web.controller.form;

import java.util.List;

public class EmployeeMonthAttendanceStatusVO {
	private String name;
	private String eid;
	private String designation;
	private int totalDays;
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
	public int getTotalDays() {
		return totalDays;
	}
	public void setTotalDays(int totalDays) {
		this.totalDays = totalDays;
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
		return "EmployeeMonthAttendanceStatusVO [name=" + name + ", eid=" + eid + ", designation=" + designation
				+ ", totalDays=" + totalDays + ", department=" + department + ", month=" + month + ", cdate=" + cdate
				+ ", dayStatusList=" + dayStatusList + "]";
	}

}
