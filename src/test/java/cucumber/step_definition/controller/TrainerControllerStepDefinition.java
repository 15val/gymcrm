package cucumber.step_definition.controller;

import com.epam.gymcrm.controller.TrainerController;
import com.epam.gymcrm.dto.CreateTrainerDto;
import com.epam.gymcrm.dto.GetTrainerDto;
import com.epam.gymcrm.dto.GetTrainersTrainingListRequestDto;
import com.epam.gymcrm.dto.GetTrainingListDto;
import com.epam.gymcrm.dto.UpdateTrainerDto;
import com.epam.gymcrm.dto.UpdateTrainerResponseDto;
import com.epam.gymcrm.dto.UsernameAndIsActiveDto;
import com.epam.gymcrm.dto.UsernameAndPasswordDto;
import com.epam.gymcrm.dto.UsernameDto;
import com.epam.gymcrm.exception.UserNotFoundException;
import com.epam.gymcrm.exception.UsernameOrPasswordInvalidException;
import com.epam.gymcrm.facade.TrainerFacade;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class TrainerControllerStepDefinition {

	@Mock
	private TrainerFacade trainerFacade;

	@InjectMocks
	private TrainerController trainerController;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	private ResponseEntity<?> responseEntity;

	@Given("a trainer CreateTrainerDto")
	public void givenCreateTrainerDto() throws UserNotFoundException, UsernameOrPasswordInvalidException {
		when(trainerFacade.registerTrainer(any(CreateTrainerDto.class))).thenReturn(new UsernameAndPasswordDto());
	}

	@Given("a trainer CreateTrainerDto that throws exception")
	public void givenCreateTrainerDtoException() throws UserNotFoundException, UsernameOrPasswordInvalidException {
		when(trainerFacade.registerTrainer(any(CreateTrainerDto.class))).thenThrow(new RuntimeException());
	}

	@Given("an trainer UpdateTrainerDto")
	public void givenUpdateTrainerDto() throws UsernameOrPasswordInvalidException {
		when(trainerFacade.updateTrainer(any(UpdateTrainerDto.class))).thenReturn(new UpdateTrainerResponseDto());
	}

	@Given("an trainer UpdateTrainerDto that throws exception")
	public void givenUpdateTrainerDtoException() throws UsernameOrPasswordInvalidException {
		when(trainerFacade.updateTrainer(any(UpdateTrainerDto.class))).thenThrow(new RuntimeException());
	}

	@Given("a trainer UsernameDto")
	public void givenUsernameDto() {
		when(trainerFacade.getTrainer(any(UsernameDto.class))).thenReturn(new GetTrainerDto());
	}

	@Given("a trainer UsernameDto that throws exception")
	public void givenUsernameDtoException() {
		when(trainerFacade.getTrainer(any(UsernameDto.class))).thenThrow(new RuntimeException());
	}

	@Given("a trainer UsernameAndIsActiveDto")
	public void givenUsernameAndIsActiveDto() throws UsernameOrPasswordInvalidException {
		doNothing().when(trainerFacade).updateIsActiveTrainer(any(UsernameAndIsActiveDto.class));
	}

	@Given("a trainer UsernameAndIsActiveDto that throws exception")
	public void givenUsernameAndIsActiveDtoException() throws UsernameOrPasswordInvalidException {
		doThrow(new RuntimeException()).when(trainerFacade).updateIsActiveTrainer(any(UsernameAndIsActiveDto.class));
	}

	@Given("a trainer GetTrainersTrainingListRequestDto")
	public void givenGetTrainersTrainingListRequestDto() throws UserNotFoundException, ParseException {
		when(trainerFacade.getTrainersTrainingList(any(GetTrainersTrainingListRequestDto.class))).thenReturn(new GetTrainingListDto());
	}

	@Given("a trainer GetTrainersTrainingListRequestDto that throws exception")
	public void givenGetTrainersTrainingListRequestDtoException() throws UserNotFoundException, ParseException {
		when(trainerFacade.getTrainersTrainingList(any(GetTrainersTrainingListRequestDto.class))).thenThrow(new RuntimeException());
	}

	@When("the registerTrainer method is called")
	public void whenRegisterTrainerMethodIsCalled() {
		responseEntity = trainerController.registerTrainer(new CreateTrainerDto());
	}

	@When("the updateTrainer method is called")
	public void whenUpdateTrainerMethodIsCalled() {
		responseEntity = trainerController.updateTrainer(new UpdateTrainerDto());
	}

	@When("the getTrainer method is called")
	public void whenGetTrainerMethodIsCalled() {
		responseEntity = trainerController.getTrainer(new UsernameDto());
	}

	@When("the updateIsActive method is called")
	public void whenUpdateIsActiveMethodIsCalled() {
		responseEntity = trainerController.updateIsActive(new UsernameAndIsActiveDto());
	}

	@When("the getTrainersTrainingList method is called")
	public void whenGetTrainersTrainingListMethodIsCalled() {
		responseEntity = trainerController.getTrainersTrainingList(new GetTrainersTrainingListRequestDto());
	}

	@Then("the response status should be OK for trainer")
	public void thenResponseStatusShouldBeOk() {
		assert responseEntity.getStatusCode() == HttpStatus.OK;
	}

	@Then("the response status should be INTERNAL_SERVER_ERROR for trainer")
	public void thenResponseStatusShouldBeInternalServerError() {
		assert responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR;
	}
}


