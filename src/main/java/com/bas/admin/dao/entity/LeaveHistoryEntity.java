package com.bas.admin.dao.entity;

public class LeaveHistoryEntity {
	private int empid;
	private String name;
	private String department;
	private String designation;
	private int leavesWithoutPay; // number of leaves without pay
	private int noApprovedLeaves;
	private int noHolidays;
	private int daysWorked; // number of days worked
	private byte[] photograph;

	public int getEmpid() {
		return empid;
	}

	public void setEmpid(int empid) {
		this.empid = empid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public int getLeavesWithoutPay() {
		return leavesWithoutPay;
	}

	public void setLeavesWithoutPay(int leavesWithoutPay) {
		this.leavesWithoutPay = leavesWithoutPay;
	}

	public int getNoApprovedLeaves() {
		return noApprovedLeaves;
	}

	public void setNoApprovedLeaves(int noApprovedLeaves) {
		this.noApprovedLeaves = noApprovedLeaves;
	}

	public int getNoHolidays() {
		return noHolidays;
	}

	public void setNoHolidays(int noHolidays) {
		this.noHolidays = noHolidays;
	}

	public int getDaysWorked() {
		return daysWorked;
	}

	public void setDaysWorked(int daysWorked) {
		this.daysWorked = daysWorked;
	}

	public byte[] getPhotograph() {
		return photograph;
	}

	public void setPhotograph(byte[] photograph) {
		this.photograph = photograph;
	}

	@Override
	public String toString() {
		return "LeaveHistoryEntity [empid=" + empid + ", name=" + name
				+ ", department=" + department + ", designation=" + designation
				+ ", leavesWithoutPay=" + leavesWithoutPay
				+ ", noApprovedLeaves=" + noApprovedLeaves + ", noHolidays="
				+ noHolidays + ", daysWorked=" + daysWorked + "]";
	}

}
