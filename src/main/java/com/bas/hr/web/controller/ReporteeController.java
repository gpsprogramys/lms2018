package com.bas.hr.web.controller;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bas.common.constant.NavigationConstant;
import com.bas.common.constant.PageTitleContant;
import com.bas.common.web.controller.LoginController;
import com.bas.employee.service.BasFacultyService;
import com.bas.employee.web.controller.form.LoginForm;
import com.bas.employee.web.controller.form.ReportingManagerVO;
import com.bas.hr.service.ReporteeService;
import com.bas.hr.web.controller.model.ManagerEmployeeRelationVO;

/**
 * 
 * @author nagendra.yadav
 *
 */
@Controller
@Scope("request")
public class ReporteeController{
	
    private static final Log logger = LogFactory.getLog(LoginController.class);
	
	@Autowired
	@Qualifier("ReporteeServiceImpl")
	private ReporteeService reporteeService;
	
	@Autowired
	@Qualifier("BasFacultyServiceImpl")
	private BasFacultyService basFacultyService;
	
	
	@RequestMapping(value="addEmployeeManagerRelationship", method=RequestMethod.POST,produces = { MediaType.TEXT_PLAIN_VALUE })
@ResponseBody	 public String employeeToManagerController(@ModelAttribute("addEmployeeManagerForm") ManagerEmployeeRelationVO managerEmployeeRelationVO,HttpSession session) {
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		managerEmployeeRelationVO.setHrid(loginForm.getEid());
		managerEmployeeRelationVO.setUserid(loginForm.getUserid());
		logger.debug("Printing from the controller layer..............");
		logger.debug("System.out.println(managerEmployeeRelationVO");
		String result = reporteeService.addEmployeeToManagerRelation(managerEmployeeRelationVO);
		return result;
	}
	
	
	@RequestMapping(value="/reportee", method=RequestMethod.GET)
	public String employeeOnLeavePost(@RequestParam(value="depttName",required=false) String depttName, Model model,HttpSession session)	{
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		List<ReportingManagerVO> reportingManagerVOs = basFacultyService.findEmployeeListByManager(loginForm.getEid()+"");
		model.addAttribute("reportingManagerVOs",reportingManagerVOs);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.REPORTEE_EMPLOYE_TITLE);
		return NavigationConstant.EMPLOYEE_PREFIX_PAGE
				+ NavigationConstant.REPORTEE_EMPLOYEE_PAGE;
	}

}
