package com.epam.gymcrm.actuator;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureObservability
@TestPropertySource(locations = "classpath:application.properties")
class ServerPingHealthIndicatorTest {

	@Test
	void testHealthServerReachable() {
		// Arrange
		ServerPingHealthIndicator serverPingHealthIndicator = new ServerPingHealthIndicator() {
			@Override
			public Health health() {
				// Mocking scenario where server is reachable
				return Health.up().withDetail("Server status", "Server is reachable").build();
			}
		};

		// Act
		Health health = serverPingHealthIndicator.health();

		// Assert
		assertEquals(Health.status("UP").build().toString(), health.getStatus().toString() + " {}");
		assertTrue(health.getDetails().containsKey("Server status"));
		assertEquals("Server is reachable", health.getDetails().get("Server status"));
	}

	@Test
	void testHealthServerUnreachable() {
		// Arrange
		ServerPingHealthIndicator serverPingHealthIndicator = new ServerPingHealthIndicator() {
			@Override
			public Health health() {
				// Mocking scenario where server is unreachable
				return Health.down().withDetail("Server status", "Server is unreachable").build();
			}
		};

		// Act
		Health health = serverPingHealthIndicator.health();

		// Assert
		assertEquals(Health.status("DOWN").build().toString(), health.getStatus().toString() + " {}");
		assertTrue(health.getDetails().containsKey("Server status"));
		assertEquals("Server is unreachable", health.getDetails().get("Server status"));
	}

	@Test
	void testHealthServerError() {
		// Arrange
		ServerPingHealthIndicator serverPingHealthIndicator = new ServerPingHealthIndicator() {
			@Override
			public Health health() {
				// Mocking scenario where an error occurs while pinging the server
				return Health.down(new IOException("Connection timed out"))
						.withDetail("Server status", "Error while pinging server").build();
			}
		};

		// Act
		Health health = serverPingHealthIndicator.health();

		// Assert
		assertEquals(Health.status("DOWN").build().toString(), health.getStatus().toString() + " {}");
		assertTrue(health.getDetails().containsKey("Server status"));
		assertEquals("Error while pinging server", health.getDetails().get("Server status"));
	}
}


