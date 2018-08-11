package com.bas.admin.web.controller.form;

import java.util.Comparator;

/**
 * 
 * @author nagendra.yadav
 *
 */
public class ManualAttendanceDepartmentComparator implements Comparator<FaculityDailyAttendanceReportVO> {

	@Override
	public int compare(FaculityDailyAttendanceReportVO o1,
			FaculityDailyAttendanceReportVO o2) {
		int p=0;
		if(o1!=null && o1.getDepartment()!=null && o2!=null && o2.getDepartment()!=null){
			p=o1.getDepartment().compareTo(o2.getDepartment());
		}
		return p;
	}


}
