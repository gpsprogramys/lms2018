package com.bas.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bas.admin.dao.AdminBasReportDao;
import com.bas.admin.dao.entity.FaculityDailyAttendanceReportEntity;
import com.bas.admin.dao.entity.HolidayEntryEntity;
import com.bas.admin.service.AdminBasReportService;
import com.bas.admin.web.controller.form.FaculityAttendanceReportVO;
import com.bas.admin.web.controller.form.FaculityDailyAttendanceReportVO;
import com.bas.employee.dao.BasFacultyDao;
import com.bas.employee.web.controller.form.ManualAttendanceVO;

/**
 * 
 * @author xxxxxxxxxx
 * 
 */
@Transactional
@Service("AdminBasReportServiceImpl")
public class AdminBasReportServiceImpl implements AdminBasReportService {

	@Autowired
	@Qualifier("AdminBasReportDaoImpl")
	private AdminBasReportDao adminBasReportDao;
	
	@Autowired
	@Qualifier("BasFacultyDaoImpl")
	private BasFacultyDao basFacultyDao;
	
	@Override
	public List<FaculityDailyAttendanceReportVO> getAllFacultyForManualAttendance(){
		List<FaculityDailyAttendanceReportEntity> facultyForManualAttendanceEntityList = adminBasReportDao.getAllFacultyForManualAttendance();
		List<FaculityDailyAttendanceReportVO> getFacultyForManualAttendanceList = new ArrayList<FaculityDailyAttendanceReportVO>();
		for(FaculityDailyAttendanceReportEntity fDARE: facultyForManualAttendanceEntityList)
		{
			FaculityDailyAttendanceReportVO facultyForManualAttendance = new FaculityDailyAttendanceReportVO();
			BeanUtils.copyProperties(fDARE, facultyForManualAttendance);
			getFacultyForManualAttendanceList.add(facultyForManualAttendance);
		}
		System.out.println("Service " + getFacultyForManualAttendanceList);
		return getFacultyForManualAttendanceList;
	}
	
	@Override
	public String submitAllFacultyForManualAttendance(String[] presentEmployeeID){
		adminBasReportDao.submitAllFacultyForManualAttendance(presentEmployeeID);
		return "Service is done";
	}


	@Override
	public List<ManualAttendanceVO> findFaculityAttendanceForToday() {
		return adminBasReportDao.findFaculityAttendanceForToday();
	}

	@Override
	public List<FaculityAttendanceReportVO> findAllAttendanceByEmpId(int empid) {
		return adminBasReportDao.findAllAttendanceByEmpId(empid);
	}

	@Override
	public List<HolidayEntryEntity> getHolidayCategoryList(String month, String year) {
		// TODO Auto-generated method stub
		return basFacultyDao.getHolidayCategoryList(month, year, null);
	}
	
}
