package com.mysample.service.eureka;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HostResolverImplITCase.TestConfig.class)
public class HostResolverImplITCase {

    private final static String VALID_SERVICE_NAME = "caseSvc";
    private final static String URL_SUFFIX = "sample.com";

    @Autowired
    private HostResolver hostResolver;

    @Ignore
    @Test
    public void happyPath() {
        String url = hostResolver.resolveHost(VALID_SERVICE_NAME);
        assertTrue(url.contains(URL_SUFFIX));
    }

    @Ignore
    @Test
    public void invalidServiceName() {
        String url = hostResolver.resolveHost("something");
        assertNull(url);
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableDiscoveryClient
    public static class TestConfig {
        @Bean
        public HostResolver hostResolver() {
            return new HostResolverImpl();
        }
    }
}
