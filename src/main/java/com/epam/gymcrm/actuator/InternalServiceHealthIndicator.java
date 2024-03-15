package com.epam.gymcrm.actuator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
@Slf4j
public class InternalServiceHealthIndicator implements HealthIndicator {

	@Override
	public Health health() {
		boolean isInternalServiceAvailable = checkInternalServiceStatus();
		if (isInternalServiceAvailable) {
			return Health.up().withDetail("message", "Internal service is available").build();
		} else {
			return Health.down().withDetail("message", "Internal service is not available").build();
		}
	}

	private boolean checkInternalServiceStatus() {
		HttpURLConnection connection = null;
		try {
			URL url = new URL("http://localhost:5433/trainingType/get");
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(10000);
			connection.setReadTimeout(10000);
			int responseCode = connection.getResponseCode();
			return responseCode == HttpURLConnection.HTTP_OK;//true if response code is 200
		} catch (IOException e) {
			return false;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}
}