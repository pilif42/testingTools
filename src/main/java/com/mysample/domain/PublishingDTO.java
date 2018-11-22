package com.mysample.domain;

import lombok.Data;

@Data
public class PublishingDTO {
    private NotificationData notificationData;
    private AckMetadataWrapper<?> ackMetadataWrapper;
}
