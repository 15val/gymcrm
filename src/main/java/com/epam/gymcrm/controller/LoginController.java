package com.epam.gymcrm.controller;

import com.epam.gymcrm.dto.UpdatePasswordDto;
import com.epam.gymcrm.dto.UsernameAndPasswordDto;
import com.epam.gymcrm.facade.LoginFacade;
import jakarta.annotation.security.PermitAll;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class LoginController {

	private final LoginFacade loginFacade;


	@GetMapping("/login")
	ResponseEntity<HttpStatus> login(@RequestBody (required = false) UsernameAndPasswordDto request){
		if(request == null){
			log.info("Logged as unauthenticated user");
			return ResponseEntity.ok().build();
		}
		try {
			loginFacade.loginFacade(request);
			log.info("Login successful: Username: " + request.getUsername() + ", Password: " + request.getPassword());
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			log.error("Controller: Error while login: {}", e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}

	@PutMapping("/updatePassword")
	ResponseEntity<HttpStatus> updatePassword(@RequestBody UpdatePasswordDto request){
		try {
			loginFacade.updatePasswordFacade(request);
			log.info("Password updated successfully: New password is: " + request.getNewPassword());
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			log.error("Controller: Error while updating password: {}", e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}
}
