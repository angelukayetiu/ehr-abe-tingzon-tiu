package com.ehr.upcsg.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
public class User {
	
	private Long id;
	private RoleDTO roleDTO;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String occupation;
	private String email;
	private String hospital;
	
	private boolean enabled;
	
	public User(){
		super();
		this.enabled=false;
		this.roleDTO = new RoleDTO(Role.USER);
	}
	
	public User(User loggedInUser) {
		super();
		this.id = loggedInUser.getId();
		this.roleDTO = loggedInUser.getRoleDTO();
		this.username = loggedInUser.getUsername();
		this.firstName = loggedInUser.getFirstName();
		this.lastName = loggedInUser.getLastName();
		this.occupation = loggedInUser.getOccupation();
		this.email = loggedInUser.getEmail();
		this.hospital = loggedInUser.getHospital();
	}

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn()
	public RoleDTO getRoleDTO() {
		return roleDTO;
	}
	public void setRoleDTO(RoleDTO roleDTO) {
		this.roleDTO = roleDTO;
	}
	
	@Column(unique=true, nullable=false)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column(nullable=false)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Column(nullable=false)
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(nullable=false)
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
		
	@Column(nullable=false)
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}	

	@Column(unique=true, nullable=false)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Column(nullable=false)
	public String getHospital() {
		return hospital;
	}
	public void setHospital(String hospital) {
		this.hospital = hospital;
	}	
	
	
	@Transient
	public boolean isRecordsOfficer () {
		return roleDTO.getName().equals("Records Officer");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Transient
	public String[] attributesToStringArray() {
		String[] attributes = {roleDTO.getName(), username,
				firstName, lastName, occupation,
				email, hospital};
		return attributes;
	}

	public String attributesToString() {
		return roleDTO.getName()+" "+ username+" "+
				firstName+" "+ lastName+" "+ occupation+" "+
				email+" "+hospital;
	}
	
}
