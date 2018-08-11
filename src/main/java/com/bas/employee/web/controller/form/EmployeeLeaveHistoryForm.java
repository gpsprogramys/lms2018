package com.bas.employee.web.controller.form;

import java.util.Date;

public class EmployeeLeaveHistoryForm 
{
	private int empNo;
	private String empName;
	private String deptt;
	private String leaveType;
	private String leaveCategory;
	private String leaveMeeting;
	private String lstatus;
	private String purpose;
	private Date ldateFrom;
	private Date ldateTo;
	private String addressTelNoLeave;
	private int totalDays;
	private Date doe;
	private Date dom;
	private String description;
	private String approvedBy;
	private String effectiveDates;
	
	public int getEmpNo() {
		return empNo;
	}
	public EmployeeLeaveHistoryForm() {
		super();
	}
	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
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
	public String getLeaveCategory() {
		return leaveCategory;
	}
	public void setLeaveCategory(String leaveCategory) {
		this.leaveCategory = leaveCategory;
	}
	public String getLeaveMeeting() {
		return leaveMeeting;
	}
	public void setLeaveMeeting(String leaveMeeting) {
		this.leaveMeeting = leaveMeeting;
	}
	public String getLstatus() {
		return lstatus;
	}
	public void setLstatus(String lstatus) {
		this.lstatus = lstatus;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public Date getLdateFrom() {
		return ldateFrom;
	}
	public void setLdateFrom(Date ldateFrom) {
		this.ldateFrom = ldateFrom;
	}
	public Date getLdateTo() {
		return ldateTo;
	}
	public void setLdateTo(Date ldateTo) {
		this.ldateTo = ldateTo;
	}
	public String getAddressTelNoLeave() {
		return addressTelNoLeave;
	}
	public void setAddressTelNoLeave(String addressTelNoLeave) {
		this.addressTelNoLeave = addressTelNoLeave;
	}
	public int getTotalDays() {
		return totalDays;
	}
	public void setTotalDays(int totalDays) {
		this.totalDays = totalDays;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public String getEffectiveDates() {
		return effectiveDates;
	}
	public void setEffectiveDates(String effectiveDates) {
		this.effectiveDates = effectiveDates;
	}
	@Override
	public String toString() {
		return "EmployeeLeaveHistoryForm [empNo=" + empNo + ", empName=" + empName + ", deptt=" + deptt + ", leaveType="
				+ leaveType + ", leaveCategory=" + leaveCategory + ", leaveMeeting=" + leaveMeeting + ", lstatus="
				+ lstatus + ", purpose=" + purpose + ", ldateFrom=" + ldateFrom + ", ldateTo=" + ldateTo
				+ ", addressTelNoLeave=" + addressTelNoLeave + ", totalDays=" + totalDays + ", doe=" + doe + ", dom="
				+ dom + ", description=" + description + ", approvedBy=" + approvedBy + ", effectiveDates="
				+ effectiveDates + "]";
	}
	
	
	

}
