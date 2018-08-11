package com.bas.admin.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bas.admin.dao.LeaveHistoryDao;
import com.bas.admin.dao.entity.LeaveHistoryEntity;
import com.bas.admin.service.LeaveHistoryService;
import com.bas.admin.web.controller.form.LeaveHistory;

@Service("LeaveHistoryServiceImpl")
public class LeaveHistoryServiceImpl implements LeaveHistoryService {

	@Autowired
	@Qualifier("LeaveHistoryDaoImpl")
	private LeaveHistoryDao leaveHistoryDao;
	
	@Override
	public List<LeaveHistory> getLeaveHistory(int month, int year, String department) {
		List<LeaveHistory> leaveHistoryList = new LinkedList<LeaveHistory>();		
		List<LeaveHistoryEntity> leaveHistoryEntity = leaveHistoryDao.getLeaveHistory( month, year, department);
		for (LeaveHistoryEntity lhe : leaveHistoryEntity) {
			LeaveHistory tmpObj = new LeaveHistory();
			BeanUtils.copyProperties(lhe, tmpObj);
			leaveHistoryList.add(tmpObj);
		}
		return leaveHistoryList;		
	}

	@Override
	public byte[] findPDPhotoByID(String id) {		
		return leaveHistoryDao.findPhotoByID(id);
	}

}
