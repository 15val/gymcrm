package com.epam.gymcrm.facade;

import com.epam.gymcrm.service.TokenBlacklistService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
@RequiredArgsConstructor
@Slf4j
public class LogoutFacade {

	private final TokenBlacklistService tokenBlacklistService;

	public void logout(HttpServletRequest request){
		String authHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			jwtToken = authHeader.substring(7);
			tokenBlacklistService.addToBlacklist(jwtToken);
		}
	}
}
