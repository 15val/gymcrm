package com.epam.gymcrm.config;

import com.epam.gymcrm.repository.TraineeRepository;
import com.epam.gymcrm.repository.TrainerRepository;
import com.epam.gymcrm.repository.TrainingRepository;
import com.epam.gymcrm.repository.TrainingTypeRepository;
import com.epam.gymcrm.repository.UserRepository;
import com.epam.gymcrm.service.AppUserDetailsService;
import com.epam.gymcrm.service.LoginAttemptService;
import com.epam.gymcrm.service.TraineeService;
import com.epam.gymcrm.service.TrainerService;
import com.epam.gymcrm.service.TrainingService;
import com.epam.gymcrm.service.TrainingTypeService;
import com.epam.gymcrm.service.UserService;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan(basePackages = "com.epam.gymcrm")
@EnableTransactionManagement
public class AppConfig {
	private TraineeRepository traineeRepository;
	private TrainerRepository trainerRepository;
	private TrainingRepository trainingRepository;
	private TrainingTypeRepository trainingTypeRepository;
	private UserRepository userRepository;
	private UserService	userService;
	private PasswordEncoder passwordEncoder;
	private LoginAttemptService loginAttemptService;

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

	@Bean
	public TraineeService traineeService(){
		return new TraineeService(traineeRepository, userRepository, userService, trainerRepository);
	}

	@Bean
	public TrainerService trainerService(){
		return new TrainerService(trainerRepository, userRepository, userService);
	}

	@Bean
	public TrainingService trainingService(){
		return new TrainingService(trainingRepository, trainingTypeRepository, userRepository, userService);
	}

	@Bean
	public UserService userService(){
		return new UserService(userRepository, passwordEncoder);
	}

	@Bean
	public TrainingTypeService trainingTypeService(){
		return new TrainingTypeService(trainingTypeRepository);
	}

	@Bean
	public AppUserDetailsService appUserDetailsService(){
		return new AppUserDetailsService(userRepository, loginAttemptService);
	}
}