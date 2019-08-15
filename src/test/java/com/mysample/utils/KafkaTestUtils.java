package com.mysample.utils;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.DirectFieldAccessor;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Kafka testing utilities.
 */
public final class KafkaTestUtils {

    private KafkaTestUtils() {
    }

    /**
     * Set up test properties for a {@code <Integer, String>} consumer.
     *
     * @param embeddedKafka a {@link KafkaEmbedded} instance.
     * @param groupId the group id.
     * @param autoCommit the auto commit.
     * @return the properties.
     */
    public static Map<String, Object> consumerProps(KafkaEmbedded embeddedKafka, String groupId, String autoCommit) {
        return consumerProps(embeddedKafka.getBrokersAsString(), groupId, autoCommit);
    }

    /**
     * Set up test properties for a {@code <Integer, String>} consumer.
     *
     * @param brokers the bootstrapServers property.
     * @param groupId the group id.
     * @param autoCommit the auto commit.
     * @return the properties.
     */
    public static Map<String, Object> consumerProps(String brokers, String groupId, String autoCommit) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, autoCommit);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 15000);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }


    /**
     * Set up test properties for a {@code <Integer, String>} producer.
     *
     * @param embeddedKafka a {@link KafkaEmbedded} instance.
     * @return the properties.
     */
    public static Map<String, Object> producerProps(KafkaEmbedded embeddedKafka) {
        return producerProps(embeddedKafka.getBrokersAsString());
    }

    /**
     * Set up test properties for a {@code <Integer, String>} producer.
     *
     * @param brokers the bootstrapServers property.
     * @return the properties.
     */
    public static Map<String, Object> producerProps(String brokers) {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }

    /**
     * Poll the consumer, expecting a single record for the specified topic.
     *
     * @param consumer the consumer.
     * @param topic the topic.
     * @param <K> the key type.
     * @param <V> the value type.
     * @return the record.
     * @throws org.junit.ComparisonFailure if exactly one record is not received.
     */
    public static <K, V> ConsumerRecord<K, V> getSingleRecord(Consumer<K, V> consumer, String topic) {
        ConsumerRecords<K, V> received = getRecords(consumer);
        assertEquals(1, received.count());
        return received.records(topic).iterator().next();
    }

    /**
     * Poll the consumer for records.
     *
     * @param consumer the consumer.
     * @param <K> the key type.
     * @param <V> the value type.
     * @return the records.
     */
    public static <K, V> ConsumerRecords<K, V> getRecords(Consumer<K, V> consumer) {
        ConsumerRecords<K, V> received = consumer.poll(10000);
        assertNotNull(received);
        return received;
    }

    /**
     * Uses nested {@link DirectFieldAccessor}s to obtain a property using dotted notation to traverse fields; e.g.
     * "foo.bar.baz" will obtain a reference to the baz field of the bar field of foo. Adopted from Spring Integration.
     *
     * @param root The object.
     * @param propertyPath The path.
     * @return The field.
     */
    public static Object getPropertyValue(Object root, String propertyPath) {
        Object value = null;
        DirectFieldAccessor accessor = new DirectFieldAccessor(root);
        String[] tokens = propertyPath.split("\\.");
        for (int i = 0; i < tokens.length; i++) {
            value = accessor.getPropertyValue(tokens[i]);
            if (value != null) {
                accessor = new DirectFieldAccessor(value);
            }
            else if (i == tokens.length - 1) {
                return null;
            }
            else {
                throw new IllegalArgumentException("intermediate property '" + tokens[i] + "' is null");
            }
        }
        return value;
    }

    /**
     * A typed version of {@link #getPropertyValue(Object, String)}.
     *
     * @param root the object.
     * @param propertyPath the path.
     * @param type the type to cast the object to.
     * @param <T> the type.
     * @return the field value.
     * @see #getPropertyValue(Object, String)
     */
    @SuppressWarnings("unchecked")
    public static <T> T getPropertyValue(Object root, String propertyPath, Class<T> type) {
        Object value = getPropertyValue(root, propertyPath);
        if (value != null) {
            Assert.isAssignable(type, value.getClass());
        }
        return (T) value;
    }
}
