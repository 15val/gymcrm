package com.epam.gymcrm.controller;

import com.epam.gymcrm.dto.GetTrainingTypeListDto;
import com.epam.gymcrm.facade.TrainingTypeFacade;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
class TrainingTypeControllerTest {

	@Mock
	private TrainingTypeFacade trainingTypeFacade;

	@InjectMocks
	private TrainingTypeController trainingTypeController;

	@Test
	void testGetTrainingTypeList_Success() throws Exception {
		// Arrange
		when(trainingTypeFacade.getTrainingTypeListFacade()).thenReturn(new GetTrainingTypeListDto());

		// Act
		ResponseEntity<GetTrainingTypeListDto> responseEntity = trainingTypeController.getTrainingTypeList();

		// Assert
		verify(trainingTypeFacade, times(1)).getTrainingTypeListFacade();
		assert responseEntity.getStatusCode() == HttpStatus.OK;
	}

	@Test
	void testGetTrainingTypeList_Exception() throws Exception {
		// Arrange
		when(trainingTypeFacade.getTrainingTypeListFacade()).thenThrow(new RuntimeException());

		// Act
		ResponseEntity<GetTrainingTypeListDto> responseEntity = trainingTypeController.getTrainingTypeList();

		// Assert
		verify(trainingTypeFacade, times(1)).getTrainingTypeListFacade();
		assert responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR;
	}

}
