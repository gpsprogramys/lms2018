package com.bas.admin.dao.entity;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author nagendra.yadav
 */
public class HolidayEntryEntity {

	private Date holidayDate;
	
	private String cdate;

	public String getCdate() {
		return cdate;
	}
	public void setCdate(String cdate) {
		this.cdate = cdate;
	}

	private String working;
	
	private String holiday;
	
	private String weekend;
	
	private String description;
	
	private String comment;
	
	private String holidayType;
	
	private byte[] image;
		
	private Timestamp doe;
	
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
	/**
	 * @return the holidayDate
	 */
	public Date getHolidayDate() {
		return holidayDate;
	}
	/**
	 * @param holidayDate the holidayDate to set
	 */
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
		return "HolidayEntryEntity [holidayDate=" + holidayDate + ", working="
				+ working + ", holiday=" + holiday + ", weekend=" + weekend
				+ ", description=" + description + "]";
	}

	
}
