package com.bas.employee.web.controller.form;

import java.util.Date;

public class LoginForm {
	private String gender;
	private String name;
	private int fsn;
	private String userid;
	private String eid;
	private String password;
	private String locked;
	private String role;
	private String reportingManager; 
	private String homePage;
	private Date doe;
	private Date dom;
	
	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
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
		return "LoginForm [gender=" + gender + ", name=" + name + ", fsn="
				+ fsn + ", userid=" + userid + ", eid=" + eid + ", password="
				+ password + ", locked=" + locked + ", role=" + role
				+ ", reportingManager=" + reportingManager + ", homePage="
				+ homePage + ", doe=" + doe + ", dom=" + dom + "]";
	}

}
