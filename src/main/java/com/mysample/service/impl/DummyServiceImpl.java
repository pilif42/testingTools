package com.mysample.service.impl;

import com.mysample.domain.Case;
import com.mysample.domain.repository.CaseRepository;
import com.mysample.service.DummyService;
import org.springframework.beans.factory.annotation.Autowired;

public class DummyServiceImpl implements DummyService {

    public static final int ZERO = 0;

    private static final String TRANSFORMED = "Transformed";

    @Autowired
    private CaseRepository caseRepository;


    @Override
    public String transform(String inputJson) {
        if (caseRepository.updateCase(new Case()) == ZERO) {
            inputJson = String.format("%s_%s", inputJson, TRANSFORMED);
        }
        return inputJson;
    }
}
