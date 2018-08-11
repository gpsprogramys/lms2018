package com.bas.employee.utility;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MonthUtility {

	private int month;

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}
	
	 public int getYear() {
	        Date date = new Date();
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        int year = cal.get(Calendar.YEAR);        
	        return year;
	    }

	public int getDate() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month_value = cal.get(Calendar.MONTH);		
		return month_value+1;
	}

	public Map<Integer, String> monthList() {
		Map<Integer, String> monthList = new HashMap<Integer, String>();
		monthList.put(1, "Jan");
		monthList.put(2, "Feb");
		monthList.put(3, "Mar");
		monthList.put(4, "Apr");
		monthList.put(5, "May");
		monthList.put(6, "Jun");
		monthList.put(7, "Jul");		
		monthList.put(8, "Aug");
		monthList.put(9, "Sep");
		monthList.put(10, "Oct");
		monthList.put(11, "Nov");
		monthList.put(12, "Dec");		
		return monthList;
	}

	public Map<Integer, String> getMonthList(int inputMonth){
		Map<Integer, String> monthList = monthList();
		Map<Integer, String> monthListNew =  new HashMap<Integer, String>();				

		if(inputMonth!=getDate()){
			for(Map.Entry<Integer, String> entry : monthList.entrySet()){	
				if(!(getDate()==entry.getKey())){					
					monthListNew.put(entry.getKey(), entry.getValue());
				}else{
					break;
				}
			}
		}else {
			for(Map.Entry<Integer, String> entry1 : monthList.entrySet()){		
				if(!(inputMonth==entry1.getKey())){					
					monthListNew.put(entry1.getKey(), entry1.getValue());
				}else{
					break;
				}
			}
		}			
		monthListNew.put(getDate(),monthList.get(getDate()));
		return monthListNew;
	}

}
