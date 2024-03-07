package com.epam.gymcrm.controller;

import com.epam.gymcrm.dto.request.CreateTraineeDto;
import com.epam.gymcrm.dto.request.UpdateTraineeDto;
import com.epam.gymcrm.dto.response.UpdateTraineeResponseDto;
import com.epam.gymcrm.dto.response.UsernameAndPasswordResponseDto;
import com.epam.gymcrm.facade.TraineeFacade;

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
@RequestMapping("/trainee")
@Slf4j
public class TraineeController {

	private final TraineeFacade traineeFacade;

	@PostMapping("/register")
	public ResponseEntity<UsernameAndPasswordResponseDto> registerTrainee(@RequestBody CreateTraineeDto request) {
		try {
			UsernameAndPasswordResponseDto response = traineeFacade.registerTraineeFacade(request);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			log.error("Controller: Error while creating trainee: {}", e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}

	@PutMapping("/update")
	public ResponseEntity<UpdateTraineeResponseDto> updateTrainee(@RequestBody UpdateTraineeDto request) {
		try{
			UpdateTraineeResponseDto response = traineeFacade.updateTraineeFacade(request);
			return ResponseEntity.ok(response);
		}
		catch (Exception e) {
			log.error("Controller: Error while updating trainee: {}", e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}
}


