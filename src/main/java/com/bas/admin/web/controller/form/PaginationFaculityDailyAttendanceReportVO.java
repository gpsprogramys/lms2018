package com.bas.admin.web.controller.form;

import java.util.List;



public class PaginationFaculityDailyAttendanceReportVO{
	


	private int currentPage;
	private int noOfRecords;
	private List<FaculityDailyAttendanceReportVO> faculityDailyAttendanceReportVOList;
	private int recordsPerPage=7;
	private int noOfPages;
	
	public void initPagination(){
		noOfPages=(int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getNoOfRecords() {
		return noOfRecords;
	}

	public void setNoOfRecords(int noOfRecords) {
		this.noOfRecords = noOfRecords;
	}

	


	public List<FaculityDailyAttendanceReportVO> getFaculityDailyAttendanceReportVOList() {
		return faculityDailyAttendanceReportVOList;
	}

	public void setFaculityDailyAttendanceReportVOList(
			List<FaculityDailyAttendanceReportVO> faculityDailyAttendanceReportVOList) {
		this.faculityDailyAttendanceReportVOList = faculityDailyAttendanceReportVOList;
	}

	public int getRecordsPerPage() {
		return recordsPerPage;
	}

	public void setRecordsPerPage(int recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}

	public int getNoOfPages() {
		return noOfPages;
	}

	public void setNoOfPages(int noOfPages) {
		this.noOfPages = noOfPages;
	}

	@Override
	public String toString() {
		return "PaginationFaculityDailyAttendanceReportVO [currentPage=" + currentPage + ", noOfRecords=" + noOfRecords
				+ ", faculityDailyAttendanceReportVOList=" + faculityDailyAttendanceReportVOList + ", recordsPerPage="
				+ recordsPerPage + ", noOfPages=" + noOfPages + "]";
	}

	
	

	

}
