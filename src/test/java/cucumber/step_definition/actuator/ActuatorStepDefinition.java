package cucumber.step_definition.actuator;

import com.epam.gymcrm.actuator.MemoryHealthIndicator;
import com.epam.gymcrm.actuator.ServerPingHealthIndicator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.boot.actuate.health.Health;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ActuatorStepDefinition {

	private ServerPingHealthIndicator serverPingHealthIndicator;
	private MemoryHealthIndicator memoryHealthIndicator;
	private Health health;

	@Given("the server is reachable")
	public void givenServerIsReachable() {
		serverPingHealthIndicator = new ServerPingHealthIndicator() {
			@Override
			public Health health() {
				return Health.up().withDetail("Server status", "Server is reachable").build();
			}
		};
	}

	@Given("the server is unreachable")
	public void givenServerIsUnreachable() {
		serverPingHealthIndicator = new ServerPingHealthIndicator() {
			@Override
			public Health health() {
				return Health.down().withDetail("Server status", "Server is unreachable").build();
			}
		};
	}

	@Given("an error occurs while pinging the server")
	public void givenErrorOccursWhilePingingServer() {
		serverPingHealthIndicator = new ServerPingHealthIndicator() {
			@Override
			public Health health() {
				return Health.down(new IOException("Connection timed out"))
						.withDetail("Server status", "Error while pinging server").build();
			}
		};
	}

	@Given("the free memory is above the threshold")
	public void givenFreeMemoryIsAboveThreshold() {
		memoryHealthIndicator = new MemoryHealthIndicator();
	}

	@Given("the free memory is below the threshold")
	public void givenFreeMemoryIsBelowThreshold() {
		memoryHealthIndicator = new MemoryHealthIndicator() {
			@Override
			public Health health() {
				return Health.down()
						.withDetail("Free memory", 1000L + " bytes")
						.withDetail("Total memory", 5000L + " bytes")
						.withDetail("Free memory percent", 20.0 + " % but required at least 30%")
						.build();
			}
		};
	}

	@When("the health method is called")
	public void whenHealthMethodIsCalled() {
		if (serverPingHealthIndicator != null) {
			health = serverPingHealthIndicator.health();
		} else if (memoryHealthIndicator != null) {
			health = memoryHealthIndicator.health();
		}
	}

	@Then("the server status should be UP")
	public void thenServerStatusShouldBeUp() {
		assertEquals(Health.status("UP").build().toString(), health.getStatus().toString() + " {}");
	}

	@Then("the server status should be DOWN")
	public void thenServerStatusShouldBeDown() {
		assertEquals(Health.status("DOWN").build().toString(), health.getStatus().toString() + " {}");
	}

	@Then("the server status should be UP and the detail should be {string}")
	public void thenServerStatusShouldBeUpAndDetailShouldBe(String detail) {
		thenServerStatusShouldBeUp();
		assertTrue(health.getDetails().containsKey("Server status"));
		assertEquals(detail, health.getDetails().get("Server status"));
	}

	@Then("the server status should be DOWN and the detail should be {string}")
	public void thenServerStatusShouldBeDownAndDetailShouldBe(String detail) {
		thenServerStatusShouldBeDown();
		assertTrue(health.getDetails().containsKey("Server status"));
		assertEquals(detail, health.getDetails().get("Server status"));
	}
}



