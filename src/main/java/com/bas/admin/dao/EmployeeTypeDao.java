package com.bas.admin.dao;

import java.util.List;

import com.bas.admin.dao.entity.EmployeeTypeEntity;

/**
 * 
 * @author Amogh
 *
 */
public interface EmployeeTypeDao {
	
	public String addEmployeeType(EmployeeTypeEntity employeeTypeEntity);
	public String editEmployeeType(EmployeeTypeEntity departmentEntity);
	public String deleteEmployeeType(int employeeId);
	public List<EmployeeTypeEntity> findEmployeeTypes();
	public EmployeeTypeEntity findEmployeeTypeById(int empId);
	public String updateEmployeeType(EmployeeTypeEntity employeeTypeEntity);
	public List<String> searchEmployee(String employeeName);
	public String getEmployeeId(String fName, String lname);
	public int getEmployeeLate(int monthVal, String employeeId, String status);

}
