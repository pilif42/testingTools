package com.mysample.service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Service
public class ReceiverImpl implements Receiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiverImpl.class);

    @Value("${kafka.topic.receiver}")
    private String topicToReadFrom;

    private CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return latch;
    }

    @KafkaListener(topics = "outputEvent")  // TODO Get this from topicToReadFrom
    @Override
    public void receive(String payload) {
        LOGGER.info("received payload = '{}'", payload);
        latch.countDown();
    }
}
