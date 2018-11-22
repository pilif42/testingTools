package com.mysample.service;

import com.mysample.domain.AckMetadataWrapper;

public interface ConsumerService {
    void commit(final AckMetadataWrapper<?> ackMetadataWrapper);
}
