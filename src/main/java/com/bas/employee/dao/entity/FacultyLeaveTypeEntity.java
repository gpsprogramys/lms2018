package com.bas.employee.dao.entity;

import java.util.Date;

public class FacultyLeaveTypeEntity {

	private String requestId;
	private int empId;
	private String empName;
	private Date leaveFrom;
	private Date leaveTo;
	private String deptt;
	private String leaveType;
	private String lStatus;
	private String leaveDates;
	private float lwp;
	private String inAccount;
	private float totalDays;

	public int getEmpId() {
		return empId;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public Date getLeaveFrom() {
		return leaveFrom;
	}
	public void setLeaveFrom(Date leaveFrom) {
		this.leaveFrom = leaveFrom;
	}
	public Date getLeaveTo() {
		return leaveTo;
	}
	public void setLeaveTo(Date leaveTo) {
		this.leaveTo = leaveTo;
	}
	public String getDeptt() {
		return deptt;
	}
	public void setDeptt(String deptt) {
		this.deptt = deptt;
	}
	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	public String getlStatus() {
		return lStatus;
	}
	public void setlStatus(String lStatus) {
		this.lStatus = lStatus;
	}
	public String getLeaveDates() {
		return leaveDates;
	}
	public void setLeaveDates(String leaveDates) {
		this.leaveDates = leaveDates;
	}
	public float getLwp() {
		return lwp;
	}
	public void setLwp(float lwp) {
		this.lwp = lwp;
	}
	public String getInAccount() {
		return inAccount;
	}
	public void setInAccount(String inAccount) {
		this.inAccount = inAccount;
	}
	public float getTotalDays() {
		return totalDays;
	}
	public void setTotalDays(float totalDays) {
		this.totalDays = totalDays;
	}
	@Override
	public String toString() {
		return "FacultyLeaveTypeEntity [requestId=" + requestId + ", empId=" + empId + ", empName=" + empName
				+ ", leaveFrom=" + leaveFrom + ", leaveTo=" + leaveTo + ", deptt=" + deptt + ", leaveType=" + leaveType
				+ ", lStatus=" + lStatus + ", leaveDates=" + leaveDates + ", lwp=" + lwp + ", inAccount=" + inAccount
				+ ", totalDays=" + totalDays + "]";
	}

}
