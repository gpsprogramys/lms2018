package com.bas.hr.web.controller.model;

public class EmployeeLeave {
	private float el;
	private float cl;
	private float lwp;
	private float mcl;
	private float mel;
	private float mlwp;
	private String changeType;
	
	public float getMcl() {
		return mcl;
	}

	public void setMcl(float mcl) {
		this.mcl = mcl;
	}

	public float getMel() {
		return mel;
	}

	public void setMel(float mel) {
		this.mel = mel;
	}

	public float getMlwp() {
		return mlwp;
	}

	public void setMlwp(float mlwp) {
		this.mlwp = mlwp;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public float getLwp() {
		return lwp;
	}

	public void setLwp(float lwp) {
		this.lwp = lwp;
	}

	public float getEl() {
		return el;
	}

	public void setEl(float el) {
		this.el = el;
	}

	public float getCl() {
		return cl;
	}

	public void setCl(float cl) {
		this.cl = cl;
	}

	@Override
	public String toString() {
		return "EmployeeLeave [el=" + el + ", cl=" + cl + ", lwp=" + lwp + ", mcl=" + mcl + ", mel=" + mel + ", mlwp="
				+ mlwp + ", changeType=" + changeType + "]";
	}

}
