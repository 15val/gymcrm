package com.epam.gymcrm.facade;

import com.epam.gymcrm.dto.CreateTraineeDto;
import com.epam.gymcrm.dto.GetTraineeDto;
import com.epam.gymcrm.dto.GetTraineesTrainingListRequestDto;
import com.epam.gymcrm.dto.GetTrainerListDto;
import com.epam.gymcrm.dto.GetTrainingListDto;
import com.epam.gymcrm.dto.TrainingDto;
import com.epam.gymcrm.dto.UpdateTraineeDto;
import com.epam.gymcrm.dto.UpdateTraineesTrainerListDto;
import com.epam.gymcrm.dto.UsernameAndIsActiveDto;
import com.epam.gymcrm.dto.UsernameDto;
import com.epam.gymcrm.dto.TrainerDto;
import com.epam.gymcrm.dto.UpdateTraineeResponseDto;
import com.epam.gymcrm.dto.UpdateTraineesTrainerListResponseDto;
import com.epam.gymcrm.dto.UsernameAndPasswordDto;
import com.epam.gymcrm.entity.Trainee;
import com.epam.gymcrm.entity.Trainer;
import com.epam.gymcrm.entity.Training;
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
import java.util.Date;
import java.util.List;

@Component
@Service
@RequiredArgsConstructor
@Slf4j
public class TraineeFacade {

	private final TraineeService traineeService;
	private final UserService userService;
	private final TrainerService trainerService;

	@Transactional
	public UsernameAndPasswordDto registerTraineeFacade(CreateTraineeDto request) throws UserNotFoundException, ParseException {
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
			return new UsernameAndPasswordDto(user.getUsername(), user.getPassword());
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

	@Transactional
	public GetTraineeDto getTraineeFacade(UsernameDto request) {
		try {
			String username = request.getUsername();
			Trainee trainee = traineeService.getTraineeByUsername(username);
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
			return new GetTraineeDto(trainee.getUser().getFirstName(),
					trainee.getUser().getLastName(), String.valueOf(trainee.getDateOfBirth()), trainee.getAddress(),
					String.valueOf(trainee.getUser().getIsActive()), trainerDtoList);
		} catch (Exception e) {
			log.error("Facade: Error while retrieving trainee: {}", e.getMessage());
			throw e;
		}
	}

	@Transactional
	public void deleteTraineeFacade(UsernameDto request) {
		try {
			String username = request.getUsername();
			traineeService.deleteTraineeByUsername(username);
		} catch (Exception e) {
			log.error("Facade: Error while deleting trainee: {}", e.getMessage());
			throw e;
		}
	}

	@Transactional
	public void updateIsActiveTrainee(UsernameAndIsActiveDto request) {
		try {
			String username = request.getUsername();
			boolean isActive = Boolean.parseBoolean(request.getIsActive());
			traineeService.updateIsActive(username, isActive);
		} catch (Exception e) {
			log.error("Facade: Error while updating isActive trainee: {}", e.getMessage());
			throw e;
		}
	}

	@Transactional
	public GetTrainerListDto getUnassignedTrainersFacade(UsernameDto request) throws UserNotFoundException {
		try {
			String username = request.getUsername();
			List<Trainer> trainerList = traineeService.getUnassignedTrainersByTraineeUsername(username);
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
			return new GetTrainerListDto(trainerDtoList);
		}
		catch (Exception e) {
			log.error("Facade: Error while retrieving unassigned trainers: {}", e.getMessage());
			throw e;
		}
	}

	@Transactional
	public GetTrainingListDto getTraineesTrainingListFacade(GetTraineesTrainingListRequestDto request) throws UserNotFoundException, ParseException {
		try {
			String traineeUsername = request.getTraineeUsername();
			String trainingTypeName = request.getTrainingTypeName();
			String trainerUsername = request.getTrainerUsername();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date periodFrom = null;
			if(request.getPeriodFrom() != null){
				periodFrom = dateFormat.parse(request.getPeriodFrom());
			}
			Date periodTo = null;
			if(request.getPeriodTo() != null){
				periodTo = dateFormat.parse(request.getPeriodTo());
			}
			List<Training> trainingList = traineeService.getTrainingsByTraineeUsernameAndCriteria(
					traineeUsername, periodFrom, periodTo, trainerUsername, trainingTypeName);
			List<TrainingDto> trainingDtoList = new ArrayList<>();
			if(trainingList != null){
				for(Training training : trainingList){
					TrainingDto trainingDto = TrainingDto.builder()
							.trainingName(training.getTrainingName())
							.trainingDate(String.valueOf(training.getTrainingDate()))
							.trainingType(training.getTrainingType1().getTrainingTypeName())
							.trainingDuration(String.valueOf(training.getTrainingDuration()))
							.username(training.getTrainer1().getUser1().getUsername())
							.build();
					trainingDtoList.add(trainingDto);
				}
			}
			return new GetTrainingListDto(trainingDtoList);
 		}
		catch (Exception e) {
			log.error("Facade: Error while retrieving trainee's training list: {}", e.getMessage());
			throw e;
		}
	}
}
