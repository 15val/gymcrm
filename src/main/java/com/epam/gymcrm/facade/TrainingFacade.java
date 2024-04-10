package com.epam.gymcrm.facade;

import com.epam.gymcrm.dto.AddTrainingDto;
import com.epam.gymcrm.dto.TrainingMicroserviceDto;
import com.epam.gymcrm.entity.Trainee;
import com.epam.gymcrm.entity.Trainer;
import com.epam.gymcrm.entity.Training;
import com.epam.gymcrm.entity.TrainingType;
import com.epam.gymcrm.exception.UserNotFoundException;
import com.epam.gymcrm.exception.UsernameOrPasswordInvalidException;
import com.epam.gymcrm.service.TraineeService;
import com.epam.gymcrm.service.TrainerService;
import com.epam.gymcrm.service.TrainingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingFacade {

	private final TraineeService traineeService;
	private final TrainerService trainerService;
	private final TrainingService trainingService;

	public void addTrainingFacade(AddTrainingDto request) throws UserNotFoundException, UsernameOrPasswordInvalidException, ParseException {
		try {
			String traineeUsername = request.getTraineeUsername();
			String trainerUsername = request.getTrainerUsername();
			String trainingName = request.getTrainingName();
			String trainingDateString = request.getTrainingDate();
			String trainingDurationString = request.getTrainingDuration();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date trainingDate = dateFormat.parse(trainingDateString);
			Integer trainingDuration = Integer.valueOf(trainingDurationString);

			Trainee trainee = traineeService.getTraineeByUsername(traineeUsername);
			Trainer trainer = trainerService.getTrainerByUsername(trainerUsername);
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
			trainee.getTrainerList().add(trainer);
			trainer.getTraineeList().add(trainee);
			traineeService.updateTrainee(trainee);
			trainerService.updateTrainer(trainer);
		} catch (DataIntegrityViolationException e) {
			log.error("Facade: Training for these trainer and trainee already exists");
			throw e;
		} catch (Exception e) {
			log.error("Facade: Error while creating training: {}", e.getMessage());
			throw e;
		}
	}

	public TrainingMicroserviceDto createTrainingMicroserviceDto(AddTrainingDto request, String actionType) throws ParseException {
		log.info("DTO creating started");
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date trainingDate = dateFormat.parse(request.getTrainingDate());
			Trainer trainer = trainerService.getTrainerByUsername(request.getTrainerUsername());
			return TrainingMicroserviceDto.builder()
					.trainerUsername(trainer.getUser1().getUsername())
					.trainerFirstName(trainer.getUser1().getFirstName())
					.trainerLastName(trainer.getUser1().getLastName())
					.isActive(trainer.getUser1().getIsActive())
					.trainingDate(trainingDate)
					.trainingDuration(Integer.valueOf(request.getTrainingDuration()))
					.actionType(actionType)
					.build();

		} catch (Exception e) {
			log.error("Facade: Error while creating TrainingMicroserviceDto: {}", e.getMessage());
			throw e;
		}
	}
}