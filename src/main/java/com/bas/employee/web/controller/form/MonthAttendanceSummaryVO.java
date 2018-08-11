package com.bas.employee.web.controller.form;

import java.util.List;

public class MonthAttendanceSummaryVO {
	private String eid;
	private int totalNoOfDays;
	private String name;
	private String designation;
	private String department;
	private float totalDaysWorked;
	private String selectedDateMonth;
	private List<String> leaveDatesList;
	
	public String getSelectedDateMonth() {
		return selectedDateMonth;
	}
	public void setSelectedDateMonth(String selectedDateMonth) {
		this.selectedDateMonth = selectedDateMonth;
	}
	
	
	public int getTotalNoOfDays() {
		return totalNoOfDays;
	}
	public void setTotalNoOfDays(int totalNoOfDays) {
		this.totalNoOfDays = totalNoOfDays;
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
	public float getTotalDaysWorked() {
		return totalDaysWorked;
	}
	public void setTotalDaysWorked(float totalDaysWorked) {
		this.totalDaysWorked = totalDaysWorked;
	}
	public List<String> getLeaveDatesList() {
		return leaveDatesList;
	}
	public void setLeaveDatesList(List<String> leaveDatesList) {
		this.leaveDatesList = leaveDatesList;
	}
	@Override
	public String toString() {
		return "MonthAttendanceSummaryVO [eid=" + eid + ", totalNoOfDays=" + totalNoOfDays + ", name=" + name
				+ ", designation=" + designation + ", department=" + department + ", totalDaysWorked=" + totalDaysWorked
				+ ", selectedDateMonth=" + selectedDateMonth + ", leaveDatesList=" + leaveDatesList + "]";
	}
	
	
	
}
