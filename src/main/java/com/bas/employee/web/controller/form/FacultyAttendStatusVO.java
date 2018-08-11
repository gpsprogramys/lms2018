package com.bas.employee.web.controller.form;

import java.util.Date;


public class FacultyAttendStatusVO {    
	private int fid;
	private Date cdate;
	private String intime;
	private String outtime;
	private String status;
	private Date dom;
	private String intimestatus;
	private String outtimestatus;
	private String detail;
	private String present;
	private String description;
	private String alert;	
	
	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public Date getCdate() {
		return cdate;
	}

	public void setCdate(Date cdate) {
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

	public Date getDom() {
		return dom;
	}

	public void setDom(Date dom) {
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

	@Override
	public String toString() {
		return "FacultyAttendStatusVO [fid=" + fid + ", cdate=" + cdate
				+ ", intime=" + intime + ", outtime=" + outtime + ", status="
				+ status + ", dom=" + dom + ", intimestatus=" + intimestatus
				+ ", outtimestatus=" + outtimestatus + ", detail=" + detail
				+ ", present=" + present + ", description=" + description
				+ ", alert=" + alert + "]";
	}
}
