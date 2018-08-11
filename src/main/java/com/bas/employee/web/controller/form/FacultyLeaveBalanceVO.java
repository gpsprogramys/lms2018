package com.bas.employee.web.controller.form;

import java.sql.Timestamp;
import java.util.Arrays;

public class FacultyLeaveBalanceVO {
	private long id;
	private String name;
	private String department;
	private String designation;
	private int totalCL;
	private int totalSL;
	private int totalEL;
	private Timestamp dom;
	private Timestamp doe;
	private byte[] image;
	
	public FacultyLeaveBalanceVO() {}

	public FacultyLeaveBalanceVO(FaculityLeaveMasterVO flme, FacultyForm fe) {
		super();
		this.id = fe.getId();
		this.name = fe.getName();
		this.department = fe.getDepartment();
		this.designation = fe.getDesignation();
		this.totalCL = flme.getTotalCL();
		this.totalSL = flme.getTotalSL();
		this.totalEL = flme.getTotalEL();
		this.image = fe.getImage();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public int getTotalCL() {
		return totalCL;
	}

	public void setTotalCL(int totalCL) {
		this.totalCL = totalCL;
	}

	public int getTotalSL() {
		return totalSL;
	}

	public void setTotalSL(int totalSL) {
		this.totalSL = totalSL;
	}

	public int getTotalEL() {
		return totalEL;
	}

	public void setTotalEL(int totalEL) {
		this.totalEL = totalEL;
	}
	
	public Timestamp getDom() {
		return dom;
	}

	public void setDom(Timestamp dom) {
		this.dom = dom;
	}

	public Timestamp getDoe() {
		return doe;
	}

	public void setDoe(Timestamp doe) {
		this.doe = doe;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "FacultyLeaveBalanceVO [id=" + id + ", name=" + name + ", department=" + department + ", designation=" + designation
				+ ", totalCL=" + totalCL + ", totalSL=" + totalSL + ", totalEL=" + totalEL + ", image="
				+ Arrays.toString(image) + "]";
	}
}