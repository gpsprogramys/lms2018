package com.bas.employee.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bas.admin.web.controller.form.MessageStatus;
import com.bas.common.dao.entity.EmployeeLeaveDetailEntity;
import com.bas.common.dao.entity.MessageStatusEntity;
import com.bas.common.dao.entity.SuggestionManagerOptionEntity;
import com.bas.common.dao.entity.SuggestionOptionEntity;
import com.bas.common.web.controller.form.AllEmployeeDetailsEntity;
import com.bas.common.web.controller.form.EmployeeDetailsVO;
import com.bas.common.web.controller.form.EmployeeLeaveDetailVO;
import com.bas.employee.dao.IEmployeeLMSDao;
import com.bas.employee.dao.entity.EmployeeLeaveRequestEntity;
import com.bas.employee.dao.entity.SubjectAlternativeArrangementsEntity;
import com.bas.employee.web.controller.form.EmployeeDetailsForm;
import com.bas.employee.web.controller.form.EmployeeLeaveBalanceForm;
import com.bas.employee.web.controller.form.EmployeeLeaveRequestForm;
import com.bas.employee.web.controller.form.SubjectAlternativeArrangementsVO;
import com.bas.hr.web.controller.model.SuggestionManagerOptionVO;
import com.bas.hr.web.controller.model.SuggestionOptionVO;
import com.synergy.bank.employee.dao.entity.EmployeeDetailsEntity;
import com.synergy.bank.employee.dao.entity.EmployeeLeaveBalanceEntity;
import com.synergy.bank.employee.service.IEmployeeLMSService;

@Service("EmployeeLMSService")
@Scope("singleton")
@Transactional
public class EmployeeLMSService implements IEmployeeLMSService {

	/*@Autowired
	@Qualifier("EMailSenderServiceImpl")
	private EMailSenderServiceImpl bankEmailService;*/

	@Autowired
	@Qualifier("EmployeeLMSDao")
	private IEmployeeLMSDao iEmployeeLMSDao;
	
	@Override
	public EmployeeDetailsForm relationOfEmployeeToManagerById(
			EmployeeDetailsForm employeeDetailsForm) {
		EmployeeDetailsEntity employeeDetailsEntity = new EmployeeDetailsEntity();
		BeanUtils.copyProperties(employeeDetailsForm, employeeDetailsEntity);
		EmployeeDetailsForm detailsForm = new EmployeeDetailsForm();
		BeanUtils.copyProperties(
				iEmployeeLMSDao.findEmployeeById(employeeDetailsEntity),
				detailsForm);
		return detailsForm;

	}

	@Override
	public EmployeeDetailsForm findEmployeeById(
			EmployeeDetailsForm employeeDetailsForm) {
		EmployeeDetailsEntity employeeDetailsEntity = new EmployeeDetailsEntity();
		BeanUtils.copyProperties(employeeDetailsForm, employeeDetailsEntity);
		EmployeeDetailsForm detailsForm = new EmployeeDetailsForm();
		BeanUtils.copyProperties(
				iEmployeeLMSDao.findEmployeeById(employeeDetailsEntity),
				detailsForm);
		return detailsForm;

	}

	@Override
	public EmployeeLeaveBalanceForm getLeaveBalanceByEmpId(
			EmployeeLeaveBalanceForm employeeLeaveBalanceForm) {
		EmployeeLeaveBalanceEntity balanceEntity = new EmployeeLeaveBalanceEntity();
		BeanUtils.copyProperties(employeeLeaveBalanceForm, balanceEntity);
		EmployeeLeaveBalanceForm balanceForm = new EmployeeLeaveBalanceForm();
		BeanUtils.copyProperties(
				iEmployeeLMSDao.getLeaveBalanceByEmpId(balanceEntity),
				balanceForm);
		return balanceForm;
	}
	
	@Override
	public String isLeaveAlreadyApplied(EmployeeLeaveRequestForm employeeLeaveRequestForm) {
		EmployeeLeaveRequestEntity employeeLeaveRequestEntity = new EmployeeLeaveRequestEntity();
		BeanUtils.copyProperties(employeeLeaveRequestForm,
				employeeLeaveRequestEntity);
		return iEmployeeLMSDao.isLeaveAlreadyApplied(employeeLeaveRequestEntity);
	}

  @Transactional(propagation=Propagation.REQUIRED)
	@Override
	public MessageStatus saveLeaveRequest(
			EmployeeLeaveRequestForm employeeLeaveRequestForm) {
		EmployeeLeaveRequestEntity employeeLeaveRequestEntity = new EmployeeLeaveRequestEntity();
		BeanUtils.copyProperties(employeeLeaveRequestForm,
				employeeLeaveRequestEntity);
		MessageStatusEntity messageStatusEntity=iEmployeeLMSDao.saveLeaveRequest(employeeLeaveRequestEntity);
		MessageStatus messageStatus=new MessageStatus();
		BeanUtils.copyProperties(messageStatusEntity,messageStatus);
		return messageStatus;
	}

	@Override
	public EmployeeDetailsForm getReportingManagerForEmployee(int managerId) {
		EmployeeDetailsForm detailsForm = new EmployeeDetailsForm();
		BeanUtils.copyProperties(
				iEmployeeLMSDao.getReportingManagerForEmployee(managerId),
				detailsForm);
		return detailsForm;
	}

	@Override
	public int findNoOfHolidays(Date leaveFrom, Date leaveTo) {
		int noOfHolidays = iEmployeeLMSDao.findNoOfHolidays(leaveFrom, leaveTo);
		return noOfHolidays;
	}
	
	@Override
	public List<String> findfHolidaysDatesInBetween(Date leaveFrom, Date leaveTo) {
		return iEmployeeLMSDao.findfHolidaysDatesInBetween(leaveFrom, leaveTo);
	}

	@Override
	public int getReportingManagerId(int empId) {
		int reportingManagerId = iEmployeeLMSDao.getReportingManagerId(empId);

		return reportingManagerId;
	}

	@Override
	public void sendEmails(EmployeeLeaveRequestForm leaveFormData,
			EmployeeDetailsForm detailsForm) {
		System.out.println("sending email....");
		String messageBody = "The leave application for Employee is submitted for further approval. \n"
				+ "Details of the application are: \n"
				+ "Employee Name: "
				+ detailsForm.getName()
				+ "\n"
				+ "Employee Id : "
				+ detailsForm.getId()
				+ "\n"
				+ "Reporting Manager "
				+ leaveFormData.getReportingManager()
				+ "\n"
				+ "Mobile No: "
				+ leaveFormData.getMobile()
				+ "\n"
				+ "No of Days leaves applied for :"
				+ leaveFormData.getTotalDays() + " days \n";
		String subject = "Leave Application : Employee Name :"
				+ leaveFormData.getEmpName() + "(" + detailsForm.getId() + ")";
		// String body =
		// "Leave has been applied for "+leaveFormData.getTotalDays()+" days by the employee :"+leaveFormData.getName();
		// String subject =
		// "Leave Application : Employee Name :"+leaveFormData.getName();
		// EmailSenderThread emailSenderThread = new EmailSenderThread(
		// bankEmailService, emailIds, body, subject);
		// emailSenderThread.start();

		// email for the reporting manager
		/*EmailSenderThread emailSenderThread = new EmailSenderThread(
				bankEmailService, detailsForm.getEmail(), messageBody, subject);
		emailSenderThread.start();*/

		// email for CCs
		/*String CCTo = leaveFormData.getCCTo();
		String[] emails = CCTo.split(",");
		for (int i = 0; i < emails.length; i++) {
			if (emails[i].contains("@")) {
				EmailSenderThread emailSender = new EmailSenderThread(
						bankEmailService, emails[i], messageBody, subject);
				emailSender.start();
			}
		}*/

	}

	@Override
	public String getEmployeeName(int empId) {
		return iEmployeeLMSDao.getEmployeeName(empId);
	}

	@Override
	public List<SuggestionOptionVO> findEmployeeSuggestionOption(
			String searchword,String empid) {
		List<SuggestionOptionEntity> suggestionOptionEntityList = iEmployeeLMSDao
				.findEmployeeSuggestionOption(searchword,empid);
		List<SuggestionOptionVO> suggestionOptionList = new ArrayList<SuggestionOptionVO>();
		for (SuggestionOptionEntity soe : suggestionOptionEntityList) {
			SuggestionOptionVO suggestionOptionVO = new SuggestionOptionVO();
			BeanUtils.copyProperties(soe, suggestionOptionVO);
			suggestionOptionList.add(suggestionOptionVO);
		}
		return suggestionOptionList;
	}
	
	@Override
	public List<SuggestionOptionVO> findEmplyeeSuggestionOptionForLeaveHistory(String searchword) {
		List<SuggestionOptionEntity> suggestionOptionEntityList = iEmployeeLMSDao.findEmplyeeSuggestionOptionForLeaveHistory(searchword);
		List<SuggestionOptionVO> suggestionOptionList = new ArrayList<SuggestionOptionVO>();
		for (SuggestionOptionEntity soe : suggestionOptionEntityList) {
			SuggestionOptionVO suggestionOptionVO = new SuggestionOptionVO();
			BeanUtils.copyProperties(soe, suggestionOptionVO);
			suggestionOptionList.add(suggestionOptionVO);
		}
		return suggestionOptionList;
	}
	
	@Override
	public List<SuggestionManagerOptionVO> findEmplyeeSuggestionManager(String searchword, String manager_id) {
		List<SuggestionManagerOptionEntity> suggestionOptionEntityList = iEmployeeLMSDao.findEmplyeeSuggestionManager(searchword, manager_id);
		List<SuggestionManagerOptionVO> suggestionOptionList = new ArrayList<SuggestionManagerOptionVO>();
		for (SuggestionManagerOptionEntity soe : suggestionOptionEntityList) {
			SuggestionManagerOptionVO suggestionOptionVO = new SuggestionManagerOptionVO();
			BeanUtils.copyProperties(soe, suggestionOptionVO);
			suggestionOptionList.add(suggestionOptionVO);
		}
		return suggestionOptionList;
	}

	@Override
	public String deleteEmployeeLwpByDate(String lwpDate,String empid) {
			return iEmployeeLMSDao.deleteEmployeeLwpByDate(lwpDate, empid);
	}

	@Override
	public EmployeeLeaveDetailVO findEmployeeLeaveDetail(String eid) {
		EmployeeLeaveDetailEntity employeeLeaveDetailEntity = iEmployeeLMSDao
				.findEmployeeLeaveDetail(eid);
		EmployeeLeaveDetailVO employeeLeaveDetailVO =null; 
		if(employeeLeaveDetailEntity!=null) {
			    employeeLeaveDetailVO=new EmployeeLeaveDetailVO();
				BeanUtils.copyProperties(employeeLeaveDetailEntity,
					employeeLeaveDetailVO);
			}
		return employeeLeaveDetailVO;
	}
	
	@Override
	public EmployeeDetailsVO findEmployeeDetails(String eid) {
		/*EmployeeDetailsEntity employeeDetailEntity = iEmployeeLMSDao
				.findEmployeeDetails(eid);*/
		AllEmployeeDetailsEntity employeeDetailEntity = iEmployeeLMSDao.findEmployeeDetails(eid);
		EmployeeDetailsVO employeeDetailVO =null; 
		if(employeeDetailEntity!=null) {
			employeeDetailVO=new EmployeeDetailsVO();
				BeanUtils.copyProperties(employeeDetailEntity,
						employeeDetailVO);
			}
		return employeeDetailVO;
	}
	
	@Override
	public EmployeeLeaveDetailVO findEmployeeLeaveDetailForManager(String eid) {
		EmployeeLeaveDetailEntity employeeLeaveDetailEntity = iEmployeeLMSDao.findEmployeeLeaveDetailForManager(eid);
		EmployeeLeaveDetailVO employeeLeaveDetailVO =null; 
		if(employeeLeaveDetailEntity!=null) {
			    employeeLeaveDetailVO=new EmployeeLeaveDetailVO();
				BeanUtils.copyProperties(employeeLeaveDetailEntity,
					employeeLeaveDetailVO);
			}
		return employeeLeaveDetailVO;
	}

	@Override
	public int findTotaPendingLeaveCount(String role,String empid) {
		return iEmployeeLMSDao.findTotaPendingLeaveCount(role,empid);
	}

	@Override
	public void addAlternativeArrangementsFaculty(String[] name, String[] branch, String[] period, String[] subject,
			String[] date,String userid,Date doe,Date dom,String fid) {
		iEmployeeLMSDao.addAlternativeArrangementsFaculty(name,branch,period,subject,date,userid,doe,dom,fid);
	}

	@Override
	public List<SubjectAlternativeArrangementsVO> findAlternateArrangementPeriodFarFaculty(String username) {
		List<SubjectAlternativeArrangementsEntity> alternativeArrangementsEntityList=iEmployeeLMSDao.findAlternateArrangementPeriodFarFaculty(username);
		List<SubjectAlternativeArrangementsVO> alternativeArrangementsVOList=new ArrayList<SubjectAlternativeArrangementsVO>();
		for(SubjectAlternativeArrangementsEntity alternativeArrangementsEntity:alternativeArrangementsEntityList){
			SubjectAlternativeArrangementsVO alternativeArrangementsVO=new SubjectAlternativeArrangementsVO();
			String name=iEmployeeLMSDao.findFacultyById(alternativeArrangementsEntity.getSubmitedby());
			BeanUtils.copyProperties(alternativeArrangementsEntity, alternativeArrangementsVO);
			alternativeArrangementsVO.setName(name);
			alternativeArrangementsVOList.add(alternativeArrangementsVO);
		}
		return alternativeArrangementsVOList;
	}

	@Override
	public String updateStatusForAlternateClass(String id, String status) {
		String msg=iEmployeeLMSDao.updateStatusForAlternateClass(id,status);
		return msg;
	}

	@Override
	public List<SubjectAlternativeArrangementsVO> findAllAlternativeClassDetailsForHrHod(String id) {
		List<SubjectAlternativeArrangementsEntity> alternativeArrangementsEntityList=iEmployeeLMSDao.findAllAlternativeClassDetailsForHrHod(id);
		List<SubjectAlternativeArrangementsVO> alternativeArrangementsVOList=new ArrayList<SubjectAlternativeArrangementsVO>();
		for(SubjectAlternativeArrangementsEntity alternativeArrangementsEntity:alternativeArrangementsEntityList){
			SubjectAlternativeArrangementsVO alternativeArrangementsVO=new SubjectAlternativeArrangementsVO();
			String submitToName=iEmployeeLMSDao.findFacultyById(alternativeArrangementsEntity.getFid());
			String submitedByName=iEmployeeLMSDao.findFacultyById(alternativeArrangementsEntity.getSubmitedby());
			BeanUtils.copyProperties(alternativeArrangementsEntity, alternativeArrangementsVO);
			alternativeArrangementsVO.setFid(submitToName);
			alternativeArrangementsVO.setName(submitedByName);
			alternativeArrangementsVOList.add(alternativeArrangementsVO);
		}
		return alternativeArrangementsVOList;
	}

	@Override
	public String updateBasicProfile(String eid, String dob, String mobile, String password, String email){
		return iEmployeeLMSDao.updateBasicProfile(eid,dob,mobile,password,email);
	}
	

}
