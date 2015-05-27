package com.ehr.upcsg.model;


public enum Role {
	RECORDS_OFFICER("Records Officer"),
	USER("User");	

	private String name;

	Role(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
