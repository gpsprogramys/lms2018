package com.bas.admin.web.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.bas.admin.service.EmployeeTypeService;
import com.bas.admin.service.LeaveTypeService;
import com.bas.admin.web.controller.form.EmployeeTypeForm;
import com.bas.admin.web.controller.form.FaculityDailyAttendanceReportVO;
import com.bas.common.constant.NavigationConstant;
import com.bas.common.constant.PageTitleContant;
import com.bas.common.message.vo.ApplicationMessageVo;
import com.bas.employee.service.BasFacultyService;
import com.bas.employee.utility.MonthUtility;
import com.bas.employee.web.controller.form.FaculityLeaveMasterVO;
import com.bas.employee.web.controller.form.LoginForm;
import com.bas.employee.web.controller.form.ReportingManagerVO;

/**
 * 
 * @author Amogh
 * 
 */
@Controller
public class BasEmployeeTypeController {

	@Autowired
	@Qualifier("EmployeeTypeServiceImpl")
	private EmployeeTypeService employeeTypeService;

	@Autowired
	@Qualifier("BasFacultyServiceImpl")
	private BasFacultyService basFacultyService;

	@Autowired
	@Qualifier("LeaveTypeServiceImpl")
	private LeaveTypeService leaveTypeService;

	@RequestMapping(value = "/adminUpdateAttendance", method = RequestMethod.GET)
	public String adminUpdateAttendance(HttpServletRequest request, Model model) {
		MonthUtility monthUtility = new MonthUtility();
		FaculityDailyAttendanceReportVO facultyAttendStatusVO = new FaculityDailyAttendanceReportVO(); 
		monthUtility.setMonth(monthUtility.getDate());
		model.addAttribute("adminMonthUtility", monthUtility);
		model.addAttribute("EmptyfacultyAttendStatusVO", facultyAttendStatusVO);	
		model.addAttribute("adminMonthList", monthUtility.getMonthList(monthUtility.getDate()));		
		return NavigationConstant.ADMIN_PREFIX_PAGE
				+ NavigationConstant.ADMIN_ATTEND_UPDATE_PAGE;		
	}

	@RequestMapping(value = "/searchEmployee", method = RequestMethod.GET)
	public @ResponseBody List<String> showEmployee(HttpServletRequest request) {	
		System.out.println("Searching");
		return 	employeeTypeService.searchEmployee(request.getParameter("term"));
	}

	@ModelAttribute("managerList")
	public List<String> getReportingManagerList() {
		List<FaculityLeaveMasterVO> reportingManagerList =basFacultyService.getReportingManagerList();
		List<String> managerList=new ArrayList<String>();
		for (FaculityLeaveMasterVO faculityLeaveMasterVO : reportingManagerList) {
			managerList.add(faculityLeaveMasterVO.getId()+"-"+faculityLeaveMasterVO.getName()+ "-" + faculityLeaveMasterVO.getDesignation()+"-"+faculityLeaveMasterVO.getDepartment());
		}
		return managerList;
	}
	
	@RequestMapping(value = "/searchingIds", method = RequestMethod.GET)
	public @ResponseBody List<String> showIds(HttpServletRequest request) {	
		return 	basFacultyService.searchId(request.getParameter("term"));
	}
	
	
	@RequestMapping(value = "/searchingEmployees", method = RequestMethod.GET)
	public @ResponseBody List<String> showEmployees(HttpServletRequest request) {	
		return 	basFacultyService.searchEmployeeByManId(request.getParameter("term"));
	}
	
	
	@RequestMapping(value = "/deleteEmployeeManagerByEmpId", method = RequestMethod.GET)
	public @ResponseBody ApplicationMessageVo deleteEmployeeManagerByEmpId(@RequestParam("empid") String empid) {
		String message=basFacultyService.deleteEmployeeManagerByEmpId(empid);
		ApplicationMessageVo applicationMessageVo=new ApplicationMessageVo();
		applicationMessageVo.setStatus("success");
		applicationMessageVo.setMessage(message);
		return applicationMessageVo;
	}

	@RequestMapping(value = "/reporteeManagement", method = RequestMethod.GET)
	public String currentAttendus(HttpServletRequest request, Model model) {
		FaculityLeaveMasterVO faculityLeaveMasterVO = new FaculityLeaveMasterVO();
		model.addAttribute("faculityLeaveMasterVO", faculityLeaveMasterVO);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.REPORTEE_MANAGEMENT_PAGE);
		return NavigationConstant.ADMIN_PREFIX_PAGE
				+ NavigationConstant.REPORTEE_MANAGEMENT;	
	}
	
	@RequestMapping(value = "/employeeListByManagerId", method = RequestMethod.GET)
	@ResponseBody 
	public List<ReportingManagerVO> employeeListByManagerId(@RequestParam("managerData") String managerData, Model model) {
		String managerId=managerData.split("-")[0];
		List<ReportingManagerVO> managerVOsList=basFacultyService.findEmployeeListByManager(managerId);
		return managerVOsList;
	}
	
	
	

	@RequestMapping(value="/retreiveEmployeeForAdmin",method=RequestMethod.POST)
	public @ResponseBody List<FaculityDailyAttendanceReportVO> showAttendStatus(@RequestParam(value="requiredData") String employeeName,HttpSession session,
			Model model)
	{	
		MonthUtility monthUtility = new MonthUtility();
		String[] names = employeeName.split(" ");
		String employeeId = employeeTypeService.getEmployeeId(names[0], names[1]);
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);		
		List<FaculityDailyAttendanceReportVO> facultyAttendStatusVOlist=basFacultyService.findAttendStatus(employeeId,monthUtility.getDate());	

		return facultyAttendStatusVOlist;
	}



	@RequestMapping(value="/retreiveEmployeeForAdminMonth",method=RequestMethod.POST)
	public @ResponseBody List<FaculityDailyAttendanceReportVO> showAttendStatus(@RequestParam(value="requiredData") String monthVal,
			@RequestParam(value="employeeName") String employeeName,HttpSession session)
	{	
		String[] names = employeeName.split(" ");
		String employeeId = employeeTypeService.getEmployeeId(names[0], names[1]);
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);		
		List<FaculityDailyAttendanceReportVO> facultyAttendStatusVOlist=basFacultyService.findAttendStatus(employeeId,Integer.parseInt(monthVal));	

		return facultyAttendStatusVOlist;
	}

	@RequestMapping(value = "/updateEmployee", method = RequestMethod.POST)
	public @ResponseBody String editEmployeeType(@ModelAttribute(value="EmptyfacultyAttendStatusVO") FaculityDailyAttendanceReportVO facultyAttendStatusVO, 
			HttpServletRequest request) {
		System.out.println(facultyAttendStatusVO.getFid()+" Hello FID");
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		String newdate="";
		try {
			date = format.parse(facultyAttendStatusVO.getCdate());
			newdate = newFormat.format(date);			
		} catch (Exception e) {		
			e.printStackTrace();
		}
		if(facultyAttendStatusVO.getFid()==0){
			String employeeName = request.getParameter("employeeName");
			String[] names = employeeName.split(" ");
			String employeeId = employeeTypeService.getEmployeeId(names[0], names[1]);			
			String message = basFacultyService.updateEmployee(facultyAttendStatusVO, employeeId,newdate);
			return message;	
		}else{
			String message = basFacultyService.updateEmployee(facultyAttendStatusVO, Integer.toString(facultyAttendStatusVO.getFid()),newdate);
			System.out.println(message);
			return message;	
		}
			
	}
	


	@RequestMapping(value = "/deleteAttendus", method = RequestMethod.GET)
	public @ResponseBody String deleteAttendus(HttpServletRequest request,@RequestParam(value="employeeName") String employeeName,
			 @RequestParam(value="attndDate") String attndDate) {		
		
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		String newdate="";
		try {
			date = format.parse(attndDate);
			newdate = newFormat.format(date);			
		} catch (ParseException e) {		
			e.printStackTrace();
		}
		
		if(employeeName.isEmpty()){
			String fid = request.getParameter("fid");
			System.out.println("Testing Delete Attendus");	
			String message = basFacultyService.deleteAttendus(fid,newdate);
			System.out.println(" Delete Using Id "+ message);
			return message;
		}else{
			String[] names = employeeName.split(" ");
			String employeeId = employeeTypeService.getEmployeeId(names[0], names[1]);				
			String message = basFacultyService.deleteAttendus(employeeId,newdate);
			return message;
		}
		
	}

	@RequestMapping(value = "/addEmployeeType.htm", method = RequestMethod.GET)
	public String showAddEmploymentType(Model model) {
		EmployeeTypeForm employeeTypeForm = new EmployeeTypeForm();
		model.addAttribute("employeeTypeForm", employeeTypeForm);
		model.addAttribute("buttonLable", "Add EmployeeType");
		List<EmployeeTypeForm> employeeTypeForms = employeeTypeService
				.findEmployeeTypes();
		model.addAttribute("employeeTypeForms", employeeTypeForms);
		return NavigationConstant.ADMIN_PREFIX_PAGE
				+ NavigationConstant.ADD_EMPLOYEE_TYPE;
	}

	@RequestMapping(value = "/addEmployeeType.htm", method = RequestMethod.POST)
	public String submitAddEmployee(
			@ModelAttribute("employeeTypeForm") EmployeeTypeForm employeeTypeForm,
			@RequestParam(value = "buttonAction", required = false) String buttonAction,
			Model model) {
		if (buttonAction != null && buttonAction.equals("Update LeaveReason")) {

			employeeTypeService.updateEmployeeType(employeeTypeForm);
			System.out.println("Contr : " + employeeTypeForm);
		} else {
			employeeTypeService.addEmployeeType(employeeTypeForm);
		}
		List<EmployeeTypeForm> employeeTypeForms = employeeTypeService.findEmployeeTypes();
		model.addAttribute("buttonLable", "Add EmployeeType");
		EmployeeTypeForm nemployeeTypeForm = new EmployeeTypeForm();
		model.addAttribute("employeeTypeForm", nemployeeTypeForm);
		model.addAttribute("employeeTypeForms", employeeTypeForms);
		System.out.println(employeeTypeForms);
		return NavigationConstant.ADMIN_PREFIX_PAGE
				+ NavigationConstant.ADD_EMPLOYEE_TYPE;
	}


	@RequestMapping(value = "/deleteEmployeeType.htm", method = RequestMethod.GET)
	public String deleteEmployeeType(HttpServletRequest request, Model model) {
		Integer empId = Integer.parseInt(request.getParameter("employeeTypeId"));
		System.out.println("empId" + empId);
		employeeTypeService.deleteEmployeeType(empId);
		return "redirect:addEmployeeType.htm";
	}
}