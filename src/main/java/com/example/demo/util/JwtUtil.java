package com.example.demo.util;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {
	private static final SecretKey SECRET_KEY=Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	//Generate Token
	public static String generateToken(String username, List<String> roles) {
		return Jwts.builder()
				.setSubject(username)
				.claim("roles",roles)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+3600_000)) //1 hour
				.signWith(SECRET_KEY)
				.compact();
	}
	
	//Validate Token
	public static Map<String, Object> validateToken(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(SECRET_KEY)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
}
