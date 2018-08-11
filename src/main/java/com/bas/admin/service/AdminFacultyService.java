package com.bas.admin.service;

import java.util.List;

import com.bas.admin.web.controller.form.AdminSalaryMasterVO;
import com.bas.employee.web.controller.form.FacultyForm;

/**
 * 
 * @author Sid
 *
 */
public interface AdminFacultyService{
	public List<AdminSalaryMasterVO> findSalaryHistory(String empid); 
	public byte[] findEmpPhotoByEid(int eid);
	public AdminSalaryMasterVO findSalaryHistoryAdmin(String string);
	public List<FacultyForm> findallSalInfo();
	public List<AdminSalaryMasterVO> findSpecificSal(String eid);
	public List<AdminSalaryMasterVO> findWrkDays(String department,String value);

}
