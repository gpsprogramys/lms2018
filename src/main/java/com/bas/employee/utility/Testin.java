package com.bas.employee.utility;

import java.util.Calendar;
import java.util.Date;

public class Testin {

	public static void main(String[] args) {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month_value = cal.get(Calendar.MONTH);		
		System.out.println(month_value);
}
}