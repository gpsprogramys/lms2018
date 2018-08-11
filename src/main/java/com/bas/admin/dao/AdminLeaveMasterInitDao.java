package com.bas.admin.dao;

import java.util.List;

import com.bas.employee.dao.entity.FaculityLeaveMasterEntity;

public interface AdminLeaveMasterInitDao {
	public String addLeaveInit(FaculityLeaveMasterEntity faculityLeaveMasterEntity);	
	public String deleteLeaveInit(String empNo,String mdate);
	public List<FaculityLeaveMasterEntity> findAllleaveBalance();
	String editLeaveInit(FaculityLeaveMasterEntity faculityLeaveMasterEntity);
	public FaculityLeaveMasterEntity findemplist(String empno,String mdate);
	
}
