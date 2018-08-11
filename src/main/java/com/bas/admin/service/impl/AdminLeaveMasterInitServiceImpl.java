package com.bas.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bas.admin.dao.AdminLeaveMasterInitDao;
import com.bas.admin.service.AdminLeaveMasterInitService;
import com.bas.employee.dao.entity.FaculityLeaveMasterEntity;
import com.bas.employee.web.controller.form.FaculityLeaveMasterVO;

@Service("adminLeaveMasterInitImpl")
public class AdminLeaveMasterInitServiceImpl implements AdminLeaveMasterInitService {
	
	@Autowired
	@Qualifier("leavemasterinitdaoimpl")
	private AdminLeaveMasterInitDao adminLeaveMasterInitDao;

	@Override
	public String addLeaveInit(FaculityLeaveMasterVO faculityLeaveMasterVO) {
		FaculityLeaveMasterEntity faculityLeaveMasterEntity = new FaculityLeaveMasterEntity();
		BeanUtils.copyProperties(faculityLeaveMasterVO, faculityLeaveMasterEntity);
		return adminLeaveMasterInitDao.addLeaveInit(faculityLeaveMasterEntity);
	}

	@Override
	public String editLeaveInit(FaculityLeaveMasterVO faculityLeaveMasterVO) {
		FaculityLeaveMasterEntity faculityLeaveMasterEntity = new FaculityLeaveMasterEntity();
		BeanUtils.copyProperties(faculityLeaveMasterVO, faculityLeaveMasterEntity);
		String result = adminLeaveMasterInitDao.editLeaveInit(faculityLeaveMasterEntity);
		return "__done__";
	}

	@Override
	public String deleteLeaveInit(String empNo,String mdate) {		
		String result = adminLeaveMasterInitDao.deleteLeaveInit(empNo,mdate);
		return "__done__";
	}

	@Override
	public List<FaculityLeaveMasterVO> findAllleaveBalance() {
		List<FaculityLeaveMasterVO> faculityLeaveMasterVOslist = new ArrayList<FaculityLeaveMasterVO>();
		List<FaculityLeaveMasterEntity> faculityLeaveMasterEntitieslist=adminLeaveMasterInitDao.findAllleaveBalance();
		for (FaculityLeaveMasterEntity faculityLeaveMasterEntity : faculityLeaveMasterEntitieslist) {
			FaculityLeaveMasterVO faculityLeaveMasterVO = new FaculityLeaveMasterVO();
			BeanUtils.copyProperties(faculityLeaveMasterEntity,faculityLeaveMasterVO);
			faculityLeaveMasterVOslist.add(faculityLeaveMasterVO);		}
		
		return faculityLeaveMasterVOslist;
	}

	@Override
	public FaculityLeaveMasterVO findemplist(String empno,String mdate) {		
		FaculityLeaveMasterVO faculityLeaveMasterVO = new FaculityLeaveMasterVO();
		FaculityLeaveMasterEntity faculityLeaveMasterEntity=adminLeaveMasterInitDao.findemplist(empno,mdate);	
		BeanUtils.copyProperties(faculityLeaveMasterEntity,faculityLeaveMasterVO);				
		return faculityLeaveMasterVO;
		
	}



	
}
