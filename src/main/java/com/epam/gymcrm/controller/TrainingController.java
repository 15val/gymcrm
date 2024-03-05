package com.epam.gymcrm.controller;

import com.epam.gymcrm.entity.Trainee;
import com.epam.gymcrm.entity.Trainer;
import com.epam.gymcrm.entity.Training;
import com.epam.gymcrm.entity.TrainingType;
import com.epam.gymcrm.service.TraineeService;
import com.epam.gymcrm.service.TrainerService;
import com.epam.gymcrm.service.TrainingService;
import com.epam.gymcrm.service.TrainingTypeService;
import com.epam.gymcrm.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/training")
@Slf4j
public class TrainingController {

	private final TraineeService traineeService;
	private final TrainerService trainerService;
	private final TrainingService trainingService;
	@Transactional
	@PostMapping("/register")
	public ResponseEntity<HttpStatus> registerTrainer(@RequestBody Map<String, String> request) {
		try {
			String traineeUsername = request.get("traineeUsername");
			String trainerUsername = request.get("trainerUsername");
			String trainingName = request.get("trainingName");
			String trainingDateString = request.get("trainingDate");
			String trainingDurationString = request.get("trainingDuration");
			Date trainingDate;
			Integer trainingDuration;
			if (traineeUsername == null || trainerUsername == null || trainingName == null || trainingDateString == null || trainingDurationString == null) {
				return ResponseEntity.badRequest().build();
			} else {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				trainingDate = dateFormat.parse(trainingDateString);
				trainingDuration = Integer.valueOf(trainingDurationString);
			}
			Trainee trainee = traineeService.getTraineeByUsername(traineeUsername);
			Trainer trainer = trainerService.getTrainerByUsername(traineeUsername);
			TrainingType trainingType = trainer.getTrainingType2();
			Training training = Training.builder()
					.trainee1(trainee)
					.trainer1(trainer)
					.trainingName(trainingName)
					.trainingType1(trainingType)
					.trainingDate(trainingDate)
					.trainingDuration(trainingDuration)
					.build();
			trainingService.createTraining(training);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			log.error("Controller: Error while creating trainer: {}", e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}

}