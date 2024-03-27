package com.epam.gymcrm.controller;

import com.epam.gymcrm.facade.LogoutFacade;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class LogoutController {

	private final LogoutFacade logoutFacade;

	@GetMapping("/log_out")
	ResponseEntity<HttpStatus> logout(HttpServletRequest request){
		try{
			logoutFacade.logout(request);
			log.info("Logged out successfully");
			return ResponseEntity.ok().build();
		}
		catch (Exception e){
			log.error("Controller: Error while login: {}", e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}

}
