package com.revature.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.revature.models.User;
import com.revature.repositories.UserDAO;

@Service
public class UserService {
	
	private UserDAO userDAO;
	private BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
	
	@Autowired
	public UserService(UserDAO userDAO) {
		super();
		this.userDAO = userDAO;
	}
	
	public User register(User user) {
		Optional<User> dbUser = userDAO.getUserByUsername(user.getUsername());
		if(!dbUser.isPresent()) {
			String password = pwEncoder.encode(user.getPassword());
			user.setPassword(password);
			user.setId(0);
			return userDAO.save(user);
		}
		return null;
	}
	
	public User login(User user) {
		Optional<User> dbUser = userDAO.getUserByUsername(user.getUsername());
		if(dbUser.isPresent()) {
			if(pwEncoder.matches(user.getPassword(), dbUser.get().getPassword())) {
				return dbUser.get();
			}
		}
		return null;
	}
	
	public User update(User user) {
		Optional<User> dbUser = userDAO.getUserByUsername(user.getUsername());
		if(dbUser.isPresent()) {
			User updatedUser = dbUser.get();
			String password = pwEncoder.encode(user.getPassword());
			updatedUser.setPassword(password);
			updatedUser.setReimbursements(user.getReimbursements());
			userDAO.save(updatedUser);
			return updatedUser;
		}
		return null;
	}
	
	public User getUserByUsername(String username) {
		return userDAO.getUserByUsername(username).get();
	}
	
	
}
