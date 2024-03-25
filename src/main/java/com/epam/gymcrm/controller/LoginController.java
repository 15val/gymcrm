package com.epam.gymcrm.controller;

import com.epam.gymcrm.dto.JwtResponse;
import com.epam.gymcrm.dto.UpdatePasswordDto;
import com.epam.gymcrm.dto.UsernameAndPasswordDto;
import com.epam.gymcrm.entity.AppUserDetails;
import com.epam.gymcrm.facade.LoginFacade;
import com.epam.gymcrm.service.AppUserDetailsService;
import com.epam.gymcrm.utils.JwtTokenUtil;
import jakarta.annotation.security.PermitAll;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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
	private final AuthenticationManager authenticationManager;
	private final AppUserDetailsService appUserDetailsService;
	private final JwtTokenUtil jwtTokenUtil;

	@GetMapping("/login")
	ResponseEntity<?> login(@RequestBody UsernameAndPasswordDto request){
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
			log.info("Login successful: Username: " + request.getUsername() + ", Password: " + request.getPassword());
		}catch (Exception e){
			log.error("Controller: Error while login: {}", e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
		UserDetails appUserDetails = appUserDetailsService.loadUserByUsername(request.getUsername());
		String token = jwtTokenUtil.generateToken(appUserDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}

	/*@GetMapping("/login")
	ResponseEntity<HttpStatus> login(@RequestBody UsernameAndPasswordDto request){
		if(request == null){
			log.info("Logged as unknown user");
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
	}*/

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
