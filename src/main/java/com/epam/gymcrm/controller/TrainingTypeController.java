package com.epam.gymcrm.controller;

import com.epam.gymcrm.dto.GetTrainingTypeListDto;
import com.epam.gymcrm.facade.TrainingTypeFacade;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trainingType")
@Slf4j
public class TrainingTypeController {

	private final TrainingTypeFacade trainingTypeFacade;
	private Counter counter;
	private Gauge gauge;

	public TrainingTypeController(TrainingTypeFacade trainingTypeFacade, CollectorRegistry collectorRegistry) {
		this.trainingTypeFacade = trainingTypeFacade;
		this.counter = Counter.build()
				.name("training_type_fetch_request_counter")
				.help("Training Type Fetch Count")
				.register(collectorRegistry);
		this.gauge = Gauge.build()
				.name("training_type_total_amount_gauge")
				.help("Training Types Total Amount")
				.register(collectorRegistry);
	}

	@GetMapping("/get")
	public ResponseEntity<GetTrainingTypeListDto> getTrainingTypeList() {
		try {
			GetTrainingTypeListDto response = trainingTypeFacade.getTrainingTypeList();
			counter.inc();
			gauge.set(response.getTrainingTypeList().size());
			log.info("Training type list successfully retrieved");
			return ResponseEntity.ok(response);
		}
		catch (Exception e) {
			log.error("Controller: Error while retrieving training type list: {}", e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}

}
