package com.revature.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.revature.models.Role;
import com.revature.models.User;
import com.revature.repositories.UserDAO;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	@InjectMocks
	private UserService userService;
	
	@Mock
	private UserDAO userDAO;
	
	private BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
	
	private User testUser;
	
	@BeforeEach
	public void setup() {
		userDAO = Mockito.mock(UserDAO.class);
        userService = new UserService(userDAO);
		testUser = User.builder()
				.id(1)
				.username("Paul")
				.password("password")
				.role(Role.EMPLOYEE)
				.build();
	}
	
	@Test
	public void registerTest() {
		
		BDDMockito.given(userDAO.getUserByUsername(testUser.getUsername()))
			.willReturn(Optional.of(testUser));
		
		User savedUser = userService.register(testUser);
				
		assertThat(userService.register(testUser)).isEqualTo(savedUser);
	}
	
	@Test
	public void LoginTest() {
		
		BDDMockito.given(userDAO.getUserByUsername(testUser.getUsername()))
			.willReturn(Optional.of(testUser));
		
		User savedUser = userService.login(testUser);
				
		assertThat(userService.login(testUser)).isEqualTo(savedUser);
	}
	
	@Test
	public void getUserByUsernameTest() {
		
		BDDMockito.given(userDAO.getUserByUsername("Paul"))
			.willReturn(Optional.of(testUser));
		
		User savedUser = userService.getUserByUsername(testUser.getUsername());
				
		assertThat(savedUser).isEqualTo(testUser);
	}
	
	@Test
	public void updateUserTest() {
		
		BDDMockito.given(userDAO.save(testUser))
			.willReturn(testUser);
		
		BDDMockito.given(userDAO.getUserByUsername(testUser.getUsername()))
		.willReturn(Optional.of(testUser));
			
		testUser.setPassword("password2");
		
		User updatedUser = userService.update(testUser);
		
		boolean pw = pwEncoder.matches("password2", updatedUser.getPassword());
		
		assertThat(pw).isTrue();
	}
	
	
	
	
	
	
	
	
	
}
