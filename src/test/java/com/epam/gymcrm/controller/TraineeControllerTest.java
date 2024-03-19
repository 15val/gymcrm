package com.epam.gymcrm.controller;

import com.epam.gymcrm.dto.CreateTraineeDto;
import com.epam.gymcrm.dto.GetTraineeDto;
import com.epam.gymcrm.dto.UpdateTraineeDto;
import com.epam.gymcrm.dto.UpdateTraineeResponseDto;
import com.epam.gymcrm.dto.UsernameAndPasswordDto;
import com.epam.gymcrm.dto.UsernameDto;
import com.epam.gymcrm.facade.TraineeFacade;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
		when(traineeFacade.registerTraineeFacade(any(CreateTraineeDto.class))).thenReturn(new UsernameAndPasswordDto());

		// Act
		ResponseEntity<UsernameAndPasswordDto> responseEntity = traineeController.registerTrainee(new CreateTraineeDto());

		// Assert
		verify(traineeFacade, times(1)).registerTraineeFacade(any(CreateTraineeDto.class));
		assert responseEntity.getStatusCode() == HttpStatus.OK;
	}

	@Test
	void testRegisterTrainee_Exception() throws Exception {
		// Arrange
		when(traineeFacade.registerTraineeFacade(any(CreateTraineeDto.class))).thenThrow(new RuntimeException());

		// Act
		ResponseEntity<UsernameAndPasswordDto> responseEntity = traineeController.registerTrainee(new CreateTraineeDto());

		// Assert
		verify(traineeFacade, times(1)).registerTraineeFacade(any(CreateTraineeDto.class));
		assert responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR;
	}

	@Test
	void testUpdateTrainee_Success() throws Exception {
		// Arrange
		when(traineeFacade.updateTraineeFacade(any(UpdateTraineeDto.class))).thenReturn(new UpdateTraineeResponseDto());

		// Act
		ResponseEntity<UpdateTraineeResponseDto> responseEntity = traineeController.updateTrainee(new UpdateTraineeDto());

		// Assert
		verify(traineeFacade, times(1)).updateTraineeFacade(any(UpdateTraineeDto.class));
		assert responseEntity.getStatusCode() == HttpStatus.OK;
	}

	@Test
	void testUpdateTrainee_Exception() throws Exception {
		// Arrange
		when(traineeFacade.updateTraineeFacade(any(UpdateTraineeDto.class))).thenThrow(new RuntimeException());

		// Act
		ResponseEntity<UpdateTraineeResponseDto> responseEntity = traineeController.updateTrainee(new UpdateTraineeDto());

		// Assert
		verify(traineeFacade, times(1)).updateTraineeFacade(any(UpdateTraineeDto.class));
		assert responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR;
	}

	@Test
	void testGetTrainee_Success() throws Exception {
		// Arrange
		when(traineeFacade.getTraineeFacade(any(UsernameDto.class))).thenReturn(new GetTraineeDto());

		// Act
		ResponseEntity<GetTraineeDto> responseEntity = traineeController.getTrainee(new UsernameDto());

		// Assert
		verify(traineeFacade, times(1)).getTraineeFacade(any(UsernameDto.class));
		assert responseEntity.getStatusCode() == HttpStatus.OK;
	}

	@Test
	void testGetTrainee_Exception() throws Exception {
		// Arrange
		when(traineeFacade.getTraineeFacade(any(UsernameDto.class))).thenThrow(new RuntimeException());

		// Act
		ResponseEntity<GetTraineeDto> responseEntity = traineeController.getTrainee(new UsernameDto());

		// Assert
		verify(traineeFacade, times(1)).getTraineeFacade(any(UsernameDto.class));
		assert responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR;
	}

	@Test
	void testDeleteTrainee_Success() throws Exception {
		// Arrange
		doNothing().when(traineeFacade).deleteTraineeFacade(any(UsernameDto.class));

		// Act
		ResponseEntity<HttpStatus> responseEntity = traineeController.deleteTrainee(new UsernameDto());

		// Assert
		verify(traineeFacade, times(1)).deleteTraineeFacade(any(UsernameDto.class));
		assert responseEntity.getStatusCode() == HttpStatus.OK;
	}

	@Test
	void testDeleteTrainee_Exception() throws Exception {
		// Arrange
		doThrow(new RuntimeException()).when(traineeFacade).deleteTraineeFacade(any(UsernameDto.class));

		// Act
		ResponseEntity<HttpStatus> responseEntity = traineeController.deleteTrainee(new UsernameDto());

		// Assert
		verify(traineeFacade, times(1)).deleteTraineeFacade(any(UsernameDto.class));
		assert responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR;
	}
}


