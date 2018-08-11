package com.bas.employee.dao.entity;

import java.util.Date;

/**
 * 
 * @author kushal for new registration page
 *
 */
public class FacultyEntity {
	private long id;
	private String name;
	private String fatherName;
	private String motherName;
	private String guardianName;
	private String spouseName;
	private String email;
	private String paddress;
	private String sex;
	private String phoneNumber;
	private String dob;
	private String maritalStatus;
	private String bloodGroup;
	private String designation;
	private String department;
	private String type;
	private String category;
	private String doj;
	private String diploma;
	private String batchlourDegree;
	private String mastersDegree;
	private String postMastersDegree;
	private String otherQualification;
	private String description;
	private String active;
	private Date dom;
	private Date doe;
	private byte[] image;
	private byte[] leftThumb;
	private byte[] rightThumb;
	private String reportingManager;
	private String manager;
	
	private String password;
	private String role;
	
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getReportingManager() {
		return reportingManager;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public void setReportingManager(String reportingManager) {
		this.reportingManager = reportingManager;
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the fatherName
	 */
	public String getFatherName() {
		return fatherName;
	}
	/**
	 * @param fatherName the fatherName to set
	 */
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	/**
	 * @return the motherName
	 */
	public String getMotherName() {
		return motherName;
	}
	/**
	 * @param motherName the motherName to set
	 */
	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}
	/**
	 * @return the guardianName
	 */
	public String getGuardianName() {
		return guardianName;
	}
	/**
	 * @param guardianName the guardianName to set
	 */
	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}
	/**
	 * @return the spouseName
	 */
	public String getSpouseName() {
		return spouseName;
	}
	/**
	 * @param spouseName the spouseName to set
	 */
	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the paddress
	 */
	public String getPaddress() {
		return paddress;
	}
	/**
	 * @param paddress the paddress to set
	 */
	public void setPaddress(String paddress) {
		this.paddress = paddress;
	}
	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	/**
	 * @return the dob
	 */
	public String getDob() {
		return dob;
	}
	/**
	 * @param dob the dob to set
	 */
	public void setDob(String dob) {
		this.dob = dob;
	}
	/**
	 * @return the maritalStatus
	 */
	public String getMaritalStatus() {
		return maritalStatus;
	}
	/**
	 * @param maritalStatus the maritalStatus to set
	 */
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	/**
	 * @return the bloodGroup
	 */
	public String getBloodGroup() {
		return bloodGroup;
	}
	/**
	 * @param bloodGroup the bloodGroup to set
	 */
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	/**
	 * @return the designation
	 */
	public String getDesignation() {
		return designation;
	}
	/**
	 * @param designation the designation to set
	 */
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}
	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	/**
	 * @return the doj
	 */
	public String getDoj() {
		return doj;
	}
	/**
	 * @param doj the doj to set
	 */
	public void setDoj(String doj) {
		this.doj = doj;
	}
	/**
	 * @return the diploma
	 */
	public String getDiploma() {
		return diploma;
	}
	/**
	 * @param diploma the diploma to set
	 */
	public void setDiploma(String diploma) {
		this.diploma = diploma;
	}
	/**
	 * @return the batchlourDegree
	 */
	public String getBatchlourDegree() {
		return batchlourDegree;
	}
	/**
	 * @param batchlourDegree the batchlourDegree to set
	 */
	public void setBatchlourDegree(String batchlourDegree) {
		this.batchlourDegree = batchlourDegree;
	}
	/**
	 * @return the mastersDegree
	 */
	public String getMastersDegree() {
		return mastersDegree;
	}
	/**
	 * @param mastersDegree the mastersDegree to set
	 */
	public void setMastersDegree(String mastersDegree) {
		this.mastersDegree = mastersDegree;
	}
	/**
	 * @return the postMastersDegree
	 */
	public String getPostMastersDegree() {
		return postMastersDegree;
	}
	/**
	 * @param postMastersDegree the postMastersDegree to set
	 */
	public void setPostMastersDegree(String postMastersDegree) {
		this.postMastersDegree = postMastersDegree;
	}
	/**
	 * @return the otherQualification
	 */
	public String getOtherQualification() {
		return otherQualification;
	}
	/**
	 * @param otherQualification the otherQualification to set
	 */
	public void setOtherQualification(String otherQualification) {
		this.otherQualification = otherQualification;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the dom
	 */
	public Date getDom() {
		return dom;
	}
	/**
	 * @param dom the dom to set
	 */
	public void setDom(Date dom) {
		this.dom = dom;
	}
	/**
	 * @return the doe
	 */
	public Date getDoe() {
		return doe;
	}
	/**
	 * @param doe the doe to set
	 */
	public void setDoe(Date doe) {
		this.doe = doe;
	}
	/**
	 * @return the image
	 */
	public byte[] getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(byte[] image) {
		this.image = image;
	}
	/**
	 * @return the leftThumb
	 */
	public byte[] getLeftThumb() {
		return leftThumb;
	}
	/**
	 * @param leftThumb the leftThumb to set
	 */
	public void setLeftThumb(byte[] leftThumb) {
		this.leftThumb = leftThumb;
	}
	/**
	 * @return the rightThumb
	 */
	public byte[] getRightThumb() {
		return rightThumb;
	}
	/**
	 * @param rightThumb the rightThumb to set
	 */
	public void setRightThumb(byte[] rightThumb) {
		this.rightThumb = rightThumb;
	}

	
		

}