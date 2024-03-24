package com.epam.gymcrm.controller;

import com.epam.gymcrm.dto.AddTrainingDto;
import com.epam.gymcrm.facade.TrainingFacade;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/training")
@Slf4j
public class TrainingController {

	private final TrainingFacade trainingFacade;

	@PostMapping("/add")
	public ResponseEntity<HttpStatus> addTraining(@RequestBody AddTrainingDto request) {
		try {
			trainingFacade.addTrainingFacade(request);
			log.info("Training successfully added");
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			log.error("Controller: Error while creating training: {}", e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}

}