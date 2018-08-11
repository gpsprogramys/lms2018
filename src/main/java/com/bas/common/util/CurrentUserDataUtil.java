package com.bas.common.util;

import javax.servlet.http.HttpSession;

import com.bas.common.constant.NavigationConstant;
import com.bas.employee.web.controller.form.LoginForm;

public class CurrentUserDataUtil {

	public static String getCurrentLoggedUser(HttpSession session){
		LoginForm loginForm=(LoginForm)session.getAttribute(NavigationConstant.USER_SESSION_DATA);
		return loginForm.getUserid();
	}


}
