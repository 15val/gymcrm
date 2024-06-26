package com.epam.gymcrm.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtTokenUtil {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.lifetime}")
	private Duration jwtLifetime;

	public String generateToken(UserDetails userDetails){
		Map<String,Object> claims = new HashMap<>();
		List<String> rolesList = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.toList();
		claims.put("roles", rolesList);

		Date dateOfIssue = new Date();
		Date dateOfExpiration = new Date(dateOfIssue.getTime() + jwtLifetime.toMillis());
		return Jwts.builder()
				.claims()
				.issuedAt(dateOfIssue)
				.expiration(dateOfExpiration)
				.subject(userDetails.getUsername())
				.add(claims)
				.and()
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}

	private Claims getAllClaimsFromToken(String token){
		return Jwts.parser()
				.setSigningKey(secret)
				.build()
				.parseClaimsJws(token)
				.getPayload();
	}

	public String getUsername(String token){
		return getAllClaimsFromToken(token).getSubject();
	}

	public List<String> getRoles(String token){
		return getAllClaimsFromToken(token).get("roles", List.class);
	}

}
