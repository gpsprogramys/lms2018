package com.bas.admin.service;

import java.util.List;

import com.bas.admin.web.controller.form.EmployeeTypeForm;

/**
 * 
 * @author Amogh
 *
 */
public interface EmployeeTypeService {
	
	public String addEmployeeType(EmployeeTypeForm employeeTypeForm);
	public String editEmployeeType(EmployeeTypeForm employeeTypeForm);
	public String deleteEmployeeType(int employeeId);
	public List<EmployeeTypeForm> findEmployeeTypes();
	public EmployeeTypeForm findEmployeeTypeById(int empId);
	public String updateEmployeeType(EmployeeTypeForm employeeTypeForm);
	public List<String> searchEmployee(String employeeName);
	public String getEmployeeId(String fName, String lname);
	public int getEmployeeLate(int monthVal, String employeeId, String status);
}
