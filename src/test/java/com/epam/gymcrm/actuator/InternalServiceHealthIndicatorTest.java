package com.epam.gymcrm.actuator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureObservability
@TestPropertySource(locations = "classpath:application.properties")
class InternalServiceHealthIndicatorTest {

	@MockBean
	private TestRestTemplate restTemplate;

	private InternalServiceHealthIndicator healthIndicator;

	@BeforeEach
	void setUp() {
		healthIndicator = new InternalServiceHealthIndicator();
	}

	@Test
	void healthIndicator_WhenInternalServiceIsAvailable_ExpectUp() {
		when(restTemplate.getForEntity("http://localhost:5433/trainingType/get", String.class))
				.thenReturn(ResponseEntity.ok("Training Types Retrieved"));

		var health = healthIndicator.health();

		assertEquals("UP", health.getStatus().getCode());
	}

}
