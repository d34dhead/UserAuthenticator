package com.domain.userAuthenticator;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.domain.userAuthenticator.UserRepository;
/*
 * Author: Lubomir Nepil
 * */
@SpringBootApplication
public class UserAuthApp {
	
			
	public static void main(String[] args) {
		SpringApplication.run(UserAuthApp.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(UserRepository repository) {
		return (args) -> {
			new UI(repository).launch();
		};
	}
	
}
