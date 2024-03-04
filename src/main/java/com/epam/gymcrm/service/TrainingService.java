package com.epam.gymcrm.service;

import com.epam.gymcrm.entity.Training;
import com.epam.gymcrm.exception.UserNotFoundException;
import com.epam.gymcrm.exception.UsernameOrPasswordInvalidException;
import com.epam.gymcrm.repository.TrainingRepository;
import com.epam.gymcrm.repository.TrainingTypeRepository;
import com.epam.gymcrm.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingService {
	private final TrainingRepository trainingRepository;
	private final TrainingTypeRepository trainingTypeRepository;
	private final UserRepository userRepository;
	private final UserService userService;

	@Transactional
	public Long createTraining(Training training) throws UserNotFoundException, UsernameOrPasswordInvalidException {
		try {
			if(userService.isUsernameAndPasswordValid(training.getTrainee1().getUser().getId()) && userService.isUsernameAndPasswordValid(training.getTrainer1().getUser1().getId())) {
				if (userRepository.findById(training.getTrainee1().getUser().getId()).orElse(null) == null || userRepository.findById(training.getTrainer1().getUser1().getId()).orElse(null) == null) {
					throw new UserNotFoundException("User was not found");
				} else if (trainingTypeRepository.findById(training.getTrainingType1().getId()).orElse(null) == null) {
					throw new UserNotFoundException("Training type was not found");
				}
				trainingRepository.save(training);
				return training.getId();
			}
			else {
				throw new UsernameOrPasswordInvalidException("Username or password is invalid");
			}
		}
		catch (Exception e) {
			log.error("Error while creating training: {}", e.getMessage());
			throw e;
		}
	}

	@Transactional
	public Training getTrainingById(Long trainingId) throws UserNotFoundException {
		try {
			Training training = trainingRepository.findById(trainingId).orElse(null);
			if(training == null){
				throw new UserNotFoundException("Training was not found");
			}
			else {
				return training;
			}
		}
		catch (Exception e) {
			log.error("Error while retrieving training: {}", e.getMessage());
			throw e;
		}
	}

}
