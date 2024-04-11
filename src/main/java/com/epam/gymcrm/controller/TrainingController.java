package com.epam.gymcrm.controller;

import com.epam.gymcrm.dto.AddTrainingDto;
import com.epam.gymcrm.dto.TrainingMicroserviceDto;
import com.epam.gymcrm.entity.Trainer;
import com.epam.gymcrm.facade.TrainingFacade;
import com.epam.gymcrm.service.TrainerService;
import com.epam.gymcrm.service.TrainingService;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
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
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

@RestController
@AllArgsConstructor
@RequestMapping("/training")
@Slf4j
public class TrainingController {

	private final TrainingFacade trainingFacade;
	@PostMapping("/add")
	public ResponseEntity<HttpStatus> addTraining(@RequestBody AddTrainingDto request) {
		try {
			TrainingMicroserviceDto trainingMicroserviceDto = trainingFacade.createTrainingMicroserviceDto(request, "ADD");
			trainingFacade.callMicroserviceFacade(trainingMicroserviceDto);
			trainingFacade.addTrainingFacade(request);
			log.info("Training successfully added");
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			log.error("Controller: Error while creating training: {}", e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}
}