package com.bas.admin.dao;

public class AddressB {

	int name;
	String password;
	String mock;

	public String getMock() {
		return mock;
	}

	public void setMock(String mock) {
		this.mock = mock;
	}

	public int getName() {
		return name;
	}

	public void setName(int name) {
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
		return "AddressA [name=" + name + ", password=" + password + "]";
	}

}
