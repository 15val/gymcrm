package com.epam.gymcrm.controller;

import com.epam.gymcrm.entity.Trainer;
import com.epam.gymcrm.entity.User;
import com.epam.gymcrm.service.TraineeService;
import com.epam.gymcrm.service.TrainerService;
import com.epam.gymcrm.service.TrainingTypeService;
import com.epam.gymcrm.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/trainer")
@Slf4j
public class TrainerController {

	private final TrainerService trainerService;
	private final UserService userService;
	private final TrainingTypeService trainingTypeService;

	@Transactional
	@PostMapping("/register")
	public ResponseEntity<Map<String, String>> registerTrainer(@RequestBody Map<String, String> request) {
		try {//dto
			String firstName = request.get("firstName");
			String lastName = request.get("lastName");
			String specializationString = request.get("specialization");
			Long specialization;
			if (firstName == null || lastName == null || specializationString == null) {
				return ResponseEntity.badRequest().build();
			}
			else{
				specialization = Long.valueOf(specializationString);
			}
			Long userId = userService.createUser(firstName, lastName);

			User user = userService.getUserById(userId);
			Trainer trainer = Trainer.builder()
					.user1(user)
					.trainingType2(trainingTypeService.getTrainingTypeById(specialization))
					.build();
			trainerService.createTrainer(trainer);
//facade layer
			Map<String, String> response = new HashMap<>();
			response.put("username", user.getUsername());
			response.put("password", user.getPassword());
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			log.error("Controller: Error while creating trainer: {}", e.getMessage());
			return ResponseEntity.internalServerError().build();
		}

	}
}