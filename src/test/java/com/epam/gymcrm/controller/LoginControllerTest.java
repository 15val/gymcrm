package com.epam.gymcrm.controller;

import com.epam.gymcrm.dto.UpdatePasswordDto;
import com.epam.gymcrm.dto.UsernameAndPasswordDto;
import com.epam.gymcrm.exception.UserNotFoundException;
import com.epam.gymcrm.facade.LoginFacade;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureObservability
@TestPropertySource(locations = "classpath:application.properties")
class LoginControllerTest {

	@Mock
	private LoginFacade loginFacade;

	@InjectMocks
	private LoginController loginController;

	@Test
	void testLogin_Success() throws Exception {
		// Підготовка моків та поведінки
		doNothing().when(loginFacade).loginFacade(any(UsernameAndPasswordDto.class));

		// Викликаємо метод контролера
		ResponseEntity<HttpStatus> responseEntity = loginController.login(new UsernameAndPasswordDto());

		// Перевіряємо, що метод loginFacade.loginFacade() був викликаний один раз
		verify(loginFacade, times(1)).loginFacade(any(UsernameAndPasswordDto.class));

		// Перевіряємо статус відповіді
		assert responseEntity.getStatusCode() == HttpStatus.OK;
	}

	@Test
	void testLogin_Error() throws Exception {
		// Підготовка моків та поведінки для симуляції помилки
		doThrow(UserNotFoundException.class).when(loginFacade).loginFacade(any(UsernameAndPasswordDto.class));

		// Викликаємо метод контролера
		ResponseEntity<HttpStatus> responseEntity = loginController.login(new UsernameAndPasswordDto());

		// Перевіряємо, що метод loginFacade.loginFacade() був викликаний один раз
		verify(loginFacade, times(1)).loginFacade(any(UsernameAndPasswordDto.class));

		// Перевіряємо статус відповіді
		assert responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR;
	}

	@Test
	void testUpdatePassword_Success() throws Exception {
		// Підготовка моків та поведінки
		doNothing().when(loginFacade).updatePassword(any(UpdatePasswordDto.class));

		// Викликаємо метод контролера
		ResponseEntity<HttpStatus> responseEntity = loginController.updatePassword(new UpdatePasswordDto());

		// Перевіряємо, що метод loginFacade.updatePasswordFacade() був викликаний один раз
		verify(loginFacade, times(1)).updatePassword(any(UpdatePasswordDto.class));

		// Перевіряємо статус відповіді
		assert responseEntity.getStatusCode() == HttpStatus.OK;
	}

	@Test
	void testUpdatePassword_Error() throws Exception {
		// Підготовка моків та поведінки для симуляції помилки
		doThrow(UserNotFoundException.class).when(loginFacade).updatePassword(any(UpdatePasswordDto.class));

		// Викликаємо метод контролера
		ResponseEntity<HttpStatus> responseEntity = loginController.updatePassword(new UpdatePasswordDto());

		// Перевіряємо, що метод loginFacade.updatePasswordFacade() був викликаний один раз
		verify(loginFacade, times(1)).updatePassword(any(UpdatePasswordDto.class));

		// Перевіряємо статус відповіді
		assert responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR;
	}
}


