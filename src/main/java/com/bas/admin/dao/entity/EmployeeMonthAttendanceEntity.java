package com.bas.admin.dao.entity;

import java.util.List;

public class EmployeeMonthAttendanceEntity {

	private String name;
	private String eid;
	private String designation;
	private int totalDays;
	private String department;
	private String cdate;
	private String month;
	
	public String getCdate() {
		return cdate;
	}

	public void setCdate(String cdate) {
		this.cdate = cdate;
	}

	private List<String> dayStatusList;
	
	
	
	public EmployeeMonthAttendanceEntity() {}
	
	public EmployeeMonthAttendanceEntity(String name, String eid, String designation, int totalDays, String department,
			String month, List<String> dayStatusList) {
		this.name = name;
		this.eid = eid;
		this.designation = designation;
		this.totalDays = totalDays;
		this.department = department;
		this.month = month;
		this.dayStatusList = dayStatusList;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public int getTotalDays() {
		return totalDays;
	}
	public void setTotalDays(int totalDays) {
		this.totalDays = totalDays;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public List<String> getDayStatusList() {
		return dayStatusList;
	}
	public void setDayStatusList(List<String> dayStatusList) {
		this.dayStatusList = dayStatusList;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cdate == null) ? 0 : cdate.hashCode());
		result = prime * result + ((eid == null) ? 0 : eid.hashCode());
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
		EmployeeMonthAttendanceEntity other = (EmployeeMonthAttendanceEntity) obj;
		if (cdate == null) {
			if (other.cdate != null)
				return false;
		} else if (!cdate.equals(other.cdate))
			return false;
		if (eid == null) {
			if (other.eid != null)
				return false;
		} else if (!eid.equals(other.eid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EmployeeMonthAttendanceEntity [name=" + name + ", eid=" + eid + ", designation=" + designation
				+ ", totalDays=" + totalDays + ", department=" + department + ", cdate=" + cdate + ", month=" + month
				+ ", dayStatusList=" + dayStatusList + "]";
	}


}
