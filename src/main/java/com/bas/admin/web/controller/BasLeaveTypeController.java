package com.bas.admin.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bas.admin.service.LeaveTypeService;
import com.bas.admin.web.controller.form.LeaveTypeForm;
import com.bas.common.constant.NavigationConstant;
import com.bas.common.constant.PageTitleContant;
import com.bas.common.util.CurrentUserDataUtil;
import com.bas.common.util.DateUtils;

/**
 * 
 * @author aaaaaaaa
 * 
 */
@Controller
public class BasLeaveTypeController {

	@Autowired
	@Qualifier("LeaveTypeServiceImpl")
	private LeaveTypeService leaveTypeService;

	
	@RequestMapping(value = "/leaveTypes", method = RequestMethod.GET)
	public String showAddLeaveType(Model model) {
		LeaveTypeForm leaveTypeForm = new LeaveTypeForm();
		List<LeaveTypeForm> leaveTypeForms = leaveTypeService.findLeaveType();
		model.addAttribute("leaveTypeForm", leaveTypeForm);
		model.addAttribute("listOfLeaveType", leaveTypeForms);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.LEAVE_TYPES_TITLE);
		return NavigationConstant.ADMIN_PREFIX_PAGE
				+ NavigationConstant.LEAVE_TYPE;
	}
	
	
	@RequestMapping(value = "/addLeaveType", method = RequestMethod.GET)
	public @ResponseBody LeaveTypeForm addDesignation(@RequestParam("leaveType") String leaveType,@RequestParam("description") String description,HttpSession session) {
		LeaveTypeForm leaveTypeForm = new LeaveTypeForm();
		leaveTypeForm.setEntryBy(CurrentUserDataUtil.getCurrentLoggedUser(session));
		leaveTypeForm.setLeaveType(leaveType);
		leaveTypeForm.setDescription(description);
		leaveTypeForm.setDoe(DateUtils.getCurrentTimeIntoTimestamp());
		leaveTypeForm.setSdoe(DateUtils.convertDateIntoString(new Date()));
		leaveTypeForm.setSdom(DateUtils.convertDateIntoString(new Date()));
		leaveTypeForm.setDom(DateUtils.getCurrentTimeIntoTimestamp());
		String maxid = leaveTypeService.addLeaveType(leaveTypeForm);
		leaveTypeForm.setLeaveTypeId(Integer.parseInt(maxid));
		return leaveTypeForm;
	}
		

	@RequestMapping(value = "/editLeaveType.htm", method = RequestMethod.GET)
	public String editLeaveType(HttpServletRequest request, Model model) {
		Integer leavId = Integer.parseInt(request.getParameter("leaveTypeId"));
		LeaveTypeForm leaveTypeForm = leaveTypeService
				.findLeaveTypeById(leavId);
		model.addAttribute("leaveTypeForm", leaveTypeForm);
		List<LeaveTypeForm> leaveTypeForms = leaveTypeService.findLeaveType();
		model.addAttribute("buttonLable", "Update LeaveType");
		model.addAttribute("leaveTypeForms", leaveTypeForms);
		return NavigationConstant.ADMIN_PREFIX_PAGE
				+ NavigationConstant.LEAVE_TYPE;
	}

	@RequestMapping(value = "/deleteLeaveType", method = RequestMethod.GET)
	public @ResponseBody String deleteLeaveType(HttpServletRequest request, Model model) {
		Integer lvId = Integer.parseInt(request.getParameter("leaveTypeId"));
		System.out.println("lvId" + lvId);
		leaveTypeService.deleteLeaveType(lvId);
		return "Leave Type with id "+lvId +" is deleted successfully.";
	}
}
