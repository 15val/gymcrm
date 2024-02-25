package com.epam.gymcrm.service;

import com.epam.gymcrm.entity.Trainee;
import com.epam.gymcrm.entity.Trainer;
import com.epam.gymcrm.entity.Training;
import com.epam.gymcrm.entity.User;
import com.epam.gymcrm.exception.UserNotFoundException;
import com.epam.gymcrm.repository.TrainerRepository;
import com.epam.gymcrm.repository.UserRepository;
import com.epam.gymcrm.utils.HibernateUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainerService {
	private final TrainerRepository trainerRepository;
	private final UserRepository userRepository;
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
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	public Long updateTrainer(Trainer trainer) {
		try {
			Trainer updatedTrainer = Trainer.builder()
					.id(trainer.getId())
					.user1(trainer.getUser1())
					.trainingSet(trainer.getTrainingSet())
					.traineeSet(trainer.getTraineeSet())
					.trainingType2(trainer.getTrainingType2())
					.build();
			trainerRepository.save(updatedTrainer);
			return updatedTrainer.getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	public Trainer getTrainerById(Long trainerId) {
		try {
			Trainer trainer = trainerRepository.findById(trainerId).orElse(null);
			if(trainer == null){
				throw new UserNotFoundException("Trainer was not found");
			}
			else {
				return trainer;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void deleteTrainer(Long trainerId) {
		throw new UnsupportedOperationException("Not allowed to delete trainer");
	}

	@Transactional
	public void switchActive(Long trainerId) {
		try {
			User user = userService.getUserById(getTrainerById(trainerId).getUser1().getId());
			user.setIsActive(!user.getIsActive());
			userService.updateUser(user);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void changePassword(Long trainerId, String newPassword){
		try {
			User user = userService.getUserById(getTrainerById(trainerId).getUser1().getId());
			user.setPassword(newPassword);
			userService.updateUser(user);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public Trainer getTrainerByUsername(String username) {
		try {
			User user = userRepository.findByUsername(username);
			if(user == null){
				throw new UserNotFoundException("User was not found");
			}
			Trainer trainer = trainerRepository.findById(user.getTrainer().getId()).orElse(null);
			if(trainer == null){
				throw new UserNotFoundException("Trainer was not found");
			}
			else {
				return trainer;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Set<Training> getTrainingsByTrainerUsernameAndCriteria(String username, Date fromDate, Date toDate, String traineeUsername) throws UserNotFoundException {
		try {
			User user = userRepository.findByUsername(username);
			if (user == null) {
				throw new UserNotFoundException("Trainer was not found");
			}
			Set<Training> trainings = new HashSet<>(user.getTrainer().getTrainingSet());
			trainings = trainings.stream()
					.filter(training -> fromDate == null || training.getTrainingDate().after(fromDate))
					.filter(training -> toDate == null || training.getTrainingDate().before(toDate))
					.filter(training -> traineeUsername == null || training.getTrainee1().getUser().getUsername().equals(traineeUsername))
					.collect(Collectors.toSet());
			return trainings;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		throw new UserNotFoundException("Trainings matching criteria not found");
	}

}
