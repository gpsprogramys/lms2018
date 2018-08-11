package com.bas.hr.web.controller.model;

/**
 * 
 * @author xxxxxxxxxxxxx
 *
 */
public class LeaveStatusVO {
	private int totalDays;
	private String message;
	private String status;
	private String leaveDates;
	private String lwp="no";
	
	public String getLwp() {
		return lwp;
	}

	public void setLwp(String lwp) {
		this.lwp = lwp;
	}

	public String getLeaveDates() {
		return leaveDates;
	}

	public void setLeaveDates(String leaveDates) {
		this.leaveDates = leaveDates;
	}

	public int getTotalDays() {
		return totalDays;
	}

	public void setTotalDays(int totalDays) {
		this.totalDays = totalDays;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "LeaveStatusVO [totalDays=" + totalDays + ", message=" + message + ", status=" + status + ", leaveDates="
				+ leaveDates + "]";
	}

}
