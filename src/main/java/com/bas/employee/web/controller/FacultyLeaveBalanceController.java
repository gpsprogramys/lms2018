package com.bas.employee.web.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bas.common.constant.NavigationConstant;
import com.bas.common.constant.PageTitleContant;
import com.bas.employee.service.BasFacultyService;
import com.bas.employee.web.controller.form.FacultyLeaveApprovalVO;
import com.bas.employee.web.controller.form.LeaveBalanceForm;
import com.bas.employee.web.controller.form.LoginForm;

@Controller
public class FacultyLeaveBalanceController {
	
	@Autowired
	@Qualifier("BasFacultyServiceImpl")
	private BasFacultyService basFacultyService;

	@RequestMapping(value="/leaveBalance",method=RequestMethod.GET)
	public String showLeaveBalance(Model model,HttpSession session) {
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
	//	FaculityLeaveMasterVO faculityLeaveMasterVO=basFacultyService.findLeaveBalance(loginForm.getEid());
		LeaveBalanceForm leaveBalanceForm=basFacultyService.findLeaveBalance(loginForm.getEid());
		model.addAttribute("leaveBalanceForm", leaveBalanceForm);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.LEAVE_BALANCE_TITLE);
		return NavigationConstant.LMS_PREFIX_PAGE+NavigationConstant.LEAVE_BALANCE_PAGE;
	}
	
	@RequestMapping(value = "/employeeLeaveStatus", method = RequestMethod.GET)
	public String employeeLeaveStatus(HttpSession session, Model model) {
		LoginForm loginForm = (LoginForm) session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		List<FacultyLeaveApprovalVO> empLeaveDataList = basFacultyService.getPendingLeaveRequestByEmpid(loginForm.getEid());
		model.addAttribute("empLeaveDataList", empLeaveDataList);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.EMPLOYEES_LEAVE_REQUEST_TITLE);
		model.addAttribute("facultyAttendStatusVO", new FacultyLeaveApprovalVO());
		return NavigationConstant.LMS_PREFIX_PAGE + NavigationConstant.EMPLOYEE_LEAVE_STATUS_PAGE;
	}

}
