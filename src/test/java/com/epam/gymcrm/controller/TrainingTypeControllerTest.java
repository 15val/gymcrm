package com.epam.gymcrm.controller;

import com.epam.gymcrm.dto.GetTrainingTypeListDto;
import com.epam.gymcrm.dto.TrainingTypeDto;
import com.epam.gymcrm.entity.TrainingType;
import com.epam.gymcrm.facade.TrainingTypeFacade;
import com.epam.gymcrm.repository.TrainingTypeRepository;
import io.prometheus.client.CollectorRegistry;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureObservability
@TestPropertySource(locations = "classpath:application.properties")
class TrainingTypeControllerTest {

	@Mock
	private TrainingTypeFacade trainingTypeFacade;

	@Mock
	private TrainingTypeRepository trainingTypeRepository;

	@Mock
	private CollectorRegistry collectorRegistry;

	@InjectMocks
	private TrainingTypeController trainingTypeController;

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
