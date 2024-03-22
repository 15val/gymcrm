package com.epam.gymcrm.config;

import com.epam.gymcrm.controller.TrainingTypeController;
import com.epam.gymcrm.facade.TrainingTypeFacade;
import com.epam.gymcrm.repository.TraineeRepository;
import com.epam.gymcrm.repository.TrainerRepository;
import com.epam.gymcrm.repository.TrainingRepository;
import com.epam.gymcrm.repository.TrainingTypeRepository;
import com.epam.gymcrm.repository.UserRepository;
import com.epam.gymcrm.service.TraineeService;
import com.epam.gymcrm.service.TrainerService;
import com.epam.gymcrm.service.TrainingService;
import com.epam.gymcrm.service.TrainingTypeService;
import com.epam.gymcrm.service.UserService;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
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
		return new UserService(userRepository);
	}

	@Bean
	public TrainingTypeService trainingTypeService(){
		return new TrainingTypeService(trainingTypeRepository);
	}

}