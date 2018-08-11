package com.bas.admin.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.bas.admin.dao.AdminLeaveMasterInitDao;
import com.bas.employee.dao.entity.FaculityLeaveMasterEntity;

@Repository("leavemasterinitdaoimpl")
public class AdminLeaveMasterInitDaoImpl extends JdbcDaoSupport implements AdminLeaveMasterInitDao {

	@Autowired
	@Qualifier("sdatasource")
	public void setDataSourceInSuper(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	public String addLeaveInit(FaculityLeaveMasterEntity faculityLeaveMasterEntity) {
		String addqry = "INSERT INTO emp_leave_balance(empNo,leaveMonth,totalCL,totalSL,totalEL,od,doe,dom,description,modifiedBy) values(?,?,?,?,?,?,?,?,?,?)";
		Object data[] = new Object[] {faculityLeaveMasterEntity.getEmpid(),faculityLeaveMasterEntity.getLeaveMonth(),faculityLeaveMasterEntity.getTotalCL(),faculityLeaveMasterEntity.getTotalSL(),faculityLeaveMasterEntity.getTotalEL(),faculityLeaveMasterEntity.getOd(),faculityLeaveMasterEntity.getLeaveMonth(),faculityLeaveMasterEntity.getLeaveMonth(),"Initial Leave", "system"};		
		super.getJdbcTemplate().update(addqry, data);
		return "______success___";

	}
	
	@Override
	public List<FaculityLeaveMasterEntity> findAllleaveBalance() {
		String lquery = "SELECT empNo,leaveMonth,totalCL,totalSL,totalEL,od FROM emp_leave_balance";
		List<FaculityLeaveMasterEntity> faculityLeaveMasterEntitylist = super.getJdbcTemplate().query(lquery,new BeanPropertyRowMapper(FaculityLeaveMasterEntity.class));		
		return faculityLeaveMasterEntitylist;
	}



	@Override
	public String editLeaveInit(FaculityLeaveMasterEntity faculityLeaveMasterEntity){
		String lquery = "UPDATE emp_leave_balance SET empNo=?,leaveMonth=?,totalCL=?,totalSL=?,totalEL=?,od=? WHERE empNo=? AND leaveMonth=?";
		super.getJdbcTemplate().update(lquery,new Object[]{faculityLeaveMasterEntity.getEmpid(),faculityLeaveMasterEntity.getLeaveMonth(),faculityLeaveMasterEntity.getTotalCL(),faculityLeaveMasterEntity.getTotalSL(),faculityLeaveMasterEntity.getTotalEL(),faculityLeaveMasterEntity.getOd(),faculityLeaveMasterEntity.getEmpid(),faculityLeaveMasterEntity.getLeaveMonth()});
		return "done";
	}

	@Override
	public String deleteLeaveInit(String empNo,String mdate) {
		String lquery = "DELETE FROM emp_leave_balance WHERE empNo=? AND leaveMonth=?";
		super.getJdbcTemplate().update(lquery,new Object[]{empNo,mdate});
		return "done";
	}

	@Override
	public FaculityLeaveMasterEntity findemplist(String empno,String mdate) {
		String lquery = "SELECT e.id AS empNo,e.name,e.designation,e.department,e.type,eb.leaveMonth,eb.totalCL,eb.totalSL,eb.totalEL,eb.od FROM emp_db as e,emp_leave_balance AS eb  WHERE eb.empNo=e.id AND e.id="+empno+" AND eb.leaveMonth='"+mdate+"'";
		FaculityLeaveMasterEntity faculityLeaveMasterEntitylist = (FaculityLeaveMasterEntity)super.getJdbcTemplate().queryForObject(lquery,new BeanPropertyRowMapper(FaculityLeaveMasterEntity.class));		
		return faculityLeaveMasterEntitylist;
	}



}
