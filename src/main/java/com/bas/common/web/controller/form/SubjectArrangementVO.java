package com.bas.common.web.controller.form;

import java.util.ArrayList;
import java.util.List;

public class SubjectArrangementVO {
	private String facultyId;
	private String name;
	private List<SubjectNameCodeVO> subjectsCode=new ArrayList<SubjectNameCodeVO>();

	

	public String getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(String facultyId) {
		this.facultyId = facultyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SubjectNameCodeVO> getSubjectsCode() {
		return subjectsCode;
	}

	public void setSubjectsCode(List<SubjectNameCodeVO> subjectsCode) {
		this.subjectsCode = subjectsCode;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((facultyId == null) ? 0 : facultyId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubjectArrangementVO other = (SubjectArrangementVO) obj;
		if (facultyId == null) {
			if (other.facultyId != null)
				return false;
		} else if (!facultyId.equals(other.facultyId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SubjectArrangementVO [facultyId=" + facultyId + ", name="
				+ name + ", subjectsCode=" + subjectsCode + "]";
	}

}
