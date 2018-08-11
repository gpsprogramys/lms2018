package com.bas.admin.dao.impl;

import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bas.admin.dao.BasAdminDao;
import com.bas.admin.dao.entity.AdminSalaryMasterEntity;
import com.bas.employee.dao.entity.FaculityLeaveMasterEntity;
import com.bas.employee.dao.entity.FacultyEntity;

/**
 * @author Sid :
 *	Compute values to insert into admin_salary_table. Insert into database and view it.
 */

@Repository("BasAdminDaoImpl")
public class BasAdminDaoImpl extends JdbcDaoSupport implements BasAdminDao{

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
	@Transactional(noRollbackFor=NullPointerException.class)
	public List<AdminSalaryMasterEntity> findSalaryHistory(String empid){

		//Get current date,month,year
		java.util.Date date = new java.util.Date();		
		Calendar cal = Calendar.getInstance();  
		int year = cal.get(Calendar.YEAR);  
		int month = cal.get(Calendar.MONTH)	;	    
		int monthMaxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);	
		cal.set(2014, 7, 1);
		date = cal.getTime();

		//Get holiday entries in holiday table. *Must check if working holiday.
		String cntquery = "SELECT COUNT(*) FROM holiday_entry_tbl AS hol WHERE MONTH(hol.cdate)="+month+"";
		int numHol = super.getJdbcTemplate().queryForObject(cntquery,Integer.class);
		int totworkdays = monthMaxDays - numHol;
		float leavetotal = 0;

		//Get empNos from empNos from emp_leave_history
		String leavetknqry ="SELECT el.empNo FROM emp_leave_history AS el";
		List<Integer> checkeid = super.getJdbcTemplate().queryForList(leavetknqry,Integer.class);

		//Get eids from emp_db 
		String emplistqry = "SELECT e.id FROM emp_db AS e";
		List<FacultyEntity> empidlist  = super.getJdbcTemplate().query(emplistqry, new BeanPropertyRowMapper<FacultyEntity>(FacultyEntity.class));

		/* For all ids in emp_db
		 *  check if faculty has applied for leave
		 *    if yes , compute salary and insert table, else insert accordingly		 * 
		 */
		try {
			for(int i = 0;i<empidlist.size();i++){			
				long eidid = empidlist.get(i).getId();			
				if(checkeid.contains(eidid)==true){
					String leavetknqry2 ="SELECT el.empNo,SUM(el.totalDays) AS totalDays " +
							"FROM emp_leave_history AS el " +
							"WHERE MONTH(el.ldateFrom)="+8+" AND YEAR(el.ldateFrom)=2013 " +
							"AND el.empNo="+eidid+" GROUP BY empNo ORDER BY empNo ASC";
					FaculityLeaveMasterEntity leavemaster = super.getJdbcTemplate().queryForObject(leavetknqry2, new BeanPropertyRowMapper<FaculityLeaveMasterEntity>(FaculityLeaveMasterEntity.class));
					leavetotal = leavemaster.getTotalDays();
					float daysworked = totworkdays - leavetotal;			
					String insquery = "INSERT INTO admin_salary_table VALUES(?,?,?,?,?,?,?,?,?,?,?)";	
					Object[] data = new Object[]{eidid,"2014-06-01",daysworked,leavetotal,totworkdays,1000,"CS","paid in Full","2014-06-01","2014-06-01","null"};
					super.getJdbcTemplate().update(insquery,data);	
					//remove this					
					/*String insquery2 = "INSERT INTO admin_salary_table VALUES(?,?,?,?,?,?,?,?,?,?,?)";	
					Object[] data2 = new Object[]{eidid,"2014-08-01",0,0,0,0,"CS","Pending","2014-08-01","2014-08-01","null"};
					super.getJdbcTemplate().update(insquery2,data2);*/
				}else{				
					leavetotal =0;
					float daysworked = totworkdays - leavetotal;			
					String insquery = "INSERT INTO admin_salary_table VALUES(?,?,?,?,?,?,?,?,?,?,?)";	
					Object[] data = new Object[]{eidid,"2014-06-01",daysworked,leavetotal,totworkdays,1000,"CS","paid in Full","2014-06-01","2014-06-01","null"};
					super.getJdbcTemplate().update(insquery,data);
					//remove this
					/*String insquery2 = "INSERT INTO admin_salary_table VALUES(?,?,?,?,?,?,?,?,?,?,?)";	
					Object[] data2 = new Object[]{eidid,"2014-08-01",0,0,0,0,"CS","Pending","2014-08-01","2014-08-01","null"};
					super.getJdbcTemplate().update(insquery2,data2);*/
				}

			}
		}catch (RuntimeException e) {
			e.printStackTrace();
			String dquery="SELECT sal.empid as eid,sal.month,sal.daysworked,sal.noleaves,sal.totworkdays,sal.salpaid,sal.comment FROM admin_salary_table AS sal ORDER BY sal.month ASC";
			List<AdminSalaryMasterEntity> adminSalaryMasterEntitieslist=super.getJdbcTemplate().query(dquery,new BeanPropertyRowMapper<AdminSalaryMasterEntity>(AdminSalaryMasterEntity.class));	
			return adminSalaryMasterEntitieslist;		
		}

		String dquery="SELECT sal.empid as eid,sal.month,sal.daysworked,sal.noleaves,sal.totworkdays,sal.salpaid,sal.comment FROM admin_salary_table AS sal ORDER BY sal.month ASC";
		List<AdminSalaryMasterEntity> adminSalaryMasterEntitieslist=super.getJdbcTemplate().query(dquery,new BeanPropertyRowMapper<AdminSalaryMasterEntity>(AdminSalaryMasterEntity.class));
		return adminSalaryMasterEntitieslist;
	}






	@Override
	public byte[] findEmpPhotoByEid(int eid) {
		String query = "select * from emp_db where id="+eid+"";
		FaculityLeaveMasterEntity faculityLeaveMasterEntity = new FaculityLeaveMasterEntity();
		try {
			faculityLeaveMasterEntity = (FaculityLeaveMasterEntity) super
					.getJdbcTemplate().queryForObject(
							query,
							new BeanPropertyRowMapper(
									FaculityLeaveMasterEntity.class));
		} catch (EmptyResultDataAccessException exception) {
			exception.printStackTrace();
		}
		return faculityLeaveMasterEntity.getImage();
	}


	@Override
	public List<FacultyEntity> findallSalInfo() {
		String query = "SELECT e.id,e.name from emp_db AS e";
		List<FacultyEntity> empidlist2  = super.getJdbcTemplate().query(query, new BeanPropertyRowMapper<FacultyEntity>(FacultyEntity.class));

		return empidlist2;
	}


	@Override
	public List<AdminSalaryMasterEntity> findSpecificSal(String eid) {
		Calendar cal = Calendar.getInstance();		
		int month = cal.get(Calendar.MONTH);
		if(!eid.equalsIgnoreCase("")){
		String query = "SELECT sal.empid as eid,sal.month,sal.daysworked,sal.noleaves,sal.totworkdays,sal.salpaid,sal.comment FROM admin_salary_table AS sal WHERE sal.empid="+eid+" ORDER BY sal.month ASC";
		List<AdminSalaryMasterEntity> adminSalaryMasterEntitylist = super.getJdbcTemplate().query(query,new BeanPropertyRowMapper<AdminSalaryMasterEntity>(AdminSalaryMasterEntity.class));
		return adminSalaryMasterEntitylist;}
		else{
			String query = "SELECT sal.empid as eid,sal.month,sal.daysworked,sal.noleaves,sal.totworkdays,sal.salpaid,sal.comment FROM admin_salary_table AS sal ORDER BY sal.month ASC";
			List<AdminSalaryMasterEntity> adminSalaryMasterEntitylist = super.getJdbcTemplate().query(query,new BeanPropertyRowMapper<AdminSalaryMasterEntity>(AdminSalaryMasterEntity.class));
			return adminSalaryMasterEntitylist;}
	}


	@Override
	public List<AdminSalaryMasterEntity> findWrkDays(String department,String value) {
		if(value.equalsIgnoreCase("all") && department.equalsIgnoreCase("all")){
			String query = "SELECT sal.empid as eid,sal.month,sal.department,sal.daysworked,sal.noleaves,sal.totworkdays,sal.salpaid,sal.comment,e.name,e.category FROM admin_salary_table AS sal,emp_db as e group by sal.empid,sal.month ORDER BY sal.month ASC";
			List<AdminSalaryMasterEntity> adminSalaryMasterEntitylist = super.getJdbcTemplate().query(query,new BeanPropertyRowMapper<AdminSalaryMasterEntity>(AdminSalaryMasterEntity.class));
			return adminSalaryMasterEntitylist;}
		else if(value.equalsIgnoreCase("all") && department.equalsIgnoreCase(department)){
			String query = "SELECT sal.empid as eid,sal.month,sal.department,sal.daysworked,sal.noleaves,sal.totworkdays,sal.salpaid,sal.comment,e.name,e.category FROM admin_salary_table AS sal,emp_db as e where sal.department='"+department+"' group by sal.empid,sal.month ORDER BY sal.month ASC";
			List<AdminSalaryMasterEntity> adminSalaryMasterEntitylist = super.getJdbcTemplate().query(query,new BeanPropertyRowMapper<AdminSalaryMasterEntity>(AdminSalaryMasterEntity.class));
			return adminSalaryMasterEntitylist;}
		else if(value.equalsIgnoreCase(value) && department.equalsIgnoreCase("all")){
			String query = "SELECT sal.empid as eid,sal.month,sal.department,sal.daysworked,sal.noleaves,sal.totworkdays,sal.salpaid,sal.comment,e.name,e.category FROM admin_salary_table AS sal,emp_db as e where e.category='"+value+"' group by sal.empid,sal.month ORDER BY sal.month ASC";
			List<AdminSalaryMasterEntity> adminSalaryMasterEntitylist = super.getJdbcTemplate().query(query,new BeanPropertyRowMapper<AdminSalaryMasterEntity>(AdminSalaryMasterEntity.class));
			return adminSalaryMasterEntitylist;}
		else{
			String query = "SELECT sal.empid as eid,sal.month,sal.department,sal.daysworked,sal.noleaves,sal.totworkdays,sal.salpaid,sal.comment,e.name,e.category FROM admin_salary_table AS sal,emp_db as e WHERE sal.department='"+department+"' AND e.category='"+value+"' GROUP BY sal.empid,sal.month ORDER BY sal.month ASC";
			List<AdminSalaryMasterEntity> adminSalaryMasterEntitylist = super.getJdbcTemplate().query(query,new BeanPropertyRowMapper<AdminSalaryMasterEntity>(AdminSalaryMasterEntity.class));
			return adminSalaryMasterEntitylist;}


	}

}

