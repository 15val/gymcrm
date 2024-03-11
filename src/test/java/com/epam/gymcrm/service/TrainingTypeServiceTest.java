package com.epam.gymcrm.service;

import com.epam.gymcrm.entity.Trainer;
import com.epam.gymcrm.entity.Training;
import com.epam.gymcrm.entity.TrainingType;
import com.epam.gymcrm.exception.UserNotFoundException;
import com.epam.gymcrm.repository.TrainingTypeRepository;
import com.epam.gymcrm.repository.UserRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
class TrainingTypeServiceTest {
	@Mock
	private TrainingTypeRepository trainingTypeRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserService userService;

	@InjectMocks
	private TrainingTypeService trainingTypeService;


	@Test
	void testGetTrainingTypeById_ValidId_Success() throws UserNotFoundException {
		// Arrange
		Long trainingTypeId = 1L;
		TrainingType expectedTrainingType = new TrainingType(trainingTypeId, "Test Training Type", new ArrayList<Training>(), new ArrayList<Trainer>());
		when(trainingTypeRepository.findById(trainingTypeId)).thenReturn(Optional.of(expectedTrainingType));

		// Act
		TrainingType result = trainingTypeService.getTrainingTypeById(trainingTypeId);

		// Assert
		assertNotNull(result);
		assertEquals(result.getId(), trainingTypeId);
		assertEquals(result.getTrainingTypeName(), ("Test Training Type"));
	}

	@Test
	void testGetTrainingTypeById_InvalidId_ReturnsNull() throws UserNotFoundException {
		Long invalidTrainingTypeId = 999L;
		when(trainingTypeRepository.findById(invalidTrainingTypeId)).thenReturn(Optional.empty());
		Assertions.assertThrows(UserNotFoundException.class, () -> {
					trainingTypeService.getTrainingTypeById(invalidTrainingTypeId);
				});
	}

}
