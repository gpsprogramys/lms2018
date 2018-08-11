package com.bas.admin.web.controller;

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

import com.bas.admin.service.OrgTimeService;
import com.bas.admin.web.controller.form.OrganizationTimeVO;
import com.bas.common.constant.NavigationConstant;

/**
 * 
 * @author sid
 * 
 */
@Controller
@Scope("request")
public class OrganizationTimeController {

	@Autowired
	@Qualifier("orgTimeServiceImpl")
	private OrgTimeService orgTimeService;

	@RequestMapping(value = "/orgTime.htm", method = RequestMethod.GET)
	public String orgTimeInit(@ModelAttribute("orgTimeCommand") OrganizationTimeVO OrganizationTimeVO,@RequestParam(value = "buttonAction", required = false) String buttonAction,Model model) {
			
		//List<OrganizationTimeVO> organizationTimeVOslist = orgTimeService.findOrgTimes();		
//		//model.addAttribute("organizationTimeVOslist", organizationTimeVOslist);		
		model.addAttribute("buttonLable", "Add Organization Time");
		return NavigationConstant.ADMIN_PREFIX_PAGE	+ NavigationConstant.ORG_TIME_PAGE;
	}
	
	@RequestMapping(value = "/orgTimeInsert.htm", method = RequestMethod.POST)
	public String orgTimeInsertUpdatePost(@ModelAttribute("orgTimeCommand") OrganizationTimeVO organizationTimeVO,@RequestParam(value = "buttonAction", required = false) String buttonAction,Model model) {
		//OrganizationTimeVO organizationTimeVO2 = new OrganizationTimeVO();
		
		if (buttonAction != null && buttonAction.equalsIgnoreCase("Edit Organization Time")) {
			String result=orgTimeService.editOrgTime(organizationTimeVO);
			model.addAttribute("buttonLable", "Add Organization Time");
			model.addAttribute("Message", "Updation Successful");			
		} else {
			try {
				String result = orgTimeService.addOrgTime(organizationTimeVO);
				model.addAttribute("buttonLable", "Add Organization Time");
				model.addAttribute("Message", "Insertion Successful");
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("buttonLable", "Add Organization Time");
				model.addAttribute("Message", "Duplicate Entry:Month Exists");
			}			
		}	
		
		organizationTimeVO = new OrganizationTimeVO();
		//List<OrganizationTimeVO> organizationTimeVOslist = orgTimeService.findOrgTimes();		
		//model.addAttribute("organizationTimeVOslist", organizationTimeVOslist);
		model.addAttribute("orgTimeCommand", organizationTimeVO);
		return NavigationConstant.ADMIN_PREFIX_PAGE	+ NavigationConstant.ORG_TIME_PAGE;
	}
	
	@RequestMapping(value = "/editOrgTime.htm", method = RequestMethod.GET)
	public String orgTimeEditInitGet(@ModelAttribute("orgTimeCommand") OrganizationTimeVO organizationTimeVO,HttpServletRequest request, Model model) {
		Integer sno = Integer.parseInt(request.getParameter("psno"));	
		model.addAttribute("buttonLable", "Edit Organization Time");
		organizationTimeVO = orgTimeService.findOrgTimesbySno(sno);		
		//List<OrganizationTimeVO> organizationTimeVOslist = orgTimeService.findOrgTimes();
		//model.addAttribute("organizationTimeVOslist", organizationTimeVOslist);		
		model.addAttribute("orgTimeCommand", organizationTimeVO);
		return NavigationConstant.ADMIN_PREFIX_PAGE	+ NavigationConstant.ORG_TIME_PAGE;
	}

	@RequestMapping(value = "/deleteOrgTime.htm", method = RequestMethod.GET)
	public String orgTimeDeleteGet(HttpServletRequest request, Model model) {
		Integer sno = Integer.parseInt(request.getParameter("psno"));		
		orgTimeService.deleteOrgTime(sno);
		model.addAttribute("Message", "Deletion Successful");
		return "redirect:orgTime.htm";
	
	}

}
