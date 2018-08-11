package com.bas.admin.service;

import java.util.List;

import com.bas.admin.web.controller.form.LeaveTypeForm;

public interface LeaveTypeService {
	public String addLeaveType(LeaveTypeForm leaveTypeForm);
	public String editLeaveType(LeaveTypeForm leaveTypeForm);
	public String deleteLeaveType(int leaveTypeId);
	public List<LeaveTypeForm> findLeaveType();
	public LeaveTypeForm findLeaveTypeById(int lveTpId);
	public String updateLeaveType(LeaveTypeForm leaveTypeForm);
}
