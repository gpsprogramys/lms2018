package com.bas.hr.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.bas.common.util.DateUtils;
import com.bas.hr.dao.ReporteeDao;
import com.bas.hr.web.controller.model.ManagerEmployeeRelationEntity;

import javax.sql.DataSource;

@Repository("ReporteeDaoImpl")
@Scope("singleton")
public class ReporteeDaoImpl extends JdbcDaoSupport implements ReporteeDao{

	@Autowired
	@Qualifier("sdatasource")
	public void setDataSourceInSuper(DataSource dataSource) {
		super.setDataSource(dataSource);
	}
	
	/**
	 * 
	 */
	@Override
	public String addEmployeToManagerRelation(ManagerEmployeeRelationEntity managerEmployeeRelationEntity) {
		String sql = "select employee_id, manager_id from emp_manager_relation_tb where manager_id=? and employee_id=?";
		Object values[] =new Object[]{managerEmployeeRelationEntity.getManagerid(), managerEmployeeRelationEntity.getEmpid(),managerEmployeeRelationEntity.getHrid(),DateUtils.getCurrentTimeIntoTimestamp(),DateUtils.getCurrentTimeIntoTimestamp(),managerEmployeeRelationEntity.getUserid()};
		try{
				super.getJdbcTemplate().queryForObject(sql, new Object[]{managerEmployeeRelationEntity.getManagerid(),managerEmployeeRelationEntity.getEmpid()}, ManagerEmployeeRelationEntity.class);
		}
		catch(EmptyResultDataAccessException ex){
			String sql2 = "insert into emp_manager_relation_tb(manager_id,employee_id,hr_id,dom,doe,entryBy)  values(?,?,?,?,?,?)";
			super.getJdbcTemplate().update(sql2, values);
			return "This employee with id "+managerEmployeeRelationEntity.getEmpid()+" is  now reporting to Manager id "+managerEmployeeRelationEntity.getManagerid();
		}
		return "This employee with id "+managerEmployeeRelationEntity.getEmpid()+" is already reporting to Manager id "+managerEmployeeRelationEntity.getManagerid();
	}
}
