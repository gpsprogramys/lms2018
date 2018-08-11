package com.bas.admin.aop.logger;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect  // making this WelcomeDao as advice 
public class WelcomeDao {
	
	@Before("execution(* com.bas.faculty.dao.impl.*.*(..))")
    public void logBeforeTime(JoinPoint joinPoint) {
             System.out.println("Name of the method " + joinPoint.getSignature().getName());
             Date cdate=new Date();
             System.out.println("++++Current Date is  = "+cdate);
             System.out.println("___________________________________");
    }


}
