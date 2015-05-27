package com.ehr.upcsg.controller;


import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.amazonaws.services.s3.model.S3Object;
import com.ehr.upcsg.exceptions.DataAccessException;
import com.ehr.upcsg.exceptions.EmailDoesNotExistException;
import com.ehr.upcsg.exceptions.MailServiceException;
import com.ehr.upcsg.exceptions.UserExistException;
import com.ehr.upcsg.exceptions.UserRegisterException;
import com.ehr.upcsg.form.CreateUserForm;
import com.ehr.upcsg.model.Role;
import com.ehr.upcsg.model.User;
import com.ehr.upcsg.service.UserService;

@Controller
public class RegistrationController {

	@Autowired
	private UserService userService;	
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerUser(Model model, HttpSession session){
		String message = (String)session.getAttribute("registerMessage");
		model.addAttribute("registerMessage", message);
		session.removeAttribute("registerMessage");
		
		setUpRegisterUserPage(model);
		return "login/register";	
	}
		
	private void setUpRegisterUserPage(Model model) {

		model.addAttribute("roles", EnumSet.allOf(Role.class));
		CreateUserForm createUserForm = new CreateUserForm();
		model.addAttribute("registerUser", createUserForm);		
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String processRegisterUser(@Valid @ModelAttribute("registerUser") CreateUserForm createUserForm,
			BindingResult result, Model model,
			HttpServletResponse response, HttpServletRequest request){
		System.out.println("in register user post");
		User user = null;
		String message = null;
		try{
			user = userService.createNewUser(createUserForm);
		} catch(UserRegisterException e){ 
			result.rejectValue("email", "Existing");
			System.out.println("rejected email");
			setUpRegisterUserPage(model);
			model.addAttribute("registerUser", createUserForm);
			model.addAttribute("registerMessage", "A user with the email already exists");
			return "login/register";			
		} 
/*		System.out.println("error"+result.getFieldErrorCount());
		if(result.hasErrors()){
			setUpRegisterUserPage(model);
    			return "login/register";
		}
		System.out.println("try to save");
*/		try {
			userService.registerUser(user);
			
			downloadKeys(user.getUsername(),request, response);
			
			message = "Successfully registered. Please check your email for confirmation.";
			return "redirect:login";		
		} catch (DataAccessException e) {
			message = "Can not system right now. Please wait until system is up and running. ";
		} catch (UserExistException e) {
			message = "A user with the same email is already used.";
		} catch (EmailDoesNotExistException e) {
			message = "A user with the same email is already used.";
		} catch (MailServiceException e) {
			message = "Can not access mail right now. Check you internet connection.";
		}
		setUpRegisterUserPage(model);
		model.addAttribute("registerMessage", message);
		return "login/register";
	}

	private void downloadKeys(
			String username,
			HttpServletRequest request,
			HttpServletResponse response) {
		String mimetype = request.getSession().getServletContext().getMimeType("pubkey");
	//	S3Object obj= .download("test-bucket-download", filename);
	//	response.setContentType(mimetype);
	//	response.setContentLength((int) obj.getObjectMetadata().getContentLength());
	//	response.setHeader("Content-Disposition","attachment; filename=\"" + filename +"\"");
	//	FileCopyUtils.copy(obj.getObjectContent() , response.getOutputStream());
	}
	
	
	
}
