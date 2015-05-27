package com.ehr.upcsg.user;

import java.util.EnumSet;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ehr.upcsg.exceptions.DataAccessException;
import com.ehr.upcsg.exceptions.UserExistException;
import com.ehr.upcsg.exceptions.UserRegisterException;
import com.ehr.upcsg.form.CreateUserForm;
import com.ehr.upcsg.model.Role;
import com.ehr.upcsg.model.User;
import com.ehr.upcsg.service.UserService;

@Controller
@RequestMapping("user")
public class CreateUserController {
	
	@Autowired
	private UserService userService; 

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String setUpCreateUser(Model model){
		setUpUserPage(model);
		CreateUserForm createUserForm = new CreateUserForm();
		model.addAttribute("createUser", createUserForm);
		return "user/settings-view-users";	
	}
		
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String processCreateUser(@Valid @ModelAttribute("createUser") CreateUserForm createUserForm,
			BindingResult result, Model model) throws DataAccessException, UserExistException{
		User user = null;

		try{
			user = userService.createNewUser(createUserForm);
		} catch(UserRegisterException e){ 
			result.rejectValue("email", "Existing");
		}
		
		if(result.hasErrors()){
			setUpUserPage(model);
    		return "user/settings-view-users";
		}
		userService.save(user);
		return "redirect:/user/create";		
	}
	
	@RequestMapping(value = "/view-inactive")
	public String displayInactiveUsers(Model model){		
		setUpUserPage(model);
		CreateUserForm createUserForm = new CreateUserForm();
		model.addAttribute("createUser", createUserForm);
		model.addAttribute("action", "displayInactiveUsers");
		return "user/settings-view-users";	
	}
	
	private void setUpUserPage(Model model){
		List<User> activeUsers = userService.listActive((User)model.asMap().get("loggedInUser"));
		model.addAttribute("activeUsers", activeUsers);
		List<User> inactiveUsers = userService.listInactive();
		model.addAttribute("inactiveUsers", inactiveUsers);
		model.addAttribute("action", "displayActiveUsers");
		model.addAttribute("roles", EnumSet.allOf(Role.class));
	}
	
}
