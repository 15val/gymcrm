package cucumber.step_definition.integration;

import com.epam.gymcrm.config.TestConfig;
import com.epam.gymcrm.dto.CreateTraineeDto;
import com.epam.gymcrm.dto.UsernameAndPasswordDto;
import com.epam.gymcrm.entity.Trainee;
import com.epam.gymcrm.entity.User;
import com.epam.gymcrm.repository.TraineeRepository;
import com.epam.gymcrm.repository.UserRepository;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import lombok.RequiredArgsConstructor;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
@ComponentScan
public class TraineeRegistrationStepDefinition {

	private final JdbcTemplate jdbcTemplate;

	public TraineeRegistrationStepDefinition() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/gymcrm");
		dataSource.setUsername("postgres");
		dataSource.setPassword("root");
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private final RestTemplate restTemplate = new RestTemplate();
	private final CreateTraineeDto createTraineeDto = new CreateTraineeDto();
	private ResponseEntity<UsernameAndPasswordDto> response;

	@Given("a CreateTraineeDto request for TraineeRegistrationTest")
	public void givenCreateTraineeDtoRequest() {
		createTraineeDto.setFirstName("Testing");
		createTraineeDto.setLastName("Testing");
		createTraineeDto.setAddress(null);
		createTraineeDto.setDateOfBirth(null);

	}

	@When("the registerTrainee endpoint is called for TraineeRegistrationTest")
	public void whenRegisterTraineeEndpointIsCalled() {
		response = restTemplate.postForEntity("http://localhost:5433/trainee/register", createTraineeDto, UsernameAndPasswordDto.class);
	}

	@Then("a new Trainee should be created and stored in the database for TraineeRegistrationTest")
	public void thenNewTraineeShouldBeCreatedAndStoredInTheDatabase() {
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());

		String sql = "SELECT * FROM \"gymcrm_shema\".\"user\" WHERE username = ?";
		User user = jdbcTemplate.queryForObject(sql, new Object[]{response.getBody().getUsername()}, (rs, rowNum) -> {
			User u = new User();
			u.setUsername(rs.getString("username"));
			u.setFirstName(rs.getString("first_name"));
			u.setLastName(rs.getString("last_name"));
			return u;
		});

		assertNotNull(user);
		assertEquals("Testing", user.getFirstName());
		assertEquals("Testing", user.getLastName());

	}

	@After("@registerTraineeTag")
	public void cleanup() {
		if (response != null && response.getBody() != null) {
			String deleteTraineeSql = "DELETE FROM \"gymcrm_shema\".\"trainee\" WHERE user_id = (SELECT id FROM \"gymcrm_shema\".\"user\" WHERE username = ?)";
			jdbcTemplate.update(deleteTraineeSql, response.getBody().getUsername());

			String sql = "DELETE FROM \"gymcrm_shema\".\"user\" WHERE username = ?";
			jdbcTemplate.update(sql, response.getBody().getUsername());
		}
	}

}


