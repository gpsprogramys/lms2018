package com.bas.admin.dao;

import java.util.List;

import com.bas.admin.dao.entity.LeaveHistoryEntity;

public interface LeaveHistoryDao {

	public List<LeaveHistoryEntity> getLeaveHistory(int month, int year,
			String department);

	public byte[] findPhotoByID(String id);

}
