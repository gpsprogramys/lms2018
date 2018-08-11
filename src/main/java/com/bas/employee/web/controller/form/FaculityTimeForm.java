package com.bas.employee.web.controller.form;

import java.util.Date;
import java.util.List;

public class FaculityTimeForm {
	private int fid;
	private String subjectCode;
	private String subjectName;
	private String branchSemSec;
	private String subjectType;
	private String shortSubjectName;
	private String dayName;
	private List<String> period;
	private String classRoom;
	private String startTime;
	private Date doe;
	private Date dom;
	private String endTime;
	private String description;
	private byte[] image;

	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getBranchSemSec() {
		return branchSemSec;
	}

	public void setBranchSemSec(String branchSemSec) {
		this.branchSemSec = branchSemSec;
	}

	public String getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}

	public String getShortSubjectName() {
		return shortSubjectName;
	}

	public void setShortSubjectName(String shortSubjectName) {
		this.shortSubjectName = shortSubjectName;
	}

	public String getDayName() {
		return dayName;
	}

	public void setDayName(String dayName) {
		this.dayName = dayName;
	}

	public List<String> getPeriod() {
		return period;
	}

	public void setPeriod(List<String> period) {
		this.period = period;
	}

	public String getClassRoom() {
		return classRoom;
	}

	public void setClassRoom(String classRoom) {
		this.classRoom = classRoom;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
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

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "FaculityTimeForm [fid=" + fid + ", subjectCode=" + subjectCode
				+ ", subjectName=" + subjectName + ", branchSemSec="
				+ branchSemSec + ", subjectType=" + subjectType
				+ ", shortSubjectName=" + shortSubjectName + ", dayName="
				+ dayName + ", period=" + period + ", classRoom=" + classRoom
				+ ", startTime=" + startTime + ", doe=" + doe + ", dom=" + dom
				+ ", endTime=" + endTime + ", description=" + description + "]";
	}

}
