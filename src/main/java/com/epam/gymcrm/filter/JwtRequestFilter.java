package com.epam.gymcrm.filter;

import com.epam.gymcrm.exception.TokenIsBlacklistedException;
import com.epam.gymcrm.service.TokenBlacklistService;
import com.epam.gymcrm.utils.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.SignatureException;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

	private final JwtTokenUtil jwtTokenUtil;
	private final TokenBlacklistService tokenBlacklistService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");
		String username = null;
		String jwtToken = null;
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			jwtToken = authHeader.substring(7);
			log.info("Authentication token is: " + jwtToken);
			try {
				if (tokenBlacklistService.isBlacklisted(jwtToken)) {
					throw new TokenIsBlacklistedException("Authentication token is blacklisted");
				}
				username = jwtTokenUtil.getUsername(jwtToken);
			} catch (ExpiredJwtException e) {
				log.error("Token expired");
			} catch (Exception e) {
				log.error("Filter: Failure: " + e.getMessage());
			}
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
					username,
					null,
					jwtTokenUtil.getRoles(jwtToken).stream()
							.map(SimpleGrantedAuthority::new)
							.toList()
			);
			SecurityContextHolder.getContext().setAuthentication(token);
		}
		filterChain.doFilter(request, response);
	}
}
