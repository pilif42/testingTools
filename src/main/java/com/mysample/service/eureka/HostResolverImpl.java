package com.mysample.service.eureka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

@Service
public class HostResolverImpl implements HostResolver {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Override
    public String resolveHost(String applicationName) {
        return this.discoveryClient.getInstances(applicationName).get(0).getHost();
    }
}
