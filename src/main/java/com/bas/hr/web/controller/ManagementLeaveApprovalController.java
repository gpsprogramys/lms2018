package com.bas.hr.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bas.common.constant.BASApplicationConstants;
import com.bas.common.constant.LeaveStatus;
import com.bas.common.constant.NavigationConstant;
import com.bas.common.constant.PageTitleContant;
import com.bas.common.util.DateUtils;
import com.bas.common.web.controller.form.EmployeeLeaveDetailVO;
import com.bas.employee.service.BasFacultyService;
import com.bas.employee.web.controller.form.FacultyLeaveApprovalVO;
import com.bas.employee.web.controller.form.LoginForm;
import com.bas.employee.web.controller.form.ReportingManagerVO;
import com.bas.hr.service.HrLeaveApprovalService;
import com.bas.hr.service.HrOnePageLeaveHistoryService;
import com.bas.hr.web.controller.model.EmployeeLeave;
import com.bas.hr.web.controller.model.EmployeeOnePageLeaveHistoryVO;
import com.bas.hr.web.controller.model.LeavesModifiedForm;
import com.synergy.bank.employee.service.IEmployeeLMSService;

/**
 * 
 * @author nagendra
 * 
 *   This is used to approve and reject leave
 *   for the employee
 *   ddd
 */
@Controller
@Scope("request")
public class ManagementLeaveApprovalController {
	
	@Autowired
	@Qualifier("BasFacultyServiceImpl")
	private BasFacultyService basFacultyService;
	
	@Autowired
	@Qualifier("EmployeeLMSService")
	private IEmployeeLMSService iEmployeeService;

	@Autowired
	@Qualifier("HrOnePageLeaveHistoryServiceImpl")
	private HrOnePageLeaveHistoryService hrOnePageLeaveHistoryService;
	
	
	@Autowired
	@Qualifier("HrLeaveApprovalServiceImpl")
	private HrLeaveApprovalService hrLeaveApprovalService;
	
	
	
	@RequestMapping(value ="/employeeLeaveClElByManagement", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody public EmployeeLeave employeeLeaveClEl(@RequestParam("empid") String empid,@RequestParam("requestid") String requestid) {
		EmployeeLeave employeeLeave=basFacultyService.findEmployeeLeaveBalance(empid,requestid);
		LeavesModifiedForm leavesModifiedForm=hrLeaveApprovalService.findModifiedLeaveByRequestId(requestid);
		employeeLeave.setMcl(leavesModifiedForm.getCl());
		employeeLeave.setMel(leavesModifiedForm.getEl());
		employeeLeave.setMlwp(leavesModifiedForm.getLwp());
		employeeLeave.setChangeType(leavesModifiedForm.getChangeType());
		return employeeLeave;
	}
	
	@RequestMapping(value="/managementApproveLeave", method=RequestMethod.POST)
	public String getLeaveManagementApprovalPost(HttpSession session, Model model)	{
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		List<FacultyLeaveApprovalVO> empLeaveDataList = basFacultyService.getLeaveApprovalForManagement(loginForm.getEid());
		model.addAttribute("empLeaveDataList",empLeaveDataList);
		model.addAttribute("facultyAttendStatusVO", new FacultyLeaveApprovalVO());
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.MANAGEMENT_LEAVE_APPROVAL_TITLE);
		return NavigationConstant.MANAGEMENT_PREFIX_PAGE
				+ NavigationConstant.MANAGEMENT_APPROVE_LEAVE;
	}
	
	@RequestMapping(value="/managementApproveLeave", method=RequestMethod.GET)
	public String getLeaveManagementApproval(HttpSession session, Model model)	{
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		List<FacultyLeaveApprovalVO> empLeaveDataList = basFacultyService.getLeaveApprovalForManagement(loginForm.getEid());
		model.addAttribute("empLeaveDataList",empLeaveDataList);
		model.addAttribute("facultyAttendStatusVO", new FacultyLeaveApprovalVO());
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.MANAGEMENT_LEAVE_APPROVAL_TITLE);
		return NavigationConstant.MANAGEMENT_PREFIX_PAGE
				+ NavigationConstant.MANAGEMENT_APPROVE_LEAVE;
	}
	

	@RequestMapping(value = "/rejectLeaveByManagement", method = RequestMethod.POST)
	public String rejectLeaveByHr(@RequestParam("requestLeaveId") String requestLeaveId,@RequestParam("approveComment") String rejectReason, HttpSession session,Model model) {
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		hrLeaveApprovalService.rejectLeaveByLeaveRequestId(requestLeaveId, rejectReason,BASApplicationConstants.MANAGEMENT);
		List<FacultyLeaveApprovalVO> empLeaveDataList = basFacultyService.getLeaveApprovalForManagement(loginForm.getEid());
		model.addAttribute("empLeaveDataList", empLeaveDataList);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.MANAGEMENT_LEAVE_APPROVAL_TITLE);
		model.addAttribute("facultyAttendStatusVO", new FacultyLeaveApprovalVO());
		return NavigationConstant.MANAGEMENT_PREFIX_PAGE + NavigationConstant.MANAGEMENT_APPROVE_LEAVE;
	}
	
	@RequestMapping(value = "/approveLeaveByManagement", method = RequestMethod.POST)
	public String approveLeaveByHr(@RequestParam("requestLeaveId") String requestLeaveId,@RequestParam(value="changeLeaveType",required=false) String changeLeaveType,@RequestParam("approveComment") String approveComment,@RequestParam(value="cl",required=false) float cl,@RequestParam(value="el",required=false) float el,@RequestParam(value="lwp",required=false) float lwp,HttpSession session, Model model) {
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		hrLeaveApprovalService.approveLeaveByLeaveRequestIdTemp(requestLeaveId, approveComment,changeLeaveType,cl,el,lwp);
		List<FacultyLeaveApprovalVO> empLeaveDataList = basFacultyService.getLeaveApprovalForManagement(loginForm.getEid());
		model.addAttribute("empLeaveDataList", empLeaveDataList);
		model.addAttribute("facultyAttendStatusVO", new FacultyLeaveApprovalVO());
		model.addAttribute(BASApplicationConstants.APPLICATION_MESSAGE, "Leave Application has been approved successfully with leave request "+requestLeaveId);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.MANAGEMENT_LEAVE_APPROVAL_TITLE);
		return NavigationConstant.MANAGEMENT_PREFIX_PAGE + NavigationConstant.MANAGEMENT_APPROVE_LEAVE;
	}
	
	@RequestMapping(value="/mreportee", method=RequestMethod.GET)
	public String employeeOnLeavePost(@RequestParam(value="depttName",required=false) String depttName, Model model,HttpSession session)	{
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		List<ReportingManagerVO> reportingManagerVOs = basFacultyService.findEmployeeListByManager(loginForm.getEid()+"");
		model.addAttribute("reportingManagerVOs",reportingManagerVOs);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.REPORTEE_EMPLOYE_TITLE);
		return NavigationConstant.MANAGEMENT_PREFIX_PAGE
				+ NavigationConstant.REPORTEE_EMPLOYEE_PAGE;
	}

//	
//	@RequestMapping(value="/memployeeOnLeave", method=RequestMethod.GET)
//	public String employeeOnLeave(@RequestParam(value="leaveOnDate",required=false) String leaveOnDate, Model model)	{
//		if(leaveOnDate==null)
//			leaveOnDate=DateUtils.getCurrentCalendarDate();
//		List<FacultyLeaveApprovalVO> empOnLeaveList = basFacultyService.findEmployeesLeaveAppOnDate(leaveOnDate);
//		//List<FacultyLeaveApprovalVO> empOnLeaveList =new ArrayList<>();
//		model.addAttribute("empLeaveDataList",empOnLeaveList);
//		model.addAttribute("facultyAttendStatusVO", new FacultyLeaveApprovalVO());
//		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.EMPLOYEE_ON_LEAVE_TITLE);
//		return NavigationConstant.MANAGEMENT_PREFIX_PAGE
//				+ NavigationConstant.EMPLOYEE_ON_LEAVE_PAGE;
//	} 
	
	@RequestMapping(value="/memployeeOnLeave", method=RequestMethod.GET)
	public String employeeOnLeave(Model model)	{
		//if(leaveOnDate==null)
		String leaveOnDate=DateUtils.getCurrentCalendarDate();
		List<FacultyLeaveApprovalVO> empOnLeaveList = basFacultyService.findEmployeesLeaveAppOnDate(leaveOnDate);
		//List<FacultyLeaveApprovalVO> empOnLeaveList =new ArrayList<>();
		model.addAttribute("empLeaveDataList",empOnLeaveList);
		model.addAttribute("facultyAttendStatusVO", new FacultyLeaveApprovalVO());
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.EMPLOYEE_ON_LEAVE_TITLE);
		return NavigationConstant.MANAGEMENT_PREFIX_PAGE
				+ NavigationConstant.EMPLOYEE_ON_LEAVE_PAGE;
	} 
	
	@RequestMapping(value="/employeeOnLeaveByAjax", method=RequestMethod.GET)
	public @ResponseBody List<FacultyLeaveApprovalVO> employeeOnLeaveByAjax(@RequestParam(value="leaveOnDate",required=false) String leaveOnDate, Model model)	{
		if(leaveOnDate==null)
			leaveOnDate=DateUtils.getCurrentCalendarDate();
		List<FacultyLeaveApprovalVO> empOnLeaveList = basFacultyService.findEmployeesLeaveAppOnDate(leaveOnDate);
    	return empOnLeaveList; //@ResponseBody  responsible for converting java object into JSON 
	} 
	
	@RequestMapping(value="/employeeOnLeaveDetailByAjax", method=RequestMethod.GET)
	public @ResponseBody FacultyLeaveApprovalVO employeeOnLeaveDetailByAjax(@RequestParam(value="pempRequestId",required=false) String pempRequestId,@RequestParam(value="pempid",required=false) String pempid, Model model)	{
		FacultyLeaveApprovalVO empOnLeaveDetail = basFacultyService.employeeOnLeaveDetailByAjax(pempRequestId,pempid);
    	return empOnLeaveDetail; //@ResponseBody  responsible for converting java object into JSON 
		//System.out.println("hello i m execfuting-----------------------------"+pempRequestId);
		//return null;
		
	} 
	
	@RequestMapping(value="/memployeeOnLeave", method=RequestMethod.POST)
	public String employeeOnLeavePost(@RequestParam(value="leaveOnDate",required=false) String leaveOnDate, Model model)	{
		if(leaveOnDate==null)
			leaveOnDate=DateUtils.getCurrentCalendarDate();
		List<FacultyLeaveApprovalVO> empOnLeaveList = basFacultyService.findEmployeesLeaveAppOnDate(leaveOnDate);
		//List<FacultyLeaveApprovalVO> empOnLeaveList =new ArrayList<>();
		model.addAttribute("empLeaveDataList",empOnLeaveList);
		model.addAttribute("facultyAttendStatusVO", new FacultyLeaveApprovalVO());
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.EMPLOYEE_ON_LEAVE_TITLE);
		return NavigationConstant.MANAGEMENT_PREFIX_PAGE
				+ NavigationConstant.EMPLOYEE_ON_LEAVE_PAGE;
	} 
	
	/*@RequestMapping(value="/memployeeOnLeave", method=RequestMethod.GET)
	public String employeeOnLeave( Model model)	{
		List<FacultyLeaveApprovalVO> empOnLeaveList=basFacultyService.findEmployeesOnLeaveOnDate();
		model.addAttribute("empOnLeaveList",empOnLeaveList);
		return NavigationConstant.MANAGEMENT_PREFIX_PAGE
				+ NavigationConstant.EMPLOYEE_ON_LEAVE_PAGE;
	  }*/
	

	@RequestMapping(value = "memployeeOnePageLeaveHistory", method = RequestMethod.GET)
	public String findEmployeeOnePageLeaveHistory(
			@RequestParam(value = "eid", required = false) String eid,
			Model model, HttpSession session) {
		if (eid == null) {
			LoginForm loginForm = (LoginForm) session
					.getAttribute(NavigationConstant.USER_SESSION_DATA);
			eid = loginForm.getEid();
		}
		//getCurrentSessionYear 2015-2016
		String currentSessionYear=DateUtils.getCurrentSessionYear();
		String previousSessionYear=DateUtils.getPreviousSessionYear();
		model.addAttribute("currentSessionYear",currentSessionYear);
		model.addAttribute("previousSessionYear",previousSessionYear);
		EmployeeLeaveDetailVO employeeLeaveDetailVO = iEmployeeService.findEmployeeLeaveDetail(eid);
		model.addAttribute("employeeLeaveDetailVO",employeeLeaveDetailVO);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.EMPLOYEES_ONE_PAGE_LEAVE_HISTORY_TITLE);
		 
		List<EmployeeOnePageLeaveHistoryVO> employeeOnePageLeaveHistoryVOList = hrOnePageLeaveHistoryService.findEmployeeOnePageLeaveHistory(eid, "2016-02-01","2016-02-29");
		model.addAttribute("employeeOnePageLeaveHistoryVOList",employeeOnePageLeaveHistoryVOList);
		return NavigationConstant.MANAGEMENT_PREFIX_PAGE
				+ NavigationConstant.FIND_EMPLOYEE_ONE_PAGE_LEAVE_HISTORY;
	}
	
	
	@RequestMapping(value="/managementApproveLeaveByStatus", method=RequestMethod.GET)
	public String hrApproveLeaveByStatus(@RequestParam("leaveRequestStatus") String leaveRequestStatus,HttpSession session, Model model)	{
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		List<FacultyLeaveApprovalVO> empLeaveDataList =null;
		if(!leaveRequestStatus.equalsIgnoreCase("Management Pending")) {
			empLeaveDataList = basFacultyService.getLeaveApprovalForAdmin(loginForm.getEid());
		}else{
		     empLeaveDataList = basFacultyService.getLeaveApprovalForManagement(LeaveStatus.MANAGEMENT_ID);
		}
		List<FacultyLeaveApprovalVO> filteredEempLeaveDataList=new ArrayList<FacultyLeaveApprovalVO>();
		for(FacultyLeaveApprovalVO facultyLeaveApprovalVO:empLeaveDataList){
			  if(leaveRequestStatus.equalsIgnoreCase("Manager Pending")){
				     if(facultyLeaveApprovalVO.getManagerApproval().equalsIgnoreCase("PENDING")){
				    	 filteredEempLeaveDataList.add(facultyLeaveApprovalVO);
				     }
			  }else  if(leaveRequestStatus.equalsIgnoreCase("HR Pending")){
				     if(facultyLeaveApprovalVO.getHrApproval().equalsIgnoreCase("PENDING")){
				    	 filteredEempLeaveDataList.add(facultyLeaveApprovalVO);
				     }
			  }else  if(leaveRequestStatus.equalsIgnoreCase("Management Pending")){
				    	 filteredEempLeaveDataList.add(facultyLeaveApprovalVO);
			  }
		}
		model.addAttribute("leaveRequestStatus",leaveRequestStatus);
		model.addAttribute("empLeaveDataList",filteredEempLeaveDataList);
		model.addAttribute("facultyAttendStatusVO", new FacultyLeaveApprovalVO());
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.MANAGEMENT_LEAVE_APPROVAL_TITLE);
		model.addAttribute("MessageShow","no");
		return NavigationConstant.MANAGEMENT_PREFIX_PAGE
				+ NavigationConstant.MANAGEMENT_APPROVE_LEAVE;
	}
	
	
}
