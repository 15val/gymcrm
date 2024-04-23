package com.epam.gymcrm.controller;

import com.epam.gymcrm.dto.AddTrainingDto;
import com.epam.gymcrm.dto.TrainingDurationCountDto;
import com.epam.gymcrm.facade.TrainingFacade;
import com.epam.gymcrm.service.TrainingService;
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
	private final TrainingService trainingService;
	private static final String ACTION_TYPE_ADD = "ADD";

	@PostMapping("/add")
	public ResponseEntity<HttpStatus> addTraining(@RequestBody AddTrainingDto request) {
		try {
			TrainingDurationCountDto trainingMicroserviceDto = trainingService.createTrainingMicroserviceDto(request, ACTION_TYPE_ADD);
			trainingService.callTrainingDurationMicroservice(trainingMicroserviceDto);
			trainingFacade.addTraining(request);
			log.info("Training successfully added");
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			log.error("Controller: Error while creating training: {}", e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}
}