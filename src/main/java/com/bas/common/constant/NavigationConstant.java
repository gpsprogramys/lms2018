package com.bas.common.constant;

public interface NavigationConstant {
	
	final public static String PROFILE_COMPLETE_PAGE="profileComplete";
	final public static String EMPLOYEE="/WEB-INF/jsp/hr/";
	final public static String HR_BASE="/WEB-INF/jsp/hr/";
	final public static String APPLY_LEAVE_PAGE="applyLeave";
	final public static String SEARCH_EMPLOYEE="searchEmployeesByCriteria";
	final public static String EMPLOYEE_LEAVE_APPLICATION_PAGE="employeeLeaveApplication";
	final public static String EMPLOYEE_LEAVE_STATUS_PAGE="employeeLeaveStatus";
	final public static String EMPLOYEE_BASE="/WEB-INF/jsp/employee/";
	final public static String MARK_LWP_EMPLOYEE_PAGE = "markLwpEmployee";
	final public static String WORKING_DAY_PAGE = "empWorkingDays";
	public static final String DEPT_MONTHLY_ATTENDANCE_PAGE = "deptMonthlyAttendance";
	final public static String VIEW_ATTENDANCE="viewAttendance";
	public static final String ADMIN_APPROVE_LEAVE = "adminLeaveApproval";
	public static final String MANAGEMENT_APPROVE_LEAVE = "managementLeaveApproval";
	public static final String REPORTEE_EMPLOYEE_PAGE = "reporteeEmployee";
	public static final String EMPLOYEE_ON_LEAVE_PAGE = "employeeOnLeave";
	public static final String EMPLOYEE_ON_LEAVE_EXCEL = "employeeOnLeaveExcel";
	
	public static final String NATIONAL_HOLIDAY = "National Holiday";
	public static final String REGIONAL_HOLIDAY = "Regional Holiday";
	public static final String NOT_A_PUBLIC_HOLIDAY = "Not a Public Holiday";
	public static final String MANUAL_ATTENDANCE = "manualAttendance";
	public static final String VIEW_ONE_EMPLOYEE_ATTENDANCE = "viewOneEmployeeAttendance";
	public static final String VIEW_MESSAGE_BOARD = "viewMessageBoard";
	final public static String MESSAGE_BOARD="messageBoard";
	final public static String POST_MESSAGE_BOARD_PAGE="postMessageBoard";
	final public static String MESSAGE_BOARD_INBOX_PAGE="messageBoardInbox";

	final public static String LMS_PREFIX_PAGE="WEB-INF/jsp/lms/";
	final public static String MANAGER_APPROVE_LEAVE ="managerApproveLeave";

	final public static String EMP_MONTHLY_ATTENDANCE_STATUS ="employeeMonthlyAttendanceStatus";

	final public static String EMPLOYEE_MONTHLY_ATT_STATUS_WITH_CHART ="employeeMonthlyAttStatusWithChart";
	final public static String EMPLOYEE_MANUAL_ATTENDANCE ="employeeManualAttendance";
	final public static String EMPLOYEE_ALTERNATE_CLASS_STATUS ="employeeAlternateClassStatus";

	////NEW FOR EMPLOYEE
	final public static String EMPLOYEE_PREFIX_PAGE="WEB-INF/jsp/employee/";
	final public static String EMPLOYEE_HOME_PAGE="employee-home";
	final public static String MANAGEMENT_HOME_PAGE="management-home";
	
	final public static String USER_SESSION_DATA="user_session_data";
	final public static String COMMON_PREFIX_PAGE="WEB-INF/jsp/common/";
	final public static String COMMON_HOME_PAGE="basHomePage";
	final public static String FACULTY_PREFIX_PAGE="WEB-INF/jsp/faculty/";
	final public static String LEAVE_HISTORY_PAGE="leaveHistory";
	final public static String ADMIN_PREFIX_PAGE="WEB-INF/jsp/admin/";
	final public static String MANAGEMENT_PREFIX_PAGE="WEB-INF/jsp/management/";
	final public static String SUCCESS_PAGE="success";
	public static final String ORAGANIGATION_TIME_PAGE = "oraganigationTime";
	public static final String ADMINPAGINATION_ATTENDANCE_TODAY = "adminAttendanceToday";
    public static final String ADMIN_ATTENDANCE_TODAY = "showEmpAttendenceForToday";
	final public static String FACULTY_REGISTRATION_PAGE="facultyRegistration";
	final public static String FACULTY_EDIT_PAGE="editFaculty";
	final public static String SHOW_REGISTEREDFACULTY_PAGE="showAllFaculty";
	final public static String FACULTY_HOME_PAGE="facultyHome";
	final public static String ADMIN_HOME_PAGE="admin-home";
	final public static String LEAVE_BALANCE_PAGE="leaveBalance";
	final public static String ATTEND_STATUS_PAGE="attendanceStatus";
	final public static String ADMIN_ATTEND_UPDATE_PAGE="adminUpdateAttendance";
	final public static String SALARY_HISTORY_PAGE="facultySalaryHistory";
	final public static String LOGIN_PAGE="Login";
	final public static String SUCCESS_STATUS_PAGE="successStatus";
	final public static String COMMON_ERROR_PAGE="errorPage";
	final public static String RESET_PASSWORD_PAGE="resetPassword";
	final public static String CHANGE_PASSWORD_PAGE="changePassword";
	final public static String ADD_EMPLOYEES="addEmployees";
	final public static String PROFILE="profile";


	final public static String FACULTY_TIME_PAGE="addFacultyTime";
	final public static String FACULTY_LEAVE_APPLY="facultyLeaveApply";
	final public static String SHOW_REGISTEREDFACULTY_WITH_LEAVEHISTORY_PAGE="showAllFacultyWithLeaveHistory";
	final public static String SEARCH_FACULTY_DETAIL_PAGE="searchFacultyDetail";
	final public static String FACULTY_PROFILE_DETAILS_PAGE="facultyProfileDetails";
	
	
	final public static String LEAVE_HISTORY_DETAILS_PAGE="leaveHistoryDetails";
	final public static String FIND_EMPLOYEE_ONE_PAGE_LEAVE_HISTORY="findEmployeeOnePageLeaveHistory";


	//###############ADMIN CONSTANT
	final public static String ADD_DEPARTMENT_PAGE="addDepartment";
	final public static String LEAVE_APPLY_PAGE="leaveApply";
	final public static String VIEW_LEAVE_APPLY_PAGE="viewApplyLeave";
	final public static String MANUAL_ATTENDANCE_PAGE="manualAttendance";
	final public static String ADMINSALARY_HISTORY_PAGE="adminSalaryHistory";
	final public static String ADMINSALARY_HISTORY_PAGEDISP="adminSalaryHistoryDisp";
	final public static String ADMINSALARY_HISTORY_WRKDAYS="adminSalaryHistoryDispWorkdys";
	final public static String ADMINSALARY_HISTORY_BYDATE="adminSalaryHistoryByDate";
	final public static String SHOW_DESIGNATIONS = "showDesignations";
	final public static String ADD_DESIGNATION_PAGE = "addDesignation";
	final public static String ADD_EMPLOYEE_TYPE="addEmployeeType";
	final public static String ADD_HOLIDAY_ENTRY="HolidayEntry";
	final public static String ADD_HOLIDAY_CALENDAR="addHolidayCalendar";
	final public static String ADD_HOLIDAY_ENTRY_VIEW="viewHolidayCalender";
	final public static String SHOW_ALL_LEAVE_HISTORY="showAllLeaveHistory";
	final public static String ADD_CATEGORY_PAGE="addCategory";
	final public static String LEAVE_REASON="addLeaveReason";
	final public static String LEAVE_TYPE="addLeaveType";
	final public static String ORG_TIME_PAGE="organizationTime";
	final public static String ADMIN_LEAVEMASTRINIT_PAGE="leaveMasterInit";
	public static final String ADMIN_ATTENDANCE_PAGE = "adminAttendance";
	public static final String ADMIN_DELETE_ATTENDANCE_PAGE = "adminDeleteAttendance";
	public static final String EMPLOYEE_MANUAL_REQUEST_ATTENDANCE = "empManualRequestAtt";
	public static final String SHOW_ALL_PENDING_LEAVE_HISTORY = "approveLeaves";
	public static final String SHOW_ALL_PENDING_RM_LEAVE_HISTORY = "approveRmLeaves";
	public static final String REPORTEE_MANAGEMENT = "reporteeManagement";
	final public static String LEAVE_APPLY_MANAGER = "leaveApplyManager";
	public static final String SHOW_LEAVE_BALANCE_PAGE = "showAllFacultyLeaveBalance";
	public static final String SHOW_ADMIN_LEAVE_BALANCE_PAGE = "showAdminLeaveBalance";
	public static final String LEAVE_BALANCE_DETAIL_PAGE = "leaveBalanceDetail";

	 final public static String LATE_IN_STATUS = "Late";
  	 final public static String EARLY_OUT_STATUS = "Early";
	 final public static String LATE_IN_COLOR = "late_in_color";
	final public static String EARLY_OUT_COLOR = "early_out_color";
	final public static String NORMAL_COLOR = "normal_color";
	final public static String HOLIDAY_COLOR = "holiday_color";
    public static final String LEAVE_COLOR = "leave_color";
    public static final String SUCCESS_EMPLOYEE_PAGE = "successEmployee";
    public static final String SUCCESS_ADMIN_PAGE = "successAdmin";

    final public static String LEAVE_HISTORY_EXCEL="leaveHistoryExcel";
}

