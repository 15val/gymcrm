package com.epam.gymcrm.controller;


import com.epam.gymcrm.dto.CreateTrainerDto;
import com.epam.gymcrm.dto.GetTrainerDto;
import com.epam.gymcrm.dto.GetTrainersTrainingListRequestDto;
import com.epam.gymcrm.dto.GetTrainingListDto;
import com.epam.gymcrm.dto.UpdateTrainerDto;
import com.epam.gymcrm.dto.UpdateTrainerResponseDto;
import com.epam.gymcrm.dto.UsernameAndIsActiveDto;
import com.epam.gymcrm.dto.UsernameAndPasswordDto;
import com.epam.gymcrm.dto.UsernameDto;
import com.epam.gymcrm.facade.TrainerFacade;
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
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
class TrainerControllerTest {

	@Mock
	private TrainerFacade trainerFacade;

	@InjectMocks
	private TrainerController trainerController;

	@Test
	void testRegisterTrainer_Success() throws Exception {
		// Arrange
		when(trainerFacade.registerTrainerFacade(any(CreateTrainerDto.class))).thenReturn(new UsernameAndPasswordDto());

		// Act
		ResponseEntity<UsernameAndPasswordDto> responseEntity = trainerController.registerTrainer(new CreateTrainerDto());

		// Assert
		verify(trainerFacade, times(1)).registerTrainerFacade(any(CreateTrainerDto.class));
		assert responseEntity.getStatusCode() == HttpStatus.OK;
	}

	@Test
	void testRegisterTrainer_Exception() throws Exception {
		// Arrange
		when(trainerFacade.registerTrainerFacade(any(CreateTrainerDto.class))).thenThrow(new RuntimeException());

		// Act
		ResponseEntity<UsernameAndPasswordDto> responseEntity = trainerController.registerTrainer(new CreateTrainerDto());

		// Assert
		verify(trainerFacade, times(1)).registerTrainerFacade(any(CreateTrainerDto.class));
		assert responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR;
	}

	@Test
	void testUpdateTrainer_Success() throws Exception {
		// Arrange
		when(trainerFacade.updateTrainerFacade(any(UpdateTrainerDto.class))).thenReturn(new UpdateTrainerResponseDto());

		// Act
		ResponseEntity<UpdateTrainerResponseDto> responseEntity = trainerController.updateTrainer(new UpdateTrainerDto());

		// Assert
		verify(trainerFacade, times(1)).updateTrainerFacade(any(UpdateTrainerDto.class));
		assert responseEntity.getStatusCode() == HttpStatus.OK;
	}

	@Test
	void testUpdateTrainer_Exception() throws Exception {
		// Arrange
		when(trainerFacade.updateTrainerFacade(any(UpdateTrainerDto.class))).thenThrow(new RuntimeException());

		// Act
		ResponseEntity<UpdateTrainerResponseDto> responseEntity = trainerController.updateTrainer(new UpdateTrainerDto());

		// Assert
		verify(trainerFacade, times(1)).updateTrainerFacade(any(UpdateTrainerDto.class));
		assert responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR;
	}

	@Test
	void testGetTrainer_Success() throws Exception {
		// Arrange
		when(trainerFacade.getTrainerFacade(any(UsernameDto.class))).thenReturn(new GetTrainerDto());

		// Act
		ResponseEntity<GetTrainerDto> responseEntity = trainerController.getTrainer(new UsernameDto());

		// Assert
		verify(trainerFacade, times(1)).getTrainerFacade(any(UsernameDto.class));
		assert responseEntity.getStatusCode() == HttpStatus.OK;
	}

	@Test
	void testGetTrainer_Exception() throws Exception {
		// Arrange
		when(trainerFacade.getTrainerFacade(any(UsernameDto.class))).thenThrow(new RuntimeException());

		// Act
		ResponseEntity<GetTrainerDto> responseEntity = trainerController.getTrainer(new UsernameDto());

		// Assert
		verify(trainerFacade, times(1)).getTrainerFacade(any(UsernameDto.class));
		assert responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR;
	}

	@Test
	void testUpdateIsActive_Success() throws Exception {
		// Arrange
		doNothing().when(trainerFacade).updateIsActiveTrainer(any(UsernameAndIsActiveDto.class));

		// Act
		ResponseEntity<HttpStatus> responseEntity = trainerController.updateIsActive(new UsernameAndIsActiveDto());

		// Assert
		verify(trainerFacade, times(1)).updateIsActiveTrainer(any(UsernameAndIsActiveDto.class));
		assert responseEntity.getStatusCode() == HttpStatus.OK;
	}

	@Test
	void testUpdateIsActive_Exception() throws Exception {
		// Arrange
		doThrow(new RuntimeException()).when(trainerFacade).updateIsActiveTrainer(any(UsernameAndIsActiveDto.class));

		// Act
		ResponseEntity<HttpStatus> responseEntity = trainerController.updateIsActive(new UsernameAndIsActiveDto());

		// Assert
		verify(trainerFacade, times(1)).updateIsActiveTrainer(any(UsernameAndIsActiveDto.class));
		assert responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR;
	}

	@Test
	void testGetTrainersTrainingList_Success() throws Exception {
		// Arrange
		when(trainerFacade.getTrainersTrainingListFacade(any(GetTrainersTrainingListRequestDto.class))).thenReturn(new GetTrainingListDto());

		// Act
		ResponseEntity<GetTrainingListDto> responseEntity = trainerController.getTrainersTrainingList(new GetTrainersTrainingListRequestDto());

		// Assert
		verify(trainerFacade, times(1)).getTrainersTrainingListFacade(any(GetTrainersTrainingListRequestDto.class));
		assert responseEntity.getStatusCode() == HttpStatus.OK;
	}

	@Test
	void testGetTrainersTrainingList_Exception() throws Exception {
		// Arrange
		when(trainerFacade.getTrainersTrainingListFacade(any(GetTrainersTrainingListRequestDto.class))).thenThrow(new RuntimeException());

		// Act
		ResponseEntity<GetTrainingListDto> responseEntity = trainerController.getTrainersTrainingList(new GetTrainersTrainingListRequestDto());

		// Assert
		verify(trainerFacade, times(1)).getTrainersTrainingListFacade(any(GetTrainersTrainingListRequestDto.class));
		assert responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR;
	}
}
