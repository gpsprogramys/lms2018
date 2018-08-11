package com.bas.employee.service;

import java.sql.Time;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.bas.admin.web.controller.form.EmployeeShowForm;
import com.bas.admin.web.controller.form.FaculityDailyAttendanceReportVO;
import com.bas.admin.web.controller.form.FacultyManualAttendance;
import com.bas.admin.web.controller.form.HolidayEntryForm;
import com.bas.admin.web.controller.form.OrganisationTimeForm;
import com.bas.admin.web.controller.form.PaginationFaculityDailyAttendanceReportVO;
import com.bas.common.dao.entity.EmployeeHeader;
import com.bas.employee.web.controller.form.FaculityLeaveMasterVO;
import com.bas.employee.web.controller.form.FacultyForm;
import com.bas.employee.web.controller.form.FacultyLeaveApprovalVO;
import com.bas.employee.web.controller.form.FacultyLeaveBalanceVO1;
import com.bas.employee.web.controller.form.FacultySalaryMasterVO;
import com.bas.employee.web.controller.form.FacultyWorkingDaysVO;
import com.bas.employee.web.controller.form.LeaveBalanceForm;
import com.bas.employee.web.controller.form.ManualAttendanceVO;
import com.bas.employee.web.controller.form.ReportingManagerVO;
import com.bas.employee.web.controller.form.SubjectAlternativeArrangementsVO;
import com.bas.hr.web.controller.model.EmployeeLeave;

/**
 *
 * @author Sid
 *
 */
public interface BasFacultyService {
	public String persistFaculty(FacultyForm facultyForm);

	public String updateFaculty(FacultyForm facultyForm);

	public String deletetFaculty(String name);

	public FacultyForm findFacultyByName(String name);

	public List<FacultyForm> findAllFaculty();
	public List<EmployeeShowForm> findAllEmployee();

	public byte[] findPhotoByEmpId(String empid);

	public LeaveBalanceForm findLeaveBalance(String empid);

	public List<FaculityLeaveMasterVO> findLeaveHistory(String empid);

	public List<FaculityDailyAttendanceReportVO> findAttendStatus(String fid, int monthValue);

	public List<FacultySalaryMasterVO> findSalaryHistory(String empid);

	public ManualAttendanceVO findEmployeeDataForAttendance(String empid);

	public String addEmployeeManulAttendance(ManualAttendanceVO manualAttendanceVO);

	public byte[] findEmpPhotoByName(String name);

	public String enterLeaveHistory(FaculityLeaveMasterVO faculityLeaveMasterVO);

	public List<FaculityLeaveMasterVO> findAllLeaveHistory();

	public List<FaculityLeaveMasterVO> findAllEmpDb();

	String deleteLeaveHistory(String name, String date);

	public List<FaculityLeaveMasterVO> findAllPendingLeaveHistory();

	void updateLeaveHistory(String empNo, String date, String lstatus);

	public List<FaculityLeaveMasterVO> getReportingManagerList();

	public List<FaculityLeaveMasterVO> getCCToList();

	public String enterRmLeaveHistory(FaculityLeaveMasterVO faculityLeaveMasterVO);

	public List<FaculityLeaveMasterVO> findAllRmPendingLeaveHistory();

	public FaculityLeaveMasterVO findLeaveAppData(String empid/*, String leaveMonth*/);

	public void addLeaveEntry(FaculityLeaveMasterVO faculityLeaveMasterVO);


	public String deleteAttendus(String employeeId, String attndDate);

	public List<FaculityDailyAttendanceReportVO> showAttendusReport(String date);

	public List<String> selectDepartments();

	public List<FaculityDailyAttendanceReportVO> showAttendusReportByDep(String date,
			String dep);

	public List<String> searchEmployee(String employeeName);

	public EmployeeShowForm findFacultyByNamespeci(String name);

	public String updateEmployee(FaculityDailyAttendanceReportVO dfaAttendStatusVO,
			String fid, String newdate);

	public String createEmployeeAccount(FacultyForm facultyForm);

	public List<FacultyLeaveApprovalVO> getLeaveApprovalForManager(int managerId);

	public String updateLeaveApprovalForManager(int requestID, String managerApproval);

	public ReportingManagerVO findReportingManagerByEmpId(String employeeId);

	public List<String> findRelation(String manager);

	public	String applyAddendumLeave(FaculityLeaveMasterVO faculityLeaveMasterVO);

	public List<String> selectDesignations();

	public List<String> selectReportingManagers();

	public List<String> searchEmployeeByManId(String employeeName);

	public List<String> searchId(String employeeId);

	public List<FaculityDailyAttendanceReportVO> showAttendenceReportByDepForToday(
			String dep);

	public	List<FaculityDailyAttendanceReportVO> showAttendenceReportForToday();

	public PaginationFaculityDailyAttendanceReportVO TodaysAttendanceWithPagination(int start,int noOfRecords);


	public List<FaculityDailyAttendanceReportVO> updateAttendenceForToday(
			List<String> checkedIds);

	public FacultyLeaveBalanceVO1 findFacultyLeaveBalanceById(String empId);

	public FacultyLeaveBalanceVO1 updateLeaveBalance(
			FacultyLeaveBalanceVO1 facultyLeaveBalanceVOCopy);

	public List<FaculityLeaveMasterVO> sortLeaveByDate(String d1, String d2, String eid);

	public byte[] findImgById(String empid);

	public FacultyForm showEmployee(String id);

	public List<FacultyLeaveApprovalVO> getLeaveApprovalForAdmin(String hrid);

	public String addOrganisationTime(OrganisationTimeForm organisationTimeForm);

	public List<OrganisationTimeForm> findOrgTimes();
	 public String activateOrganisationTime(String sno);

	public String updateLeaveApprovalForAdmin(int requestID, String hrApproval);

	public List<FaculityDailyAttendanceReportVO> getAttStatusByDate(String d1,
			String d2, String eid);

	public List<HolidayEntryForm> getHolidays(int m,int y);

	public List<FaculityLeaveMasterVO> LeaveHistory(String d1, String d2, String eid);

	public Map<EmployeeHeader,List<String>>  getDeptMonthlyAttendance(String department, String selectedMonth, String year);

	public void saveToFacultyWorkingTable(FacultyWorkingDaysVO fwd);

	public String markLwpService(int id, String desc);

	public List<FaculityDailyAttendanceReportVO> findAllEmployeeForAttendance();

	public List<FaculityDailyAttendanceReportVO> findAllEmployeeForByDepartment(String departmentName,String employeeType);

	public List<String> findEmpoyeeIdsForLwpByDate(String lwpDate);

	public Map<EmployeeHeader,List<String>>  getEmployeeMonthlyAttendanceStatus(String eid, String selectedMonth, String year) ;

	public List<ReportingManagerVO> findEmployeeListByManager(String managerId);

	public List<String> selectEmployeeType();

	public List<String> selectBloodGroups();

	public List<FacultyLeaveApprovalVO> getPendingLeaveRequestByEmpid(String empid);

	public EmployeeLeave findEmployeeLeaveBalance(String empid,String requestid);

	public List<FaculityDailyAttendanceReportVO> findEmployeesOnLeaveOnDate(String ondate);

	public List<FacultyLeaveApprovalVO> getLeaveApprovalForManagement(String managementId);

	public List<FacultyLeaveApprovalVO> findEmployeesLeaveAppOnDate(String ondate);

	
	public FacultyLeaveApprovalVO employeeOnLeaveDetailByAjax(String pempRequestId,String pempid);
	
	public FacultyLeaveApprovalVO empAppliedLeaveDetail(String empRequestId);
     
  public FacultyLeaveApprovalVO empApprovedLeaveDetail(String empRequestId);

  public String deleteEmployeeManagerByEmpId(String empid);

  public String deletetEmployeeById(String empid);
  
  public String activeEmployeeByEmpId(String empid);
  
  public FacultyForm  findEmplyeeByAjax(String empid);

  public List<FacultyForm> findAllFacultyByStatus(String employeeStatus);
	
  public String updateEmplyeeByAjax(FacultyForm facultyForm);

  public boolean isEmployeePresentOnHoliday(String cdate, String empid);

  public boolean checkEmployeeProfileByNameAndMobile(String mobile, String name, String email);
  
//  public List<FacultyFormForComboBox> findEmployeeDataByAjax();
  
  public String employeeManualAttendancePost(String eid,String cdate,String detail,Time intime,Time outtime);
 
  public List <FacultyManualAttendance> empManualRequestAtt(); 
 
 public String rejectEmpAttRequestById(String empid,String cdate);
 
 public String approveEmpAttRequestById(String empid,String cdate);

 public FacultyManualAttendance findEmployeeAttendanceByDate(String eid, String cdate);

 public String updateProfilePic(FacultyForm facultyForm);

 public String findEmpNameByEmpId(String empid);

public List<SubjectAlternativeArrangementsVO> findAllemployeeAlternateClassStatus();
public List<String> selectEmployeeCategory();

public Map<String, String> selectDepartmentsAsMap();

List<FacultyForm> findFacultyWithBirthday();

}
