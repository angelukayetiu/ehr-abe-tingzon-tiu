package com.ehr.upcsg.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ehr.upcsg.form.CreateUserForm;
import com.ehr.upcsg.model.User;
import com.ehr.upcsg.service.UserService;

@Controller
@RequestMapping("user/search")
public class SearchUserController{

	@Autowired
	private UserService userService;
	
	@RequestMapping("/active")
	public String searchActiveUser(@RequestParam("searchKey") String searchKey, Model model) {
		if(searchKey.isEmpty()) {
			return "redirect:/user/create";
		}
		setUpSearchPage(model, searchKey);
		model.addAttribute("action", "displayActiveUsers");
		return "/user/settings-view-users";
	}
	
	@RequestMapping("/inactive")
	public String searchDisabledUser(@RequestParam("searchKey") String searchKey, Model model) {
		if(searchKey.isEmpty()) {
			return "redirect:/user/view-inactive";
		}
		setUpSearchPage(model, searchKey);
		model.addAttribute("action", "displayInactiveUsers");
		return "/user/settings-view-users";
	}
	
	private void setUpSearchPage(Model model, String searchKey) {
		List<User> activeUsers = userService.searchActive(searchKey);
		List <User> inactiveUsers = userService.searchDisabled(searchKey);
		model.addAttribute("activeUsers", activeUsers);
		model.addAttribute("inactiveUsers", inactiveUsers);
		model.addAttribute("createUser", new CreateUserForm());
		model.addAttribute("searchKey", searchKey);
	}
}
