package com.bas.admin.dao.entity;

import java.util.Date;

public class OrganizationTimeEntity {
	private int sno;
	private String intime;
	private String latein;
	private String outtime;
	private String earlyout;
	private Date doe;
	private Date dom;
	private String enteredby;
	private int active;
	

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public int getSno() {
		return sno;
	}

	public void setSno(int sno) {
		this.sno = sno;
	}

	public String getIntime() {
		return intime;
	}

	public void setIntime(String intime) {
		this.intime = intime;
	}

	public String getOuttime() {
		return outtime;
	}

	public void setOuttime(String outtime) {
		this.outtime = outtime;
	}

	public String getLatein() {
		return latein;
	}

	public void setLatein(String latein) {
		this.latein = latein;
	}

	public String getEarlyout() {
		return earlyout;
	}

	public void setEarlyout(String earlyout) {
		this.earlyout = earlyout;
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

	public String getEnteredby() {
		return enteredby;
	}

	public void setEnteredby(String enteredby) {
		this.enteredby = enteredby;
	}

	@Override
	public String toString() {
		return "OrganizationTimeEntity [sno=" + sno + ", intime=" + intime + ", latein=" + latein + ", outtime="
				+ outtime + ", earlyout=" + earlyout + ", doe=" + doe + ", dom=" + dom + ", enteredby=" + enteredby
				+ ", active=" + active + "]";
	}

	



}
