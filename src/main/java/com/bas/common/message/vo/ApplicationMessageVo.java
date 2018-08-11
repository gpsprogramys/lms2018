package com.bas.common.message.vo;

/**
 * 
 * @author nagendra
 *
 */
public class ApplicationMessageVo {

	private String status;
	private String message;
	private String description;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ApplicationMessageVo [status=" + status + ", message=" + message + ", description=" + description + "]";
	}

}
