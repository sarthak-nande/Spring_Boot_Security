package com.springbootsecurity.changingandmanaginguserindatabase.customeauthexception;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

public class CustomizeAccessDenied implements AccessDeniedHandler{
	
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		response.setHeader("Custome-Authentication", "Failed To Authenticate User");
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(HttpStatus.FORBIDDEN.value());
		
		Map<String, Object> errorDetais = new HashMap<>();
		
		errorDetais.put("timestamp", LocalDateTime.now().toString());
		errorDetais.put("status", HttpStatus.FORBIDDEN.value());
		errorDetais.put("error", HttpStatus.FORBIDDEN.getReasonPhrase());
		errorDetais.put("message" , accessDeniedException.getMessage());
		errorDetais.put("path" , request.getRequestURI());
		
		response.getWriter().write(objectMapper.writeValueAsString(errorDetais));
		
	}

}
