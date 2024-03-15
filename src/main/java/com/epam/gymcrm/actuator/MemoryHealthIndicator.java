package com.epam.gymcrm.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class MemoryHealthIndicator implements HealthIndicator {
	@Override
	public Health health() {
		long freeMemory = Runtime.getRuntime().freeMemory();
		long totalMemory = Runtime.getRuntime().totalMemory();
		double freeMemoryPercent = ((double) freeMemory / totalMemory) * 100;
		if (freeMemoryPercent > 20) {
			return Health.up()
					.withDetail("Free memory", freeMemory + " bytes")
					.withDetail("Total memory", totalMemory + " bytes")
					.withDetail("Free memory percent", freeMemoryPercent + " %")
					.build();
		}
		else {
			return Health.down()
					.withDetail("Free memory", freeMemory + " bytes")
					.withDetail("Total memory", totalMemory + " bytes")
					.withDetail("Free memory percent", freeMemoryPercent + " % but required at least 20%")
					.build();
		}
	}
}
