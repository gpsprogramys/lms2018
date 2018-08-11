package com.bas.employee.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bas.admin.web.controller.form.AttendanceHeaderVO;
import com.bas.employee.dao.FaculityBasDao;
import com.bas.employee.service.FaculityBasService;
import com.bas.employee.web.controller.form.EmployeeAttendanceSummaryVO;

/**
 * 
 * @author astha
 *
 */
@Service("FaculityBasServiceImpl")
@Transactional(propagation=Propagation.REQUIRED)
public class FaculityBasServiceImpl implements FaculityBasService{

    @Autowired
    @Qualifier("FaculityBasDaoImpl")
    private FaculityBasDao basDao;
	
	
    @Override
    public List<EmployeeAttendanceSummaryVO> findLastTenAttendance(int eid) {
        return basDao.findLastTenAttendance(eid);
      
    }
    
    @Override
    public AttendanceHeaderVO findAttendanceHeaderByFid(int fid){
         return basDao.findAttendanceHeaderByFid(fid);    
     }

    @Override
    public String markFaculityAttendance(int fid) {
      return  basDao.markFaculityAttendance(fid);
    }

	@Override
	public String authUser(String userid, String password) {
		return basDao.authUser(userid, password);
	}

	@Override
	public String changePassword(String empid, String newpassword) {
		return basDao.changePassword(empid, newpassword);
	}

   
}
