package com.bas.admin.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.bas.admin.dao.LeaveHistoryDao;
import com.bas.admin.dao.entity.LeaveHistoryEntity;

@Repository("LeaveHistoryDaoImpl")
@Scope("singleton")
public class LeaveHistoryDaoImpl extends JdbcDaoSupport
		implements
			LeaveHistoryDao {

	@Autowired
	@Qualifier("sdatasource")
	public void setDataSourceInSuper(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	public List<LeaveHistoryEntity> getLeaveHistory(int month, int year, String department) {
		String sql = null;
		if(department.toLowerCase().equals("all")){
			sql = "select "
                    + "emp_db.id as empid, "
                    + "emp_db.name, "
                    + "emp_db.department,    "
                    + "emp_db.designation,    "
                    + "faculty_working_days_tbl.noOfLwp as leavesWithoutPay, "
                    + "faculty_working_days_tbl.noOfApprovedLeaves as noApprovedLeaves, "
                    + "faculty_working_days_tbl.noOfHolidays as noHolidays, "
                    + "faculty_working_days_tbl.totalworkingdays as daysWorked, "
                    + "emp_db.image as photograph "
                    + "from emp_db inner join faculty_working_days_tbl "
                    + "on emp_db.id = faculty_working_days_tbl.empid and  faculty_working_days_tbl.month like '"+year+"-"+String.format("%02d", month) +"-%';";
		}else{
			sql = "select "
                    + "emp_db.id as empid, "
                    + "emp_db.name, "
                    + "emp_db.department,    "
                    + "emp_db.designation,    "
                    + "faculty_working_days_tbl.noOfLwp as leavesWithoutPay, "
                    + "faculty_working_days_tbl.noOfApprovedLeaves as noApprovedLeaves, "
                    + "faculty_working_days_tbl.noOfHolidays as noHolidays, "
                    + "faculty_working_days_tbl.totalworkingdays as daysWorked, "
                    + "emp_db.image as photograph "
                    + "from emp_db inner join faculty_working_days_tbl "
                    + "on emp_db.id = faculty_working_days_tbl.empid and  faculty_working_days_tbl.month like '"+year+"-"+String.format("%02d", month) +"-%' and emp_db.department = '"+ department + "' ;";
		}
		List<LeaveHistoryEntity> eList = super.getJdbcTemplate().query(sql, new BeanPropertyRowMapper(LeaveHistoryEntity.class));
		return eList;
	}

	@Override
	public byte[] findPhotoByID(String id) {
		
		System.out.println("====================================="+id);
		
		String sql = "select emp_db.image from emp_db where emp_db.id =" + id;
		byte[] image = super.getJdbcTemplate().queryForObject(sql, byte[].class);
		return image;
	}
}
