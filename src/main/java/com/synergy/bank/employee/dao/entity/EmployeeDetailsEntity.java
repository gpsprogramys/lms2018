package com.synergy.bank.employee.dao.entity;

import java.util.Arrays;
import java.util.Date;

public class EmployeeDetailsEntity 
{
	private int id;
	private String name;
	private String fatherName;
	private String motherName;
	private String spouseName;
	private String guardianName;
	private String email;
	private String paddress;
	private String phoneNumber;
	private String dob;
	private String sex;
	private String maritalStatus;
	private String bloodGroup;
	private String designation; 
	private String department;
	private String type;
	private String category;
	private String doj;
	private String diploma;
	private String bachelorDegree;
	private String mastersDegree;
	private String postMastersDegree;
	private String otherQualification;
	private byte[]	image;
	private byte[] leftThumb;
	private byte[] rightThumb;
	private String description;
	private String reportingManager;
	private Date dom;
	private Date doe;
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
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public String getMotherName() {
		return motherName;
	}
	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}
	public String getSpouseName() {
		return spouseName;
	}
	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}
	public String getGuardianName() {
		return guardianName;
	}
	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPaddress() {
		return paddress;
	}
	public void setPaddress(String paddress) {
		this.paddress = paddress;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDoj() {
		return doj;
	}
	public void setDoj(String doj) {
		this.doj = doj;
	}
	public String getDiploma() {
		return diploma;
	}
	public void setDiploma(String diploma) {
		this.diploma = diploma;
	}
	public String getBachelorDegree() {
		return bachelorDegree;
	}
	public void setBachelorDegree(String bachelorDegree) {
		this.bachelorDegree = bachelorDegree;
	}
	public String getMastersDegree() {
		return mastersDegree;
	}
	public void setMastersDegree(String mastersDegree) {
		this.mastersDegree = mastersDegree;
	}
	public String getPostMastersDegree() {
		return postMastersDegree;
	}
	public void setPostMastersDegree(String postMastersDegree) {
		this.postMastersDegree = postMastersDegree;
	}
	public String getOtherQualification() {
		return otherQualification;
	}
	public void setOtherQualification(String otherQualification) {
		this.otherQualification = otherQualification;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public byte[] getLeftThumb() {
		return leftThumb;
	}
	public void setLeftThumb(byte[] leftThumb) {
		this.leftThumb = leftThumb;
	}
	public byte[] getRightThumb() {
		return rightThumb;
	}
	public void setRightThumb(byte[] rightThumb) {
		this.rightThumb = rightThumb;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getReportingManager() {
		return reportingManager;
	}
	public void setReportingManager(String reportingManager) {
		this.reportingManager = reportingManager;
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
	
	
	
	@Override
	public String toString() {
		return "EmployeeDetailsForm [id=" + id + ", name=" + name + ", fatherName=" + fatherName + ", motherName="
				+ motherName + ", spouseName=" + spouseName + ", guardianName=" + guardianName + ", email=" + email
				+ ", paddress=" + paddress + ", phoneNumber=" + phoneNumber + ", dob=" + dob + ", sex=" + sex
				+ ", maritalStatus=" + maritalStatus + ", bloodGroup=" + bloodGroup + ", designation=" + designation
				+ ", department=" + department + ", type=" + type + ", category=" + category + ", doj=" + doj
				+ ", diploma=" + diploma + ", bachelorDegree=" + bachelorDegree + ", mastersDegree=" + mastersDegree
				+ ", postMastersDegree=" + postMastersDegree + ", otherQualification=" + otherQualification + ", image="
				+ Arrays.toString(image) + ", leftThumb=" + Arrays.toString(leftThumb) + ", rightThumb="
				+ Arrays.toString(rightThumb) + ", description=" + description + ", reportingManager="
				+ reportingManager + ", dom=" + dom + ", doe=" + doe + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmployeeDetailsEntity other = (EmployeeDetailsEntity) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
