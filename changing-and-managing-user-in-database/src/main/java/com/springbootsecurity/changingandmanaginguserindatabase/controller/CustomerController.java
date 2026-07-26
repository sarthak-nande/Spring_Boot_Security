package com.springbootsecurity.changingandmanaginguserindatabase.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springbootsecurity.changingandmanaginguserindatabase.model.Customer;
import com.springbootsecurity.changingandmanaginguserindatabase.repository.CustomerRepository;

@RestController
public class CustomerController {
	
	private final CustomerRepository customerRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public CustomerController(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
		this.customerRepository = customerRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody Customer customer){
		try {
			
			Optional<Customer> checkEmailPresnt = customerRepository.findByEmail(customer.getEmail());
			
			if(checkEmailPresnt.isPresent()) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User already registered");
			}
			
			String hashPassword = passwordEncoder.encode(customer.getPwd());
			customer.setEmail(hashPassword);
			
			Customer registeredUser = customerRepository.save(customer);
			
			if(registeredUser.getId() > 0) {
				return ResponseEntity.status(HttpStatus.CREATED).body("User Registration Successfully Done");
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Not Successfully Register!");
			}
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected Error Ouccred While User Registration");
		}
	}

}
