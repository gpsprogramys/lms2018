package com.bas.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bas.admin.dao.EmployeeTypeDao;
import com.bas.admin.dao.entity.EmployeeTypeEntity;
import com.bas.admin.service.EmployeeTypeService;
import com.bas.admin.web.controller.form.EmployeeTypeForm;

@Service("EmployeeTypeServiceImpl")
@Transactional
public class EmployeeTypeServiceImpl implements EmployeeTypeService {
	@Autowired
	@Qualifier("EmployeeTypeDaoImpl")
	private EmployeeTypeDao employeeTypeDao;

	@Override
	public String addEmployeeType(EmployeeTypeForm employeeTypeForm) {
		EmployeeTypeEntity employeeTypeEntity = new EmployeeTypeEntity();
		BeanUtils.copyProperties(employeeTypeForm, employeeTypeEntity);
		String result = employeeTypeDao.addEmployeeType(employeeTypeEntity);
		return result;
	}
	@Override
	public String getEmployeeId(String fName, String lname){
		return employeeTypeDao.getEmployeeId(fName, lname);
	}

	
	// New Impl By Syed
	@Override
	public int getEmployeeLate(int monthVal, String employeeId, String status){
		return employeeTypeDao.getEmployeeLate(monthVal, employeeId, status);
	}
	
	@Override
	public String editEmployeeType(EmployeeTypeForm employeeTypeForm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> searchEmployee(String employeeName){
		List<String> query = employeeTypeDao.searchEmployee(employeeName);
		return query;
	}
	
	
	@Override
	public String deleteEmployeeType(int employeeId) {
		employeeTypeDao.deleteEmployeeType(employeeId);
		return "success";
	}

	@Override
	public List<EmployeeTypeForm> findEmployeeTypes() {
		List<EmployeeTypeEntity> employeeTypeEntities = employeeTypeDao
				.findEmployeeTypes();
		List<EmployeeTypeForm> employeeTypeForms = new ArrayList<EmployeeTypeForm>();
		for (EmployeeTypeEntity ete : employeeTypeEntities) {
			EmployeeTypeForm etf = new EmployeeTypeForm();
			BeanUtils.copyProperties(ete, etf);
			employeeTypeForms.add(etf);
		}
		return employeeTypeForms;
	}

	@Override
	public EmployeeTypeForm findEmployeeTypeById(int empId) {
		EmployeeTypeEntity employeeTypeEntity = employeeTypeDao
				.findEmployeeTypeById(empId);
		EmployeeTypeForm employeeTypeForm = new EmployeeTypeForm();
		BeanUtils.copyProperties(employeeTypeEntity, employeeTypeForm);
		return employeeTypeForm;
	}

	@Override
	public String updateEmployeeType(EmployeeTypeForm employeeTypeForm) {
		EmployeeTypeEntity employeeTypeEntity = new EmployeeTypeEntity();
		BeanUtils.copyProperties(employeeTypeForm, employeeTypeEntity);
		String res = employeeTypeDao.updateEmployeeType(employeeTypeEntity);
		return res;
	}
}