package com.bas.common.dao;

import com.bas.employee.dao.entity.LoginEntity;

/**
 * 
 * @author 234u
 *  interface to expose functionality for authentication of the users
 */
public interface LoginDao {
	public LoginEntity validateUser(String username,String password);
	public String updatePassword(String email,String password) ;
	public 	String changePasswordByUserId(String userid, String newPassword);
}
