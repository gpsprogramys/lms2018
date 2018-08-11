package com.bas.soap.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service("EMailSenderServiceImpl")
public class EMailSenderServiceImpl {


	@Autowired
	@Qualifier("mailSender")
	private MailSender mailSender;
	
	public void sendMail(String from, String to, String subject, String body) {
		    SimpleMailMessage message = new SimpleMailMessage();
	        message.setFrom(from);
	        message.setTo(to);
	        message.setSubject(subject);
	        message.setText(body);
	        mailSender.send(message);
	}

}
