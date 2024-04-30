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

	private static final String  HTTP_STATUS_OK = "200";
	private static final String  HTTP_STATUS_INTERNAL_SERVER_ERROR = "500";

	@JmsListener(destination = "trainingDurationResponseQueue")
	public void receiveResponse(String message) {
		if (message.equals(HTTP_STATUS_OK)) {
			log.info("Success from training duration microservice");
		} else if(message.equals(HTTP_STATUS_INTERNAL_SERVER_ERROR)) {
			log.error("Error from training duration microservice, failed to modify training duration: {}", message);
		}
	}

	@JmsListener(destination = "DLQ.trainingDurationQueue")
	public void receiveDlqMessage(String message) {
		log.error("DLQ.trainingDurationQueue received message: {}", message);
	}

}
