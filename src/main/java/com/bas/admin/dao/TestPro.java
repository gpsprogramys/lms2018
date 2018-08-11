package com.bas.admin.dao;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

public class TestPro {

	 public static void main(String[] args) throws IllegalAccessException, InvocationTargetException {
		AddressA addressA=new AddressA();
		addressA.setName("Amama");
		addressA.setPassword("Aaaa");
		AddressB addressB=new AddressB();
		BeanUtils.copyProperties(addressB, addressA);
		System.out.println(addressB);
		 
		 
	}

}
