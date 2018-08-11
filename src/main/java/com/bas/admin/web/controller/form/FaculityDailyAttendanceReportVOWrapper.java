package com.bas.admin.web.controller.form;

import java.util.List;

/**
 * 
 * @author astha
 *  Wrapper class which will be used to generate the report in pdf format
 */
public class FaculityDailyAttendanceReportVOWrapper {

	private String cdate;
	private String cttiaLogo;
	private String collegeLogo;
	private List<FaculityDailyAttendanceReportVO> dailyDailyAttendanceReportVOs;

	public String getCdate() {
		return cdate;
	}

	public void setCdate(String cdate) {
		this.cdate = cdate;
	}

	public String getCttiaLogo() {
		return cttiaLogo;
	}

	public void setCttiaLogo(String cttiaLogo) {
		this.cttiaLogo = cttiaLogo;
	}

	public String getCollegeLogo() {
		return collegeLogo;
	}

	public void setCollegeLogo(String collegeLogo) {
		this.collegeLogo = collegeLogo;
	}

	public List<FaculityDailyAttendanceReportVO> getDailyDailyAttendanceReportVOs() {
		return dailyDailyAttendanceReportVOs;
	}

	public void setDailyDailyAttendanceReportVOs(
			List<FaculityDailyAttendanceReportVO> dailyDailyAttendanceReportVOs) {
		this.dailyDailyAttendanceReportVOs = dailyDailyAttendanceReportVOs;
	}

}
