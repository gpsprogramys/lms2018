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

import com.bas.admin.web.controller.form.FaculityDailyAttendanceReportVO;
import com.bas.common.constant.BASApplicationConstants;
import com.bas.common.constant.LeaveStatus;
import com.bas.common.constant.NavigationConstant;
import com.bas.common.constant.PageTitleContant;
import com.bas.common.util.DateUtils;
import com.bas.employee.service.BasFacultyService;
import com.bas.employee.web.controller.form.FacultyLeaveApprovalVO;
import com.bas.employee.web.controller.form.LoginForm;
import com.bas.hr.service.HrLeaveApprovalService;
import com.bas.hr.web.controller.model.EmployeeLeave;

/**
 * 
 * @author nagendra
 * 
 *   This is used to approve and reject leave
 *   for the employee
 */
@Controller
@Scope("request")
public class HrLeaveApprovalController {
	
	@Autowired
	@Qualifier("BasFacultyServiceImpl")
	private BasFacultyService basFacultyService;
	
	
	@Autowired
	@Qualifier("HrLeaveApprovalServiceImpl")
	private HrLeaveApprovalService hrLeaveApprovalService;
	
	@RequestMapping(value ="/employeeLeaveClEl", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody public EmployeeLeave employeeLeaveClEl(@RequestParam("empid") String empid,@RequestParam("requestid") String requestid) {
		EmployeeLeave employeeLeave=basFacultyService.findEmployeeLeaveBalance(empid,requestid);
		return employeeLeave;
	}
	
	@RequestMapping(value="/hrApproveLeave", method=RequestMethod.GET)
	public String getLeaveHrApproval(HttpSession session, Model model)	{
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		List<FacultyLeaveApprovalVO> empLeaveDataList = basFacultyService.getLeaveApprovalForAdmin(loginForm.getEid());
		model.addAttribute("empLeaveDataList",empLeaveDataList);
		model.addAttribute("facultyAttendStatusVO", new FacultyLeaveApprovalVO());
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.HR_LEAVE_APPROVAL_TITLE);
		model.addAttribute("MessageShow","no");
		return NavigationConstant.ADMIN_PREFIX_PAGE
				+ NavigationConstant.ADMIN_APPROVE_LEAVE;
	}
	
	@RequestMapping(value="/hrApproveLeaveByStatus", method=RequestMethod.GET)
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
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.HR_LEAVE_APPROVAL_TITLE);
		model.addAttribute("MessageShow","no");
		return NavigationConstant.ADMIN_PREFIX_PAGE
				+ NavigationConstant.ADMIN_APPROVE_LEAVE;
	}
	
	
//	@RequestMapping(value="/hrPendingEmployeeByEmpId", method=RequestMethod.GET)
//	public String hrLeavePending(HttpSession session,Model model){
//		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
//		List<FacultyLeaveApprovalVO> hrLeaveDataList=basFacultyService.getHrPendingLeaveForAdmin(loginForm.getEid());
//		model.addAttribute("hrLeaveDataList",hrLeaveDataList);
//		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.HR_LEAVE_APPROVAL_TITLE);
//		model.addAttribute("MessageShow","no");
//		return NavigationConstant.ADMIN_PREFIX_PAGE
//				+ NavigationConstant.ADMIN_APPROVE_LEAVE;
//		
//	}
	
	@RequestMapping(value = "/rejectLeaveByHr", method = RequestMethod.POST)
	public String rejectLeaveByHr(@RequestParam("requestLeaveId") String requestLeaveId,@RequestParam("rejectReason") String rejectReason, HttpSession session,Model model) {
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		hrLeaveApprovalService.rejectLeaveByLeaveRequestId(requestLeaveId, rejectReason,BASApplicationConstants.HR);
		List<FacultyLeaveApprovalVO> empLeaveDataList = basFacultyService.getLeaveApprovalForAdmin(loginForm.getEid());
		model.addAttribute("ApplicationMessage", "Leave with request id "+requestLeaveId+" has been rejected with reject comment "+rejectReason);
		model.addAttribute("empLeaveDataList", empLeaveDataList);
		model.addAttribute("facultyAttendStatusVO", new FacultyLeaveApprovalVO());
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.HR_LEAVE_APPROVAL_TITLE);
		return NavigationConstant.ADMIN_PREFIX_PAGE + NavigationConstant.ADMIN_APPROVE_LEAVE;
	}
	
	@RequestMapping(value = "/approveLeaveByHr", method = RequestMethod.POST)
	public String approveLeaveByHr(@RequestParam("requestLeaveId") String requestLeaveId,@RequestParam(value="spchangeLeaveType",required=false) String spchangeLeaveType,@RequestParam("changeLeaveType") String changeLeaveType,@RequestParam("approveComment") String approveComment,@RequestParam("cl") float cl,@RequestParam("el") float el,@RequestParam("lwp") float lwp,HttpSession session, Model model) {
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		hrLeaveApprovalService.approveLeaveWithLeaveRequestIdByHRManagement(requestLeaveId, approveComment,spchangeLeaveType,changeLeaveType,cl,el,lwp,loginForm.getRole());
		List<FacultyLeaveApprovalVO> empLeaveDataList = basFacultyService.getLeaveApprovalForAdmin(loginForm.getEid());
		model.addAttribute("empLeaveDataList", empLeaveDataList);
		model.addAttribute("facultyAttendStatusVO", new FacultyLeaveApprovalVO());
		model.addAttribute("ApplicationMessage", "Leave with request id "+requestLeaveId+" has been appropved with approve comment "+approveComment);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.HR_LEAVE_APPROVAL_TITLE);
		return NavigationConstant.ADMIN_PREFIX_PAGE + NavigationConstant.ADMIN_APPROVE_LEAVE;
	}
	
	@RequestMapping(value="/employeeOnLeave", method=RequestMethod.GET)
	public String employeeOnLeave(@RequestParam(value="leaveOnDate",required=false) String leaveOnDate, Model model)	{
		if(leaveOnDate==null)
		leaveOnDate=DateUtils.getCurrentCalendarDate();
		List<FacultyLeaveApprovalVO> empOnLeaveList = basFacultyService.findEmployeesLeaveAppOnDate(leaveOnDate);
		//List<FacultyLeaveApprovalVO> empOnLeaveList =new ArrayList<>();
		model.addAttribute("empLeaveDataList",empOnLeaveList);
		model.addAttribute("facultyAttendStatusVO", new FacultyLeaveApprovalVO());
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.EMPLOYEE_ON_LEAVE_TITLE);
		model.addAttribute("role","hr");
		return NavigationConstant.MANAGEMENT_PREFIX_PAGE
				+ NavigationConstant.EMPLOYEE_ON_LEAVE_PAGE;
	}
	/*
	@RequestMapping(value="/employeeLeaveDownloadExcel", method=RequestMethod.GET)
	public String employeeLeaveDownloadExcel(@RequestParam(value="leaveOnDate",required=false) String leaveOnDate, Model model)	{
		
		List<FacultyLeaveApprovalVO> empOnLeaveList = basFacultyService.findEmployeesOnLeaveOnDate(leaveOnDate);
		
		model.addAttribute("empLeaveDataList",empOnLeaveList);
		model.addAttribute("facultyAttendStatusVO", new FacultyLeaveApprovalVO());
		
		return NavigationConstant.EMPLOYEE_ON_LEAVE_EXCEL;
	}
	@RequestMapping(value = "/departmentEmployeeDetails", method = RequestMethod.GET)
	public String currentAttendus(HttpServletRequest request, Model model) {
		List<String> depList = basFacultyService.selectDepartments();
		depList.add(0, "All");
		DepartmentForm departmentForm = new DepartmentForm();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		FaculityDailyAttendanceReportVO facultyAttendStatusVO = new FaculityDailyAttendanceReportVO();
		String date = format.format(new Date());
		// System.out.println(date);
		List<FaculityDailyAttendanceReportVO> faculityDailyAttendanceReportVOs = basFacultyService
				.showAttendusReport(date);
		System.out.println("I am showing Attendance");
		model.addAttribute("depList", depList);
		model.addAttribute("departmentForm", departmentForm);
		model.addAttribute("EmptyfacultyAttendStatusVO", facultyAttendStatusVO);
		if (faculityDailyAttendanceReportVOs != null) {
			Collections.sort(faculityDailyAttendanceReportVOs);
		}
		model.addAttribute("faculityDailyAttendanceReportVOs", faculityDailyAttendanceReportVOs);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.TODAYS_ATTENDANCE_TITLE);
		return NavigationConstant.ADMIN_PREFIX_PAGE + NavigationConstant.EMPLOYEE_ON_LEAVE_PAGE;
	}*/
	
	@RequestMapping(value="/employeeLeaveDownloadExcel", method=RequestMethod.GET)
	public String employeeLeaveDownloadExcel(@RequestParam(value="leaveOnDate",required=false) String leaveOnDate, Model model)	{
		if(leaveOnDate==null){
			leaveOnDate=DateUtils.getCurrentDateSQLDB();
		}
		
		List<FaculityDailyAttendanceReportVO> empOnLeaveList = basFacultyService.findEmployeesOnLeaveOnDate(leaveOnDate);
		
		model.addAttribute("empLeaveDataList",empOnLeaveList);
		model.addAttribute("facultyAttendStatusVO", new FacultyLeaveApprovalVO());
		
		return NavigationConstant.EMPLOYEE_ON_LEAVE_EXCEL;
	}
	
	
}