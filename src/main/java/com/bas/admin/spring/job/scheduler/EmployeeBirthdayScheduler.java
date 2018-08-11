package com.bas.admin.spring.job.scheduler;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.bas.employee.service.BasFacultyService;
import com.bas.employee.web.controller.form.FacultyForm;


@Service
public class EmployeeBirthdayScheduler {
	
	
	@Autowired
	@Qualifier("BasFacultyServiceImpl")
	private BasFacultyService basFacultyService;
	/**
	 * You can opt for cron expression or fixedRate or fixedDelay
	 * <p>
	 * See Spring Framework 3 Reference:
	 * Chapter 25.5 Annotation Support for Scheduling and Asynchronous Execution
	 */
	//@Scheduled(fixedDelay=5000)
	//@Scheduled(fixedRate=5000)
	//https://javahunter.wordpress.com/2011/05/05/cronscheduler-in-spring/
	//0 0 24 * * ?
	//0 0 0 * * ?
	// @Scheduled(cron="*/60 * * * * ?")
	 public void sendBirthdayEmailsToEmployees() {
	     //write code to fecth all the employees who has birthday today
		 List<FacultyForm> facultyForms=basFacultyService.findFacultyWithBirthday();
		 for (FacultyForm facultyForm : facultyForms) {
			    ///
			 	System.out.println(facultyForm);
		 }
		 System.out.println("___sending birtday email");

	 }
}
