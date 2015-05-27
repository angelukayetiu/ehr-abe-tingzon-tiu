package com.ehr.upcsg.controller;

import java.util.EnumSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ehr.upcsg.model.Role;
import com.ehr.upcsg.model.User;
import com.ehr.upcsg.service.UserService;

@Controller
@RequestMapping("user")
public class UpdateUserController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/update-display")
	public String displayUserProfile(@RequestParam(value = "userId") Long userId, Model model) {
		User selectedUser = userService.findUserByID(userId);
		model.addAttribute("selectedUser", selectedUser);
		
		return "user/settings-update-full";
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String setUpUpdateUser(@RequestParam(value = "userId") Long userId, Model model) {
		
		User selectedUser = userService.findUserByID(userId);
		model.addAttribute("selectedUser", selectedUser);
		model.addAttribute("roles", EnumSet.allOf(Role.class));
		return "user/settings-update";
	}

	
	
}