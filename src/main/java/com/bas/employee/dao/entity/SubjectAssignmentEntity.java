package com.bas.employee.dao.entity;

public class SubjectAssignmentEntity {
	
	private String name;
	private String userId;
	private String dayPeriods;
	private String subCode;
	private String subjectShortName;

	public String getSubCode() {
		return subCode;
	}

	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}

	public String getSubjectShortName() {
		return subjectShortName;
	}

	public void setSubjectShortName(String subjectShortName) {
		this.subjectShortName = subjectShortName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDayPeriods() {
		return dayPeriods;
	}

	public void setDayPeriods(String dayPeriods) {
		this.dayPeriods = dayPeriods;
	}

	@Override
	public String toString() {
		return "SubjectAssignmentEntity [name=" + name + ", userId=" + userId
				+ ", dayPeriods=" + dayPeriods + ", subCode=" + subCode
				+ ", subjectShortName=" + subjectShortName + "]";
	}

}
