package com.bas.admin.aop.logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Dlowloader {
	
	public static void main(String[] args) {
		String url="https://www.facebook.com/";
		BufferedWriter bufferedWriter=null;
		try {
			URL url2=new URL(url);
			BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(url2.openStream()));
			String str="";
			 bufferedWriter=new BufferedWriter(new FileWriter(new File("D:/facebook.htm")));
			while((str=bufferedReader.readLine())!=null){
				bufferedWriter.write(str+"\n");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				bufferedWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
