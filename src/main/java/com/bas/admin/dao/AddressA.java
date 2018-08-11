package com.bas.admin.dao;

/**
 * 
 * @author this pcFss
 *
 */
public class AddressA {
	
	String name;
	String password;
	Mock mock;

	public Mock getMock() {
		return mock;
	}

	public void setMock(Mock mock) {
		this.mock = mock;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "AddressA [name=" + name + ", password=" + password + ", mock=" + mock + "]";
	}

}
