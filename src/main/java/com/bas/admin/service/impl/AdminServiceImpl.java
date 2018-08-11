package com.bas.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bas.admin.dao.BasAdminDao;
import com.bas.admin.dao.entity.AdminSalaryMasterEntity;
import com.bas.admin.service.AdminFacultyService;
import com.bas.admin.web.controller.form.AdminSalaryMasterVO;
import com.bas.employee.dao.entity.FacultyEntity;
import com.bas.employee.web.controller.form.FacultyForm;

/**
 * @author Sid
 * Service layer while accessing Salary History(Admin)
 */
@Service("BasAdminServiceImpl")
@Transactional
public class AdminServiceImpl implements AdminFacultyService{

	@Autowired
	@Qualifier("BasAdminDaoImpl")
	private BasAdminDao basAdminDao;


	@Override
	public List<AdminSalaryMasterVO> findSalaryHistory(String empid){
		List<AdminSalaryMasterEntity> adminSalaryMasterEntitieslist=basAdminDao.findSalaryHistory(empid);
		List<AdminSalaryMasterVO> adminSalaryMasterVOslist=new ArrayList<AdminSalaryMasterVO>();
		for(AdminSalaryMasterEntity fs: adminSalaryMasterEntitieslist){
			AdminSalaryMasterVO adminSalaryMasterVO=new AdminSalaryMasterVO();
			BeanUtils.copyProperties(fs,adminSalaryMasterVO);
			adminSalaryMasterVOslist.add(adminSalaryMasterVO);
		}
		return adminSalaryMasterVOslist;
	}

	@Override
	public byte[] findEmpPhotoByEid(int eid) {
		return basAdminDao.findEmpPhotoByEid(eid);
	}

	@Override
	public AdminSalaryMasterVO findSalaryHistoryAdmin(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FacultyForm> findallSalInfo() {
		List<FacultyEntity> facultyEntitieslist=basAdminDao.findallSalInfo();
		List<FacultyForm> facultyFormslist=new ArrayList<FacultyForm>();
		for(FacultyEntity fs: facultyEntitieslist){
			FacultyForm facultyForm=new FacultyForm();
			BeanUtils.copyProperties(fs,facultyForm);
			facultyFormslist.add(facultyForm);
		}
		return facultyFormslist;
		
	}

	@Override
	public List<AdminSalaryMasterVO> findSpecificSal(String eid) {
		List<AdminSalaryMasterEntity> adminSalaryMasterEntitylist = basAdminDao.findSpecificSal(eid);
		List<AdminSalaryMasterVO> adminSalaryMasterVOslist=new ArrayList<AdminSalaryMasterVO>();
		for(AdminSalaryMasterEntity fs: adminSalaryMasterEntitylist){
			AdminSalaryMasterVO adminSalaryMasterVO=new AdminSalaryMasterVO();
			BeanUtils.copyProperties(fs,adminSalaryMasterVO);
			adminSalaryMasterVOslist.add(adminSalaryMasterVO);
		}
		return adminSalaryMasterVOslist;		
	}

	@Override
	public List<AdminSalaryMasterVO> findWrkDays(String department,String value) {
		List<AdminSalaryMasterEntity> adminSalaryMasterEntitylist = basAdminDao.findWrkDays(department,value);
		List<AdminSalaryMasterVO> adminSalaryMasterVOslist=new ArrayList<AdminSalaryMasterVO>();
		for(AdminSalaryMasterEntity fs: adminSalaryMasterEntitylist){
			AdminSalaryMasterVO adminSalaryMasterVO=new AdminSalaryMasterVO();
			BeanUtils.copyProperties(fs,adminSalaryMasterVO);
			adminSalaryMasterVOslist.add(adminSalaryMasterVO);
		}
		return adminSalaryMasterVOslist;	
	}

	

		
	/*public AdminSalaryMasterVO findSalaryHistoryAdmin(String string) {
		AdminSalaryMasterEntity adminSalaryMasterEntityad=basAdminDao.findSalaryHistory(empid);
		AdminSalaryMasterVO adminSalaryMasterVOad=new AdminSalaryMasterVO();	
			BeanUtils.copyProperties(adminSalaryMasterEntityad,adminSalaryMasterVO);
			adminSalaryMasterVOslist.add(adminSalaryMasterVO);
		}
		return adminSalaryMasterVOslist;
	}*/





}
