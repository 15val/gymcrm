package com.epam.gymcrm.service;

import com.epam.gymcrm.entity.Trainee;
import com.epam.gymcrm.entity.Trainer;
import com.epam.gymcrm.entity.User;
import com.epam.gymcrm.exception.UserNotFoundException;
import com.epam.gymcrm.repository.TraineeRepository;
import com.epam.gymcrm.repository.TrainerRepository;
import com.epam.gymcrm.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.TestPropertySource;

import java.sql.Date;
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@TestPropertySource(locations = "classpath:application.properties")
class TraineeServiceTest {

	@Mock
	private TraineeRepository traineeRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserService userService;

	@Mock
	private TrainerRepository trainerRepository;

	@InjectMocks
	private TraineeService traineeService;


	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	@Test
	void testCreateTrainee_Success() throws UserNotFoundException {
		// Arrange
		Trainee trainee = createDummyTrainee();
		when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(trainee.getUser()));

		// Act
		Long traineeId = traineeService.createTrainee(trainee);

		// Assert
		Assertions.assertNotNull(traineeId);
		verify(traineeRepository, times(1)).save(trainee);
	}

	@Test
	void testCreateTrainee_UserNotFoundException() throws UserNotFoundException {
		// Arrange
		Trainee trainee = createDummyTrainee();
		when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

		// Act
		Assertions.assertThrows(UserNotFoundException.class, () -> {
			traineeService.createTrainee(trainee);
		});
		verify(traineeRepository, never()).save(any());
	}

	@Test
	void testUpdateTrainee_Success() {
		// Arrange
		Trainee trainee = createDummyTrainee();
		when(userService.isUsernameAndPasswordValid(any(Long.class))).thenReturn(true);
		when(traineeRepository.save(any(Trainee.class))).thenReturn(trainee);

		// Act
		Long updatedTraineeId = traineeService.updateTrainee(trainee);

		// Assert
		Assertions.assertNotNull(updatedTraineeId);
		verify(traineeRepository, times(1)).save(trainee);
	}

	@Test
	void testDeleteTrainee_Success() {
		// Arrange
		Trainee trainee = createDummyTrainee();
		when(traineeRepository.findById(any(Long.class))).thenReturn(Optional.of(trainee));

		// Act
		traineeService.deleteTrainee(trainee.getId());

		// Assert
		verify(traineeRepository, times(1)).delete(trainee);
	}

	@Test
	void testGetTraineeById_Success() {
		// Arrange
		Trainee trainee = createDummyTrainee();
		when(traineeRepository.findById(any(Long.class))).thenReturn(Optional.of(trainee));
		when(userService.isUsernameAndPasswordValid(any(Long.class))).thenReturn(true);

		// Act
		Trainee retrievedTrainee = traineeService.getTraineeById(1L);

		// Assert
		Assertions.assertNotNull(retrievedTrainee);
		Assertions.assertEquals(trainee.getId(), retrievedTrainee.getId());
	}

	@Test
	void testSwitchActive_Success() {
		// Arrange
		Trainee trainee = createDummyTrainee();
		User user = trainee.getUser();
		when(userService.getUserById(any(Long.class))).thenReturn(user);
		when(userService.isUsernameAndPasswordValid(any(Long.class))).thenReturn(true);
		when(traineeRepository.findById(any(Long.class))).thenReturn(Optional.of(trainee));
		// Act
		traineeService.switchActive(user.getId());

		// Assert
		Assertions.assertFalse(user.getIsActive());
		verify(userService, times(1)).updateUser(user);
	}

	@Test
	void testChangePassword_Success() {
		// Arrange
		String newPassword = "newPassword";
		Trainee trainee = createDummyTrainee();
		User user = trainee.getUser();
		when(userService.getUserById(any(Long.class))).thenReturn(user);
		when(userService.isUsernameAndPasswordValid(any(Long.class))).thenReturn(true);
		when(userService.updateUser(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));;
		user.setPassword(newPassword);

		// Act
		traineeService.changePassword(trainee.getId(), newPassword);

		// Assert
		Assertions.assertEquals(newPassword, user.getPassword());
	}

	private Trainee createDummyTrainee() {
		Trainee trainee = new Trainee();
		trainee.setId(1L);
		trainee.setDateOfBirth(new Date(System.currentTimeMillis()));
		trainee.setAddress("Dummy Address");
		trainee.setUser(new User(1L, "John", "Doe", "sss", "sss",  true, new Trainee(), new Trainer()));
		trainee.setTrainerSet(new HashSet<>());
		trainee.setTrainingSet(new HashSet<>());
		return trainee;
	}
}