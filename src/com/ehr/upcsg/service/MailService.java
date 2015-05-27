package com.ehr.upcsg.service;

import com.ehr.upcsg.exceptions.DataAccessException;
import com.ehr.upcsg.exceptions.EmailDoesNotExistException;
import com.ehr.upcsg.exceptions.MailServiceException;
import com.ehr.upcsg.exceptions.UserExistException;
import com.ehr.upcsg.model.User;


public interface MailService {

	void sendPasswordRecovery(User user, String password) throws UserExistException, EmailDoesNotExistException, MailServiceException, DataAccessException;

	void sendConfirmationLink(String recipient, String url) throws EmailDoesNotExistException, MailServiceException, UserExistException, DataAccessException;
	
	void sendMail(User user, String subject, String content)
			throws UserExistException, EmailDoesNotExistException,
			MailServiceException;
	
}
