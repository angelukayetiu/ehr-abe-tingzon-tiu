package com.ehr.upcsg.dao;

import java.util.List;

import com.ehr.upcsg.exceptions.DataAccessException;
import com.ehr.upcsg.exceptions.UserExistException;
import com.ehr.upcsg.model.User;


public interface UserDao {
	
	User findUserById(Long id);

	User findUserByUsername(String username);

	User findUserByEmail(String email);

	void createUser(User user) throws DataAccessException, UserExistException;

	void save(User user) throws DataAccessException, UserExistException;

	void update(User user) throws DataAccessException, UserExistException;
	
	void delete(User user) throws DataAccessException, UserExistException;
	
	int countUserLikeUsername(String username);

	List<User> list();

	List<User> findAllActive();

	List<User> findAllInactive();

	List<User> findInactive(String searchKey, String[] searchedUserFields);

	List<User> findActive(String searchKey, String[] searchedUserFields);


}
