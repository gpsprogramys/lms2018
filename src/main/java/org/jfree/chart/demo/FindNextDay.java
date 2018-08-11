package org.jfree.chart.demo;


public class FindNextDay {

	public static void main(String args[]) {
		/*
		 * This is our input date. We will be generating the previous and next
		 * day date of this one
		 */
		String fromDate = "2014-01-01";
		// split year, month and days from the date using StringBuffer.
		StringBuffer sBuffer = new StringBuffer(fromDate);
		String year = sBuffer.substring(2, 4);
		String mon = sBuffer.substring(5, 7);
		String dd = sBuffer.substring(8, 10);

		String modifiedFromDate = dd + '/' + mon + '/' + year;
		int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
		/*
		 * Use SimpleDateFormat to get date in the format as passed in the
		 * constructor. This object can be used to covert date in string format
		 * to java.util.Date and vice versa.
		 */
		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
				"dd/MM/yy");
		java.util.Date dateSelectedFrom = null;
		java.util.Date dateNextDate = null;
		java.util.Date datePreviousDate = null;

		// convert date present in the String to java.util.Date.
		try {
			dateSelectedFrom = dateFormat.parse(modifiedFromDate);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// get the next date in String.
		String nextDate = dateFormat.format(dateSelectedFrom.getTime()
				+ MILLIS_IN_DAY);

		// get the previous date in String.
		String previousDate = dateFormat.format(dateSelectedFrom.getTime()
				- MILLIS_IN_DAY);

		// get the next date in java.util.Date.
		try {
			dateNextDate = dateFormat.parse(nextDate);
			System.out.println("Next day's date: " + dateNextDate);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// get the previous date in java.util.Date.
		try {
			datePreviousDate = dateFormat.parse(previousDate);
			System.out.println("Previous day's date: " + datePreviousDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
