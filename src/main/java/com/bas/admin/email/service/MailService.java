package com.bas.admin.email.service;

import java.io.UnsupportedEncodingException;
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
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * @author 
 * nagendra.yadav 
 * This code is used to send the email to everyone as a
 * notification
 * 
 */
@Service("mailService")
public class MailService {

	@Autowired
	private JavaMailSender mailSender;

/*	@Autowired
	private SimpleMailMessage alertMailMessage;*/
	
	public void sendNotificationMail(String from, String to, String subject, String msg) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(msg);
		mailSender.send(message);	
	}
	
	public void sendNoticeEMail(String from, String to, String subject, String body) {
				MimeMessage message = mailSender.createMimeMessage();
				try {
					try {
						InternetAddress fromAddress=new InternetAddress(from, "Director Sir");
						message.setFrom(fromAddress);
						//message.setSender(fromAddress);
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//message.setSender(new InternetAddress(from));
					 message.setRecipients(Message.RecipientType.TO,
					            InternetAddress.parse(to));
					message.setSubject(subject);
					message.setSentDate(new Date());
					// This mail has 2 part, the BODY and the embedded image
			         MimeMultipart multipart = new MimeMultipart("related");
			      // first part (the html)
			         BodyPart messageBodyPart = new MimeBodyPart();
			         messageBodyPart.setContent(body, "text/html");
			         // add it
			         multipart.addBodyPart(messageBodyPart);
			         // put everything together
			         message.setContent(multipart);
			          mailSender.send(message);	
				} catch (MessagingException e) {
					throw new MailParseException(e);
				}
	}

	public void sendMail(String from, String to, String subject, String body) {
		// SimpleMailMessage message = new SimpleMailMessage();
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body);
			FileSystemResource file = new FileSystemResource("C:/reports/attendance.pdf");
			helper.addAttachment(file.getFilename(), file);
			mailSender.send(message);	
		} catch (MessagingException e) {
			throw new MailParseException(e);
		}
	}
	
	private String birthdayMessage="<table border=\"0\">"+
  "   <tr>"+
   "  	<td>"+
    " 	   <img style=\"border-radius:70%;\"  alt=\"\" src=\"cid:photo\" width=\"100\" height=\"100\" />"+
     "	</td>"+
     "	</tr>"+
     "	<tr>"+
     "	<td>"+
     "	   <font style=\"background-color:#F5DB00;color:black;font-weight: bold;font-size:18px;\">Nagendra Yadav - CS</font>"+
     "	</td>"+
     "	</tr>"+
     "	<tr>"+
     "	<td>"+
     "	  &nbsp;"+
     "	</td>"+
     "	</tr>"+
     "	<tr>"+
     "	<td>"+
     "		<font style=\"color: blue;font-weight: bold;font-size:18px;\">Wish you very happy birthday Nagendra Sir!</font> "+
     "	</td>"+
     "	</tr>"+
     "	<tr>"+
     "	<td>"+
     "	  <img src=\"cid:image\" style=\"width:800px;\">"+
     "	</td>"+
     "</tr>"+
     "	<tr>"+
     "	<td>"+
     "	 Regards,"+
     "	</td>"+
     "	</tr>"+
     "	    	<tr>"+
     "	<td>"+
     "	 <font style=\"color: black;font-weight: bold;font-size:16px;\">AMS Admin</font>"+
     "	</td>"+
     "	</tr>"+
     "	<tr>"+
  "</table>";
	
	public void sendHappyBirthdayEmail(String from, String to, String subject, String body) {
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
	         String htmlText = "<H1>Hello</H1>";
	         messageBodyPart.setContent(birthdayMessage, "text/html");
	         // add it
	         multipart.addBodyPart(messageBodyPart);
			// message.setContent(birthdayMessage, "text/html" ); 
			/*FileSystemResource file = new FileSystemResource("C:/reports/attendance.pdf");
			helper.addAttachment(file.getFilename(), file);*/
			  // Create the message body part
	        /*    BodyPart messageBodyPart = new MimeBodyPart();
	            messageBodyPart.setContent(birthdayMessage, "text/html");*/
	 
	           /* // Create a multipart message for attachment
	            Multipart multipart = new MimeMultipart();*/
	            // Set text message part
	          /*  multipart.addBodyPart(messageBodyPart);
	            messageBodyPart = new MimeBodyPart();*/
	            // Valid file location
	         /*   String filename = "E:/test/happybirthday.jpg";
	            DataSource source = new FileDataSource(filename);
	            messageBodyPart.setDataHandler(new DataHandler(source));
	            messageBodyPart.setFileName(filename);
	            // Trick is to add the content-id header here
	            messageBodyPart.setHeader("Content-ID", "image_id");
	            multipart.addBodyPart(messageBodyPart);
	            System.out.println("\n4th ===> third part for displaying image in the email body..");
	            messageBodyPart = new MimeBodyPart();
	            messageBodyPart.setContent("<br><h3>Find below attached image</h3>"
	                    + "<img src='cid:image_id'>", "text/html");
	            multipart.addBodyPart(messageBodyPart);
	            message.setContent(multipart);*/
	      // second part (the image)
	         messageBodyPart = new MimeBodyPart();
	         DataSource fds = new FileDataSource("F:/img/happybirthday.jpg");
	         messageBodyPart.setDataHandler(new DataHandler(fds));
	         messageBodyPart.setHeader("Content-ID", "<image>");
	         multipart.addBodyPart(messageBodyPart);
	         
	         messageBodyPart = new MimeBodyPart();
	         DataSource photoDs = new FileDataSource("F:/img/nagendra.png");
	         messageBodyPart.setDataHandler(new DataHandler(photoDs));
	         messageBodyPart.setHeader("Content-ID", "<photo>");
	         // add image to the multipart
	         multipart.addBodyPart(messageBodyPart);

	         // put everything together
	         message.setContent(multipart);
	 
	            System.out.println("\n5th ===> Finally Send message..");
	            mailSender.send(message);	
		} catch (MessagingException e) {
			throw new MailParseException(e);
		}
	}

	/*public void sendAlertMail(String alert) {
		SimpleMailMessage mailMessage = new SimpleMailMessage(alertMailMessage);
		mailMessage.setText(alert);
		mailSender.send(mailMessage);
	}*/

}