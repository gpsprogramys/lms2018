package com.bas.common.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RemoteDateController {

	
	/**
	 *  Method which returns the server data into MM/dd/yyyy format
	 * @return
	 */
	@RequestMapping(value="serverDate",method=RequestMethod.GET)
	public @ResponseBody String findTodaysServerDate(){
		SimpleDateFormat calendarDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date currentDate = new Date();
		String currentDateString = calendarDateFormat.format(currentDate);
		return currentDateString;
	}
	
}
