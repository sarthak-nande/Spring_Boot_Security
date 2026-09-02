package com.springbootsecurity.changingandmanaginguserindatabase.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.springbootsecurity.changingandmanaginguserindatabase.customeauthexception.CustomizeAccessDenied;
import com.springbootsecurity.changingandmanaginguserindatabase.customeauthexception.CustomizeAuthenticationEntryPoint;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class ProjectSecurtiyConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) {
//		http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());
//		http.authorizeHttpRequests((requests) -> requests.anyRequest().denyAll());
		http.cors(corsConfig -> corsConfig.configurationSource(new CorsConfigurationSource() {
	        @Override
	        public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
	            CorsConfiguration config = new CorsConfiguration();
	            config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
	            config.setAllowedMethods(Collections.singletonList("*"));
	            config.setAllowedHeaders(Collections.singletonList("*"));
	            config.setAllowCredentials(true);
	            config.setMaxAge(3600L);
	            return config;
	        }
	    }));
		http.sessionManagement(ism -> ism.invalidSessionUrl("/invalid-session"));
		http.redirectToHttps(Customizer.withDefaults());
		http.authorizeHttpRequests((requests) -> requests.requestMatchers("/user-details").authenticated()
				.requestMatchers("/home","/invalid-session").permitAll());
		http.formLogin(withDefaults());
		http.httpBasic(withDefaults());
		http.httpBasic(exc -> exc.authenticationEntryPoint(new CustomizeAuthenticationEntryPoint()));
		http.exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomizeAccessDenied()));
		//http.exceptionHandling(ehc -> ehc.authenticationEntryPoint(new CustomizeAuthenticationEntryPoint()));
		return http.build();
	}
	
//	@Bean
//	public UserDetailsService userDetailsService(DataSource dataSource) {
//		return new JdbcUserDetailsManager(dataSource);
//	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	public CompromisedPasswordChecker compromisedPasswordChecker() {
		return new HaveIBeenPwnedRestApiPasswordChecker();
	}
}
