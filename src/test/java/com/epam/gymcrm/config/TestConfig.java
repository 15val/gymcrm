package com.epam.gymcrm.config;

import com.epam.gymcrm.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.step_definition.integration.TraineeRegistrationStepDefinition;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ComponentScan
@EnableTransactionManagement
public class TestConfig {

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

}
