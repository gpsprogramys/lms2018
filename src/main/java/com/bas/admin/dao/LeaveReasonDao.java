package com.bas.admin.dao;

import java.util.List;

import com.bas.admin.dao.entity.LeaveReasonEntity;

public interface LeaveReasonDao {
	public String addLeaveReason(LeaveReasonEntity leaveReasonEntity);
	public String editLeaveReason(LeaveReasonEntity leaveReasonEntity);
	public String deleteLeaveReason(int leaveId);
	public List<LeaveReasonEntity> findLeaveReason();
	public LeaveReasonEntity findLeaveReasonById(int lveRsnId);
	public String updateLeaveReason(LeaveReasonEntity leaveReasonEntity);

}
