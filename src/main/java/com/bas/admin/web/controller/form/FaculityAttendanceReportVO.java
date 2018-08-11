package com.bas.admin.web.controller.form;

import java.sql.Time;
import java.util.Date;

/**
 * 
 * @author Nagendra
 * 
 */
public class FaculityAttendanceReportVO {

	private int eid;
	private Date cdate;
	private String detail;
	private Time intime;
	private String intimestatus;
	private Time outtime;
	private String outtimestatus;
	private String present;
	private String status;
	
	private String cdateString;
	private String intimeString;
	private String outtimeString;
	
	
	public String getCdateString() {
		return cdateString;
	}

	public void setCdateString(String cdateString) {
		this.cdateString = cdateString;
	}

	public String getIntimeString() {
		return intimeString;
	}

	public void setIntimeString(String intimeString) {
		this.intimeString = intimeString;
	}

	public String getOuttimeString() {
		return outtimeString;
	}

	public void setOuttimeString(String outtimeString) {
		this.outtimeString = outtimeString;
	}

	public int getEid() {
		return eid;
	}

	public void setEid(int eid) {
		this.eid = eid;
	}

	public Date getCdate() {
		return cdate;
	}

	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Time getIntime() {
		return intime;
	}

	public void setIntime(Time intime) {
		this.intime = intime;
	}

	public String getIntimestatus() {
		return intimestatus;
	}

	public void setIntimestatus(String intimestatus) {
		this.intimestatus = intimestatus;
	}

	public Time getOuttime() {
		return outtime;
	}

	public void setOuttime(Time outtime) {
		this.outtime = outtime;
	}

	public String getOuttimestatus() {
		return outtimestatus;
	}

	public void setOuttimestatus(String outtimestatus) {
		this.outtimestatus = outtimestatus;
	}

	public String getPresent() {
		return present;
	}

	public void setPresent(String present) {
		this.present = present;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "FaculityAttendanceReportVO [eid=" + eid + ", cdate=" + cdate
				+ ", detail=" + detail + ", intime=" + intime
				+ ", intimestatus=" + intimestatus + ", outtime=" + outtime
				+ ", outtimestatus=" + outtimestatus + ", present=" + present
				+ ", status=" + status + "]";
	}

}
