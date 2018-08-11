package com.bas.admin.web.controller.form;

import java.util.List;

/**
 * 
 * @author Nagendra
 *
 */
public class FaculityAttendanceReportVOWrapper {

	private int empId;
	private String name;
	private String department;
	private String designation;
	private String cdate;
	private String logo;
	private String cphoto;
	private List<FaculityAttendanceReportVO> attendanceReportVOs;

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getCdate() {
		return cdate;
	}

	public void setCdate(String cdate) {
		this.cdate = cdate;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getCphoto() {
		return cphoto;
	}

	public void setCphoto(String cphoto) {
		this.cphoto = cphoto;
	}

	public List<FaculityAttendanceReportVO> getAttendanceReportVOs() {
		return attendanceReportVOs;
	}

	public void setAttendanceReportVOs(
			List<FaculityAttendanceReportVO> attendanceReportVOs) {
		this.attendanceReportVOs = attendanceReportVOs;
	}

	@Override
	public String toString() {
		return "FaculityAttendanceReportVOWrapper [empId=" + empId + ", name="
				+ name + ", department=" + department + ", designation="
				+ designation + ", cdate=" + cdate + ", logo=" + logo
				+ ", cphoto=" + cphoto + ", attendanceReportVOs="
				+ attendanceReportVOs + "]";
	}
}
