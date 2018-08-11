package com.bas.employee.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bas.common.constant.NavigationConstant;
import com.bas.employee.service.BasFacultyTimeService;
import com.bas.employee.web.controller.form.FaculityTimeForm;

@Controller
@Scope("request")
public class FacultyTimeController {
	
	@Autowired
	@Qualifier("BasFacultyTimeServiceImpl")
	private BasFacultyTimeService basFacultyTimeService;
	
	@ModelAttribute("dayNames")
	public List<String> dayNames() {
		List<String> dayNameList = new ArrayList<String>();
		dayNameList.add("MONDAY");
		dayNameList.add("TUESDAY");
		dayNameList.add("WEDNESDAY");
		dayNameList.add("THURSDAY");
		dayNameList.add("FRIDAY");
		return dayNameList;
	}
	
	@ModelAttribute("periods")
	public List<String> periodNames() {
		List<String> periodList = new ArrayList<String>();
		periodList.add("1");
		periodList.add("2");
		periodList.add("3");
		periodList.add("4");
		periodList.add("5");
		periodList.add("6");
		periodList.add("7");
		periodList.add("8");
		periodList.add("9");
		return periodList;
	}
	
	@RequestMapping(value="/addFacultyTime.htm", method=RequestMethod.GET)
	public String showFacultyPage(Model model){
		
		FaculityTimeForm facultyTimeForm = new FaculityTimeForm();
		model.addAttribute("facultyTimeForm", facultyTimeForm);
		
		model.addAttribute("buttonLable", "Add Faculty Time");
		List<FaculityTimeForm> facultyTimeForms = basFacultyTimeService.findFacultyTime();
		model.addAttribute("buttonLable", "Add Faculty Time");
		model.addAttribute("facultyTimeForms", facultyTimeForms);
		return NavigationConstant.FACULTY_PREFIX_PAGE+ NavigationConstant.FACULTY_TIME_PAGE;
	}
	
	@RequestMapping(value="/addFacultyTime.htm", method=RequestMethod.POST)
	public String showFacultyPage(@ModelAttribute("facultyTimeForm") FaculityTimeForm facultyTimeForm,
			@RequestParam(value = "buttonAction", required = false) String buttonAction, Model model){
		if (buttonAction.equals("Update Faculty Time")) {
			System.out.println("Contr111 : " + facultyTimeForm);
			basFacultyTimeService.updateFacultyTime(facultyTimeForm);
			//System.out.println("Contr : " + facultyTimeForm);
		} else {
			basFacultyTimeService.addFacultyTime(facultyTimeForm);
		}

		List<FaculityTimeForm> facultyTimeForms = basFacultyTimeService
				.findFacultyTime();
		model.addAttribute("buttonLable", "Add Faculty Time");

		
		FaculityTimeForm nFacultyTimeForm = new FaculityTimeForm();
		model.addAttribute("facultyTimeForm", nFacultyTimeForm);

		model.addAttribute("facultyTimeForms", facultyTimeForms);
		//System.out.println(facultyTimeForms);
		return NavigationConstant.FACULTY_PREFIX_PAGE+ NavigationConstant.FACULTY_TIME_PAGE;
	}
	
	@RequestMapping(value = "/editFacultyTime.htm", method = RequestMethod.GET)
	public String editFacultyTime(HttpServletRequest request, Model model) {
		
		//RequestDispatcher rd = request.getRequestDispatcher("");
		
		Integer facId = Integer.parseInt(request.getParameter("fid"));
		String dayName = request.getParameter("dayName");
		String classRoom = request.getParameter("classRoom");
		String subjectCode = request.getParameter("subjectCode");
		
		//FaculityTimeForm facultyTimeForm = basFacultyTimeService.findFacultyTimeById(facId);
		FaculityTimeForm facultyTimeForm = basFacultyTimeService.findFacultyTimeByParams(facId, dayName, classRoom, subjectCode);
		model.addAttribute("facultyTimeForm", facultyTimeForm);
		List<FaculityTimeForm> facultyTimeForms = basFacultyTimeService
				.findFacultyTime();
		model.addAttribute("buttonLable", "Update Faculty Time");
		model.addAttribute("facultyTimeForms", facultyTimeForms);
		return NavigationConstant.FACULTY_PREFIX_PAGE+ NavigationConstant.FACULTY_TIME_PAGE;
	}
	@RequestMapping(value = "/deleteFacultyTime.htm", method = RequestMethod.GET)
	public String deleteFacultyTime(HttpServletRequest request, Model model) {
		Integer facId = Integer.parseInt(request.getParameter("fid"));
		System.out.println("ficId" + facId);
		basFacultyTimeService.deleteFacultyTime(facId);
		return "redirect:addFacultyTime.htm";
	
	}
}
