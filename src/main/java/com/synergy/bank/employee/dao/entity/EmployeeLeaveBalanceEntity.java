package com.synergy.bank.employee.dao.entity;

import java.util.Date;

public class EmployeeLeaveBalanceEntity 
{
	private int empNo;
	private Date leaveMonth;
	private int totalCL;
	private int totalSL;
	private int totalEL;
	private int OD;
	private Date dom;
	private Date doe;
	private String description;
	private String entryType;
	private String modifiedBy;
	
	
	
	@Override
	public String toString() {
		return "EmployeeLeaveBalanceForm [empNo=" + empNo + ", leaveMonth=" + leaveMonth + ", totalCL=" + totalCL
				+ ", totalSL=" + totalSL + ", totalEL=" + totalEL + ", OD=" + OD + ", dom=" + dom + ", doe=" + doe
				+ ", description=" + description + ", entryType=" + entryType + ", modifiedBy=" + modifiedBy + "]";
	}
	public EmployeeLeaveBalanceEntity() {
		super();
	}
	public int getEmpNo() {
		return empNo;
	}
	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}
	public Date getLeaveMonth() {
		return leaveMonth;
	}
	public void setLeaveMonth(Date leaveMonth) {
		this.leaveMonth = leaveMonth;
	}
	public int getTotalCL() {
		return totalCL;
	}
	public void setTotalCL(int totalCL) {
		this.totalCL = totalCL;
	}
	public int getTotalSL() {
		return totalSL;
	}
	public void setTotalSL(int totalSL) {
		this.totalSL = totalSL;
	}
	public int getTotalEL() {
		return totalEL;
	}
	public void setTotalEL(int totalEL) {
		this.totalEL = totalEL;
	}
	public int getOD() {
		return OD;
	}
	public void setOD(int oD) {
		OD = oD;
	}
	public Date getDom() {
		return dom;
	}
	public void setDom(Date dom) {
		this.dom = dom;
	}
	public Date getDoe() {
		return doe;
	}
	public void setDoe(Date doe) {
		this.doe = doe;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEntryType() {
		return entryType;
	}
	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	
	
	

}
