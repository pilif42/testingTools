package com.mysample.service;

import com.mysample.domain.AckMetadataWrapper;
import com.mysample.domain.KafkaAckMetadata;
import com.mysample.domain.NotificationData;
import com.mysample.domain.PublishingDTO;
import com.mysample.domain.Status;
import com.mysample.service.impl.PublisherImpl;
import org.apache.kafka.common.TopicPartition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PublisherImplTest {

    private static final String CONNECTION_ID = "some connId";
    private static final String TOPIC = "eventTopic";
    private static final int PARTITION = 1;
    private static final long OFFSET = 100L;

    @InjectMocks
    private PublisherImpl publisher;

    @Mock
    private ConsumerService consumerService;

    @Captor
    private ArgumentCaptor<AckMetadataWrapper<?>> argumentCaptor;

    @Test
    public void happyPath() {
        // Given
        final NotificationData notificationData = new NotificationData();
        notificationData.setStep("Enrichment");
        notificationData.setStatus(Status.COMPLETE);

        final TopicPartition topicPartition = new TopicPartition(TOPIC, PARTITION);
        final KafkaAckMetadata kafkaAckMetadata = KafkaAckMetadata.builder()
                .connectionId(CONNECTION_ID)
                .topicPartition(topicPartition)
                .offset(OFFSET)
                .build();
        final AckMetadataWrapper ackMetadataWrapper = AckMetadataWrapper.builder().ackMetadata(kafkaAckMetadata).build();


        final PublishingDTO publishingDTO = new PublishingDTO();
        publishingDTO.setNotificationData(notificationData);
        publishingDTO.setAckMetadataWrapper(ackMetadataWrapper);


        // When
        publisher.publish(publishingDTO);

        // Then
        verify(consumerService, times(1)).commit(argumentCaptor.capture());
        final AckMetadataWrapper<?> result = argumentCaptor.getValue();
        assertEquals(ackMetadataWrapper, result);
    }

}
