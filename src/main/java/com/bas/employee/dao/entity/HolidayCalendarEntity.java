package com.bas.employee.dao.entity;

import java.util.Arrays;
import java.util.Date;

public class HolidayCalendarEntity {

	private int sno;
	private Date holidayDate;
	private String holidayName;
	private String holidayDescription;
	private byte[] holidayImage;

	public byte[] getHolidayImage() {
		return holidayImage;
	}

	public void setHolidayImage(byte[] holidayImage) {
		this.holidayImage = holidayImage;
	}

	public int getSno() {
		return sno;
	}

	public void setSno(int sno) {
		this.sno = sno;
	}

	public Date getHolidayDate() {
		return holidayDate;
	}

	public void setHolidayDate(Date holidayDate) {
		this.holidayDate = holidayDate;
	}

	public String getHolidayName() {
		return holidayName;
	}

	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}

	public String getHolidayDescription() {
		return holidayDescription;
	}

	public void setHolidayDescription(String holidayDescription) {
		this.holidayDescription = holidayDescription;
	}

	@Override
	public String toString() {
		return "HolidayCalendarEntity [sno=" + sno + ", holidayDate="
				+ holidayDate + ", holidayName=" + holidayName
				+ ", holidayDescription=" + holidayDescription
				+ ", holidayImage=" + Arrays.toString(holidayImage) + "]";
	}

}
