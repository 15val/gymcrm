package com.epam.gymcrm.service;

import com.epam.gymcrm.entity.Trainee;
import com.epam.gymcrm.entity.Trainer;
import com.epam.gymcrm.entity.Training;
import com.epam.gymcrm.entity.User;
import com.epam.gymcrm.exception.UserNotFoundException;
import com.epam.gymcrm.exception.UsernameOrPasswordInvalidException;
import com.epam.gymcrm.repository.TrainerRepository;
import com.epam.gymcrm.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
class TrainerServiceTest {

	@Mock
	private TrainerRepository trainerRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserService userService;

	@InjectMocks
	private TrainerService trainerService;

	@Test
	void testCreateTrainer_Success() {
		// Arrange
		Trainer trainer = createDummyTrainer();
		when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(trainer.getUser1()));

		// Act
		Long trainerId = trainerService.createTrainer(trainer);

		// Assert
		Assertions.assertNotNull(trainerId);
		verify(trainerRepository, times(1)).save(trainer);
	}

	@Test
	void testCreateTrainer_UserNotFound() {
		// Arrange
		Trainer trainer = createDummyTrainer();
		when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

		// Act
		Long trainerId = trainerService.createTrainer(trainer);

		// Assert
		Assertions.assertNull(trainerId);
	}

	@Test
	void testUpdateTrainer_Success() {
		// Arrange
		Trainer trainer = createDummyTrainer();
		when(userService.isUsernameAndPasswordValid(any(Long.class))).thenReturn(true);
		when(trainerRepository.save(any(Trainer.class))).thenReturn(trainer);

		// Act
		Long updatedTrainerId = trainerService.updateTrainer(trainer);

		// Assert
		Assertions.assertNotNull(updatedTrainerId);
		verify(trainerRepository, times(1)).save(trainer);
	}

	@Test
	void testGetTrainerById_Success() throws UserNotFoundException, UsernameOrPasswordInvalidException {
		// Arrange
		Trainer trainer = createDummyTrainer();
		when(trainerRepository.findById(any(Long.class))).thenReturn(Optional.of(trainer));
		when(userService.isUsernameAndPasswordValid(any(Long.class))).thenReturn(true);
		// Act
		Trainer foundTrainer = trainerService.getTrainerById(1L);

		// Assert
		Assertions.assertNotNull(foundTrainer);
		Assertions.assertEquals(trainer.getId(), foundTrainer.getId());
	}

	@Test
	void testGetTrainerById_UserNotFound() {
		// Arrange
		when(trainerRepository.findById(any(Long.class))).thenReturn(Optional.empty());

		// Act & Assert
		Assertions.assertThrows(UserNotFoundException.class, () -> {
			trainerService.getTrainerById(1L);
		});
	}

	private Trainer createDummyTrainer() {
		User user = User.builder()
				.id(1L)
				.firstName("John")
				.lastName("Doe")
				.username("sss")
				.password("password")
				.isActive(true)
				.build();
		Trainee trainee = Trainee.builder()
				.id(1L)
				.dateOfBirth(new Date(System.currentTimeMillis()))
				.address("Dummy Address")
				.user(user)
				.build();
		Training training = Training.builder()
				.id(1L)
				.trainee1(trainee)
				.trainingDuration(25)
				.trainingDate(new Date(System.currentTimeMillis()))
				.trainingName("Training Name")
				.build();
		return Trainer.builder()
				.id(1L)
				.trainingType2(null)
				.user1(user)
				.traineeSet(Set.of(trainee))
				.trainingSet(Set.of(training))
				.build();
	}
}
