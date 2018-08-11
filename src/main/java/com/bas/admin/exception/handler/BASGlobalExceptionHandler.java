package com.bas.admin.exception.handler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bas.common.constant.NavigationConstant;

/**
 * 
 * @author nagendra
 * This class is for handling global exception
 *
 */
@ControllerAdvice
public class BASGlobalExceptionHandler {
	
	/**
     * Initiate Logger for this class
     */
    private static final Log logger = LogFactory.getLog(BASGlobalExceptionHandler.class);
    
    public BASGlobalExceptionHandler(){
    	System.out.println("_!__@))))))))BASGlobalExceptionHandler))))))))))(*******************");
    	System.out.println("_!__@))))))))BASGlobalExceptionHandler))))))))))(*******************");
    }
	
	@ExceptionHandler(SQLException.class)
	 String handlerDbException(HttpServletRequest req, Exception ex,Model model) {
		BASErrorMessage basErrorMessage=new BASErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.toString(),"5000" , ex.getMessage(), ex.getClass().toString(), req.getRequestURL().toString());
		errorLoggerAsString(ex);
		model.addAttribute("basErrorMessage", basErrorMessage);
		return NavigationConstant.COMMON_PREFIX_PAGE+NavigationConstant.COMMON_ERROR_PAGE;
	} 
    
  	@ExceptionHandler(Exception.class)
  	 String handlerGlobalException(HttpServletRequest req, Exception ex,Model model) {
  		BASErrorMessage basErrorMessage=new BASErrorMessage(HttpStatus.OK.toString(),"5000" , ex.getMessage(), ex.getClass().toString(), req.getRequestURL().toString());
  		errorLoggerAsString(ex);
  		model.addAttribute("basErrorMessage", basErrorMessage);
  		return NavigationConstant.COMMON_PREFIX_PAGE+NavigationConstant.COMMON_ERROR_PAGE;
  	} 
    
    
    private void errorLoggerAsString(Exception ex){
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
