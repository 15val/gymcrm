package com.epam.gymcrm.controller;

import com.epam.gymcrm.dto.CreateTraineeDto;
import com.epam.gymcrm.dto.GetTraineeDto;
import com.epam.gymcrm.dto.UpdateTraineeDto;
import com.epam.gymcrm.dto.UpdateTraineesTrainerListDto;
import com.epam.gymcrm.dto.UpdateTraineeResponseDto;
import com.epam.gymcrm.dto.UpdateTraineesTrainerListResponseDto;
import com.epam.gymcrm.dto.UsernameAndPasswordDto;
import com.epam.gymcrm.dto.UsernameDto;
import com.epam.gymcrm.facade.TraineeFacade;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

	@PutMapping("/updateTrainersList")
	public ResponseEntity<UpdateTraineesTrainerListResponseDto> updateTraineesTrainerList(@RequestBody UpdateTraineesTrainerListDto request) {
		try{
			UpdateTraineesTrainerListResponseDto response = traineeFacade.updateTraineesTrainerListFacade(request);
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
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			log.error("Controller: Error while retrieving trainee: {}", e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}
}


