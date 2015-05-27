package com.ehr.upcsg.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;


public class CreateUserForm {
	private String firstName;
	private String lastName;
	private String email;
	private String occupation;
	private String hospital;

	@NotEmpty
	@Size(max = 50)
	public String getFirstName() {
		return firstName;
	}

	@NotEmpty
	@Size(max = 50)
	public String getLastName() {
		return lastName;
	}

	@NotEmpty
	@Size(max = 50)
	@Pattern(regexp = "[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})")
	public String getEmail() {
		return email;
	}

	@NotEmpty
	@Size(max = 50)
	public String getOccupation() {
		return occupation;
	}

	@NotEmpty
	@Size(max = 50)
	public String getHospital() {
		return hospital;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}
}
