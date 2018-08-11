package com.bas.employee.web.controller.form;

import java.util.Arrays;
import java.util.Date;

import com.bas.employee.dao.entity.FaculityLeaveMasterEntity1;
import com.bas.employee.dao.entity.FacultyEntity1;

public class FacultyLeaveBalanceVO1 {

	public void setSl(float sl) {
		this.sl = sl;
	}

	public void setWv(float wv) {
		this.wv = wv;
	}
	private Long empid;
	private String name;
	private String department;
	private String designation;
	private String doj;	
	private byte[] image;
	private float sl;
	private float cl;
	private float el;
	private float co;
	private float study;
	private float od;
	private float wv;
	private float sv;
	private Date doe;
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
	private Date dom;
	
	
	public FacultyLeaveBalanceVO1() {}

	public FacultyLeaveBalanceVO1(FaculityLeaveMasterEntity1 flme, FacultyEntity1 fe) {
		super();
		this.empid = fe.getId();
		this.name = fe.getName();
		this.department = fe.getDepartment();
		this.designation = fe.getDesignation();
		this.doj=fe.getDoj();
		this.image = fe.getImage();
		this.cl = flme.getCl();
		this.sl = flme.getSl();
		this.el = flme.getEl();
		this.co = flme.getCo();
		this.study = flme.getStudy();
		this.od = flme.getOd();
		this.wv = flme.getWv();
		this.sv = flme.getSv();
	}
	public Long getEmpid() {
		return empid;
	}
	public void setEmpid(Long empid) {
		this.empid = empid;
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
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getDoj() {
		return doj;
	}
	public void setDoj(String doj) {
		this.doj = doj;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public float getSl() {
		return sl;
	}
	public void setSl(int sl) {
		this.sl = sl;
	}
	public float getCl() {
		return cl;
	}
	public void setCl(float cl) {
		this.cl = cl;
	}
	public float getEl() {
		return el;
	}
	public void setEl(float el) {
		this.el = el;
	}
	public float getCo() {
		return co;
	}
	public void setCo(float co) {
		this.co = co;
	}
	public float getStudy() {
		return study;
	}
	public void setStudy(float study) {
		this.study = study;
	}
	public float getOd() {
		return od;
	}
	public void setOd(float od) {
		this.od = od;
	}
	public float getWv() {
		return wv;
	}
	public void setWv(int wv) {
		this.wv = wv;
	}
	public float getSv() {
		return sv;
	}
	public void setSv(float sv) {
		this.sv = sv;
	}
	@Override
	public String toString() {
		return "FacultyLeaveBalanceVO1 [empid=" + empid + ", name=" + name + ", department=" + department
				+ ", designation=" + designation + ", doj=" + doj + ", image=" + Arrays.toString(image) + ", sl=" + sl
				+ ", cl=" + cl + ", el=" + el + ", co=" + co + ", study=" + study + ", od=" + od + ", wv=" + wv
				+ ", sv=" + sv + ", doe=" + doe + ", dom=" + dom + "]";
	}
	

}
