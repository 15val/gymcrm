package com.epam.gymcrm.facade;

import com.epam.gymcrm.dto.request.CreateTrainerDto;
import com.epam.gymcrm.dto.request.UpdateTraineeDto;
import com.epam.gymcrm.dto.request.UpdateTrainerDto;
import com.epam.gymcrm.dto.response.TraineeDto;
import com.epam.gymcrm.dto.response.UpdateTraineeResponseDto;
import com.epam.gymcrm.dto.response.UpdateTrainerResponseDto;
import com.epam.gymcrm.dto.response.UsernameAndPasswordResponseDto;
import com.epam.gymcrm.entity.Trainee;
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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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

	@Transactional
	public UpdateTrainerResponseDto updateTrainerFacade(UpdateTrainerDto request){
		try{
			String username = request.getUsername();
			String firstName = request.getFirstName();
			String lastName = request.getLastName();
			boolean isActive = Boolean.parseBoolean(request.getIsActive());
			//Long specialization = Long.valueOf(request.getSpecialization());   READ ONLY???
			Trainer trainer = trainerService.getTrainerByUsername(username);
			User user = trainer.getUser1();
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setIsActive(isActive);
			trainer.setUser1(user);
			userService.updateUser(user);
			trainerService.updateTrainer(trainer);

			List<Trainee> traineeList = trainer.getTraineeList();
			List<TraineeDto> traineeDtoList = new ArrayList<>();
			if(traineeList != null){
				for (Trainee trainee : traineeList){
					TraineeDto traineeDto = new TraineeDto();
					traineeDto.setTraineeUsername(trainee.getUser().getUsername());
					traineeDto.setTraineeFirstName(trainee.getUser().getFirstName());
					traineeDto.setTraineeLastName(trainee.getUser().getLastName());
					traineeDtoList.add(traineeDto);
				}
			}
			return new UpdateTrainerResponseDto(user.getUsername(), user.getFirstName(),
					user.getLastName(), String.valueOf(trainer.getTrainingType2().getId()),
					String.valueOf(user.getIsActive()), traineeDtoList);
		}
		catch (Exception e) {
			log.error("Facade: Error while updating trainer: {}", e.getMessage());
			throw e;
		}
	}
}