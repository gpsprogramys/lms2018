package com.bas.employee.dao;

import java.sql.Time;
import java.util.List;
import java.util.Map;

import com.bas.admin.dao.entity.EmployeeMonthAttendanceEntity;
import com.bas.admin.dao.entity.FaculityDailyAttendanceReportEntity;
import com.bas.admin.dao.entity.FacultyManualAttendanceEntity;
import com.bas.admin.dao.entity.HolidayEntryEntity;
import com.bas.admin.dao.entity.OrganizationTimeEntity;
import com.bas.admin.dao.entity.PaginationFaculityDailyAttendanceReportEntity;
import com.bas.common.dao.entity.EmployeeHeader;
import com.bas.employee.dao.entity.EmployeeShowFormEntity;
import com.bas.employee.dao.entity.FaculityLeaveMasterEntity;
import com.bas.employee.dao.entity.FacultyAttendStatusEntity;
import com.bas.employee.dao.entity.FacultyEntity;
import com.bas.employee.dao.entity.FacultyLeaveApprovalEntity;
import com.bas.employee.dao.entity.FacultyLeaveBalanceEntity1;
import com.bas.employee.dao.entity.FacultyLeaveTypeEntity;
import com.bas.employee.dao.entity.FacultySalaryMasterEntity;
import com.bas.employee.dao.entity.FacultyWorkingDaysEntity;
import com.bas.employee.dao.entity.LeaveBalanceEntity;
import com.bas.employee.dao.entity.ManualAttendanceEntity;
import com.bas.employee.dao.entity.ReportingManagerEntity;
import com.bas.employee.dao.entity.SubjectAlternativeArrangementsEntity;
import com.bas.employee.web.controller.form.FaculityLeaveMasterVO;
import com.bas.employee.web.controller.form.FacultyForm;

/**
 *
 * @author sssss
 *
 */
public interface BasFacultyDao {
	public String persistFaculty(FacultyEntity facultyEntity);
	public String updateFaculty(FacultyEntity facultyEntity);
	public String deletetFaculty(String name);
	public String deletetEmployeeById(String empid);
	public String activeEmployeeByEmpId(String empid);
	public FacultyEntity findFacultyByName(String name);
//	public List<FacultyEntity> findAllFaculty();
	public List<FacultyEntity> findAllFaculty();
	public List<FacultyEntity> findAllFacultyByStatus(String employeeStatus);
	public byte[] findPhotoByEmpId(String name);
	public LeaveBalanceEntity findLeaveBalance(String empid);
	public List<FaculityDailyAttendanceReportEntity> findAttendStatus(String fid,int monthValue);
	public List<FaculityLeaveMasterEntity> findLeaveHistory(String empid);
	public List<FacultySalaryMasterEntity> findSalaryHistory(String empid);
	public byte[] findPhotoByEmpName(String name);
	public String enterLeaveHistory(FaculityLeaveMasterVO faculityLeaveMasterVO);
	public List<FaculityLeaveMasterEntity> findAllLeaveHistory();
	public List<FaculityLeaveMasterEntity> findAllEmpDb();
	public String deleteLeaveHistory(String name, String date);
	public ManualAttendanceEntity findEmployeeDataForAttendance(String empid);
	public String addEmployeeManulAttendance(ManualAttendanceEntity manualAttendanceEntity);
	public List<FaculityLeaveMasterEntity> findAllPendingLeaveHistory();
	public void updateLeaveHistory(String empNo, String date, String lstatus);
	public List<FaculityLeaveMasterEntity> getReportingManagerList();
	public List<FaculityLeaveMasterEntity> getCCToList();
	public String enterRmLeaveHistory(FaculityLeaveMasterVO faculityLeaveMasterVO);
	public List<FaculityLeaveMasterEntity> findAllRmPendingLeaveHistory();
	public FaculityLeaveMasterEntity findLeaveAppData(String empid/*, String leaveMonth*/);
	public void addLeaveEntry(FaculityLeaveMasterEntity faculityLeaveMasterEntity);
	public String deleteAttendus(String employeeId, String attndDate);
	public List<String> selectDepartments();
	public List<FaculityDailyAttendanceReportEntity> showAttendusReport(String date);
	public List<FaculityDailyAttendanceReportEntity> showAttendusReportByDep(
			String date, String dep);
	public List<String> searchEmployee(String employeeName);
	public EmployeeShowFormEntity findFacultyByNamespecific(String name);
	public String updateEmployee(
			FaculityDailyAttendanceReportEntity facultyAttendStatusEntity,
			String fid, String newdate);
	public String createEmployeeAccount(FacultyEntity facultyEntity);
	public List<FacultyLeaveApprovalEntity> getLeaveApprovalForManager(int managerId);
	public String updateLeaveApprovalForManager(int requestID, String managerApproval);
	public ReportingManagerEntity findReportingManagerByEmpId(String employeeId);
	public List<String> findRelation(String manager);
	public String applyAddendumLeave(FaculityLeaveMasterEntity al);
	public List<String> selectReportingManager();
	public List<String> selectDesignations();
	public List<String> searchEmployeeByManId(String employeeName);
	public List<String> searchId(String employeeId);
	public List<FaculityDailyAttendanceReportEntity> showAttendenceForTodayForSelectedDept(
			String dep);
	public List<FaculityDailyAttendanceReportEntity> updateAttendenceForToday(
			List<String> checkedIds);
	public List<FaculityDailyAttendanceReportEntity> showAttendenceforTodayAllDepts();
	public PaginationFaculityDailyAttendanceReportEntity TodaysAttendanceWithPagination(int start,int noOfRecords);
	public FacultyLeaveBalanceEntity1 findFacultyLeaveBalanceById(String employeeId);
	public FacultyLeaveBalanceEntity1 updateLeaveBalance(
			FacultyLeaveBalanceEntity1 facultyLeaveBalanceEntity);
	public List<FaculityLeaveMasterEntity> sortLeaveHistoryByDate(String date1,
			String date2, String empid);
	public byte[] findImageById(String empid);
	public FacultyEntity showEmployee(String id);
	public List<FacultyLeaveApprovalEntity> getLeaveApprovalForAdmin(String hrid);
	public String updateLeaveApprovalForAdmin(int requestID, String hrApproval);
	public List<FaculityDailyAttendanceReportEntity> getAttendanceStatusByDate(
			String d1, String d2, String eid);
	public List<HolidayEntryEntity> getHoliday(int month,int year);
	public List<FaculityLeaveMasterEntity> LeaveHistoryByDate(String date1,
			String date2, String empid);
	public void saveToFacultyWorkingTable(FacultyWorkingDaysEntity fwdEntity);
	public String markLwpInDb(int id, String description);
	public List<FaculityDailyAttendanceReportEntity> findAllEmployeeForAttendance();
	public String addOrganisationTime(OrganizationTimeEntity organisationTimeEntity);
	public List<OrganizationTimeEntity> findOrgTimes();
	public String activateOrganisationTime(String sno);
	public List<FaculityDailyAttendanceReportEntity> findAllEmployeeForByDepartmentAndType(String departmentName,String employeeType);
	public List<String> findEmpoyeeIdsForLwpByDate(String lwpDate);
	public List<ReportingManagerEntity> findEmployeeListByManager(String managerId);
	public List<String> selectEmployeeType();
	public List<String> selectBloodGroups();
	public List<EmployeeMonthAttendanceEntity> getEmployeeMonthlyAttendanceInfo(String eid, String selectedMonth, String year);
	public List<HolidayEntryEntity> getHolidayCategoryList(String month, String year, Map<EmployeeHeader, List<String>> employeeMonthlyLeaveStatus);
	public List<FacultyLeaveTypeEntity> getLeaveCategoryList(String eid, String month, String year, Map<EmployeeHeader, List<String>> employeeMonthlyLeaveStatus,
			int maxDate);
	public List<EmployeeMonthAttendanceEntity> getDeptMonthlyAttendanceInfo(String department, String month, String year);
	public List<FacultyLeaveTypeEntity> getDeptLeaveCategoryList(String department, String month, String year,
			Map<EmployeeHeader, List<String>> employeeMonthlyLeaveStatus, int maxDate);
	public List<FacultyLeaveApprovalEntity> getPendingLeaveRequestByEmpid(String empid);
	public List<FaculityDailyAttendanceReportEntity> findEmployeesOnLeaveOnDate(String ondate);
	public List<FacultyLeaveApprovalEntity> getLeaveApprovalForManagement(String managementId);
	public FacultyLeaveApprovalEntity findCurrentClByRequestId(String requestId);
	public List<FacultyLeaveApprovalEntity> findEmployeesLeaveAppOnDate(String ondate);
	public FacultyLeaveApprovalEntity employeeOnLeaveDetailByAjax(String pempRequestId,String pempid);
	
	
	public FacultyLeaveApprovalEntity  empAppliedLeaveDetail(String empRequestId);
	public FacultyLeaveApprovalEntity empApprovedLeaveDetail(String empRequestId);
	public String deleteEmployeeManagerByEmpId(String empid);
	
	public FacultyEntity findEmplyeeByAjax(String empid);
	
	public String updateEmplyeeByAjax(FacultyForm facultyForm);
	public boolean isEmployeePresentOnHoliday(String cdate, String empid);
	public boolean checkEmployeeProfileByNameAndMobile(String mobile, String name,String email);
	
	//public List<FacultyEntityForComboBox> findEmployeeDataByAjax();
	
	public String employeeManualAttendancePost(String eid,String cdate, String detail, Time intime, Time outtime);
	public List<FacultyManualAttendanceEntity>empManualRequestAtt();
	public String rejectEmpAttRequestById(String empid,String cdate);
	public String approveEmpAttRequestById(String empid,String cdate);
	public FacultyAttendStatusEntity findEmployeeAttendanceByDate(String eid, String cdate);
	public String updateProfilePic(FacultyEntity facultyEntity);
	public String findEmpNameByEmpId(String empid);
	public List<SubjectAlternativeArrangementsEntity> findAllemployeeAlternateClassStatus();
	public List<String> selectEmployeeCategory();
	public Map<String, String> selectDepartmentsAsMap();
	List<FacultyEntity> findFacultyWithBirthday();

}