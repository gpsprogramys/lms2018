package com.bas.employee.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.bas.common.constant.NavigationConstant;
import com.bas.employee.web.controller.form.EmployeeDetailsForm;
import com.bas.employee.web.controller.form.EmployeeLeaveRequestForm;
import com.synergy.bank.employee.service.IEmployeeLMSService;

@Controller
public class BankEmployeeController 
{
	private boolean DEBUG = false;
	//private int empId = 555;
	
	@Autowired
	@Qualifier("EmployeeLMSService")
	private IEmployeeLMSService iEmployeeService;
	
	 public BankEmployeeController(){
		
	}
	
/*	@RequestMapping(value = "bankEmployeeHome", method = RequestMethod.GET)
	public String creditCardAppStatus(Model model) 
	{
		System.out.println("--------------------------=================");
		return NavigationConstant.EMPLOYEE_BASE
				+ NavigationConstant.BANK_EMPLOYEE_HOME_PAGE;
	}*/
	
//	// @ResponseBody here converts java object into JSON format
//	@RequestMapping(value = "emplyeeSuggestionManager", method = RequestMethod.GET, produces = {
//			MediaType.APPLICATION_JSON_VALUE })
//	public @ResponseBody List<SuggestionManagerOptionVO> findManagerEmplyeeSuggestionOption(
//			@RequestParam("searchword") String searchword) {
//		//Converting the integer id of the manager into String for methods sake
//		String managerId = String.valueOf(empId);
//		List<SuggestionManagerOptionVO> suggestionOptionList = iEmployeeService.findEmplyeeSuggestionManager(searchword, managerId);
//		return suggestionOptionList;
//	}
	 
//	BANK_EMPLOYEE_LEAVE_APPLY
//	@RequestMapping(value="applyLeave", method= RequestMethod.GET)
//	public String applyLeave(Model model, HttpServletRequest request){
//		
//		EmployeeDetailsForm employeeDetailsForm = new EmployeeDetailsForm();
//		employeeDetailsForm.setId(empId);
//		EmployeeDetailsForm employeeDetails = new EmployeeDetailsForm();
//		
//		employeeDetails = iEmployeeService.findEmployeeById(employeeDetailsForm);
//		String department = employeeDetails.getDepartment();
//		String designation = employeeDetails.getDesignation();
//		String type = employeeDetails.getType();
//		System.out.println("department============>>>>>>>>>>>>"+department);
//		model.addAttribute("department",department);
//		model.addAttribute("designation",designation);
//		model.addAttribute("type", type);
//		model.addAttribute(employeeDetails.getDepartment());
//		request.setAttribute("depart", department);
//		System.out.println("department>>>>>>>>>>>"+department);
//		EmployeeLeaveBalanceForm balanceForm = getLeaveBalanceByEmpId();
//		model.addAttribute("totalCL", balanceForm.getTotalCL());
//		model.addAttribute("totalEL", balanceForm.getTotalEL());
//		model.addAttribute("totalSL", balanceForm.getTotalSL());
//		model.addAttribute("od", balanceForm.getOD());
//		
//		EmployeeDetailsForm detailsForm = new EmployeeDetailsForm();
//		int managerId = getReportingManagerId(empId);
//		detailsForm = getReportingManager(managerId);
//		System.out.println("manager's email id is: "+detailsForm.getEmail());
//		model.addAttribute("managers", detailsForm.getName());
//		System.out.println("manager's name is "+detailsForm.getName());
//		return NavigationConstant.EMPLOYEE_BASE+NavigationConstant.APPLY_LEAVE_PAGE;
//	}
//	
	/**
	 * Method which fetches the leave information
	 * @return
	 */
/*	public EmployeeLeaveBalanceForm getLeaveBalanceByEmpId(){
		EmployeeLeaveBalanceForm balanceForm = new EmployeeLeaveBalanceForm();
		balanceForm.setEmpNo(555);
		balanceForm = iEmployeeService.getLeaveBalanceByEmpId(balanceForm);
		return balanceForm;
	}*/
	
	@RequestMapping(value="leaveEntry", method=RequestMethod.POST)
	public String submitLeaveRequest(@ModelAttribute("leaveFormData") EmployeeLeaveRequestForm leaveFormData){
		System.out.println("leave from======"+leaveFormData.getLeaveFrom());
		System.out.println("leave to===="+leaveFormData.getLeaveTo());
//		System.out.println("total days===="+leaveFormData.getTotalDays());
		System.out.println("leave type==="+leaveFormData.getLeaveType());
		System.out.println("description===="+leaveFormData.getDescription());
		System.out.println("purpose==== "+leaveFormData.getPurpose());
		System.out.println("reason==="+leaveFormData.getReason());
		System.out.println("mobile==="+leaveFormData.getMobile());
		System.out.println("leave category==="+leaveFormData.getLeaveCategory());
		System.out.println("address==="+leaveFormData.getAddress());
		System.out.println("reporting mangr==="+leaveFormData.getReportingManager());
		System.out.println("ccto===="+leaveFormData.getCCTo());
		int totalDays = calculateTotalLeaveDays(leaveFormData.getLeaveFrom(), leaveFormData.getLeaveTo());
		leaveFormData.setTotalDays(totalDays);
		System.out.println("total days===="+leaveFormData.getTotalDays());
		int managerId = getReportingManagerId(Integer.parseInt(leaveFormData.getEmployeeId()));
		EmployeeDetailsForm detailsForm = new EmployeeDetailsForm();
		detailsForm = getReportingManager(managerId);
		System.out.println("Manager's email id is :"+detailsForm.getEmail());
		iEmployeeService.saveLeaveRequest(leaveFormData);
		//sendEmails(leaveFormData, detailsForm);
		return NavigationConstant.EMPLOYEE_BASE
				+ NavigationConstant.APPLY_LEAVE_PAGE;
	}
	
	@ResponseBody
	@RequestMapping(value="calculateDays", method=RequestMethod.POST)
	public int calculateTotalLeaveDays(Date leaveFrom, Date leaveTo)	{
		int totalDays = 0;
		System.out.println("=======================================>>>");
		System.out.println("leaveFrom  :"+leaveFrom);
		System.out.println("leaveTo  :"+leaveTo);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String date1 = sdf.format(leaveFrom);
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
		String date2 = sdf1.format(leaveTo);
		int startDay = Integer.parseInt(date1.substring(date1.indexOf("/")+1, date1.indexOf("/", date1.indexOf("/")+1)));
		int endDay = Integer.parseInt(date2.substring(date2.indexOf("/")+1, date2.indexOf("/", date2.indexOf("/")+1)));
		
////		System.out.println(endDay);
		int startMonth = Integer.parseInt(date1.substring(0,date1.indexOf("/")));
		int endMonth = Integer.parseInt(date2.substring(0,date2.indexOf("/")));
		
		if(startMonth == endMonth)	{
			totalDays = (endDay - startDay);
		}
		else	{
			totalDays = (30 - startDay) + (30*((endMonth-startMonth)-1))+endDay;
		}
		int noOfHolidays=findNoOfHolidays(leaveFrom, leaveTo);
		System.out.println("no of holidays"+noOfHolidays);
		System.out.println("no of leaves applied "+(totalDays));
		
		if(leaveFrom.equals(leaveTo) && noOfHolidays==1) {
			System.out.println("inside if");
			return 0;
		}
		totalDays = totalDays-noOfHolidays;
		return totalDays;
	}
	
	public int findNoOfHolidays(Date leaveFrom, Date leaveTo)
	{
		
		return iEmployeeService.findNoOfHolidays(leaveFrom, leaveTo); 
	}
	
	public int getReportingManagerId(int empId)
	{
		return iEmployeeService.getReportingManagerId(empId);
	}
	
	public EmployeeDetailsForm getReportingManager(int empId)
	{
		EmployeeDetailsForm detailsForm = new EmployeeDetailsForm();
		detailsForm = iEmployeeService.getReportingManagerForEmployee(empId);
		return detailsForm;
	}
	
	public void sendEmails(EmployeeLeaveRequestForm leaveFormData, EmployeeDetailsForm detailsForm)
	{
		iEmployeeService.sendEmails(leaveFormData, detailsForm);
	}
	
	public String getEmployeeName(int empId)
	{
		return iEmployeeService.getEmployeeName(empId); 
	}
	
	
	//It converts uploaded file to byte array form after it is populated in custom java object
		@InitBinder
		public void initBinder(WebDataBinder binder){
			//to actually be able to convert Multipart instance to byte[] we have
			//to register a custom editor
			binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
			//now spring knows how to handle multipart object and handle them
			// to actually be able to convert Multipart instance to byte[]
			// we have to register a custom editor
	       // now Spring knows how to handle multipart object and convert them
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	       // Create a new CustomDateEditor
	       CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
//	       // Register it as custom editor for the Date type
	       binder.registerCustomEditor(Date.class, editor);
		}

}
