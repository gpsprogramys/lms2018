package com.bas.employee.dao;

import java.util.Date;
import java.util.List;

import com.bas.common.dao.entity.EmployeeLeaveDetailEntity;
import com.bas.common.dao.entity.MessageStatusEntity;
import com.bas.common.dao.entity.SuggestionManagerOptionEntity;
import com.bas.common.dao.entity.SuggestionOptionEntity;
import com.bas.common.web.controller.form.AllEmployeeDetailsEntity;
import com.bas.employee.dao.entity.EmployeeLeaveRequestEntity;
import com.bas.employee.dao.entity.SubjectAlternativeArrangementsEntity;
import com.synergy.bank.employee.dao.entity.EmployeeDetailsEntity;
import com.synergy.bank.employee.dao.entity.EmployeeLeaveBalanceEntity;


public interface IEmployeeLMSDao 
{
	
	public int findTotaPendingLeaveCount(String role,String empid);
	
	public EmployeeDetailsEntity findEmployeeById(EmployeeDetailsEntity employeeDetailsEntity);

	public EmployeeLeaveBalanceEntity getLeaveBalanceByEmpId(EmployeeLeaveBalanceEntity employeeLeaveBalanceEntity);
	public MessageStatusEntity saveLeaveRequest(EmployeeLeaveRequestEntity employeeLeaveRequestEntity);
	public int getReportingManagerId(int empId);
	public EmployeeDetailsEntity getReportingManagerForEmployee(int managerId);
	public int findNoOfHolidays(Date leaveFrom, Date leaveTo);
	public String getEmployeeName(int empId);
	public List<SuggestionOptionEntity> findEmployeeSuggestionOption(String searchword,String empid);
	public EmployeeLeaveDetailEntity findEmployeeLeaveDetail(String eid);
	public List<SuggestionManagerOptionEntity> findEmplyeeSuggestionManager(String searchword, String manager_id);
	public List<SuggestionOptionEntity> findEmplyeeSuggestionOptionForLeaveHistory(String searchword);
	public String isLeaveAlreadyApplied(EmployeeLeaveRequestEntity entity);
	public List<String> findfHolidaysDatesInBetween(Date leaveFrom, Date leaveTo);
	public EmployeeLeaveDetailEntity findEmployeeLeaveDetailForManager(String eid);
	public String deleteEmployeeLwpByDate(String lwpDate, String empid);

	public AllEmployeeDetailsEntity findEmployeeDetails(String eid);

	public void addAlternativeArrangementsFaculty(String[] name, String[] branch, String[] period, String[] subject,
			String[] date, String userid, Date doe, Date dom, String fid);

	public List<SubjectAlternativeArrangementsEntity> findAlternateArrangementPeriodFarFaculty(String username);

	public String findFacultyById(String submitedby);

	public String updateStatusForAlternateClass(String id, String status);

	public List<SubjectAlternativeArrangementsEntity> findAllAlternativeClassDetailsForHrHod(String empid);

	public String updateBasicProfile(String eid, String dob, String mobile, String password, String email);
	


	
}
