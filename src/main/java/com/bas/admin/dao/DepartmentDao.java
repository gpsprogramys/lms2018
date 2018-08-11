package com.bas.admin.dao;

import java.util.List;

import com.bas.admin.dao.entity.DepartmentEntity;

/**
 * @author nagendra.yadav
 *   This is master for department
*
 */
public interface DepartmentDao {
	
	public String addDepartment(DepartmentEntity departmentEntity);
	public String editDepartment(DepartmentEntity departmentEntity);
	public String deleteDepartment(int departmentId);
	public List<DepartmentEntity> findDepartments();
	public DepartmentEntity findDepartmentById(int deptId);
	public String updateDepartment(DepartmentEntity departmentEntity);
	String validateDep(String depName);
	String deleteDepartment(String depName);

}
