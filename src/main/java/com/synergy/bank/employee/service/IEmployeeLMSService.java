package com.synergy.bank.employee.service;

import java.util.Date;
import java.util.List;

import com.bas.admin.web.controller.form.MessageStatus;
import com.bas.common.web.controller.form.EmployeeDetailsVO;
import com.bas.common.web.controller.form.EmployeeLeaveDetailVO;
import com.bas.employee.web.controller.form.SubjectAlternativeArrangementsVO;
import com.bas.employee.web.controller.form.EmployeeDetailsForm;
import com.bas.employee.web.controller.form.EmployeeLeaveBalanceForm;
import com.bas.employee.web.controller.form.EmployeeLeaveRequestForm;
import com.bas.hr.web.controller.model.SuggestionManagerOptionVO;
import com.bas.hr.web.controller.model.SuggestionOptionVO;


public interface IEmployeeLMSService{
	public EmployeeDetailsForm findEmployeeById(EmployeeDetailsForm employeeDetailsForm);
	public EmployeeLeaveBalanceForm getLeaveBalanceByEmpId(EmployeeLeaveBalanceForm employeeLeaveBalanceForm);;
	public MessageStatus saveLeaveRequest(EmployeeLeaveRequestForm employeeLeaveRequestForm);
	public int getReportingManagerId(int empId);
	public EmployeeDetailsForm getReportingManagerForEmployee(int managerId);
	public int findNoOfHolidays(Date leaveFrom, Date leaveTo);
	public void sendEmails(EmployeeLeaveRequestForm leaveFormData, EmployeeDetailsForm detailsForm);
	public String getEmployeeName(int empId);
	public List<SuggestionOptionVO> findEmployeeSuggestionOption(String searchword,String empid);
	public EmployeeLeaveDetailVO findEmployeeLeaveDetail(String eid);
	public List<SuggestionOptionVO> findEmplyeeSuggestionOptionForLeaveHistory(
			String searchword);
	public List<SuggestionManagerOptionVO> findEmplyeeSuggestionManager(String searchword, String manager_id);
	public String isLeaveAlreadyApplied(EmployeeLeaveRequestForm employeeLeaveRequestForm);
	public List<String> findfHolidaysDatesInBetween(Date leaveFrom, Date leaveTo);
	public EmployeeLeaveDetailVO findEmployeeLeaveDetailForManager(String eid);
	public String deleteEmployeeLwpByDate(String lwpDate, String empid);
	public EmployeeDetailsForm relationOfEmployeeToManagerById(EmployeeDetailsForm employeeDetailsForm);
	public EmployeeDetailsVO findEmployeeDetails(String eid);
	public int findTotaPendingLeaveCount(String role,String empid);
	public void addAlternativeArrangementsFaculty(String[] name, String[] branch, String[] period, String[] subject,
			String[] date, String userid, Date doe, Date dom, String fid);
	List<SubjectAlternativeArrangementsVO> findAlternateArrangementPeriodFarFaculty(String username);
	public String updateStatusForAlternateClass(String id, String status);
	public List<SubjectAlternativeArrangementsVO> findAllAlternativeClassDetailsForHrHod(String id);
	public String updateBasicProfile(String eid, String dob, String mobile, String password, String email);
	

}
