package com.mysample.service;

import com.mysample.service.eureka.HostResolver;
import com.mysample.service.eureka.HostResolverImpl;
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
@SpringBootTest(classes = ResolverServiceITCase.TestConfig.class)
public class ResolverServiceITCase {
    private final static String VALID_SERVICE_NAME = "CPP-CPCD-V1-CURRENCY";
    private final static String URL_SUFFIX = "dev.discoverfinancial.com";

    @Autowired
    private HostResolver hostResolver;

    /**
     * Why the @Ignore :
     *      -1) See the javadoc at the class level.
     *      -2) VALID_SERVICE_NAME can vary so test too brittle to be run on Jenkins.
     */
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