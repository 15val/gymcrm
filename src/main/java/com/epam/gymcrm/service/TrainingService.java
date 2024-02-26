package com.epam.gymcrm.service;

import com.epam.gymcrm.entity.Trainer;
import com.epam.gymcrm.entity.Training;
import com.epam.gymcrm.entity.TrainingType;
import com.epam.gymcrm.entity.User;
import com.epam.gymcrm.exception.UserNotFoundException;
import com.epam.gymcrm.exception.UsernameOrPasswordInvalidException;
import com.epam.gymcrm.repository.TrainingRepository;
import com.epam.gymcrm.repository.TrainingTypeRepository;
import com.epam.gymcrm.repository.UserRepository;
import com.epam.gymcrm.utils.HibernateUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainingService {
	private final TrainingRepository trainingRepository;
	private final TrainingTypeRepository trainingTypeRepository;
	private final UserRepository userRepository;
	private final UserService userService;

	@Transactional
	public Long createTraining(Training training) {
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
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	public Training getTrainingById(Long trainingId) {
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
			e.printStackTrace();
		}
		return null;
	}

	public void deleteTraining(Long trainingId) {
		throw new UnsupportedOperationException("Not allowed to delete training");
	}
	public Training updateTraining(Training training) {
		throw new UnsupportedOperationException("Not allowed to update training");
	}
}
