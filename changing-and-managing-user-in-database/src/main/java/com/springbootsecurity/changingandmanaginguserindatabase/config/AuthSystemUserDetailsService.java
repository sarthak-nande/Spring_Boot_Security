package com.springbootsecurity.changingandmanaginguserindatabase.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springbootsecurity.changingandmanaginguserindatabase.model.Customer;
import com.springbootsecurity.changingandmanaginguserindatabase.repository.CustomerRepository;

@Service
public class AuthSystemUserDetailsService implements UserDetailsService {
	
	private final CustomerRepository customerRepository;
	
	@Autowired
	public AuthSystemUserDetailsService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Customer customer  = customerRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("User not found for username " + username));
		List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(customer.getRole()));
		return new User(customer.getEmail(), customer.getPwd(), authorities);
	}

}
