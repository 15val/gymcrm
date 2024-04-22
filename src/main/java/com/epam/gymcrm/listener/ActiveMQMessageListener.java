package com.epam.gymcrm.listener;

import com.epam.gymcrm.exception.FailedToModifyTrainingDurationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Slf4j
@Component
public class ActiveMQMessageListener {

	@JmsListener(destination = "trainingDurationResponseQueue")
	public void receiveResponse(String message) throws FailedToModifyTrainingDurationException {
		if (message.startsWith("Error:")) {
			log.error("Error from training duration microservice: {}", message);
			throw new FailedToModifyTrainingDurationException("Failed to modify training duration: " + message);
		} else if(message.startsWith("Success")) {
			log.info("Success from training duration microservice");
		}
	}

	@JmsListener(destination = "DLQ.trainingDurationQueue")
	public void receiveDlqMessage(String message) {
		log.error("DLQ.trainingDurationQueue received message: {}", message);
	}

	@JmsListener(destination = "DLQ.trainingDurationResponseQueue")
	public void receiveResponseDlqMessage(String message) {
		log.error("DLQ.trainingDurationResponseQueue received message: {}", message);
	}

}
