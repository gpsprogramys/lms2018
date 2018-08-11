package com.bas.common.dao.entity;

import java.util.Arrays;
import java.util.Date;

public class MessageBoardEntity {

	private int mid;
	private String subject;
	private String message;
	private String category;
	private String from;
	private int totalMessage;
	private byte[] image;
	private Date domessage;
	
	public int getTotalMessage() {
		return totalMessage;
	}

	public void setTotalMessage(int totalMessage) {
		this.totalMessage = totalMessage;
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "MessageBoardEntity [mid=" + mid + ", subject=" + subject
				+ ", category=" + category + ", message=" + message + ", from="
				+ from + ", domessage=" + domessage + ", image="
				+ Arrays.toString(image) + "]";
	}

	public Date getDomessage() {
		return domessage;
	}

	public void setDomessage(Date domessage) {
		this.domessage = domessage;
	}

}
