package com.epam.gymcrm.facade;

import com.epam.gymcrm.dto.request.CreateTrainerDto;
import com.epam.gymcrm.dto.response.UsernameAndPasswordResponseDto;
import com.epam.gymcrm.entity.Trainer;
import com.epam.gymcrm.entity.User;
import com.epam.gymcrm.service.TrainerService;
import com.epam.gymcrm.service.TrainingTypeService;
import com.epam.gymcrm.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Component
@Service
@RequiredArgsConstructor
@Slf4j
public class TrainerFacade {

	private final TrainerService trainerService;
	private final UserService userService;
	private final TrainingTypeService trainingTypeService;
	@Transactional
	public UsernameAndPasswordResponseDto registerTrainerFacade(CreateTrainerDto request) {
		try {
			String firstName = request.getFirstName();
			String lastName = request.getLastName();
			Long specialization = request.getSpecialization();
			Long userId = userService.createUser(firstName, lastName);

			User user = userService.getUserById(userId);
			Trainer trainer = Trainer.builder()
					.user1(user)
					.trainingType2(trainingTypeService.getTrainingTypeById(specialization))
					.build();
			trainerService.createTrainer(trainer);
			return new UsernameAndPasswordResponseDto(user.getUsername(), user.getPassword());
		} catch (Exception e) {
			log.error("Facade: Error while creating trainer: {}", e.getMessage());
			throw e;
		}
	}
}