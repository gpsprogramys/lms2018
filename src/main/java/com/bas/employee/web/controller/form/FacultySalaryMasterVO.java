package com.bas.employee.web.controller.form;

import java.sql.Date;

/**
 * @author Sid
 *Master Entity for computing salary of an employee.Computed on the basis of various tables.
 *
 */
public class FacultySalaryMasterVO {
	private int eid;
	private Date month;
	private int daysworked;
	private int noleaves;
	private int totworkdays;
	private int salpaid;
	private String comment;
	private String department;
	private Date dom;
	private Date doe;
	private String modifyby;
	private byte[] photo;
	
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public int getEid() {
		return eid;
	}
	public void setEid(int eid) {
		this.eid = eid;
	}
	public Date getMonth() {
		return month;
	}
	public void setMonth(Date month) {
		this.month = month;
	}
	public int getDaysworked() {
		return daysworked;
	}
	public void setDaysworked(int daysworked) {
		this.daysworked = daysworked;
	}
	public int getNoleaves() {
		return noleaves;
	}
	public void setNoleaves(int noleaves) {
		this.noleaves = noleaves;
	}
	public int getTotworkdays() {
		return totworkdays;
	}
	public void setTotworkdays(int totworkdays) {
		this.totworkdays = totworkdays;
	}
	public int getSalpaid() {
		return salpaid;
	}
	public void setSalpaid(int salpaid) {
		this.salpaid = salpaid;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public Date getDom() {
		return dom;
	}
	public void setDom(Date dom) {
		this.dom = dom;
	}
	public Date getDoe() {
		return doe;
	}
	public void setDoe(Date doe) {
		this.doe = doe;
	}
	public String getModifyby() {
		return modifyby;
	}
	public void setModifyby(String modifyby) {
		this.modifyby = modifyby;
	}
	@Override
	public String toString() {
		return "FacultySalaryMasterEntity [eid=" + eid + ", month=" + month
				+ ", daysworked=" + daysworked + ", noleaves=" + noleaves
				+ ", totworkdays=" + totworkdays + ", salpaid=" + salpaid
				+ ", comment=" + comment + ", department=" + department
				+ ", dom=" + dom + ", doe=" + doe + ", modifyby=" + modifyby
				+ "]";
	}	
	
}
