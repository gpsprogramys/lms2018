package com.bas.admin.web.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.bas.common.constant.PageTitleContant;
import com.bas.employee.service.BasFacultyService;
import com.bas.employee.web.controller.form.FaculityLeaveMasterVO;
import com.bas.employee.web.controller.form.ManualAttendanceVO;

/**
 * 
 * @author kushal mehta
 * 
 */
@Controller
@Scope("request")
//add class level comments
public class BasLeaveApplicationController {
	
	/**
     * Initiate Logger for this class
     */
    private static final Log logger = LogFactory.getLog(BasLeaveApplicationController.class);
    
    
    @Autowired
	@Qualifier("BasFacultyServiceImpl")
	private BasFacultyService basFacultyService;
    

	//don't use hard coded values. use excel or xml
	@ModelAttribute("leaveReasons")
	public List<String> leaveReasons() {
		List<String> leaveReasonList = new ArrayList<String>();
		leaveReasonList.add("Not feeling well");
		leaveReasonList.add("I have some urgent work at home");
		leaveReasonList.add("I have appointment with doctor");
		return leaveReasonList;
	}

	@ModelAttribute("leaveTypes")
	//put method level comments
	public List<String> leaveTypes() {
		List<String> leaveTypeList = new ArrayList<String>();
		leaveTypeList.add("CL");
		leaveTypeList.add("EL");
		leaveTypeList.add("OD");
		leaveTypeList.add("CompL");
		return leaveTypeList;
	}

	@RequestMapping(value = "/facultyidAndNames.htm", method = RequestMethod.GET)
	public @ResponseBody
	String fetchFacultyidAndNames(Model model) {
		List<FaculityLeaveMasterVO> faculityLeaveMasterVOs = basFacultyService
				.findAllEmpDb();
		StringBuilder stringBuilder = new StringBuilder();
		for (FaculityLeaveMasterVO faculityLeaveMasterVO : faculityLeaveMasterVOs) {
			stringBuilder.append(faculityLeaveMasterVO.getName() + "-"
					+ faculityLeaveMasterVO.getEmpid() + ",");
		}
		return stringBuilder.toString();
	}

	@RequestMapping(value = "/uploadManualAttendance.htm", method = RequestMethod.POST)
	public String uploadManualAttendance(@ModelAttribute("manualAttendanceVO") ManualAttendanceVO manualAttendanceVO,Model model) {
		ManualAttendanceVO attendanceVO=new ManualAttendanceVO();
		String result=basFacultyService.addEmployeeManulAttendance(manualAttendanceVO);
		//make these string literals in constant files. will be problematic when using internationalization.
		if("NOTDONE".equals(result)) {
			model.addAttribute("applicationMessage","Attendance is already marked, please check it.");
			model.addAttribute("manualAttendanceVO", manualAttendanceVO);
		}else{
			model.addAttribute("applicationMessage","Attendance is marked sucessfully into the system ("+manualAttendanceVO.getName()+").");
			model.addAttribute("manualAttendanceVO", attendanceVO);
		}
		return NavigationConstant.ADMIN_PREFIX_PAGE
				+ NavigationConstant.MANUAL_ATTENDANCE_PAGE;
	}

	@RequestMapping(value = "/manualAttendance.htm", method = RequestMethod.GET)
	public String showManualAttendance(Model model) {
		//dont use syso. use logging instead.each application should have a log file. log files will help dev. to understand the exceptions the client faces.
		//System.out.println("get method");
		
		if(logger.isDebugEnabled()){
			logger.debug("get method");
		
		}
		ManualAttendanceVO manualAttendanceVO = new ManualAttendanceVO();
		model.addAttribute("manualAttendanceVO", manualAttendanceVO);
		return NavigationConstant.ADMIN_PREFIX_PAGE
				+ NavigationConstant.MANUAL_ATTENDANCE_PAGE;
	}

	@RequestMapping(value = "/manualAttendancePost.htm", method = RequestMethod.POST)
	public String manualAttendanceSubmit(HttpServletRequest request, Model model) {
		//Do not create un-necessary object
		ManualAttendanceVO manualAttendanceVO = null;
		String name = request.getParameter("searchFiled");
		if (name != null) {
			String tokens[] = name.split("-");
			String empid = tokens[1];
			manualAttendanceVO = basFacultyService
					.findEmployeeDataForAttendance(empid);
		}
		manualAttendanceVO.setDetail("BAS device was not working.");
		model.addAttribute("manualAttendanceVO", manualAttendanceVO);
		return NavigationConstant.ADMIN_PREFIX_PAGE
				+ NavigationConstant.MANUAL_ATTENDANCE_PAGE;
	}
	


	@RequestMapping(value = "/leaveApplication.htm", method = RequestMethod.GET)
	public String showAddDepartment(Model model) {
		//use if info enabled here
		logger.info("inside the method showAddDepartment");
		FaculityLeaveMasterVO faculityLeaveMasterVO = new FaculityLeaveMasterVO();
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.MANAGEMENT_HOME_TITLE);
		model.addAttribute("faculityLeaveMasterVO", faculityLeaveMasterVO);
		//model.addAttribute("any", faculityLeaveMasterVO);
		
		return NavigationConstant.ADMIN_PREFIX_PAGE
				+ NavigationConstant.LEAVE_APPLY_PAGE;

	}

	

	@RequestMapping(value = "/leaveApplicationPost.htm", method = RequestMethod.POST)
	public String getSearch(HttpServletRequest request, Model model) {

		FaculityLeaveMasterVO faculityLeaveMasterVO = new FaculityLeaveMasterVO();
		String name = request.getParameter("searchFiled");
		if (name != null) {
			String tokens[] = name.split("-");
			String empid = tokens[1];
			//put the calendar part in the util file not in the controller
			 Calendar cal = Calendar.getInstance(); 
				int month = cal.get(Calendar.MONTH)+1;
				 String leaveMonth;
				 if(month<10)
				 {leaveMonth="0"+month;}
				 else
				 {leaveMonth=month+"";}
			faculityLeaveMasterVO = basFacultyService.findLeaveAppData(empid);
		}

		model.addAttribute("faculityLeaveMasterVO", faculityLeaveMasterVO);
		return NavigationConstant.ADMIN_PREFIX_PAGE
				+ NavigationConstant.LEAVE_APPLY_PAGE;

	}

	@RequestMapping(value = "/renderEmpImage", method = RequestMethod.GET)
	public void renderPhoto(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");
		response.setContentType("image/jpg");
		byte[] image = basFacultyService.findEmpPhotoByName(name);
		if (image != null) {
			response.getOutputStream().write(image);
		}
	}

	@RequestMapping(value = "/enterLeaveHistory.htm", method = RequestMethod.POST)
	public String enterleaveHistory(
			@ModelAttribute("faculityLeaveMasterVO") FaculityLeaveMasterVO faculityLeaveMasterVO,
			Model model) {
		//use exception handling in spring mvc
		try{
		String result = basFacultyService
				.enterLeaveHistory(faculityLeaveMasterVO);
		if(logger.isInfoEnabled()){
		logger.info(result);
		}
		}
		catch(DuplicateKeyException e)
		{
		//To Do;	
		}
		String str = finaAllLeaveHistory(model);
		return str;

	}

	@RequestMapping(value = "/allEmployeeHistory.htm", method = RequestMethod.GET)
	public String finaAllLeaveHistory(Model model) {
		List<FaculityLeaveMasterVO> faculityLeaveMasterVOslist = basFacultyService
				.findAllLeaveHistory();
		model.addAttribute("faculityLeaveMasterVOslist",
				faculityLeaveMasterVOslist);
		return NavigationConstant.ADMIN_PREFIX_PAGE
				+ NavigationConstant.SHOW_ALL_LEAVE_HISTORY;
	}
	
	@RequestMapping(value = "/allPendingLeaveHistory.htm", method = RequestMethod.GET)
	public String finaAllPendingLeaveHistory(Model model) {
		List<FaculityLeaveMasterVO> faculityLeaveMasterVOslist = basFacultyService
				.findAllPendingLeaveHistory();
		model.addAttribute("faculityLeaveMasterVOslist",
				faculityLeaveMasterVOslist);
		
		return NavigationConstant.ADMIN_PREFIX_PAGE
				+ NavigationConstant.SHOW_ALL_PENDING_LEAVE_HISTORY;
	}

	
	@RequestMapping(value = "/allRmPendingLeaveHistory.htm", method = RequestMethod.GET)
	public String finaAllRmPendingLeaveHistory(Model model) {
		List<FaculityLeaveMasterVO> faculityLeaveMasterVOslist = basFacultyService
				.findAllRmPendingLeaveHistory();
		model.addAttribute("faculityLeaveMasterVOslist",
				faculityLeaveMasterVOslist);
		return NavigationConstant.ADMIN_PREFIX_PAGE
				+ NavigationConstant.SHOW_ALL_PENDING_RM_LEAVE_HISTORY;
	}

	
	
	@RequestMapping(value = "/approveLeave.htm", method = RequestMethod.POST)
	public String approveLeave(HttpServletRequest request, Model model){
		String empNo = request.getParameter("empNo");
		String date = request.getParameter("leaveStartDate");
		String lstatus="APPROVED";
		basFacultyService.updateLeaveHistory(empNo,date,lstatus);
		return finaAllPendingLeaveHistory(model);
	}
	
	@RequestMapping(value = "/rmApproveLeave.htm", method = RequestMethod.POST)
	public String rmApproveLeave(HttpServletRequest request, Model model){
		String empNo = request.getParameter("empNo");
		String date = request.getParameter("leaveStartDate");
		String lstatus="RM-APPROVED";
		basFacultyService.updateLeaveHistory(empNo,date,lstatus);
		return finaAllRmPendingLeaveHistory(model);
	}
	
	@RequestMapping(value = "/rejectLeave.htm", method = RequestMethod.POST)
	public String rejectLeave(HttpServletRequest request, Model model){
		String empNo = request.getParameter("empNo");
		String date = request.getParameter("leaveStartDate");
		String lstatus="REFUSED";
		basFacultyService.updateLeaveHistory(empNo,date,lstatus);
		return finaAllPendingLeaveHistory(model);
	}
	
	
	@RequestMapping(value = "/rmRejectLeave.htm", method = RequestMethod.POST)
	public String rmRejectLeave(HttpServletRequest request, Model model){
		String empNo = request.getParameter("empNo");
		String date = request.getParameter("leaveStartDate");
		String lstatus="RM-REFUSED";
		basFacultyService.updateLeaveHistory(empNo,date,lstatus);
		return finaAllRmPendingLeaveHistory(model);
	}
	
	

	@RequestMapping(value = "/deleteLeaveHistory.htm", method = RequestMethod.POST)
	public String deleteLeaveHistory(HttpServletRequest request, Model model)
			throws ParseException {
		String empNo = request.getParameter("empNo");
		String sdate = request.getParameter("leaveStartDate");
		basFacultyService.deleteLeaveHistory(empNo, sdate);
		return finaAllPendingLeaveHistory(model);
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// now Spring knows how to handle multipart object and convert them
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //Create a new CustomDateEditor
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        //Register it as custom editor for the Date type
		binder.registerCustomEditor(Date.class, editor);
	}


}
