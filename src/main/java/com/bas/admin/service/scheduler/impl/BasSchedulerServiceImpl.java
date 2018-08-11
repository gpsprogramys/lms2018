package com.bas.admin.service.scheduler.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bas.admin.service.BasSchedulerService;
import com.bas.admin.web.controller.form.FaculityDailyAttendanceReportVO;
import com.bas.admin.web.controller.form.FacultyMonthlyAttendanceStatusVO;
import com.bas.admin.web.controller.form.HolidayEntryForm;
import com.bas.common.util.DateUtils;
import com.bas.employee.service.BasFacultyService;
import com.bas.employee.web.controller.form.FaculityLeaveMasterVO;
import com.bas.employee.web.controller.form.FacultyForm;
import com.bas.employee.web.controller.form.FacultyWorkingDaysVO;
import com.bas.employee.web.controller.form.ManualAttendanceVO;

/**
 *
 * @author java
 *
 */
@Service("BasSchedulerServiceImpl")
@Transactional
public class BasSchedulerServiceImpl implements BasSchedulerService {

	@Autowired
	@Qualifier("BasFacultyServiceImpl")
	private BasFacultyService basFacultyService;
	

	@Override
	public String computeNoOfDaysForSalaryInAMonth() throws ParseException {
		int noOfWorkingDays = DateUtils.getNoOfDaysForCurrentMonth();
		int currYear = DateUtils.getCurrentYear();
		int month = new Date().getMonth()+1;
		Calendar c = Calendar.getInstance();		
	    List<String> empids = new ArrayList<String>();
	    List<FacultyForm> list = basFacultyService.findAllFaculty();
	    for(FacultyForm faculty : list){
		List<FaculityDailyAttendanceReportVO> facultyAttendStatusVO = basFacultyService.getAttStatusByDate(currYear+"-"+month+"-01",currYear+"-"+month+"-"+noOfWorkingDays,""+faculty.getId());
		int noOfDaysWorked = facultyAttendStatusVO.size();
		List<HolidayEntryForm> holidays = basFacultyService.getHolidays(month,currYear);		
		List<FacultyMonthlyAttendanceStatusVO> n = new ArrayList<FacultyMonthlyAttendanceStatusVO>();
		int noOfHolidays = holidays.size();
		//List<FaculityLeaveMasterVO> approvalStatus = basFacultyService.getLeaveApproval(NavigationConstant.USER_SESSION_DATA);
		
		int noOfLwp = 0;
		List<FaculityLeaveMasterVO> leave_history = basFacultyService.LeaveHistory(currYear+"-"+month+"-01",currYear+"-"+month+"-"+noOfWorkingDays,""+faculty.getId());
		int noOfApprovedLeaves = leave_history.size();
		for(FaculityLeaveMasterVO lh : leave_history){
			for(int i=0; i< lh.getTotalDays(); i++){
				if(lh.getLeaveType().equals("LWP")){
					noOfLwp++;
				}
			}
		}
		ManualAttendanceVO empDetails = basFacultyService.findEmployeeDataForAttendance(""+faculty.getId());
		    if(noOfDaysWorked==0 && noOfApprovedLeaves==0) {
		    	noOfLwp=noOfWorkingDays;
		    }	
			FacultyWorkingDaysVO facultyWorkingDaysVO = new FacultyWorkingDaysVO();
			facultyWorkingDaysVO.setEmpid(Integer.parseInt(""+faculty.getId()));
			facultyWorkingDaysVO.setNoOfDaysWorked(noOfDaysWorked);
			facultyWorkingDaysVO.setMonth(DateUtils.getCurrentCalendarDate());
			facultyWorkingDaysVO.setNoOfApprovedLeaves(noOfApprovedLeaves);
			facultyWorkingDaysVO.setNoOfHolidays(noOfHolidays);
			facultyWorkingDaysVO.setTotalworkingdays(noOfWorkingDays);
			facultyWorkingDaysVO.setDepartment(empDetails.getDepartment());
			facultyWorkingDaysVO.setNoOfLwp(noOfLwp);
			basFacultyService.saveToFacultyWorkingTable(facultyWorkingDaysVO);
	    }
			return null;	
	}
}
