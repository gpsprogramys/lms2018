package com.bas.admin.email.service;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class EmailTestListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		WebApplicationContext webApplicationContext=ContextLoader.getCurrentWebApplicationContext();
		VelocityTemplateEmailSender velocityTemplateEmailSender=(VelocityTemplateEmailSender)webApplicationContext.getBean("VelocityTemplateEmailSender");
	    	Mail mail = new Mail();
		  mail.setMailFrom("hietams@gmail.com");
		  mail.setMailTo("nagendra.yadav.niit@gmail.com");
		  mail.setMailSubject("Subject - Send Email using Spring Velocity Template");
		  mail.setTemplateName("emailtemplate.vm");
		  System.out.println("++++++++++++++++Please wait sending the email+++++++++++++++++++");
		  velocityTemplateEmailSender.sendMail(mail);
		  
		 System.out.println("_@)@)@)@(@*@^Sending email please wait@^@^@^@^@^@");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
