package com.mysample.service.impl;

import com.mysample.domain.Case;
import com.mysample.domain.repository.CaseRepository;
import com.mysample.service.DummyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DummyServiceImpl implements DummyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DummyServiceImpl.class);

    public static final int ZERO = 0;

    private static final String TRANSFORMED = "Transformed";

    @Autowired
    private CaseRepository caseRepository;


    @Override
    public String transform(String inputJson) {
        LOGGER.debug("Entering transform ...");
        if (caseRepository.updateCase(new Case()) == ZERO) {
            LOGGER.debug("About to transform inputJson...");
            inputJson = String.format("%s_%s", inputJson, TRANSFORMED);
        }
        return inputJson;
    }
}
