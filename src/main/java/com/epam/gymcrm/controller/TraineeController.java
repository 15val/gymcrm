package com.epam.gymcrm.controller;

import com.epam.gymcrm.dto.CreateTraineeDto;
import com.epam.gymcrm.dto.GetTraineeDto;
import com.epam.gymcrm.dto.GetTraineesTrainingListRequestDto;
import com.epam.gymcrm.dto.GetTrainerListDto;
import com.epam.gymcrm.dto.GetTrainingListDto;
import com.epam.gymcrm.dto.UpdateTraineeDto;
import com.epam.gymcrm.dto.UpdateTraineeResponseDto;
import com.epam.gymcrm.dto.UpdateTraineesTrainerListDto;
import com.epam.gymcrm.dto.UpdateTraineesTrainerListResponseDto;
import com.epam.gymcrm.dto.UsernameAndIsActiveDto;
import com.epam.gymcrm.dto.UsernameAndPasswordDto;
import com.epam.gymcrm.dto.UsernameDto;
import com.epam.gymcrm.facade.TraineeFacade;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
	public ResponseEntity<UsernameAndPasswordDto> registerTrainee(@RequestBody CreateTraineeDto request) {
		try {
			UsernameAndPasswordDto response = traineeFacade.registerTraineeFacade(request);
			log.info("Trainee successfully registered");
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
			log.info("Trainee successfully updated " + response.toString());
			return ResponseEntity.ok(response);
		}
		catch (Exception e) {
			log.error("Controller: Error while updating trainee: {}", e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}

	@PutMapping("/updateTrainersList")
	public ResponseEntity<UpdateTraineesTrainerListResponseDto> updateTraineesTrainerList(@RequestBody UpdateTraineesTrainerListDto request) {
		try{
			UpdateTraineesTrainerListResponseDto response = traineeFacade.updateTraineesTrainerListFacade(request);
			log.info("Trainee's trainer list successfully updated");
			return ResponseEntity.ok(response);
		}
		catch (Exception e) {
			log.error("Controller: Error while updating trainee's trainer list: {}", e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}

	@GetMapping("/get")
	public ResponseEntity<GetTraineeDto> getTrainee(@RequestBody UsernameDto request){
		try {
			GetTraineeDto response = traineeFacade.getTraineeFacade(request);
			log.info("Trainee successfully retrieved");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			log.error("Controller: Error while retrieving trainee: {}", e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}

	@DeleteMapping("/delete")
	public ResponseEntity<HttpStatus> deleteTrainee(@RequestBody UsernameDto request) {
		try {
			traineeFacade.deleteTraineeFacade(request);
			log.info("Trainee successfully deleted");
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			log.error("Controller: Error while deleting trainee: {}", e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}

	@PatchMapping("/updateIsActive")
	public ResponseEntity<HttpStatus> updateIsActive(@RequestBody UsernameAndIsActiveDto request) {
		try {
			traineeFacade.updateIsActiveTrainee(request);
			log.info("Trainee's isActive successfully updated");
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			log.error("Controller: Error while updating isActive trainee: {}", e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}

	@GetMapping("/getUnassignedTrainers")
	public ResponseEntity<GetTrainerListDto> getUnassignedTrainers(@RequestBody UsernameDto request) {
		try {
			GetTrainerListDto response = traineeFacade.getUnassignedTrainersFacade(request);
			log.info("Unassigned trainers successfully retrieved");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			log.error("Controller: Error while retrieving unassigned trainers: {}", e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}

	@GetMapping("/getTrainingList")
	public ResponseEntity<GetTrainingListDto> getTraineesTrainingList (@RequestBody GetTraineesTrainingListRequestDto request){
		try {
			GetTrainingListDto response = traineeFacade.getTraineesTrainingListFacade(request);
			log.info("Trainee's list of trainings successfully retrieved");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			log.error("Controller: Error while retrieving trainee's list of trainings: {}", e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}
}


