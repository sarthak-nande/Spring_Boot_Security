package com.springbootsecurity.changingdefaultsecurityconfiguration.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurtiyConfig {
	
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) {
//		http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());
//		http.authorizeHttpRequests((requests) -> requests.anyRequest().denyAll());
		http.authorizeHttpRequests((requests) -> requests.requestMatchers("/user-details").authenticated()
				.requestMatchers("/home").permitAll());
		http.formLogin(withDefaults());
		http.httpBasic(withDefaults());
		return http.build();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user = User.withUsername("user").password("{bcrypt}$2a$12$qi1dJkvYFfhjF1zppykQLODxOCbz4GcENpp3nJgl4UEpFrg52.dBq").authorities("user").build();
		UserDetails admin = User.withUsername("admin").password("{bcrypt}$2a$12$qi1dJkvYFfhjF1zppykQLODxOCbz4GcENpp3nJgl4UEpFrg52.dBq").authorities("admin").build();
		
		return new InMemoryUserDetailsManager(user,admin);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

}
