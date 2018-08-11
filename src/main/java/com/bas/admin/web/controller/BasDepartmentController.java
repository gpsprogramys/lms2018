package com.bas.admin.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bas.admin.service.DepartmentService;
import com.bas.admin.web.controller.form.DepartmentForm;
import com.bas.common.constant.NavigationConstant;
import com.bas.common.constant.PageTitleContant;
import com.bas.common.util.CurrentUserDataUtil;
import com.bas.common.util.DateUtils;

/**
 * 
 * @author nagendra.yadav
 * 
 */
@Controller
public class BasDepartmentController {

	@Autowired
	@Qualifier("DepartmentServiceImpl")
	private DepartmentService departmentService;

	@RequestMapping(value = "/addDepartment", method = RequestMethod.GET)
	public String showAddDepartment(Model model) {
		DepartmentForm addDepartment = new DepartmentForm();
		// DepartmentForm departmentForm = new DepartmentForm();
		model.addAttribute("departmentForm", addDepartment);
		List<DepartmentForm> departmentForms = departmentService.findDepartments();
		model.addAttribute("buttonLable", "Add Department");
		model.addAttribute("departmentForms", departmentForms);
		model.addAttribute("department", addDepartment);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.ADD_DEPARTMENT_TITLE);
		return NavigationConstant.ADMIN_PREFIX_PAGE + NavigationConstant.ADD_DEPARTMENT_PAGE;
	}

	@RequestMapping(value = "/addAjaxDepartment", method = RequestMethod.GET)
	public @ResponseBody DepartmentForm AddDepartment(@RequestParam("departmentName") String departmentName,
			@RequestParam("departmentShortName") String shortName, @RequestParam("description") String description, Model model,HttpSession session) {
		DepartmentForm departmentForm = new DepartmentForm();
		departmentForm.setEntryBy(CurrentUserDataUtil.getCurrentLoggedUser(session));
		departmentForm.setDepartmentName(departmentName);
		departmentForm.setDepartmentShortName(shortName);
		departmentForm.setDescription(description);
		departmentForm.setDoe(DateUtils.getCurrentTimeIntoTimestamp());
		departmentForm.setSdoe(DateUtils.convertDateIntoString(new Date()));
		departmentForm.setSdom(DateUtils.convertDateIntoString(new Date()));
		departmentForm.setDom(DateUtils.getCurrentTimeIntoTimestamp());
		String message = "";
		message = departmentService.addDepartment(departmentForm);
		departmentForm.setDepartmentId(Integer.parseInt(message));
		return departmentForm;
	}

	@RequestMapping(value = "/deleteDepartment", method = RequestMethod.GET)
	public @ResponseBody String deleteDepartment(@RequestParam(value = "depName") String depName,Model model) {
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.ADD_DEPARTMENT_TITLE);
		return departmentService.deleteDepartment(depName);
	}
}
