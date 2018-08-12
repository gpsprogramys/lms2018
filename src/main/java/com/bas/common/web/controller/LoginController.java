package com.bas.common.web.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bas.admin.email.service.IResetPasswordEmailService;
import com.bas.admin.email.service.Mail;
import com.bas.common.constant.BASApplicationConstants;
import com.bas.common.constant.BASMessageConstant;
import com.bas.common.constant.NavigationConstant;
import com.bas.common.constant.PageTitleContant;
import com.bas.common.dao.LoginDao;
import com.bas.common.util.PasswordGenerator;
import com.bas.employee.dao.entity.LoginEntity;
import com.bas.employee.service.BasFacultyService;
import com.bas.employee.web.controller.form.EmployeeDetailsForm;
import com.bas.employee.web.controller.form.LoginForm;
import com.bas.employee.web.controller.form.SubjectAlternativeArrangementsVO;
import com.bas.soap.service.email.EMailSenderServiceImpl;
import com.bas.soap.service.email.EmailSenderThread;
import com.synergy.bank.employee.service.IEmployeeLMSService;

@Controller //@Component
@Scope("request")
public class LoginController {
	
    private static final Log logger = LogFactory.getLog(LoginController.class);
	
	@Autowired
	@Qualifier("LoginDaoImpl")
	private LoginDao loginDao;
	
	
	public LoginController(){
		System.out.println("_@)@))Starting_________Web Application___________________");
	}
	
	
	@Autowired
	@Qualifier("ResetPasswordEmailService")
	private IResetPasswordEmailService resetPasswordEmailService;
	
	@Autowired
	@Qualifier("EMailSenderServiceImpl")
	private EMailSenderServiceImpl eMailSenderService;
	
	@Autowired
	@Qualifier("EmployeeLMSService")
	private IEmployeeLMSService 	 employeeLMSService;

	@Autowired
	@Qualifier("BasFacultyServiceImpl")
   private BasFacultyService basFacultyService;
   
	
	@RequestMapping(value="/test",method=RequestMethod.GET)
	public String test(){
		return NavigationConstant.ADMIN_PREFIX_PAGE+"test";
	}
	
	@RequestMapping(value="/managementHome",method=RequestMethod.GET)
	public String managementHome(Model model,HttpSession session){
		String empid=((LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA)).getEid();
		int pendingLeaveCount=employeeLMSService.findTotaPendingLeaveCount("manegementApproval",empid);
		model.addAttribute("pendingLeaveCount", pendingLeaveCount+"");
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.MANAGEMENT_HOME_TITLE);
		return NavigationConstant.MANAGEMENT_PREFIX_PAGE+NavigationConstant.MANAGEMENT_HOME_PAGE;
	}
	
	@RequestMapping(value="/adminHome",method=RequestMethod.GET)
	public String adminHome(Model model,HttpSession session){
		String empid=((LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA)).getEid();
		int pendingLeaveCount=employeeLMSService.findTotaPendingLeaveCount("hrApproval",empid);
		model.addAttribute("pendingLeaveCount", pendingLeaveCount+"");
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.ADMIN_HOME_TITLE);
		return NavigationConstant.ADMIN_PREFIX_PAGE+NavigationConstant.ADMIN_HOME_PAGE;
	}
	
	
	@RequestMapping(value="/employeeHome",method=RequestMethod.GET)
	public String employeeHome(Model model,HttpSession session){
		String empid=((LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA)).getEid();
		int pendingLeaveCount=employeeLMSService.findTotaPendingLeaveCount("managerApproval",empid);
		model.addAttribute("pendingLeaveCount", pendingLeaveCount+"");
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.EMPLOYEE_HOME_TITLE);
		return NavigationConstant.EMPLOYEE_PREFIX_PAGE+NavigationConstant.EMPLOYEE_HOME_PAGE;
	}
	
	@RequestMapping(value="/facultyHome",method=RequestMethod.GET)
	public String facultyHome(){
		return NavigationConstant.FACULTY_PREFIX_PAGE+NavigationConstant.FACULTY_HOME_PAGE;
	}
	
	
	@RequestMapping(value="/changePassword",method=RequestMethod.GET)
	public String changePassword(Model model){
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.RESET_PASSWORD_PAGE);
		return NavigationConstant.COMMON_PREFIX_PAGE+NavigationConstant.CHANGE_PASSWORD_PAGE;
	}
	
	@RequestMapping(value="/changePassword",method=RequestMethod.POST)
	public String changePassword(@RequestParam("newPassword") String newPassword,Model model,HttpSession session,RedirectAttributes redirectAttributes){
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);	
		String userid=loginForm.getUserid();
		String email=loginDao.changePasswordByUserId(userid, newPassword);
		if(email!=null){
			redirectAttributes.addAttribute("ApplicationMessage", "Hey! your password is changed successfully! , please check your email.");
			Mail mail=new Mail();
			mail.setMailFrom("hietams@gmail.com");
			mail.setPassword(newPassword);
			mail.setMailTo(email);
			mail.setMailSubject("Regarding reset password through portal.");
			mail.setName(loginForm.getName());
			mail.setTemplateName("reset-password.vm");
			resetPasswordEmailService.sendResetPasswordEmail(mail);
		}else{
			redirectAttributes.addFlashAttribute("ApplicationMessage", "Sorry! your password could not be changed , please contact to system admin!");
		}
		String role=((LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA)).getRole();	
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.RESET_PASSWORD_PAGE);
		String nextPage="redirect:/employee/employeeHome";
		if("admin".equalsIgnoreCase(role)){
			 nextPage="redirect:/admin/adminHome";
		}else if("management".equalsIgnoreCase(role)){
			 nextPage="redirect:/admin/managementHome";
		}
		return nextPage;
	}
	
	
	@RequestMapping(value="/resetPassword",method=RequestMethod.GET)
	public String resetPassword(Model model){
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.RESET_PASSWORD_PAGE);
		return NavigationConstant.COMMON_PREFIX_PAGE+NavigationConstant.RESET_PASSWORD_PAGE;
	}
	
	
	/**
	 * This is the page which will change the password and email will be sent for the same! 
	 * @param email
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/resetPassword",method=RequestMethod.POST)
	public String resetPasswordPost(@RequestParam("email") String email,Model model) {
		//Here we have to write logic to generate the password
		//and same we have to update into the database.........
		PasswordGenerator passwordGenerator = new PasswordGenerator(6);
		char password[]=passwordGenerator.get();
		String strPassword=new String(password);
		loginDao.updatePassword(email, strPassword);
		
		EmailSenderThread emailSenderThread=new EmailSenderThread(eMailSenderService,email,"Hey! your new password is ...."+strPassword,BASMessageConstant.EMAIL_RESET_PASSWORD_SUBJECT);
		emailSenderThread.start();
		
		//eMailSenderService.sendMail("nagendra.uuuuuuuuuuu@gmail.com", email, BASMessageConstant.EMAIL_RESET_PASSWORD_SUBJECT,"Hey! your new password is ...."+strPassword);	
		model.addAttribute("error","Your Password have been reset successfully, Please check  your email.");
		return NavigationConstant.COMMON_PREFIX_PAGE+NavigationConstant.LOGIN_PAGE;
	}
	
		
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logout(HttpSession session,Model model){
		session.invalidate();
		model.addAttribute("error","You have successfully logout from application!!!");
		return NavigationConstant.COMMON_PREFIX_PAGE+NavigationConstant.LOGIN_PAGE;
	}
	
	@RequestMapping(value="/bashome",method=RequestMethod.GET)
	public String showHomePage(){	
		return NavigationConstant.COMMON_PREFIX_PAGE+NavigationConstant.COMMON_HOME_PAGE;		
	}
	
	@RequestMapping(value="/auth",method=RequestMethod.GET)
	public String showLoginPage(Model model){	
		/*List<FacultyForm> facultyForms=basFacultyService.findAllFacultyByStatus("yes");
		model.addAttribute("facultyForms", facultyForms);*/
		return NavigationConstant.COMMON_PREFIX_PAGE+NavigationConstant.LOGIN_PAGE;
	
	}
	
	@RequestMapping(value="/validateUserByAjax",method=RequestMethod.GET)
	public @ResponseBody String validateUserByAjax(@RequestParam("username") String username,
			@RequestParam("password") String password){
		LoginEntity loginEntity=loginDao.validateUser(username, password);
		if(loginEntity!=null){
			return "valid";
		}else{
			return "invalid";
		}
	}
	
	private String procedeToHomePage(LoginEntity loginEntity,LoginForm form,Model model){
		String nextPage=NavigationConstant.EMPLOYEE_PREFIX_PAGE+NavigationConstant.EMPLOYEE_HOME_PAGE;
		List<SubjectAlternativeArrangementsVO> alternativeArrangements= employeeLMSService.findAlternateArrangementPeriodFarFaculty(form.getUserid());
		model.addAttribute("alternativeArrangements",alternativeArrangements);
		if(loginEntity!=null &&  loginEntity.getRole()!=null && "user".equals(loginEntity.getRole())){
			form.setHomePage(BASApplicationConstants.EMPLOYEE+"/"+NavigationConstant.EMPLOYEE_HOME_PAGE);
			int pendingLeaveCount=employeeLMSService.findTotaPendingLeaveCount("managerApproval",loginEntity.getEid());
			model.addAttribute("pendingLeaveCount", pendingLeaveCount+"");
			model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.EMPLOYEE_HOME_TITLE);
			nextPage= NavigationConstant.EMPLOYEE_PREFIX_PAGE+NavigationConstant.EMPLOYEE_HOME_PAGE;
		}else if(loginEntity!=null &&  loginEntity.getRole()!=null && "admin".equals(loginEntity.getRole())){
			int pendingLeaveCount=employeeLMSService.findTotaPendingLeaveCount("hrApproval",loginEntity.getEid());
			model.addAttribute("pendingLeaveCount", pendingLeaveCount+"");
			model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.ADMIN_HOME_TITLE);
			form.setHomePage(BASApplicationConstants.ADMIN+"/"+NavigationConstant.ADMIN_HOME_PAGE);
			nextPage= NavigationConstant.ADMIN_PREFIX_PAGE+NavigationConstant.ADMIN_HOME_PAGE;
			
	    }else if(loginEntity!=null &&  loginEntity.getRole()!=null && "userRM".equals(loginEntity.getRole())){
	    	int pendingLeaveCount=employeeLMSService.findTotaPendingLeaveCount("managerApproval",loginEntity.getEid());
			model.addAttribute("pendingLeaveCount", pendingLeaveCount+"");
	    	model.addAttribute("role", "userRM");
	    	model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.ADMIN_HOME_TITLE);
	    	form.setHomePage(BASApplicationConstants.EMPLOYEE+"/"+NavigationConstant.EMPLOYEE_HOME_PAGE);
	    	nextPage= NavigationConstant.EMPLOYEE_PREFIX_PAGE+NavigationConstant.EMPLOYEE_HOME_PAGE;
	    }
	    else if(loginEntity!=null &&  loginEntity.getRole()!=null && "management".equals(loginEntity.getRole())){
	    	int pendingLeaveCount=employeeLMSService.findTotaPendingLeaveCount("manegementApproval",loginEntity.getEid());
			model.addAttribute("pendingLeaveCount", pendingLeaveCount+"");
	    	model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.MANAGEMENT_HOME_TITLE);
	    	form.setHomePage(BASApplicationConstants.MANAGEMENT+"/"+NavigationConstant.MANAGEMENT_HOME_PAGE);
	    	nextPage="redirect:/admin/managementHome"; 
	    	//return NavigationConstant.MANAGEMENT_PREFIX_PAGE+NavigationConstant.MANAGEMENT_HOME_PAGE;
	    }
	    else{
	    	nextPage=NavigationConstant.FACULTY_PREFIX_PAGE+NavigationConstant.FACULTY_HOME_PAGE; 
	    }
		//Checking employee is login first time or not
		if(loginEntity!=null && BASApplicationConstants.DEFAULT_EMAIL_ID.equalsIgnoreCase(loginEntity.getEmail())) {
			EmployeeDetailsForm detailsForm=new EmployeeDetailsForm();
			detailsForm.setId(Integer.parseInt(loginEntity.getEid()));
			EmployeeDetailsForm employeeDetailsForm=employeeLMSService.findEmployeeById(detailsForm);
			model.addAttribute("dob", employeeDetailsForm.getDob());
			model.addAttribute("mobile", employeeDetailsForm.getPhoneNumber());
			model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.PROFILE_COMPLETE_TITLE);
			nextPage= NavigationConstant.COMMON_PREFIX_PAGE+NavigationConstant.PROFILE_COMPLETE_PAGE;
		}
		return nextPage;
	}

	/**
	 * 
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/auth",method=RequestMethod.POST)
	public String validateUser(@RequestParam("username") String username,@RequestParam("password") String password,HttpSession session,Model model){
		//ApplicationContext applicationContext=new ClassPathXmlApplicationContext("bas-att-spring-context.xml");
		//LoginDao loginDao=(LoginDao)applicationContext.getBean("LoginDaoImpl");
		LoginEntity loginEntity=loginDao.validateUser(username, password);
		if(loginEntity!=null){
			LoginForm form=new LoginForm();
			BeanUtils.copyProperties(loginEntity, form);
			session.setAttribute(NavigationConstant.USER_SESSION_DATA, form);
			return procedeToHomePage(loginEntity,form,model);
		}else {
			model.addAttribute("error","Username and password are not valid!!!");
			 model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.PROFILE_COMPLETE_TITLE);
			return NavigationConstant.COMMON_PREFIX_PAGE+NavigationConstant.LOGIN_PAGE;
		}
	}
	
	@RequestMapping(value="/findAllAlternativeClassDetails",method=RequestMethod.GET)
	public @ResponseBody List<SubjectAlternativeArrangementsVO> findAllAlternativeClassDetails(Model model,HttpSession session){
		String empid=((LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA)).getEid();
		List<SubjectAlternativeArrangementsVO> alternativeArrangements= employeeLMSService.findAlternateArrangementPeriodFarFaculty(empid);
		return alternativeArrangements;
	}
	
	@RequestMapping(value="/findAllAlternativeClassDetailsForHrHod",method=RequestMethod.GET)
	public @ResponseBody List<SubjectAlternativeArrangementsVO> findAllAlternativeClassDetailsForHrHod(@RequestParam("empid") String id){
		List<SubjectAlternativeArrangementsVO> alternativeArrangements= employeeLMSService.findAllAlternativeClassDetailsForHrHod(id);
		return alternativeArrangements;
	}
	
	@RequestMapping(value="/updateStatusForAlternateClass",method=RequestMethod.GET)
	public @ResponseBody SubjectAlternativeArrangementsVO updateStatusForAlternateClass(@RequestParam("id") String id,@RequestParam("status") String status){
		String msg=employeeLMSService.updateStatusForAlternateClass(id,status);
		SubjectAlternativeArrangementsVO subjectAlternativeArrangementsVO=new SubjectAlternativeArrangementsVO();
		subjectAlternativeArrangementsVO.setStatus(msg);
		return subjectAlternativeArrangementsVO;
	}
	
	@RequestMapping(value="/completeProfile",method=RequestMethod.POST)
	public  String completeProfilePost(@RequestParam("email") String email,@RequestParam("mobile") String mobile,@RequestParam("dob") String dob,@RequestParam("password") String password,HttpSession session, Model model){
		//String msg=employeeLMSService.updateStatusForAlternateClass(id,status);
		 LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		 String eid=loginForm.getEid();
		 employeeLMSService.updateBasicProfile(eid, dob, mobile, password, email);
		 String nextPage="redirect:/"+loginForm.getHomePage();
		 return nextPage;
	}
	
	
}

