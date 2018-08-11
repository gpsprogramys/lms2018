package com.bas.common.dao.entity;

/**
 * 
 * @author xxxxxxxxxx
 *
 */
public class MessageStatusEntity {

	private String status;
	private String message;
	private String warning;
	

	public String getWarning() {
		return warning;
	}

	public void setWarning(String warning) {
		this.warning = warning;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "MessageStatusEntity [status=" + status + ", message=" + message + ", warning=" + warning + "]";
	}

}
