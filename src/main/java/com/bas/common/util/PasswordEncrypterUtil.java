package com.bas.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 
 * @author nagendra.yadav
 *  This class is used to encrypt the password for user 
 * 
 */

@Service("Encrypter")
@Scope("singleton")
public class PasswordEncrypterUtil {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the password ");
		String password = scanner.next();
		String epassword = new PasswordEncrypterUtil().encrypt(password);
		System.out.println("password  = " + epassword);
	}

	public PasswordEncrypterUtil() {

	}

	public String encrypt(String word) {
		String hash = "";
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			byte[] data = word.getBytes();
			sha.update(data);
			byte[] msgDigest = sha.digest();
			hash = hexToString(msgDigest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hash;
	}

	private String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff; // remove higher bits, sign
			if (val < 16)
				buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
}
