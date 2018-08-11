package com.bas.admin.web.controller.form;


/**
 * 
 * @author Sid
 * 
 */
public class OrganizationTimeVO {
	private int sno;
	private java.sql.Date fromdate;
	private String intime;
	private String outtime;
	private String latein;
	private String earlyout;
	public int getSno() {
		return sno;
	}
	public void setSno(int sno) {
		this.sno = sno;
	}
	public java.sql.Date getFromdate() {
		return fromdate;
	}
	public void setFromdate(java.sql.Date fromdate) {
		this.fromdate = fromdate;
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
	@Override
	public String toString() {
		return "OrganizationTimeEntity [sno=" + sno + ", fromdate=" + fromdate
				+ ", intime=" + intime + ", outtime=" + outtime + ", latein="
				+ latein + ", earlyout=" + earlyout + "]";
	}	

}
