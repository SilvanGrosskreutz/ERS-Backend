package com.revature.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.models.Reimbursement;
import com.revature.models.Role;
import com.revature.models.Status;
import com.revature.models.User;
import com.revature.repositories.ReimbursementDAO;
import com.revature.repositories.UserDAO;

@ExtendWith(MockitoExtension.class)
public class ReimbursementServiceTest {

	@InjectMocks
	private ReimbursementService reimService;

	@Mock
	private ReimbursementDAO reimDAO;
	@Mock
	private UserDAO userDAO;

	private Reimbursement reim;
	private User user;

	@BeforeEach
	public void setup() {
		reimDAO = Mockito.mock(ReimbursementDAO.class);
		reimService = new ReimbursementService(reimDAO, userDAO);

		user = User.builder()
				.id(1)
				.username("Paul")
				.password("password")
				.role(Role.EMPLOYEE)
				.build();

		reim = Reimbursement.builder()
				.id(1)
				.status(Status.PENDING)
				.author(user)
				.amount(69.00)
				.build();
	}

	@Test
	public void createReimTest() {

		BDDMockito.given(reimDAO.save(reim)).willReturn(reim);

		Reimbursement testReim = reimService.createReimbursement(reim);

		assertThat(reim).isEqualTo(testReim);
	}

	@Test
	public void getAllReimsTest() {

		Reimbursement reim2 = Reimbursement.builder()
				.id(0)
				.status(Status.PENDING)
				.author(user)
				.amount(72.00)
				.build();

		List<Reimbursement> reims = new ArrayList<Reimbursement>();

		reims.add(reim);
		reims.add(reim2);

		BDDMockito.given(reimDAO.findAll()).willReturn(reims);

		List<Reimbursement> reimList = reimService.getAllReimbursements();

		assertThat(reims).isEqualTo(reimList);
	}

	@Test
	public void getReimByIdTest() {

		BDDMockito.given(reimDAO.findById(1)).willReturn(Optional.of(reim));

		Reimbursement reim2 = reimService.findById(reim.getId());

		assertThat(reim2).isEqualTo(reim);
	}

	@Test
	public void findByAuthorTest() {

		Reimbursement reim2 = Reimbursement.builder()
				.id(2)
				.status(Status.PENDING)
				.author(user)
				.amount(72.00)
				.build();

		List<Reimbursement> reims = new ArrayList<Reimbursement>();

		reims.add(reim);
		reims.add(reim2);
		
		BDDMockito.given(userDAO.getUserByUsername(user.getUsername()))
			.willReturn(Optional.of(user));
		
		BDDMockito.given(reimDAO.getReimbursementByAuthor(user)).willReturn(reims);
		
		List<Reimbursement> reimList = reimService.findByAuthor(user.getUsername());

		assertThat(reims).isEqualTo(reimList);
	}
	
	@Test
	public void findByresolverTest() {

		Reimbursement reim2 = Reimbursement.builder()
				.id(2)
				.status(Status.PENDING)
				.author(user)
				.resolver(user)
				.amount(72.00)
				.build();
		
		reim.setResolver(user);
		
		List<Reimbursement> reims = new ArrayList<Reimbursement>();

		reims.add(reim);
		reims.add(reim2);
		
		BDDMockito.given(userDAO.getUserByUsername(user.getUsername()))
			.willReturn(Optional.of(user));
		
		BDDMockito.given(reimDAO.getReimbursementByResolver(user)).willReturn(reims);
		
		List<Reimbursement> reimList = reimService.findByResolver(user.getUsername());

		assertThat(reims).isEqualTo(reimList);
	}
	
	@Test
	public void updateReimTest() {
		
		BDDMockito.given(reimDAO.save(reim)).willReturn(reim);
		
		BDDMockito.given(reimDAO.findById(reim.getId())).willReturn(Optional.of(reim));
			
		reim.setStatus(Status.ACCEPTED);
		
		Reimbursement updatedReim = reimService.updateReimbursement(reim);
		
		assertThat(Status.ACCEPTED).isEqualTo(updatedReim.getStatus());
	}

}
