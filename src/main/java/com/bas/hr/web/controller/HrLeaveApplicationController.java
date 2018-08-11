package com.bas.hr.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.bas.admin.aop.logger.Loggable;
import com.bas.admin.email.service.LeaveApplicationEmailService;
import com.bas.admin.email.service.LeaveApplicationNotificationThread;
import com.bas.admin.service.LeaveReasonService;
import com.bas.admin.service.LeaveTypeService;
import com.bas.admin.web.controller.form.LeaveReasonForm;
import com.bas.admin.web.controller.form.LeaveTypeForm;
import com.bas.admin.web.controller.form.MessageStatus;
import com.bas.common.constant.BASApplicationConstants;
import com.bas.common.constant.LeaveStatus;
import com.bas.common.constant.NavigationConstant;
import com.bas.common.constant.PageTitleContant;
import com.bas.common.util.CurrentUserDataUtil;
import com.bas.common.util.DateUtils;
import com.bas.common.web.controller.form.EmployeeDetailsVO;
import com.bas.common.web.controller.form.EmployeeLeaveDetailVO;
import com.bas.employee.service.BasFacultyService;
import com.bas.employee.web.controller.form.EmployeeLeaveRequestForm;
import com.bas.employee.web.controller.form.FacultyLeaveApprovalVO;
import com.bas.employee.web.controller.form.LoginForm;
import com.bas.hr.web.controller.model.LeaveStatusVO;
import com.bas.hr.web.controller.model.SuggestionManagerOptionVO;
import com.bas.hr.web.controller.model.SuggestionOptionVO;
import com.synergy.bank.employee.service.IEmployeeLMSService;

@Controller
@Scope("request")
public class HrLeaveApplicationController {

	@Autowired
	@Qualifier("EmployeeLMSService")
	private IEmployeeLMSService iEmployeeService;
	
	@Autowired
	@Qualifier("LeaveApplicationEmailService")
	LeaveApplicationEmailService leaveApplicationEmailService;
	
	@Autowired
	@Qualifier("LeaveTypeServiceImpl")
	private LeaveTypeService leaveTypeService;
	
	@Autowired
	@Qualifier("BasFacultyServiceImpl")
	private BasFacultyService basFacultyService;
	
	
	@Autowired
	@Qualifier("LeaveReasonServiceImpl")
	private LeaveReasonService LeaveReasonService;
	
	
	/**
	 *  This is one of the important method where we haven't written
	 *   @RequestParam to take Date object , this is automatic converted from string format 
	 *   to Date object  as per rules define inside the @InitBinder method in this controller.
	 * @param leaveFrom
	 * @param leaveTo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="calculateNoOfDays", method=RequestMethod.GET)
	public LeaveStatusVO calculateTotalNoOfLeaveDays(Date leaveFrom, Date leaveTo,String empid,@RequestParam(value="sandwitch",required=false) String sandwitch)	{
		LeaveStatusVO leaveStatusVO=new LeaveStatusVO();
		int p=leaveFrom.compareTo(leaveTo);
		if(p>0) {
			leaveStatusVO.setMessage(".......From Date cannot be greater than to date...");
			leaveStatusVO.setTotalDays(0);
			leaveStatusVO.setStatus("fail");
			return leaveStatusVO;
		}	
		List<String> noOfHolidaysList=iEmployeeService.findfHolidaysDatesInBetween(leaveFrom, leaveTo);
		int noOfHolidays=noOfHolidaysList.size();
		if(sandwitch==null || sandwitch.equalsIgnoreCase("Yes")){
			noOfHolidaysList=new ArrayList<String>();
			noOfHolidays=0;
		}
		System.out.println("no of holidays"+noOfHolidays);
		//System.out.println("no of leaves applied "+(totalDays));
		///////////////////////////////
		String startDate = DateUtils.getDateIntoStringFormat(leaveFrom);
		String endDate= DateUtils.getDateIntoStringFormat(leaveTo);
		int sd=DateUtils.twoDateDifference(DateUtils.getCurrentCalendarDate(),startDate);
		if(sd>2){
			leaveStatusVO.setLwp("yes");
		}
		int ed=DateUtils.twoDateDifference(DateUtils.getCurrentCalendarDate(),endDate);
		if(ed>2){
			leaveStatusVO.setLwp("yes");
		}
			
		StringBuilder leaveDates = new StringBuilder();
		if(noOfHolidaysList.contains(startDate)){
			leaveDates.append("<font color=\"red\"><b>"+startDate+"</b></font>");
		}else {
			leaveDates.append(startDate);
		}
		int count=1;
		while (true) {
			if(startDate.equalsIgnoreCase(endDate) ) {
				break;
			}
			String nextDate = DateUtils.nextDate(startDate,count);
			count++;
			if(noOfHolidaysList.contains(nextDate)){
				leaveDates.append(", " +"<font color=\"red\"><b>"+nextDate+"</b></font>");
			}else {
				leaveDates.append("," + nextDate);
			}
			if(nextDate.equalsIgnoreCase(endDate)){
				break;
			}
		}
		int totalDays = count-noOfHolidays;
		EmployeeLeaveRequestForm employeeLeaveRequestForm=new EmployeeLeaveRequestForm();
		employeeLeaveRequestForm.setLeaveDates(leaveDates.toString());
		employeeLeaveRequestForm.setEmployeeId(empid);
		String result=iEmployeeService.isLeaveAlreadyApplied(employeeLeaveRequestForm);
		leaveStatusVO.setMessage(result);
		leaveStatusVO.setTotalDays(totalDays);
		leaveStatusVO.setLeaveDates(leaveDates.toString());
		if("no".equalsIgnoreCase(result)){
			leaveStatusVO.setStatus("pass");
		}else{
			leaveStatusVO.setStatus("fail");
		}
		//////////////////
		return leaveStatusVO;
	}

	// @ResponseBody here converts java object into JSON format
	@RequestMapping(value = "findEmplyeeLeaveDetail", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody EmployeeLeaveDetailVO findEmplyeeLeaveDetail(@RequestParam("eid") String eid) {
		EmployeeLeaveDetailVO employeeLeaveDetailVO = iEmployeeService.findEmployeeLeaveDetail(eid);
		return employeeLeaveDetailVO;
	}
	//SELECTS ALL EMPLOYEE DETAILS
	@RequestMapping(value = "findAllEmployeeDetails", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody EmployeeDetailsVO findAllEmployeeDetails(@RequestParam("eid") String eid) {
		EmployeeDetailsVO employeeDetailsVO = iEmployeeService.findEmployeeDetails(eid);
		return employeeDetailsVO;
	}
	
	/**
	 * Method which fetches the leave information of an employee for a manager.
	 * 
	 * @return Employee Details Value Object
	 */
	@RequestMapping(value = "findEmplyeeLeaveDetailForManager", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody EmployeeLeaveDetailVO findEmplyeeLeaveDetailForManager(@RequestParam("eid") String eid) {
		EmployeeLeaveDetailVO employeeLeaveDetailVO = iEmployeeService.findEmployeeLeaveDetailForManager(eid);
		return employeeLeaveDetailVO;
	}

	@RequestMapping(value = "emplyeeSuggestionOption", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody List<SuggestionOptionVO> findEmplyeeSuggestionOption(
			@RequestParam("searchword") String searchword,HttpSession session) {
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		List<SuggestionOptionVO> suggestionOptionList = iEmployeeService.findEmployeeSuggestionOption(searchword,loginForm.getEid());
		return suggestionOptionList;
	}
	
	@RequestMapping(value = "emplyeeSuggestionOptionForLeaveHistory", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody List<SuggestionOptionVO> findEmplyeeSuggestionOptionForLeaveHistory(
			@RequestParam("searchword") String searchword) {
		List<SuggestionOptionVO> suggestionOptionList = iEmployeeService.findEmplyeeSuggestionOptionForLeaveHistory(searchword);
		return suggestionOptionList;
	}
	
	// @ResponseBody here converts java object into JSON format
		@Loggable
		@RequestMapping(value = "emplyeeSuggestionManager", method = RequestMethod.GET, produces = {
				MediaType.APPLICATION_JSON_VALUE })
		public @ResponseBody List<SuggestionManagerOptionVO> findManagerEmplyeeSuggestionOption(
				@RequestParam("searchword") String searchword, HttpSession session) {
			
			// Taking the USer Id from the session
			LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);	
			String managerId = loginForm.getEid();
			
			List<SuggestionManagerOptionVO> suggestionOptionList = iEmployeeService.findEmplyeeSuggestionManager(searchword, managerId);
			return suggestionOptionList;
		}

		@RequestMapping(value = "/viewLeaveApplication", method = RequestMethod.GET)
		public String viewLeaveApplication(@RequestParam("requestId") String requestId,Model model) {
			EmployeeLeaveRequestForm employeeLeaveRequestForm = new EmployeeLeaveRequestForm();
			FacultyLeaveApprovalVO facultyLeaveApprovalVO =basFacultyService.empAppliedLeaveDetail(requestId);
			String managerName=basFacultyService.findEmpNameByEmpId(facultyLeaveApprovalVO.getManager_id()+"");
			BeanUtils.copyProperties(facultyLeaveApprovalVO, employeeLeaveRequestForm);
			model.addAttribute("employeeLeaveRequestForm", employeeLeaveRequestForm);
			model.addAttribute("facultyLeaveApprovalVO", facultyLeaveApprovalVO);
			model.addAttribute("managerName", managerName);
			model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.VIEW_LEAVE_APPLICATION_TITLE);
			return NavigationConstant.HR_BASE
					+ NavigationConstant.VIEW_LEAVE_APPLY_PAGE;
		}
		
	// BANK_EMPLOYEE_LEAVE_APPLY
	@RequestMapping(value = "leaveApplication", method = RequestMethod.GET)
	public String applyLeave(Model model, HttpServletRequest request) {
		EmployeeLeaveRequestForm employeeLeaveRequestForm = new EmployeeLeaveRequestForm();
		model.addAttribute("employeeLeaveRequestForm", employeeLeaveRequestForm);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.HR_HOME_TITLE);
		return NavigationConstant.HR_BASE + NavigationConstant.APPLY_LEAVE_PAGE;
	}
	
	@RequestMapping(value = "searchEmployeesByCriteria", method = RequestMethod.GET)
	public String searchEmployeesByCriteria(Model model, HttpServletRequest request) {
		EmployeeLeaveRequestForm employeeLeaveRequestForm = new EmployeeLeaveRequestForm();
		model.addAttribute("employeeLeaveRequestForm", employeeLeaveRequestForm);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.HR_HOME_TITLE);
		return NavigationConstant.HR_BASE + NavigationConstant.SEARCH_EMPLOYEE;
	}

	/**
	 * Method which fetches the leave information
	 * 
	 * @return
	 */
	/*public EmployeeLeaveBalanceForm getLeaveBalanceByEmpId() {
		EmployeeLeaveBalanceForm balanceForm = new EmployeeLeaveBalanceForm();
		balanceForm.setEmpNo(555);
		balanceForm = iEmployeeService.getLeaveBalanceByEmpId(balanceForm);
		return balanceForm;
	}*/

	/**
	 * 
	 * @param employeeLeaveRequest
	 */
	private void setApplicationData(EmployeeLeaveRequestForm employeeLeaveRequest) {
		
		if(employeeLeaveRequest.getLeaveType().equalsIgnoreCase(BASApplicationConstants.LWP_LEAVE)){
			employeeLeaveRequest.setLwp(employeeLeaveRequest.getTotalDays());
			employeeLeaveRequest.setDescription(employeeLeaveRequest.getOtherLeaveDescription());
		}else{
			employeeLeaveRequest.setDescription(BASApplicationConstants.NOT_APPLICABLE);
		}
		
		employeeLeaveRequest.setCCTo(BASApplicationConstants.NOT_APPLICABLE);
		
		employeeLeaveRequest.setHrApproval(LeaveStatus.PENDING_STATUS);
		
		/*if (employeeLeaveRequest.getInAccount() == null)
			employeeLeaveRequest.setInAccount(BASApplicationConstants.NOT_APPLICABLE);*/
		
		if (employeeLeaveRequest.getLeaveMeeting() == null)
			employeeLeaveRequest.setLeaveMeeting(BASApplicationConstants.NOT_APPLICABLE);
		
		employeeLeaveRequest.setLstatus(BASApplicationConstants.NOT_APPLICABLE);
		employeeLeaveRequest.setManagerApproval(BASApplicationConstants.NOT_APPLICABLE);
		
		if (employeeLeaveRequest.getOtherLeaveDescription() == null)
			employeeLeaveRequest.setOtherLeaveDescription(BASApplicationConstants.NOT_APPLICABLE);

		if (employeeLeaveRequest.getLeaveType() != null
				&& employeeLeaveRequest.getLeaveType().equalsIgnoreCase(BASApplicationConstants.EL_LEAVE)) {
			employeeLeaveRequest.setSandwitch(BASApplicationConstants.YES);
		}else {
			employeeLeaveRequest.setSandwitch(BASApplicationConstants.NO);
		}
	}

	@RequestMapping(value = "leaveApplication", method = RequestMethod.POST)
	public String submitLeaveRequest(@ModelAttribute("employeeLeaveRequestForm") EmployeeLeaveRequestForm leaveFormData,
			HttpSession session,Model model) {
		preProcessApplication(leaveFormData,session);
		leaveFormData.setManagementApproval(LeaveStatus.PENDING_STATUS);
		leaveFormData.setManagementid(LeaveStatus.MANAGEMENT_ID);
		leaveFormData.setManagementApproval(LeaveStatus.PENDING_STATUS);
		leaveFormData.setManagementid(LeaveStatus.MANAGEMENT_ID);
		MessageStatus messageStatus=iEmployeeService.saveLeaveRequest(leaveFormData);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.EMPLOYEE_HOME_TITLE);
		if("fail".equalsIgnoreCase(messageStatus.getStatus())){
			EmployeeLeaveDetailVO employeeLeaveDetailVO = iEmployeeService.findEmployeeLeaveDetail(leaveFormData.getEmployeeId()+"");
			model.addAttribute("employeeLeaveDetailVO",employeeLeaveDetailVO);
			model.addAttribute("employeeLeaveRequestForm",leaveFormData);
			model.addAttribute("applicationMessage", messageStatus.getMessage());
			return NavigationConstant.HR_BASE + NavigationConstant.APPLY_LEAVE_PAGE;
		}
		model.addAttribute("messageStatus", messageStatus);
		model.addAttribute("applicationMessage","Leave has been submitted successfully for the employee "+leaveFormData.getEmpName());
		// sendEmails(leaveFormData, detailsForm);
		
		return NavigationConstant.HR_BASE + NavigationConstant.APPLY_LEAVE_PAGE;
	}
	
	/*@RequestMapping(value = "searchEmployeesByCriteria", method = RequestMethod.POST)
	public String searchEmployeesByCriteria(@ModelAttribute("employeeLeaveRequestForm") EmployeeLeaveRequestForm leaveFormData,
			HttpSession session,Model model) {
		preProcessApplication(leaveFormData,session);
		MessageStatus messageStatus=iEmployeeService.saveLeaveRequest(leaveFormData);
		if("fail".equalsIgnoreCase(messageStatus.getStatus())){
			EmployeeLeaveDetailVO employeeLeaveDetailVO = iEmployeeService.findEmployeeLeaveDetail(leaveFormData.getEmployeeId()+"");
			model.addAttribute("employeeLeaveDetailVO",employeeLeaveDetailVO);
			model.addAttribute("employeeLeaveRequestForm",leaveFormData);
			model.addAttribute("applicationMessage", messageStatus.getMessage());
			return NavigationConstant.HR_BASE + NavigationConstant.SEARCH_EMPLOYEE;
		}
		model.addAttribute("messageStatus", messageStatus);
		model.addAttribute("applicationMessage","Leave has been submitted successfully for the employee "+leaveFormData.getEmpName());
		// sendEmails(leaveFormData, detailsForm);
		
		return NavigationConstant.HR_BASE + NavigationConstant.SEARCH_EMPLOYEE;
	}*/
	
	private void preProcessApplication(EmployeeLeaveRequestForm leaveFormData,HttpSession session){
		String userid = CurrentUserDataUtil.getCurrentLoggedUser(session);
		String sandwitch=leaveFormData.getSandwitch();
		leaveFormData.setUserid(userid);
		leaveFormData.setDoapplication(DateUtils.getCurrentJavaDate());
		leaveFormData.setDom(DateUtils.getCurrentJavaDate());
		float totalDays = leaveFormData.getTotalDays();
		List<String> noOfHolidaysList=iEmployeeService.findfHolidaysDatesInBetween(leaveFormData.getLeaveFrom(), leaveFormData.getLeaveTo());
		if(sandwitch==null || sandwitch.equalsIgnoreCase("Yes")){
			noOfHolidaysList=new ArrayList<String>();
		}
		String startDate = DateUtils.getDateIntoStringFormat(leaveFormData.getLeaveFrom());
		StringBuilder leaveDates = new StringBuilder();
		if(!noOfHolidaysList.contains(startDate))
		leaveDates.append(startDate);
		int totalLeaveCount=(int)Math.ceil(totalDays);
		//totalLeaveCount++;
		totalLeaveCount=totalLeaveCount+noOfHolidaysList.size();
		for (int i = 1; i < totalLeaveCount; i++) {
			String nextDate = DateUtils.nextDate(startDate, i);
			if(!noOfHolidaysList.contains(nextDate))
			leaveDates.append("," + nextDate);
		}
		leaveFormData.setLeaveDates(leaveDates.toString());
		setApplicationData(leaveFormData);
	}
	
	@RequestMapping(value = "employeeLeaveApplication", method = RequestMethod.POST)
	public String submitEmployeeLeaveApplication(@ModelAttribute("employeeLeaveRequestForm") EmployeeLeaveRequestForm leaveFormData,
			HttpSession session,HttpServletRequest request,Model model) {
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		String name[]=request.getParameterValues("name");
		String branch[]=request.getParameterValues("branch");
		String period[]=request.getParameterValues("period");
		String subject[]=request.getParameterValues("subject");
		String date[]=request.getParameterValues("date");
		String userid = CurrentUserDataUtil.getCurrentLoggedUser(session);
		String fid=leaveFormData.getEmployeeId();
		Date doe=DateUtils.getCurrentJavaDate();
		Date dom=DateUtils.getCurrentJavaDate();
		preProcessApplication(leaveFormData,session);
		leaveFormData.setHrApproval(LeaveStatus.PENDING_STATUS);
		leaveFormData.setManagementApproval(LeaveStatus.PENDING_STATUS);
		leaveFormData.setManagementid(LeaveStatus.MANAGEMENT_ID);
		leaveFormData.setManagerApproval(LeaveStatus.PENDING_STATUS);
		MessageStatus messageStatus=iEmployeeService.saveLeaveRequest(leaveFormData);
		try{
			iEmployeeService.addAlternativeArrangementsFaculty(name,branch,period,subject,date,userid,doe,dom,fid);
		}catch(NullPointerException ex){
			ex.printStackTrace();
		}
		//Sending email in asynchronous fashion
		LeaveApplicationNotificationThread leaveApplicationNotificationThread=new LeaveApplicationNotificationThread(leaveApplicationEmailService, "abs@gmail.com", "gpsinfosolutions@gmail.com", leaveFormData.getLeaveFrom().toString(), leaveFormData.getLeaveTo().toString());
		leaveApplicationNotificationThread.start();
		
		if("fail".equalsIgnoreCase(messageStatus.getStatus())){
			EmployeeLeaveDetailVO employeeLeaveDetailVO = iEmployeeService.findEmployeeLeaveDetail(leaveFormData.getEmployeeId()+"");
			model.addAttribute("employeeLeaveDetailVO",employeeLeaveDetailVO);
			model.addAttribute("employeeLeaveRequestForm",leaveFormData); 
			model.addAttribute("applicationMessage", messageStatus.getMessage());
			return NavigationConstant.EMPLOYEE_BASE + NavigationConstant.EMPLOYEE_LEAVE_APPLICATION_PAGE;
		}
		model.addAttribute("messageStatus", messageStatus);
		model.addAttribute("applicationMessage","Leave has been submitted successfully for the employee "+leaveFormData.getEmpName());
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.EMPLOYEE_LEAVE_APPLICATION_PAGE);
		// sendEmails(leaveFormData, detailsForm);
		return NavigationConstant.EMPLOYEE_BASE + NavigationConstant.SUCCESS_EMPLOYEE_PAGE;
	}


	private int findNoOfHolidays(Date leaveFrom, Date leaveTo) {
		return iEmployeeService.findNoOfHolidays(leaveFrom, leaveTo);
	}

	// It converts uploaded file to byte array form after it is populated in
	// custom java object
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// to register a custom editor
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		// Create a new CustomDateEditor
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		// // Register it as custom editor for the Date type
		binder.registerCustomEditor(Date.class, editor);
	}
	
	/**
	 * 
	 * @return
	 */
	@ModelAttribute("leaveTypeList")
	public List<String> findAllLeaveTypes(){
		List<String> leaveStringList=new  ArrayList<String>();
		List<LeaveTypeForm> leaveTypeList=leaveTypeService.findLeaveType();
		for(LeaveTypeForm form:leaveTypeList) {
			 leaveStringList.add(form.getLeaveType());
		}
		return leaveStringList;
	}
	
	/**
	 * 
	 * @return
	 */
	@ModelAttribute("reasonTypeList")
	public List<String> findAllReasonTypes(){
		List<String> reasonStringList=new  ArrayList<String>();
		List<LeaveReasonForm> reasonTypeList=LeaveReasonService.findLeaveReason();
		for(LeaveReasonForm form:reasonTypeList) {
			reasonStringList.add(form.getLeaveDetail());
		}
		return reasonStringList;
	}
	
	
	@RequestMapping(value="applyLeave", method= RequestMethod.GET)
	public String employeeApplyLeave(Model model, HttpServletRequest request){
		
		EmployeeLeaveRequestForm employeeLeaveRequestForm = new EmployeeLeaveRequestForm();
		model.addAttribute("employeeLeaveRequestForm", employeeLeaveRequestForm);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.HR_HOME_TITLE);
//		return NavigationConstant.HR_BASE + NavigationConstant.APPLY_LEAVE_PAGE;

//		EmployeeDetailsForm employeeDetailsForm = new EmployeeDetailsForm();
//		employeeDetailsForm.setId(empId);
//		EmployeeDetailsForm employeeDetails = new EmployeeDetailsForm();
//		
//		employeeDetails = iEmployeeService.findEmployeeById(employeeDetailsForm);
//		String department = employeeDetails.getDepartment();
//		String designation = employeeDetails.getDesignation();
//		String type = employeeDetails.getType();
//		System.out.println("department============>>>>>>>>>>>>"+department);
//		model.addAttribute("department",department);
//		model.addAttribute("designation",designation);
//		model.addAttribute("type", type);
//		model.addAttribute(employeeDetails.getDepartment());
//		request.setAttribute("depart", department);
//		System.out.println("department>>>>>>>>>>>"+department);
//		EmployeeLeaveBalanceForm balanceForm = getLeaveBalanceByEmpId();
//		model.addAttribute("totalCL", balanceForm.getTotalCL());
//		model.addAttribute("totalEL", balanceForm.getTotalEL());
//		model.addAttribute("totalSL", balanceForm.getTotalSL());
//		model.addAttribute("od", balanceForm.getOD());
//		
//		EmployeeDetailsForm detailsForm = new EmployeeDetailsForm();
//		int managerId = getReportingManagerId(empId);
//		detailsForm = getReportingManager(managerId);
//		System.out.println("manager's email id is: "+detailsForm.getEmail());
//		model.addAttribute("managers", detailsForm.getName());
//		System.out.println("manager's name is "+detailsForm.getName());
		return NavigationConstant.EMPLOYEE_BASE+NavigationConstant.APPLY_LEAVE_PAGE;
	}
	
	
	
	
	@RequestMapping(value = "/unmarkEmployeeLwp", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody public MessageStatus unmarkEmployeeLwpByDate(Model model, @RequestParam(value="lwpDate",required=false) String lwpDate,@RequestParam(value="empid",required=false) String empid) {
		String result=iEmployeeService.deleteEmployeeLwpByDate(lwpDate, empid);
		MessageStatus messageStatus=new MessageStatus();
		messageStatus.setStatus(result);
		messageStatus.setMessage("Employee with id "+empid+" is successfully unmarked for lwp on date "+lwpDate);
		return messageStatus;
	}
	
	
	@RequestMapping(value = "/markEmployeeLwp", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody public MessageStatus markEmployeeLwpByDate(Model model, @RequestParam(value="department",required=false) String department,@RequestParam(value="lwpDate",required=false) String lwpDate,@RequestParam(value="empid",required=false) String empid,@RequestParam(value="mcomment",required=false) String mcomment,HttpSession session) {
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		//converting List into Set
		//02/04/2016
		String lwpDateTokens[]=lwpDate.split("/");
		lwpDate=lwpDateTokens[2]+"-"+lwpDateTokens[0]+"-"+lwpDateTokens[1];
		//creating request for lwp leave application
		EmployeeLeaveRequestForm leaveFormData=new EmployeeLeaveRequestForm();
		EmployeeLeaveDetailVO employeeLeaveDetailVO = iEmployeeService.findEmployeeLeaveDetail(empid);
		leaveFormData.setAddress(employeeLeaveDetailVO.getPaddress());
		leaveFormData.setCCTo("NA");
		leaveFormData.setDepartment(employeeLeaveDetailVO.getDepartment());
		leaveFormData.setDescription(mcomment);
		leaveFormData.setDoapplication(DateUtils.getCurrentTimeIntoTimestamp());
		leaveFormData.setDom(DateUtils.getCurrentTimeIntoTimestamp());
		leaveFormData.setEmployeeId(empid);
		leaveFormData.setEmpName(employeeLeaveDetailVO.getName());
		leaveFormData.setHrApproval(LeaveStatus.PENDING_STATUS);
		leaveFormData.setHrManagerId(loginForm.getEid());
		leaveFormData.setInAccount(null);
		leaveFormData.setLeaveCategory("fullDay");
		leaveFormData.setLeaveDates(lwpDate);
		leaveFormData.setLeaveFrom(DateUtils.getDateIntoCalendarFormat(lwpDate));
		leaveFormData.setLeaveMeeting("NA");
		leaveFormData.setLeaveTo(DateUtils.getDateIntoCalendarFormat(lwpDate));
		leaveFormData.setLeaveType(BASApplicationConstants.LWP_LEAVE);
		leaveFormData.setLstatus(BASApplicationConstants.NOT_APPLICABLE);
		leaveFormData.setLwp(1);
		leaveFormData.setManagerApproval(BASApplicationConstants.NOT_APPLICABLE);
		leaveFormData.setManagerId(employeeLeaveDetailVO.getManagerId());
		leaveFormData.setManagementid(LeaveStatus.MANAGEMENT_ID);
		leaveFormData.setManagementApproval(LeaveStatus.PENDING_STATUS);
		leaveFormData.setMobile(employeeLeaveDetailVO.getMobile());
		leaveFormData.setOtherLeaveDescription(BASApplicationConstants.NOT_APPLICABLE);
		leaveFormData.setOtherReason(BASApplicationConstants.NOT_APPLICABLE);
		leaveFormData.setPurpose(mcomment);
		leaveFormData.setReason(mcomment);
		leaveFormData.setReportingManager(employeeLeaveDetailVO.getManagerName());
		leaveFormData.setSandwitch(BASApplicationConstants.NO);
		leaveFormData.setTotalDays(1.0F);
		leaveFormData.setUserid(CurrentUserDataUtil.getCurrentLoggedUser(session));
		MessageStatus messageStatus=iEmployeeService.saveLeaveRequest(leaveFormData);
		messageStatus.setStatus("success");
		//We have to write code to update comment which is not done so far.....
		//TODO
		messageStatus.setMessage("Employee  "+employeeLeaveDetailVO.getName()+" ("+empid+") is successfully marked  lwp on date "+lwpDate);
		 return messageStatus;
	}
	
	//EMPLOYEE_LEAVE_APPLY
		@RequestMapping(value = "employeeLeaveApplication", method = RequestMethod.GET)
		public String employeeLeaveApplication(Model model, HttpSession session) {
			LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
			EmployeeLeaveRequestForm employeeLeaveRequestForm = new EmployeeLeaveRequestForm();
			model.addAttribute("employeeLeaveRequestForm", employeeLeaveRequestForm);
			EmployeeLeaveDetailVO employeeLeaveDetailVO = iEmployeeService.findEmployeeLeaveDetail(loginForm.getEid());
			model.addAttribute("employeeLeaveDetailVO", employeeLeaveDetailVO);
			model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.EMPLOYEE_LEAVE_APPLICATION_PAGE);
			return NavigationConstant.EMPLOYEE_BASE + NavigationConstant.EMPLOYEE_LEAVE_APPLICATION_PAGE;
		}
	
}
