package com.bas.admin.service;

import java.util.List;

import com.bas.admin.web.controller.form.HolidayEntryForm;

/**
 * 
 * @author Amogh
 *
 */
public interface HolidayEntryService {
	
	public String addHolidayEntry(HolidayEntryForm holidayEntryForm);
	public String editHolidayEntry(HolidayEntryForm holidayEntryForm);
	public String deleteHolidayEntry(String date);
	public List<HolidayEntryForm> findHolidayEntry();
	public HolidayEntryForm findHolidayEntryByDate(String date);
	public byte[] getHoildayImageByDate(String date);
	public List<HolidayEntryForm> getHolidayId(int month);
	 

}
