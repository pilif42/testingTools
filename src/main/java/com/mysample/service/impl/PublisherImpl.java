package com.mysample.service.impl;

import com.mysample.domain.NotificationData;
import com.mysample.domain.PublishingDTO;
import com.mysample.service.ConsumerService;
import com.mysample.service.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This publisher could be involved at the end of the following process (possibly implemented with Spring Integration DSL):
 *      -1) a consumerService reads a message from Kafka
 *      -2) the message is processed
 *      -3) we then publish a notificationData to Kafka to confirm that all went fine
 *      -4) the consumerService can now commit the input message
 */
@Service
public class PublisherImpl implements Publisher {

    @Autowired
    private ConsumerService consumerService;

    @Override
    public void publish(final PublishingDTO publishingDTO) {
        final NotificationData notificationData = publishingDTO.getNotificationData();
        // TODO Publish the notificationData to Kafka for instance.

        consumerService.commit(publishingDTO.getAckMetadataWrapper());
    }
}
