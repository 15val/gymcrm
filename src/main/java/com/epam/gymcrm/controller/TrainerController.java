package com.epam.gymcrm.controller;

import com.epam.gymcrm.dto.request.CreateTrainerDto;
import com.epam.gymcrm.dto.request.UpdateTraineeDto;
import com.epam.gymcrm.dto.request.UpdateTrainerDto;
import com.epam.gymcrm.dto.response.UpdateTraineeResponseDto;
import com.epam.gymcrm.dto.response.UpdateTrainerResponseDto;
import com.epam.gymcrm.dto.response.UsernameAndPasswordResponseDto;
import com.epam.gymcrm.facade.TrainerFacade;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@PutMapping("/update")
	public ResponseEntity<UpdateTrainerResponseDto> updateTrainer(@RequestBody UpdateTrainerDto request) {
		try{
			UpdateTrainerResponseDto response = trainerFacade.updateTrainerFacade(request);
			return ResponseEntity.ok(response);
		}
		catch (Exception e) {
			log.error("Controller: Error while updating trainer: {}", e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}

}