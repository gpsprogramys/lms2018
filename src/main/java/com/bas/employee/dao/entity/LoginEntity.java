package com.bas.employee.dao.entity;

import java.util.Date;

public class LoginEntity {
	private String gender;
	private int fsn;
	private String name;
	private String userid;
	private String eid;
	private String password;
	private String locked;
	private String role;
	private String email;
	private String reportingManager; 
	private Date doe;
	private Date dom;
   
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReportingManager() {
		return reportingManager;
	}

	public void setReportingManager(String reportingManager) {
		this.reportingManager = reportingManager;
	}

	public int getFsn() {
		return fsn;
	}

	public void setFsn(int fsn) {
		this.fsn = fsn;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLocked() {
		return locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

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

	@Override
	public String toString() {
		return "LoginEntity [gender=" + gender + ", fsn=" + fsn + ", name=" + name + ", userid=" + userid + ", eid="
				+ eid + ", password=" + password + ", locked=" + locked + ", role=" + role + ", email=" + email
				+ ", reportingManager=" + reportingManager + ", doe=" + doe + ", dom=" + dom + "]";
	}

}
