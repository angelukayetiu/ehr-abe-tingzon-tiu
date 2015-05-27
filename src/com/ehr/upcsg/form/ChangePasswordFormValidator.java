package com.ehr.upcsg.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;

import com.ehr.upcsg.exceptions.DataAccessException;
import com.ehr.upcsg.exceptions.UserExistException;
import com.ehr.upcsg.model.User;
import com.ehr.upcsg.service.UserService;

public class ChangePasswordFormValidator {
	
	@Autowired
	private static UserService userService;

	public static void validateForm(ChangePasswordForm changePasswordForm, BindingResult errors) throws DataAccessException, UserExistException {
		Long selectedUserId = changePasswordForm.getId();
		User editedUser = userService.findUserByID(selectedUserId);
		validateCurrentPassword(changePasswordForm, errors, editedUser);
		validateNewPassword(changePasswordForm, errors, editedUser);
		
	}
	
	private static void validateCurrentPassword(ChangePasswordForm changePasswordForm, BindingResult errors, User editedUser) throws DataAccessException, UserExistException {
		String hashedOldPassword = editedUser.getPassword();
		editedUser.setPassword(changePasswordForm.getCurrentPassword());
		String hashedInputedPassword = userService.encodePassword(editedUser);
		if( !hashedOldPassword.equals(hashedInputedPassword) ) {
			errors.rejectValue("currentPassword", "Password.editPassword.invalidPassword");
		}
	}
	
	private static void validateNewPassword(ChangePasswordForm changePasswordForm, BindingResult errors, User editedUser) throws DataAccessException, UserExistException {
		validateNewPasswordDifferent(changePasswordForm, errors, editedUser);
		validatePasswordConfirmMatch(changePasswordForm, errors);
	}
	
	private static void validateNewPasswordDifferent(ChangePasswordForm changePasswordForm, BindingResult errors, User editedUser) throws DataAccessException, UserExistException {
		String hashedOldPassword = editedUser.getPassword();
		editedUser.setPassword(changePasswordForm.getNewPassword());
		String hashedNewPassword = userService.encodePassword(editedUser);
		if (hashedOldPassword.equals(hashedNewPassword)) {
			errors.rejectValue("newPassword", "Password.editPassword.passwordNotChanged");
		}
	}
	
	private static void validatePasswordConfirmMatch(ChangePasswordForm changePasswordForm, BindingResult errors) {
		String newPassword = changePasswordForm.getNewPassword();
		String passwordConfirmation = changePasswordForm.getPasswordConfirmation();
		if( !newPassword.equals(passwordConfirmation) ) {
			errors.rejectValue("passwordConfirmation", "Password.editPassword.passwordConfirmationMatch");
		}
	}
}

//code copied from onbcrm