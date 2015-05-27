package com.ehr.upcsg.controller;

import javax.servlet.http.HttpSession;
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
import com.ehr.upcsg.form.ChangePasswordForm;
import com.ehr.upcsg.model.User;
import com.ehr.upcsg.service.UserService;

@Controller
@RequestMapping("user")
public class ChangePasswordController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/change-pass", method = RequestMethod.GET)
	public String displayChangePasswordPage(Model model, HttpSession session) {
		model.addAttribute("editPassword", new ChangePasswordForm());
		model.addAttribute("loggedInUser", userService.findUserByUsername((String)session.getAttribute("login")));
		return "/user/settings-change-password";
	}

	@RequestMapping(value = "/change-pass", method = RequestMethod.POST)
	public String processChangePassword(
			@Valid @ModelAttribute("editPassword") ChangePasswordForm changePasswordForm,			
			BindingResult errors, Model model){
		
		User loggedInUser = userService.findUserByID(changePasswordForm.getId());
		try {
			validatePassword(changePasswordForm, loggedInUser, errors);
			if (errors.hasErrors()) {
				return "/user/settings-change-password";
			}
			userService.changePassword(loggedInUser,
					changePasswordForm.getNewPassword());
			return "redirect:/user/change-pass-success";
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/user/settings-change-password";
	}
	
	@RequestMapping(value = "/change-pass-success", method = RequestMethod.GET)
	public String displayChangePasswordPageOnSuccess(Model model) {
		model.addAttribute("editPassword", new ChangePasswordForm());
		model.addAttribute("success", "Password successfully changed!");
		return "/user/settings-change-password";
	}

	private void validatePassword(ChangePasswordForm changePasswordForm,
			final User loggedInUser, BindingResult errors) throws DataAccessException, UserExistException {
		User userCopy = new User(loggedInUser);
		String hashedCurrentPassword = loggedInUser.getPassword();
		userCopy.setPassword(changePasswordForm.getCurrentPassword());
		String hashedInputtedPassword = userService.encodePassword(userCopy);
		if (!hashedCurrentPassword.equals(hashedInputtedPassword)) {
			errors.rejectValue("currentPassword",
					"Password.editPassword.currentPassword");
		}
	}
}
