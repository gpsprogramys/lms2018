package com.bas.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bas.admin.dao.LeaveTypeDao;
import com.bas.admin.dao.entity.LeaveTypeEntity;
import com.bas.admin.service.LeaveTypeService;
import com.bas.admin.web.controller.form.LeaveTypeForm;

@Service("LeaveTypeServiceImpl")
@Transactional
public class LeaveTypeServiceImpl implements LeaveTypeService {
	@Autowired
	@Qualifier("LeaveTypeDaoImpl")
	private LeaveTypeDao leaveTypeDao;

	@Override
	public String addLeaveType(LeaveTypeForm leaveTypeForm) {
		LeaveTypeEntity leaveTypeEntity = new LeaveTypeEntity();
		BeanUtils.copyProperties(leaveTypeForm, leaveTypeEntity);
		String result = leaveTypeDao.addLeaveType(leaveTypeEntity);
		return result;
	}

	@Override
	public String editLeaveType(LeaveTypeForm leaveTypeForm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteLeaveType(int leaveTypeId) {
		leaveTypeDao.deleteLeaveType(leaveTypeId);
		return "success";
	}

	@Override
	public List<LeaveTypeForm> findLeaveType() {
		List<LeaveTypeEntity> leaveTypeEntities = leaveTypeDao.findLeaveType();
		List<LeaveTypeForm> leaveTypeForms = new ArrayList<LeaveTypeForm>();
		for (LeaveTypeEntity lte : leaveTypeEntities) {
			LeaveTypeForm ltf = new LeaveTypeForm();
			BeanUtils.copyProperties(lte, ltf);
			leaveTypeForms.add(ltf);
		}
		return leaveTypeForms;
	}

	@Override
	public LeaveTypeForm findLeaveTypeById(int lveTpId) {
		LeaveTypeEntity leaveTypeEntity = leaveTypeDao
				.findLeaveTypeById(lveTpId);
		LeaveTypeForm leaveTypeForm = new LeaveTypeForm();
		BeanUtils.copyProperties(leaveTypeEntity, leaveTypeForm);
		return leaveTypeForm;
	}

	@Override
	public String updateLeaveType(LeaveTypeForm leaveTypeForm) {
		LeaveTypeEntity leaveTypeEntity = new LeaveTypeEntity();
		BeanUtils.copyProperties(leaveTypeForm, leaveTypeEntity);
		String res = leaveTypeDao.updateLeaveType(leaveTypeEntity);
		return res;
	}
}
