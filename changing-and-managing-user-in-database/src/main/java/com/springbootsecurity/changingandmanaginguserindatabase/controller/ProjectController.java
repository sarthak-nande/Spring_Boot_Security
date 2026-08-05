package com.springbootsecurity.changingandmanaginguserindatabase.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ProjectController {
	
	@GetMapping("/home")
	public String login() {
		return "This is home page";
	}
	
	@GetMapping("/user-details")
	public String userDetails() {
		return "I am Sarthak Nande";
	}
	
	

}
