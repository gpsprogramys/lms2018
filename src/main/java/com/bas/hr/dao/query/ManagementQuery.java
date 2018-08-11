package com.bas.hr.dao.query;

import com.bas.common.constant.LeaveStatus;

/**
 * 
 * @author SynergisticIT
 *
 */


public interface ManagementQuery {
	public static String FIND_PENDING_LEAVE_REQUESTS ="select * from emp_leave_requests_tbl where hrApproval!='"+LeaveStatus.PENDING_STATUS+"' and managementid=?";
	//public static String FIND_PENDING_LEAVE_REQUESTS_MANAGER ="select * from emp_leave_requests_tbl where managerApproval = '"+LeaveStatus.PENDING_STATUS+"' and reportingManager=?";
	//public static String FIND_EMPLOYEE_ON_LEAVE_ON_DATE ="select * from emp_leave_history where hrApproval = '"+LeaveStatus.APPROVED_STATUS+"' and leaveFrom=?";
}
