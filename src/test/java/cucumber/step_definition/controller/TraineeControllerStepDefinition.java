package cucumber.step_definition.controller;

import com.epam.gymcrm.controller.TraineeController;
import com.epam.gymcrm.dto.CreateTraineeDto;
import com.epam.gymcrm.dto.GetTraineeDto;
import com.epam.gymcrm.dto.UpdateTraineeDto;
import com.epam.gymcrm.dto.UsernameAndPasswordDto;
import com.epam.gymcrm.dto.UsernameDto;
import com.epam.gymcrm.exception.UserNotFoundException;
import com.epam.gymcrm.exception.UsernameOrPasswordInvalidException;
import com.epam.gymcrm.facade.TraineeFacade;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TraineeControllerStepDefinition {

	@Mock
	private TraineeFacade traineeFacade;

	@InjectMocks
	private TraineeController traineeController;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	private ResponseEntity<?> responseEntity;

	@Given("a trainee CreateTraineeDto")
	public void givenCreateTraineeDto() throws UserNotFoundException, UsernameOrPasswordInvalidException, ParseException {
		when(traineeFacade.registerTrainee(any(CreateTraineeDto.class))).thenReturn(new UsernameAndPasswordDto());
	}

	@Given("a trainee CreateTraineeDto exception")
	public void givenCreateTraineeDtoException() throws UserNotFoundException, UsernameOrPasswordInvalidException, ParseException {
		when(traineeFacade.registerTrainee(any(CreateTraineeDto.class))).thenThrow(new RuntimeException());
	}

	@Given("an trainee UpdateTraineeDto")
	public void givenUpdateTraineeDto() throws UsernameOrPasswordInvalidException, ParseException {
		when(traineeFacade.updateTrainee(any(UpdateTraineeDto.class))).thenThrow(new RuntimeException());
	}

	@Given("a trainee UsernameDto")
	public void givenUsernameDto() throws UsernameOrPasswordInvalidException {
		when(traineeFacade.getTrainee(any(UsernameDto.class))).thenReturn(new GetTraineeDto());
	}


	@When("registerTrainee method is called")
	public void whenRegisterTraineeMethodIsCalled() {
		responseEntity = traineeController.registerTrainee(new CreateTraineeDto());
	}

	@When("the registerTrainee method is called and throws exception")
	public void whenRegisterTraineeMethodIsCalledAndThrowsException() {
		responseEntity = traineeController.registerTrainee(new CreateTraineeDto());
	}

	@When("the updateTrainee method is called and throws exception")
	public void whenUpdateTraineeMethodIsCalledAndThrowsException() {
		responseEntity = traineeController.updateTrainee(new UpdateTraineeDto());
	}

	@When("the getTrainee method is called")
	public void whenGetTraineeMethodIsCalled() {
		responseEntity = traineeController.getTrainee(new UsernameDto());
	}

	@When("the getTrainee method is called and throws exception")
	public void whenGetTraineeMethodIsCalledAndThrowsException() throws UsernameOrPasswordInvalidException {
		when(traineeFacade.getTrainee(any(UsernameDto.class))).thenThrow(new RuntimeException());
		responseEntity = traineeController.getTrainee(new UsernameDto());
	}


	@Then("Trainee should be registered")
	public void thenTraineeShouldBeRegistered() throws UserNotFoundException, UsernameOrPasswordInvalidException, ParseException {
		verify(traineeFacade, times(1)).registerTrainee(any(CreateTraineeDto.class));
		assert responseEntity.getStatusCode() == HttpStatus.OK;
	}

	@Then("the response status should be INTERNAL_SERVER_ERROR for register")
	public void thenResponseStatusShouldBeInternalServerErrorForRegister() throws UserNotFoundException, UsernameOrPasswordInvalidException, ParseException {
		verify(traineeFacade, times(1)).registerTrainee(any(CreateTraineeDto.class));
		assert responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR;
	}

	@Then("the response status should be INTERNAL_SERVER_ERROR for update")
	public void thenResponseStatusShouldBeInternalServerErrorForUpdate() throws UserNotFoundException, UsernameOrPasswordInvalidException, ParseException {
		verify(traineeFacade, times(1)).updateTrainee(any(UpdateTraineeDto.class));
		assert responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR;
	}

	@Then("the response status should be INTERNAL_SERVER_ERROR for retrieving")
	public void thenResponseStatusShouldBeInternalServerErrorForRetrieving() throws UserNotFoundException, UsernameOrPasswordInvalidException, ParseException {
		verify(traineeFacade, times(1)).getTrainee(any(UsernameDto.class));
		assert responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR;
	}

	@Then("the response status should be OK")
	public void thenResponseStatusShouldBeOk() throws UsernameOrPasswordInvalidException {
		verify(traineeFacade, times(1)).getTrainee(any(UsernameDto.class));
		assert responseEntity.getStatusCode() == HttpStatus.OK;
	}
}


