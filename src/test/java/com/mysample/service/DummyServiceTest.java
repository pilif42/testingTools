package com.mysample.service;

import ch.qos.logback.core.Appender;
import com.mysample.domain.Case;
import com.mysample.domain.repository.CaseRepository;
import com.mysample.service.impl.DummyServiceImpl;
import com.mysample.utils.JsonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.List;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.*;
import static com.mysample.utils.LoggingAssertion.assertLogging;
import static com.mysample.utils.LoggingAssertion.givenLoggingMonitored;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * A classic test. See DummyServiceParameterizedTest for a Parameterized unit test.
 */
@RunWith(MockitoJUnitRunner.class)
public class DummyServiceTest {

    private static final int ONE = 1;

    private static final String CLUB_PATH = "$.eventPayload.club";
    private static final String FROM_PATH = "$.eventPayload.from";
    private static final String TO_PATH = "$.eventPayload.to";
    private static final String POST_CODE_PATH = "$.eventPayload.postCode";

    @Mock
    private CaseRepository caseRepository;

    @InjectMocks
    private DummyServiceImpl dummyService;

    @Test
    public void testWithGoldenJson() throws IOException {
        Appender appender = givenLoggingMonitored();

        // Given - Read the input json file
        final String inputJson = new JsonUtils.InputJsonBuilder().build();

        // Convert input json canonical json
        when(caseRepository.updateCase(any(Case.class))).thenReturn(ONE);
        final String transformedJson = dummyService.transform(inputJson);

        // Then check the json path is not present in the transformed json
        verifyPathsAreAbsent(transformedJson, asList(FROM_PATH, TO_PATH, POST_CODE_PATH));
        assertLogging(appender).hasMessage("Entering transform ...");
    }

    @Test
    public void testWithJsonMissingEventPayloadClub() throws IOException {
        // Given - Read the input json file
        final String inputJson = new JsonUtils.InputJsonBuilder().removePath("eventPayload.club").build();

        // Convert input json canonical json
        when(caseRepository.updateCase(any(Case.class))).thenReturn(ONE);
        final String transformedJson = dummyService.transform(inputJson);

        // Then check the json path is not present in the transformed json
        verifyPathsAreAbsent(transformedJson, asList(FROM_PATH, TO_PATH, POST_CODE_PATH, CLUB_PATH));
    }

    private void verifyPathsAreAbsent(String transformedJson, List<String> paths) {
        for (String aPath : paths) {
            assertThat(transformedJson, isJson(withoutJsonPath(aPath)));
        }
    }
}