package cucumber.step_definition.controller;

import com.epam.gymcrm.controller.TrainingTypeController;
import com.epam.gymcrm.facade.TrainingTypeFacade;
import com.epam.gymcrm.repository.TrainingTypeRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.prometheus.client.CollectorRegistry;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;

public class TrainingTypeControllerStepDefinition {

	@Mock
	private TrainingTypeFacade trainingTypeFacade;

	@Mock
	private TrainingTypeRepository trainingTypeRepository;

	@Mock
	private CollectorRegistry collectorRegistry;

	@InjectMocks
	private TrainingTypeController trainingTypeController;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	private ResponseEntity<?> responseEntity;

	@Given("the TrainingTypeFacade throws exception for training type")
	public void givenTrainingTypeFacadeThrowsException() {
		when(trainingTypeFacade.getTrainingTypeList()).thenThrow(new RuntimeException());
	}

	@When("the getTrainingTypeList method is called for training type")
	public void whenGetTrainingTypeListMethodIsCalled() {
		responseEntity = trainingTypeController.getTrainingTypeList();
	}

	@Then("the response status should be INTERNAL_SERVER_ERROR for training type")
	public void thenResponseStatusShouldBeInternalServerError() {
		assert responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR;
	}
}


