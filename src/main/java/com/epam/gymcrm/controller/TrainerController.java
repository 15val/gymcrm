package com.epam.gymcrm.controller;

import com.epam.gymcrm.dto.CreateTrainerDto;
import com.epam.gymcrm.dto.GetTraineeDto;
import com.epam.gymcrm.dto.GetTraineesTrainingListRequestDto;
import com.epam.gymcrm.dto.GetTrainerDto;
import com.epam.gymcrm.dto.GetTrainersTrainingListRequestDto;
import com.epam.gymcrm.dto.GetTrainingListDto;
import com.epam.gymcrm.dto.UpdateTrainerDto;
import com.epam.gymcrm.dto.UpdateTrainerResponseDto;
import com.epam.gymcrm.dto.UsernameAndIsActiveDto;
import com.epam.gymcrm.dto.UsernameAndPasswordDto;
import com.epam.gymcrm.dto.UsernameDto;
import com.epam.gymcrm.facade.TrainerFacade;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
	public ResponseEntity<UsernameAndPasswordDto> registerTrainer(@RequestBody CreateTrainerDto request) {
		try {
			UsernameAndPasswordDto response = trainerFacade.registerTrainerFacade(request);
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

	@GetMapping("/get")
	public ResponseEntity<GetTrainerDto> getTrainer(@RequestBody UsernameDto request){
		try {
			GetTrainerDto response = trainerFacade.getTrainerFacade(request);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			log.error("Controller: Error while retrieving trainer: {}", e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}

	@PatchMapping("/updateIsActive")
	public ResponseEntity<HttpStatus> updateIsActive(@RequestBody UsernameAndIsActiveDto request) {
		try {
			trainerFacade.updateIsActiveTrainer(request);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			log.error("Controller: Error while updating isActive trainer: {}", e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}

	@GetMapping("/getTrainingList")
	public ResponseEntity<GetTrainingListDto> getTrainersTrainingList (@RequestBody GetTrainersTrainingListRequestDto request){
		try {
			GetTrainingListDto response = trainerFacade.getTrainersTrainingListFacade(request);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			log.error("Controller: Error while retrieving trainer's list of trainings: {}", e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}
}