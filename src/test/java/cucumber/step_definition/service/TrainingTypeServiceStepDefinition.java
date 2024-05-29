package cucumber.step_definition.service;

import com.epam.gymcrm.entity.Trainer;
import com.epam.gymcrm.entity.Training;
import com.epam.gymcrm.entity.TrainingType;
import com.epam.gymcrm.exception.UserNotFoundException;
import com.epam.gymcrm.repository.TrainingTypeRepository;
import com.epam.gymcrm.repository.UserRepository;
import com.epam.gymcrm.service.TrainingTypeService;
import com.epam.gymcrm.service.UserService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class TrainingTypeServiceStepDefinition {

	@Mock
	private TrainingTypeRepository trainingTypeRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserService userService;

	@InjectMocks
	private TrainingTypeService trainingTypeService;

	private TrainingType trainingType;
	private Exception exception;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Given("a valid TrainingType Id for training type service test")
	public void givenValidTrainingTypeId() {
		Long trainingTypeId = 1L;
		trainingType = new TrainingType(trainingTypeId, "Test Training Type", new ArrayList<Training>(), new ArrayList<Trainer>());
		when(trainingTypeRepository.findById(trainingTypeId)).thenReturn(Optional.of(trainingType));
	}

	@Given("an invalid TrainingType Id for training type service test")
	public void givenInvalidTrainingTypeId() {
		Long invalidTrainingTypeId = 999L;
		when(trainingTypeRepository.findById(invalidTrainingTypeId)).thenReturn(Optional.empty());
	}

	@When("the getTrainingTypeById method is called for training type service test")
	public void whenGetTrainingTypeByIdMethodIsCalled() {
		try {
			trainingType = trainingTypeService.getTrainingTypeById(1L);
		} catch (UserNotFoundException e) {
			exception = e;
		}
	}

	@Then("the TrainingType should be retrieved successfully for training type service test")
	public void thenTrainingTypeShouldBeRetrievedSuccessfully() {
		assertNotNull(trainingType);
		assertEquals(trainingType.getId(), Long.valueOf(1L));
		assertEquals(trainingType.getTrainingTypeName(), "Test Training Type");
	}

	@Then("a UserNotFoundException should be thrown for training type service test")
	public void thenUserNotFoundExceptionShouldBeThrown() {
		assertNotNull(exception);
		assertTrue(exception instanceof UserNotFoundException);
	}

}
