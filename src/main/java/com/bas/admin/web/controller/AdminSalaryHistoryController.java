package com.bas.admin.web.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.bas.admin.service.AdminFacultyService;
import com.bas.admin.web.controller.form.AdminSalaryMasterVO;
import com.bas.common.constant.NavigationConstant;
import com.bas.employee.web.controller.form.FacultyForm;
import com.bas.employee.web.controller.form.LoginForm;

/**
 * @author Sid
 *
 *
 */
@Controller
@Scope("request")
public class AdminSalaryHistoryController {


	@Autowired
	@Qualifier("BasAdminServiceImpl")
	private AdminFacultyService adminFacultyService;
    
	static List<AdminSalaryMasterVO> adminSalaryMasterVOslist;
	
	@RequestMapping(value="/adminSalaryHistory",method=RequestMethod.GET)
	public String showSalaryHistoryGet(@ModelAttribute("adminSalCommand") AdminSalaryMasterVO adminSalaryMasterVO,Model model,HttpSession session) {	
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);		
		return NavigationConstant.ADMIN_PREFIX_PAGE+NavigationConstant.ADMINSALARY_HISTORY_PAGE;
	}

	@RequestMapping(value="/viewAdminSalMonthajx",method=RequestMethod.POST)
	public String showSalaryHistoryMonthAjax(@ModelAttribute("adminSalCommand") AdminSalaryMasterVO adminSalaryMasterVO,Model model,HttpSession session,HttpServletRequest request){				
		String flag = request.getParameter("pflag");
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		AdminSalaryMasterVO adminSalaryMasterVO2 = new AdminSalaryMasterVO();		
		if(!flag.equals("yes")==true){
			try {
				adminSalaryMasterVOslist=adminFacultyService.findSalaryHistory(loginForm.getEid()+"");
				model.addAttribute("adminSalaryMasterVOslist", adminSalaryMasterVOslist);
				model.addAttribute("messageid","Salary Computation Successful !");
			} catch (Exception e) {
				model.addAttribute("adminSalaryMasterVOslist", adminSalaryMasterVOslist);
				model.addAttribute("messageid","Computation UnSuccessful:Already Exists for the Month!");
				e.printStackTrace();
			}
		}
		else if(flag.equals("yes")==true){
			org.apache.poi.ss.usermodel.Workbook wb = new HSSFWorkbook();
			org.apache.poi.ss.usermodel.Sheet salhistorySheet = wb.createSheet("SalaryHistorySheet");				
			Row headerRow = salhistorySheet.createRow(0);				
			Cell eidcell = headerRow.createCell(0);
			Cell monthcell = headerRow.createCell(1);
			Cell daysWrkedcell=headerRow.createCell(2); 
			Cell noleavescell=headerRow.createCell(3);
			Cell totworkdayscell=headerRow.createCell(4); 
			Cell salpaidcell=headerRow.createCell(5); 
			Cell commentcell=headerRow.createCell(6);
			int row=1;
			for(int k=0;k<adminSalaryMasterVOslist.size();k++){
				int eid = adminSalaryMasterVOslist.get(k).getEid();
				Date monthexcel = adminSalaryMasterVOslist.get(k).getMonth();
				int daysworked=adminSalaryMasterVOslist.get(k).getDaysworked();
				int noleaves=adminSalaryMasterVOslist.get(k).getNoleaves();
				int totworkdaysexcel=adminSalaryMasterVOslist.get(k).getTotworkdays();
				int salpaid=adminSalaryMasterVOslist.get(k).getSalpaid();
				String comment=adminSalaryMasterVOslist.get(k).getComment();				

				Row dataRow = salhistorySheet.createRow(row);
				Cell eidNamecell = dataRow.createCell(0);
				eidNamecell.setCellValue(eid);			

				Cell dayswrkedNamecell = dataRow.createCell(1);
				dayswrkedNamecell.setCellValue(monthexcel);
				Cell nolvsNamecell = dataRow.createCell(2);
				nolvsNamecell.setCellValue(daysworked);
				Cell totwrkdaysNamecell = dataRow.createCell(3);
				totwrkdaysNamecell.setCellValue(noleaves);
				Cell salpdNamecell = dataRow.createCell(4);
				salpdNamecell.setCellValue(totworkdaysexcel);
				Cell commentNamecell = dataRow.createCell(5);
				commentNamecell.setCellValue(salpaid);
				Cell monthNamecell = dataRow.createCell(6);
				monthNamecell.setCellValue(comment);

				row = row + 1; //create new row for other entries in list.
			}

			String outputDirPath = "D:\\SalHistorySheet.xls";
			FileOutputStream fileOut;
			try {
				fileOut = new FileOutputStream(outputDirPath,false);
				wb.write(fileOut);
				fileOut.close();
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			model.addAttribute("adminSalaryMasterVOslist", adminSalaryMasterVOslist);

		}
		return NavigationConstant.ADMIN_PREFIX_PAGE+NavigationConstant.ADMINSALARY_HISTORY_PAGEDISP;
	}		


	@RequestMapping(value="/renderImageAdmin",method=RequestMethod.GET)
	public void renderPhotoAdmin(HttpServletRequest request,HttpServletResponse response) throws IOException{
		int eid=Integer.parseInt(request.getParameter("eid"));
		response.setContentType("image/jpg");
		byte[] image=adminFacultyService.findEmpPhotoByEid(eid);
		if(image!=null){
			response.getOutputStream().write(image);
		}
	}


	@RequestMapping(value="/viewSpecificSalary",method=RequestMethod.POST)
	public String viewSpecificSalaryGet(@ModelAttribute("adminSalCommand") AdminSalaryMasterVO adminSalaryMasterVO,Model model,HttpServletRequest request) {				

		String name=request.getParameter("viewspecsal");				

		if(name!=null) {
			String tokens[]=name.split("-");
			String empid=tokens[0];
			adminSalaryMasterVOslist = adminFacultyService.findSpecificSal(empid);
			model.addAttribute("adminSalaryMasterVOslist", adminSalaryMasterVOslist);
		}else{
			String empid="all";
			adminSalaryMasterVOslist = adminFacultyService.findSpecificSal(empid);
			model.addAttribute("adminSalaryMasterVOslist", adminSalaryMasterVOslist);			
		}						
		return NavigationConstant.ADMIN_PREFIX_PAGE+NavigationConstant.ADMINSALARY_HISTORY_PAGEDISP;

	}	
	
	
	
	@ModelAttribute("department")
	public List<String> departments() {
		List<String> deptlist = new ArrayList<String>();
		deptlist.add("All");
		deptlist.add("CS");
		deptlist.add("IT");
		deptlist.add("ME");
		deptlist.add("EC");
		return deptlist;
	}
	
	@RequestMapping(value="/showWorkDaysTillnow",method=RequestMethod.GET)
	public String showWorkdaysTillnow(@ModelAttribute("adminSalCommand") AdminSalaryMasterVO adminSalaryMasterVO,Model model,HttpSession session) {	
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);	
		
		/*java.util.Calendar cal1 = new java.util.GregorianCalendar();
	    java.util.Calendar cal2 = new java.util.GregorianCalendar();
		int days = (cal2.getTime() - cal1.getTime()) / (1000 * 60 * 60 * 24); */	
		
		Calendar c = new GregorianCalendar();
	   // c.set(Calendar.HOUR_OF_DAY, 0); 
	    //c.set(Calendar.MINUTE, 0);
	    //c.set(Calendar.SECOND, 0);
	    Date d1 = c.getTime();
			
		model.addAttribute("ttlwrkdays", 27);
		model.addAttribute("tddate", d1);
		//adminSalaryMasterVOslist=adminFacultyService.findSalaryHistory(loginForm.getEid()+"");
		model.addAttribute("adminSalaryMasterVOslist", adminSalaryMasterVOslist);
		
		return NavigationConstant.ADMIN_PREFIX_PAGE+NavigationConstant.ADMINSALARY_HISTORY_WRKDAYS;
	}
	
	
	@RequestMapping(value="/showWorkDaysTillnow",method=RequestMethod.POST)
	public String showWorkdaysTillnowPost(@ModelAttribute("adminSalCommand") AdminSalaryMasterVO adminSalaryMasterVO,Model model,HttpSession session,HttpServletRequest request) {	
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);	
		
		Calendar c = new GregorianCalendar();
	   // c.set(Calendar.HOUR_OF_DAY, 0); 
	    //c.set(Calendar.MINUTE, 0);
	    //c.set(Calendar.SECOND, 0);
	    Date d1 = c.getTime();			
		model.addAttribute("ttlwrkdays", 27);
		model.addAttribute("tddate", d1);
		
		String cat = request.getParameter("categorynm");	
		adminSalaryMasterVOslist=adminFacultyService.findWrkDays(adminSalaryMasterVO.getDepartment(),cat);
		model.addAttribute("adminSalaryMasterVOslist", adminSalaryMasterVOslist);		
		return NavigationConstant.ADMIN_PREFIX_PAGE+NavigationConstant.ADMINSALARY_HISTORY_WRKDAYS;
	}	

	@RequestMapping(value="/viewsalbydate",method=RequestMethod.GET)
	public String showSalbyDate(@ModelAttribute("adminSalCommand") AdminSalaryMasterVO adminSalaryMasterVO,Model model,HttpSession session) {	
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		Calendar c = new GregorianCalendar();
	   // c.set(Calendar.HOUR_OF_DAY, 0); 
	    //c.set(Calendar.MINUTE, 0);
	    //c.set(Calendar.SECOND, 0);
	    Date d1 = c.getTime();
			
		model.addAttribute("ttlwrkdays", 27);
		model.addAttribute("tddate", d1);
		//adminSalaryMasterVOslist=adminFacultyService.findSalaryHistory(loginForm.getEid()+"");
		model.addAttribute("adminSalaryMasterVOslist", adminSalaryMasterVOslist);
		
		return NavigationConstant.ADMIN_PREFIX_PAGE+NavigationConstant.ADMINSALARY_HISTORY_BYDATE;
	}



	@RequestMapping(value="/viewSpecificSalajax",method=RequestMethod.POST)
	public @ResponseBody String viewSpecificSalajaxPost(Model model,HttpSession session) {		
		List<FacultyForm> facultyFormslist=adminFacultyService.findallSalInfo();
		model.addAttribute("facultyFormslist", facultyFormslist);				

		StringBuilder stringBuilder=new StringBuilder();
		for(FacultyForm facultyForm:facultyFormslist){
			stringBuilder.append(facultyForm.getId()+"-"+facultyForm.getName()+",");
		}
		return stringBuilder.toString();
	}


	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// to actually be able to convert Multipart instance to byte[]
		// we have to register a custom editor
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
		// now Spring knows how to handle multipart object and convert them
	}

}





