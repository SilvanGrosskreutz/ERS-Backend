package com.revature.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.Reimbursement;
import com.revature.models.User;

public interface ReimbursementDAO extends JpaRepository<Reimbursement, Integer>{
	
	public List<Reimbursement> getReimbursementByAuthor(User author);
	public List<Reimbursement> getReimbursementByResolver(User resolver);
}
