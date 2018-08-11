/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bas.admin.web.controller.form;

/**
 *
 * @author hiet
 */
public class AttendanceHeaderVO {
    
    
    private int empid;
    private String name;
    private String designation;
    private String department;
    private byte[] photo;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    

    public int getEmpid() {
        return empid;
    }

    public void setEmpid(int empid) {
        this.empid = empid;
    }
    
    

    public String getDepartment() {
        return department;
    }

    public String getDesignation() {
        return designation;
    }

    public String getName() {
        return name;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "AttendanceHeaderVO{" + "empid=" + empid + ", name=" + name + ", designation=" + designation + ", department=" + department + ", type=" + type + '}';
    }
  
}
