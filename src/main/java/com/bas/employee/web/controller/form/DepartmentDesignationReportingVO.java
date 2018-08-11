package com.bas.employee.web.controller.form;

import java.util.ArrayList;
import java.util.List;

public class DepartmentDesignationReportingVO {
	private List<String> departmentList=new ArrayList<String>();
	private List<String> designationList=new ArrayList<String>();;
	private List<String> reportingManagerList=new ArrayList<String>();;

	public List<String> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<String> departmentList) {
		this.departmentList = departmentList;
	}

	public List<String> getDesignationList() {
		return designationList;
	}

	public void setDesignationList(List<String> designationList) {
		this.designationList = designationList;
	}

	public List<String> getReportingManagerList() {
		return reportingManagerList;
	}

	public void setReportingManagerList(List<String> reportingManagerList) {
		this.reportingManagerList = reportingManagerList;
	}

	@Override
	public String toString() {
		return "DepartmentDesignationReportingVO [departmentList=" + departmentList + ", designationList="
				+ designationList + ", reportingManagerList=" + reportingManagerList + "]";
	}

}
