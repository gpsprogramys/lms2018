package com.bas.employee.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bas.common.constant.NavigationConstant;
import com.bas.employee.service.BasFacultyService;
import com.bas.employee.web.controller.form.FaculityLeaveMasterVO;
import com.bas.employee.web.controller.form.LoginForm;

/**
 * @author kushal
 *
 */

@Controller
@Scope("request")
public class EmployeeLeaveApplyController {
	
	
	@Autowired
	@Qualifier("BasFacultyServiceImpl")
	private BasFacultyService basFacultyService;
	
	
	
	@ModelAttribute("leaveReasons")
    public List<String> leaveReasons() {
        List<String> leaveReasonList = new ArrayList<String>();
        leaveReasonList.add("(Select an option)");
        leaveReasonList.add("Not feeling well");
        leaveReasonList.add("I have some urgent work at home");
        leaveReasonList.add("I have appointment with doctor");
        leaveReasonList.add("Others");
        return leaveReasonList;
    }
	
	
	@ModelAttribute("managerList")
	public List<String> getReportingManagerList() {
		List<FaculityLeaveMasterVO> reportingManagerList = new ArrayList<FaculityLeaveMasterVO>();
		reportingManagerList=basFacultyService.getReportingManagerList();
		List<String> managerList=new ArrayList<String>();
		for (FaculityLeaveMasterVO faculityLeaveMasterVO : reportingManagerList) {
		managerList.add(faculityLeaveMasterVO.getEmpid()+"-"+faculityLeaveMasterVO.getName());
		}
		return managerList;
	}
	
	
	
	@ModelAttribute("ccToList")
	public List<String> getCCToList() {
		List<FaculityLeaveMasterVO> ccTosList = new ArrayList<FaculityLeaveMasterVO>();
		ccTosList=basFacultyService.getCCToList();
		List<String> ccToList=new ArrayList<String>();
		for (FaculityLeaveMasterVO faculityLeaveMasterVO : ccTosList) {
		ccToList.add(faculityLeaveMasterVO.getEmpid()+"-"+faculityLeaveMasterVO.getName());
		}
		return ccToList;
	}
	
	
	@RequestMapping(value ="/leaveApplyManager", method = RequestMethod.GET)
	public String makeEmptyObjectAddendum(HttpSession session,Model model) {
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		FaculityLeaveMasterVO faculityLeaveMasterVO = new FaculityLeaveMasterVO();
		 Calendar cal = Calendar.getInstance(); 
		int month = cal.get(Calendar.MONTH)+1;
		 String leaveMonth;
		 if(month<10)
		 {leaveMonth="0"+month;}
		 else
		 {leaveMonth=month+"";}
		//faculityLeaveMasterVO=basFacultyService.findLeaveAppData(loginForm.getEid(),leaveMonth);
		 faculityLeaveMasterVO=basFacultyService.findLeaveAppData(loginForm.getEid());
		 /*String reportingManager = faculityLeaveMasterVO.getReportingManager();
		 System.out.println(reportingManager);*/
		//To see who are the employees
		model.addAttribute("employees_list", basFacultyService.findRelation(loginForm.getEid()));

		model.addAttribute("faculityLeaveMasterVO", faculityLeaveMasterVO);
		
				
		return NavigationConstant.LMS_PREFIX_PAGE
				+ NavigationConstant.LEAVE_APPLY_MANAGER;

	}
		//Rahul Jain
		@RequestMapping(value = "/leaveApplyManager", method = RequestMethod.POST)
		public String addendumLeave(@ModelAttribute("faculityLeaveMasterVO") FaculityLeaveMasterVO faculityLeaveMasterVO,Model model ) 
		{
			basFacultyService.applyAddendumLeave(faculityLeaveMasterVO);
			FaculityLeaveMasterVO nfaculityLeaveMasterVO = new FaculityLeaveMasterVO();
			model.addAttribute("message","Leave Successfully Applied");
			model.addAttribute("faculityLeaveMasterVO", nfaculityLeaveMasterVO);
			return NavigationConstant.LMS_PREFIX_PAGE
					+NavigationConstant.LEAVE_APPLY_MANAGER;
		}
	
	
	@RequestMapping(value = "/enterRmFacultyLeaveHistory.htm", method = RequestMethod.POST)
	public String enterRmLeaveHistory(
			@ModelAttribute("faculityLeaveMasterVO") FaculityLeaveMasterVO faculityLeaveMasterVO,
			Model model) {
	try{
	
	basFacultyService.enterRmLeaveHistory(faculityLeaveMasterVO);
	model.addAttribute("message","Leave Applied Successfully");	
	
	
	}
	catch(DuplicateKeyException e)
	{
	model.addAttribute("message","Leave Already Applied For This Date!");	
	}
	return NavigationConstant.FACULTY_PREFIX_PAGE
	+ NavigationConstant.FACULTY_LEAVE_APPLY;
	}
	
	
	
	
	
	
	@RequestMapping(value = "/enterFacultyLeaveHistory.htm", method = RequestMethod.POST)
	public String enterleaveHistory(
			@ModelAttribute("faculityLeaveMasterVO") FaculityLeaveMasterVO faculityLeaveMasterVO,
			Model model) {
	try{
	basFacultyService.enterLeaveHistory(faculityLeaveMasterVO);
	model.addAttribute("message","Leave Applied Successfully");	

	}
	catch(DuplicateKeyException e)
	{
	model.addAttribute("message","Leave Already Applied For This Date!");	
	}
	return NavigationConstant.FACULTY_PREFIX_PAGE
	+ NavigationConstant.FACULTY_LEAVE_APPLY;
	}
	

	@RequestMapping(value = "/searchEmployees", method = RequestMethod.GET)
	public @ResponseBody List<String> showEmployee(HttpServletRequest request) {	
		return 	basFacultyService.searchEmployee(request.getParameter("term"));
	}

	/*@RequestMapping(value ="/leaveEntry", method = RequestMethod.GET)
	public String makeEmptyObject(HttpSession session,Model model) {
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		FaculityLeaveMasterVO faculityLeaveMasterVO = new FaculityLeaveMasterVO();
		 Calendar cal = Calendar.getInstance(); 
		int month = cal.get(cal.MONTH)+1;
		 String leaveMonth;
		 if(month<10)
		 {leaveMonth="0"+month;}
		 else
		 {leaveMonth=month+"";}System.out.println(leaveMonth);
	//	faculityLeaveMasterVO=basFacultyService.findLeaveAppData(loginForm.getEid(),leaveMonth);
		 faculityLeaveMasterVO=basFacultyService.findLeaveAppData("555",leaveMonth);
		 model.addAttribute("faculityLeaveMasterVO", faculityLeaveMasterVO);
		 model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.LEAVE_APPLE_PAGE);
		return NavigationConstant.LMS_PREFIX_PAGE
				+ NavigationConstant.LEAVE_APPLY_PAGE;

	}
	
	@RequestMapping(value = "/leaveEntry", method = RequestMethod.POST)
	public String submitHolidayEntry(@ModelAttribute("faculityLeaveMasterVO") FaculityLeaveMasterVO faculityLeaveMasterVO,	Model model) {
		System.out.println("before COntroller" + faculityLeaveMasterVO.getLeaveFrom());
		basFacultyService.addLeaveEntry(faculityLeaveMasterVO);
		 model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.SUCCESS_PAGE);
			model.addAttribute("successMessage", "Thank you for submitting your application. Please keep checking your status for the approval.");
			return NavigationConstant.EMPLOYEE_PREFIX_PAGE
					+ NavigationConstant.SUCCESS_EMPLOYEE_PAGE;
	}*/
	
	
	
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// now Spring knows how to handle multipart object and convert them
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        //Create a new CustomDateEditor
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        //Register it as custom editor for the Date type
		binder.registerCustomEditor(Date.class, editor);
	}

	
	
	
	

}
