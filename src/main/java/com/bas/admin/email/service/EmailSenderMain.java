package com.bas.admin.email.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class EmailSenderMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//System.out.println("_________________PPPPPPP______________________");
		org.springframework.context.ApplicationContext applicationContext=new ClassPathXmlApplicationContext("email-service.xml");
		MailService mailService=(MailService)applicationContext.getBean("mailService");
		System.out.println("___Please wait email is sending now___");
		mailService.sendHappyBirthdayEmail("nagendra.yadav.j2ee@gmail.com","nagendra.yadav.niit@gmail.com", "Wish you very happy birthaday.","I am fine here..");
		//System.out.println("_____________+++++++++++++++________________");
		//System.out.println("_____________+++++++++++++++________________");
		//System.out.println("_____________+++++++++++++++________________");
		//System.out.println("_____________+++++++++++++++________________");
	}
}
