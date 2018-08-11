package com.bas.soap.service.email;


/**
 * 
 * @author xxxxxxxxxxx
 *
 */
public class EmailSenderThread extends Thread {
	
	private EMailSenderServiceImpl eMailSenderService;
	private String toEMail;
	private String message;
	private String subject;
	
	public EmailSenderThread(EMailSenderServiceImpl eMailSenderService, String toEMail,
			String message,String subject) {
		this.eMailSenderService = eMailSenderService;
		this.toEMail = toEMail;
		this.message = message;
		this.subject=subject;
	}

	public EmailSenderThread(String tname){
	  super(tname);
	}
	
	@Override
	public void run() {
		try {
			eMailSenderService.sendMail("nagendra.synergisticit@gmail.com",toEMail,subject,message);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}