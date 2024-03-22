package com.epam.gymcrm.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;

@Component
public class ServerPingHealthIndicator implements HealthIndicator {

	private static final String SERVER_HOST = "localhost";

	@Override
	public Health health() {
		try {
			InetAddress address = InetAddress.getByName(SERVER_HOST);
			if (address.isReachable(3000)) { // pinging with timeout 3 seconds
				return Health.up().withDetail("Server status", "Server is reachable").build();
			} else {
				return Health.down().withDetail("Server status", "Server is unreachable").build();
			}
		} catch (IOException e) {
			return Health.down(e).withDetail("Server status", "Error while pinging server").build();
		}
	}
}