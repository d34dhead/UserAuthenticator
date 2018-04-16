package com.domain.userAuthenticator;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UI {
	
	private UserRepository repo;
	private  MessageDigest md;
	private List<Option> options;
	private Scanner scanner = new Scanner(System.in);
	
	public UI(UserRepository repo) {
		this.repo = repo;
	};
    
    public void launch() {
    	createOptions();
        String input;
        
        while(true){
        	displayCommands();
            try{
                input = scanner.nextLine();
                handleCommand(input);
            }catch(Exception exc){
                exc.printStackTrace();
            }
        }
    }

	private void handleCommand(String input) {
        switch (input){
            case "exit": System.exit(0);
            case "help": displayCommands(); break;
            case "list": printUsers(); break;
            case "add": {
            	String username = requestUsername(false);
            	String password = requestPassword();
            	repo.save(new User(username, password)); 
            	System.out.println("User " + username + " was registered.");
            	break;}
            case "delete":{ 
            	String username = requestUsername(true);
            	List<User> users = repo.findByUsername(username);
            	repo.deleteAll(users);
            	System.out.println("User " + username + " was deleted.");
            	break;}
            case "login": {
            	String username = requestUsername(true);   	
            	validateUser(username);
            } 
        }
    }
	
	

	private void validateUser(String username) {
		String userPwd = requestPassword();
		
		User user = repo.findByUsername(username).get(0);	
		String actualPwd = user.getPassword();
		
		if(!userPwd.equals(actualPwd)) {
			System.out.println("Wrong password!");
			validateUser(username);
		}
		System.out.println("Logged in user " + username + " successfully!");
	}

	private String hashPassword(String rawPassword) {
		String hashedPassword = null;
		
		 try {
        	md = MessageDigest.getInstance("MD5");
        	byte[] bytes = md.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
        	StringBuilder sb = new StringBuilder();
        	
            for(int i=0; i< bytes.length ;i++){
               sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            
            hashedPassword = sb.toString();
        	
        } catch(NoSuchAlgorithmException exc) {
        	exc.printStackTrace();
        }
		
		return hashedPassword;
	}
	
    private String requestUsername(boolean supposedToExist) {
		String username;

		System.out.println("Enter username: ");
		username = scanner.nextLine();
		
		if(repo.existsByUsername(username)) {
			if(!supposedToExist) {
				System.out.println("User " + username + " already exists!");
				requestUsername(supposedToExist);	
			}
		}else {
			if(supposedToExist) {
				System.out.println("User " + username + " does not exist!");
				requestUsername(supposedToExist);	
			}
		}
		
		return username;
	}

	private String requestPassword() {
		String password;

		System.out.println("Enter password: ");
		password = scanner.nextLine();
		if(password.length() < 6) {
			System.out.println("Minimum password length is 6 characters!");
			requestPassword();
		}
		String hashedPassword = hashPassword(password);
		
		return hashedPassword;
	}

	private void printUsers() {
    	Iterable<User> users = repo.findAll();
    	System.out.println("Users in database:");
    	for(User user : users) {
    		System.out.println(user.toString());
    	}
    }

    private void createOptions() {
        options = new ArrayList<Option>();

        options.add(new Option("add", "add a new user"));
        options.add(new Option("delete", "delete a user"));
        options.add(new Option("list", "list all users"));
        options.add(new Option("login", "login a user"));
        options.add(new Option("exit", "terminate the program"));
        options.add(new Option("help", "display available commands"));
    }

    private void displayCommands(){
    	System.out.println();
        System.out.println("Enter one of the following commands:");
        for(Option o : options){
            System.out.println(String.format("%1$" + 15 + "s", o.getName()) + "     " + o.getDescription());
        }
    }
}
