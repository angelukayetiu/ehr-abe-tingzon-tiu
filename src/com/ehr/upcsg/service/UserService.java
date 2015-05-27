package com.ehr.upcsg.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ehr.upcsg.exceptions.DataAccessException;
import com.ehr.upcsg.exceptions.EmailDoesNotExistException;
import com.ehr.upcsg.exceptions.MailServiceException;
import com.ehr.upcsg.exceptions.UserExistException;
import com.ehr.upcsg.exceptions.UserRegisterException;
import com.ehr.upcsg.form.CreateUserForm;
import com.ehr.upcsg.model.User;

public interface UserService {

	List<User> list();
	List<User> listActive(User user);
	List<User> listInactive();
	
	User findUserByEmail(String email);
	User findUserByID(Long id);
	User findUserByUsername(String username);
	List<User> searchActive(String searchKey);
	List<User> searchDisabled(String searchKey);

	User createNewUser(CreateUserForm createUserForm) throws UserRegisterException;
	void registerUser(User user) throws DataAccessException, UserExistException, EmailDoesNotExistException, MailServiceException;
	void save(User user) throws DataAccessException, UserExistException;
	void update(User user) throws DataAccessException, UserExistException;
	
	String encodePassword(User editedUser) throws DataAccessException, UserExistException;
	void changePassword(User loggedInUser, String newPassword) throws DataAccessException, UserExistException;
	
	void disable(List<User> users, User user2) throws DataAccessException, UserExistException;
	void disable(User user, User user2) throws DataAccessException, UserExistException;

	void resetPassword(User user, User user2) throws DataAccessException, UserExistException;
	void resetPassword(List<User> users, User user2) throws DataAccessException, UserExistException;
	
	UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException;
	boolean authenticate(User user, String password);

}
