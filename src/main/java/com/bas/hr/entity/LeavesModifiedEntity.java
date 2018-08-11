package com.bas.hr.entity;

import java.sql.Timestamp;

/**
 * 
 * @author xxxxxxxx
 *
 */
public class LeavesModifiedEntity {

	private int sno;
	private String requestid;
	private float cl;
	private float el;
	private float lwp;
	private String changeType;
	private String description;
	private Timestamp dom;
	private Timestamp doe;
	private String adjustedBy;

	public int getSno() {
		return sno;
	}

	public void setSno(int sno) {
		this.sno = sno;
	}

	public String getRequestid() {
		return requestid;
	}

	public void setRequestid(String requestid) {
		this.requestid = requestid;
	}

	public float getCl() {
		return cl;
	}

	public void setCl(float cl) {
		this.cl = cl;
	}

	public float getEl() {
		return el;
	}

	public void setEl(float el) {
		this.el = el;
	}

	public float getLwp() {
		return lwp;
	}

	public void setLwp(float lwp) {
		this.lwp = lwp;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getDom() {
		return dom;
	}

	public void setDom(Timestamp dom) {
		this.dom = dom;
	}

	public Timestamp getDoe() {
		return doe;
	}

	public void setDoe(Timestamp doe) {
		this.doe = doe;
	}

	public String getAdjustedBy() {
		return adjustedBy;
	}

	public void setAdjustedBy(String adjustedBy) {
		this.adjustedBy = adjustedBy;
	}

	@Override
	public String toString() {
		return "LeavesModifiedEntity [sno=" + sno + ", requestid=" + requestid + ", cl=" + cl + ", el=" + el + ", lwp="
				+ lwp + ", changeType=" + changeType + ", description=" + description + ", dom=" + dom + ", doe=" + doe
				+ ", adjustedBy=" + adjustedBy + "]";
	}

}
