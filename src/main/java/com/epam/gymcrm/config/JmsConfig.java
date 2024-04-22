package com.epam.gymcrm.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.region.policy.IndividualDeadLetterStrategy;
import org.apache.activemq.broker.region.policy.PolicyEntry;
import org.apache.activemq.broker.region.policy.PolicyMap;
import org.apache.activemq.network.NetworkConnector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class JmsConfig {

	@Bean
	public ActiveMQConnectionFactory connectionFactory(){
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL("tcp://localhost:61616");
		return connectionFactory;
	}

	@Bean
	public JmsTemplate jmsTemplate(){
		JmsTemplate template = new JmsTemplate();
		template.setConnectionFactory(connectionFactory());
		return template;
	}

	@Bean
	public BrokerService broker() throws Exception {
		BrokerService broker = new BrokerService();
		broker.addConnector("tcp://localhost:61616");

		IndividualDeadLetterStrategy deadLetterStrategy = new IndividualDeadLetterStrategy();
		deadLetterStrategy.setQueuePrefix("DLQ.");
		deadLetterStrategy.setUseQueueForQueueMessages(true);

		PolicyEntry policy = new PolicyEntry();
		policy.setDeadLetterStrategy(deadLetterStrategy);

		PolicyMap policyMap = new PolicyMap();
		policyMap.setDefaultEntry(policy);

		broker.setDestinationPolicy(policyMap);

		return broker;
	}

}