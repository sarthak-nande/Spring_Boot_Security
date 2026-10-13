package com.jwtauth.jwtauth.security;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.jwtauth.jwtauth.entity.User;
import com.jwtauth.jwtauth.repository.UserRepository;
import com.jwtauth.jwtauth.service.JwtService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OauthTwoSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
	
	private final UserRepository userRepository;
	private final JwtService jwtService;
	
	@Autowired
	public OauthTwoSuccessHandler(UserRepository userRepository, JwtService jwtService) {
		this.userRepository = userRepository;
		this.jwtService = jwtService;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
		
		String email = oAuth2User.getAttribute("email");
		String name = oAuth2User.getAttribute("name");
		
		User user = userRepository.findUserByEmail(email).orElseGet(() -> {
			User newUser = new User();
			newUser.setEmail(email);
			newUser.setFullName(name);
			newUser.setRole("ROLE_ADMIN");
			newUser.setPassword(UUID.randomUUID().toString());
			newUser.setEnabled(true);
			
			return userRepository.save(newUser);
		});
		
		String token = jwtService.generateToken(email);
		
		String targetUrl = UriComponentsBuilder.fromUriString("http;//localhost:5173/oauth/callback")
				.queryParam(token, "token")
				.build()
				.toUriString();
		
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}

}
