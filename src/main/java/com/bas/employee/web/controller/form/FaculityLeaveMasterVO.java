/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bas.employee.web.controller.form;

import java.util.Arrays;
import java.util.Date;

/**
 *
 * @author astha
 */
public class FaculityLeaveMasterVO {
    
    private int empid;
    private String requestId;
    private int id;
    private String name;
    private String designation;
    private String department;
    private byte[] image;
    private String leaveMonth;
    private int totalCL;
    private int totalSL;
    private int totalEL;
    private int od;
    private float lwp;
    private String type;
    private Date doe;
    private Date dom;
    private String description;
    private String entryType;
    private String modifiedBy;
    private String leaveType;
    private String leaveCategory;
    private String lstatus;
    private String purpose;
    private Date leaveFrom;
    private Date leaveTo;
    private float totalDays;
    private String address;
    private String leaveMeeting;
    private String mobile;
    private String reportingManager;
    private String ccTo;
    private String role;
    private String reason;
    /*PK*/
    private Date doj;
    //End
    //mayur
    private String leaveDates;
    private String inAccount;
    private String sdateFrom;
    private String sdateTo;
    
    
    public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public float getLwp() {
		return lwp;
	}

	public void setLwp(float lwp) {
		this.lwp = lwp;
	}

	public String getSdateFrom() {
		return sdateFrom;
	}

	public void setSdateFrom(String sdateFrom) {
		this.sdateFrom = sdateFrom;
	}

	public String getSdateTo() {
		return sdateTo;
	}

	public void setSdateTo(String sdateTo) {
		this.sdateTo = sdateTo;
	}

	public String getLeaveDates() {
		return leaveDates;
	}

	public void setLeaveDates(String leaveDates) {
		this.leaveDates = leaveDates;
	}

	public String getInAccount() {
		return inAccount;
	}

	public void setInAccount(String inAccount) {
		this.inAccount = inAccount;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	public Date getDoj() {
		return doj;
	}

	public void setDoj(Date doj) {
		this.doj = doj;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the reportingManager
	 */
	public String getReportingManager() {
		return reportingManager;
	}

	/**
	 * @param reportingManager the reportingManager to set
	 */
	public void setReportingManager(String reportingManager) {
		this.reportingManager = reportingManager;
	}

	/**
	 * @return the ccTo
	 */
	public String getCcTo() {
		return ccTo;
	}

	/**
	 * @param ccTo the ccTo to set
	 */
	public void setCcTo(String ccTo) {
		this.ccTo = ccTo;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getLeaveMeeting() {
		return leaveMeeting;
	}

	public void setLeaveMeeting(String leaveMeeting) {
		this.leaveMeeting = leaveMeeting;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getLeaveFrom() {
		return leaveFrom;
	}

	public void setLeaveFrom(Date leaveFrom) {
		this.leaveFrom = leaveFrom;
	}

	public Date getLeaveTo() {
		return leaveTo;
	}

	public void setLeaveTo(Date leaveTo) {
		this.leaveTo = leaveTo;
	}

	public float getTotalDays() {
		return totalDays;
	}

	public void setTotalDays(float totalDays) {
		this.totalDays = totalDays;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
    
    public String getLeaveMonth() {
        return leaveMonth;
    }

    public void setLeaveMonth(String leaveMonth) {
        this.leaveMonth = leaveMonth;
    }
    
    

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
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

   
    public int getEmpid() {
		return empid;
	}

	public void setEmpid(int empid) {
		this.empid = empid;
	}

	public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOd() {
        return od;
    }

    public void setOd(int od) {
        this.od = od;
    }

    public int getTotalCL() {
        return totalCL;
    }

    public void setTotalCL(int totalCL) {
        this.totalCL = totalCL;
    }

    public int getTotalEL() {
        return totalEL;
    }

    public void setTotalEL(int totalEL) {
        this.totalEL = totalEL;
    }

    public int getTotalSL() {
        return totalSL;
    }

    public void setTotalSL(int totalSL) {
        this.totalSL = totalSL;
    }

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getLeaveCategory() {
		return leaveCategory;
	}

	public void setLeaveCategory(String leaveCategory) {
		this.leaveCategory = leaveCategory;
	}

	public String getLstatus() {
		return lstatus;
	}

	public void setLstatus(String lstatus) {
		this.lstatus = lstatus;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "FaculityLeaveMasterVO [empNo=" + empid + ", id=" + id + ", name=" + name + ", designation="
				+ designation + ", department=" + department + ", image=" + Arrays.toString(image) + ", leaveMonth="
				+ leaveMonth + ", totalCL=" + totalCL + ", totalSL=" + totalSL + ", totalEL=" + totalEL + ", od=" + od
				+ ", lwp=" + lwp + ", type=" + type + ", doe=" + doe + ", dom=" + dom + ", description=" + description
				+ ", entryType=" + entryType + ", modifiedBy=" + modifiedBy + ", leaveType=" + leaveType
				+ ", leaveCategory=" + leaveCategory + ", lstatus=" + lstatus + ", purpose=" + purpose + ", leaveFrom="
				+ leaveFrom + ", leaveTo=" + leaveTo + ", totalDays=" + totalDays + ", address=" + address
				+ ", leaveMeeting=" + leaveMeeting + ", mobile=" + mobile + ", reportingManager=" + reportingManager
				+ ", ccTo=" + ccTo + ", role=" + role + ", reason=" + reason + ", doj=" + doj + ", leaveDates="
				+ leaveDates + ", inAccount=" + inAccount + ", sdateFrom=" + sdateFrom + ", sdateTo=" + sdateTo + "]";
	}
}