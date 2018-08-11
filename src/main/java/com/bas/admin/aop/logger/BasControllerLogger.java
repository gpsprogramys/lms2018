
package com.bas.admin.aop.logger;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 
 * @author aaxx
 * 
 *  This is an advice which is going to log data for dao layer
 *
 */
@Component
@Aspect // this class is going to work as an advice
public class BasControllerLogger {
	
    private static final Log logger = LogFactory.getLog(BasControllerLogger.class);
	
	@Around("execution(* com.bas.hr.web.controller.*.*(..))")
	public Object  daoLogger(ProceedingJoinPoint proceedingJoinPoint){
		if(logger.isDebugEnabled()) {
		logger.debug("_____________________________________________________________");
		logger.debug("Name of the method " + proceedingJoinPoint.getSignature().getName());
		}
		if(logger.isInfoEnabled()) {
			logger.info("Input parameters .."+Arrays.asList(proceedingJoinPoint.getArgs()));
		}	
		Object value = null;
		 try {            //go ahead and call actual method and return the value....
	            value = proceedingJoinPoint.proceed();
	        } catch (Throwable e) {
	            e.printStackTrace();
	        }
		 ///Here we can write code for after advice
		 if(logger.isDebugEnabled()) {
				logger.debug("output from the method = "+value); 	
				logger.debug( proceedingJoinPoint.getSignature().getName()+" execution is finised.....");
			}
		 return value;
	}
}
