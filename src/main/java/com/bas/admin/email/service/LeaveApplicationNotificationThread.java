package com.bas.admin.email.service;


public class LeaveApplicationNotificationThread extends Thread {
	      
	private  LeaveApplicationEmailService leaveApplicationEmailService;
	private String from;
	private String to;
	private String body;
	private String fromDate;
	private String toDate;
	
	public LeaveApplicationNotificationThread(LeaveApplicationEmailService leaveApplicationEmailService, String fromEmail, String emailTo,
			String fromDate, String toDate) {
		this.leaveApplicationEmailService = leaveApplicationEmailService;
		this.from = fromEmail;
		this.to = emailTo;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}
	
	@Override
	public void run(){
		try {
			leaveApplicationEmailService.sendApplyEmailNotification(from,to,"Regarding Leave Application","",fromDate,toDate);
		}catch (Exception e) {
			System.out.println("###############Some issue in email sending ... "+e.getMessage());
		}
	}

}
