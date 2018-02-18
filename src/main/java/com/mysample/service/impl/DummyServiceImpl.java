package com.mysample.service.impl;

import com.mysample.service.DummyService;

public class DummyServiceImpl implements DummyService {

    @Override
    public String transform(String inputJson) {
        return inputJson;
    }
}
