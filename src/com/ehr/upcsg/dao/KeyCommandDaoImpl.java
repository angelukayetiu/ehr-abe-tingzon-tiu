package com.ehr.upcsg.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import com.ehr.upcsg.exceptions.CommandFailedException;

public class KeyCommandDaoImpl implements KeyDao {

	final String SETUP="kgcclient.SSLKGCClient";
	final String KEYMANAGER="keymanager.ABEImpl";
	final int MODE;

	public KeyCommandDaoImpl(int mode){
		this.MODE = mode;
	}
	
	@Override
	public void fetchKeys(String username, String attrb) throws CommandFailedException {
		String[] attribute={"java",SETUP, username, "\""+attrb+"\""};
		execute(attribute);
	}
	@Override
	public void encrypt(String filename,
			String policy) throws CommandFailedException {
		String[] attribute={"java", KEYMANAGER, MODE+"", "encrypt",filename, "\""+policy+"\""};
		execute(attribute);
	}

	@Override
	public void decrypt(String username, String lambda, String filename) 
			throws CommandFailedException {
		String[] attribute={"java",KEYMANAGER, MODE+"", "decrypt",
				username, lambda, filename};
		execute(attribute);
	}

    private void execute(String[] command) throws CommandFailedException{
        StringBuilder output = new StringBuilder();
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.directory(new File(System.getProperty("user.home")));
        try {
            Process p = pb.start();
            BufferedReader reader = 
                        new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader error = 
                            new BufferedReader(new InputStreamReader(p.getErrorStream()));

            //Read output of commands (if status is not 0)
            String line;           
            while ((line = error.readLine())!= null) {
                output.append(line).append("\n");
            }
            while ((line = reader.readLine())!= null ) {
                output.append(line).append("\n");
            }

            //returns status code of command; 0 if successful
            int status_code = p.waitFor();
            System.out.println(command + " exited with status code " + status_code + "." + "\n " + output);

            if (status_code!=0) throw new CommandFailedException();
        } catch (IOException e){throw new CommandFailedException();}
        		catch(InterruptedException e) {throw new CommandFailedException();}   
    }

}
