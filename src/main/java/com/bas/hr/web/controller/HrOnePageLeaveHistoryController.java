package com.bas.hr.web.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bas.common.constant.NavigationConstant;
import com.bas.common.constant.PageTitleContant;
import com.bas.common.util.DateUtils;
import com.bas.common.web.controller.form.EmployeeLeaveDetailVO;
import com.bas.employee.web.controller.form.LoginForm;
import com.bas.hr.service.HrOnePageLeaveHistoryService;
import com.bas.hr.web.controller.model.EmployeeOnePageLeaveHistoryVO;
import com.synergy.bank.employee.service.IEmployeeLMSService;

@Controller
@Scope("request")
public class HrOnePageLeaveHistoryController {

	@Autowired
	@Qualifier("EmployeeLMSService")
	private IEmployeeLMSService iEmployeeService;

	@Autowired
	@Qualifier("HrOnePageLeaveHistoryServiceImpl")
	private HrOnePageLeaveHistoryService hrOnePageLeaveHistoryService;

	// @ResponseBody here converts java object into JSON format
	/*
	 * @RequestMapping(value = "findEmployeeOnePageLeaveHistory", method =
	 * RequestMethod.GET) public String
	 * findEmployeeOnePageLeaveHistory(@RequestParam("eid") String
	 * eid,@RequestParam(value="yearSession",required=false) String
	 * yearSession,Model model) { EmployeeLeaveDetailVO employeeLeaveDetailVO =
	 * iEmployeeService.findEmployeeLeaveDetail(eid);
	 * List<EmployeeOnePageLeaveHistoryVO>
	 * employeeOnePageLeaveHistoryVOList=hrOnePageLeaveHistoryService
	 * .findEmployeeOnePageLeaveHistory(eid, yearSession);
	 * model.addAttribute("employeeLeaveDetailVO", employeeLeaveDetailVO);
	 * model.addAttribute("employeeOnePageLeaveHistoryVOList",
	 * employeeOnePageLeaveHistoryVOList); return null; }
	 */

	@RequestMapping(value = "findEmployeeOnePageLeaveHistory", method = RequestMethod.GET)
	public String findEmployeeOnePageLeaveHistory(
			@RequestParam(value = "eid", required = false) String eid,
			Model model, HttpSession session) {
		if (eid == null) {
			LoginForm loginForm = (LoginForm) session
					.getAttribute(NavigationConstant.USER_SESSION_DATA);
			eid = loginForm.getEid();
		}
		//getCurrentSessionYear 2015-2016
		String currentSessionYear=DateUtils.getCurrentSessionYear();
		String previousSessionYear=DateUtils.getPreviousSessionYear();
		
		model.addAttribute("currentSessionYear",currentSessionYear);
		model.addAttribute("previousSessionYear",previousSessionYear);
		EmployeeLeaveDetailVO employeeLeaveDetailVO = iEmployeeService.findEmployeeLeaveDetail(eid);
		model.addAttribute("employeeLeaveDetailVO",employeeLeaveDetailVO);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.EMPLOYEES_ONE_PAGE_LEAVE_HISTORY_TITLE);
		if(employeeLeaveDetailVO!=null)
		model.addAttribute("eidNameDepartment",employeeLeaveDetailVO.getEid()+"-"+employeeLeaveDetailVO.getName()+"-"+employeeLeaveDetailVO.getDepartment());
		List<EmployeeOnePageLeaveHistoryVO> employeeOnePageLeaveHistoryVOList = hrOnePageLeaveHistoryService.findEmployeeOnePageLeaveHistory(eid, "2016-02-01","2016-02-29");
		model.addAttribute("employeeOnePageLeaveHistoryVOList",employeeOnePageLeaveHistoryVOList);
		return NavigationConstant.ADMIN_PREFIX_PAGE
				+ NavigationConstant.FIND_EMPLOYEE_ONE_PAGE_LEAVE_HISTORY;
	}

}
