package com.bas.employee.web.controller.form;

import java.util.Date;

/**
 * 
 * @author nagendra
 *
 */
public class EmployeeLeaveRequestForm {
	private String requestID;
	private String managerId;
	private String employeeId;
	private String empName;
	private String department;
	private String mobile;
	private Date leaveFrom;
	private Date leaveTo;
	private float totalDays;
	private String leaveType;
	private String purpose;
	private String leaveCategory;
	private String leaveMeeting;
	private String hrManagerId;
	private String managerApproval;
	private String hrApproval;
	private String managementid;
	private String managementApproval;
	private String lstatus;
	private String reason;
	private String address;
	private String CCTo;
	private String reportingManager;
	private String description;
	private String leaveDates;
	private float lwp;
	private Date inAccount;
	private String sandwitch;
	private String otherLeaveDescription;
    private String otherReason;
    private Date  doapplication;
    private Date dom;
    private String userid;
    
    
	public String getManagementid() {
		return managementid;
	}

	public void setManagementid(String managementid) {
		this.managementid = managementid;
	}
	

	public String getManagementApproval() {
		return managementApproval;
	}

	public void setManagementApproval(String managementApproval) {
		this.managementApproval = managementApproval;
	}

	public float getLwp() {
		return lwp;
	}

	public void setLwp(float lwp) {
		this.lwp = lwp;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
    
	public Date getDoapplication() {
		return doapplication;
	}

	public void setDoapplication(Date doapplication) {
		this.doapplication = doapplication;
	}

	public Date getDom() {
		return dom;
	}

	public void setDom(Date dom) {
		this.dom = dom;
	}
	
	public String getLstatus() {
		return lstatus;
	}
	public void setLstatus(String lstatus) {
		this.lstatus = lstatus;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getOtherLeaveDescription() {
		return otherLeaveDescription;
	}
	public void setOtherLeaveDescription(String otherLeaveDescription) {
		this.otherLeaveDescription = otherLeaveDescription;
	}
	public String getOtherReason() {
		return otherReason;
	}
	public void setOtherReason(String otherReason) {
		this.otherReason = otherReason;
	}
	
	public String getLeaveDates() {
		return leaveDates;
	}

	public void setLeaveDates(String leaveDates) {
		this.leaveDates = leaveDates;
	}

	public Date getInAccount() {
		return inAccount;
	}

	public void setInAccount(Date inAccount) {
		this.inAccount = inAccount;
	}

	public String getSandwitch() {
		return sandwitch;
	}

	public void setSandwitch(String sandwitch) {
		this.sandwitch = sandwitch;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getHrManagerId() {
		return hrManagerId;
	}

	public void setHrManagerId(String hrManagerId) {
		this.hrManagerId = hrManagerId;
	}

	public EmployeeLeaveRequestForm() {
		super();
	}

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}


	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public float getTotalDays() {
		return totalDays;
	}

	public void setTotalDays(float totalDays) {
		this.totalDays = totalDays;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
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

	public String getManagerApproval() {
		return managerApproval;
	}

	public void setManagerApproval(String managerApproval) {
		this.managerApproval = managerApproval;
	}

	public String getHrApproval() {
		return hrApproval;
	}

	public void setHrApproval(String hrApproval) {
		this.hrApproval = hrApproval;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCCTo() {
		return CCTo;
	}

	public void setCCTo(String cCTo) {
		CCTo = cCTo;
	}

	public String getReportingManager() {
		return reportingManager;
	}

	public void setReportingManager(String reportingManager) {
		this.reportingManager = reportingManager;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "EmployeeLeaveRequestForm [requestID=" + requestID + ", managerId=" + managerId + ", employeeId="
				+ employeeId + ", empName=" + empName + ", department=" + department + ", mobile=" + mobile
				+ ", leaveFrom=" + leaveFrom + ", leaveTo=" + leaveTo + ", totalDays=" + totalDays + ", leaveType="
				+ leaveType + ", purpose=" + purpose + ", leaveCategory=" + leaveCategory + ", leaveMeeting="
				+ leaveMeeting + ", hrManagerId=" + hrManagerId + ", managerApproval=" + managerApproval
				+ ", hrApproval=" + hrApproval + ", lstatus=" + lstatus + ", reason=" + reason + ", address=" + address
				+ ", CCTo=" + CCTo + ", reportingManager=" + reportingManager + ", description=" + description
				+ ", leaveDates=" + leaveDates + ", lwp=" + lwp + ", inAccount=" + inAccount + ", sandwitch="
				+ sandwitch + ", otherLeaveDescription=" + otherLeaveDescription + ", otherReason=" + otherReason
				+ ", doapplication=" + doapplication + ", dom=" + dom + ", userid=" + userid + "]";
	}

}
