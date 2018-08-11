/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bas.common.dao.entity;

import java.sql.Time;
import java.util.Date;

/**
 *
 * @author astha
 */
public class InOutTimeEntity {
    
    private int sno;
    private Date date;
    private Time intime;
    private Time outtime;
    private Time latein;
    private Time earlyout;
    private Date doe;
    private Date dom;
    private String userid;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public Time getEarlyout() {
        return earlyout;
    }

    public void setEarlyout(Time earlyout) {
        this.earlyout = earlyout;
    }

    public Time getIntime() {
        return intime;
    }

    public void setIntime(Time intime) {
        this.intime = intime;
    }

    public Time getLatein() {
        return latein;
    }

    public void setLatein(Time latein) {
        this.latein = latein;
    }

    public Time getOuttime() {
        return outtime;
    }

    public void setOuttime(Time outtime) {
        this.outtime = outtime;
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "InOutTimeVO{" + "sno=" + sno + ", date=" + date + ", intime=" + intime + ", outtime=" + outtime + ", latein=" + latein + ", earlyout=" + earlyout + ", doe=" + doe + ", dom=" + dom + ", userid=" + userid + '}';
    }
    
    
    
}
