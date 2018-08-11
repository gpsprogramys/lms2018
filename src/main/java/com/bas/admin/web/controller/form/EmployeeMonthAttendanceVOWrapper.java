package com.bas.admin.web.controller.form;

import java.util.List;

public class EmployeeMonthAttendanceVOWrapper {
	private List<EmployeeMonthAttendanceVO> employeeMonthAttendanceVOs;
	private int totalDays;
	private int totalHolidays;
	private int totalWorkingDays;

	public List<EmployeeMonthAttendanceVO> getEmployeeMonthAttendanceVOs() {
		return employeeMonthAttendanceVOs;
	}

	public void setEmployeeMonthAttendanceVOs(List<EmployeeMonthAttendanceVO> employeeMonthAttendanceVOs) {
		this.employeeMonthAttendanceVOs = employeeMonthAttendanceVOs;
	}

	public int getTotalDays() {
		return totalDays;
	}

	public void setTotalDays(int totalDays) {
		this.totalDays = totalDays;
	}

	public int getTotalHolidays() {
		return totalHolidays;
	}

	public void setTotalHolidays(int totalHolidays) {
		this.totalHolidays = totalHolidays;
	}

	public int getTotalWorkingDays() {
		return totalWorkingDays;
	}

	public void setTotalWorkingDays(int totalWorkingDays) {
		this.totalWorkingDays = totalWorkingDays;
	}

	@Override
	public String toString() {
		return "EmployeeMonthAttendanceVOWrapper [employeeMonthAttendanceVOs=" + employeeMonthAttendanceVOs
				+ ", totalDays=" + totalDays + ", totalHolidays=" + totalHolidays + ", totalWorkingDays="
				+ totalWorkingDays + "]";
	}

}
