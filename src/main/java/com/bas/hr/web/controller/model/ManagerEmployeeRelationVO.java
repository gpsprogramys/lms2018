package com.bas.hr.web.controller.model;

/**
 * 
 * @author nagendra
 *
 */
public class ManagerEmployeeRelationVO {
	private String empid;
	private String managerid;
	private String hrid;
	private String userid;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getHrid() {
		return hrid;
	}

	public void setHrid(String hrid) {
		this.hrid = hrid;
	}

	public String getEmpid() {
		return empid;
	}

	public void setEmpid(String empid) {
		this.empid = empid;
	}

	public String getManagerid() {
		return managerid;
	}

	public void setManagerid(String managerid) {
		this.managerid = managerid;
	}

	@Override
	public String toString() {
		return "ManagerEmployeeRelationVO [empid=" + empid + ", managerid=" + managerid + ", hrid=" + hrid + "]";
	}
}
