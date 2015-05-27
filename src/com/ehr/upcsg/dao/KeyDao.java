package com.ehr.upcsg.dao;

import com.ehr.upcsg.exceptions.CommandFailedException;

public interface KeyDao {
	
	public void fetchKeys(String username, String attrb) throws CommandFailedException;
	
	void decrypt(String username, String lambda, String filename)
			throws CommandFailedException;

	void encrypt(String filename, String policy) throws CommandFailedException;
}
