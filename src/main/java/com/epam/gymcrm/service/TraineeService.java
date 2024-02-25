package com.epam.gymcrm.service;

import com.epam.gymcrm.entity.Trainee;
import com.epam.gymcrm.entity.Trainer;
import com.epam.gymcrm.entity.Training;
import com.epam.gymcrm.entity.User;
import com.epam.gymcrm.exception.UserNotFoundException;
import com.epam.gymcrm.repository.TraineeRepository;
import com.epam.gymcrm.repository.TrainerRepository;
import com.epam.gymcrm.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TraineeService {
	private final TraineeRepository traineeRepository;
	private final UserRepository userRepository;
	private final UserService userService;
	private final TrainerRepository trainerRepository;


	@Transactional
	public Long createTrainee(Trainee trainee) {
		try {
			if (userRepository.findById(trainee.getUser().getId()).orElse(null) == null) {
				throw new UserNotFoundException("User was not found");
			}
			traineeRepository.save(trainee);
			return trainee.getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	public Long updateTrainee(Trainee trainee) {
		try {
			Trainee updatedTrainee = Trainee.builder()
					.id(trainee.getId())
					.address(trainee.getAddress())
					.dateOfBirth(trainee.getDateOfBirth())
					.trainerSet(trainee.getTrainerSet())
					.trainingSet(trainee.getTrainingSet())
					.user(trainee.getUser())
					.build();
			traineeRepository.save(updatedTrainee);
			return updatedTrainee.getId();
		} catch (Exception e) {
			e.printStackTrace();
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
				traineeRepository.delete(trainee);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public Trainee getTraineeById(Long traineeId) {
		try {
			Trainee trainee = traineeRepository.findById(traineeId).orElse(null);
			if (trainee == null) {
				throw new UserNotFoundException("Trainee was not found");
			} else {
				return trainee;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	public void switchActive(Long traineeId){
		try {
			User user = userService.getUserById(getTraineeById(traineeId).getUser().getId());//query update user status jpql/native sql
			user.setIsActive(!user.getIsActive());
			userService.updateUser(user);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void changePassword(Long traineeId, String newPassword){
		try {
			User user = userService.getUserById(getTraineeById(traineeId).getUser().getId());
			user.setPassword(newPassword);
			userService.updateUser(user);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void updateTrainersSet(Long traineeId, Set<Trainer> trainerSet){
		try {
			Trainee trainee = getTraineeById(traineeId);
			trainee.setTrainerSet(trainerSet);
			traineeRepository.save(trainee);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public Trainee getTraineeByUsername(String username) {
		try {
			Trainee trainee = traineeRepository.findById(userRepository.findByUsername(username).getTrainee().getId()).orElse(null);
			if(trainee == null){
				throw new UserNotFoundException("Trainee was not found");
			}
			else {
				return trainee;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	public void deleteTraineeByUsername(String username){
		try {
			deleteTrainee(getTraineeByUsername(username).getId());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public Set<Training> getTrainingsByTraineeUsernameAndCriteria(String username, Date fromDate, Date toDate, String trainerUsername, String trainingTypeName) throws UserNotFoundException {
		try {
			User user = userRepository.findByUsername(username);
			if (user == null) {
				throw new UserNotFoundException("Trainee was not found");
			}
			Set<Training> trainings = new HashSet<>(user.getTrainee().getTrainingSet());
			trainings = trainings.stream()
					.filter(training -> fromDate == null || training.getTrainingDate().after(fromDate))
					.filter(training -> toDate == null || training.getTrainingDate().before(toDate))
					.filter(training -> trainerUsername == null || training.getTrainer1().getUser1().getUsername().equals(trainerUsername))
					.filter(training -> trainingTypeName == null || training.getTrainingType1().getTrainingTypeName().equals(trainingTypeName))
					.collect(Collectors.toSet());
			return trainings;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		throw new UserNotFoundException("Trainings matching criteria not found");
	}

	public List<Trainer> getUnassignedTrainersByTraineeUsername(String traineeUsername) throws UserNotFoundException {
		try {
			User user = userRepository.findByUsername(traineeUsername);
			if (user == null) {
				throw new UserNotFoundException("Trainee was not found");
			}
			List<Trainer> allTrainers = trainerRepository.findAll();
			//trainers who assigned to this trainee
			Set<Trainer> assignedTrainers = user.getTrainee().getTrainerSet();
			// remove all assigned trainers
			allTrainers.removeAll(assignedTrainers);

			return new ArrayList<>(allTrainers);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		throw new UserNotFoundException("Unassigned trainers not found");

	}
}
