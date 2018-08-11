package com.bas.admin.web.controller.temp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bas.admin.dao.entity.HolidayEntryEntity;
import com.bas.admin.service.AdminBasReportService;
import com.bas.admin.service.DepartmentService;
import com.bas.admin.web.controller.form.DepartmentForm;
import com.bas.admin.web.controller.form.EmployeeMonthAttendanceVO;
import com.bas.admin.web.controller.form.EmployeeMonthAttendanceVOWrapper;
import com.bas.admin.web.controller.form.FaculityDailyAttendanceReportVO;
import com.bas.admin.web.controller.form.FacultyManualAttendance;
import com.bas.admin.web.controller.form.FacultyMonthlyAttendanceStatusVO;
import com.bas.admin.web.controller.form.InTimeDescComparator;
import com.bas.admin.web.controller.form.ManualAttendanceDepartmentComparator;
import com.bas.admin.web.controller.form.MessageStatus;
import com.bas.admin.web.controller.form.OrganisationTimeForm;
import com.bas.common.constant.NavigationConstant;
import com.bas.common.constant.PageTitleContant;
import com.bas.common.dao.entity.EmployeeHeader;
import com.bas.common.util.CurrentUserDataUtil;
import com.bas.common.util.DateUtils;
import com.bas.employee.service.BasFacultyService;


/**
 *
 * @author pkumar
 *
 */

@Controller
@Scope(value="request",proxyMode=ScopedProxyMode.DEFAULT)
public class AdminAttendenceController {

	/**
	 * Initiate Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(AdminAttendenceController.class);

	@Autowired
	@Qualifier("BasFacultyServiceImpl")
	private BasFacultyService basFacultyService;
	
	
	@Autowired
	@Qualifier("DepartmentServiceImpl")
	private DepartmentService departmentService;


	@Autowired
	@Qualifier("AdminBasReportServiceImpl")
	private AdminBasReportService adminBasReportService;



	/*@RequestMapping(value = "/addOrganisation", method = RequestMethod.POST)
    public @ResponseBody String addDesignation(@ModelAttribute("intime") String inittime1,
    		@ModelAttribute("latein")String latein1, @ModelAttribute("outtime")String outtime1, @ModelAttribute("earlyout")String earlyout1,
            HttpSession session) {
        String currentUser=CurrentUserDataUtil.getCurrentLoggedUser(session);
        OrganisationTimeForm organisationTimeForm= new OrganisationTimeForm();
        organisationTimeForm.setEnteredby(currentUser);
        organisationTimeForm.setDoe(DateUtils.getCurrentTimeIntoTimestamp());
        organisationTimeForm.setDom(DateUtils.getCurrentTimeIntoTimestamp());
        organisationTimeForm.setIntime(inittime1);
        organisationTimeForm.setLatein(latein1);
        organisationTimeForm.setOuttime(outtime1);
        organisationTimeForm.setEarlyout(earlyout1);
       System.out.println(organisationTimeForm);
        String message = basFacultyService.addOrganisationTime(organisationTimeForm);
        return message;
    }*/

	@RequestMapping(value = "/organizationTime", method = RequestMethod.GET)
	public String addOrganisationTime (Model model) {
		OrganisationTimeForm cform=new OrganisationTimeForm();
		model.addAttribute("cform",cform);
		List<OrganisationTimeForm> organizationTimeVOslist = basFacultyService.findOrgTimes();
		model.addAttribute("organizationTimeVOslist", organizationTimeVOslist);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.ORGANIZATION_TIME_TITLE);
		return NavigationConstant.ADMIN_PREFIX_PAGE + NavigationConstant.ORAGANIGATION_TIME_PAGE;
	}

	@RequestMapping(value = "/addOrganisation", method = RequestMethod.POST)
	public String addOrganisationTime (@ModelAttribute(value="cform") OrganisationTimeForm fform,Model model,HttpSession session) {
		//Date doe=null;
	//	Date dom=null;
	//	String reformattedStr = null;
		String message="";

		String currentUser=CurrentUserDataUtil.getCurrentLoggedUser(session);
		System.out.println("Welcome end from organisation");
		int i=0;
		fform.setDoe(new Date());
		fform.setDom(new Date());
		fform.setActive(0);
		fform.setEnteredby(currentUser);
		/*	String intime=value.getParameter("t1");
		String outtime=value.getParameter("outtime");
		String earlyout=value.getParameter("earlyout");
		String latein=value.getParameter("latein");
		fform.setEarlyout(earlyout);
		fform.setIntime(intime);
		fform.setOuttime(outtime);
		fform.setLatein(latein);*/
		System.out.println(fform);
		message=basFacultyService.addOrganisationTime(fform);
		model.addAttribute("applicationMessage","Organization time has been added successfully...<br/>&nbsp;&nbsp;");
		List<OrganisationTimeForm> organizationTimeVOslist = basFacultyService.findOrgTimes();
		model.addAttribute("organizationTimeVOslist", organizationTimeVOslist);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.ORGANIZATION_TIME_TITLE);
		return NavigationConstant.ADMIN_PREFIX_PAGE + NavigationConstant.ORAGANIGATION_TIME_PAGE;
	}


	/**
	 *
	 * @param sno
	 * @return
	 */
	@RequestMapping(value = "/activeOrganisationTime", method = RequestMethod.GET,produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody MessageStatus activeOrganisationTime(@RequestParam(value = "sno",required=false) String sno) {
		if(logger.isDebugEnabled())
			logger.debug("Executing inside  activeOrganisationTime method");
		if(logger.isInfoEnabled())
			logger.info("Organization TIme Id = "+sno);
		String result=basFacultyService.activateOrganisationTime(sno);
		MessageStatus messageStatus=new MessageStatus();
		messageStatus.setMessage(result);
		messageStatus.setStatus("success");
		return messageStatus;
	}
	@RequestMapping(value = "/manualAttendance", method = RequestMethod.GET)
	public String getAllFacultyForManualAttendance(Model model) {
		DepartmentForm departmentForm = new DepartmentForm();
		model.addAttribute("departmentForm", departmentForm);
		List<FaculityDailyAttendanceReportVO> faculityManualAttendance = adminBasReportService
				.getAllFacultyForManualAttendance();
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		model.addAttribute("dateFromController", format.format(new Date()));
		if (faculityManualAttendance != null) {
			Collections.sort(faculityManualAttendance, new ManualAttendanceDepartmentComparator());
		}
		model.addAttribute("faculityManualAttendance", faculityManualAttendance);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.MANUAL_ATTENDANCE_TITLE);
		return NavigationConstant.ADMIN_PREFIX_PAGE + NavigationConstant.MANUAL_ATTENDANCE;
	}

	@RequestMapping(value = "/manualAttendance", method = RequestMethod.POST)
	public String submitAllFacultyForManualAttendance(String[] presentEmployeeID, Model model) {
		adminBasReportService.submitAllFacultyForManualAttendance(presentEmployeeID);
		FaculityDailyAttendanceReportVO lfaculityDailyAttendanceReportVO = new FaculityDailyAttendanceReportVO();
		model.addAttribute("faculityManualAttendance", lfaculityDailyAttendanceReportVO);
		return NavigationConstant.ADMIN_PREFIX_PAGE + NavigationConstant.MANUAL_ATTENDANCE;
	}
	
	@RequestMapping(value = "/viewOneEmployeeAttendance", method = RequestMethod.GET)
	public String viewOneEmployeeAttendance(Model model) {
		return "redirect:/admin/employeeAttendance";
	}

	@RequestMapping(value = "/adminAttendance", method = RequestMethod.GET)
	public String currentAttendus(HttpServletRequest request, Model model) {
		DepartmentForm departmentForm = new DepartmentForm();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		FaculityDailyAttendanceReportVO facultyAttendStatusVO = new FaculityDailyAttendanceReportVO();
		String date = format.format(new Date());
		// System.out.println(date);
		List<FaculityDailyAttendanceReportVO> faculityDailyAttendanceReportVOs = basFacultyService
				.showAttendusReport(date);
		model.addAttribute("departmentForm", departmentForm);
		model.addAttribute("EmptyfacultyAttendStatusVO", facultyAttendStatusVO);
		if (faculityDailyAttendanceReportVOs != null) {
			Collections.sort(faculityDailyAttendanceReportVOs);
		}
		model.addAttribute("totalRecords", faculityDailyAttendanceReportVOs!=null?faculityDailyAttendanceReportVOs.size():"0");
		model.addAttribute("faculityDailyAttendanceReportVOs", faculityDailyAttendanceReportVOs);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.TODAYS_ATTENDANCE_TITLE);
		return NavigationConstant.ADMIN_PREFIX_PAGE + NavigationConstant.ADMIN_ATTENDANCE_PAGE;
	}
	
	@RequestMapping(value = "/adminDeleteAttendance", method = RequestMethod.GET)
	public String deleteAttendus(HttpServletRequest request, Model model) {
		DepartmentForm departmentForm = new DepartmentForm();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		FaculityDailyAttendanceReportVO facultyAttendStatusVO = new FaculityDailyAttendanceReportVO();
		String date = format.format(new Date());
		// System.out.println(date);
		List<FaculityDailyAttendanceReportVO> faculityDailyAttendanceReportVOs = basFacultyService
				.showAttendusReport(date);
		model.addAttribute("departmentForm", departmentForm);
		model.addAttribute("EmptyfacultyAttendStatusVO", facultyAttendStatusVO);
		if (faculityDailyAttendanceReportVOs != null) {
			Collections.sort(faculityDailyAttendanceReportVOs);
		}
		model.addAttribute("totalRecords", faculityDailyAttendanceReportVOs!=null?faculityDailyAttendanceReportVOs.size():"0");
		model.addAttribute("faculityDailyAttendanceReportVOs", faculityDailyAttendanceReportVOs);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.TODAYS_ATTENDANCE_TITLE);
		return NavigationConstant.ADMIN_PREFIX_PAGE + NavigationConstant. ADMIN_DELETE_ATTENDANCE_PAGE;
	}
	@RequestMapping(value = "/empManualRequestAtt", method = RequestMethod.GET)
	public String empManualRequestAtt( Model model) {	
		List<FacultyManualAttendance> facultyManualAttendanceList=basFacultyService.empManualRequestAtt();
		model.addAttribute("facultyManualAttendanceList", facultyManualAttendanceList);	
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.HR_ATTENDANCE_APPROVAL);
		return NavigationConstant.ADMIN_PREFIX_PAGE + NavigationConstant. EMPLOYEE_MANUAL_REQUEST_ATTENDANCE;
	}
	@RequestMapping(value = "/approveEmpAttRequestById", method = RequestMethod.GET)
	public @ResponseBody String approveEmpAttRequestById(@RequestParam(value = "empid") String empid,@RequestParam(value = "cdate") String cdate) {	
		String value=basFacultyService.approveEmpAttRequestById(empid,cdate);
		return value;
	}
	@RequestMapping(value = "/rejectEmpAttRequestById", method = RequestMethod.GET)
	public @ResponseBody String rejectEmpAttRequestById(@RequestParam(value = "empid") String empid,@RequestParam(value = "cdate") String cdate) {	
		String value=basFacultyService.rejectEmpAttRequestById(empid,cdate);
		return value;
	}
	@RequestMapping(value = "/adminAttendanceByPastDate", method = RequestMethod.GET)
	public @ResponseBody List<FaculityDailyAttendanceReportVO> pastAttendus(
			@RequestParam(value = "pastDate") String pastDate) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatJSP = new SimpleDateFormat("MM/dd/yyyy");
		String reformattedStr = null;
		try {
			reformattedStr = myFormat.format(formatJSP.parse(pastDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<FaculityDailyAttendanceReportVO> faculityDailyAttendanceReportVOs = basFacultyService
				.showAttendusReport(reformattedStr);
		if (faculityDailyAttendanceReportVOs != null) {
			Collections.sort(faculityDailyAttendanceReportVOs);
		}
		System.out.println("I am showing Attendance from Past");
		return faculityDailyAttendanceReportVOs;
	}
	@RequestMapping(value = "/adminAttendanceTodayByDep", method = RequestMethod.GET)
	public @ResponseBody List<FaculityDailyAttendanceReportVO> TodayDepAttendus(@RequestParam(value = "depName") String department) {
		System.out.println("hello from dep");
	//	SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
	//	SimpleDateFormat formatJSP = new SimpleDateFormat("MM/dd/yyyy");
		String reformattedStr = null;
		String d2=DateUtils.getCurrentCalendarDate();
		System.out.println(d2);
	//	String d1="09/20/2013";
		reformattedStr = DateUtils.getCurrentCalendarDate();

		String AllCheck = "All";
		System.out.println(department);
		List<FaculityDailyAttendanceReportVO> faculityDailyAttendanceReportVOs = new ArrayList<FaculityDailyAttendanceReportVO>();
		if (department.equals(AllCheck)) {
			System.out.println("hello from if for all");
			faculityDailyAttendanceReportVOs = basFacultyService.showAttendusReport(reformattedStr);
			System.out.println("hello from if for all ends");
		} else {
			System.out.println("hello from else");
			faculityDailyAttendanceReportVOs = basFacultyService.showAttendusReportByDep(reformattedStr, department);
		}
		if (faculityDailyAttendanceReportVOs != null) {
			Collections.sort(faculityDailyAttendanceReportVOs);
		}
		System.out.println("hello from end");
		System.out.println(faculityDailyAttendanceReportVOs);
		return faculityDailyAttendanceReportVOs;
	}

	@RequestMapping(value = "/adminAttendanceByPastDateAndDep", method = RequestMethod.GET)
	public @ResponseBody List<FaculityDailyAttendanceReportVO> pastDateAndDepAttendus(
			@RequestParam(value = "pastDate") String pastDate, @RequestParam(value = "depName") String department) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatJSP = new SimpleDateFormat("MM/dd/yyyy");
		String reformattedStr = null;
		try {
			reformattedStr = myFormat.format(formatJSP.parse(pastDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String AllCheck = "All";
		System.out.println(department);
		List<FaculityDailyAttendanceReportVO> faculityDailyAttendanceReportVOs = new ArrayList<FaculityDailyAttendanceReportVO>();
		if (department.equals(AllCheck)) {
			faculityDailyAttendanceReportVOs = basFacultyService.showAttendusReport(reformattedStr);
		} else {
			faculityDailyAttendanceReportVOs = basFacultyService.showAttendusReportByDep(reformattedStr, department);
		}
		/*if (faculityDailyAttendanceReportVOs != null) {
			Collections.sort(faculityDailyAttendanceReportVOs);
		}*/
		
		Map<String,FaculityDailyAttendanceReportVO> employeeAttendanceRecordLinkedMap=new LinkedHashMap<String,FaculityDailyAttendanceReportVO>();
		for(FaculityDailyAttendanceReportVO facultyMonthlyAttendanceStatusVO:faculityDailyAttendanceReportVOs) {
			   if(employeeAttendanceRecordLinkedMap.containsKey(facultyMonthlyAttendanceStatusVO.getFid()+"")) {
				   FaculityDailyAttendanceReportVO mattendanceStatusVO =employeeAttendanceRecordLinkedMap.get(facultyMonthlyAttendanceStatusVO.getFid()+"");
				   mattendanceStatusVO.setOuttime(facultyMonthlyAttendanceStatusVO.getOuttime());
				   mattendanceStatusVO.setOuttimestatus(facultyMonthlyAttendanceStatusVO.getOuttimestatus());
			   }else{
				   employeeAttendanceRecordLinkedMap.put(facultyMonthlyAttendanceStatusVO.getFid()+"", facultyMonthlyAttendanceStatusVO);
			   }
	   }
		
		List<FaculityDailyAttendanceReportVO> modifiedAttendanceStatusVOsList=new ArrayList<FaculityDailyAttendanceReportVO>();
		Set<Map.Entry<String, FaculityDailyAttendanceReportVO>> entries=employeeAttendanceRecordLinkedMap.entrySet();
		for(Map.Entry<String, FaculityDailyAttendanceReportVO> fmasv:entries){
			modifiedAttendanceStatusVOsList.add(fmasv.getValue());
		}
		if (modifiedAttendanceStatusVOsList != null) {
			Collections.sort(modifiedAttendanceStatusVOsList,new InTimeDescComparator());
		}
		return modifiedAttendanceStatusVOsList;
	}

	@RequestMapping(value = "/adminAttendenceView.htm", method = RequestMethod.GET)
	public String adminAttendanceSubmitGet(HttpServletRequest request, Model model) {
		return NavigationConstant.ADMIN_PREFIX_PAGE + NavigationConstant.ADMIN_ATTENDANCE_PAGE;

	}

	/*
	 * PK
	 */
	@RequestMapping(value = "/adminAttendanceToday", method = RequestMethod.GET)
	public String pastDateAndDepAttendus(Model model, HttpServletRequest request) {
		System.out.println("Hello from COntroller");
		List<String> depList = basFacultyService.selectDepartments();
		depList.add(0, "All");
		DepartmentForm departmentForm = new DepartmentForm();
		String date = DateUtils.getCurrentCalendarDate();
		FaculityDailyAttendanceReportVO facultyAttendStatusVO = new FaculityDailyAttendanceReportVO();
		System.out.println(date);
		List<FaculityDailyAttendanceReportVO> faculityDailyAttendanceReportVOs = new ArrayList<FaculityDailyAttendanceReportVO>();
		faculityDailyAttendanceReportVOs = basFacultyService.showAttendenceReportForToday();
		System.out.println(faculityDailyAttendanceReportVOs);
		model.addAttribute("cdate", new Date());
		model.addAttribute("depList", depList);
		model.addAttribute("departmentForm", departmentForm);
		model.addAttribute("EmptyfacultyAttendStatusVO", facultyAttendStatusVO);
		model.addAttribute("todayAttObj", faculityDailyAttendanceReportVOs);
		if (faculityDailyAttendanceReportVOs != null) {
			Collections.sort(faculityDailyAttendanceReportVOs);
		}
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.TODAYS_ATTENDENCE_TITLE);
		return NavigationConstant.ADMIN_PREFIX_PAGE + NavigationConstant.ADMIN_ATTENDANCE_TODAY;
	}

	/*
	 *This code is for adminattendanceToday with PAGINATION
	 @RequestMapping(value = "/adminAttendanceToday", method = RequestMethod.GET)
	public String Pagination(@RequestParam(value = "page", required = false) String page, Model model) {

		List<String> depList = basFacultyService.selectDepartments();
		depList.add(0, "All");
		DepartmentForm departmentForm = new DepartmentForm();
		String date = DateUtils.getCurrentCalendarDate();
		FaculityDailyAttendanceReportVO facultyAttendStatusVO = new FaculityDailyAttendanceReportVO();
		System.out.println(date);
		System.out.println("Welcome from pagination controller");
		int recordsPerPage = 7;
		int currentPage = 0;
		if (page == null) {
			currentPage = 1;
		} else {
			currentPage = Integer.parseInt(page);
		}
		PaginationFaculityDailyAttendanceReportVO paginationFaculityDailyAttendanceReportVO = basFacultyService
				.TodaysAttendanceWithPagination((currentPage - 1) * recordsPerPage, recordsPerPage);
		System.out.println("Welcome end from pagination controller");
		paginationFaculityDailyAttendanceReportVO.setCurrentPage(currentPage);
		paginationFaculityDailyAttendanceReportVO.initPagination();
		model.addAttribute("cdate", new Date());
		model.addAttribute("depList", depList);
		model.addAttribute("departmentForm", departmentForm);
		model.addAttribute("EmptyfacultyAttendStatusVO", facultyAttendStatusVO);
		if (paginationFaculityDailyAttendanceReportVO != null) {
			Collections.sort(paginationFaculityDailyAttendanceReportVO.getFaculityDailyAttendanceReportVOList());
		}

		model.addAttribute("paginationFaculityDailyAttendanceReportVO", paginationFaculityDailyAttendanceReportVO);
		return NavigationConstant.ADMIN_PREFIX_PAGE + NavigationConstant.ADMINPAGINATION_ATTENDANCE_TODAY; // give
		// view
		// name
		// with
		// out
		// extension........../fruitSearch.jsp
	}*/

	@RequestMapping(value = "/showDeptMonthlyAttendance", method = RequestMethod.GET)
	public String showMonthlyAttendance(Model model) {
		EmployeeMonthAttendanceVO employeeMonthAttendance=new EmployeeMonthAttendanceVO();
		model.addAttribute("employeeMonthAttendance", employeeMonthAttendance);
		//List<String> depList = basFacultyService.selectDepartments();
		List<DepartmentForm> depList = departmentService.findDepartments();
		Map<String,String> departmentOptions=new LinkedHashMap<String,String>();
		departmentOptions.put("All", "All");
		for(DepartmentForm departmentForm:depList){
			departmentOptions.put(departmentForm.getDepartmentShortName(), departmentForm.getDepartmentName());
		}
		model.addAttribute("departmentList", departmentOptions);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.EMPLOYEE_MONTHLY_REGISTER_TITLE);
		return NavigationConstant.ADMIN_PREFIX_PAGE + NavigationConstant.DEPT_MONTHLY_ATTENDANCE_PAGE;
	}

	@RequestMapping(value = "/showDeptMonthlyAttendance", method = RequestMethod.POST)
	public String MonthlyAttendanceDetail(
			@ModelAttribute("employeeMonthAttendance") EmployeeMonthAttendanceVO employeeMonthAttendance,
			Model model) {
		String department = employeeMonthAttendance.getDepartment();
		String[] monthYear = employeeMonthAttendance.getMonth().split(" ");
		String month = monthYear[0];
		String year = monthYear[1];
		List<String> depList = basFacultyService.selectDepartments();
		model.addAttribute("departmentList", depList);
		// get result
		model.addAttribute("employeeMonthAttendance", employeeMonthAttendance);
		Map<EmployeeHeader, List<String>> deptEmpMonthlyLeaveStatus = basFacultyService
				.getDeptMonthlyAttendance(department, month, year);
		model.addAttribute("deptEmpMonthlyLeaveStatus", deptEmpMonthlyLeaveStatus);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.EMPLOYEE_MONTHLY_REGISTER_TITLE);
		return NavigationConstant.ADMIN_PREFIX_PAGE + NavigationConstant.DEPT_MONTHLY_ATTENDANCE_PAGE;
	}

	@RequestMapping(value = "/showDeptMonthlyAttendanceWithAjax", method = RequestMethod.GET)
	@ResponseBody public EmployeeMonthAttendanceVOWrapper deptMonthlyAttendanceWithAjax(@RequestParam("department") String department, @RequestParam("cdate") String cdate,
			Model model) {
		String[] monthYear = cdate.split(",");
		String month = monthYear[0];
		String year = monthYear[1];
		List<String> dayStatusList=null;
		EmployeeMonthAttendanceVO employeeMonthAttendanceVO=null;
		List<EmployeeMonthAttendanceVO> employeeMonthAttendanceVOList = new ArrayList<EmployeeMonthAttendanceVO>();
		Map<EmployeeHeader, List<String>> deptEmpMonthlyLeaveStatus = basFacultyService
				.getDeptMonthlyAttendance(department, month, year);
		Set<Map.Entry<EmployeeHeader, List<String>>> deptMonthlyAttendanceSet = deptEmpMonthlyLeaveStatus.entrySet();
		for(Map.Entry<EmployeeHeader, List<String>> entry: deptMonthlyAttendanceSet){
		    employeeMonthAttendanceVO = new EmployeeMonthAttendanceVO();
			employeeMonthAttendanceVO.setEid(entry.getKey().getEid());
			employeeMonthAttendanceVO.setName(entry.getKey().getName());
			employeeMonthAttendanceVO.setDesignation(entry.getKey().getDesignation());
			employeeMonthAttendanceVO.setDepartment(entry.getKey().getDepartment());
			employeeMonthAttendanceVO.setTotalDaysWorked(entry.getKey().getTotalDaysWorked());
			dayStatusList = entry.getValue();
			employeeMonthAttendanceVO.setDayStatusList(dayStatusList);
			employeeMonthAttendanceVOList.add(employeeMonthAttendanceVO);
		}
		
		EmployeeMonthAttendanceVOWrapper attendanceVOWrapper=new EmployeeMonthAttendanceVOWrapper();
		List<HolidayEntryEntity> entryList =adminBasReportService.getHolidayCategoryList(DateUtils.getCurrentMonth(month), year);		
		attendanceVOWrapper.setEmployeeMonthAttendanceVOs(employeeMonthAttendanceVOList);
		attendanceVOWrapper.setTotalDays(DateUtils.getNoOfDaysByMonthAndYear(month,year));
		attendanceVOWrapper.setTotalHolidays(entryList.size());
		return attendanceVOWrapper;
	}

	/*ff
	 * @RequestMapping(value = "/adminAttendanceToday", method =
	 * RequestMethod.GET) public String pastDateAndDepAttendence(Model model) {
	 * FaculityDailyAttendanceReportVO vo = new
	 * FaculityDailyAttendanceReportVO(); model.addAttribute("departmentForm",
	 * vo); return NavigationConstant.ADMIN_PREFIX_PAGE +
	 * NavigationConstant.ADMIN_ATTENDANCE_TODAY; }
	 */
	// End

	@RequestMapping(value = "/markLwpButtonClick", method = RequestMethod.GET)
	public @ResponseBody String markLwpForAnEmployee(@RequestParam("id") int id,
			@RequestParam("description") String comment, Model model) {
		String result = basFacultyService.markLwpService(id, comment);
		return result;
	}

	@RequestMapping(value = "/adminMarkLwp", method = RequestMethod.GET)
	public String markLwpForToday(Model model, HttpServletRequest request) {
		DepartmentForm departmentForm = new DepartmentForm();
		FaculityDailyAttendanceReportVO facultyAttendStatusVO = new FaculityDailyAttendanceReportVO();
		List<FaculityDailyAttendanceReportVO> faculityDailyAttendanceReportVOs = new ArrayList<FaculityDailyAttendanceReportVO>();
		faculityDailyAttendanceReportVOs = basFacultyService.findAllEmployeeForAttendance();
		model.addAttribute("departmentForm", departmentForm);
		model.addAttribute("EmptyfacultyAttendStatusVO", facultyAttendStatusVO);
		model.addAttribute("todayAttObj", faculityDailyAttendanceReportVOs);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.MARK_EMPLOYEE_LWP);
		return NavigationConstant.ADMIN_PREFIX_PAGE + NavigationConstant.MARK_LWP_EMPLOYEE_PAGE;
	}
	
	
	@ModelAttribute("depList")
	public Map<String,String> loadDepartmentList(){
		List<DepartmentForm> depList = departmentService.findDepartments();
		Map<String,String> departmentOptions=new LinkedHashMap<String,String>();
		departmentOptions.put("All", "All");
		for(DepartmentForm departmentForm:depList){
			departmentOptions.put(departmentForm.getDepartmentShortName(), departmentForm.getDepartmentName());
		}
		return departmentOptions;
	}
	@RequestMapping(value = "/employeeByDepartment", method = RequestMethod.GET)
	public String employeeOnLeave(Model model, HttpServletRequest request) {
		List<DepartmentForm> depList = departmentService.findDepartments();
		Map<String,String> departmentOptions=new LinkedHashMap<String,String>();
		for(DepartmentForm departmentForm:depList){
			departmentOptions.put(departmentForm.getDepartmentShortName(), departmentForm.getDepartmentName());
		}
		DepartmentForm departmentForm = new DepartmentForm();
		FaculityDailyAttendanceReportVO facultyAttendStatusVO = new FaculityDailyAttendanceReportVO();
		List<FaculityDailyAttendanceReportVO> faculityDailyAttendanceReportVOs = new ArrayList<FaculityDailyAttendanceReportVO>();
		faculityDailyAttendanceReportVOs = basFacultyService.findAllEmployeeForAttendance();
		model.addAttribute("departmentOptions", departmentOptions);
		model.addAttribute("departmentForm", departmentForm);
		model.addAttribute("EmptyfacultyAttendStatusVO", facultyAttendStatusVO);
		model.addAttribute("todayAttObj", faculityDailyAttendanceReportVOs);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.EMPLOYEES_LEAVE_BALANCE_TITLE);
		return NavigationConstant.ADMIN_PREFIX_PAGE + NavigationConstant.EMPLOYEE_ON_LEAVE_PAGE;
	}

	@RequestMapping(value = "/employeeListByDepartment", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody public List<FaculityDailyAttendanceReportVO> employeeListByDepartment(Model model, @RequestParam(value="departmentName",required=false) String department,@RequestParam(value="lwpDate",required=false) String lwpDate) {
		//converting List into Set
		//02/04/2016
		String lwpDateTokens[]=lwpDate.split("/");
		lwpDate=lwpDateTokens[2]+"-"+lwpDateTokens[0]+"-"+lwpDateTokens[1];
		List<String> employeeLwpIdList=basFacultyService.findEmpoyeeIdsForLwpByDate(lwpDate);
		Map<String,String> empidComments=new HashMap<String,String>();
		HashSet<String> employeeLwpIdSet=new HashSet<String>();
		for(String empcomment:employeeLwpIdList){
			String tokens[]=empcomment.split("-");
			if(tokens.length>1){
				empidComments.put(tokens[0], tokens[1]);
			}else{
				empidComments.put(tokens[0],"");
			}
			employeeLwpIdSet.add(tokens[0]);
		}
		 String employeeType="Teaching Staff";
		 List<FaculityDailyAttendanceReportVO> faculityDailyAttendanceReportVOList = basFacultyService.findAllEmployeeForByDepartment(department,employeeType);
		 for(FaculityDailyAttendanceReportVO attendanceReportVO:faculityDailyAttendanceReportVOList){
			     if(employeeLwpIdSet.contains(attendanceReportVO.getFid()+"")) {
			    	 //yes means that he is lwp on date
			    	 attendanceReportVO.setPresent("yes");
			    	 attendanceReportVO.setStatus(empidComments.get(attendanceReportVO.getFid()+""));
			    	 System.out.println("_#_)# = "+empidComments.get(attendanceReportVO.getFid()+""));
			     }
		 }
		 return faculityDailyAttendanceReportVOList;
	}

	@RequestMapping(value = "/employeeListByDepartmentAjax", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody public List<FaculityDailyAttendanceReportVO> employeeListByDepartmentAjax(Model model, @RequestParam(value="departmentName",required=false) String department,@RequestParam(value="employeeType",required=false) String employeeType){
		 List<FaculityDailyAttendanceReportVO> faculityDailyAttendanceReportVOList = basFacultyService.findAllEmployeeForByDepartment(department,employeeType);
		 return faculityDailyAttendanceReportVOList;
	}
	
	@RequestMapping(value = "/employeeManualAttendanceListByDepartmentAjax", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody public List<FaculityDailyAttendanceReportVO> employeeManualAttendanceListByDepartmentAjax(Model model, @RequestParam(value="departmentName",required=false) String department,@RequestParam(value="employeeType",required=false) String employeeType){
		 List<FaculityDailyAttendanceReportVO> faculityDailyAttendanceReportVOList = basFacultyService.findAllEmployeeForByDepartment(department,employeeType);
		 return faculityDailyAttendanceReportVOList;
	}
	


}
