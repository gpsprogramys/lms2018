package com.bas.admin.web.report.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bas.admin.service.AdminBasReportService;
import com.bas.admin.web.controller.form.AttendanceHeaderVO;
import com.bas.admin.web.controller.form.FaculityAttendanceReportVO;
import com.bas.admin.web.controller.form.FaculityAttendanceReportVOWrapper;
import com.bas.admin.web.controller.form.FaculityDailyAttendanceReportVO;
import com.bas.admin.web.controller.form.FaculityDailyAttendanceReportVOWrapper;
import com.bas.common.constant.NavigationConstant;
import com.bas.common.util.DateUtils;
import com.bas.employee.service.FaculityBasService;
import com.bas.employee.web.controller.form.LoginForm;
import com.bas.employee.web.controller.form.ManualAttendanceVO;

/**
 * 
 * @author nagendra.yadav
 *  This is for jasper report ...................................
 *  ................................................................
 *  
 * 
 */
@Controller
public class AdminReportController {

	@Autowired
	@Qualifier("AdminBasReportServiceImpl")
	private AdminBasReportService adminBasReportService;

	@Autowired
	@Qualifier("FaculityBasServiceImpl")
	private FaculityBasService faculityBasService;
	
	/**
	 * Method which is used to generate PDF report
	 * 
	 * @param loginSession
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "faculityAttReport", method = RequestMethod.GET)
	public ModelAndView generateFaculityAttReport(HttpSession session) {
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		String sempid=loginForm.getEid();
		 
		int empid=sempid==null?0:Integer.parseInt(sempid);
		List<FaculityAttendanceReportVO> faculityAttendanceReportVOs = adminBasReportService
				.findAllAttendanceByEmpId(empid);
		
		for (FaculityAttendanceReportVO attendanceReportVO : faculityAttendanceReportVOs) {
			
			if (attendanceReportVO.getIntime() != null)
				attendanceReportVO.setIntimeString(attendanceReportVO
						.getIntime().toString());
			
			if (attendanceReportVO.getCdate() != null)
				attendanceReportVO.setCdateString(DateUtils.convertDateIntoString(attendanceReportVO
						.getCdate()));

			if (attendanceReportVO.getOuttime() != null)
				attendanceReportVO.setOuttimeString(attendanceReportVO
						.getOuttime().toString());
		}

		FaculityAttendanceReportVOWrapper attendanceReportVOWrapper = new FaculityAttendanceReportVOWrapper();
		attendanceReportVOWrapper
				.setAttendanceReportVOs(faculityAttendanceReportVOs);
		AttendanceHeaderVO attendanceHeaderVO = faculityBasService
				.findAttendanceHeaderByFid(empid);
		String currentDate=DateUtils.getCurrentDate();
		attendanceReportVOWrapper.setCdate(currentDate);
		attendanceReportVOWrapper.setCphoto("shphoto.jpg");
		attendanceReportVOWrapper.setDepartment(attendanceHeaderVO
				.getDepartment());
		attendanceReportVOWrapper.setDesignation(attendanceHeaderVO
				.getDesignation());
		attendanceReportVOWrapper.setEmpId(empid);
		attendanceReportVOWrapper.setLogo("logo.jpg");
		attendanceReportVOWrapper.setName(attendanceHeaderVO.getName());

		List<FaculityAttendanceReportVOWrapper> faculityAttendanceReportVOWrapper = new ArrayList<FaculityAttendanceReportVOWrapper>();
		faculityAttendanceReportVOWrapper.add(attendanceReportVOWrapper);
	
		JRDataSource jrDataSource = new JRBeanCollectionDataSource(
				faculityAttendanceReportVOWrapper);
		// In order to use Spring's built-in Jasper support,
		// We are required to pass our datasource as a map parameter
		// parameterMap is the Model of our application
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("datasource", jrDataSource);
		// pdfReport is the View of our application
		// This is declared inside the /WEB-INF/jasper-views.xml
		ModelAndView modelAndView = new ModelAndView("faculityAttendanceReport", parameterMap);
		// Return the View and the Model combined
		return modelAndView;
	}

	
	/**
	 * Method which is used to generate PDF report
	 * 
	 * @param loginSession
	 * @param model	
	 * @return
	 */
	@RequestMapping(value = "faculityDailyAttReport", method = RequestMethod.GET)
	public ModelAndView execute() {
		ModelAndView modelAndView = null;
		List<com.bas.employee.web.controller.form.ManualAttendanceVO> manualAttendanceVOs = adminBasReportService
				.findFaculityAttendanceForToday();
		List<FaculityDailyAttendanceReportVO> faculityDailyAttendanceReportVOs = new ArrayList<FaculityDailyAttendanceReportVO>();
		for (ManualAttendanceVO attendanceVO : manualAttendanceVOs) {
			FaculityDailyAttendanceReportVO attendanceReportVO = new FaculityDailyAttendanceReportVO();

			//attendanceReportVO.setEid(attendanceVO.getEid());
			//attendanceReportVO.setCdate(DateUtils.convertDateIntoString(attendanceVO.getCdate()));
			attendanceReportVO.setDepartment(attendanceVO.getDepartment());
			attendanceReportVO.setDesignation(attendanceVO.getDesignation());
			attendanceReportVO.setDetail(attendanceVO.getDetail());
			attendanceReportVO.setIntime(attendanceVO.getIntime().toString());
			attendanceReportVO.setIntimestatus(attendanceVO.getIntimestatus());
			attendanceReportVO.setName(attendanceVO.getName());
			attendanceReportVO.setOuttime(attendanceVO.getOuttime().toString());
			attendanceReportVO
					.setOuttimestatus(attendanceVO.getOuttimestatus());
			attendanceReportVO.setType(attendanceVO.getType());

			// adding into the list
			faculityDailyAttendanceReportVOs.add(attendanceReportVO);
		}

		FaculityDailyAttendanceReportVOWrapper faculityDailyAttendanceReportVOWrapper = new FaculityDailyAttendanceReportVOWrapper();
		// setting the list here
		faculityDailyAttendanceReportVOWrapper
				.setDailyDailyAttendanceReportVOs(faculityDailyAttendanceReportVOs);
		String currentDate=DateUtils.getCurrentDate();
		faculityDailyAttendanceReportVOWrapper.setCdate(currentDate);
		faculityDailyAttendanceReportVOWrapper.setCollegeLogo("D:/mock.jpg");
		faculityDailyAttendanceReportVOWrapper.setCttiaLogo("D:/uuuuu.jpg");

		List<FaculityDailyAttendanceReportVOWrapper> faculityDailyAttendanceReportVOWrappersList = new ArrayList<FaculityDailyAttendanceReportVOWrapper>();
		faculityDailyAttendanceReportVOWrappersList
				.add(faculityDailyAttendanceReportVOWrapper);

		JRDataSource jrDataSource = new JRBeanCollectionDataSource(
				faculityDailyAttendanceReportVOWrappersList);
		// In order to use Spring's built-in Jasper support,
		// We are required to pass our datasource as a map parameter
		// parameterMap is the Model of our application
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("datasource", jrDataSource);
		// pdfReport is the View of our application
		// This is declared inside the /WEB-INF/jasper-views.xml
		modelAndView = new ModelAndView("pdfReport", parameterMap);
		// Return the View and the Model combined
		return modelAndView;
	}

	
}
