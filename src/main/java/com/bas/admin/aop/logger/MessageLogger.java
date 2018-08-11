package com.bas.admin.aop.logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MessageLogger {
    
	   private static final Log logger = LogFactory.getLog(MessageLogger.class);
    
		@Before("@annotation(com.bas.admin.aop.logger.Loggable)")
	    public void myAdvice() {
			System.out.println("++++++...............................................+++++++");
	        logger.debug("_@_@_@_Magic of Advice with Custom Annotation Pointcut = ");
	    }
}
