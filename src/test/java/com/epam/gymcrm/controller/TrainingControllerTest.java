package com.epam.gymcrm.controller;

import com.epam.gymcrm.dto.AddTrainingDto;
import com.epam.gymcrm.facade.TrainingFacade;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
class TrainingControllerTest {

	@Mock
	private TrainingFacade trainingFacade;

	@InjectMocks
	private TrainingController trainingController;

	@Test
	void testAddTraining_Success() throws Exception {
		// Arrange
		doNothing().when(trainingFacade).addTrainingFacade(any(AddTrainingDto.class));

		// Act
		ResponseEntity<HttpStatus> responseEntity = trainingController.addTraining(new AddTrainingDto());

		// Assert
		verify(trainingFacade, times(1)).addTrainingFacade(any(AddTrainingDto.class));
		assert responseEntity.getStatusCode() == HttpStatus.OK;
	}

	@Test
	void testAddTraining_Exception() throws Exception {
		// Arrange
		doThrow(new RuntimeException()).when(trainingFacade).addTrainingFacade(any(AddTrainingDto.class));

		// Act
		ResponseEntity<HttpStatus> responseEntity = trainingController.addTraining(new AddTrainingDto());

		// Assert
		verify(trainingFacade, times(1)).addTrainingFacade(any(AddTrainingDto.class));
		assert responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR;
	}

}
