package com.bas.admin.service;

import java.util.List;

import com.bas.admin.dao.entity.HolidayEntryEntity;
import com.bas.admin.web.controller.form.FaculityAttendanceReportVO;
import com.bas.admin.web.controller.form.FaculityDailyAttendanceReportVO;
import com.bas.employee.web.controller.form.ManualAttendanceVO;

/**
 *
 * @author sd
 *
 */
public interface AdminBasReportService {

	 public List<ManualAttendanceVO> findFaculityAttendanceForToday();
	 public List<FaculityAttendanceReportVO> findAllAttendanceByEmpId(int empid);
	 public List<FaculityDailyAttendanceReportVO> getAllFacultyForManualAttendance();
	 public String submitAllFacultyForManualAttendance(String[] presentEmployeeID);
	public List<HolidayEntryEntity> getHolidayCategoryList(String month, String year);
             
}
