package com.bas.common.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author astha
 * 
 */
public class DateUtils {
	
	/**
	 * 
	 * @param currentDate format should in yyyy-MM-dd
	 * 01/11/2017
	 */
	public static String findDayAsPerDate(String currentDate){
	    	String days[]=new String[]{"SUN","MON","TUE","WED","THU","FRI","SAT"};
		    String dateTokens[]=currentDate.split("/");
		   Calendar xmas = new GregorianCalendar(Integer.parseInt(dateTokens[2]),Integer.parseInt(dateTokens[0])-1, Integer.parseInt(dateTokens[1]));
		   int currentDay = xmas.get(Calendar.DAY_OF_WEEK); // 6=Friday
		   return days[currentDay-1];
	}
	
	public static String getPreviousSessionYear() {
		String currentSessionYear=getSpCurrentSessionYear();
		// 2015-2016
		String currentSessionYearTokens[]=currentSessionYear.split("-");
		String previousSessionYear=(Integer.parseInt(currentSessionYearTokens[0])-1)+"-"+((Integer.parseInt(currentSessionYearTokens[1])-1)+"").substring(2);
		return previousSessionYear;
	}
	
	public static String getPreviousYear() {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH);
		String sessionYear = "";
		if(month>=6 && month<12){
			sessionYear= year+"";
		}else{
			sessionYear=(year-1)+"";
		}
		return sessionYear;
	}
	
	public static String getCurrentSessionYear() {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH);
		String sessionYear = "";
		if(month>=6 && month<12){
			sessionYear= year+"-"+((year+1+"").substring(2));
		}else{
			sessionYear=(year-1)+"-"+(year+"").substring(2);
		}
		return sessionYear;
	}
	
	public static String getSpCurrentSessionYear() {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH);
		String sessionYear = "";
		if(month>=6 && month<12){
			sessionYear= year+"-"+(year+1);
		}else{
			sessionYear=(year-1)+"-"+year;
		}
		return sessionYear;
	}

	public static Timestamp getCurrentTimeIntoTimestamp() {
		java.util.Date date = new java.util.Date();
		return new Timestamp(date.getTime());
	}
	
	public static List<String> getStartMonthDateList(){
		List<String> startDateList=new ArrayList<String>();
		String currentSession=getSpCurrentSessionYear();//2016-2017
		String yearsToken[]=currentSession.split("-");
		
		startDateList.add(yearsToken[0]+"-08-01");
		startDateList.add(yearsToken[0]+"-09-01");
		startDateList.add(yearsToken[0]+"-10-01");
		startDateList.add(yearsToken[0]+"-11-01");
		startDateList.add(yearsToken[0]+"-12-01");
		startDateList.add(yearsToken[1]+"-01-01");
		startDateList.add(yearsToken[1]+"-02-01");
		startDateList.add(yearsToken[1]+"-03-01");
		startDateList.add(yearsToken[1]+"-04-01");
		startDateList.add(yearsToken[1]+"-05-01");
		startDateList.add(yearsToken[1]+"-06-01");
		startDateList.add(yearsToken[1]+"-07-01");
		
		return startDateList;
	}
	
	public static List<String> getEndDateMonthList(){
		List<String> endtDateList=new ArrayList<String>();
		String currentSession=getSpCurrentSessionYear();//2016-2017
		String yearsToken[]=currentSession.split("-");
		
		endtDateList.add(yearsToken[0]+"-08-31");
		endtDateList.add(yearsToken[0]+"-09-30");
		endtDateList.add(yearsToken[0]+"-10-31");
		endtDateList.add(yearsToken[0]+"-11-30");
		endtDateList.add(yearsToken[0]+"-12-31");
		endtDateList.add(yearsToken[1]+"-01-31");
		if(isleapYear(yearsToken[1])){
		endtDateList.add(yearsToken[1]+"-02-29");
		}else{
			endtDateList.add(yearsToken[1]+"-02-28");
		}
		endtDateList.add(yearsToken[1]+"-03-31");
		endtDateList.add(yearsToken[1]+"-04-30");
		endtDateList.add(yearsToken[1]+"-05-31");
		endtDateList.add(yearsToken[1]+"-06-30");
		endtDateList.add(yearsToken[1]+"-07-31");
		return endtDateList;
	}	
	public static boolean isleapYear(String year) {
			GregorianCalendar cal = (GregorianCalendar) Calendar.getInstance();
			return cal.isLeapYear(Integer.parseInt(year));
	}

	public static String nextDate(String currentDate, int numberOfDays) {
		long MILLIS_IN_DAY = 1000 * 60 * 60 * 24 * numberOfDays;
		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dateSelectedFrom = null;
		// convert date present in the String to java.util.Date.
		String nextDate = null;
		try {
			dateSelectedFrom = dateFormat.parse(currentDate);
			// get the previous date in String.
			nextDate = dateFormat.format(dateSelectedFrom.getTime() + MILLIS_IN_DAY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nextDate;
	}
	
	/**
	 * method which compute number of date @ return String as date. 08/26/2014
	 */
	public static int twoDateDifferenceSecond(String firstDate, String seconDate) {
		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("MM-dd-yyyy");
		java.util.Date dateFrom = null;
		java.util.Date toFrom = null;
		long dd = 0;
		try {
			dateFrom = dateFormat.parse(firstDate);
			toFrom = dateFormat.parse(seconDate);
			dd = dateFrom.getTime() - toFrom.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		int days = (int) (dd / (1000 * 60 * 60 * 24));
		return days;
	}

	/**
	 * method which compute number of date @ return String as date. 08/26/2014
	 */
	public static int twoDateDifference(String firstDate, String seconDate) {
		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dateFrom = null;
		java.util.Date toFrom = null;
		long dd = 0;
		try {
			dateFrom = dateFormat.parse(firstDate);
			toFrom = dateFormat.parse(seconDate);
			dd = dateFrom.getTime() - toFrom.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		int days = (int) (dd / (1000 * 60 * 60 * 24));
		return days;
	}

	public static String getCurrentCalendarDate(Date date) {
		SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// Date currentDate=new Date();
		String currentDateString = calendarDateFormat.format(date);
		return currentDateString;
	}

	public static Date getDateInSQLFormat(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String cdateInString = formatter.format(date);
		DateFormat dformatter = new SimpleDateFormat("yyyy-MM-dd");
		Date javaDate = null;
		try {
			javaDate = dformatter.parse(cdateInString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date cSQLDate = new Date(javaDate.getDate());
		return cSQLDate;
	}

	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days); // minus number would decrement the days
		return cal.getTime();
	}

	public static Date convertStringIntoDateYYYYMMDD(String date) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date formatter = null;
		try {
			formatter = myFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return formatter;
	}

	public static int getCurrentYear() {
		Calendar c = Calendar.getInstance();
		int currYear = c.get(Calendar.YEAR);
		return currYear;
	}

	public static int getNoOfDaysForCurrentMonth() {
		Calendar c = Calendar.getInstance();
		String month = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
		int currYear = c.get(Calendar.YEAR);
		int noOfDays = 0;
		int feb;
		if (getCurrentYear() % 4 != 0)
			feb = 28;
		else
			feb = 29;
		if("January".equalsIgnoreCase(month)) {
			noOfDays = 31;
		}else if("February".equalsIgnoreCase(month)) {
			noOfDays = feb;
		}	else if("March".equalsIgnoreCase(month)){
			noOfDays = 31;
		}	
		else if("April".equalsIgnoreCase(month)) {
			noOfDays = 30;
		}	else if("May".equalsIgnoreCase(month)) {
			noOfDays = 31;
		}	
		else if("June".equalsIgnoreCase(month)) {
			noOfDays = 30;
		}	else if("July".equalsIgnoreCase(month)) {
			noOfDays = 31;
		}		else if("August".equalsIgnoreCase(month)){
			noOfDays = 31;
		}	else if("September".equalsIgnoreCase(month)){
			noOfDays = 30;
		}	else if("October".equalsIgnoreCase(month)){
			noOfDays = 31;
		}	else if("November".equalsIgnoreCase(month)){
			noOfDays = 30;
		}	else if("December".equalsIgnoreCase(month)){
			noOfDays = 31;
		}	
		return noOfDays;
	}

	public static int getNoOfDaysByMonthAndYear(String month, String year) {
		int noOfDays = 0;
		int feb;
		if (getCurrentYear() % 4 != 0)
			feb = 28;
		else
			feb = 29;

		if("January".equalsIgnoreCase(month)){
			noOfDays = 31;
		}else if("February".equalsIgnoreCase(month)){
			noOfDays = feb;
		}	else if("March".equalsIgnoreCase(month)){
			noOfDays = 31;
		}	
		else if("April".equalsIgnoreCase(month)){
			noOfDays = 30;
		}	else if("May".equalsIgnoreCase(month)){
			noOfDays = 31;
		}	
		else if("June".equalsIgnoreCase(month)){
			noOfDays = 30;
		}	else if("July".equalsIgnoreCase(month)){
			noOfDays = 31;
		}		else if("August".equalsIgnoreCase(month)){
			noOfDays = 31;
		}	else if("September".equalsIgnoreCase(month)){
			noOfDays = 30;
		}	else if("October".equalsIgnoreCase(month)){
			noOfDays = 31;
		}	else if("November".equalsIgnoreCase(month)){
			noOfDays = 30;
		}	else if("December".equalsIgnoreCase(month)){
			noOfDays = 31;
		}	
		return noOfDays;
	}

	public static Timestamp getCurrentTimestatmp() {
		Timestamp timestamp = new Timestamp(new Date().getTime());
		return timestamp;
	}

	public static String convertDateIntoString(Date date) {
		SimpleDateFormat myFormat = new SimpleDateFormat("dd-MMM-yyyy");
		return myFormat.format(date);
	}

	/**
	 * 
	 * @return current date as string into format MM/dd/yyyy SimpleDateFormat
	 *         formatter = new SimpleDateFormat("yyyy-MM-dd");
	 */
	public static String getCurrentCalendarDate() {
		SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		String currentDateString = calendarDateFormat.format(currentDate);
		return currentDateString;
	}
	
	/**
	 * 
	 * @return current date as string into format MM/dd/yyyy SimpleDateFormat
	 *         formatter = new SimpleDateFormat("yyyy-MM-dd");
	 */
	public static String getCurrentCalendarDateMMddYy() {
		SimpleDateFormat calendarDateFormat = new SimpleDateFormat("MM/dd/yyy");
		Date currentDate = new Date();
		String currentDateString = calendarDateFormat.format(currentDate);
		return currentDateString;
	}

	/**
	 * method which compute number of date @ return String as date. 08/26/2014
	 */
	public static String previousDate(String currentDate, int numberOfDays) {
		int MILLIS_IN_DAY = 1000 * 60 * 60 * 24 * numberOfDays;
		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dateSelectedFrom = null;
		// convert date present in the String to java.util.Date.
		String previousDate = null;
		try {
			dateSelectedFrom = dateFormat.parse(currentDate);
			// get the previous date in String.
			previousDate = dateFormat.format(dateSelectedFrom.getTime() - MILLIS_IN_DAY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return previousDate;
	}

	/**
	 * This method will assign a unique URL to uploaded image.
	 * 
	 * @param request
	 * @param sharedMedia
	 * @return assign URL to the uploaded image http://localhost:70707
	 */
	public static String getImageContextPath(HttpServletRequest request) {
		StringBuilder assignedUrl = new StringBuilder("http://");
		assignedUrl.append(request.getServerName() + ":");
		assignedUrl.append(request.getServerPort());
		assignedUrl.append(request.getContextPath() + "/images" + "/");
		return assignedUrl.toString();
	}

	static public long dayDiff(Date d1, Date d2) {
		final long DAY_MILLIS = 1000 * 60 * 60 * 24;
		long day1 = d1.getTime() / DAY_MILLIS;
		long day2 = d2.getTime() / DAY_MILLIS;
		return (day1 - day2);
	}

	/**
	 * Convert this 04/15/2014 format into 2014-04-14
	 * 
	 * @param date
	 * @return
	 */
	public static String convertMMDDYYYYInToYYYYMMDD(String date) {
		String tokens[] = date.split("/");
		StringBuilder builder = new StringBuilder();
		builder.append(tokens[2]);
		builder.append("-");
		builder.append(tokens[0]);
		builder.append("-");
		builder.append(tokens[1]);
		return builder.toString();
	}

	static public String getJavaDateYYMMDDFormat(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String datestring = dateFormat.format(date);
		return datestring;
	}

	public static Date getCurrentJavaDate() {
		return new Date();
	}

	public static void main(String[] args) {
		String currentSession=getSpCurrentSessionYear();
		System.out.println(currentSession);
		/*
		 * try { boolean status = DateUtils.validateCurrentDate(datepicker);
		 * System.out.println(" ___ status __ " + status); } catch
		 * (ParseException e) { e.printStackTrace(); }
		 */

	}

	/**
	 * Method which return the current date This method will take date in the
	 * format 12-May-14(dd-month-yy) and will return in the format
	 * 14-05-12(yy-mm-dd)
	 * 
	 * @return
	 */
	public static String getDateOfClass(String doclass) {
		String tokens[] = doclass.split("-");
		String fdate = tokens[2] + "-" + getMonthIndexByName(tokens[1]) + "-" + tokens[0];
		return fdate;
	}

	/**
	 * Method which return the current date This method will take date in the
	 * format 12-May-14(dd-month-yy) and will return in the format
	 * 14-05-12(yy-mm-dd)
	 * 
	 * @return
	 */
	public static String getSpecialDateOfClass(String doclass) {
		String tokens[] = doclass.split("-");
		String fdate = tokens[2] + "-" + getMonthIndexByName(tokens[1].toUpperCase()) + "-" + tokens[0];
		return fdate;
	}

	/**
	 * Method which return the current date This method will take date in the
	 * format 12/10/1984 and will return in the format 1984/10/12
	 * 
	 * @return
	 */
	public static String getChangeDOBFromExcel(String doclass) {
		String tokens[] = doclass.split("/");
		String fdate = tokens[2] + "-" + tokens[1] + "-" + tokens[0];
		return fdate;
	}

	/**
	 * Method which return the current date
	 * 
	 * @return
	 */
	public static String getCurrentDateSQLDB() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String cdateInString = formatter.format(new Date());
		return cdateInString;
	}
	
	
	public static Time getCurrentTimeNotAMPM(String ptime) {
		// String str = "08:03:10 pm";
		DateFormat formatter = new SimpleDateFormat("hh:mm:ss");
		Date date = null;
		try {
			date = formatter.parse(ptime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long d = date.getTime();
		Time time = new Time(d);
		return time;
	}

	public static Time getCurrentTime(String ptime) {
		// String str = "08:03:10 pm";
		DateFormat formatter = new SimpleDateFormat("hh:mm a");
		Date date = null;
		try {
			date = formatter.parse(ptime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long d = date.getTime();
		Time time = new Time(d);
		return time;
	}

	public static Time getCurrentTime() {
		// setting the values inside the queries
		Date date = new Date();
		long d = date.getTime();
		Time time = new Time(d);
		return time;
	}

	/**
	 * Method which return the current date
	 * 
	 * @return
	 */
	public static java.sql.Date getCurrentDateInSQLFormatFromCalendar(Date date) {
		// 08/12/2015
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String cdateInString = formatter.format(date);
		DateFormat dformatter = new SimpleDateFormat("yyyy-MM-dd");
		Date javaDate = null;
		try {
			javaDate = dformatter.parse(cdateInString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		java.sql.Date cSQLDate = new java.sql.Date(javaDate.getTime());
		return cSQLDate;
	}

	/**
	 * Method which return the current date
	 * 
	 * @return
	 */
	public static java.sql.Date getCurrentDateInSQLFormat() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String cdateInString = formatter.format(new Date());
		DateFormat dformatter = new SimpleDateFormat("yyyy-MM-dd");
		Date javaDate = null;
		try {
			javaDate = dformatter.parse(cdateInString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		java.sql.Date cSQLDate = new java.sql.Date(javaDate.getTime());
		return cSQLDate;
	}

	public static String getMonthIndexByName(String monthName) {
		String mname = monthName.toUpperCase();
		if ("JAN".equals(mname)) {
			return "01";
		} else if ("FEB".equals(mname)) {
			return "02";
		} else if ("MAR".equals(mname)) {
			return "03";
		} else if ("APR".equals(mname)) {
			return "04";
		} else if ("MAY".equals(mname)) {
			return "05";
		} else if ("JUN".equals(mname)) {
			return "06";
		} else if ("JUL".equals(mname)) {
			return "07";
		} else if ("AUG".equals(mname)) {
			return "08";
		} else if ("SEP".equals(mname)) {
			return "09";
		} else if ("OCT".equals(mname)) {
			return "10";
		} else if ("NOV".equals(mname)) {
			return "11";
		} else if ("DEC".equals(mname)) {
			return "12";
		}
		return "01";
	}

	public static String getMonthName(int index) {
		String montharr[] = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };
		return montharr[index];
	}

	public static int getCurrentMonth() {
		Calendar cnd = Calendar.getInstance();
		int month = cnd.get(Calendar.MONTH) + 1;
		return month;
	}

	/**
	 * get current date in dd-MMM-yyyy format 26-MAY-2013
	 * 
	 * @return
	 */
	public static String getCurrentDate() {
		// //////this is for date purpose
		String month, dd, year;
		Calendar cnd;
		String montharr[] = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };
		String datearr[] = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
				"16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };
		cnd = Calendar.getInstance();
		dd = datearr[cnd.get(Calendar.DATE) - 1];
		month = montharr[cnd.get(Calendar.MONTH)];
		year = "" + cnd.get(Calendar.YEAR);
		String sysdate = "" + dd + "-" + month + "-" + year;

		return sysdate;
	}

	public static Date getDateIntoSQLFormat(String cdate) {
		// 04-08-2014
		SimpleDateFormat fromUser = new SimpleDateFormat("dd-MM-yyyy");
		Date dateFromUser = null;
		try {
			dateFromUser = fromUser.parse(cdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateFromUser;
	}
	
	public static Date getDateIntoCalendarFormat(String cdate) {
		// 04-08-2014
		SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFromUser = null;
		try {
			dateFromUser = fromUser.parse(cdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateFromUser;
	}

	/**
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static boolean validateCurrentDate(String datepicker) throws ParseException {
		// long TWELVE_PM=24*60*60*1000;
		// 07/21/2014
		SimpleDateFormat fromUser = new SimpleDateFormat("MM/dd/yyyy");
		Date dateFromUser = fromUser.parse(datepicker);
		Date cdate = new Date();
		/*
		 * int chrs=cdate.getHours(); int cmins=cdate.getMinutes(); int
		 * cseconds=cdate.getSeconds();
		 */
		long currentTimeInMillis = cdate.getTime();
		// currentTimeInMillis=currentTimeInMillis-((cseconds*1000)+(cmins*60*1000)+(chrs*60*60*1000))+TWELVE_PM;
		long dateInMilliFromUser = dateFromUser.getTime();
		if (dateInMilliFromUser > currentTimeInMillis) {
			return false;
		}
		return true;
	}

	/**
	 *  Convert leave date format into mysql database date format
	 * @param pdate
	 * @return
	 */
	public static Date convertDateForLeave(Date pdate) {
		// 01/15/2016
		SimpleDateFormat fromUserDate = new SimpleDateFormat("MMM/dd/yyyy");
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MMM-dd");
		Date mysqlDateFormat = null;
		try {
			// 01/15/2016
			String stringDate = fromUserDate.format(pdate);
			String dateTokens[] = stringDate.split("/");// 01 , 15,2016
			String newDateFormat = dateTokens[2] + "-" + dateTokens[0] + "-" + dateTokens[1];
			mysqlDateFormat = myFormat.parse(newDateFormat);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return mysqlDateFormat;
	}
	
	/**
	 *  Convert leave date format into mysql database date format
	 * @param pdate
	 * @return
	 */
	public static String getDateIntoStringFormat(Date pdate) {
		// 01/15/2016
		SimpleDateFormat fromUserDate = new SimpleDateFormat("MM/dd/yyyy");
		String newDateFormat=null;
		try {
			// 01/15/2016
			String stringDate = fromUserDate.format(pdate);
			String dateTokens[] = stringDate.split("/");// 01 , 15,2016
			 newDateFormat = dateTokens[2] + "-" + dateTokens[0] + "-" + dateTokens[1];
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newDateFormat;
	}
	
	public static String dataConversionStrToDbFormat(String input){
		//08/24/2016
		String tempToken[]=input.split("/");
		return tempToken[2]+"-"+tempToken[0]+"-"+tempToken[1];
	}

	public static String convertddMMFormat(String inputString) {
		SimpleDateFormat fromUser = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat myFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date date = null;
		try {
			date = fromUser.parse(inputString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return myFormat.format(date);
	}

	public static String getCurrentMonth(String month) {
		Map<String, String> monthMap = new HashMap<String, String>();
		monthMap.put("January", "01");
		monthMap.put("February", "02");
		monthMap.put("March", "03");
		monthMap.put("April", "04");
		monthMap.put("May", "05");
		monthMap.put("June", "06");
		monthMap.put("July", "07");
		monthMap.put("August", "08");
		monthMap.put("September", "09");
		monthMap.put("October", "10");
		monthMap.put("November", "11");
		monthMap.put("December", "12");
		return monthMap.get(month);
	}
	
}
