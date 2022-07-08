package com.revature.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.User;
import com.revature.services.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UserController {
	
	private UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody User user) {
		if(userService.register(user)!=null) {
			return ResponseEntity.status(HttpStatus.CREATED).build();
		}
		return ResponseEntity.status(400).build();
	}
	
	@PostMapping
	public ResponseEntity<User> loginAttempt(@RequestBody User user, HttpSession session){
		user = userService.login(user);
		if(user != null) {
			session.setAttribute("logged in", true);
			session.setAttribute("user", user);
			return ResponseEntity.status(200).body(user);
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}	
	
	@PutMapping
	public ResponseEntity<User> updateUser(@RequestBody User user, HttpSession session){
		if((boolean)session.getAttribute("logged in")==true) {
			user = userService.update(user);
			return ResponseEntity.status(200).body(user);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

}
