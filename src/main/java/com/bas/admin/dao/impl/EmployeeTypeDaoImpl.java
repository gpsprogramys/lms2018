package com.bas.admin.dao.impl;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.bas.admin.dao.EmployeeTypeDao;
import com.bas.admin.dao.entity.EmployeeTypeEntity;

@Repository("EmployeeTypeDaoImpl")
@Scope("singleton")
public class EmployeeTypeDaoImpl extends JdbcDaoSupport implements
		EmployeeTypeDao {

	@Autowired
	@Qualifier("sdatasource")
	public void setDataSourceInSuper(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	private int findNextNum() {
		int num = getJdbcTemplate().queryForInt(
				"select max(employeeTypeId) from employee_type_tbl");
		num = num + 1;
		return num;
	}
	
	/*SELECT CONCAT(first_name, ' ', last_name) as 'full_name' 
	FROM customer WHERE CONCAT(first_name, ' ', last_name) LIKE 'John D%'*/
	
	
	// New Impl By Syed
	@Override
	public List<String> searchEmployee(String employeeName){
		String sQuery = "select concat(name, ' ', fatherName) as 'full_name' from emp_db where concat(name, ' ', fatherName) like '%"+employeeName+"%'";
		List<String> query = super.getJdbcTemplate().queryForList(sQuery, String.class);
		return query;
	}
	// New Impl By Syed
	@Override
	public String getEmployeeId(String fName, String lname){
		String query = "select id from emp_db where name=? and fatherName=?";
		String id = super.getJdbcTemplate().queryForObject(query, new Object[] {fName,lname}, String.class);
		return id;
	}	
	
	// New Impl By Syed
	@Override
	public int getEmployeeLate(int monthVal, String employeeId, String status){
		String query = "select COUNT("+status+") from faculity_att_tab where intimestatus='Late' and fid=? and month(cdate)=?";
		int rowCount = super.getJdbcTemplate().queryForObject(query, new Object[] {employeeId,monthVal}, Integer.class);
		return rowCount;
	}
	
	@Override
	public String addEmployeeType(EmployeeTypeEntity employeeTypeEntity) {
		String sql = "insert into employee_type_tbl values(?,?,?,?,?,?)";
		Object[] data = new Object[] { findNextNum(),
				employeeTypeEntity.getEmployeeTypeName(),
				employeeTypeEntity.getDescription(), new Date(), new Date(),
				"Amogh" };
		super.getJdbcTemplate().update(sql, data);		
		return "Added";
	}

	@Override
	public String editEmployeeType(EmployeeTypeEntity departmentEntity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteEmployeeType(int employeeId) {
		String query = "delete from employee_type_tbl where employeeTypeId=?";
		super.getJdbcTemplate().update(query, employeeId);
		return "deleted";
	}

	@Override
	public List<EmployeeTypeEntity> findEmployeeTypes() {
		String query = "select * from employee_type_tbl";
		List<EmployeeTypeEntity> employeeTypeEntities = super.getJdbcTemplate()
				.query(query,
						new BeanPropertyRowMapper(EmployeeTypeEntity.class));
		return employeeTypeEntities;
	}

	@Override
	public EmployeeTypeEntity findEmployeeTypeById(int empId) {
		String query = "select * from employee_type_tbl where employeeTypeId="
				+ empId;
		EmployeeTypeEntity employeeTypeEntity = (EmployeeTypeEntity) super.getJdbcTemplate()
				.queryForObject(query,
						new BeanPropertyRowMapper(EmployeeTypeEntity.class));
		return employeeTypeEntity;
	}

	@Override
	public String updateEmployeeType(EmployeeTypeEntity employeeTypeEntity) {
		String query = "update employee_type_tbl set employeeTypeName=?,description=?,dom=? where employeeTypeId=?";
		super.getJdbcTemplate().update(
				query,
				new Object[] { employeeTypeEntity.getEmployeeTypeName(),
						employeeTypeEntity.getDescription(), new Date(),
						employeeTypeEntity.getEmployeeTypeId() });
		System.out.println("DAOIMPL: " + employeeTypeEntity);
		System.out.println(query);
		return "success";
	}

}