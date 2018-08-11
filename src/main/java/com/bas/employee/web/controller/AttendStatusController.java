package com.bas.employee.web.controller;

import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.bas.admin.service.BasSchedulerService;
import com.bas.admin.web.controller.form.FaculityDailyAttendanceReportVO;
import com.bas.admin.web.controller.form.FacultyManualAttendance;
import com.bas.admin.web.controller.form.FacultyMonthlyAttendanceStatusVO;
import com.bas.admin.web.controller.form.HolidayEntryForm;
import com.bas.common.constant.BaoConstants;
import com.bas.common.constant.NavigationConstant;
import com.bas.common.constant.PageTitleContant;
import com.bas.common.dao.entity.EmployeeHeader;
import com.bas.common.util.DateUtils;
import com.bas.common.web.controller.form.EmployeeLeaveDetailVO;
import com.bas.employee.service.BasFacultyService;
import com.bas.employee.utility.MonthUtility;
import com.bas.employee.web.controller.form.FaculityLeaveMasterVO;
import com.bas.employee.web.controller.form.LoginForm;
import com.bas.employee.web.controller.form.ManualAttendanceVO;
import com.bas.employee.web.controller.form.MonthAttendanceSummaryVO;
import com.bas.employee.web.controller.form.SubjectAlternativeArrangementsVO;
import com.synergy.bank.employee.service.IEmployeeLMSService;

/**
 * @author nagendra
 *
 * This is controller which will give
 * you attendance status for the faculty.
 *
 */
@Controller
public class AttendStatusController {

	@Autowired
	@Qualifier("BasFacultyServiceImpl")
	private BasFacultyService basFacultyService;

	@Autowired
	@Qualifier("BasSchedulerServiceImpl")
	private BasSchedulerService basSchedulerService;

	@Autowired
	@Qualifier("EmployeeLMSService")
	private IEmployeeLMSService iEmployeeService;


	@RequestMapping(value="/computeNoOfDaysForSalaryInAMonth", method=RequestMethod.GET)
	public String computeNoOfDaysForSalaryInAMonth(){
		try {
			basSchedulerService.computeNoOfDaysForSalaryInAMonth();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return NavigationConstant.EMPLOYEE_PREFIX_PAGE
				+ NavigationConstant.VIEW_ATTENDANCE;
	}

	@RequestMapping(value = "/employeeAttendance", method = RequestMethod.GET)
	public String currentAttendus(@RequestParam(value="month",required=false) String pmonth, @RequestParam(value="year",required=false) String pyear,@RequestParam(value="eid",required=false) String eid, Model model, HttpSession session) throws ParseException {
		int currYear=0;
		int month=0;
		if(pmonth!=null && pyear!=null) {
				  String temp =pmonth.substring(0, 3);
			  	  String m = DateUtils.getMonthIndexByName(temp);
				 month = Integer.parseInt(m);
				 currYear=Integer.parseInt(pyear);
		}else { 
				currYear = DateUtils.getCurrentYear();
				month = new Date().getMonth()+1;
		}
		
		int noOfWorkingDays = DateUtils.getNoOfDaysForCurrentMonth();
		Calendar c = Calendar.getInstance();
		String pid="";
		if(eid!=null && eid.length()>0) {
			pid=eid;
			//getCurrentSessionYear 2015-2016
			String currentSessionYear=DateUtils.getCurrentSessionYear();
			String previousSessionYear=DateUtils.getPreviousSessionYear();
			
			model.addAttribute("currentSessionYear",currentSessionYear);
			model.addAttribute("previousSessionYear",previousSessionYear);
			EmployeeLeaveDetailVO employeeLeaveDetailVO = iEmployeeService.findEmployeeLeaveDetail(eid);
			model.addAttribute("employeeLeaveDetailVO",employeeLeaveDetailVO);
			model.addAttribute("eidNameDepartment",employeeLeaveDetailVO.getEid()+"-"+employeeLeaveDetailVO.getName()+"-"+employeeLeaveDetailVO.getDepartment());

		}else{
			LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
			pid=loginForm.getEid();
		}
	    List<FaculityDailyAttendanceReportVO> facultyAttendStatusVO = basFacultyService.getAttStatusByDate(currYear+"-"+month+"-01",currYear+"-"+month+"-"+noOfWorkingDays,pid);
		int noOfDaysWorked = facultyAttendStatusVO.size();
		List<HolidayEntryForm> holidays = basFacultyService.getHolidays(month,currYear);
		List<FacultyMonthlyAttendanceStatusVO> n = new ArrayList<FacultyMonthlyAttendanceStatusVO>();

		//List<FaculityLeaveMasterVO> leave_history = basFacultyService.LeaveHistory(currYear+"-"+month+"-01",currYear+"-"+month+"-"+noOfWorkingDays,loginForm.getEid());
		List<FaculityLeaveMasterVO> leave_history = basFacultyService.sortLeaveByDate(currYear+"-"+month+"-01",currYear+"-"+month+"-"+noOfWorkingDays,pid);
		for(FaculityLeaveMasterVO lh : leave_history){
			for(int i=0; i< lh.getTotalDays(); i++){
				FacultyMonthlyAttendanceStatusVO lst = new FacultyMonthlyAttendanceStatusVO();
				String myDate = null;
				String date = null;
				myDate = DateUtils.getCurrentCalendarDate(lh.getLeaveFrom());
				date = DateUtils.nextDate(myDate,i);
				int m = DateUtils.twoDateDifference(currYear+"-"+month+"-"+noOfWorkingDays,date);
				if(m < 0) break;
				lst.setCdate(date);
				lst.setIntime("----------");
				lst.setOuttime("APPROVED HOLIDAY");
				lst.setIntimestatus("---------");
				lst.setOuttimestatus("----------");
				lst.setPresent(lh.getLeaveType());
	            if(lh.getLeaveType().equals("LWP"))
	                lst.setColor(NavigationConstant.LEAVE_COLOR);
				n.add(lst);
			}
		}
		for(FaculityDailyAttendanceReportVO f : facultyAttendStatusVO){
				FacultyMonthlyAttendanceStatusVO lst = new FacultyMonthlyAttendanceStatusVO();
				lst.setCdate(f.getCdate());
				lst.setIntime(f.getIntime());
				lst.setOuttime(f.getOuttime());
				lst.setIntimestatus(f.getIntimestatus());
				lst.setOuttimestatus(f.getOuttimestatus());
				lst.setDescription("");
				lst.setPresent(f.getPresent());
				if(f.getIntimestatus()!=null && f.getIntimestatus().equals(NavigationConstant.LATE_IN_STATUS) && f.getOuttimestatus()!=null && f.getOuttimestatus().equals(NavigationConstant.EARLY_OUT_STATUS))
					lst.setColor("both");
				if(f.getIntimestatus().equals(NavigationConstant.LATE_IN_STATUS))
					lst.setColor(NavigationConstant.LATE_IN_COLOR);
				else{
					if((f.getOuttimestatus()!=null && f.getOuttimestatus().equals(NavigationConstant.EARLY_OUT_STATUS)))
					  lst.setColor(NavigationConstant.EARLY_OUT_COLOR);
					else
					  lst.setColor(NavigationConstant.NORMAL_COLOR);
				}
				n.add(lst);
		}
		for(HolidayEntryForm h : holidays){
			FacultyMonthlyAttendanceStatusVO lst2 = new FacultyMonthlyAttendanceStatusVO();
			String[] d = h.getHolidayDate().toString().split(" ");
			String dte = d[0];
			lst2.setCdate(dte);
			lst2.setHolidayDate(h.getHolidayDate());
			lst2.setIntime("----------");
			lst2.setOuttime(h.getDescription());
			lst2.setIntimestatus("---------");
			lst2.setOuttimestatus("----------");
			lst2.setDescription(h.getDescription());
			lst2.setPresent(h.getHolidayType());
			lst2.setColor(NavigationConstant.HOLIDAY_COLOR);
			n.add(lst2);
		}

		Collections.sort(n);
		Map<String,FacultyMonthlyAttendanceStatusVO> employeeAttendanceRecordLinkedMap=new LinkedHashMap<String,FacultyMonthlyAttendanceStatusVO>();
		for(FacultyMonthlyAttendanceStatusVO facultyMonthlyAttendanceStatusVO:n) {
			   if(employeeAttendanceRecordLinkedMap.containsKey(facultyMonthlyAttendanceStatusVO.getCdate())) {
				   FacultyMonthlyAttendanceStatusVO mattendanceStatusVO =employeeAttendanceRecordLinkedMap.get(facultyMonthlyAttendanceStatusVO.getCdate());
				   mattendanceStatusVO.setOuttime(facultyMonthlyAttendanceStatusVO.getOuttime());
				   mattendanceStatusVO.setOuttimestatus(facultyMonthlyAttendanceStatusVO.getOuttimestatus());
			   }else{
				   employeeAttendanceRecordLinkedMap.put(facultyMonthlyAttendanceStatusVO.getCdate(), facultyMonthlyAttendanceStatusVO);
			   }
	   }
		
		List<FacultyMonthlyAttendanceStatusVO> modifiedAttendanceStatusVOsList=new ArrayList<FacultyMonthlyAttendanceStatusVO>();
		Set<Map.Entry<String, FacultyMonthlyAttendanceStatusVO>> entries=employeeAttendanceRecordLinkedMap.entrySet();
		for(Map.Entry<String, FacultyMonthlyAttendanceStatusVO> fmasv:entries){
			modifiedAttendanceStatusVOsList.add(fmasv.getValue());
		}
		ManualAttendanceVO empDetails = basFacultyService.findEmployeeDataForAttendance(pid);
		model.addAttribute("noOfWorkingDays", noOfWorkingDays);
		model.addAttribute("noOfDaysWorked", noOfDaysWorked);
		model.addAttribute("empDetails", empDetails);
		model.addAttribute("EmptyfacultyAttendStatusVO", facultyAttendStatusVO);
		model.addAttribute("faculityDailyAttendanceReportVOs", modifiedAttendanceStatusVOsList);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.EMPLOYEE_ATTENDANCE_TITLE);
		return NavigationConstant.EMPLOYEE_PREFIX_PAGE
				+ NavigationConstant.VIEW_ATTENDANCE;
	}

	@RequestMapping(value = "/viewAttendenceByDate", method = RequestMethod.GET)
	@ResponseBody public List<FacultyMonthlyAttendanceStatusVO> viewAttendanceByDate(@RequestParam("month") String month, @RequestParam("year") String year,HttpSession session){
		int noOfWorkingDays = DateUtils.getNoOfDaysForCurrentMonth();
		String temp = month.substring(0, 3);
		String m = DateUtils.getMonthIndexByName(temp);
		int mnth = Integer.parseInt(m);
		int yr=Integer.parseInt(year);
	    LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		List<FaculityDailyAttendanceReportVO> facultyAttendStatusVO = basFacultyService.getAttStatusByDate(year+"-"+mnth+"-01",year+"-"+mnth+"-"+noOfWorkingDays,loginForm.getEid());
		List<HolidayEntryForm> holidays = basFacultyService.getHolidays(mnth,yr);
		List<FaculityLeaveMasterVO> leave_history = basFacultyService.sortLeaveByDate(year+"-"+mnth+"-01",year+"-"+mnth+"-"+noOfWorkingDays,loginForm.getEid());
		List<FacultyMonthlyAttendanceStatusVO> n = new ArrayList<FacultyMonthlyAttendanceStatusVO>();

		for(FaculityLeaveMasterVO lh : leave_history){
		  for(int i=0; i< lh.getTotalDays(); i++){
			FacultyMonthlyAttendanceStatusVO lst = new FacultyMonthlyAttendanceStatusVO();
			String myDate = null;
			String date = null;
			myDate = DateUtils.getCurrentCalendarDate(lh.getLeaveFrom());
			date = DateUtils.nextDate(myDate,i);
			int monthDifference = DateUtils.twoDateDifference(year+"-"+Integer.parseInt(DateUtils.getMonthIndexByName(month.substring(0,3)))+"-"+noOfWorkingDays,date);
			if(monthDifference < 0) break;
			lst.setCdate(date);
			lst.setIntime("----------");
			lst.setOuttime("APPROVED HOLIDAY");
			lst.setIntimestatus("---------");
			lst.setOuttimestatus("----------");
			lst.setPresent(lh.getLeaveType());
            if(lh.getLeaveType().equals("LWP"))
                lst.setColor(NavigationConstant.LEAVE_COLOR);

			n.add(lst);
		}
	}

		for(FaculityDailyAttendanceReportVO f : facultyAttendStatusVO){
				FacultyMonthlyAttendanceStatusVO lst = new FacultyMonthlyAttendanceStatusVO();
				lst.setCdate(f.getCdate());
				lst.setIntime(f.getIntime());
				lst.setOuttime(f.getOuttime());
				lst.setIntimestatus(f.getIntimestatus());
				lst.setOuttimestatus(f.getOuttimestatus());
				lst.setDescription("");
				lst.setPresent(f.getPresent());
				if(f.getIntimestatus().equals(NavigationConstant.LATE_IN_STATUS) && f.getOuttimestatus().equals(NavigationConstant.EARLY_OUT_STATUS))
					lst.setColor("both");
				if(f.getIntimestatus().equals(NavigationConstant.LATE_IN_STATUS))
					lst.setColor(NavigationConstant.LATE_IN_COLOR);
				else{
					if((f.getOuttimestatus().equals(NavigationConstant.EARLY_OUT_STATUS)))
					  lst.setColor(NavigationConstant.EARLY_OUT_COLOR);
					else
					  lst.setColor(NavigationConstant.NORMAL_COLOR);
				}
				n.add(lst);
		}
		for(HolidayEntryForm h : holidays){
			FacultyMonthlyAttendanceStatusVO lst2 = new FacultyMonthlyAttendanceStatusVO();
			String[] d = h.getHolidayDate().toString().split(" ");
			String dte = d[0];
			lst2.setCdate(dte);
			lst2.setHolidayDate(h.getHolidayDate());
			lst2.setIntime("----------");
			lst2.setOuttime(h.getDescription());
			lst2.setIntimestatus("---------");
			lst2.setOuttimestatus("----------");
			lst2.setDescription(h.getDescription());
			lst2.setPresent(h.getHolidayType());
			lst2.setColor(NavigationConstant.HOLIDAY_COLOR);
			n.add(lst2);
		}
		
		Collections.sort(n);

		Map<String,FacultyMonthlyAttendanceStatusVO> employeeAttendanceRecordLinkedMap=new LinkedHashMap<String,FacultyMonthlyAttendanceStatusVO>();
		for(FacultyMonthlyAttendanceStatusVO facultyMonthlyAttendanceStatusVO:n) {
			   if(employeeAttendanceRecordLinkedMap.containsKey(facultyMonthlyAttendanceStatusVO.getCdate())) {
				   FacultyMonthlyAttendanceStatusVO mattendanceStatusVO =employeeAttendanceRecordLinkedMap.get(facultyMonthlyAttendanceStatusVO.getCdate());
				   mattendanceStatusVO.setOuttime(facultyMonthlyAttendanceStatusVO.getOuttime());
				   mattendanceStatusVO.setOuttimestatus(facultyMonthlyAttendanceStatusVO.getOuttimestatus());
			   }else{
				   employeeAttendanceRecordLinkedMap.put(facultyMonthlyAttendanceStatusVO.getCdate(), facultyMonthlyAttendanceStatusVO);
			   }
	   }
		
		List<FacultyMonthlyAttendanceStatusVO> modifiedAttendanceStatusVOsList=new ArrayList<FacultyMonthlyAttendanceStatusVO>();
		Set<Map.Entry<String, FacultyMonthlyAttendanceStatusVO>> entries=employeeAttendanceRecordLinkedMap.entrySet();
		for(Map.Entry<String, FacultyMonthlyAttendanceStatusVO> fmasv:entries){
			modifiedAttendanceStatusVOsList.add(fmasv.getValue());
		}
		return modifiedAttendanceStatusVOsList;
	}


	@RequestMapping(value="/attendanceStatus",method=RequestMethod.GET)
	public String showAttendStatus( Model model,HttpSession session)	{
		MonthUtility monthUtility = new MonthUtility();
		monthUtility.setMonth(monthUtility.getDate());
		model.addAttribute("monthUtility", monthUtility);
		model.addAttribute("monthList", monthUtility.getMonthList(monthUtility.getDate()));
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		List<FaculityDailyAttendanceReportVO> facultyAttendStatusVOlist=basFacultyService.findAttendStatus(loginForm.getEid(),monthUtility.getDate());
		model.addAttribute("facultyAttendStatusVOlist", facultyAttendStatusVOlist);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.ATTENDANCE_STATUS_TITLE);
		return NavigationConstant.EMPLOYEE_PREFIX_PAGE+NavigationConstant.ATTEND_STATUS_PAGE;
	}

	@RequestMapping(value="/retreiveEmployeeInfo",method=RequestMethod.POST)
	public @ResponseBody List<FaculityDailyAttendanceReportVO> showAttendStatus(@RequestParam(value="dateInfo") String monthVal,HttpSession session) {
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		List<FaculityDailyAttendanceReportVO> facultyAttendStatusVOlist=basFacultyService.findAttendStatus(loginForm.getEid(),Integer.parseInt(monthVal));
	/*	System.out.println(facultyAttendStatusVOlist.get(0));*/
		return facultyAttendStatusVOlist;
	}


	@RequestMapping(value="/employeeMonthlyAttendanceStatus",method=RequestMethod.GET)
	public String showMonthlyAttendanceStatus( Model model, @RequestParam(value="eid",required=false) String eid, HttpSession session)	{
		if(eid==null) {
			LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
			eid=loginForm.getEid();
		}
		EmployeeLeaveDetailVO employeeLeaveDetailVO = iEmployeeService.findEmployeeLeaveDetail(eid);
		model.addAttribute("employeeLeaveDetailVO",employeeLeaveDetailVO);

		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.EMPLOYEE_MONTHLY_REGISTER_TITLE);
		return NavigationConstant.EMPLOYEE_PREFIX_PAGE+NavigationConstant.EMP_MONTHLY_ATTENDANCE_STATUS;
	}
	
	@RequestMapping(value="/employeeMonthlyAttStatusWithChart",method=RequestMethod.GET)
	public String employeeMonthlyAttStatusWithChart( Model model, @RequestParam(value="eid",required=false) String eid, HttpSession session)	{
		if(eid==null) {
			LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
			eid=loginForm.getEid();
		}
		EmployeeLeaveDetailVO employeeLeaveDetailVO = iEmployeeService.findEmployeeLeaveDetail(eid);
		model.addAttribute("employeeLeaveDetailVO",employeeLeaveDetailVO);

		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.EMPLOYEE_MONTHLY_REGISTER_TITLE);
		return NavigationConstant.EMPLOYEE_PREFIX_PAGE+NavigationConstant.EMPLOYEE_MONTHLY_ATT_STATUS_WITH_CHART;
	}
	
	@RequestMapping(value="/employeeAttendanceByDate",method=RequestMethod.GET)
	public @ResponseBody FacultyManualAttendance employeeAttendanceByDate( Model model, @RequestParam(value="cdate",required=false) String cdate, HttpSession session)	{
			LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
			String eid=loginForm.getEid();
			FacultyManualAttendance facultyManualAttendance = basFacultyService.findEmployeeAttendanceByDate(eid,cdate);
			return facultyManualAttendance;
	}
	
	@RequestMapping(value="/employeeManualAttendance",method=RequestMethod.GET)
	public String employeeManualAttendance( Model model, @RequestParam(value="eid",required=false) String eid, HttpSession session)	{
		if(eid==null) {
			LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
			eid=loginForm.getEid();
		}
		EmployeeLeaveDetailVO employeeLeaveDetailVO = iEmployeeService.findEmployeeLeaveDetail(eid);
		model.addAttribute("employeeLeaveDetailVO",employeeLeaveDetailVO);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.EMPLOYEE_MONTHLY_REGISTER_TITLE);
		return NavigationConstant.EMPLOYEE_PREFIX_PAGE+NavigationConstant.EMPLOYEE_MANUAL_ATTENDANCE;
	}
	
	@RequestMapping(value="/employeeManualAttendance",method=RequestMethod.POST)
	public String employeeManualAttendancePost( Model model,String cdate,String detail,String intime,String outtime, HttpSession session)	{
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		String eid=loginForm.getEid();
		String csystemdate=DateUtils.getCurrentCalendarDate();
		if(cdate.contains("/")){
			String stringTokens[]=cdate.split("/");
			cdate=stringTokens[2]+"-"+stringTokens[0]+"-"+stringTokens[1];
		}
		Date cdateFormat=DateUtils.getDateIntoCalendarFormat(cdate);
		Date currentDate = new Date();
		if(cdateFormat.compareTo(currentDate)>0){
			model.addAttribute("ApplicationMessage", "Sorry , Your cannot  apply attendance for futute date "+cdate+" , inntime = "+intime+" and outtime = "+outtime);
			EmployeeLeaveDetailVO employeeLeaveDetailVO = iEmployeeService.findEmployeeLeaveDetail(eid);
			model.addAttribute("cdate",cdate);
			model.addAttribute("detail",detail);
			model.addAttribute("intime",intime);
			model.addAttribute("outtime",outtime);
			model.addAttribute("employeeLeaveDetailVO",employeeLeaveDetailVO);
			model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.EMPLOYEE_MONTHLY_REGISTER_TITLE);
			return NavigationConstant.EMPLOYEE_PREFIX_PAGE+NavigationConstant.EMPLOYEE_MANUAL_ATTENDANCE;
		}
		Time dintime=null;
		Time outintime=null;
		if(intime.toLowerCase().contains("am") || intime.toLowerCase().contains("pm")){
			dintime=DateUtils.getCurrentTime(intime);
		}else{
			dintime=DateUtils.getCurrentTimeNotAMPM(intime);
		}
		if(outtime.toLowerCase().contains("am") || outtime.toLowerCase().contains("pm")){
			 outintime=DateUtils.getCurrentTime(outtime);
		}else{
			outintime=DateUtils.getCurrentTimeNotAMPM(outtime);
		}
		
		String result=basFacultyService.employeeManualAttendancePost( eid,cdate, detail, dintime, outintime);
		if(BaoConstants.RECORD_ALREADY_EXIST.equals(result)) {
			model.addAttribute("ApplicationMessage", "Sorry , Your attendance is already marks for date "+cdate+" , inntime = "+intime+" and outtime = "+outintime);
		}else if(BaoConstants.RECORD_UPDATED.equals(result)) {
			model.addAttribute("ApplicationMessage", "Your attendance has been applied to update  of date "+cdate+" to the adminstrator "+" with inntime = "+intime+" and outtime = "+outintime);
		}	
		else{
			model.addAttribute("ApplicationMessage", "Your attendance has been applied successfully for date "+cdate+" to the adminstrator");
		}
		EmployeeLeaveDetailVO employeeLeaveDetailVO = iEmployeeService.findEmployeeLeaveDetail(eid);
		model.addAttribute("employeeLeaveDetailVO",employeeLeaveDetailVO);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.EMPLOYEE_MONTHLY_REGISTER_TITLE);
		return NavigationConstant.EMPLOYEE_PREFIX_PAGE+NavigationConstant.EMPLOYEE_MANUAL_ATTENDANCE;
	}


	@RequestMapping(value = "/employeeMonthlyAttendanceStatus", method = RequestMethod.POST)
	public String MonthlyAttendanceStatus( @ModelAttribute("employeeLeaveDetailVO") EmployeeLeaveDetailVO employeeLeaveDetailVO,Model model,HttpSession session) {
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		String eid=loginForm.getEid();
//		String monthYear[] = employeeLeaveDetailVO.getMonth().split("");
		String[] monthYear = employeeLeaveDetailVO.getMonth().split(" ");
		String selectedMonth = monthYear[0];
		String selectedYear = monthYear[1];
		System.out.println("-------------------------> selectedMonth : "+ selectedMonth);
		System.out.println("-------------------------> selectedYear : "+ selectedYear);
	    Map<EmployeeHeader, List<String>> empMonthlyAttendanceStatus= basFacultyService.getEmployeeMonthlyAttendanceStatus(eid, selectedMonth, selectedYear);
	    System.out.println(empMonthlyAttendanceStatus);
	    model.addAttribute("empMonthlyAttendanceStatus", empMonthlyAttendanceStatus);
	    employeeLeaveDetailVO = iEmployeeService.findEmployeeLeaveDetail(eid);
		model.addAttribute("employeeLeaveDetailVO",employeeLeaveDetailVO);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.EMPLOYEE_MONTHLY_REGISTER_TITLE);
		return NavigationConstant.EMPLOYEE_PREFIX_PAGE+NavigationConstant.EMP_MONTHLY_ATTENDANCE_STATUS ;
	}

	@RequestMapping(value = "/employeeMonthlyAttendanceStatusWithAjax", method = RequestMethod.GET)
	@ResponseBody public MonthAttendanceSummaryVO employeeMonthlyAttendanceStatusWithAjax( @RequestParam("cdate") String pmonthYear, HttpSession session) {
		List<String> leaveDatesList=null;
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		String eid=loginForm.getEid();
		String[] monthYear = pmonthYear.split(",");
		String selectedMonth = monthYear[0];
		String selectedYear = monthYear[1];
	    Map<EmployeeHeader, List<String>> empMonthlyAttendanceStatus= basFacultyService.getEmployeeMonthlyAttendanceStatus(eid, selectedMonth, selectedYear);
	    int noOfDaysInMonth=DateUtils.getNoOfDaysByMonthAndYear(selectedMonth, selectedYear);
	    Set<Map.Entry<EmployeeHeader,List<String>>> empMonthlyAttendanceStatusSet = empMonthlyAttendanceStatus.entrySet();
	    EmployeeHeader employeeHeader=null;
	    for(Map.Entry<EmployeeHeader,List<String>> t:empMonthlyAttendanceStatusSet){
	    	leaveDatesList=t.getValue();
	    	employeeHeader=t.getKey();
	    }
	    MonthAttendanceSummaryVO attendanceSummaryVO=new MonthAttendanceSummaryVO();
	    attendanceSummaryVO.setTotalNoOfDays(noOfDaysInMonth);
	    attendanceSummaryVO.setLeaveDatesList(leaveDatesList);
	    if(employeeHeader!=null){
	    	attendanceSummaryVO.setTotalDaysWorked(employeeHeader.getTotalDaysWorked());
	    	attendanceSummaryVO.setEid(employeeHeader.getEid());
	    	attendanceSummaryVO.setName(employeeHeader.getName());
	    	attendanceSummaryVO.setDepartment(employeeHeader.getDepartment());
	    	attendanceSummaryVO.setDesignation(employeeHeader.getDesignation());
	    	String month = DateUtils.getCurrentMonth(selectedMonth);
	    	attendanceSummaryVO.setSelectedDateMonth(month+"-"+selectedYear);
	    }
	    return attendanceSummaryVO;
	}
	
	@RequestMapping(value="/employeeAlternateClassStatus",method=RequestMethod.GET)
	public String employeeAlternateClassStatus(Model model)	{
		List<SubjectAlternativeArrangementsVO> alternativeArrangementsVOsList=basFacultyService.findAllemployeeAlternateClassStatus();
		model.addAttribute("alternativeArrangementsVOsList", alternativeArrangementsVOsList);
		return NavigationConstant.LMS_PREFIX_PAGE+ NavigationConstant.MANAGER_APPROVE_LEAVE;
	}

}
