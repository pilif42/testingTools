package com.mysample.service.impl;

import com.mysample.domain.AckMetadataWrapper;
import com.mysample.service.ConsumerService;
import org.springframework.stereotype.Service;

@Service
public class ConsumerServiceImpl implements ConsumerService {
    @Override
    public void commit(AckMetadataWrapper<?> ackMetadataWrapper) {
    }
}
