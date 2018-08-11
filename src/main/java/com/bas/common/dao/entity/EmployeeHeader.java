package com.bas.common.dao.entity;

public class EmployeeHeader {
	private String eid;
	private String name;
	private String designation;
	private String department;
	private float totalDaysWorked;

	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public float getTotalDaysWorked() {
		return totalDaysWorked;
	}
	public void setTotalDaysWorked(float totalDaysWorked) {
		this.totalDaysWorked = totalDaysWorked;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		EmployeeHeader other = (EmployeeHeader) obj;
		if (eid == null) {
			if (other.eid != null)
				return false;
		} else if (!eid.equals(other.eid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EmployeeHeader [eid=" + eid + ", name=" + name + ", designation=" + designation + ", department="
				+ department + ", totalDaysWorked=" + totalDaysWorked + "]";
	}
}
