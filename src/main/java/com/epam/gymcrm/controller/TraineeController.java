package com.epam.gymcrm.controller;

import com.epam.gymcrm.entity.Trainee;
import com.epam.gymcrm.entity.User;
import com.epam.gymcrm.service.TraineeService;
import com.epam.gymcrm.service.UserService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.resource.transaction.backend.jdbc.internal.JdbcResourceLocalTransactionCoordinatorImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.aop.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/trainee")
@Slf4j
public class TraineeController {

	private final TraineeService traineeService;
	private final UserService userService;

	@Transactional
	@PostMapping("/register")
	public ResponseEntity<Map<String, String>> registerTrainee(@RequestBody Map<String, String> request) {
		try {
			String firstName = request.get("firstName");
			String lastName = request.get("lastName");
			if (firstName == null || lastName == null) {
				return ResponseEntity.badRequest().build();
			}
			Long userId = userService.createUser(firstName, lastName);

			User user = userService.getUserById(userId);
			Trainee trainee = Trainee.builder()
					.user(user)
					.build();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			if (request.containsKey("dateOfBirth")) {
				trainee.setDateOfBirth(dateFormat.parse(request.get("dateOfBirth")));
			}
			if (request.containsKey("address")) {
				trainee.setAddress(request.get("address"));
			}

			traineeService.createTrainee(trainee);
			Map<String, String> response = new HashMap<>();
			response.put("username", user.getUsername());
			response.put("password", user.getPassword());
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			log.error("Controller: Error while creating trainee: {}", e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}
}


