package com.epam.gymcrm.controller;

import com.epam.gymcrm.dto.AddTrainingDto;
import com.epam.gymcrm.dto.GetTrainingTypeListDto;
import com.epam.gymcrm.facade.TrainingFacade;
import com.epam.gymcrm.facade.TrainingTypeFacade;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/trainingType")
@Slf4j
public class TrainingTypeController {

	private final TrainingTypeFacade trainingTypeFacade;

	@GetMapping("/get")
	public ResponseEntity<GetTrainingTypeListDto> getTrainingTypeList() {
		try {
			GetTrainingTypeListDto response = trainingTypeFacade.getTrainingTypeListFacade();
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			log.error("Controller: Error while retrieving training type list: {}", e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}

}
