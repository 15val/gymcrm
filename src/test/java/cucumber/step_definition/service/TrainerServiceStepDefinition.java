package cucumber.step_definition.service;

import com.epam.gymcrm.entity.Trainee;
import com.epam.gymcrm.entity.Trainer;
import com.epam.gymcrm.entity.Training;
import com.epam.gymcrm.entity.User;
import com.epam.gymcrm.exception.UserNotFoundException;
import com.epam.gymcrm.exception.UsernameOrPasswordInvalidException;
import com.epam.gymcrm.repository.TrainerRepository;
import com.epam.gymcrm.repository.UserRepository;
import com.epam.gymcrm.service.TrainerService;
import com.epam.gymcrm.service.UserService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TrainerServiceStepDefinition {

	@Mock
	private TrainerRepository trainerRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserService userService;

	@InjectMocks
	private TrainerService trainerService;

	private Trainer trainer;
	private Long trainerId;
	private Exception exception;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Given("a Trainer and a User for trainer service test")
	public void givenTrainerAndUser() {
		trainer = createDummyTrainer();
		when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(trainer.getUser1()));
	}

	@Given("a Trainer without a User for trainer service test")
	public void givenTrainerWithoutUser() {
		trainer = createDummyTrainer();
		when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());
	}

	@Given("a valid Trainer for trainer service test")
	public void givenValidTrainer() {
		trainer = createDummyTrainer();
		when(userService.isUsernameAndPasswordValid(any(Long.class))).thenReturn(true);
		when(trainerRepository.save(any(Trainer.class))).thenReturn(trainer);
	}

	@Given("a valid Trainer Id for trainer service test")
	public void givenValidTrainerId() {
		trainer = createDummyTrainer();
		when(trainerRepository.findById(any(Long.class))).thenReturn(Optional.of(trainer));
		when(userService.isUsernameAndPasswordValid(any(Long.class))).thenReturn(true);
	}

	@Given("an invalid Trainer Id for trainer service test")
	public void givenInvalidTrainerId() {
		when(trainerRepository.findById(any(Long.class))).thenReturn(Optional.empty());
	}

	@When("the createTrainer method is called for trainer service test")
	public void whenCreateTrainerMethodIsCalled() {
		trainerId = trainerService.createTrainer(trainer);
	}

	@When("the updateTrainer method is called for trainer service test")
	public void whenUpdateTrainerMethodIsCalled() {
		trainerId = trainerService.updateTrainer(trainer);
	}

	@When("the getTrainerById method is called for trainer service test")
	public void whenGetTrainerByIdMethodIsCalled() {
		try {
			trainer = trainerService.getTrainerById(1L);
		} catch (UserNotFoundException | UsernameOrPasswordInvalidException e) {
			exception = e;
		}
	}

	@Then("the Trainer should be created successfully for trainer service test")
	public void thenTrainerShouldBeCreatedSuccessfully() {
		assertNotNull(trainerId);
		verify(trainerRepository, times(1)).save(trainer);
	}

	@Then("the Trainer should not be created for trainer service test")
	public void thenTrainerShouldNotBeCreated() {
		assertNull(trainerId);
	}

	@Then("the Trainer should be updated successfully for trainer service test")
	public void thenTrainerShouldBeUpdatedSuccessfully() {
		assertNotNull(trainerId);
		verify(trainerRepository, times(1)).save(trainer);
	}

	@Then("the Trainer should be retrieved successfully for trainer service test")
	public void thenTrainerShouldBeRetrievedSuccessfully() {
		assertNotNull(trainer);
		assertEquals(trainer.getId(), trainer.getId());
	}

	@Then("a UserNotFoundException should be thrown for trainer service test")
	public void thenUserNotFoundExceptionShouldBeThrown() {
		assertNotNull(exception);
		assertTrue(exception instanceof UserNotFoundException);
	}

	private Trainer createDummyTrainer() {
		User user = User.builder()
				.id(1L)
				.firstName("John")
				.lastName("Doe")
				.username("sss")
				.password("password")
				.isActive(true)
				.build();
		Trainee trainee = Trainee.builder()
				.id(1L)
				.dateOfBirth(new Date(System.currentTimeMillis()))
				.address("Dummy Address")
				.user(user)
				.build();
		Training training = Training.builder()
				.id(1L)
				.trainee1(trainee)
				.trainingDuration(25)
				.trainingDate(new Date(System.currentTimeMillis()))
				.trainingName("Training Name")
				.build();
		return Trainer.builder()
				.id(1L)
				.trainingType2(null)
				.user1(user)
				.traineeList(List.of(trainee))
				.trainingList(List.of(training))
				.build();
	}

}
