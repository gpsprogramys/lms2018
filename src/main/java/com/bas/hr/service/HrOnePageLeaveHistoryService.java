package com.bas.hr.service;

import java.util.List;

import com.bas.hr.web.controller.model.EmployeeOnePageLeaveHistoryVO;

public interface HrOnePageLeaveHistoryService {
	
	public List<EmployeeOnePageLeaveHistoryVO> findEmployeeOnePageLeaveHistory(String empid,String sDate, String eDate);

	
}
