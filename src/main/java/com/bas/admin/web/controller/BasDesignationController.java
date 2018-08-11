package com.bas.admin.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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

import com.bas.admin.service.DesignationService;
import com.bas.admin.web.controller.form.DesignationForm;
import com.bas.common.constant.NavigationConstant;
import com.bas.common.constant.PageTitleContant;
import com.bas.common.util.CurrentUserDataUtil;
import com.bas.common.util.DateUtils;

/**
 * 
 * @author Amogh
 * 
 */
@Controller
public class BasDesignationController {
	@Autowired
	@Qualifier("DesignationServiceImpl")
	private DesignationService designationService;

	@RequestMapping(value = "/addDesignation", method = RequestMethod.GET)
	public @ResponseBody DesignationForm addDesignation(@RequestParam("designationName") String designationName,@RequestParam("description") String description,HttpSession session) {
		DesignationForm designationForm = new DesignationForm();
		designationForm.setEntryBy(CurrentUserDataUtil.getCurrentLoggedUser(session));
		designationForm.setDesignationName(designationName);
		designationForm.setDescription(description);
		designationForm.setDoe(DateUtils.getCurrentTimeIntoTimestamp());
		designationForm.setSdoe(DateUtils.convertDateIntoString(new Date()));
		designationForm.setSdom(DateUtils.convertDateIntoString(new Date()));
		designationForm.setDom(DateUtils.getCurrentTimeIntoTimestamp());
		String maxid = designationService.addDesignation(designationForm);
		designationForm.setDesignationId(Integer.parseInt(maxid));
		return designationForm;
	}
	
	
	
	@RequestMapping(value = "/addDesignationTodb", method = RequestMethod.POST)
	public String AddDesignation(@ModelAttribute(value="designation") DesignationForm designationform ,Model model) {
	String message = designationService.addDesignation(designationform);
	model.addAttribute("message", message);
	return NavigationConstant.ADMIN_PREFIX_PAGE
			+ NavigationConstant.ADD_DESIGNATION_PAGE;
}

	
	@RequestMapping(value = "/designations", method = RequestMethod.GET)
	public String designations(	Model model) {
		List<DesignationForm> designationForms = designationService.findDesignations();
		model.addAttribute("designationForms", designationForms);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.DESIGNATIONS_TITLE);
		return NavigationConstant.ADMIN_PREFIX_PAGE
				+ NavigationConstant.SHOW_DESIGNATIONS;
	}

	@RequestMapping(value = "/deleteDesignation", method = RequestMethod.GET)
	public @ResponseBody String deleteDesignation(HttpServletRequest request, Model model) {
		Integer desigId = Integer.parseInt(request.getParameter("designationId"));
		System.out.println("desigId" + desigId);
		String result=designationService.deleteDesignation(desigId);
		return result;
	}

	@RequestMapping(value = "/editDesignation.htm", method = RequestMethod.GET)
	public String editDesignation(HttpServletRequest request, Model model) {
		Integer desigId = Integer.parseInt(request.getParameter("designationId"));
		DesignationForm designationForm = designationService
				.findDesignationById(desigId);
		model.addAttribute("designationForm", designationForm);
		List<DesignationForm> designationForms = designationService
				.findDesignations();
		model.addAttribute("buttonLable", "Update Designation");
		model.addAttribute("designationForms", designationForms);
		return NavigationConstant.ADMIN_PREFIX_PAGE
				+ NavigationConstant.ADD_DESIGNATION_PAGE;
	}
}
