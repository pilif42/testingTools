package com.mysample.service.impl;

import com.mysample.domain.AckMetadataWrapper;
import com.mysample.domain.KafkaAckMetadata;
import com.mysample.service.ConsumerService;

public class EventConsumerService implements ConsumerService {

    @Override
    public void commit(final AckMetadataWrapper<?> ackMetadataWrapper) {
        final KafkaAckMetadata kafkaAckMetadata = (KafkaAckMetadata) ackMetadataWrapper.getAckMetadata();
        // TODO offsetManager.offer(kafkaAckMetadata);
    }
}
