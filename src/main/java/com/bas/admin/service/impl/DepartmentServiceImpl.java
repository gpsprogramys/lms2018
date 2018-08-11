package com.bas.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bas.admin.dao.DepartmentDao;
import com.bas.admin.dao.entity.DepartmentEntity;
import com.bas.admin.service.DepartmentService;
import com.bas.admin.web.controller.form.DepartmentForm;

@Service("DepartmentServiceImpl")
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
	@Autowired
	@Qualifier("DepartmentDaoImpl")
	private DepartmentDao departmentDao;

	@Override
	public String addDepartment(DepartmentForm departmentForm) {
		DepartmentEntity departmentEntity = new DepartmentEntity();
		BeanUtils.copyProperties(departmentForm, departmentEntity);
		String result = departmentDao.addDepartment(departmentEntity);
		return result;
	}

	@Override
	public String editDepartment(DepartmentForm departmentForm) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String validateDep(String depName){
		return departmentDao.validateDep(depName);
	}

	@Override
	public String deleteDepartment(String depName) {
		departmentDao.deleteDepartment(depName);
		return "success";
	}

	@Override
	public List<DepartmentForm> findDepartments() {
		List<DepartmentEntity> departmentEntities = departmentDao
				.findDepartments();
		List<DepartmentForm> departmentForms = new ArrayList<DepartmentForm>();

		for (DepartmentEntity de : departmentEntities) {

			DepartmentForm df = new DepartmentForm();
			BeanUtils.copyProperties(de, df);
			departmentForms.add(df);
		}

		return departmentForms;
	}

	
	@Override
	public DepartmentForm findDepartmentById(int deptId) {

		DepartmentEntity departmentEntity = departmentDao
				.findDepartmentById(deptId);
		DepartmentForm departmentForm = new DepartmentForm();
		BeanUtils.copyProperties(departmentEntity, departmentForm);
		return departmentForm;
	}

	@Override
	public String updateDepartment(DepartmentForm departmentForm) {

		DepartmentEntity departmentEntity = new DepartmentEntity();
		BeanUtils.copyProperties(departmentForm, departmentEntity);
		String res = departmentDao.updateDepartment(departmentEntity);

		return res;
	}

}
