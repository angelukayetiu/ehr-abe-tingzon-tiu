package com.ehr.upcsg.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ehr.upcsg.exceptions.DataAccessException;
import com.ehr.upcsg.exceptions.EmailDoesNotExistException;
import com.ehr.upcsg.exceptions.MailServiceException;
import com.ehr.upcsg.exceptions.UserExistException;
import com.ehr.upcsg.model.User;
import com.ehr.upcsg.service.MailService;
import com.ehr.upcsg.service.UserService;


@Controller
public class LoginController {

	@Autowired
	private UserService userService;
	@Autowired	
	private MailService mailService;
	
	@RequestMapping("/login")
	public String login(Model model, HttpSession session){
		System.out.println("from login controller");
		
		model.addAttribute("username", session.getAttribute("username"));
		session.removeAttribute("username");
		
		String message = (String) session.getAttribute("loginMessage");
		session.removeAttribute("loginMessage");
		
		model.addAttribute("loginMessage", message);
		
		return "login/login";
	}
	
	@RequestMapping(value="/validateLogin", method=RequestMethod.POST)
	public String validateLogin(Model model,
			HttpServletRequest request,
			@RequestParam("username") String username,
			@RequestParam("password") String password){
		System.out.println("from validate login");
		
		HttpSession session = request.getSession();
		
		username = username.trim();
		password = password.trim();
		System.out.println("Username: "+username);
		System.out.println("Password: "+password);
		if (username.length()==0){
			session.setAttribute("loginMessage", "enter username");
		} else if (password.length()==0){
			session.setAttribute("loginMessage", "enter password");			
		} else {
			//check if user exists
			User user = userService.findUserByUsername(username);
			
			System.out.println(user);
			//authenticate
			if(user!=null && userService.authenticate(user, password)){
				session = request.getSession(true);
				session.setAttribute("login", username);
				return "redirect:home/"+username;					
			}
			session.setAttribute("loginMessage", "invalid username or password");				
		}
		session.setAttribute("username", username);
		return "redirect:login";
	}
	
	@RequestMapping("/logout")
	public String logout(Model model, 
			HttpServletRequest request){
		System.out.println("logout");
				
		HttpSession session = request.getSession();
		String message = (String) session.getAttribute("loginMessage");
		session.invalidate();
		session = request.getSession(true);
		if (message == null)
			message = "Successfully logout!";
		session.setAttribute("loginMessage", message);
		return "redirect:login";
	}
	
	
	@RequestMapping(value="/forgotPassword" , method=RequestMethod.POST)
	public String forgotPassword(@RequestParam("email") String email, Model model){ 
		String errorMessage = "";
		try {
			//generate password and update password of user
	        String password = RandomStringUtils.randomAlphabetic(10);
			User user = userService.findUserByEmail(email);
			userService.changePassword(user, password);
			
			//send mail
			mailService.sendPasswordRecovery(user, password);
			model.addAttribute("successForgotPasswordMessage", "Successfully sent your new password." );
			return "login/login";
		} catch (UserExistException e ) {
			errorMessage = e.getMessage();
		} catch (EmailDoesNotExistException e) {
			errorMessage = e.getMessage();
		} catch (MailServiceException e) {
			errorMessage = e.getMessage();
		} catch (DataAccessException e) {
			errorMessage = e.getMessage();
			System.err.println("DataAccessException boo");
		}
		
		model.addAttribute("errorForgotPasswordMessage", errorMessage);
		return "login/forgot-password";
	}
	
	@RequestMapping(value="/forgotPassword" , method=RequestMethod.GET)
	public String forgotPasswordPage(){
		return "login/forgot-password";
	}

}
