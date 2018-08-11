package com.bas.employee.web.controller.form;

import java.sql.Timestamp;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

//Started by Rahul Jain

public class FacultyLeaveApprovalVO {

	// need to add more on this pojo
	private String requestID;
	private String empName;
	private String empid;
	private String doj;
	private String deptt;
	private String designation;
	private Date leaveFrom;
	private Date leaveTo;
	private float totalDays;
	private float lwp;
	private float cel;
	private float ccl;
	private String elDays;
	private String leaveType;
	private String leaveDays;
	
	private String purpose;
	private String managerApproval;
	private String hrApproval;
	private String manegementApproval;
	private String mcomment;
	private String acomment;
	private String mobile;
	private String reportingManager;
	private int employee_id;
	private int manager_id;
	private String hr_Manager_ID;
	private String reason;
	private String address;
	private String cCTo;
	private Timestamp doapplication;
	private String inAccount;
	private float el;
	private float cl;
	
	
	public String getDoj() {
		return doj;
	}

	public void setDoj(String doj) {
		this.doj = doj;
	}

	public String getLeaveDays() {
		return leaveDays;
	}

	public void setLeaveDays(String leaveDays) {
		this.leaveDays = leaveDays;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public float getCcl() {
		return ccl;
	}

	public void setCcl(float ccl) {
		this.ccl = ccl;
	}

	public float getCel() {
		return cel;
	}

	public void setCel(float cel) {
		this.cel = cel;
	}

	public String getElDays() {
		return elDays;
	}

	public void setElDays(String elDays) {
		this.elDays = elDays;
	}

	public String getDeptt() {
		return deptt;
	}

	public void setDeptt(String deptt) {
		this.deptt = deptt;
	}

	public String getManegementApproval() {
		return manegementApproval;
	}

	public void setManegementApproval(String manegementApproval) {
		this.manegementApproval = manegementApproval;
	}

	public String getMcomment() {
		return mcomment;
	}

	public void setMcomment(String mcomment) {
		this.mcomment = mcomment;
	}

	public String getAcomment() {
		return acomment;
	}

	public void setAcomment(String acomment) {
		this.acomment = acomment;
	}

	public float getEl() {
		return el;
	}

	public void setEl(float el) {
		this.el = el;
	}

	public float getCl() {
		return cl;
	}

	public void setCl(float cl) {
		this.cl = cl;
	}

	public String getInAccount() {
		return inAccount;
	}

	public void setInAccount(String inAccount) {
		this.inAccount = inAccount;
	}

	public Timestamp getDoapplication() {
		return doapplication;
	}

	public void setDoapplication(Timestamp doapplication) {
		this.doapplication = doapplication;
	}

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpid() {
		return empid;
	}

	public void setEmpid(String empid) {
		this.empid = empid;
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

	public float getLwp() {
		return lwp;
	}

	public void setLwp(float lwp) {
		this.lwp = lwp;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getReportingManager() {
		return reportingManager;
	}

	public void setReportingManager(String reportingManager) {
		this.reportingManager = reportingManager;
	}

	public int getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}

	public int getManager_id() {
		return manager_id;
	}

	public void setManager_id(int manager_id) {
		this.manager_id = manager_id;
	}

	public String getHr_Manager_ID() {
		return hr_Manager_ID;
	}

	public void setHr_Manager_ID(String hr_Manager_ID) {
		this.hr_Manager_ID = hr_Manager_ID;
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

	public String getcCTo() {
		return cCTo;
	}

	public void setcCTo(String cCTo) {
		this.cCTo = cCTo;
	}

	@Override
	public String toString() {
		return "FacultyLeaveApprovalVO [requestID=" + requestID + ", empName=" + empName + ", empid=" + empid + ", doj="
				+ doj + ", deptt=" + deptt + ", designation=" + designation + ", leaveFrom=" + leaveFrom + ", leaveTo="
				+ leaveTo + ", totalDays=" + totalDays + ", lwp=" + lwp + ", cel=" + cel + ", ccl=" + ccl + ", elDays="
				+ elDays + ", leaveType=" + leaveType + ", leaveDays=" + leaveDays + ", purpose=" + purpose
				+ ", managerApproval=" + managerApproval + ", hrApproval=" + hrApproval + ", manegementApproval="
				+ manegementApproval + ", mcomment=" + mcomment + ", acomment=" + acomment + ", mobile=" + mobile
				+ ", reportingManager=" + reportingManager + ", employee_id=" + employee_id + ", manager_id="
				+ manager_id + ", hr_Manager_ID=" + hr_Manager_ID + ", reason=" + reason + ", address=" + address
				+ ", cCTo=" + cCTo + ", doapplication=" + doapplication + ", inAccount=" + inAccount + ", el=" + el
				+ ", cl=" + cl + "]";
	}

	


}
