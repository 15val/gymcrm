package com.epam.gymcrm.controller;

import com.epam.gymcrm.dto.AddTrainingDto;
import com.epam.gymcrm.dto.TrainingMicroserviceDto;
import com.epam.gymcrm.entity.Trainer;
import com.epam.gymcrm.facade.TrainingFacade;
import com.epam.gymcrm.service.TrainerService;
import com.epam.gymcrm.service.TrainingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@AllArgsConstructor
@RequestMapping("/training")
@Slf4j
public class TrainingController {

	private final TrainingFacade trainingFacade;
	private final RestTemplate restTemplate;

	@PostMapping("/add")
	public ResponseEntity<HttpStatus> addTraining(@RequestBody AddTrainingDto request) {
		log.info("Adding training  started");
		try {
			TrainingMicroserviceDto trainingMicroserviceDto = trainingFacade.createTrainingMicroserviceDto(request, "ADD");
			log.info("DTO created: " + trainingMicroserviceDto.toString());
			restTemplate.postForEntity("http://localhost:8080/training/modifyWorkingTime", trainingMicroserviceDto, ResponseEntity.class);
			log.info("RestTemplate request sent");
			trainingFacade.addTrainingFacade(request);
			log.info("Training successfully added");
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			log.error("Controller: Error while creating training: {}", e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}

}