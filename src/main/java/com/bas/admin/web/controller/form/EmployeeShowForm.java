package com.bas.admin.web.controller.form;

import java.util.Arrays;

public class EmployeeShowForm {
	
private int id;
private String name;
private String email;
private int mobile;
private String designation;
private String department;
private byte[] image;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public int getMobile() {
	return mobile;
}
public void setMobile(int mobile) {
	this.mobile = mobile;
}
public String getDesignation() {
	return designation;
}
public void setDesignation(String designation) {
	this.designation = designation;
}
public String getDepartment() {
	return department;
}
public void setDepartment(String department) {
	this.department = department;
}
public byte[] getImage() {
	return image;
}
public void setImage(byte[] image) {
	this.image = image;
}
@Override
public String toString() {
	return "EmployeeShowForm [id=" + id + ", name=" + name + ", email=" + email
			+ ", mobile=" + mobile + ", designation=" + designation
			+ ", department=" + department + ", image="
			+ Arrays.toString(image) + "]";
}



}
