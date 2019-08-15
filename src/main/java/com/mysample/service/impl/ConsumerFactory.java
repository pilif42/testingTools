package com.mysample.service.impl;

import com.mysample.domain.KafkaEventData;
import com.mysample.service.Consumer;

public class ConsumerFactory {
    public static Consumer<KafkaEventData> getKafkaConsumer(final String bootstrapServer, final String topic) {
        // TODO Based on the topic name, build the relevant consumer.
        // TODO If topic = inputEvent --> Consumer<KafkaEventData>
        // TODO If topic = inoutNotification --> Consumer<KafkaNotificationData>
        return null;
    }
}
