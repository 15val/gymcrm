package com.epam.gymcrm.controller;

import com.epam.gymcrm.dto.request.CreateTrainerDto;
import com.epam.gymcrm.dto.response.UsernameAndPasswordResponseDto;
import com.epam.gymcrm.facade.TrainerFacade;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/trainer")
@Slf4j
public class TrainerController {

	private final TrainerFacade trainerFacade;

	@PostMapping("/register")
	public ResponseEntity<UsernameAndPasswordResponseDto> registerTrainer(@RequestBody CreateTrainerDto request) {
		try {
			UsernameAndPasswordResponseDto response = trainerFacade.registerTrainerFacade(request);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			log.error("Controller: Error while creating trainer: {}", e.getMessage());
			return ResponseEntity.internalServerError().build();
		}

	}
}