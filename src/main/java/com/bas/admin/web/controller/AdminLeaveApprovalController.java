package com.bas.admin.web.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bas.common.constant.BASApplicationConstants;
import com.bas.common.constant.BaoConstants;
import com.bas.common.constant.NavigationConstant;
import com.bas.common.constant.PageTitleContant;
import com.bas.employee.service.BasFacultyService;
import com.bas.employee.web.controller.form.FacultyLeaveApprovalVO;
import com.bas.employee.web.controller.form.LoginForm;
import com.bas.hr.service.HrLeaveApprovalService;

/**
 * 
 * @author nagendra
 * 
 *   This is used to approve and reject leave
 *   for the employee
 */
@Controller
@Scope("request")
public class AdminLeaveApprovalController {
	
	@Autowired
	@Qualifier("BasFacultyServiceImpl")
	private BasFacultyService basFacultyService;
	
	
	@Autowired
	@Qualifier("HrLeaveApprovalServiceImpl")
	private HrLeaveApprovalService hrLeaveApprovalService;
	
	@RequestMapping(value="/managerApproveLeave", method=RequestMethod.GET)
	public String getLeaveManagerApproval(HttpSession session, Model model)	{
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		List<FacultyLeaveApprovalVO> empLeaveDataList = basFacultyService.getLeaveApprovalForManager(Integer.parseInt(loginForm.getEid()));
		model.addAttribute("MessageShow","no");
		model.addAttribute("empLeaveDataList",empLeaveDataList);
		model.addAttribute("facultyAttendStatusVO", new FacultyLeaveApprovalVO());
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.MANAGER_LEAVE_APPROVAL_TITLE);
		return NavigationConstant.LMS_PREFIX_PAGE
				+ NavigationConstant.MANAGER_APPROVE_LEAVE;
	}
	
	@RequestMapping(value = "/rejectLeaveByAdmin", method = RequestMethod.POST)
	public String rejectLeaveByAdmin(@RequestParam("requestLeaveId") String requestLeaveId,@RequestParam("rejectReason") String rejectReason,HttpSession session, Model model) {
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		String result=hrLeaveApprovalService.rejectLeaveByLeaveRequestId(requestLeaveId, rejectReason,BASApplicationConstants.MANAGER);
		List<FacultyLeaveApprovalVO> empLeaveDataList = basFacultyService.getLeaveApprovalForManager(Integer.parseInt(loginForm.getEid()));
		model.addAttribute("empLeaveDataList", empLeaveDataList);
		model.addAttribute("facultyAttendStatusVO", new FacultyLeaveApprovalVO());
		if(BaoConstants.SUCCESS.equals(result)){
			model.addAttribute("ApplicationMessage", "Leave Request with request id "+requestLeaveId+" is rejected with reason "+rejectReason);
		}
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.MANAGER_LEAVE_APPROVAL_TITLE);
		return NavigationConstant.LMS_PREFIX_PAGE
				+ NavigationConstant.MANAGER_APPROVE_LEAVE;
	}
	
	@RequestMapping(value = "/approveLeaveByAdmin", method = RequestMethod.POST)
	public String approveLeaveByAdmin(@RequestParam("requestLeaveId") String requestLeaveId,@RequestParam("approveComment") String rejectReason, HttpSession session,Model model) {
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		hrLeaveApprovalService.approveLeaveByManagerRequestId(requestLeaveId, rejectReason);
		List<FacultyLeaveApprovalVO> empLeaveDataList = basFacultyService.getLeaveApprovalForManager(Integer.parseInt(loginForm.getEid()));
		model.addAttribute("empLeaveDataList", empLeaveDataList);
		model.addAttribute("facultyAttendStatusVO", new FacultyLeaveApprovalVO());
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.MANAGER_LEAVE_APPROVAL_TITLE);
		model.addAttribute("ApplicationMessage", "Leave with request id "+requestLeaveId+" has been approved successfully with reason "+rejectReason+".");
		return NavigationConstant.LMS_PREFIX_PAGE
				+ NavigationConstant.MANAGER_APPROVE_LEAVE;
	}
	
	@RequestMapping(value ="/updateLeaveManagerApprovalbyAjax", method = RequestMethod.GET)
	public @ResponseBody String updateLeaveManagerApprovalbyAjax(@RequestParam("requestId") int requestId ,@RequestParam("approvalStatus") String managerApproval)	{ 
		System.out.println("Request ID "+requestId);
		return basFacultyService.updateLeaveApprovalForManager(requestId, managerApproval);
	}

	
	
	/*@RequestMapping(value = "/updateLeaveAdminApprovalbyAjax", method = RequestMethod.GET)
	public @ResponseBody String updateLeaveAdminApprovalbyAjax(@RequestParam("requestID") int requestID,
			@RequestParam("approvalStatus") String hrApproval) {
		System.out.println("Request ID " + requestID);
		return basFacultyService.updateLeaveApprovalForAdmin(requestID, hrApproval);
	}*/
	
}
