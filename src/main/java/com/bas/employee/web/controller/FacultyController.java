package com.bas.employee.web.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.bas.admin.service.DepartmentService;
import com.bas.admin.service.DesignationService;
import com.bas.admin.web.controller.form.DepartmentForm;
import com.bas.admin.web.controller.form.DesignationForm;
import com.bas.common.constant.BASApplicationConstants;
import com.bas.common.constant.BASMessageConstant;
import com.bas.common.constant.NavigationConstant;
import com.bas.common.constant.PageTitleContant;
import com.bas.common.message.vo.ApplicationMessageVo;
import com.bas.common.util.DateUtils;
import com.bas.common.util.PasswordGenerator;
import com.bas.common.web.controller.form.EmployeeDetailsVO;
import com.bas.employee.service.BasFacultyService;
import com.bas.employee.web.controller.form.DepartmentDesignationReportingVO;
import com.bas.employee.web.controller.form.EmployeeDetailsForm;
import com.bas.employee.web.controller.form.FaculityLeaveMasterVO;
import com.bas.employee.web.controller.form.FacultyForm;
import com.bas.employee.web.controller.form.LoginForm;
import com.bas.employee.web.controller.form.ReportingManagerVO;
import com.bas.soap.service.email.EMailSenderServiceImpl;
import com.bas.soap.service.email.EmailSenderThread;
import com.synergy.bank.employee.service.IEmployeeLMSService;

/**
 * 
 * @author nagendra
 *
 */
@Controller
@Scope("request")
public class FacultyController {

	@Autowired
	@Qualifier("BasFacultyServiceImpl")
	private BasFacultyService basFacultyService;
	
	@Autowired
	@Qualifier("EMailSenderServiceImpl")
	private EMailSenderServiceImpl eMailSenderService;
	
	@Autowired
	@Qualifier("DesignationServiceImpl")
	private DesignationService designationService;
	
	@Autowired
	@Qualifier("DepartmentServiceImpl")
	private DepartmentService departmentService;
	
	@Autowired
	@Qualifier("EmployeeLMSService")
	private IEmployeeLMSService employeeLMSService;
	
	
	@RequestMapping(value="/facultyProfileDetails",method=RequestMethod.GET)
	public  String findFacultyDetailByEmpId(@RequestParam("eid") long empid,Model model){
		EmployeeDetailsVO employeeDetailsVO=employeeLMSService.findEmployeeDetails(empid+"");
		model.addAttribute("employeeDetailsVO", employeeDetailsVO);
		 model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.EMPLOYEE_PROFILE);
		return com.bas.common.constant.NavigationConstant.ADMIN_PREFIX_PAGE+com.bas.common.constant.NavigationConstant.FACULTY_PROFILE_DETAILS_PAGE;
	}
	
	/**
	 * 
	 * PK
	 */
	@RequestMapping(value="/showAllFacultyWithleaveHistory",method=RequestMethod.GET)
	public String showFacultyWithLeaveHistory(Model model,HttpSession session){
	    List<FacultyForm> employeeFormsD=basFacultyService.findAllFaculty();
	    model.addAttribute("applicationMessage", session.getAttribute("applicationMessage"));
	    session.removeAttribute("applicationMessage");
		model.addAttribute("employeeForms", employeeFormsD);
	    model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.LEAVE_HISTORY);
		return com.bas.common.constant.NavigationConstant.ADMIN_PREFIX_PAGE+com.bas.common.constant.NavigationConstant.SHOW_REGISTEREDFACULTY_WITH_LEAVEHISTORY_PAGE;
	}
	
	@RequestMapping(value="/searchFacultyDetail",method=RequestMethod.GET)
	public String searchFacultyDetail(Model model,HttpSession session){
	    List<FacultyForm> employeeFormsD=basFacultyService.findAllFaculty();
	    model.addAttribute("applicationMessage", session.getAttribute("applicationMessage"));
	    session.removeAttribute("applicationMessage");
		model.addAttribute("employeeForms", employeeFormsD);
	    model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.EMPLOYEE_DETAIL);
		return com.bas.common.constant.NavigationConstant.ADMIN_PREFIX_PAGE+com.bas.common.constant.NavigationConstant.SEARCH_FACULTY_DETAIL_PAGE;
	}
	
	
	
	@RequestMapping(value="/images",method=RequestMethod.GET)
	public void showImage(@RequestParam("id") String id, HttpServletResponse response) throws IOException {
		response.setContentType("image/jpg");
		byte[] photo = basFacultyService.findImgById(id);
		if (photo != null) {
			//System.out.println("found photo");
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write(photo);
			outputStream.flush();
			outputStream.close();
		}
	}
	
	@RequestMapping(value="/showAllFaculty",method=RequestMethod.GET)
	public String showFacultyDatas(@RequestParam(value="employeeStatus",required=false,defaultValue="YES") String employeeStatus,Model model){
		if("In Active".equalsIgnoreCase(employeeStatus)){
			employeeStatus="NO";
		}else{
			employeeStatus="YES";
		}
		List<FacultyForm> employeeFormsD=basFacultyService.findAllFacultyByStatus(employeeStatus);
		
		System.out.println(employeeFormsD);
		model.addAttribute("employeeFormsD", employeeFormsD);
		model.addAttribute("pemployeeStatus",employeeStatus);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.ALL_FACULTY_PAGE);
		return com.bas.common.constant.NavigationConstant.ADMIN_PREFIX_PAGE+com.bas.common.constant.NavigationConstant.SHOW_REGISTEREDFACULTY_PAGE;
	}
	
	
	@ModelAttribute("managerList")
	public List<String> getReportingManagerList() {
		List<FaculityLeaveMasterVO> reportingManagerList = new ArrayList<FaculityLeaveMasterVO>();
		reportingManagerList=basFacultyService.getReportingManagerList();
		List<String> managerList=new ArrayList<String>();
		for (FaculityLeaveMasterVO faculityLeaveMasterVO : reportingManagerList) {
			managerList.add(faculityLeaveMasterVO.getName()+ "-" + faculityLeaveMasterVO.getId()+"-"+faculityLeaveMasterVO.getDesignation());
		}
		return managerList;
	}

	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String employeeProfile(Model model,HttpSession session) {
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		if(loginForm!=null){
			ReportingManagerVO reportingManagerVO=basFacultyService.findReportingManagerByEmpId(loginForm.getEid());	
			model.addAttribute("reportingManagerVO", reportingManagerVO);
		}
        FacultyForm facultyFormEmpty= new FacultyForm();
 		FacultyForm facultyForm= basFacultyService.showEmployee(loginForm.getEid());
 		model.addAttribute("facultyForms", facultyForm );
        model.addAttribute("depList", basFacultyService.selectDepartments());
 		model.addAttribute("desigList", basFacultyService.selectDesignations());
 		model.addAttribute("emplyeeTypeList", basFacultyService.selectEmployeeType());
 		model.addAttribute("emplyeeCategoryList", basFacultyService.selectEmployeeCategory());
		model.addAttribute("reportingManagerList", basFacultyService.selectReportingManagers());
		  model.addAttribute("bloodGroupList", basFacultyService.selectBloodGroups());
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.EMPLOYEE_PROFILE_PAGE);
		model.addAttribute("employeeData",facultyForm);
		model.addAttribute("facultyForms",facultyFormEmpty);
		return NavigationConstant.EMPLOYEE_PREFIX_PAGE
				+ NavigationConstant.PROFILE;
	}
	
	
	@RequestMapping(value = "/profilePicUpload", method = RequestMethod.POST)
	public String profilePicUploadPost(FacultyForm facultyForm,Model model,HttpSession session) {
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		facultyForm.setId(Long.parseLong(loginForm.getEid()));
		basFacultyService.updateProfilePic(facultyForm);
		model.addAttribute("ApplicationMessage","Your profile pic has been updated successfully , thank you.");
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.APP_CONFIRMATION_PAGE);
		return NavigationConstant.COMMON_PREFIX_PAGE+NavigationConstant.SUCCESS_STATUS_PAGE;
	}
	
	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public String employeeProfilePost(@ModelAttribute("facultyForms") FacultyForm facultyForm,HttpServletRequest request,Model model,HttpSession session) {
		String reportingManager=facultyForm.getReportingManager();
		/*String paddress=request.getParameter("paddress");
		facultyForm.setPaddress(paddress);*/
		if(reportingManager!=null){
			String reportingManagerId=reportingManager.split("-")[1];
			facultyForm.setReportingManager(reportingManagerId);
		}
		facultyForm.setDom(DateUtils.getCurrentTimeIntoTimestamp());
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		facultyForm.setId(Long.parseLong(loginForm.getEid()));
		basFacultyService.updateFaculty(facultyForm);
		model.addAttribute("ApplicationMessage","Your profile is updated successfully , thank you.");
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.EMPLOYEE_HOME_TITLE);
		return NavigationConstant.EMPLOYEE_PREFIX_PAGE+NavigationConstant.EMPLOYEE_HOME_PAGE;
	}

	
	/**
	 *  The method used to validate user profile
	 * @param name
	 * @param email
	 * @param mobile
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/validateProfile", method = RequestMethod.GET)
	public @ResponseBody String validateProfile(String name,String email,String mobile,Model model) {
		if(basFacultyService.checkEmployeeProfileByNameAndMobile(mobile,name,email))
		return "exist";
		else
		return "notexist";
	}
	
	@RequestMapping(value = "/addEmployees", method = RequestMethod.GET)
	public String addEmployee(Model model) {
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.ADD_EMPLOYEE_PAGE);
		model.addAttribute("depList", basFacultyService.selectDepartmentsAsMap());
		model.addAttribute("desigList", basFacultyService.selectDesignations()); 
		model.addAttribute("reportingManagerList", basFacultyService.selectReportingManagers());
		FacultyForm facultyForm=new FacultyForm();
		model.addAttribute("facultyForm", facultyForm);
		//model.addattribute("desglist", service.getdesignation)
		return NavigationConstant.ADMIN_PREFIX_PAGE
				+ NavigationConstant.ADD_EMPLOYEES;
	}
	
	/**
	 * 
	 * @param facultyForm
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addEmployees", method = RequestMethod.POST)
	public @ResponseBody String addEmployeePost(@ModelAttribute("facultyForm") FacultyForm facultyForm ,Model model,HttpSession session) {
		if(basFacultyService.checkEmployeeProfileByNameAndMobile(facultyForm.getPhoneNumber(),facultyForm.getName(),facultyForm.getEmail())){
			return "This employee name "+facultyForm.getName()+" with mobile number "+facultyForm.getPhoneNumber()+" and email "+facultyForm.getEmail()+" already exist in the database!";
		}
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		String loggedInUsername=loginForm.getName();
		facultyForm.setDiploma(loggedInUsername);
		facultyForm.setDoe(DateUtils.getCurrentJavaDate());
		facultyForm.setDom(DateUtils.getCurrentJavaDate());
		//Write code to generate userid and password
		PasswordGenerator passwordGenerator = new PasswordGenerator(6);
		char password[]=passwordGenerator.get();
		String strPassword=new String(password);
		facultyForm.setPassword(strPassword);
		facultyForm.setRole(BASApplicationConstants.EMPLOYEE_ROLE);
		String newuserid=basFacultyService.createEmployeeAccount(facultyForm);
		EmailSenderThread emailSenderThread=new EmailSenderThread(eMailSenderService,facultyForm.getEmail(),"Hey! your userid ="+newuserid+"  and  password is ...."+strPassword,BASMessageConstant.EMAIL_RESET_PASSWORD_SUBJECT);
		emailSenderThread.start();
		System.out.println("--0--------------------------------------------------------------------------------------------------"+newuserid);
		//model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.ADD_EMPLOYEE_PAGE);
		int value=Integer.parseInt(newuserid);
		if(value==0)
		{
			return "Sorry this record is already exist in database..";
		}
		else{
			return "Employee has been create into database sucessfully..";
		}
		/*return NavigationConstant.ADMIN_PREFIX_PAGE
				+ NavigationConstant.SUCCESS_PAGE;*/
	}

	@RequestMapping(value="/register",method=RequestMethod.GET) 
	public String showRegistrationPage(Model model){
		//I am creating an instance of FacultyForm
		FacultyForm facultyForm=new FacultyForm();
		model.addAttribute("facultyCommand", facultyForm);
		return com.bas.common.constant.NavigationConstant.FACULTY_PREFIX_PAGE+com.bas.common.constant.NavigationConstant.FACULTY_REGISTRATION_PAGE;
	}

	@RequestMapping(value="/registerOne",method=RequestMethod.POST)
	public String registerFacultySubmit(@ModelAttribute("facultyCommand") FacultyForm facultyForm,Model model){
		basFacultyService.persistFaculty(facultyForm);
		return com.bas.common.constant.NavigationConstant.COMMON_PREFIX_PAGE+com.bas.common.constant.NavigationConstant.LOGIN_PAGE;
	}
/*	@RequestMapping(value="/profile",method=RequestMethod.GET)  
	public String showRegistrationPagec(Model model){
		//I am creating an instance of FacultyForm
//		FacultyForm facultyForm=new FacultyForm();
//		model.addAttribute("facultyCommand", facultyForm);
		return com.bas.common.constant.NavigationConstant.FACULTY_PREFIX_PAGE+com.bas.common.constant.NavigationConstant.PROFILE;
	}*/

	@RequestMapping(value="/after",method=RequestMethod.POST)
	public String registerFacultySubmitv(@ModelAttribute("facultyCommand") FacultyForm facultyForm,Model model){
		basFacultyService.persistFaculty(facultyForm);
		return com.bas.common.constant.NavigationConstant.COMMON_PREFIX_PAGE+com.bas.common.constant.NavigationConstant.LOGIN_PAGE;
	}

	
	@RequestMapping(value = "/searchEmployeeRama", method = RequestMethod.GET)
	public @ResponseBody List<String> showEmployee(HttpServletRequest request) {	
		System.out.println("Searching");
		List<String> abc = basFacultyService.searchEmployee(request.getParameter("term"));
		System.out.println(abc);
		System.out.println(request.getParameter("term"));
		return 	abc;
				 
				 
	}
	
	
	
	
//	 
//	@RequestMapping(value="/retreiveEmployeeForAdminspecific",method=RequestMethod.POST)
//	public @ResponseBody List<EmployeeShowForm> showAttendStatus(@RequestParam(value="requiredDatas") String employeeName,HttpSession session,
//			Model model)
//	{	
// 		
//		List<EmployeeShowForm> facultyAttendStatusVOlist=basFacultyService.findFacultyByNamespeci(employeeName);
//		 
//		return facultyAttendStatusVOlist;
//	}

		
//		model.addAttribute("employeeform", employeeform);
//		return com.bas.common.constant.NavigationConstant.ADMIN_PREFIX_PAGE+com.bas.common.constant.NavigationConstant.SHOW_REGISTEREDFACULTY_PAGE;
//		com.bas.common.constant.NavigationConstant.SHOW_REGISTEREDFACULTY_PAGE;
//	}
/*	@RequestMapping(value="/showAllFacultyd",method=RequestMethod.POST)
	//Model model = it is used to carry data from controller to the jsp
	public String showFacultyDataGet(Model model){
		List<EmployeeShowForm> employeeFormsD=basFacultyService.findAllEmployee();
		model.addAttribute("employeeFormsD", employeeFormsD);
		return com.bas.common.constant.NavigationConstant.ADMIN_PREFIX_PAGE+com.bas.common.constant.NavigationConstant.SHOW_REGISTEREDFACULTY_PAGE;
	}*/

	@RequestMapping(value="/deleteFaculty",method=RequestMethod.GET)
	//Model model = it is used to carry data from controller to the jsp
	public String deleteFacultyDb(HttpServletRequest request,Model model){
		//delete the data from database as name comming from UI
		String name=request.getParameter("pname");
		basFacultyService.deletetFaculty(name);
		//Here I am fetching once again updated data
		/*List<FacultyEntity> facultyEntities=basFacultyDao.findAllFaculty();
		model.addAttribute("facultyData", facultyEntities);*/
		return "redirect:showFaculty";
	}
	
	
	@RequestMapping(value="/activeEmployeeByEmpId",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ApplicationMessageVo activeEmployeeByEmpId(@RequestParam("empid") String empid,Model model){
		String result=basFacultyService.activeEmployeeByEmpId(empid);
		ApplicationMessageVo applicationMessageVo=new ApplicationMessageVo();
		applicationMessageVo.setStatus("success");
		applicationMessageVo.setMessage(result);
		return applicationMessageVo;
	}
	
	@RequestMapping(value="/deleteEmployeeByEmpId",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ApplicationMessageVo deleteEmployeeByEmpId(@RequestParam("empid") String empid,Model model){
		String result=basFacultyService.deletetEmployeeById(empid);
		ApplicationMessageVo applicationMessageVo=new ApplicationMessageVo();
		applicationMessageVo.setStatus("success");
		applicationMessageVo.setMessage(result);
		return applicationMessageVo;
	}

   @RequestMapping(value="/findEmplyeeByAjax",method=RequestMethod.GET)
   public @ResponseBody FacultyForm findEmplyeeByAjax(@RequestParam("empid") String empid,Model model)  {
	  // System.out.println("hello you Empid is ="+empid);
	   FacultyForm  facultyForm =basFacultyService.findEmplyeeByAjax(empid);
	   model.addAttribute("facultyForm", facultyForm);
	   return facultyForm;
   }
   
   @RequestMapping(value="/findDeptDesignationReporting" ,method=RequestMethod.GET)
   public @ResponseBody DepartmentDesignationReportingVO findDeptDesignationReporting ()   {
	   DepartmentDesignationReportingVO departmentDesignationReportingVO=new DepartmentDesignationReportingVO();
	   List<DepartmentForm> departmentList=departmentService.findDepartments();
	   for(DepartmentForm departmentForm:departmentList){
		   departmentDesignationReportingVO.getDepartmentList().add(departmentForm.getDepartmentShortName());
	   }
	   List<DesignationForm> designationList=designationService.findDesignations();
	   for(DesignationForm designationForm:designationList){
		   departmentDesignationReportingVO.getDesignationList().add(designationForm.getDesignationName());
	   }
		List<FaculityLeaveMasterVO> reportingManagerList =basFacultyService.getReportingManagerList();
		for (FaculityLeaveMasterVO faculityLeaveMasterVO : reportingManagerList) {
			departmentDesignationReportingVO.getReportingManagerList().add(faculityLeaveMasterVO.getName()+ "-" + faculityLeaveMasterVO.getId()+"-"+faculityLeaveMasterVO.getDesignation());
		}
	   return departmentDesignationReportingVO;
   }
   
   @RequestMapping(value="/updateEmplyeeByAjax",method=RequestMethod.GET)
   public @ResponseBody String updateEmplyeeByAjax(@RequestParam("empid") String id,@RequestParam("name") String name,@RequestParam("sex") String sex,@RequestParam("type") String type,@RequestParam("category") String category,@RequestParam("email") String email,@RequestParam("phoneNumber") String phoneNumber,@RequestParam("dob") String dob,@RequestParam("doj") String doj,@RequestParam("designation") String designation,@RequestParam("department") String department,@RequestParam("reportingManager") String reportingManager)
   {
	   FacultyForm  facultyForm =new FacultyForm();
	   facultyForm.setId(Integer.parseInt(id));
	   facultyForm.setName(name);
	   facultyForm.setSex(sex);
	   facultyForm.setType(type);
	   facultyForm.setCategory(category);
	   facultyForm.setEmail(email);
	   facultyForm.setPhoneNumber(phoneNumber);
	   facultyForm.setDob(dob);
	   facultyForm.setDoj(doj);
	   facultyForm.setDesignation("Associate Professor");
	   facultyForm.setDepartment("CSE");
	   facultyForm.setReportingManager("yes");
	   String msg=basFacultyService.updateEmplyeeByAjax(facultyForm);
	   System.out.println("massage is = "+msg);
	   return msg;
   }


	//Model model = it is used to carry data from controller to the jsp
	//String name=request.getParameter("pname");
	@RequestMapping(value="/editFaculty",method=RequestMethod.POST)
	public String showEditFaculty(@ModelAttribute("facultyCommand") FacultyForm facultyForm,Model model){
		String result=basFacultyService.updateFaculty(facultyForm);
		return "redirect:showFaculty";
	}

	@RequestMapping(value="/editFaculty",method=RequestMethod.GET)
	public String showEditFaculty(@RequestParam("pname") String name,Model model){
		FacultyForm facultyForm=basFacultyService.findFacultyByName(name);
		model.addAttribute("facultyCommand", facultyForm);
		return com.bas.common.constant.NavigationConstant.FACULTY_PREFIX_PAGE+com.bas.common.constant.NavigationConstant.FACULTY_EDIT_PAGE;
	}
 
	

	@RequestMapping(value="/renderImage",method=RequestMethod.GET)
	public void renderPhoto(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String empid=request.getParameter("empid");
		response.setContentType("image/jpg");
		byte[] image=basFacultyService.findPhotoByEmpId(empid);
		if(image!=null){
			response.getOutputStream().write(image);
		}
	}
	
	
	
	@RequestMapping(value="/editShowAllEmployeeByAjax",method=RequestMethod.GET)
	public String showFacultyDatass(@RequestParam(value="employeeStatus",required=false,defaultValue="YES") String employeeStatus,Model model){
	
		if("In Active".equalsIgnoreCase(employeeStatus)){
			employeeStatus="NO";
		}else{
			employeeStatus="YES";
		}
		List<FacultyForm> employeeFormsD=basFacultyService.findAllFacultyByStatus(employeeStatus);
		
		System.out.println(employeeFormsD);
		model.addAttribute("employeeFormsD", employeeFormsD);
		model.addAttribute("pemployeeStatus",employeeStatus);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.ALL_FACULTY_PAGE);
		return com.bas.common.constant.NavigationConstant.ADMIN_PREFIX_PAGE+com.bas.common.constant.NavigationConstant.SHOW_REGISTEREDFACULTY_PAGE;
	}
	


	//It converts your upload file into byte array form after it
	//it is populated in customer java object

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// to actually be able to convert Multipart instance to byte[]
		// we have to register a custom editor
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
		// now Spring knows how to handle multipart object and convert them
	}
	
	
	@InitBinder
	public void initBinder1(WebDataBinder binder) {
		// now Spring knows how to handle multipart object and convert them
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //Create a new CustomDateEditor
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        //Register it as custom editor for the Date type
		binder.registerCustomEditor(Date.class, editor);
	}
}
