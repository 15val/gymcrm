package com.epam.gymcrm.service;

import com.epam.gymcrm.entity.Trainee;
import com.epam.gymcrm.entity.Trainer;
import com.epam.gymcrm.entity.Training;
import com.epam.gymcrm.entity.User;
import com.epam.gymcrm.exception.UserNotFoundException;
import com.epam.gymcrm.exception.UsernameOrPasswordInvalidException;
import com.epam.gymcrm.repository.TraineeRepository;
import com.epam.gymcrm.repository.TrainerRepository;
import com.epam.gymcrm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TraineeService {

	@Autowired
	private final TraineeRepository traineeRepository;
	@Autowired
	private final UserRepository userRepository;
	@Autowired
	private final UserService userService;
	@Autowired
	private final TrainerRepository trainerRepository;


	@Transactional
	public Long createTrainee(Trainee trainee) throws UserNotFoundException {
		try {
			if (userRepository.findById(trainee.getUser().getId()).orElse(null) == null) {
				throw new UserNotFoundException("User was not found");
			}
			traineeRepository.save(trainee);
			return trainee.getId();
		} catch (Exception e) {
			log.error("Error while creating trainee: {}", e.getMessage());
			throw e;
		}
	}

	@Transactional
	public Long updateTrainee(Trainee trainee) {
		try {
			if(userService.isUsernameAndPasswordValid(trainee.getUser().getId())){
				traineeRepository.save(trainee);
				return trainee.getId();
			}
			else {
				throw new UsernameOrPasswordInvalidException("Username or password is invalid");
			}
		} catch (Exception e) {
			log.error("Error while updating trainee: {}", e.getMessage());
		}
		return null;
	}

	@Transactional
	public void deleteTrainee(Long traineeId) {
		try {
			Trainee trainee = traineeRepository.findById(traineeId).orElse(null);
			if (trainee == null) {
				throw new UserNotFoundException("Trainee was not found");
			} else {
				List<Training> trainings = trainee.getTrainingList();
				if (trainings != null) {
					for (Training training : trainings) {
						training.setTrainee1(null);
					}
				}
				traineeRepository.delete(trainee);
			}
		} catch (Exception e) {
			log.error("Error while deleting trainee: {}", e.getMessage());
		}
	}

	@Transactional
	public Trainee getTraineeById(Long traineeId) {
		try {
			Trainee trainee = traineeRepository.findById(traineeId).orElse(null);
			if(trainee != null && userService.isUsernameAndPasswordValid(trainee.getUser().getId())) {
				return trainee;
			}
			else {
				throw new UsernameOrPasswordInvalidException("Username or password is invalid");
			}
		} catch (Exception e) {
			log.error("Error while retrieving trainee: {}", e.getMessage());
		}
		return null;
	}

	@Transactional
	public void updateIsActive(String username, boolean isActive) {
		try {
			User user = getTraineeByUsername(username).getUser();
			if(userService.isUsernameAndPasswordValid(user.getId())) {
				user.setIsActive(isActive);
				userService.updateUser(user);
			}
			else {
				throw new UsernameOrPasswordInvalidException("Username or password is invalid");
			}
		} catch (Exception e) {
			log.error("Error while switching active of trainee: {}", e.getMessage());
		}
	}

	@Transactional
	public void changePassword(Long traineeId, String newPassword) {
		try {
			Trainee trainee = getTraineeById(traineeId);
			if (trainee != null && userService.isUsernameAndPasswordValid(trainee.getUser().getId())) {
				User user = userService.getUserById(trainee.getUser().getId());
				user.setPassword(newPassword);
				userService.updateUser(user);
			} else {
				throw new UsernameOrPasswordInvalidException("Username or password is invalid");
			}
		} catch (Exception e) {
			log.error("Error while changing password of trainee: {}", e.getMessage());
		}
	}

	@Transactional
	public void updateTrainersList(Long traineeId, List<Trainer> trainerList) {
		try {
			Trainee trainee = getTraineeById(traineeId);
			if (userService.isUsernameAndPasswordValid(trainee.getUser().getId())) {
				trainee.setTrainerList(trainerList);
				traineeRepository.save(trainee);
			} else {
				throw new UsernameOrPasswordInvalidException("Username or password is invalid");
			}
		} catch (Exception e) {
			log.error("Error while updating trainers list of trainee: {}", e.getMessage());
		}
	}

	@Transactional
	public Trainee getTraineeByUsername(String username) {
		try {
			Trainee trainee = traineeRepository.findById(userRepository.findByUsername(username).getTrainee().getId()).orElse(null);
			if (trainee != null && userService.isUsernameAndPasswordValid(trainee.getUser().getId())) {
				return trainee;
			} else {
				throw new UsernameOrPasswordInvalidException("Username or password is invalid");
			}
		} catch (Exception e) {
			log.error("Error while retrieving trainee: {}", e.getMessage());
		}
		return null;
	}

	@Transactional
	public void deleteTraineeByUsername(String username) {
		try {
			if (userService.isUsernameAndPasswordValid(getTraineeByUsername(username).getUser().getId())) {
				deleteTrainee(getTraineeByUsername(username).getId());
			} else {
				throw new UsernameOrPasswordInvalidException("Username or password is invalid");
			}
		} catch (Exception e) {
			log.error("Error while deleting trainee: {}", e.getMessage());
		}
	}

	@Transactional
	public List<Training> getTrainingsByTraineeUsernameAndCriteria(String username, Date fromDate, Date toDate, String trainerUsername, String trainingTypeName) throws UserNotFoundException {
		try {
			User user = userRepository.findByUsername(username);
			if (userService.isUsernameAndPasswordValid(user.getId())) {
				List<Training> trainings = new ArrayList<>(user.getTrainee().getTrainingList());
				trainings = trainings.stream()
						.filter(training -> fromDate == null || training.getTrainingDate().after(fromDate))
						.filter(training -> toDate == null || training.getTrainingDate().before(toDate))
						.filter(training -> trainerUsername == null || training.getTrainer1().getUser1().getUsername().equals(trainerUsername))
						.filter(training -> trainingTypeName == null || training.getTrainingType1().getTrainingTypeName().equals(trainingTypeName))
						.collect(Collectors.toList());
				return trainings;
			} else {
				throw new UsernameOrPasswordInvalidException("Username or password is invalid");
			}
		} catch (Exception e) {
			log.error("Error while retrieving trainings by trainee username: {}", e.getMessage());

		}
		throw new UserNotFoundException("Trainings matching criteria not found");
	}

	public List<Trainer> getUnassignedTrainersByTraineeUsername(String traineeUsername) throws UserNotFoundException {
		try {
			User user = userRepository.findByUsername(traineeUsername);
			if (userService.isUsernameAndPasswordValid(user.getId())) {
				List<Trainer> allTrainers = trainerRepository.findAll();
				//trainers who assigned to this trainee
				List<Trainer> assignedTrainers = user.getTrainee().getTrainerList();
				// remove all assigned trainers
				if(assignedTrainers != null) {
					allTrainers.removeAll(assignedTrainers);
				}
				for(Trainer trainer : allTrainers){//remove all inactive trainers
					if(Boolean.FALSE.equals(trainer.getUser1().getIsActive())){
						allTrainers.remove(trainer);
					}
				}
				return new ArrayList<>(allTrainers);
			} else {
				throw new UsernameOrPasswordInvalidException("Username or password is invalid");
			}
		} catch (Exception e) {
			log.error("Error while retrieving unassigned trainers by trainee username: {}", e.getMessage());

		}
		throw new UserNotFoundException("Unassigned trainers not found");

	}


}
