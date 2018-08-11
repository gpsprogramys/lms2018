package com.bas.employee.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bas.employee.dao.BasFacultyTimeDao;
import com.bas.employee.dao.entity.FaculityTimeEntity;
import com.bas.employee.service.BasFacultyTimeService;
import com.bas.employee.web.controller.form.FaculityTimeForm;
@Service("BasFacultyTimeServiceImpl")
public class BasFacultyTimeServiceImpl implements BasFacultyTimeService{

	@Autowired
	@Qualifier("BasFacultyTimeDaoImpl")
	private BasFacultyTimeDao basFacultyTimeDao;

	@Override
	public String addFacultyTime(FaculityTimeForm faculityTimeForm) {
		FaculityTimeEntity faculityTimeEntity = new FaculityTimeEntity();
		BeanUtils.copyProperties(faculityTimeForm, faculityTimeEntity);
		return basFacultyTimeDao.addFacultyTime(faculityTimeEntity);
	}


	@Override
	public String editFacultyTime(FaculityTimeForm faculityTimeForm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteFacultyTime(int fid) {
		String msg = basFacultyTimeDao.deleteFacultyTime(fid);
		return msg;
	}

	@Override
	public List<FaculityTimeForm> findFacultyTime() {
		List<FaculityTimeEntity> faculityTimeEntities = basFacultyTimeDao.findFacultyTime();
		List<FaculityTimeForm> faculityTimeForms = new ArrayList<FaculityTimeForm>();
		
		for (FaculityTimeEntity faculityTimeEntitie : faculityTimeEntities) {
			
			FaculityTimeForm FaculityTimeForm = new FaculityTimeForm();
			BeanUtils.copyProperties(faculityTimeEntitie, FaculityTimeForm);
			faculityTimeForms.add(FaculityTimeForm);
		}
		
		
		return faculityTimeForms;
	}

	@Override
	public FaculityTimeForm findFacultyTimeById(int facId) {
		FaculityTimeEntity faculityTimeEntity= basFacultyTimeDao.findFacultyTimeById(facId);
		FaculityTimeForm faculityTimeForm = new FaculityTimeForm();		
		BeanUtils.copyProperties(faculityTimeEntity, faculityTimeForm);
		return faculityTimeForm;
	}

	@Override
	public String updateFacultyTime(FaculityTimeForm faculityTimeForm) {
		FaculityTimeEntity faculityTimeEntity = new FaculityTimeEntity();
		BeanUtils.copyProperties(faculityTimeForm, faculityTimeEntity);
		String resu = basFacultyTimeDao.updateFacultyTime(faculityTimeEntity);
		return resu;
	}


	@Override
	public FaculityTimeForm findFacultyTimeByParams(int facId, String dayName,
			String classRoom, String subjectCode) {
		
		FaculityTimeEntity faculityTimeEntity= basFacultyTimeDao.findFacultyTimeByParams(facId, dayName, classRoom, subjectCode);
		FaculityTimeForm faculityTimeForm = new FaculityTimeForm();		
		BeanUtils.copyProperties(faculityTimeEntity, faculityTimeForm);
		return faculityTimeForm;
	}
	


	
}
