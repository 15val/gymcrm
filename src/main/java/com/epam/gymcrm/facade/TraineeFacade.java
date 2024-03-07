package com.epam.gymcrm.facade;

import com.epam.gymcrm.dto.request.CreateTraineeDto;
import com.epam.gymcrm.dto.request.UpdateTraineeDto;
import com.epam.gymcrm.dto.request.UpdateTraineesTrainerListDto;
import com.epam.gymcrm.dto.request.UsernameDto;
import com.epam.gymcrm.dto.response.TrainerDto;
import com.epam.gymcrm.dto.response.UpdateTraineeResponseDto;
import com.epam.gymcrm.dto.response.UpdateTraineesTrainerListResponseDto;
import com.epam.gymcrm.dto.response.UsernameAndPasswordResponseDto;
import com.epam.gymcrm.entity.Trainee;
import com.epam.gymcrm.entity.Trainer;
import com.epam.gymcrm.entity.User;
import com.epam.gymcrm.exception.UserNotFoundException;
import com.epam.gymcrm.service.TraineeService;
import com.epam.gymcrm.service.TrainerService;
import com.epam.gymcrm.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@Service
@RequiredArgsConstructor
@Slf4j
public class TraineeFacade {

	private final TraineeService traineeService;
	private final UserService userService;
	private final TrainerService trainerService;

	@Transactional
	public UsernameAndPasswordResponseDto registerTraineeFacade(CreateTraineeDto request) throws UserNotFoundException, ParseException {
		try {
			String firstName = request.getFirstName();
			String lastName = request.getLastName();
			Long userId = userService.createUser(firstName, lastName);
			User user = userService.getUserById(userId);
			Trainee trainee = Trainee.builder()
					.user(user)
					.address(request.getAddress())
					.build();

			if (request.getDateOfBirth() != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				trainee.setDateOfBirth(dateFormat.parse(request.getDateOfBirth()));
			}
			traineeService.createTrainee(trainee);
			return new UsernameAndPasswordResponseDto(user.getUsername(), user.getPassword());
		} catch (Exception e) {
			log.error("Facade: Error while creating trainee: {}", e.getMessage());
			throw e;
		}
	}

	@Transactional
	public UpdateTraineeResponseDto updateTraineeFacade(UpdateTraineeDto request) throws ParseException {
		try {
			String username = request.getUsername();
			String firstName = request.getFirstName();
			String lastName = request.getLastName();
			String address = request.getAddress();
			String dateOfBirth = request.getDateOfBirth();
			boolean isActive = Boolean.parseBoolean(request.getIsActive());
			Trainee trainee = traineeService.getTraineeByUsername(username);
			User user = trainee.getUser();
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setIsActive(isActive);
			trainee.setUser(user);
			trainee.setAddress(address);
			if (dateOfBirth != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				trainee.setDateOfBirth(dateFormat.parse(dateOfBirth));
			} else {
				trainee.setDateOfBirth(null);
			}
			userService.updateUser(user);
			traineeService.updateTrainee(trainee);

			List<Trainer> trainerList = trainee.getTrainerList();
			List<TrainerDto> trainerDtoList = new ArrayList<>();
			if (trainerList != null) {
				for (Trainer trainer : trainerList) {
					TrainerDto trainerDto = TrainerDto.builder()
							.trainerUsername(trainer.getUser1().getUsername())
							.trainerFirstName(trainer.getUser1().getFirstName())
							.trainerLastName(trainer.getUser1().getLastName())
							.specialization(String.valueOf(trainer.getTrainingType2().getId()))
							.build();
					trainerDtoList.add(trainerDto);
				}
			}
			return new UpdateTraineeResponseDto(user.getUsername(), user.getFirstName(),
					user.getLastName(), String.valueOf(trainee.getDateOfBirth()), trainee.getAddress(),
					String.valueOf(user.getIsActive()), trainerDtoList);
		} catch (Exception e) {
			log.error("Facade: Error while updating trainee: {}", e.getMessage());
			throw e;
		}
	}

	@Transactional
	public UpdateTraineesTrainerListResponseDto updateTraineesTrainerListFacade(UpdateTraineesTrainerListDto request) {
		try {
			String traineeUsername = request.getTraineeUsername();
			Trainee trainee = traineeService.getTraineeByUsername(traineeUsername);
			List<UsernameDto> trainerUsernameList = request.getTrainerList();
			List<Trainer> trainersList = new ArrayList<>();
			for (UsernameDto usernameDto : trainerUsernameList) {
				String trainerUsername = usernameDto.getUsername();
				Trainer trainer = trainerService.getTrainerByUsername(trainerUsername);
				if (trainer != null) {
					trainersList.add(trainer);
				} else {
					log.warn("Trainer with username " + usernameDto.getUsername() + " not found");
				}
			}
			traineeService.updateTrainersList(trainee.getId(), trainersList);

			List<TrainerDto> trainerList = new ArrayList<>();
			for (Trainer trainer : trainersList) {
				TrainerDto trainerDto = TrainerDto.builder()
						.trainerUsername(trainer.getUser1().getUsername())
						.trainerFirstName(trainer.getUser1().getFirstName())
						.trainerLastName(trainer.getUser1().getLastName())
						.specialization(String.valueOf(trainer.getTrainingType2().getId()))
						.build();
				trainerList.add(trainerDto);
			}
			return new UpdateTraineesTrainerListResponseDto(trainerList);
		} catch (Exception e) {
			log.error("Facade: Error while updating trainee's trainer list: {}", e.getMessage());
			throw e;
		}
	}

}
