package com.revature.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.Reimbursement;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.services.ReimbursementService;
import com.revature.services.UserService;

@RestController
@RequestMapping("/reims")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class ReimbursementController {
	
	private ReimbursementService reimService;
	private UserService userService;
	
	@Autowired
	public ReimbursementController(ReimbursementService reimService, UserService userService) {
		super();
		this.reimService = reimService;
		this.userService = userService;
	}
	
	@GetMapping
	public ResponseEntity<List<Reimbursement>> getAllReimbursements(HttpSession session){
		if((boolean)session.getAttribute("logged in")==true) {
			List<Reimbursement> list = reimService.getAllReimbursements();
			return ResponseEntity.status(200).body(list);
		}
		return ResponseEntity.status(403).build();
	}
	
	@GetMapping("/{author}")
	public ResponseEntity<List<Reimbursement>> getReimbursementAuthor(@PathVariable("author")User author, HttpSession session){
		if((boolean)session.getAttribute("logged in")==true) {
			List<Reimbursement> list = reimService.findByAuthor(author.getUsername());
			return ResponseEntity.status(200).body(list);
		}
		return ResponseEntity.status(403).build();
	}
	
	@GetMapping("/{resolver}")
	public ResponseEntity<List<Reimbursement>> getReimbursementResolver(@PathVariable("resolver")User resolver, HttpSession session){
		if((boolean)session.getAttribute("logged in")==true) {
			List<Reimbursement> list = reimService.findByAuthor(resolver.getUsername());
			return ResponseEntity.status(200).body(list);
		}
		return ResponseEntity.status(403).build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Reimbursement> getOneReimbursement(@PathVariable("id") int id,
			HttpSession session) {
		Reimbursement reim = reimService.findById(id);
		if (reim != null && (boolean)session.getAttribute("logged in")==true) {
			return ResponseEntity.status(200).body(reim);
		} else {
			return ResponseEntity.status(204).build();
		}
	}
	
	@PostMapping
	public ResponseEntity<Reimbursement> addReimbursement(@RequestBody Reimbursement reim){
		if(reim!=null) {
			User user = reim.getAuthor();
			reimService.createReimbursement(reim);
			List<Reimbursement> reims = user.getReimbursements();
			reims.add(reim);
			user.setReimbursements(reims);
			userService.update(user);
			return ResponseEntity.status(201).build();
		}
		return ResponseEntity.status(403).build();
	}
	
	@PutMapping
	public ResponseEntity<Reimbursement> updateReimbursement(@RequestBody Reimbursement reim,
			HttpSession session){
		if(reim!=null && (boolean)session.getAttribute("logged in")==true) {
			User user = (User) session.getAttribute("user");
			if(user.getRole().equals(Role.MANAGER)) {
				reim.setResolver(user);
				reimService.updateReimbursement(reim);
				return ResponseEntity.status(200).build();
			}	
		}
		return ResponseEntity.status(403).build();
	}
	
	

}
