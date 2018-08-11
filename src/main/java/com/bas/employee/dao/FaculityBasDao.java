package com.bas.employee.dao;

import java.util.List;

import com.bas.admin.web.controller.form.AttendanceHeaderVO;
import com.bas.employee.web.controller.form.EmployeeAttendanceSummaryVO;

/**
 * 
 * @author nagendra
 * 
 */
public interface FaculityBasDao {
  
  public List<EmployeeAttendanceSummaryVO> findLastTenAttendance(int eid);
  public AttendanceHeaderVO findAttendanceHeaderByFid(int fid);
  public String markFaculityAttendance(int fid);
  public String authUser(String userid,String password);
  public String changePassword(String empid, String newpassword);
 
}
