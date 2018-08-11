/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bas.employee.web.controller.form;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Nagendra
 */
public class EmployeeAttendanceSummaryVO implements  Serializable{
    
    private int fid;
    private Date cdate;
    private String intime;
    private String outtime;
    private String status;
    private Date dom;
    private String intimestatus;
    private String outtimestatus;
    private String detail;
    private String present;
    private String description;
    private String alert;
         

    public String getAlert() {
        return alert;
    }

    public Date getCdate() {
        return cdate;
    }

    public String getDescription() {
        return description;
    }

    public String getDetail() {
        return detail;
    }

    public Date getDom() {
        return dom;
    }

    public int getFid() {
        return fid;
    }

    public String getIntime() {
        return intime;
    }

    public String getIntimestatus() {
        return intimestatus;
    }

    public String getOuttime() {
        return outtime;
    }

    public String getOuttimestatus() {
        return outtimestatus;
    }

    public String getPresent() {
        return present;
    }

    public String getStatus() {
        return status;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setDom(Date dom) {
        this.dom = dom;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public void setIntime(String intime) {
        this.intime = intime;
    }

    public void setIntimestatus(String intimestatus) {
        this.intimestatus = intimestatus;
    }

    public void setOuttime(String outtime) {
        this.outtime = outtime;
    }

    public void setOuttimestatus(String outtimestatus) {
        this.outtimestatus = outtimestatus;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "EmployeeAttendanceSummaryVO{" + "fid=" + fid + ", cdate=" + cdate + ", intime=" + intime + ", outtime=" + outtime + ", status=" + status + ", dom=" + dom + ", intimestatus=" + intimestatus + ", outtimestatus=" + outtimestatus + ", detail=" + detail + ", present=" + present + ", description=" + description + ", alert=" + alert + '}';
    }
      
    
}
