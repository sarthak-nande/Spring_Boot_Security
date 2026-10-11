package com.jwtauth.jwtauth.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtService {

	public String secret;
	
	public long tokenExpirationTimeInMins;
	
	public SecretKey generateSignInKey() {
		byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(generateSignInKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
	public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}
	
	public Date extractExpirationTime(String token) {
		return extractAllClaims(token).getExpiration();
	}
	
	public String generateToken(Map<String, Object> extraClaims, String username) {
		return Jwts.builder()
				.claims(extraClaims)
				.subject(username)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + tokenExpirationTimeInMins))
				.signWith(generateSignInKey())
				.compact();
	}
	
	public String generateToken(String username) {
		return generateToken(new HashMap<>(), username);
	}
	
	public boolean isTokenExpired(String token) {
		return extractExpirationTime(token).before(new Date());
	}
	
	public boolean verifyToken(String token, UserDetails userDetails) {
		String username = extractUsername(token);
		return username.equals(userDetails.getUsername());
	}
}
