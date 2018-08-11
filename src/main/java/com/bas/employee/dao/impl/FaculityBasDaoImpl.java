package com.bas.employee.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.bas.admin.web.controller.form.AttendanceHeaderVO;
import com.bas.common.constant.FaculityQuery;
import com.bas.common.util.DateUtils;
import com.bas.employee.dao.FaculityBasDao;
import com.bas.employee.web.controller.form.AttendanceHeaderBeanRowMapper;
import com.bas.employee.web.controller.form.EmployeeAttendanceSummaryVO;

/**
 * 
 * 
 * @author astha
 * 
 */
@Repository("FaculityBasDaoImpl")
public class FaculityBasDaoImpl extends JdbcDaoSupport implements
		FaculityBasDao {

	/**
	 * This is setting datasource in super class
	 * 
	 * @param dataSource
	 */
	@Autowired
	@Qualifier("sdatasource")
	public void setDataSourceInSuper(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	
    @Override
    public List<EmployeeAttendanceSummaryVO> findLastTenAttendance(int eid) {
        List<EmployeeAttendanceSummaryVO> employeeAttendanceSummaryVOs = getJdbcTemplate().query(
				FaculityQuery.SELECT_LAST_TEN_ATTENDANCE,
				new BeanPropertyRowMapper<EmployeeAttendanceSummaryVO>(EmployeeAttendanceSummaryVO.class),eid);
		return employeeAttendanceSummaryVOs;
    }

    @Override
    public AttendanceHeaderVO findAttendanceHeaderByFid(int fid) {
    AttendanceHeaderVO attendanceHeaderVO = getJdbcTemplate().queryForObject(
				FaculityQuery.SELECT_ATTENDANCE_SUMMARY_HEADER,
				new AttendanceHeaderBeanRowMapper(),fid);
		return attendanceHeaderVO;
    }

    @Override
    public String markFaculityAttendance(int fid) {
       //match this fid is valid or not
        Integer integer=getJdbcTemplate().queryForObject("select id from emp_db where id=?",new Object[]{fid},Integer.class);
        if(integer==null || integer==0){
          return "NOTMATCHED";
        }else{
            //Here we have two use cases 
            //this is entry first entry for today for the employee
            Object[] qdata=new Object[]{fid,DateUtils.getCurrentDateInSQLFormat(),DateUtils.getCurrentTime(),"Normal",DateUtils.getCurrentDateInSQLFormat(),"Normal","AUTO","NORMAL","YES","Welcome sir","Sent","system"};
            try {
                getJdbcTemplate().update("insert into faculity_att_tab (fid,cdate,intime,status,dom,intimestatus,attmode,detail,present,description,alert,entryby) values(?,?,?,?,?,?,?,?,?,?,?,?)",qdata);
            }catch(Exception exception){
                //updating the table when faculity is logout last time from the system
                String uquery="update faculity_att_tab set outtime=?,outtimestatus=?,detail=? where fid=? and cdate=?";
                Object[] udata=new Object[]{DateUtils.getCurrentTime(),"Normal","Normal",fid,DateUtils.getCurrentDateInSQLFormat()};  
                getJdbcTemplate().update(uquery,udata);
            }
        }    
        return "MATCH";
    }
         
    
        /**
         *  This method is used for testing the code only
         * @param args 
         */
        public static void main(String[] args) {
		/*FaculityBasDao basService=(FaculityBasDao)SpringServices.getService("FaculityBasDaoImpl");
		String result=basService.markFaculityAttendance(555);
		System.out.println("result = "+result);*/
	}

		@Override
		public String authUser(String userid, String password) {
			String query=FaculityQuery.SELECT_USERID_FROM_FACULITY_LOGIN;
			String duserid="";
			try {
				 duserid=getJdbcTemplate().queryForObject(query,new Object[]{userid,password},String.class);
			}catch (EmptyResultDataAccessException e) {
				return "fail";
			}
			return duserid;
		}
		
		@Override
		public String changePassword(String empid, String newpassword) {
			String query=FaculityQuery.UPDATE_PASSWORD_BY_EMP_ID;
			try {
				 int p=getJdbcTemplate().update(query,new Object[]{newpassword,empid});
				 System.out.println("password is changed now = "+p);
			}catch (EmptyResultDataAccessException e) {
				return "fail";
			}
			return "success";
		}
}
