package com.bas.admin.dao;

import java.util.List;

import com.bas.admin.dao.entity.AdminSalaryMasterEntity;
import com.bas.employee.dao.entity.FacultyEntity;

/**
 * 
 * @author Sid
 *
 */
public interface BasAdminDao {

	public List<AdminSalaryMasterEntity> findSalaryHistory(String empid);
	public List<FacultyEntity> findallSalInfo();
	public byte[] findEmpPhotoByEid(int eid);
	public List<AdminSalaryMasterEntity> findSpecificSal(String eid);
	public List<AdminSalaryMasterEntity> findWrkDays(String department,String value);

}
