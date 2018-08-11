package com.bas.employee.web.controller.form;

import java.util.Date;

public class SubjectAlternativeArrangementsVO {

	private int id;
	private String name;
	private String doa;
	private String branch;
	private String submitedby;
	private String period;
	private String fid;
	private String subject;
	private String status;
	private Date doe;
	private Date dom;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDoa() {
		return doa;
	}

	public void setDoa(String doa) {
		this.doa = doa;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getSubmitedby() {
		return submitedby;
	}

	public void setSubmitedby(String submitedby) {
		this.submitedby = submitedby;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
		return "SubjectAlternativeArrangementsVO [id=" + id + ", name=" + name + ", doa=" + doa + ", branch=" + branch
				+ ", submitedby=" + submitedby + ", period=" + period + ", fid=" + fid + ", subject=" + subject
				+ ", status=" + status + ", doe=" + doe + ", dom=" + dom + "]";
	}

	

}
