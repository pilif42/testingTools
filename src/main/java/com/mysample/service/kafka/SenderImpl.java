package com.mysample.service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SenderImpl implements Sender {
    private static final Logger LOGGER = LoggerFactory.getLogger(SenderImpl.class);

    @Value("${kafka.topic.sender}")
    private String topicToSendTo;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void send(String payload) {
        LOGGER.info("sending payload = '{}'", payload);
        kafkaTemplate.send(topicToSendTo, payload);
    }
}
