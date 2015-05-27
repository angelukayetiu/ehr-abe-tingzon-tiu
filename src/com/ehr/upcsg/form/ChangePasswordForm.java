package com.ehr.upcsg.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.ehr.upcsg.form.constraint.AttributeEqual;
import com.ehr.upcsg.form.constraint.AttributeNotEqual;

@AttributeEqual(first = "newPassword", second = "passwordConfirmation", message="password confirmation not matched")
@AttributeNotEqual(first="currentPassword", second="newPassword", message="new password must be different")

public class ChangePasswordForm {
	private Long id;
	private String currentPassword;
	private String newPassword;
	private String passwordConfirmation;

	@NotNull
	public Long getId() {
		return id;
	}

	@Size(min = 6, max = 50)
	public String getCurrentPassword() {
		return currentPassword;
	}

	@Size(min = 6, max = 50)
	public String getNewPassword() {
		return newPassword;
	}

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public void clearFields() {
		this.currentPassword = "";
		this.newPassword = "";
		this.passwordConfirmation = "";
	}
}

//Code copied from onbcrm