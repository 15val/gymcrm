package com.epam.gymcrm.service;

import com.epam.gymcrm.entity.Trainer;
import com.epam.gymcrm.entity.Training;
import com.epam.gymcrm.entity.User;
import com.epam.gymcrm.exception.UserNotFoundException;
import com.epam.gymcrm.exception.UsernameOrPasswordInvalidException;
import com.epam.gymcrm.repository.TrainerRepository;
import com.epam.gymcrm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainerService {

	@Autowired
	private final TrainerRepository trainerRepository;
	@Autowired
	private final UserRepository userRepository;
	@Autowired
	private final UserService userService;

	@Transactional
	public Long createTrainer(Trainer trainer) {
		try {
			if(userRepository.findById(trainer.getUser1().getId()).orElse(null) == null){
				throw new UserNotFoundException("User was not found");
			}
			trainerRepository.save(trainer);
			return trainer.getId();
		}
		catch (Exception e) {
			log.error("Error while creating trainer: {}", e.getMessage());
		}
		return null;
	}

	@Transactional
	public Long updateTrainer(Trainer trainer) {
		try {
			if(userService.isUsernameAndPasswordValid(trainer.getUser1().getId())) {
				trainerRepository.save(trainer);
				return trainer.getId();
			}
			else {
				throw new UsernameOrPasswordInvalidException("Username or password is invalid");
			}
		} catch (Exception e) {
			log.error("Error while updating trainer: {}", e.getMessage());
		}
		return null;
	}

	@Transactional
	public Trainer getTrainerById(Long trainerId) throws UserNotFoundException, UsernameOrPasswordInvalidException {
		try {
			Trainer trainer = trainerRepository.findById(trainerId).orElse(null);
			if (trainer == null) {
				throw new UserNotFoundException("Trainer was not found");
			}
			else if(userService.isUsernameAndPasswordValid(trainer.getUser1().getId())) {
					return trainer;
			}
			else {
				throw new UsernameOrPasswordInvalidException("Username or password is invalid");
			}
		}
		catch (Exception e) {
			log.error("Error while retrieving trainer: {}", e.getMessage());
			throw e;
		}
	}

	@Transactional
	public void updateIsActive(String username, boolean isActive) {
		try {
			User user = getTrainerByUsername(username).getUser1();
			if(userService.isUsernameAndPasswordValid(user.getId())) {
				user.setIsActive(isActive);
				userService.updateUser(user);
			}
			else {
				throw new UsernameOrPasswordInvalidException("Username or password is invalid");
			}
		}
		catch (Exception e) {
			log.error("Error while switching active of trainer: {}", e.getMessage());
		}
	}

	@Transactional
	public void changePassword(Long trainerId, String newPassword){
		try {
			User user = userService.getUserById(getTrainerById(trainerId).getUser1().getId());
			if(userService.isUsernameAndPasswordValid(user.getId())) {
				user.setPassword(newPassword);
				userService.updateUser(user);
			}
			else {
				throw new UsernameOrPasswordInvalidException("Username or password is invalid");
			}
		}
		catch (Exception e) {
			log.error("Error while changing password of trainer: {}", e.getMessage());
		}
	}

	@Transactional
	public Trainer getTrainerByUsername(String username) {
		try {
			User user = userRepository.findByUsername(username);
			if(userService.isUsernameAndPasswordValid(user.getId())) {
				Trainer trainer = trainerRepository.findById(user.getTrainer().getId()).orElse(null);
				if (trainer == null) {
					throw new UserNotFoundException("Trainer was not found");
				} else {
					return trainer;
				}
			}
			else {
				throw new UsernameOrPasswordInvalidException("Username or password is invalid");
			}
		}
		catch (Exception e) {
			log.error("Error while retrieving trainer: {}", e.getMessage());
		}
		return null;
	}

	public List<Training> getTrainingsByTrainerUsernameAndCriteria(String username, Date fromDate, Date toDate, String traineeUsername) throws UserNotFoundException {
		try {
			User user = userRepository.findByUsername(username);
			if(userService.isUsernameAndPasswordValid(user.getId())) {
				List<Training> trainings = new ArrayList<>(user.getTrainer().getTrainingList());
				trainings = trainings.stream()
						.filter(training -> fromDate == null || training.getTrainingDate().after(fromDate))
						.filter(training -> toDate == null || training.getTrainingDate().before(toDate))
						.filter(training -> traineeUsername == null || training.getTrainee1().getUser().getUsername().equals(traineeUsername))
						.collect(Collectors.toList());
				return trainings;
			}
			else {
				throw new UsernameOrPasswordInvalidException("Username or password is invalid");
			}
		}
		catch (Exception e) {
			log.error("Error while retrieving trainings by trainer username: {}", e.getMessage());
		}
		throw new UserNotFoundException("Trainings matching criteria not found");
	}

}
