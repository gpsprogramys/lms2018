package com.bas.admin.web.controller.form;

import java.sql.Time;
import java.util.Comparator;

/**
 * 
 * @author xxxxxx
 *
 */
public class InTimeDescComparator  implements Comparator<FaculityDailyAttendanceReportVO>{

	@Override
	public int compare(FaculityDailyAttendanceReportVO o1, FaculityDailyAttendanceReportVO o2) {
		String intime1=o1.getIntime();
		String intime2=o2.getIntime();
		int p=(int)(Time.valueOf(intime2).getTime()-Time.valueOf(intime1).getTime());
		return p;
	}
}
