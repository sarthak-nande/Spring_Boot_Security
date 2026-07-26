package com.springbootsecurity.changingandmanaginguserindatabase.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="customer")
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String email;
	
	private String pwd;
	
	private String role;
	
	public Customer() {
		
	}

	public Customer(String email, String pwd, String role) {
		super();
		this.email = email;
		this.pwd = pwd;
		this.role = role;
	}

	public Customer(long id, String email, String pwd, String role) {
		super();
		this.id = id;
		this.email = email;
		this.pwd = pwd;
		this.role = role;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", email=" + email + ", pwd=" + pwd + ", role=" + role + "]";
	}
	

}
