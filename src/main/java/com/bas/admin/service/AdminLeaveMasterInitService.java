package com.bas.admin.service;

import java.util.List;

import com.bas.employee.web.controller.form.FaculityLeaveMasterVO;

public interface AdminLeaveMasterInitService {
	public String addLeaveInit(FaculityLeaveMasterVO faculityLeaveMasterVO);
	public String editLeaveInit(FaculityLeaveMasterVO faculityLeaveMasterVO);
	public String deleteLeaveInit(String empNo,String mdate);
	public List<FaculityLeaveMasterVO> findAllleaveBalance();
	public FaculityLeaveMasterVO findemplist(String empno, String mdate);
	
}
