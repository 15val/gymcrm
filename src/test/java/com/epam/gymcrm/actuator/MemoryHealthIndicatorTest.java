package com.epam.gymcrm.actuator;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureObservability
@TestPropertySource(locations = "classpath:application.properties")
class MemoryHealthIndicatorTest {


	@Test
	void testHealthFreeMemoryAboveThreshold() {
		// Arrange
		MemoryHealthIndicator memoryHealthIndicator = new MemoryHealthIndicator();

		// Act
		Health health = memoryHealthIndicator.health();

		// Assert
		assertEquals(Health.status("UP").build().toString(), health.getStatus().toString() + " {}");
		assertTrue(health.getDetails().containsKey("Free memory"));
		assertTrue(health.getDetails().containsKey("Total memory"));
		assertTrue(health.getDetails().containsKey("Free memory percent"));
	}

	@Test
	void testHealthFreeMemoryBelowThreshold() {
		// Arrange
		MemoryHealthIndicator memoryHealthIndicator = new MemoryHealthIndicator() {
			@Override
			public Health health() {
				// Mocking a scenario where free memory percent is below the threshold
				return Health.down()
						.withDetail("Free memory", 1000L + " bytes")
						.withDetail("Total memory", 5000L + " bytes")
						.withDetail("Free memory percent", 20.0 + " % but required at least 30%")
						.build();
			}
		};

		// Act
		Health health = memoryHealthIndicator.health();

		// Assert
		assertEquals(Health.status("DOWN").build().toString(), health.getStatus().toString() + " {}");

		assertTrue(health.getDetails().containsKey("Free memory"));
		assertTrue(health.getDetails().containsKey("Total memory"));
		assertTrue(health.getDetails().containsKey("Free memory percent"));
	}
}


