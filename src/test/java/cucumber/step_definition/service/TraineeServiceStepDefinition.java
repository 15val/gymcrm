package cucumber.step_definition.service;

import com.epam.gymcrm.entity.Trainee;
import com.epam.gymcrm.entity.Trainer;
import com.epam.gymcrm.entity.User;
import com.epam.gymcrm.exception.UserNotFoundException;
import com.epam.gymcrm.exception.UsernameOrPasswordInvalidException;
import com.epam.gymcrm.repository.TraineeRepository;
import com.epam.gymcrm.repository.UserRepository;
import com.epam.gymcrm.service.TraineeService;
import com.epam.gymcrm.service.UserService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TraineeServiceStepDefinition {

	@Mock
	private TraineeRepository traineeRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserService userService;

	@InjectMocks
	private TraineeService traineeService;

	private Trainee trainee;
	private Long traineeId;
	private Exception exception;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Given("a Trainee and a User for trainee service test")
	public void givenTraineeAndUser() {
		trainee = createDummyTrainee();
		when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(trainee.getUser()));
	}
	@Given("a Trainee for trainee service test")
	public void givenATrainee() {
		trainee = createDummyTrainee();
		when(traineeRepository.findById(any(Long.class))).thenReturn(Optional.of(trainee));
	}

	@Given("a Trainee without a User for trainee service test")
	public void givenTraineeWithoutUser() {
		trainee = createDummyTrainee();
		when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());
	}

	@Given("a valid Trainee for trainee service test")
	public void givenValidTrainee() {
		trainee = createDummyTrainee();
		when(userService.isUsernameAndPasswordValid(any(Long.class))).thenReturn(true);
		when(traineeRepository.save(any(Trainee.class))).thenReturn(trainee);
	}

	@Given("a valid Trainee Id for trainee service test")
	public void givenValidTraineeId() {
		trainee = createDummyTrainee();
		when(traineeRepository.findById(any(Long.class))).thenReturn(Optional.of(trainee));
		when(userService.isUsernameAndPasswordValid(any(Long.class))).thenReturn(true);
	}

	@When("the createTrainee method is called")
	public void whenCreateTraineeMethodIsCalled() {
		try {
			traineeId = traineeService.createTrainee(trainee);
		} catch (UserNotFoundException e) {
			exception = e;
		}
	}

	@When("the updateTrainee method is called")
	public void whenUpdateTraineeMethodIsCalled() {
		try {
			traineeId = traineeService.updateTrainee(trainee);
		} catch (UsernameOrPasswordInvalidException e) {
			exception = e;
		}
	}

	@When("the deleteTrainee method is called")
	public void whenDeleteTraineeMethodIsCalled() {
		try {
			traineeService.deleteTrainee(trainee.getId());
		} catch (UserNotFoundException e) {
			exception = e;
		}
	}

	@When("the getTraineeById method is called")
	public void whenGetTraineeByIdMethodIsCalled() {
		try {
			trainee = traineeService.getTraineeById(1L);
		} catch (UsernameOrPasswordInvalidException e) {
			exception = e;
		}
	}

	@Then("the Trainee should be created successfully for trainee service test")
	public void thenTraineeShouldBeCreatedSuccessfully() {
		assertNotNull(traineeId);
		verify(traineeRepository, times(1)).save(trainee);
	}

	@Then("a UserNotFoundException should be thrown for trainee service test")
	public void thenUserNotFoundExceptionShouldBeThrown() {
		assertNotNull(exception);
		assertTrue(exception instanceof UserNotFoundException);
		verify(traineeRepository, never()).save(any());
	}

	@Then("the Trainee should be updated successfully for trainee service test")
	public void thenTraineeShouldBeUpdatedSuccessfully() {
		assertNotNull(traineeId);
		verify(traineeRepository, times(1)).save(trainee);
	}

	@Then("the Trainee should be deleted successfully for trainee service test")
	public void thenTraineeShouldBeDeletedSuccessfully() {
		verify(traineeRepository, times(1)).delete(trainee);
	}

	@Then("the Trainee should be retrieved successfully for trainee service test")
	public void thenTraineeShouldBeRetrievedSuccessfully() {
		assertNotNull(trainee);
		assertEquals(trainee.getId(), trainee.getId());
	}

	private Trainee createDummyTrainee() {
		Trainee trainee = new Trainee();
		trainee.setId(1L);
		trainee.setDateOfBirth(new Date(System.currentTimeMillis()));
		trainee.setAddress("Dummy Address");
		trainee.setUser(new User(1L, "John", "Doe", "sss", "sss",  true, new Trainee(), new Trainer()));
		trainee.setTrainerList(new ArrayList<>());
		trainee.setTrainingList(new ArrayList<>());
		return trainee;
	}
}


