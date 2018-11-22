package com.mysample.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.kafka.common.TopicPartition;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Builder
public class KafkaAckMetadata {
    private String connectionId;
    private TopicPartition topicPartition;
    private long offset;
}
