package com.domain.userAuthenticator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Entity
public class User {
		@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    Long id;
	    String username;
	    String password;

	    public User(){}

	    public User(String userName, String password) {
	        this.username = userName;
	        this.password = password;
	    }

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getUserName() {
	        return username;
	    }

	    public void setUserName(String userName) {
	        this.username = userName;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }
	    
	    public String toString() {
	    	return "user ID: " + this.id + " username: " + username;
	    }
}
