package com.bas.hr.dao.query;

import com.bas.common.constant.LeaveStatus;

public interface HRQuery {
	public static String FIND_PENDING_LEAVE_REQUESTS ="select * from emp_leave_requests_tbl where hrApproval = '"+LeaveStatus.PENDING_STATUS+"' and hrid=?";
	public static String FIND_PENDING_LEAVE_REQUESTS_MANAGER ="select * from emp_leave_requests_tbl where managerApproval = '"+LeaveStatus.PENDING_STATUS+"' and reportingManager=?";
	public static String FIND_EMPLOYEE_ON_LEAVE_ON_DATE ="select * from emp_leave_history where hrApproval = '"+LeaveStatus.APPROVED_STATUS+"' and leaveFrom=?";
	public static String FIND_EMPLOYEE ="select * from emp_db";
	public static String FIND_EMP_CURRENT_CL_BALANCE_BY_REQUEST_ID ="select lwp,totalDays,leaveType,leaveDays,cel as el,ccl as cl from emp_leave_requests_tbl where requestId = ?";
	
}
 