package com.ehr.upcsg.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehr.upcsg.dao.UserDao;
import com.ehr.upcsg.exceptions.DataAccessException;
import com.ehr.upcsg.exceptions.EmailDoesNotExistException;
import com.ehr.upcsg.exceptions.MailServiceException;
import com.ehr.upcsg.exceptions.UserExistException;
import com.ehr.upcsg.model.User;
import com.ehr.upcsg.model.VerificationToken;


@Service("mailService")
public class MailServiceGoogleImpl implements MailService{
	
    
	@Autowired
	private UserDao userDao;
	
	@Override
	public void sendMail(User user, String subject, String content) 
			throws UserExistException, EmailDoesNotExistException, 
					MailServiceException {
				
		Properties mailingProperties = new Properties();
		 
        InputStream inputStream = getClass().getResourceAsStream("/emailConfiguration.properties");
        
        try {
			mailingProperties.load(inputStream);
		} catch (IOException e) {
			throw new MailServiceException("Mailing service is disabled or unavailable. Contact your system administrator.");
		}

        Session session = Session.getInstance(mailingProperties);
        MimeMessage message = new MimeMessage(session);
                
        String username = (String)mailingProperties.get("mail.smtp.user");
        System.out.println(username);
        String password = (String)mailingProperties.get("mail.smtp.password");
        String host = (String)mailingProperties.get("mail.smtp.host");
        System.out.println("sending from ");
        System.out.println(username+password);
        try {
            message.setFrom(new InternetAddress(username));
            InternetAddress toAddress = new InternetAddress(user.getEmail());

            message.addRecipient(Message.RecipientType.TO, toAddress);
            message.setSubject(subject);
            message.setContent(content,"text/html");

            Transport transport = session.getTransport("smtp");
            transport.connect(host, username, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (AddressException ae) {
        		System.out.println("cannot send");
            throw new EmailDoesNotExistException("Cannot send email. Email does not exist.");
        } catch (MessagingException me) {
        		System.out.println("Busy");
        		throw new MailServiceException("Mailing service is busy. Please try again later.");
        }        
	}
	
/* TODO: confirmation link for now, send new password;
	@Override
	public void sendConfirmationLink(String recipient, String url)
			throws EmailDoesNotExistException, MailServiceException, 
			UserExistException {
		User user = userService.findUserByEmail(recipient);
		if(user==null) 
			throw new UserExistException("User does not exist.");

		String subject = "Confirmation link";
		
		//generate verification link
        String token = RandomStringUtils.randomAlphabetic(10);
        VerificationToken verificationToken = new VerificationToken(token, user);
        
        //generate content of password recovery mail
        MailGenerator mailGenerator = new MailGenerator();
        String content = mailGenerator.generateForgotPasswordMail(user.getFirstName(), newPassword);
         
		sendMail(user, subject, content);		
	}
	*/
	@Override
	public void sendConfirmationLink(String recipient, String url)
			throws EmailDoesNotExistException, MailServiceException, 
			UserExistException {
		User user = userDao.findUserByEmail(recipient);
		if(user==null) 
			throw new UserExistException("User does not exist.");

		String subject = "EHR-System Password";
		
        //generate content of password recovery mail
        MailGenerator mailGenerator = new MailGenerator();
        String content = mailGenerator.generateConfirmationMail(user.getUsername(), url);
         
		sendMail(user, subject, content);		
	}

	@Override
	public void sendPasswordRecovery(User user, String password)
			throws UserExistException, EmailDoesNotExistException,
			MailServiceException, DataAccessException {
		String subject = "Password Recovery";
	
        //generate content of password recovery mail
        MailGenerator mailGenerator = new MailGenerator();
        String content = mailGenerator.generateForgotPasswordMail(user.getFirstName(), password);
         
		sendMail(user, subject, content);
	}
	
}
