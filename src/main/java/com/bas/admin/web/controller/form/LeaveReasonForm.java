package com.bas.admin.web.controller.form;

import java.util.Date;

public class LeaveReasonForm {
	private int leaveId;
	private String leaveDetail;
	private String description;
	private Date doe;
	private Date dom;
	private String entryBy;
	public int getLeaveId() {
		return leaveId;
	}
	public void setLeaveId(int leaveId) {
		this.leaveId = leaveId;
	}
	public String getLeaveDetail() {
		return leaveDetail;
	}
	public void setLeaveDetail(String leaveDetail) {
		this.leaveDetail = leaveDetail;
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
		return "LeaveReason [leaveId=" + leaveId + ", leaveDetail="
				+ leaveDetail + ", description=" + description + ", doe=" + doe
				+ ", dom=" + dom + ", entryBy=" + entryBy + "]";
	}
	
}
