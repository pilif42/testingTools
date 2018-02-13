package com.mysample.config;

import com.mysample.service.DummyService;
import com.mysample.service.impl.DummyServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
public class TestConfiguration {
    @Bean
    public DummyService dummyService() {
        return new DummyServiceImpl();
    }
}
