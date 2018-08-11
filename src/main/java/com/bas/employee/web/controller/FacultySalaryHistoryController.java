package com.bas.employee.web.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bas.common.constant.NavigationConstant;
import com.bas.employee.service.BasFacultyService;
import com.bas.employee.web.controller.form.FacultySalaryMasterVO;
import com.bas.employee.web.controller.form.LoginForm;

/**
 * @author Sid 
 *  Controller for the Salary History page. 
 */
@Controller
public class FacultySalaryHistoryController {
	
	@Autowired
	@Qualifier("BasFacultyServiceImpl")
	private BasFacultyService basFacultyService;
	
	@RequestMapping(value="/salaryHistory",method=RequestMethod.GET)
	public String showSalaryHistory(Model model,HttpSession session) {
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		List<FacultySalaryMasterVO> facultySalaryMasterVO=basFacultyService.findSalaryHistory(loginForm.getEid()+"");
		model.addAttribute("facultySalaryMasterVO", facultySalaryMasterVO);
		return NavigationConstant.FACULTY_PREFIX_PAGE+NavigationConstant.SALARY_HISTORY_PAGE;
	}

}
