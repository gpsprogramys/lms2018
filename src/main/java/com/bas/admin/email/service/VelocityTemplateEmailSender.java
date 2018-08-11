package com.bas.admin.email.service;

import java.io.StringWriter;
import java.net.URL;
import java.util.Date;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service("VelocityTemplateEmailSender")
@Scope("singleton")
public class VelocityTemplateEmailSender {

	@Autowired
	@Qualifier("mailSender")
	private JavaMailSender mailSender;

	@Autowired
	@Qualifier("velocityEngine")
	private VelocityEngine velocityEngine;

	public void sendMail(Mail mail) {
		MimeMessage message = mailSender.createMimeMessage();
		try {
			InternetAddress fromAddress = new InternetAddress(
					mail.getMailFrom(), "Director Sir");
			message.setFrom(fromAddress);
			// message.setSender(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(mail.getMailTo()));
			message.setSubject(mail.getMailSubject());
			message.setSentDate(new Date());
			// This mail has 2 part, the BODY and the embedded image
			MimeMultipart multipart = new MimeMultipart("related");
			// first part (the html)
			BodyPart messageBodyPart = new MimeBodyPart();
			
			Template template = velocityEngine.getTemplate("./templates/"
					+ mail.getTemplateName());
			
			VelocityContext velocityContext = new VelocityContext();
			velocityContext.put("firstName", "GPS PVT. LTD.");
			velocityContext.put("lastName", "Software");
			velocityContext.put("location", "Ghaziabad");
			velocityContext.put("profilePic", "raj.jpg");
			
			StringWriter stringWriter = new StringWriter();
			
			template.merge(velocityContext, stringWriter);
			
			System.out.println(" :-"+stringWriter.toString());
	         messageBodyPart.setContent(stringWriter.toString(), "text/html");
			multipart.addBodyPart(messageBodyPart);
			
			 messageBodyPart = new MimeBodyPart();
			// DataSource fds = new FileDataSource("D:/college.jpg");
	         messageBodyPart.setDataHandler(new DataHandler(new URL("http://lms.gpsprogramys.com/img/college.jpg")));
	         messageBodyPart.setHeader("Content-ID", "<image>");
	         multipart.addBodyPart(messageBodyPart);
	         
			// put everything together
			message.setContent(multipart);
			mailSender.send(message);
		} catch (Exception exe) {
			exe.printStackTrace();
		}
	}
}
