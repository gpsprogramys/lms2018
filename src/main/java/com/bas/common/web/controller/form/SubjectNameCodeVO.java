package com.bas.common.web.controller.form;

public class SubjectNameCodeVO {
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
	@Override
	public String toString() {
		return "SubjectNameCodeVO [subCode=" + subCode + ", subjectShortName="
				+ subjectShortName + "]";
	}
	
}
