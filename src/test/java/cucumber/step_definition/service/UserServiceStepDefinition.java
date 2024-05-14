package cucumber.step_definition.service;

import com.epam.gymcrm.entity.User;
import com.epam.gymcrm.exception.UserNotFoundException;
import com.epam.gymcrm.exception.UsernameOrPasswordInvalidException;
import com.epam.gymcrm.repository.UserRepository;
import com.epam.gymcrm.service.UserService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceStepDefinition {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	private User user;
	private String username;
	private String password;
	private boolean isValid;
	private Exception exception;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Given("a first name and a last name for user service test")
	public void givenFirstNameAndLastName() {
		// No action required
	}

	@Given("a valid User for user service test")
	public void givenValidUser() {
		user = new User();
		user.setId(1L);
		user.setUsername("sss");
		user.setPassword("sss");
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
	}

	@Given("an invalid User for user service test")
	public void givenInvalidUser() {
		user = new User();
		user.setId(2L);
		when(userRepository.findById(2L)).thenReturn(Optional.empty());
	}

	@Given("a valid User Id for user service test")
	public void givenValidUserId() {
		user = new User();
		user.setId(1L);
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
	}

	@Given("an invalid User Id for user service test")
	public void givenInvalidUserId() {
		when(userRepository.findById(2L)).thenReturn(Optional.empty());
	}

	@When("the generateUsername method is called for user service test")
	public void whenGenerateUsernameMethodIsCalled() {
		username = userService.generateUsername("John", "Doe");
	}

	@When("the generatePassword method is called for user service test")
	public void whenGeneratePasswordMethodIsCalled() {
		password = userService.generatePassword();
	}

	@When("the isUsernameAndPasswordValid method is called for user service test")
	public void whenIsUsernameAndPasswordValidMethodIsCalled() {
		isValid = userService.isUsernameAndPasswordValid(user.getId());
	}

	@When("the getUserById method is called for user service test")
	public void whenGetUserByIdMethodIsCalled() {
		try {
			user = userService.getUserById(1L);
		} catch (UserNotFoundException | UsernameOrPasswordInvalidException e) {
			exception = e;
		}
	}

	@When("the updateUser method is called for user service test")
	public void whenUpdateUserMethodIsCalled() {
		try {
			userService.updateUser(user);
		} catch (UsernameOrPasswordInvalidException e) {
			exception = e;
		}
	}

	@When("the deleteUser method is called for user service test")
	public void whenDeleteUserMethodIsCalled() {
		try {
			userService.deleteUser(1L);
		} catch (UserNotFoundException | UsernameOrPasswordInvalidException e) {
			exception = e;
		}
	}

	@Then("a valid username should be generated for user service test")
	public void thenValidUsernameShouldBeGenerated() {
		assertNotNull(username);
		assertTrue(username.matches("John.Doe\\d*"));
	}

	@Then("a valid password should be generated for user service test")
	public void thenValidPasswordShouldBeGenerated() {
		assertNotNull(password);
		assertEquals(10, password.length());
	}

	@Then("the result should be true for user service test")
	public void thenResultShouldBeTrue() {
		assertTrue(isValid);
	}

	@Then("the result should be false for user service test")
	public void thenResultShouldBeFalse() {
		assertFalse(isValid);
	}

	@Then("the User should be retrieved successfully for user service test")
	public void thenUserShouldBeRetrievedSuccessfully() {
		assertNotNull(user);
		assertEquals(user.getId(), Long.valueOf(1L));
	}

	@Then("a UsernameOrPasswordInvalidException should be thrown for user service test")
	public void thenUsernameOrPasswordInvalidExceptionShouldBeThrown() {
		assertNotNull(exception);
		assertTrue(exception instanceof UsernameOrPasswordInvalidException);
	}

}
