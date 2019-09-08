package com.mysample.service.kafka;

public interface Receiver {
    void receive(String payload);
}
