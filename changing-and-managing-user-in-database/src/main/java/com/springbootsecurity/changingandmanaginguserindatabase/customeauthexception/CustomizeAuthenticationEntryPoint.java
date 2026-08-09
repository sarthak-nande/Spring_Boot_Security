package com.springbootsecurity.changingandmanaginguserindatabase.customeauthexception;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

public class CustomizeAuthenticationEntryPoint implements AuthenticationEntryPoint{
	
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.setHeader("Custome-Authentication", "Failed To Authenticate User");
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		
		Map<String, Object> errorDetais = new HashMap<>();
		
		errorDetais.put("timestamp", LocalDateTime.now().toString());
		errorDetais.put("status", HttpStatus.UNAUTHORIZED.value());
		errorDetais.put("error", HttpStatus.UNAUTHORIZED.getReasonPhrase());
		errorDetais.put("message" , authException.getMessage());
		errorDetais.put("path" , request.getRequestURI());
		
		response.getWriter().write(objectMapper.writeValueAsString(errorDetais));
	}

}
