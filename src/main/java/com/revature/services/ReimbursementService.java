package com.revature.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.repositories.ReimbursementDAO;
import com.revature.repositories.UserDAO;

@Service
public class ReimbursementService {
	
	private ReimbursementDAO reimDAO;
	private UserDAO userDAO;
	
	@Autowired
	public ReimbursementService(ReimbursementDAO reimDAO, UserDAO userDAO) {
		super();
		this.reimDAO = reimDAO;
		this.userDAO = userDAO;
	}
	
	public Reimbursement createReimbursement(Reimbursement reim) {
		if(reim != null) {
			reim.setId(0);
			return reimDAO.save(reim);
		}
		return null;
	}
	
	public Reimbursement updateReimbursement(Reimbursement reim) {
		if(reim != null) {
			Reimbursement dbReim = reimDAO.findById(reim.getId()).get();
			dbReim.setStatus(reim.getStatus());
			return reimDAO.save(dbReim);
		}
		return null;
	}
	
	public Reimbursement findById(int id) {
		if(id > 0) {
			return reimDAO.findById(id).get();
		}
		return null;
	}
	
	public List<Reimbursement> findByAuthor(String username) {
		Optional<User> author = userDAO.getUserByUsername(username);
		if(author.isPresent()) {
			List<Reimbursement> reims = reimDAO.getReimbursementByAuthor(author.get());
			return reims;
		}
		return null;
	}
	
	public List<Reimbursement> findByResolver(String username) {
		Optional<User> resolver = userDAO.getUserByUsername(username);
		if(resolver.isPresent()) {
			List<Reimbursement> reims = reimDAO.getReimbursementByResolver(resolver.get());
			return reims;
		}
		return null;
	}
	
	public List<Reimbursement> getAllReimbursements(){
		return reimDAO.findAll();
	}

}
