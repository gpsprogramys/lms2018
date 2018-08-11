package com.bas.admin.dao.entity;

import java.sql.Date;
import java.util.Arrays;

/**
 * @author Sid
 *Master Entity for displaying of an employee.Computed on the basis of various tables.
 *
 */
public class AdminSalaryMasterEntity {
	private int eid;
	private String name;
	private String category;
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
	public int getEid() {
		return eid;
	}
	public void setEid(int eid) {
		this.eid = eid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
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
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public AdminSalaryMasterEntity(int eid, String name, String category,
			Date month, int daysworked, int noleaves, int totworkdays,
			int salpaid, String comment, String department, Date dom, Date doe,
			String modifyby, byte[] photo) {
		super();
		this.eid = eid;
		this.name = name;
		this.category = category;
		this.month = month;
		this.daysworked = daysworked;
		this.noleaves = noleaves;
		this.totworkdays = totworkdays;
		this.salpaid = salpaid;
		this.comment = comment;
		this.department = department;
		this.dom = dom;
		this.doe = doe;
		this.modifyby = modifyby;
		this.photo = photo;
	}
	@Override
	public String toString() {
		return "AdminSalaryMasterEntity [eid=" + eid + ", name=" + name
				+ ", category=" + category + ", month=" + month
				+ ", daysworked=" + daysworked + ", noleaves=" + noleaves
				+ ", totworkdays=" + totworkdays + ", salpaid=" + salpaid
				+ ", comment=" + comment + ", department=" + department
				+ ", dom=" + dom + ", doe=" + doe + ", modifyby=" + modifyby
				+ ", photo=" + Arrays.toString(photo) + "]";
	}
	public AdminSalaryMasterEntity() {
		//super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
