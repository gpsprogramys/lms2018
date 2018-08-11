package com.bas.admin.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bas.admin.service.LeaveReasonService;
import com.bas.admin.web.controller.form.LeaveReasonForm;
import com.bas.common.constant.NavigationConstant;
import com.bas.common.constant.PageTitleContant;
import com.bas.common.util.CurrentUserDataUtil;

/**
 * 
 * @author Amogh
 * 
 */
@Controller
public class BasLeaveReasonController {

	@Autowired
	@Qualifier("LeaveReasonServiceImpl")
	private LeaveReasonService leaveReasonService;

//	@RequestMapping(value = "/addLeaveReason.htm", method = RequestMethod.GET)
//	public String showAddLeaveReason(Model model) {
//		LeaveReasonForm leaveReasonForm = new LeaveReasonForm();
//		model.addAttribute("leaveReasonForm", leaveReasonForm);
//		List<LeaveReasonForm> leaveReasonForms = leaveReasonService
//				.findLeaveReason();
//		model.addAttribute("buttonLable", "Add LeaveReason");
//		model.addAttribute("leaveReasonForms", leaveReasonForms);
//		return NavigationConstant.ADMIN_PREFIX_PAGE
//				+ NavigationConstant.LEAVE_REASON;
//	}
	
	@RequestMapping(value="/addLeaveReason", method= RequestMethod.GET)
	public String addleavereasons(Model model){
		LeaveReasonForm leavereasonform= new LeaveReasonForm();
		model.addAttribute("leaveReasonForm", leavereasonform);
		/*model.addAttribute("buttonLable", "Add LeaveReason");*/
		List<LeaveReasonForm> leaveReasonFormList = leaveReasonService.findLeaveReason();		
		model.addAttribute("leaveReasonFormList", leaveReasonFormList);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.LEAVE_REASON_TITLE);
		return NavigationConstant.ADMIN_PREFIX_PAGE + NavigationConstant.LEAVE_REASON;
	}
	
	@RequestMapping(value="/addLeaveReasontodb", method=RequestMethod.POST)
	public String submitAddLeaveReasons(@ModelAttribute("leaveReasonForm") LeaveReasonForm leavereasonform, Model model,HttpSession session)
	{
		String currentUser=CurrentUserDataUtil.getCurrentLoggedUser(session);
		leavereasonform.setEntryBy(currentUser);
		leaveReasonService.addLeaveReason(leavereasonform);
		List<LeaveReasonForm> leaveReasonFormList = leaveReasonService.findLeaveReason();		
		model.addAttribute("leaveReasonFormList", leaveReasonFormList);
		model.addAttribute("applicationMessage","Leave reason has been added successfully...<br/>&nbsp;&nbsp;");	
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.LEAVE_REASON_TITLE);
		return NavigationConstant.ADMIN_PREFIX_PAGE + NavigationConstant.LEAVE_REASON;
		
	}

	@RequestMapping(value = "/addLeaveReason.htm", method = RequestMethod.POST)
	public String submitAddLeaveReason(
			@ModelAttribute("leaveReasonForm") LeaveReasonForm leaveReasonForm,
			@RequestParam(value = "buttonAction", required = false) String buttonAction,
			Model model) {
		if (buttonAction != null && buttonAction.equals("Update LeaveReason")) {

			leaveReasonService.updateLeaveReason(leaveReasonForm);
			System.out.println("Contr : " + leaveReasonForm);
		} else {
			leaveReasonService.addLeaveReason(leaveReasonForm);
		}
		List<LeaveReasonForm> leaveReasonForms = leaveReasonService
				.findLeaveReason();
		model.addAttribute("buttonLable", "Add LeaveReason");
		LeaveReasonForm nleaveReasonForm = new LeaveReasonForm();
		model.addAttribute("leaveReasonForm", nleaveReasonForm);
		model.addAttribute("leaveReasonForms", leaveReasonForms);
		System.out.println(leaveReasonForms);
		return NavigationConstant.ADMIN_PREFIX_PAGE
				+ NavigationConstant.LEAVE_REASON;
	}

	@RequestMapping(value = "/editLeaveReason.htm", method = RequestMethod.GET)
	public String editLeaveReason(HttpServletRequest request, Model model) {
		Integer deptId = Integer.parseInt(request.getParameter("leaveId"));
		LeaveReasonForm leaveReasonForm = leaveReasonService
				.findLeaveReasonById(deptId);
		model.addAttribute("leaveReasonForm", leaveReasonForm);
		List<LeaveReasonForm> leaveReasonForms = leaveReasonService
				.findLeaveReason();
		model.addAttribute("buttonLable", "Update LeaveReason");
		model.addAttribute("leaveReasonForms", leaveReasonForms);
		return NavigationConstant.ADMIN_PREFIX_PAGE
				+ NavigationConstant.LEAVE_REASON;
	}

	@RequestMapping(value = "/deleteLeaveReason.htm", method = RequestMethod.GET)
	public String deleteLeaveReason(HttpServletRequest request, Model model) {
		Integer lvId = Integer.parseInt(request.getParameter("leaveId"));
		System.out.println("lvId" + lvId);
		leaveReasonService.deleteLeaveReason(lvId);
		return "redirect:addLeaveReason.htm";
	}
}
