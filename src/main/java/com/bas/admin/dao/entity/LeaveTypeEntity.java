package com.bas.admin.dao.entity;

import java.util.Date;

public class LeaveTypeEntity {
	private int leaveTypeId;
	private String leaveType;
	private String description;
	private Date doe;
	private Date dom;
	private String entryBy;
	public int getLeaveTypeId() {
		return leaveTypeId;
	}
	public void setLeaveTypeId(int leaveTypeId) {
		this.leaveTypeId = leaveTypeId;
	}
	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getEntryBy() {
		return entryBy;
	}
	public void setEntryBy(String entryBy) {
		this.entryBy = entryBy;
	}
	@Override
	public String toString() {
		return "LeaveTypeEntity [leaveTypeId=" + leaveTypeId + ", leaveType="
				+ leaveType + ", description=" + description + ", doe=" + doe
				+ ", dom=" + dom + ", entryBy=" + entryBy + "]";
	}

}
