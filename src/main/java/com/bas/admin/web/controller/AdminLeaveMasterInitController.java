package com.bas.admin.web.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bas.admin.service.AdminLeaveMasterInitService;
import com.bas.common.constant.NavigationConstant;
import com.bas.common.constant.PageTitleContant;
import com.bas.employee.service.BasFacultyService;
import com.bas.employee.web.controller.form.FaculityLeaveMasterVO;
import com.bas.employee.web.controller.form.FacultyForm;
import com.bas.employee.web.controller.form.FacultyLeaveBalanceVO1;

/**
 * 
 * @author Sid
 * 
 */
@Controller
@Scope("request")
public class AdminLeaveMasterInitController {

	@Autowired
	@Qualifier("adminLeaveMasterInitImpl")
	private AdminLeaveMasterInitService adminLeaveMasterInitService;

	@Autowired
	@Qualifier("BasFacultyServiceImpl")
	private BasFacultyService basFacultyService;

	@RequestMapping(value = "/initLeaveMaster.htm", method = RequestMethod.GET)
	public String initLeaveMasterGet(Model model) {	
		FaculityLeaveMasterVO faculityLeaveMasterVO = new FaculityLeaveMasterVO();
		model.addAttribute("faculityLeaveMasterVO",faculityLeaveMasterVO);
		model.addAttribute("ButtonLabel","Save");		
		List<FaculityLeaveMasterVO> faculityLeaveMasterVOslist= adminLeaveMasterInitService.findAllleaveBalance();
		model.addAttribute("faculityLeaveMasterVOslist",faculityLeaveMasterVOslist);
		//System.out.println(faculityLeaveMasterVO.hashCode());
		return NavigationConstant.ADMIN_PREFIX_PAGE+NavigationConstant.ADMIN_LEAVEMASTRINIT_PAGE;
	}	


	@RequestMapping(value = "/leaveMasterInitSubmit.htm", method = RequestMethod.POST)
	public String insertInitPost(@ModelAttribute("faculityLeaveMasterVO") FaculityLeaveMasterVO faculityLeaveMasterVO,@RequestParam(value = "buttonAction", required = false) String buttonAction,HttpServletRequest request, Model model) {

		if (buttonAction != null && buttonAction.equalsIgnoreCase("Save")) {			
			try{
				String result = adminLeaveMasterInitService.addLeaveInit(faculityLeaveMasterVO);
				model.addAttribute("ButtonLabel","Save");
				model.addAttribute("Message","Added Leave Successfully");
				String str = finaAllLeaveHistory(model);
				return str;

			}
			catch(DuplicateKeyException e)
			{
				String str = finaAllLeaveHistory(model);
				model.addAttribute("ButtonLabel","Save");
				model.addAttribute("Message","Duplicate Entry Attempted");
				return str;
			}		
		} else{
			String result = adminLeaveMasterInitService.editLeaveInit(faculityLeaveMasterVO);
			model.addAttribute("faculityLeaveMasterVO",faculityLeaveMasterVO);
			model.addAttribute("ButtonLabel","Save");
			model.addAttribute("Message","Updated Succesfully");
			String str = finaAllLeaveHistory(model);	
			return str;		

		}				

	}

	@RequestMapping(value = "/allEmployeeHistoryLeaveInit.htm", method = RequestMethod.GET)
	public String finaAllLeaveHistory(Model model) {
		List<FaculityLeaveMasterVO> faculityLeaveMasterVOslist = adminLeaveMasterInitService.findAllleaveBalance();
		model.addAttribute("faculityLeaveMasterVOslist",faculityLeaveMasterVOslist);
		model.addAttribute("ButtonLabel","Save");
		return NavigationConstant.ADMIN_PREFIX_PAGE+NavigationConstant.ADMIN_LEAVEMASTRINIT_PAGE;
	}

	@RequestMapping(value = "/leaveApplicationPostSrch.htm", method = RequestMethod.POST)
	public String getSearch(HttpServletRequest request, Model model) {		
		FaculityLeaveMasterVO faculityLeaveMasterVO = new FaculityLeaveMasterVO();
		model.addAttribute("faculityLeaveMasterVO", faculityLeaveMasterVO);
		model.addAttribute("ButtonLabel","Save");
		String name = request.getParameter("searchFiled");
		if (name != null) {
			String tokens[] = name.split("-");
			String empid = tokens[1];
			 Calendar cal = Calendar.getInstance(); 
				int month = cal.get(Calendar.MONTH)+1;
				 String leaveMonth;
				 if(month<10)
				 {leaveMonth="0"+month;}
				 else
				 {leaveMonth=month+"";}
			try {
				faculityLeaveMasterVO = basFacultyService.findLeaveAppData(empid);
				model.addAttribute("faculityLeaveMasterVO", faculityLeaveMasterVO);
				model.addAttribute("ButtonLabel","Save");
			} catch (Exception e) {				
				model.addAttribute("faculityLeaveMasterVO", faculityLeaveMasterVO);	
				model.addAttribute("ButtonLabel","Save");
				e.printStackTrace();
				return NavigationConstant.ADMIN_PREFIX_PAGE+NavigationConstant.ADMIN_LEAVEMASTRINIT_PAGE;
			}
		}

		model.addAttribute("ButtonLabel","Save");
		model.addAttribute("faculityLeaveMasterVO", faculityLeaveMasterVO);
		return NavigationConstant.ADMIN_PREFIX_PAGE+NavigationConstant.ADMIN_LEAVEMASTRINIT_PAGE;

	}

	@RequestMapping(value = "/editLeaveInit.htm", method = RequestMethod.GET)
	public String editLeaveInitget(HttpServletRequest request, Model model) {
		String empno = request.getParameter("psno");
		String mdate = request.getParameter("pdate");
		//FaculityLeaveMasterVO faculityLeaveMasterVO2 = new FaculityLeaveMasterVO();
		FaculityLeaveMasterVO faculityLeaveMasterVO2 = adminLeaveMasterInitService.findemplist(empno,mdate);
		model.addAttribute("faculityLeaveMasterVO",faculityLeaveMasterVO2);
		model.addAttribute("ButtonLabel","Edit Leave");
		List<FaculityLeaveMasterVO> faculityLeaveMasterVOslist = adminLeaveMasterInitService.findAllleaveBalance();
		model.addAttribute("faculityLeaveMasterVOslist",faculityLeaveMasterVOslist);
		return NavigationConstant.ADMIN_PREFIX_PAGE+NavigationConstant.ADMIN_LEAVEMASTRINIT_PAGE;	

	}

	@RequestMapping(value = "/deleteLeaveInit.htm", method = RequestMethod.GET)
	public String deleteLeaveHistory(@ModelAttribute("faculityLeaveMasterVO") FaculityLeaveMasterVO faculityLeaveMasterVO,HttpServletRequest request, Model model)throws ParseException {
		String empNo = request.getParameter("psno");
		String mdate = request.getParameter("pdate");
		//String sdate = request.getParameter("leaveStartDate");
		String str=adminLeaveMasterInitService.deleteLeaveInit(empNo,mdate);
		List<FaculityLeaveMasterVO> faculityLeaveMasterVOslist = adminLeaveMasterInitService.findAllleaveBalance();
		model.addAttribute("faculityLeaveMasterVOslist",faculityLeaveMasterVOslist);
		model.addAttribute("Message","Deleted Successfully");
		model.addAttribute("ButtonLabel","Save");
		return NavigationConstant.ADMIN_PREFIX_PAGE+NavigationConstant.ADMIN_LEAVEMASTRINIT_PAGE;
	}


	@RequestMapping(value = "/renderEmpImage3", method = RequestMethod.GET)
	public void renderPhoto(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");
		response.setContentType("image/jpg");
		byte[] image = basFacultyService.findEmpPhotoByName(name);
		if (image != null) {
			response.getOutputStream().write(image);
		}
	}
	
	@RequestMapping(value="/leavebalance/list", method=RequestMethod.GET)
	public @ResponseBody List<FacultyForm>  leaveBalance(Model model) {
		List<FacultyForm> employeeFormsD=basFacultyService.findAllFaculty();
			//System.out.println(employeeFormsD);
			//model.addAttribute("employeeFormsD", employeeFormsD);
			//model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.EMPLOYEES_LEAVE_BALANCE_TITLE);
		  return employeeFormsD;
			//return com.bas.common.constant.NavigationConstant.ADMIN_PREFIX_PAGE+com.bas.common.constant.NavigationConstant.SHOW_LEAVE_BALANCE_PAGE;
	}
	
	@RequestMapping(value="/showLeaveBalance", method=RequestMethod.GET)
	public String showLeaveBalance(Model model) {
		//List<FacultyForm> employeeFormsD=basFacultyService.findAllFaculty();
			//System.out.println(employeeFormsD);
			//model.addAttribute("employeeFormsD", employeeFormsD);
			model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.EMPLOYEES_LEAVE_BALANCE_TITLE);
			return com.bas.common.constant.NavigationConstant.ADMIN_PREFIX_PAGE+com.bas.common.constant.NavigationConstant.SHOW_LEAVE_BALANCE_PAGE;
	}
	
	@RequestMapping(value="/showAdminLeaveBalance", method=RequestMethod.GET)
	public String showAdminLeaveBalance(Model model) {
		//List<FacultyForm> employeeFormsD=basFacultyService.findAllFaculty();
			//System.out.println(employeeFormsD);
			//model.addAttribute("employeeFormsD", employeeFormsD);
		List<FacultyForm> employeeFormsD=basFacultyService.findAllFaculty();
		model.addAttribute("allFacultyList", employeeFormsD);
			model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.EMPLOYEES_LEAVE_BALANCE_TITLE);
			return com.bas.common.constant.NavigationConstant.ADMIN_PREFIX_PAGE+com.bas.common.constant.NavigationConstant.SHOW_ADMIN_LEAVE_BALANCE_PAGE;
	}
	
	@RequestMapping(value="/leaveBalanceDetail", method=RequestMethod.GET)
	public String leaveBalanceDetail(@RequestParam("empid") String empId, Model model){
		System.out.println("Welcome from leave balance controller1");
		FacultyLeaveBalanceVO1 facultyLeaveBalanceVO = basFacultyService.findFacultyLeaveBalanceById(empId);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.LEAVE_BALANCE_TITLE);
		model.addAttribute("facultyLeaveBalanceDetail", facultyLeaveBalanceVO);
		System.out.println("leave balance controller 6");
		return com.bas.common.constant.NavigationConstant.ADMIN_PREFIX_PAGE+com.bas.common.constant.NavigationConstant.LEAVE_BALANCE_DETAIL_PAGE;
	}
	
	@RequestMapping(value="/updateBalanceDetail", method=RequestMethod.POST)
	@ResponseBody public FacultyLeaveBalanceVO1 leaveBalanceDetail(@ModelAttribute("facultyLeaveBalanceDetail") FacultyLeaveBalanceVO1 facultyLeaveBalance, Model model){
		//update record
		Timestamp time = new Timestamp(new Date().getTime());
		facultyLeaveBalance.setDom(time);
		//facultyLeaveBalance.setEmpid(Long.parseLong(empId));
		//get updated result
		FacultyLeaveBalanceVO1 facultyLeaveBalanceVO =basFacultyService.updateLeaveBalance(facultyLeaveBalance);// basFacultyService.findFacultyLeaveBalanceById(String.valueOf(facultyLeaveBalanceVOCopy.getId()));
		//model.addAttribute("facultyLeaveBalanceDetail", facultyLeaveBalanceVO);
		//model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.LEAVE_BALANCE_TITLE);
		//return com.bas.common.constant.NavigationConstant.ADMIN_PREFIX_PAGE+com.bas.common.constant.NavigationConstant.LEAVE_BALANCE_DETAIL_PAGE;
		return facultyLeaveBalanceVO;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// now Spring knows how to handle multipart object and convert them
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		//Create a new CustomDateEditor
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		//Register it as custom editor for the Date type
		binder.registerCustomEditor(Date.class, editor);
	}

}
