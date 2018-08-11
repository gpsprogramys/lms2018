package com.bas.admin.service;

import java.util.List;

import com.bas.admin.web.controller.form.LeaveHistory;

public interface LeaveHistoryService {
	public abstract List<LeaveHistory> getLeaveHistory(int month, int year, String department);

	public abstract byte[] findPDPhotoByID(String id);
}