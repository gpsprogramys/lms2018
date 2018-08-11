package com.bas.admin.exception.handler;

public class BASErrorMessage {
	private String status;
	private String code;
	private String message;
	private String ex;
	private String moreInfo;
	
	
	public BASErrorMessage(String status, String code, String message, String ex,
			String moreInfo) {
		this.status = status;
		this.code = code;
		this.message = message;
		this.ex = ex;
		this.moreInfo = moreInfo;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}




	public String getEx() {
		return ex;
	}


	public void setEx(String ex) {
		this.ex = ex;
	}



	public String getMoreInfo() {
		return moreInfo;
	}


	public void setMoreInfo(String moreInfo) {
		this.moreInfo = moreInfo;
	}


	@Override
	public String toString() {
		return "QuizErrorInfo [status=" + status + ", code=" + code
				+ ", message=" + message + ", type=" + ex + ", moreInfo="
				+ moreInfo + "]";
	}
	
	
	

}
