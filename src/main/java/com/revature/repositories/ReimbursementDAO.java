package com.revature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.Reimbursement;

public interface ReimbursementDAO extends JpaRepository<Reimbursement, Integer>{

}
