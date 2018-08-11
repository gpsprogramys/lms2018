package com.bas.admin.dao;

import java.util.List;

import com.bas.admin.dao.entity.LeaveTypeEntity;

public interface LeaveTypeDao {
	public String addLeaveType(LeaveTypeEntity leaveTypeEntity);
	public String editLeaveType(LeaveTypeEntity leaveTypeEntity);
	public String deleteLeaveType(int leaveTypeId);
	public List<LeaveTypeEntity> findLeaveType();
	public LeaveTypeEntity findLeaveTypeById(int lveTpId);
	public String updateLeaveType(LeaveTypeEntity leaveTypeEntity);

}

