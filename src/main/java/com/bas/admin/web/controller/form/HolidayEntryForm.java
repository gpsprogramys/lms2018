package com.bas.admin.web.controller.form;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 
 * @author nagendra.yadav
 * 
 */
public class HolidayEntryForm {

	private Date holidayDate;
	private String working;
	private String holiday;
	private String weekend;
	private String description;
	private String comment;
	private String holidayType;
	private byte[] image;
	private String color;
	private Timestamp doe;

	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getHolidayType() {
		return holidayType;
	}
	public void setHolidayType(String holidayType) {
		this.holidayType = holidayType;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public Timestamp getDoe() {
		return doe;
	}
	public void setDoe(Timestamp doe) {
		this.doe = doe;
	}
	public Date getHolidayDate() {
		return holidayDate;
	}
	public void setHolidayDate(Date holidayDate) {
		this.holidayDate = holidayDate;
	}
	public String getWorking() {
		return working;
	}
	public void setWorking(String working) {
		this.working = working;
	}
	public String getHoliday() {
		return holiday;
	}
	public void setHoliday(String holiday) {
		this.holiday = holiday;
	}
	public String getWeekend() {
		return weekend;
	}
	public void setWeekend(String weekend) {
		this.weekend = weekend;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "HolidayEntryEntity [date=" + holidayDate + ", working=" + working
				+ ", holiday=" + holiday + ", weekend=" + weekend
				+ ", description=" + description + "]";
	}
	
}
