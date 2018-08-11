package com.bas.admin.web.report.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component("UserSession")
@Scope(value="session",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class UserSession {
	private String username;
	private String role;
	private String email;
	private String loginActive;

}
