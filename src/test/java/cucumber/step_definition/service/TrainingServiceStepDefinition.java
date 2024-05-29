package cucumber.step_definition.service;

import com.epam.gymcrm.entity.Trainee;
import com.epam.gymcrm.entity.Trainer;
import com.epam.gymcrm.entity.Training;
import com.epam.gymcrm.entity.TrainingType;
import com.epam.gymcrm.entity.User;
import com.epam.gymcrm.exception.UserNotFoundException;
import com.epam.gymcrm.exception.UsernameOrPasswordInvalidException;
import com.epam.gymcrm.repository.TrainingRepository;
import com.epam.gymcrm.repository.TrainingTypeRepository;
import com.epam.gymcrm.repository.UserRepository;
import com.epam.gymcrm.service.TrainingService;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TrainingServiceStepDefinition {

	@Mock
	private TrainingRepository trainingRepository;

	@Mock
	private TrainingTypeRepository trainingTypeRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserService userService;

	@InjectMocks
	private TrainingService trainingService;

	private Training training;
	private Exception exception;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Given("a Training with a non-existent Trainee for training service test")
	public void givenTrainingWithNonExistentTrainee() {
		training = createDummyTraining();
		when(userService.isUsernameAndPasswordValid(any(Long.class))).thenReturn(true);
		when(userRepository.findById(101L)).thenReturn(Optional.empty());
	}

	@Given("a Training with a non-existent Trainer for training service test")
	public void givenTrainingWithNonExistentTrainer() {
		training = createDummyTraining();
		when(userService.isUsernameAndPasswordValid(any(Long.class))).thenReturn(true);
		when(userRepository.findById(101L)).thenReturn(Optional.of(training.getTrainee1().getUser()));
		when(userRepository.findById(201L)).thenReturn(Optional.empty());
	}

	@Given("a Training with a non-existent TrainingType for training service test")
	public void givenTrainingWithNonExistentTrainingType() {
		training = createDummyTraining();
		when(userService.isUsernameAndPasswordValid(any(Long.class))).thenReturn(true);
		when(userRepository.findById(101L)).thenReturn(Optional.of(training.getTrainee1().getUser()));
		when(userRepository.findById(201L)).thenReturn(Optional.of(training.getTrainer1().getUser1()));
		when(trainingTypeRepository.findById(1L)).thenReturn(Optional.empty());
	}

	@Given("a valid Training Id for training service test")
	public void givenValidTrainingId() {
		training = Training.builder().id(1L).build();
		when(trainingRepository.findById(1L)).thenReturn(Optional.of(training));
	}

	@Given("an invalid Training Id for training service test")
	public void givenInvalidTrainingId() {
		when(trainingRepository.findById(1L)).thenReturn(Optional.empty());
	}

	@When("the createTraining method is called for training service test")
	public void whenCreateTrainingMethodIsCalled() {
		try {
			trainingService.createTraining(training);
		} catch (UserNotFoundException | UsernameOrPasswordInvalidException e) {
			exception = e;
		}
	}

	@When("the getTrainingById method is called for training service test")
	public void whenGetTrainingByIdMethodIsCalled() {
		try {
			training = trainingService.getTrainingById(1L);
		} catch (UserNotFoundException e) {
			exception = e;
		}
	}

	@Then("a UserNotFoundException should be thrown for training service test")
	public void thenUserNotFoundExceptionShouldBeThrown() {
		assertNotNull(exception);
		assertTrue(exception instanceof UserNotFoundException);
	}

	@Then("the Training should be retrieved successfully for training service test")
	public void thenTrainingShouldBeRetrievedSuccessfully() {
		assertNotNull(training);
		assertEquals(training.getId(), training.getId());
	}

	private Training createDummyTraining() {
		User userTrainee = User.builder()
				.id(101L)
				.firstName("John")
				.lastName("Doe")
				.username("sss")
				.password("password")
				.isActive(true)
				.build();
		User userTrainer = User.builder()
				.id(201L)
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
				.user(userTrainee)
				.build();

		Training training = Training.builder()
				.id(1L)
				.trainee1(trainee)
				.trainingDuration(25)
				.trainingDate(new Date(System.currentTimeMillis()))
				.trainingName("Training Name")
				.build();

		TrainingType trainingType = TrainingType.builder()
				.id(1L)
				.trainingTypeName("Training Type")
				.build();

		Trainer trainer = Trainer.builder()
				.id(2L)
				.trainingType2(trainingType)
				.user1(userTrainer)
				.traineeList(List.of(trainee))
				.trainingList(List.of(training))
				.build();

		return Training.builder()
				.trainee1(trainee)
				.trainer1(trainer)
				.trainingName("Test Training")
				.trainingType1(trainingType)
				.trainingDate(new Date(System.currentTimeMillis()))
				.trainingDuration(60)
				.build();
	}
}

