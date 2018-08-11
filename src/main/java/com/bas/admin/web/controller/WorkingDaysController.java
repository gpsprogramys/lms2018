package com.bas.admin.web.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bas.admin.service.LeaveHistoryService;
import com.bas.admin.web.controller.form.DepartmentForm;
import com.bas.admin.web.controller.form.LeaveHistory;
import com.bas.common.constant.NavigationConstant;
import com.bas.common.constant.PageTitleContant;
import com.bas.common.util.DateUtils;
import com.bas.employee.service.BasFacultyService;
import com.bas.employee.utility.MonthUtility;

@Controller
public class WorkingDaysController {
		
	@Autowired
	@Qualifier("BasFacultyServiceImpl")
	private BasFacultyService basFacultyService;
	
	@Autowired
	@Qualifier("LeaveHistoryServiceImpl")
	private LeaveHistoryService leaveHistoryService;
	
	@RequestMapping(value="/empWorkingDays", method=RequestMethod.GET)
	public String showPage(Model model){	
		MonthUtility monthUtility = new MonthUtility();
		Calendar myCal = new GregorianCalendar(monthUtility.getYear(),monthUtility.getDate(),1);
		int totalDays = myCal.getActualMaximum(Calendar.DAY_OF_MONTH);
		List<String> depList = basFacultyService.selectDepartments();	
		depList.add(0, "All");
		
//		System.out.println(monthUtility.getYear() + " " + monthUtility.getDate());
		
		DepartmentForm departmentForm = new DepartmentForm();
		model.addAttribute("depListForEmp", depList);
			
		model.addAttribute("departmentFormObject", departmentForm);			
		
		//LocalDateTime now = LocalDateTime.now();
		int year =DateUtils.getCurrentYear();
		int month = DateUtils.getCurrentMonth();
		
		Calendar c = Calendar.getInstance();
		totalDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		List<LeaveHistory> leaveHistory = leaveHistoryService.getLeaveHistory(month, year,"All");
	     for (LeaveHistory var : leaveHistory) {
	            var.setDaysWorked(var.getDaysWorked()-var.getLeavesWithoutPay());
	     }
	     
	     model.addAttribute("holidays", (leaveHistory.isEmpty())?0:leaveHistory.get(0).getNoHolidays() ) ;
		model.addAttribute("leaveHistoryData", leaveHistory);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.NUMBER_OF_WORKING_DAYS);
		model.addAttribute("totalDays",totalDays);	
		
		return NavigationConstant.ADMIN_PREFIX_PAGE+NavigationConstant.WORKING_DAY_PAGE;
	}	
		
	@RequestMapping(value="/empWorkingDaysByAjax", method=RequestMethod.GET)
	public @ResponseBody List<LeaveHistory> leaveHistory(@RequestParam("month") String month, @RequestParam("year") String year, @RequestParam("department") String department){
		List<LeaveHistory> leaveHistory = leaveHistoryService.getLeaveHistory(Integer.parseInt(month)+1,Integer.parseInt(year), department);
	      for (LeaveHistory var : leaveHistory) {
	            var.setDaysWorked(var.getDaysWorked()-var.getLeavesWithoutPay());
	        }
		for (LeaveHistory lh : leaveHistory) {
			System.out.println(lh);
		}	
		return leaveHistory;
	}
	
	@RequestMapping(value="/getImageById", method=RequestMethod.GET)
	public void findPDPhotoById(@RequestParam("Id") String Id, HttpServletResponse r) throws IOException{
		
		r.setContentType("image/jpg");
				
		byte[] photo = leaveHistoryService.findPDPhotoByID(Id);
		
		System.out.println("====================================="+Id);
		
		if (photo != null) {
			ServletOutputStream outputStream = r.getOutputStream();
			outputStream.write(photo);
			outputStream.flush();
			outputStream.close();
		}
	}
}
