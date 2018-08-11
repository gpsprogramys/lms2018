package com.bas.employee.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bas.common.constant.NavigationConstant;
import com.bas.common.util.DateUtils;
import com.bas.common.web.controller.form.SubjectArrangementVO;
import com.bas.common.web.controller.form.SubjectNameCodeVO;
import com.bas.employee.service.FacultySubjectAssignmentService;
import com.bas.employee.web.controller.form.LoginForm;


/**
 * @author nagendra
 *
 * This is controller which will give
 * you subject assignment for concern faculty.
 *
 */
@Controller
public class FacultySubjectAssignmentController {
	
	@Autowired
	@Qualifier("FacultySubjectAssignmentServiceImpl")
	private FacultySubjectAssignmentService facultySubjectAssignmentService;
	
	@RequestMapping(value="findBranchSemSectionByEmpId",method=RequestMethod.GET)
	@ResponseBody public List<String> findBranchSemSection(HttpSession session){
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		return facultySubjectAssignmentService.findBranchSemSection(loginForm.getEid());
	}
	

	@RequestMapping(value="findFacultyByBranchPeriod",method=RequestMethod.GET)
	@ResponseBody  public List<SubjectArrangementVO> findFacultyByBranchPeriod(@RequestParam("currentDate") String currentDate, String branchSemSec,int period,HttpSession session){
		String dayPeriod=DateUtils.findDayAsPerDate(currentDate)+"-"+period;
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		return facultySubjectAssignmentService.findFacultyByBranchPeriod(branchSemSec, dayPeriod, Integer.parseInt(loginForm.getEid()));
	}
	
	@RequestMapping(value="findSubjectsByFacultyBranchPeriod",method=RequestMethod.GET)
	@ResponseBody  public List<SubjectNameCodeVO>  findSubjectsByFacultyBranchPeriod(@RequestParam("currentDate") String currentDate, String branchSemSec,int period,int empid,HttpSession session){
		String dayPeriod=DateUtils.findDayAsPerDate(currentDate)+"-"+period;
		return facultySubjectAssignmentService.findSubjectsByFacultyBranchPeriod(branchSemSec, dayPeriod,empid);
	}
	
	
}
