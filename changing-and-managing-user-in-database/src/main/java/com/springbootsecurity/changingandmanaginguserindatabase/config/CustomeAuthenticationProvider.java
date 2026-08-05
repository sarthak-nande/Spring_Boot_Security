package com.springbootsecurity.changingandmanaginguserindatabase.config;

import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomeAuthenticationProvider implements AuthenticationProvider{
	
	private final AuthSystemUserDetailsService customeUserDetails;
	private final PasswordEncoder passwordEncoder;
	
	public CustomeAuthenticationProvider(AuthSystemUserDetailsService customeUserDetails, PasswordEncoder passwordEncoder) {
		this.customeUserDetails = customeUserDetails;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		UserDetails userDetails = customeUserDetails.loadUserByUsername(username);
		if(passwordEncoder.matches(password, userDetails.getPassword())) {
			return new UsernamePasswordAuthenticationToken(password, userDetails.getUsername(), userDetails.getAuthorities());
		} else {
			throw new BadCredentialsException(password);
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
