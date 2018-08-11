package com.bas.employee.web.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bas.common.constant.NavigationConstant;
import com.bas.common.constant.PageTitleContant;
import com.bas.common.util.DateUtils;
import com.bas.common.web.controller.form.EmployeeLeaveDetailVO;
import com.bas.employee.service.BasFacultyService;
import com.bas.employee.web.controller.form.FaculityLeaveMasterVO;
import com.bas.employee.web.controller.form.FacultyLeaveApprovalVO;
import com.bas.employee.web.controller.form.LoginForm;
import com.bas.hr.service.HrOnePageLeaveHistoryService;
import com.bas.hr.web.controller.model.EmployeeOnePageLeaveHistoryVO;
import com.synergy.bank.employee.service.IEmployeeLMSService;

/**
 * 
 * @author 928282828
 *
 */
@Controller
public class FacultyLeaveHistoryController {


	@Autowired
	@Qualifier("BasFacultyServiceImpl")
	private BasFacultyService basFacultyService;
	
	@Autowired
	@Qualifier("EmployeeLMSService")
	private IEmployeeLMSService iEmployeeService;
	
	@Autowired
	@Qualifier("HrOnePageLeaveHistoryServiceImpl")
	private HrOnePageLeaveHistoryService hrOnePageLeaveHistoryService;
	
	
	@RequestMapping(value = "employeeOnePageLeaveHistory", method = RequestMethod.GET)
	public String employeeOnePageLeaveHistory(Model model, HttpSession session) {
		LoginForm loginForm = (LoginForm) session
					.getAttribute(NavigationConstant.USER_SESSION_DATA);
		String	eid = loginForm.getEid();
		//getCurrentSessionYear 2015-2016
		String currentSessionYear=DateUtils.getCurrentSessionYear();
		String previousSessionYear=DateUtils.getPreviousSessionYear();
		model.addAttribute("currentSessionYear",currentSessionYear);
		model.addAttribute("previousSessionYear",previousSessionYear);
		EmployeeLeaveDetailVO employeeLeaveDetailVO = iEmployeeService.findEmployeeLeaveDetail(eid);
		model.addAttribute("employeeLeaveDetailVO",employeeLeaveDetailVO);
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.EMPLOYEES_ONE_PAGE_LEAVE_HISTORY_TITLE);
		List<EmployeeOnePageLeaveHistoryVO> employeeOnePageLeaveHistoryVOList = hrOnePageLeaveHistoryService.findEmployeeOnePageLeaveHistory(eid, "2016-02-01","2016-02-29");
		model.addAttribute("employeeOnePageLeaveHistoryVOList",employeeOnePageLeaveHistoryVOList);
		return NavigationConstant.EMPLOYEE_PREFIX_PAGE
				+ NavigationConstant.FIND_EMPLOYEE_ONE_PAGE_LEAVE_HISTORY;
	}
	
	
	/**
	 * PK
	 * @throws IOException 
	 */
	@RequestMapping(value="/exportLeaveHistory",method=RequestMethod.GET)
	public String exportLeaveHistory(Model model,@RequestParam(value="id",required=false) String id,HttpSession session) throws IOException {
		if(id==null) {
			LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
			id=loginForm.getEid();
		}
		List<FaculityLeaveMasterVO> faculityLeaveMasterVOslist=basFacultyService.findLeaveHistory(id);
		EmployeeLeaveDetailVO employeeLeaveDetailVO=iEmployeeService.findEmployeeLeaveDetail(id);
		model.addAttribute("employeeLeaveDetailVO", employeeLeaveDetailVO);
		model.addAttribute("faculityLeaveMasterVOslist", faculityLeaveMasterVOslist);
		return NavigationConstant.LEAVE_HISTORY_EXCEL;
	}
	
	/**
	 * PK
	 * @throws IOException 
	 */
	@RequestMapping(value="/leaveHistory",method=RequestMethod.GET)
	public String showLeaveHistoryById(Model model,@RequestParam(value="id",required=false) String id,HttpSession session,final RedirectAttributes redirectAttributes) throws IOException {
		if(id==null) {
			LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
			id=loginForm.getEid();
		}
		List<FaculityLeaveMasterVO> faculityLeaveMasterVOslist=basFacultyService.findLeaveHistory(id);
		EmployeeLeaveDetailVO employeeLeaveDetailVO=iEmployeeService.findEmployeeLeaveDetail(id);
		if(employeeLeaveDetailVO==null) {
			//showAllFacultyWithleaveHistory
			//redirectAttributes.addAttribute("applicationMessage", "Sorry leave balance is not available for this employee id = "+id+", please contact to admin");
			session.setAttribute("applicationMessage", "Sorry leave balance is not available for this employee id = "+id+", please contact to admin");
			return "redirect:/admin/showAllFacultyWithleaveHistory";
		}
		model.addAttribute("employeeLeaveDetailVO", employeeLeaveDetailVO);
		model.addAttribute("faculityLeaveMasterVOslist", faculityLeaveMasterVOslist);
	    model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.LEAVE_HISTORY);
		return NavigationConstant.LMS_PREFIX_PAGE+NavigationConstant.LEAVE_HISTORY_DETAILS_PAGE;
	}
	@RequestMapping(value="/employeeLeaveDetailByAjax",method=RequestMethod.GET)
	@ResponseBody public List employeeLeaveDetailByAjax(@RequestParam("empLeaveDetail") String empRequestId)
	{
		FacultyLeaveApprovalVO facultyLeaveAppliedDetail=basFacultyService.empAppliedLeaveDetail(empRequestId);//data comming  from emp_applied_leave_requests_tbl 
		FacultyLeaveApprovalVO facultyLeaveApprovedDetail=basFacultyService.empApprovedLeaveDetail(empRequestId);//data comming  from emp_leave_history
		List<FacultyLeaveApprovalVO> facultyLeaveDetailList=new ArrayList<FacultyLeaveApprovalVO>();
		facultyLeaveDetailList.add(facultyLeaveAppliedDetail);
		
		//System.out.println("Request Id -----------------------> "+facultyLeaveApprovedDetail.getRequestID());
		facultyLeaveDetailList.add(facultyLeaveApprovedDetail);
		return facultyLeaveDetailList;
	}
	
	@RequestMapping(value="/image",method=RequestMethod.GET)
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
	
	@RequestMapping(value="/filter",method=RequestMethod.GET)
	@ResponseBody public List<FaculityLeaveMasterVO> filterByDate(Model model,@RequestParam("empId") String empId, @RequestParam("date1") String d1, @RequestParam("date2") String d2,HttpSession session) throws IOException {
		//String[] str = empId.split(":");
		//String eid = str[1].trim();
		/*LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		String eid=loginForm.getEid();*/
		String eid=empId;
		
		SimpleDateFormat myFormat1 = new SimpleDateFormat("yyyy-MM-dd");	
		SimpleDateFormat formatJSP1 = new SimpleDateFormat("MM/dd/yyyy");
		String reformattedStr1 = null;
		try {
			reformattedStr1 = myFormat1.format(formatJSP1.parse(d1));
		} catch (ParseException e){
			e.printStackTrace();}

		SimpleDateFormat myFormat2 = new SimpleDateFormat("yyyy-MM-dd");	
		SimpleDateFormat formatJSP2 = new SimpleDateFormat("MM/dd/yyyy");
		String reformattedStr2 = null;
		try {
			reformattedStr2 = myFormat2.format(formatJSP2.parse(d2));
		} catch (ParseException e){
			e.printStackTrace();}

		List<FaculityLeaveMasterVO> faculityLeaveMasterVOslist=basFacultyService.sortLeaveByDate(reformattedStr1, reformattedStr2, eid);
		for(FaculityLeaveMasterVO faculityLeaveMasterVO : faculityLeaveMasterVOslist){
			 if(faculityLeaveMasterVO.getLeaveFrom()!=null)
			 faculityLeaveMasterVO.setSdateFrom( DateUtils.convertDateIntoString(faculityLeaveMasterVO.getLeaveFrom()));
			 if(faculityLeaveMasterVO.getLeaveTo()!=null)
			 faculityLeaveMasterVO.setSdateTo( DateUtils.convertDateIntoString(faculityLeaveMasterVO.getLeaveTo()));
			// System.out.println("leave from : "+t.getLeaveFrom()+" leave to :"+faculityLeaveMasterVO.getLeaveTo()+"---------"+t.getLeaveDates());
		}
		return faculityLeaveMasterVOslist;
		//System.out.println("-------sfsfsfs----------"+eid);
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
             // to actually be able to convert Multipart instance to byte[]
             // we have to register a custom editor
             binder.registerCustomEditor(byte[].class,
                                new ByteArrayMultipartFileEditor());
             // now Spring knows how to handle multipart object and convert them
    }
	//End
	
}
