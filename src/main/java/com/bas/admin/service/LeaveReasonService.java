package com.bas.admin.service;

import java.util.List;

import com.bas.admin.web.controller.form.LeaveReasonForm;

public interface LeaveReasonService {
	public String addLeaveReason(LeaveReasonForm leaveReasonForm);
	public String editLeaveReason(LeaveReasonForm leaveReasonForm);
	public String deleteLeaveReason(int leaveId);
	public List<LeaveReasonForm> findLeaveReason();
	public LeaveReasonForm findLeaveReasonById(int lveRsnId);
	public String updateLeaveReason(LeaveReasonForm leaveReasonForm);

}
