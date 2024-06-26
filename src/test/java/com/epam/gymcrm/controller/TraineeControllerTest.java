package com.epam.gymcrm.controller;

import com.epam.gymcrm.dto.CreateTraineeDto;
import com.epam.gymcrm.dto.GetTraineeDto;
import com.epam.gymcrm.dto.UpdateTraineeDto;
import com.epam.gymcrm.dto.UpdateTraineeResponseDto;
import com.epam.gymcrm.dto.UsernameAndPasswordDto;
import com.epam.gymcrm.dto.UsernameDto;
import com.epam.gymcrm.facade.TraineeFacade;
import io.cucumber.java.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureObservability
@TestPropertySource(locations = "classpath:application.properties")
 class TraineeControllerTest {

	@Mock
	private TraineeFacade traineeFacade;

	@InjectMocks
	private TraineeController traineeController;

	@Test
	void testRegisterTrainee_Success() throws Exception {
		// Arrange
		when(traineeFacade.registerTrainee(any(CreateTraineeDto.class))).thenReturn(new UsernameAndPasswordDto());

		// Act
		ResponseEntity<UsernameAndPasswordDto> responseEntity = traineeController.registerTrainee(new CreateTraineeDto());

		// Assert
		verify(traineeFacade, times(1)).registerTrainee(any(CreateTraineeDto.class));
		assert responseEntity.getStatusCode() == HttpStatus.OK;
	}

	@Test
	void testRegisterTrainee_Exception() throws Exception {
		// Arrange
		when(traineeFacade.registerTrainee(any(CreateTraineeDto.class))).thenThrow(new RuntimeException());

		// Act
		ResponseEntity<UsernameAndPasswordDto> responseEntity = traineeController.registerTrainee(new CreateTraineeDto());

		// Assert
		verify(traineeFacade, times(1)).registerTrainee(any(CreateTraineeDto.class));
		assert responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR;
	}

	@Test
	void testUpdateTrainee_Exception() throws Exception {
		// Arrange
		when(traineeFacade.updateTrainee(any(UpdateTraineeDto.class))).thenThrow(new RuntimeException());

		// Act
		ResponseEntity<UpdateTraineeResponseDto> responseEntity = traineeController.updateTrainee(new UpdateTraineeDto());

		// Assert
		verify(traineeFacade, times(1)).updateTrainee(any(UpdateTraineeDto.class));
		assert responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR;
	}

	@Test
	void testGetTrainee_Success() throws Exception {
		// Arrange
		when(traineeFacade.getTrainee(any(UsernameDto.class))).thenReturn(new GetTraineeDto());

		// Act
		ResponseEntity<GetTraineeDto> responseEntity = traineeController.getTrainee(new UsernameDto());

		// Assert
		verify(traineeFacade, times(1)).getTrainee(any(UsernameDto.class));
		assert responseEntity.getStatusCode() == HttpStatus.OK;
	}

	@Test
	void testGetTrainee_Exception() throws Exception {
		// Arrange
		when(traineeFacade.getTrainee(any(UsernameDto.class))).thenThrow(new RuntimeException());

		// Act
		ResponseEntity<GetTraineeDto> responseEntity = traineeController.getTrainee(new UsernameDto());

		// Assert
		verify(traineeFacade, times(1)).getTrainee(any(UsernameDto.class));
		assert responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR;
	}

}


