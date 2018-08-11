package com.bas.employee.dao.query;


public interface EmployeeQuery {
	public static String FIND_PENDING_LEAVE_REQUESTS_BY_EMPID ="select * from emp_leave_requests_tbl where empid=?";
}
