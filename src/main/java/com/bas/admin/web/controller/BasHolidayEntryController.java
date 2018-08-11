package com.bas.admin.web.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.bas.admin.service.HolidayEntryService;
import com.bas.admin.web.controller.form.HolidayEntryForm;
import com.bas.common.constant.NavigationConstant;
import com.bas.common.constant.PageTitleContant;
import com.bas.common.util.DateUtils;

/**
 * 
 * @author Amogh
 * 
 * GG
 * 
 */
@Controller
public class BasHolidayEntryController {
	@Autowired
	@Qualifier("HolidayEntryServiceImpl")
	private HolidayEntryService holidayEntryService;
	
	
	@RequestMapping(value="/viewHolidayCalender", method=RequestMethod.GET)
	public String addHolidayCal(Model model){
		showHolidayCalendar(model);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.COMPANY_HOLIDAY_TITLE);
		return NavigationConstant.ADMIN_PREFIX_PAGE+NavigationConstant.ADD_HOLIDAY_ENTRY_VIEW;
	}
	
	@RequestMapping(value="/viewHolidayCalenderEmployee", method=RequestMethod.GET)
	public String viewHolidayCalenderEmployee(Model model){
		showHolidayCalendar(model);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.COMPANY_HOLIDAY_TITLE);
		return NavigationConstant.EMPLOYEE_PREFIX_PAGE+NavigationConstant.ADD_HOLIDAY_ENTRY_VIEW;
	}

	private void showHolidayCalendar(Model model){
		int currentMonth=DateUtils.getCurrentMonth();
		List<HolidayEntryForm> calenders = holidayEntryService.getHolidayId(currentMonth);
		System.out.println(calenders);
		model.addAttribute("listOfHolidays",calenders);
		List<HolidayEntryForm> holidayEntryList=holidayEntryService.findHolidayEntry();
		//logic to set the color inside the pojo.....
		for(HolidayEntryForm holidayEntryForm:holidayEntryList) {
			  if(NavigationConstant.NATIONAL_HOLIDAY.equalsIgnoreCase(holidayEntryForm.getHolidayType())){
				  holidayEntryForm.setColor("holiday");
			  }else if(NavigationConstant.REGIONAL_HOLIDAY.equalsIgnoreCase(holidayEntryForm.getHolidayType())){
				  holidayEntryForm.setColor("regional");
			  } else if(NavigationConstant.NOT_A_PUBLIC_HOLIDAY.equalsIgnoreCase(holidayEntryForm.getHolidayType())){
				  holidayEntryForm.setColor("publicholiday");
			  }
		}
		model.addAttribute("holidayEntryList",holidayEntryList);
	}


@RequestMapping(value="/showImage", method=RequestMethod.GET)
	public void showImages(HttpServletResponse response, HttpServletRequest request) throws IOException{
		String cdate = request.getParameter("imageId");
		byte[] image = holidayEntryService.getHoildayImageByDate(cdate);
		response.setContentType("text/jpg");
		if(image!=null){
			ServletOutputStream sos = response.getOutputStream();
			sos.write(image);
			sos.flush();
		}
	}
	@RequestMapping(value="/addHolidayCalendar", method=RequestMethod.GET)
	public String viewHolidayCal(Model  model){
		HolidayEntryForm holidayEntryForm = new HolidayEntryForm();
		model.addAttribute("holidayEntryForm", holidayEntryForm);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.HOLIDAY_CALENDAR_TITLE);
		return NavigationConstant.ADMIN_PREFIX_PAGE+NavigationConstant.ADD_HOLIDAY_CALENDAR;
	}

	
	@RequestMapping(value = "/addHoliday"  , method = RequestMethod.POST)
	public String findHolidayEntry(Model model) {
		HolidayEntryForm holidayEntryForm = new HolidayEntryForm();
		model.addAttribute("holidayEntryForm", holidayEntryForm);
		return NavigationConstant.ADMIN_PREFIX_PAGE
				+ NavigationConstant.ADD_HOLIDAY_ENTRY;
	}

	
	@RequestMapping(value = "/holidayEntry", method = RequestMethod.POST)
	public String AddDepartment(@ModelAttribute(value="holidayEntryForm")  HolidayEntryForm holiday ,Model model) {
	System.out.println("@@))#)#)@(@(((((((((((((((((((((((((((((((((((((((((((((((**********************");	
	Date  cdate=DateUtils.getCurrentDateInSQLFormatFromCalendar(holiday.getHolidayDate());
	holiday.setHolidayDate(cdate);
	String message = holidayEntryService.addHolidayEntry(holiday);
	HolidayEntryForm holidayEntryForm = new HolidayEntryForm();
	model.addAttribute("holidayEntryForm", holidayEntryForm);
	model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.HOLIDAY_CALENDAR_TITLE);
	model.addAttribute("ApplicationMessage", "Holiday calendar entry has been added successfully!");
	return NavigationConstant.ADMIN_PREFIX_PAGE+NavigationConstant.ADD_HOLIDAY_CALENDAR;
}
	
	 
	
	
	
	
	
	

//	@RequestMapping(value = "/holidayEntry", method = RequestMethod.GET)
//	public String setHolidayEntry(Model model) {
//		HolidayEntryForm holidayEntryForm = new HolidayEntryForm();
//		model.addAttribute("holidayEntryForm", holidayEntryForm);
//		model.addAttribute("buttonLabel", "Add Holiday Entry");
//		String str = findHolidayEntry(model);
//		return str;
//
//	}

	@RequestMapping(value = "/holidayEntry1111", method = RequestMethod.POST)
	public String submitHolidayEntry(
			@ModelAttribute("holidayEntryForm") HolidayEntryForm holidayEntryForm,
			@RequestParam(value ="buttonAction", required = false) String buttonAction,
			Model model) {
		if (buttonAction != null && buttonAction.equals("Update Entry")) {
			holidayEntryService.editHolidayEntry(holidayEntryForm);
		} else {
			try{
			holidayEntryService.addHolidayEntry(holidayEntryForm);
			}
			catch(DuplicateKeyException e)
			{
			model.addAttribute("alertMessage", "Entry for This Date Already Exists");	
			}
		}
		HolidayEntryForm holidayEntryForm2 = new HolidayEntryForm();
		model.addAttribute("holidayEntryForm", holidayEntryForm2);
		model.addAttribute("buttonLabel", "Add Holiday Entry");
		String str = findHolidayEntry(model);
		return str;
	}

//	@RequestMapping(value = "/deleteHolidayEntry.htm", method = RequestMethod.POST)
//	public String deleteHolidayEntry(HttpServletRequest request, Model model) {
//		String date = request.getParameter("holidayDate");
//		holidayEntryService.deleteHolidayEntry(date);
//		setHolidayEntry(model);
//		String str = findHolidayEntry(model);
//		return str;
//	}

	@RequestMapping(value = "/editHolidayEntry.htm", method = RequestMethod.GET)
	public String editHolidayEntry(HttpServletRequest request, Model model) {
		String date = request.getParameter("holidayDate");
		HolidayEntryForm holidayEntryForm = holidayEntryService
				.findHolidayEntryByDate(date);
		model.addAttribute("holidayEntryForm", holidayEntryForm);
		List<HolidayEntryForm> holidayEntryForms = holidayEntryService
				.findHolidayEntry();
		model.addAttribute("buttonLabel","Update Entry");
		model.addAttribute("holidayEntryForms", holidayEntryForms);
		return NavigationConstant.ADMIN_PREFIX_PAGE
				+ NavigationConstant.ADD_HOLIDAY_ENTRY;

	}
	
     //Read
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// to actually be able to convert Multipart instance to byte[]
		// we have to register a custom editor
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
		// now Spring knows how to handle multipart object and convert them
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        //Create a new CustomDateEditor
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        //Register it as custom editor for the Date type
		binder.registerCustomEditor(Date.class, editor);
	}
	
	
	
	
}
