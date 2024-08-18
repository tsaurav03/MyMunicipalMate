package com.app.mmm.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

// Utility class
@Component
public class JwtTokenProvider {

	@Value("${app.jwt-secret}")
	private String jwtSecret;

	@Value("${app.jwt-expiration-milliseconds}")
	private Long jwtExpirationDate;

	// generate JWT token
	public String generateToken(Authentication authentication) {
		String username = authentication.getName(); 
		Date currentDate = new Date();
		Date exipreDate = new Date(currentDate.getTime() + jwtExpirationDate);

		String token = Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(exipreDate)
				.signWith(key())
				.compact();

		return token;

	}

	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}

	public String getUsername(String token) {
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(key())
				.build()
				.parseClaimsJws(token)
				.getBody();

		String username = claims.getSubject();

		return username;
	}

	// Validate JWT Token
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}
}