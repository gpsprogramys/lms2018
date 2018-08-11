/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bas.admin.web.controller.form;



/**
 * 
 * @author astha
 */
public class FaculityDailyAttendanceReportVO implements Comparable<FaculityDailyAttendanceReportVO> {

	private int fid;
	private String name;
	private String fatherName;
	private String status;
	private String present;
	private String department;
	private String designation;
	private String type;
	private String cdate;
	private String detail;
	private String intime;
	private String intimestatus;
	private String outtime;
	private String outtimestatus;
	private String doj;
	
	
	
	public String getDoj() {
		return doj;
	}
	public void setDoj(String doj) {
		this.doj = doj;
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
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPresent() {
		return present;
	}
	public void setPresent(String present) {
		this.present = present;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getCdate() {
		return cdate;
	}
	public void setCdate(String cdate) {
		this.cdate = cdate;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getIntime() {
		return intime;
	}
	public void setIntime(String intime) {
		this.intime = intime;
	}
	public String getIntimestatus() {
		return intimestatus;
	}
	public void setIntimestatus(String intimestatus) {
		this.intimestatus = intimestatus;
	}
	public String getOuttime() {
		return outtime;
	}
	public void setOuttime(String outtime) {
		this.outtime = outtime;
	}
	public String getOuttimestatus() {
		return outtimestatus;
	}
	public void setOuttimestatus(String outtimestatus) {
		this.outtimestatus = outtimestatus;
	}
	@Override
	public String toString() {
		return "FaculityDailyAttendanceReportVO [fid=" + fid + ", name=" + name + ", fatherName=" + fatherName
				+ ", status=" + status + ", present=" + present + ", department=" + department + ", designation="
				+ designation + ", type=" + type + ", cdate=" + cdate + ", detail=" + detail + ", intime=" + intime
				+ ", intimestatus=" + intimestatus + ", outtime=" + outtime + ", outtimestatus=" + outtimestatus
				+ ", doj=" + doj + "]";
	}
	@Override
	public int compareTo(FaculityDailyAttendanceReportVO o) {
		int p=0;
		/*Integer m=fid;
		//Integer n=fid;
		if(fid>1){
			 p=m.compareTo((Integer)o.getFid());
		}*/
		
		if(intime!=null){
			 p=intime.compareTo(o.getIntime());
		}
		
		if(p==0){
			if(outtime!=null){
				 p=outtime.compareTo(o.getOuttime());
			}
		}
		if(p==0){
			if(department!=null){
				 p=department.compareTo(o.getDepartment());
			}
		}
		return p;
	}

	
}
