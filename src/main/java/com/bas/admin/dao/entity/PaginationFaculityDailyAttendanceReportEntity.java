package com.bas.admin.dao.entity;

import java.util.List;



public class PaginationFaculityDailyAttendanceReportEntity {
	private int noOfRecords;
	private List<FaculityDailyAttendanceReportEntity> FaculityDailyAttendanceReportEntityList;
	
	public int getNoOfRecords() {
		return noOfRecords;
	}
	public void setNoOfRecords(int noOfRecords) {
		this.noOfRecords = noOfRecords;
	}
	public List<FaculityDailyAttendanceReportEntity> getFaculityDailyAttendanceReportEntityList() {
		return FaculityDailyAttendanceReportEntityList;
	}
	public void setFaculityDailyAttendanceReportEntityList(List<FaculityDailyAttendanceReportEntity> faculityDailyAttendanceReportEntityList) {
		FaculityDailyAttendanceReportEntityList = faculityDailyAttendanceReportEntityList;
	}
	@Override
	public String toString() {
		return "PaginationFaculityDailyAttendanceReportEntity [noOfRecords=" + noOfRecords
				+ ", FaculityDailyAttendanceReportEntityList=" + FaculityDailyAttendanceReportEntityList + "]";
	}

}
