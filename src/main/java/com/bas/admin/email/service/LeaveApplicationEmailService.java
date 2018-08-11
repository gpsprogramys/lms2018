package com.bas.admin.email.service;

import java.util.Date;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service("LeaveApplicationEmailService")
public class LeaveApplicationEmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	
	public void sendApplyEmailNotification(String from, String to, String subject, String body,String fromDate,String toDate) {
		
		// SimpleMailMessage message = new SimpleMailMessage();
		MimeMessage message = mailSender.createMimeMessage();
		try {
			message.setFrom(new InternetAddress(from));
			 message.setRecipients(Message.RecipientType.TO,
			            InternetAddress.parse(to));
			message.setSubject(subject);
			message.setSentDate(new Date());
			
			// This mail has 2 part, the BODY and the embedded image
	         MimeMultipart multipart = new MimeMultipart("related");
	      // first part (the html)
	         BodyPart messageBodyPart = new MimeBodyPart();
	         messageBodyPart.setContent(LMSEmailComposer.getLeaveApplicationContent(fromDate, toDate), "text/html");
	         // add it
	         multipart.addBodyPart(messageBodyPart);
	         
	         messageBodyPart = new MimeBodyPart();
	         DataSource fds = new FileDataSource("F:/img/leave.jpg");
	         messageBodyPart.setDataHandler(new DataHandler(fds));
	         messageBodyPart.setHeader("Content-ID", "<image>");
	         multipart.addBodyPart(messageBodyPart);
	         
	        /* messageBodyPart = new MimeBodyPart();
	         DataSource photoDs = new FileDataSource("F:/img/nagendra.png");
	         messageBodyPart.setDataHandler(new DataHandler(photoDs));
	         messageBodyPart.setHeader("Content-ID", "<photo>");
	         // add image to the multipart
	         multipart.addBodyPart(messageBodyPart);*/

	         // put everything together
	         message.setContent(multipart);
	 
	            System.out.println("\n5th ===> Finally Send message..");
	            mailSender.send(message);	
		} catch (MessagingException e) {
			throw new MailParseException(e);
		}
	}
	
	
}
