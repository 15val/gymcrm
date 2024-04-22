package com.epam.gymcrm.service;

import com.epam.gymcrm.dto.AddTrainingDto;
import com.epam.gymcrm.dto.TrainingDurationCountDto;
import com.epam.gymcrm.entity.Trainer;
import com.epam.gymcrm.entity.Training;
import com.epam.gymcrm.exception.FailedToModifyTrainingDurationException;
import com.epam.gymcrm.exception.UserNotFoundException;
import com.epam.gymcrm.exception.UsernameOrPasswordInvalidException;
import com.epam.gymcrm.repository.TrainingRepository;
import com.epam.gymcrm.repository.TrainingTypeRepository;
import com.epam.gymcrm.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingService {

	@Autowired
	private final TrainingRepository trainingRepository;
	@Autowired
	private final TrainingTypeRepository trainingTypeRepository;
	@Autowired
	private final UserRepository userRepository;
	@Autowired
	private final UserService userService;
	@Autowired
	private final ObjectMapper objectMapper;
	@Autowired
	private final TrainerService trainerService;
	@Autowired
	private JmsTemplate jmsTemplate;

	@Transactional
	public Long createTraining(Training training) throws UserNotFoundException, UsernameOrPasswordInvalidException {
		try {
			if (userService.isUsernameAndPasswordValid(training.getTrainee1().getUser().getId()) && userService.isUsernameAndPasswordValid(training.getTrainer1().getUser1().getId())) {
				if (userRepository.findById(training.getTrainee1().getUser().getId()).orElse(null) == null || userRepository.findById(training.getTrainer1().getUser1().getId()).orElse(null) == null) {
					throw new UserNotFoundException("User was not found");
				} else if (trainingTypeRepository.findById(training.getTrainingType1().getId()).orElse(null) == null) {
					throw new UserNotFoundException("Training type was not found");
				}
				trainingRepository.save(training);
				return training.getId();
			} else {
				throw new UsernameOrPasswordInvalidException("Username or password is invalid");
			}
		} catch (Exception e) {
			log.error("Error while creating training: {}", e.getMessage());
			throw e;
		}
	}

	@Transactional
	public Training getTrainingById(Long trainingId) throws UserNotFoundException {
		try {
			Training training = trainingRepository.findById(trainingId).orElse(null);
			if (training == null) {
				throw new UserNotFoundException("Training was not found");
			} else {
				return training;
			}
		} catch (Exception e) {
			log.error("Error while retrieving training: {}", e.getMessage());
			throw e;
		}
	}

	public TrainingDurationCountDto createTrainingMicroserviceDto(AddTrainingDto request, String actionType) throws ParseException {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date trainingDate = dateFormat.parse(request.getTrainingDate());
			Trainer trainer = trainerService.getTrainerByUsername(request.getTrainerUsername());
			return TrainingDurationCountDto.builder()
					.trainerUsername(trainer.getUser1().getUsername())
					.trainerFirstName(trainer.getUser1().getFirstName())
					.trainerLastName(trainer.getUser1().getLastName())
					.isActive(trainer.getUser1().getIsActive())
					.trainingDate(trainingDate)
					.trainingDuration(Integer.valueOf(request.getTrainingDuration()))
					.actionType(actionType)
					.build();

		} catch (Exception e) {
			log.error("Error while creating TrainingMicroserviceDto: {}", e.getMessage());
			throw e;
		}
	}

	public void callTrainingDurationMicroservice(TrainingDurationCountDto trainingDurationCountDto) throws FailedToModifyTrainingDurationException, JsonProcessingException {
		try {
			String json = objectMapper.writeValueAsString(trainingDurationCountDto);
			jmsTemplate.convertAndSend("trainingDurationQueue", json);
		}
		catch (Exception e) {
			log.error("Error while calling Training Duration Microservice: {}", e.getMessage());
			throw e;
		}

	}

	public void fallbackForCallTrainingDurationMicroservice(TrainingDurationCountDto trainingDurationCountDto, ResourceAccessException ex) {
		log.error("Fallback for callTrainingDurationMicroservice: {}", ex.getMessage());
		throw ex;
	}
}
