package com.epam.gymcrm.service;

import com.epam.gymcrm.entity.Trainee;
import com.epam.gymcrm.entity.Trainer;
import com.epam.gymcrm.entity.User;
import com.epam.gymcrm.exception.UserNotFoundException;
import com.epam.gymcrm.exception.UsernameOrPasswordInvalidException;
import com.epam.gymcrm.repository.UserRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureObservability
@TestPropertySource(locations = "classpath:application.properties")
class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	@Test
	void testGenerateUsername() {
		String username = userService.generateUsername("John", "Doe");
		assertNotNull(username);
		assertTrue(username.matches("John.Doe\\d*"));
	}

	@Test
	void testGeneratePassword() {
		String password = userService.generatePassword();
		assertNotNull(password);
		assertEquals(10, password.length());
	}

	@Test
	void testIsUsernameAndPasswordValid_WithValidUser() {
		User user = new User();
		user.setId(1L);
		user.setUsername("sss");
		user.setPassword("sss");
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		assertTrue(userService.isUsernameAndPasswordValid(1L));
	}

	@Test
	void testIsUsernameAndPasswordValid_WithInvalidUser() {
		when(userRepository.findById(2L)).thenReturn(Optional.empty());
		assertFalse(userService.isUsernameAndPasswordValid(2L));
	}

	@Test
	void testGetUserById_WithValidId() throws UserNotFoundException, UsernameOrPasswordInvalidException {
		User user = new User();
		user.setId(1L);
		user.setUsername("sss");
		user.setPassword("sss");
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		User retrievedUser = userService.getUserById(1L);
		assertNotNull(retrievedUser);
		assertEquals(Optional.of(1L), Optional.of(retrievedUser.getId()));
	}

	@Test
	void testGetUserById_WithInvalidId() throws UserNotFoundException, UsernameOrPasswordInvalidException {
		when(userRepository.findById(2L)).thenReturn(Optional.empty());
		Assertions.assertThrows(UsernameOrPasswordInvalidException.class, () -> {
			userService.getUserById(2L);
		});
	}

	@Test
	void testUpdateUser_WithInvalidUser() throws UsernameOrPasswordInvalidException {
		User user = new User();
		user.setId(2L);
		when(userRepository.findById(2L)).thenReturn(Optional.empty());
		Assertions.assertThrows(UsernameOrPasswordInvalidException.class, () -> {
			userService.updateUser(user);
		});
	}

	@Test
	void testDeleteUser_WithValidId() throws UserNotFoundException, UsernameOrPasswordInvalidException {
		User user = new User();
		user.setId(1L);
		when(userRepository.findById(1L)).thenReturn(Optional.of(new User(1L, "John", "Doe", "sss", "sss", true, new Trainee(), new Trainer())));
		userService.deleteUser(1L);
	}

	@Test
	void testDeleteUser_WithInvalidId() throws UserNotFoundException, UsernameOrPasswordInvalidException {
		when(userRepository.findById(2L)).thenReturn(Optional.empty());
		Assertions.assertThrows(UsernameOrPasswordInvalidException.class, () -> {
			userService.deleteUser(2L);
		});
	}
}