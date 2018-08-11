package com.bas.admin.exception.handler;

import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bas.common.constant.NavigationConstant;

@Controller
public class GlobalServletExceptionHandler {
	/**
     * Initiate Logger for this class
     */
    private static final Log logger = LogFactory.getLog(GlobalServletExceptionHandler.class);
    
    @RequestMapping(value="/errorPage",method=RequestMethod.GET)
	 String handlerDbException(HttpServletRequest req, Exception ex,Model model) {
		BASErrorMessage basErrorMessage=new BASErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.toString(),"5000" , ex.getMessage(), ex.getClass().toString(), req.getRequestURL().toString());
		errorLoggerAsString(ex);
		model.addAttribute("basErrorMessage", basErrorMessage);
		return NavigationConstant.COMMON_PREFIX_PAGE+NavigationConstant.COMMON_ERROR_PAGE;
	} 
    
    @RequestMapping(value="/errorPage",method=RequestMethod.POST)
  	 String handlerDbExceptionPost(HttpServletRequest req, Exception ex,Model model) {
  		BASErrorMessage basErrorMessage=new BASErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.toString(),"5000" , ex.getMessage(), ex.getClass().toString(), req.getRequestURL().toString());
  		errorLoggerAsString(ex);
  		model.addAttribute("basErrorMessage", basErrorMessage);
  		return NavigationConstant.COMMON_PREFIX_PAGE+NavigationConstant.COMMON_ERROR_PAGE;
  	} 
  	
	
    private void errorLoggerAsString(Exception ex){
    	ex.printStackTrace();
		 StringWriter sw = new StringWriter();
		 PrintWriter pw = new PrintWriter(sw);
		 ex.printStackTrace(pw);
		 // stack trace as a string
		 String derrorMessage=sw.toString(); 
		 if(logger.isErrorEnabled()){
				logger.error(derrorMessage);
		}
   }
}
