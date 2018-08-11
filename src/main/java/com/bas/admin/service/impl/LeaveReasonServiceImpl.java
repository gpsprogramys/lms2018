package com.bas.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bas.admin.dao.LeaveReasonDao;
import com.bas.admin.dao.entity.LeaveReasonEntity;
import com.bas.admin.service.LeaveReasonService;
import com.bas.admin.web.controller.form.LeaveReasonForm;

@Service("LeaveReasonServiceImpl")
@Transactional
public class LeaveReasonServiceImpl implements LeaveReasonService {
	@Autowired
	@Qualifier("LeaveReasonDaoImpl")
	private LeaveReasonDao leaveReasonDao;

	@Override
	public String addLeaveReason(LeaveReasonForm leaveReasonForm) {
		LeaveReasonEntity leaveReasonEntity = new LeaveReasonEntity();
		BeanUtils.copyProperties(leaveReasonForm, leaveReasonEntity);
		String result = leaveReasonDao.addLeaveReason(leaveReasonEntity);
		return result;
	}

	@Override
	public String editLeaveReason(LeaveReasonForm leaveReasonForm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteLeaveReason(int leaveId) {
		leaveReasonDao.deleteLeaveReason(leaveId);
		return "success";
	}

	@Override
	public List<LeaveReasonForm> findLeaveReason() {
		List<LeaveReasonEntity> leaveReasonEntities = leaveReasonDao
				.findLeaveReason();
		List<LeaveReasonForm> leaveReasonForms = new ArrayList<LeaveReasonForm>();
		for (LeaveReasonEntity lre : leaveReasonEntities) {
			LeaveReasonForm lrf = new LeaveReasonForm();
			BeanUtils.copyProperties(lre, lrf);
			leaveReasonForms.add(lrf);
		}

		return leaveReasonForms;
	}

	@Override
	public LeaveReasonForm findLeaveReasonById(int lveRsnId) {
		LeaveReasonEntity leaveReasonEntity = leaveReasonDao
				.findLeaveReasonById(lveRsnId);
		LeaveReasonForm leaveReasonForm = new LeaveReasonForm();
		BeanUtils.copyProperties(leaveReasonEntity, leaveReasonForm);
		return leaveReasonForm;
	}

	@Override
	public String updateLeaveReason(LeaveReasonForm leaveReasonForm) {
		LeaveReasonEntity leaveReasonEntity = new LeaveReasonEntity();
		BeanUtils.copyProperties(leaveReasonForm, leaveReasonEntity);
		String res = leaveReasonDao.updateLeaveReason(leaveReasonEntity);
		return res;
	}
}
