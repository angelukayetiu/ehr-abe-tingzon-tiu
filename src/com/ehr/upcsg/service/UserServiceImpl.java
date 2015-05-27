package com.ehr.upcsg.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.ehr.upcsg.dao.KeyCommandDaoImpl;
import com.ehr.upcsg.dao.KeyDao;
import com.ehr.upcsg.dao.UserDao;
import com.ehr.upcsg.exceptions.CommandFailedException;
import com.ehr.upcsg.exceptions.DataAccessException;
import com.ehr.upcsg.exceptions.EmailDoesNotExistException;
import com.ehr.upcsg.exceptions.KeyServiceException;
import com.ehr.upcsg.exceptions.MailServiceException;
import com.ehr.upcsg.exceptions.SSLClientErrorException;
import com.ehr.upcsg.exceptions.UserExistException;
import com.ehr.upcsg.exceptions.UserRegisterException;
import com.ehr.upcsg.form.CreateUserForm;
import com.ehr.upcsg.model.RoleDTO;
import com.ehr.upcsg.model.User;
import com.ehr.upcsg.model.Role;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
	
	private static final String[] SEARCHED_USER_FIELDS = {"firstName", 
			"lastName" , "email", "hospital", "occupation", "email", "role"};

	
	@Autowired
	private UserDao userDao;
	
	private KeyDao keyDao;
	
//	@Autowired
//	private KeyService keyService;
	
	@Autowired
	private MailService mailService;
	
	private PasswordEncoder passwordEncoder;
	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findUserByEmail(email);
        if (user == null)
        		throw new UsernameNotFoundException("Invalid email or password");
        
        boolean accountNonExpired = true;  
        boolean credentialsNonExpired = true;  
        boolean accountNonLocked = true; 
        return new org.springframework.security.core.userdetails.User(
	    		user.getEmail(), 
	    		user.getPassword(), 
	    		user.isEnabled(), 
	    		accountNonExpired, 
	    		credentialsNonExpired, 
	    		accountNonLocked,
	    		getAuthorities(user.getRoleDTO())
		);
    }

	private Collection<? extends GrantedAuthority> getAuthorities(RoleDTO role) { 
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(role.getName()));
		return authorities;
	}

	@Override
	public User findUserByUsername(String username){
		return userDao.findUserByUsername(username);
	}

	@Override
	public User findUserByEmail(String email){
		return userDao.findUserByEmail(email);
	}

	@Override
	public void registerUser(User user) throws 
		DataAccessException, UserExistException, 
		EmailDoesNotExistException, MailServiceException {
		
		userDao.createUser(user);
		
		//generate new password and set new password
        String newPassword = RandomStringUtils.randomAlphabetic(10);
        changePassword(user, newPassword);

        System.out.println("created user");
        System.out.println("generate keys"); 
        
        try {
        		keyDao = new KeyCommandDaoImpl(1);
			keyDao.fetchKeys(user.getUsername(), user.attributesToString());
		} catch (CommandFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("generation error");
			userDao.delete(user);			
		}
        mailService.sendConfirmationLink(user.getEmail(), newPassword);
        
/*        SSLKGCClient client;
		try {
			client = new SSLKGCClient();
			System.out.println("new kgc");
	        client.fetchKeys(user.getUsername(), user.attributesToString());
			System.out.println("fetched");
		} catch (SSLClientErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("generation error");
			userDao.delete(user);
		}*/
        
        
		System.out.println("Generate done");
		
        
	}
	
	
	private String generateUsername(User user) {
		String username = user.getFirstName().charAt(0) + user.getLastName();
		username = username.replaceAll("\\s", "");
		username = username.toLowerCase();
		//check for duplicates
		int numberOfDuplicates = userDao.countUserLikeUsername(username);
		if (numberOfDuplicates > 0)
			username = username+numberOfDuplicates;
		System.out.println("generated username"+username);
		return username;
	}

	@Override
	public User findUserByID(Long id) {
		return userDao.findUserById(id);
	}

	@Override
	public String encodePassword(User user) {
		String hashedPassword = passwordEncoder.encodePassword(user.getPassword(), user.getEmail());
		user.setPassword(hashedPassword);
		return hashedPassword;
	}

	@Override
	public void changePassword(User user, String password) throws DataAccessException, UserExistException {
		user.setPassword(password);
		user.setPassword(encodePassword(user));
		System.out.println("update password to"+password+user.getEmail());
		userDao.update(user);
		
	}

	@Override
	public void update(User user) throws DataAccessException, UserExistException {
		userDao.update(user);
	}

	@Override
	public User createNewUser(CreateUserForm createUserForm)
			throws UserRegisterException {
		User user = new User();
		user.setFirstName(createUserForm.getFirstName());
		user.setLastName(createUserForm.getLastName());
		user.setEmail(createUserForm.getEmail());
		user.setHospital(createUserForm.getHospital());
		user.setOccupation(createUserForm.getOccupation());

		//generate password
        String password = RandomStringUtils.randomAlphabetic(10);
		user.setPassword(password);
		encodePassword(user);
	
		user.setEnabled(true);
		if (userDao.findUserByEmail(user.getEmail())!=null)
			throw new UserRegisterException("Email already exists!");
		user.setUsername(generateUsername(user));
		return user;
	}

	@Override
	public void save(User user) throws DataAccessException, UserExistException {
		userDao.save(user);
	}

	@Override
	public List<User> listActive(User user) {
		if(user.isRecordsOfficer()){
			if(userDao.findAllActive() == null ){
				return new ArrayList<User>();
			}
			return userDao.findAllActive(); 
		}else{
			List<User> users = new ArrayList<User>();
			users.add(user);
			return users;
		}
	}

	@Override
	public List<User> listInactive() {
		if(userDao.findAllInactive() == null ){
			return new ArrayList<User>();
		}
		return userDao.findAllInactive(); 
	}

	@Override
	public void disable(List<User> users, User loggedInUser) throws DataAccessException, UserExistException {
		for(User user: users){
			disable(user, loggedInUser);
		}
	}

	@Override
	public void disable(User user, User loggedInUser) throws DataAccessException, UserExistException {
		if(!user.equals(loggedInUser)) {
			user.setEnabled(false);
			userDao.update(user);
		}
	}

	@Override
	public void resetPassword(User user, User loggedInUser) throws DataAccessException, UserExistException {
		if(!user.equals(loggedInUser)) {
			user.setPassword("UPl1br@ry");
			encodePassword(user);
			userDao.update(user);
		}
	}

	@Override
	public void resetPassword(List<User> users, User loggedInUser) throws DataAccessException, UserExistException {
		for(User user: users){
			resetPassword(user, loggedInUser);
		}
	}

	@Override
	public List<User> searchActive(String searchKey) {
		List<User> users = userDao.findActive(searchKey, SEARCHED_USER_FIELDS);
		return users;
	}

	@Override
	public List<User> searchDisabled(String searchKey) {
		List<User> users = userDao.findInactive(searchKey, SEARCHED_USER_FIELDS);
		return users;
	}
	@Override
	public List<User> list() {
		return userDao.list();
	}

	@Override
	public boolean authenticate(User user, String password) {
		return passwordEncoder.isPasswordValid(user.getPassword(), password, user.getEmail());
	}

}
