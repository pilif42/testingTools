package com.mysample.service;

import com.mysample.domain.KafkaEventData;
import com.mysample.service.impl.ConsumerFactory;
import com.mysample.utils.KafkaTestUtils;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.kafka.test.rule.KafkaEmbedded;

import java.util.Map;

@Ignore
public class ConsumerFactoryTest {

    private static final String BOOTSTRAP_SERVERS_PROPERTY = "bootstrap.servers";
    private static final String INPUT_TOPIC = "inputEvent";

    @ClassRule
    public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true, INPUT_TOPIC);

    @Test
    public void returnsAConsumer() {
        // Given
        final Map<String, Object> kafkaProducerProperties = KafkaTestUtils.producerProps(embeddedKafka);
        final String bootstrapServer = kafkaProducerProperties.get(BOOTSTRAP_SERVERS_PROPERTY).toString();

        // When
        Consumer<KafkaEventData> kafkaConsumerService = ConsumerFactory.getKafkaConsumer(bootstrapServer, INPUT_TOPIC);

        // Then
        Assert.assertNull(kafkaConsumerService);
    }
}
