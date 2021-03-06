package com.ehr.upcsg.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ehr.upcsg.exceptions.DataAccessException;
import com.ehr.upcsg.exceptions.UserExistException;
import com.ehr.upcsg.model.User;
import com.ehr.upcsg.service.UserService;

@Controller
@RequestMapping("user")
public class ResetPasswordController {
	
	@Autowired
	private UserService userService; 
	
	@RequestMapping(value = "/reset-password", params = "userId")
	public String disableUser(@RequestParam(value = "userId") Long userId, Model model) throws DataAccessException, UserExistException{
		User user = userService.findUserByID(userId);
		userService.resetPassword(user, (User)model.asMap().get("loggedInUser"));
		return "redirect:/user/create";
	}
	
	@RequestMapping(value = "/reset-password", params = "users")
	public String resetPasswords(@RequestParam(value = "users") String userId, Model model) throws DataAccessException, UserExistException{
		String userIds[] = userId.split(",");
		List<User> users = new ArrayList<User>();
		for(String i : userIds){
			User user = userService.findUserByID(Long.parseLong(i));
			users.add(user);
		}
		userService.resetPassword(users, (User)model.asMap().get("loggedInUser"));
		return "redirect:/user/create";
	}
	
	
	
}
