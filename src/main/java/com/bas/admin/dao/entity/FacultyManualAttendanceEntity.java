package com.bas.admin.dao.entity;

public class FacultyManualAttendanceEntity {
	private int fid;
	private String name;
	private String department;
	private String designation;
	private String cdate;
	private String intime;
	private String outtime;
	private String status;
	private String dom;
	private String intimestatus;
	private String outtimestatus;
	private String attmode;
	private String detail;
	private String present;
	private String description;
	private String alert;
	private String entryby;
	
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
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
	public String getCdate() {
		return cdate;
	}
	public void setCdate(String cdate) {
		this.cdate = cdate;
	}
	public String getIntime() {
		return intime;
	}
	public void setIntime(String intime) {
		this.intime = intime;
	}
	public String getOuttime() {
		return outtime;
	}
	public void setOuttime(String outtime) {
		this.outtime = outtime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDom() {
		return dom;
	}
	public void setDom(String dom) {
		this.dom = dom;
	}
	public String getIntimestatus() {
		return intimestatus;
	}
	public void setIntimestatus(String intimestatus) {
		this.intimestatus = intimestatus;
	}
	public String getOuttimestatus() {
		return outtimestatus;
	}
	public void setOuttimestatus(String outtimestatus) {
		this.outtimestatus = outtimestatus;
	}
	public String getAttmode() {
		return attmode;
	}
	public void setAttmode(String attmode) {
		this.attmode = attmode;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getPresent() {
		return present;
	}
	public void setPresent(String present) {
		this.present = present;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAlert() {
		return alert;
	}
	public void setAlert(String alert) {
		this.alert = alert;
	}
	public String getEntryby() {
		return entryby;
	}
	public void setEntryby(String entryby) {
		this.entryby = entryby;
	}
	
	@Override
	public String toString() {
		return "FacultyManualAttendanceEntity [fid=" + fid + ", name=" + name + ", department=" + department
				+ ", designation=" + designation + ", cdate=" + cdate + ", intime=" + intime + ", outtime=" + outtime
				+ ", status=" + status + ", dom=" + dom + ", intimestatus=" + intimestatus + ", outtimestatus="
				+ outtimestatus + ", attmode=" + attmode + ", detail=" + detail + ", present=" + present
				+ ", description=" + description + ", alert=" + alert + ", entryby=" + entryby + "]";
	}
}
