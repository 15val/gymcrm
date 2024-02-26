package com.epam.gymcrm.service;

import com.epam.gymcrm.entity.Trainee;
import com.epam.gymcrm.entity.Trainer;
import com.epam.gymcrm.entity.User;
import com.epam.gymcrm.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
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
	void testGetUserById_WithValidId() {
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
	void testGetUserById_WithInvalidId() {
		when(userRepository.findById(2L)).thenReturn(Optional.empty());
		User retrievedUser = userService.getUserById(2L);
		assertNull(retrievedUser);
	}

	@Test
	void testCreateUser() {
		when(userRepository.findByUsername(any())).thenReturn(new User(1L, "John", "Doe", "sss", "sss",  true, new Trainee(), new Trainer()));
		Long userId = userService.createUser("John", "Doe");
		assertNotNull(userId);
	}

	@Test
	void testUpdateUser_WithValidUser() {
		User user = new User();
		user.setId(1L);
		user.setUsername("john.doe");
		user.setPassword("sss");
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		Long updatedUserId = userService.updateUser(user);
		assertNotNull(updatedUserId);
		assertEquals(Optional.of(1L), Optional.of(updatedUserId));
	}

	@Test
	void testUpdateUser_WithInvalidUser() {
		User user = new User();
		user.setId(2L);
		when(userRepository.findById(2L)).thenReturn(Optional.empty());
		Long updatedUserId = userService.updateUser(user);
		assertNull(updatedUserId);
	}

	@Test
	void testDeleteUser_WithValidId() {
		User user = new User();
		user.setId(1L);
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		userService.deleteUser(1L);
		// No assertion, just testing if it runs without exception
	}

	@Test
	void testDeleteUser_WithInvalidId() {
		when(userRepository.findById(2L)).thenReturn(Optional.empty());
		userService.deleteUser(2L);
		// No assertion, just testing if it runs without exception
	}
}