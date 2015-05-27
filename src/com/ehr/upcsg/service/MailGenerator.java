package com.ehr.upcsg.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.stereotype.Service;

import com.ehr.upcsg.exceptions.MailServiceException;

public class MailGenerator {
	
	public String generateConfirmationMail(String name, String url) throws MailServiceException {
		return generateMail(name, url, "/email-templates/confirm-link-email.html");
	}
	
	public static void main(String[] args){
		MailGenerator mailGenerator = new MailGenerator();
		try {
			System.out.println(mailGenerator.generateConfirmationMail("angelu", "111111"));
		} catch (MailServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String generateMail(String name, String value, String filename) throws MailServiceException {
		InputStream inputStream = getClass().getResourceAsStream(filename);
		
		String mail="";
		
		try {
			
			BufferedReader emailBuilder = new BufferedReader(new InputStreamReader(inputStream));
		
			while(emailBuilder.ready()){
				mail+=emailBuilder.readLine();
			}
		
			emailBuilder.close();
			
			mail = mail.replaceFirst("%name%", name );
			mail = mail.replaceFirst("%value%", value);
		
		} catch (IOException e) {
			e.printStackTrace();
			throw new MailServiceException("Mailing service is busy. Please try again later.");
		}
		
		return mail;
	}

	public String generateForgotPasswordMail(String name,String password) throws MailServiceException {
		System.out.println("In generateForgotpassword: "+name+password);
		return generateMail(name, password, "/email-templates/forgot-password-email.html");
	}
	
}
/* Got from onbcrm*/