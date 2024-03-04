package com.epam.gymcrm.service;

import com.epam.gymcrm.entity.Trainee;
import com.epam.gymcrm.entity.Trainer;
import com.epam.gymcrm.entity.Training;
import com.epam.gymcrm.entity.TrainingType;
import com.epam.gymcrm.entity.User;
import com.epam.gymcrm.exception.UserNotFoundException;
import com.epam.gymcrm.repository.TrainingRepository;
import com.epam.gymcrm.repository.TrainingTypeRepository;
import com.epam.gymcrm.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.sql.Date;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
class TrainingServiceTest {

	@Mock
	private TrainingRepository trainingRepository;

	@Mock
	private TrainingTypeRepository trainingTypeRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserService userService;

	@InjectMocks
	private TrainingService trainingService;

	private Trainee trainee;
	private Trainer trainer;
	private TrainingType trainingType;

	@BeforeEach
	void setUp() {
		User userTrainee = User.builder()
				.id(101L)
				.firstName("John")
				.lastName("Doe")
				.username("sss")
				.password("password")
				.isActive(true)
				.build();
		User userTrainer = User.builder()
				.id(201L)
				.firstName("John")
				.lastName("Doe")
				.username("sss")
				.password("password")
				.isActive(true)
				.build();
		trainee = Trainee.builder()
				.id(1L)
				.dateOfBirth(new Date(System.currentTimeMillis()))
				.address("Dummy Address")
				.user(userTrainee)
				.build();

		Training training = Training.builder()
				.id(1L)
				.trainee1(trainee)
				.trainingDuration(25)
				.trainingDate(new Date(System.currentTimeMillis()))
				.trainingName("Training Name")
				.build();

		trainingType = TrainingType.builder()
				.id(1L)
				.trainingTypeName("Training Type")
				.build();

		trainer = Trainer.builder()
				.id(2L)
				.trainingType2(trainingType)
				.user1(userTrainer)
				.traineeSet(Set.of(trainee))
				.trainingSet(Set.of(training))
				.build();


	}

	@Test
	void testCreateTraining_TraineeNotFound() {
		// Arrange
		Training training = Training.builder()
				.trainee1(trainee)
				.trainer1(trainer)
				.trainingName("Test Training")
				.trainingType1(trainingType)
				.trainingDate(new Date(System.currentTimeMillis()))
				.trainingDuration(60)
				.build();

		when(userService.isUsernameAndPasswordValid(any(Long.class))).thenReturn(true);
		when(userRepository.findById(101L)).thenReturn(Optional.empty());

		// Act & Assert
		Assertions.assertThrows(UserNotFoundException.class, () -> trainingService.createTraining(training));
	}

	@Test
	void testCreateTraining_TrainerNotFound() {
		// Arrange
		Training training = Training.builder()
				.trainee1(trainee)
				.trainer1(trainer)
				.trainingName("Test Training")
				.trainingType1(trainingType)
				.trainingDate(new Date(System.currentTimeMillis()))
				.trainingDuration(60)
				.build();

		when(userService.isUsernameAndPasswordValid(any(Long.class))).thenReturn(true);
		when(userRepository.findById(101L)).thenReturn(Optional.of(trainee.getUser()));
		when(userRepository.findById(201L)).thenReturn(Optional.empty());

		// Act & Assert
		Assertions.assertThrows(UserNotFoundException.class, () -> trainingService.createTraining(training));
	}

	@Test
	void testCreateTraining_TrainingTypeNotFound() {
		// Arrange
		Training training = Training.builder()
				.trainee1(trainee)
				.trainer1(trainer)
				.trainingName("Test Training")
				.trainingType1(trainingType)
				.trainingDate(new Date(System.currentTimeMillis()))
				.trainingDuration(60)
				.build();

		when(userService.isUsernameAndPasswordValid(any(Long.class))).thenReturn(true);
		when(userRepository.findById(101L)).thenReturn(Optional.of(trainee.getUser()));
		when(userRepository.findById(201L)).thenReturn(Optional.of(trainer.getUser1()));
		when(trainingTypeRepository.findById(1L)).thenReturn(Optional.empty());

		// Act & Assert
		Assertions.assertThrows(UserNotFoundException.class, () -> trainingService.createTraining(training));
	}

	@Test
	void testGetTrainingById_Success() throws UserNotFoundException {
		// Arrange
		Training training = Training.builder().id(1L).build();
		when(trainingRepository.findById(1L)).thenReturn(Optional.of(training));

		// Act
		Training foundTraining = trainingService.getTrainingById(1L);

		// Assert
		Assertions.assertNotNull(foundTraining);
		Assertions.assertEquals(training.getId(), foundTraining.getId());
	}

	@Test
	void testGetTrainingById_NotFound() {
		// Arrange
		when(trainingRepository.findById(1L)).thenReturn(Optional.empty());

		// Act & Assert
		Assertions.assertThrows(UserNotFoundException.class, () -> trainingService.getTrainingById(1L));
	}
}