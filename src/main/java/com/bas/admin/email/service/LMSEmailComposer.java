package com.bas.admin.email.service;

public class LMSEmailComposer {
	
	public static String getLeaveApplicationContent(String from,String to){
		String compose="<table><tr>	<td>Dear Sir,"+
		"</tr>"+
		"<tr>"+
			"<td>&nbsp;&nbsp;</td>"+
		"</tr>"+
		"<tr>"+
			"<td>Mr Amit Kumar has applied leave from "+from+" to "+to+". Please login into LMS for more detail.</td>"+
		"</tr>"+
		"<tr>"+
			"<td><br /> Thanks, <br /> <br /> <b style=\"color: blue;\">Nagendra"+
					"Kumar</b></td>"+
		"</tr>"+
		"<tr>"+
			"<td><img src=\"cid:image\" /></td>"+
		"</tr>"+
	"</table>";
		return compose;
	}
	
}
