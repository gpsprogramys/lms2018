/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bas.employee.web.controller.form;

import java.util.Date;

/**
 *
 * @author astha
 */
public class ManualAttendanceVO {
    
    private int eid;
    private String name;
    private String department;
    private String designation;
    private String type;
    private byte[] image;
    private Date cdate;
    private String detail;
    private String intime;
    private String intimeSuffix;
    private String intimestatus;
    private String outtime;
    private String outtimeSuffix;
    private String outtimestatus;
    private String present;
    
    private int rownum;

    public int getRownum() {
        return rownum;
    }

    public void setRownum(int rownum) {
        this.rownum = rownum;
    }
    
    

    public String getIntimeSuffix() {
		return intimeSuffix;
	}

	public void setIntimeSuffix(String intimeSuffix) {
		this.intimeSuffix = intimeSuffix;
	}

	public String getOuttimeSuffix() {
		return outtimeSuffix;
	}

	public void setOuttimeSuffix(String outtimeSuffix) {
		this.outtimeSuffix = outtimeSuffix;
	}

	public void setIntime(String intime) {
		this.intime = intime;
	}

	public String getIntime() {
		return intime;
	}

	public String getOuttime() {
		return outtime;
	}

	public void setOuttime(String outtime) {
		this.outtime = outtime;
	}

	public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
    
    

    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

   
    public String getIntimestatus() {
        return intimestatus;
    }

    public void setIntimestatus(String intimestatus) {
        this.intimestatus = intimestatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   

    public String getOuttimestatus() {
        return outtimestatus;
    }

    public void setOuttimestatus(String outtimestatus) {
        this.outtimestatus = outtimestatus;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

  
    @Override
    public String toString() {
        return "ManualAttendanceVO{" + "eid=" + eid + ", name=" + name + ", department=" + department + ", designation=" + designation + ", type=" + type + ", cdate=" + cdate + ", detail=" + detail + ", intime=" + intime + ", intimestatus=" + intimestatus + ", outtime=" + outtime + ", outtimestatus=" + outtimestatus + ", present=" + present + '}';
    }
    
}
