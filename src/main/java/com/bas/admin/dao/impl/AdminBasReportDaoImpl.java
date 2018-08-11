package com.bas.admin.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bas.admin.dao.AdminBasReportDao;
import com.bas.admin.dao.entity.FaculityDailyAttendanceReportEntity;
import com.bas.admin.dao.entity.OrganizationTimeEntity;
import com.bas.admin.web.controller.form.FaculityAttendanceReportVO;
import com.bas.common.util.DateUtils;
import com.bas.employee.web.controller.form.ManualAttendanceVO;

@Repository("AdminBasReportDaoImpl")
public class AdminBasReportDaoImpl extends JdbcDaoSupport implements AdminBasReportDao {
	

	public static void main(String[] args) {
			int p=Runtime.getRuntime().availableProcessors();
			System.out.println("availableProcessors = "+p);
			System.out.println("__________work____________");
	}
	
	@Autowired
	@Qualifier("sdatasource")
	public void setDataSourceInSuper(DataSource dataSource) {
		super.setDataSource(dataSource);
	}
	
	
	
	
	@Override
	public List<FaculityDailyAttendanceReportEntity> getAllFacultyForManualAttendance(){

		//SimpleJdbcCall jdbcCall=new SimpleJdbcCall(super.getJdbcTemplate()).withProcedureName("cool");
		
		String query = "select f.id as fid, f.name,f.designation,f.department,f.doj from emp_db as f";
		String query2 = "select intime, outtime from organisation_time order by date desc LIMIT 1";
		List<FaculityDailyAttendanceReportEntity> manualAttendanceList = null;
		//OrganizationTimeEntity orgTime = (OrganizationTimeEntity) getJdbcTemplate().query(query2, new BeanPropertyRowMapper<OrganizationTimeEntity>(OrganizationTimeEntity.class));
		OrganizationTimeEntity orgTime2 = (OrganizationTimeEntity) getJdbcTemplate().queryForObject(query2, new BeanPropertyRowMapper(OrganizationTimeEntity.class));
		try{
			manualAttendanceList = getJdbcTemplate().query(query, new BeanPropertyRowMapper<FaculityDailyAttendanceReportEntity>(FaculityDailyAttendanceReportEntity.class));
			for (FaculityDailyAttendanceReportEntity fDARE: manualAttendanceList)
			{
				fDARE.setIntime(orgTime2.getIntime());
				fDARE.setOuttime(orgTime2.getOuttime());
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return manualAttendanceList;
	}
	

	@Transactional
	@Override
	public String submitAllFacultyForManualAttendance(String[] presentEmployeeID){
		//load current timing
		String currentDate=DateUtils.getCurrentCalendarDate();
		String query2 = "select intime, outtime from organisation_time order by date desc LIMIT 1";
		OrganizationTimeEntity orgTime2 = (OrganizationTimeEntity) getJdbcTemplate().queryForObject(query2, new BeanPropertyRowMapper(OrganizationTimeEntity.class));
		try {
			for(String cid:presentEmployeeID){
			Object[] qdata = new Object[] { cid,
					currentDate,
					orgTime2.getIntime(),orgTime2.getOuttime(),"Normal",
					DateUtils.getCurrentDateInSQLFormat(), "Normal","Normal","MANUAL",
					"FULLDAY", "YES", "Welcome sir", "NotSent", "admin" };
				getJdbcTemplate()
						.update("insert into faculity_att_tab (fid,cdate,intime,outtime,status,dom,intimestatus,outtimestatus,attmode,detail,present,description,alert,entryby) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
								qdata);
			}	
			}catch(Exception ex){
				ex.printStackTrace();
				return "NOTDONE";
			}
		return "persisted";
	}

	
    @Override
    public List<ManualAttendanceVO> findFaculityAttendanceForToday(){
    	String currentDate=DateUtils.getCurrentCalendarDate();
    	//String dquery="select  f.id as eid,f.name,f.department,f.designation,cdate,s.detail,s.intime,s.intimestatus,s.outtime,s.outtimestatus  from emp_db as f,faculity_att_tab as s where f.id=s.fid and cdate='+2013-09-20'";
    	String dquery="select  f.id as eid,f.name,f.department,f.designation,cdate,s.detail,s.intime,s.intimestatus,s.outtime,s.outtimestatus  from emp_db as f,faculity_att_tab as s where f.id=s.fid and cdate='"+currentDate+"'";
    	 List<ManualAttendanceVO> manualAttendanceVOs=getJdbcTemplate().query(dquery,new BeanPropertyRowMapper<ManualAttendanceVO>(ManualAttendanceVO.class));
        return manualAttendanceVOs;
    }

	@Override
	public List<FaculityAttendanceReportVO> findAllAttendanceByEmpId(int empid) {
		 String dquery="select s.cdate,s.intime,s.intimestatus,s.outtime,s.outtimestatus,s.status,s.detail  from faculity_att_tab as s   where s.fid="+empid+"  order by s.cdate asc";
		 List<FaculityAttendanceReportVO> employeeAttendanceSummaryVOs=getJdbcTemplate().query(dquery,new BeanPropertyRowMapper<FaculityAttendanceReportVO>(FaculityAttendanceReportVO.class));
		 return employeeAttendanceSummaryVOs;
	}

}
