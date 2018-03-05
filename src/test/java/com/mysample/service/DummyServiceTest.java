package com.mysample.service;

import com.mysample.service.impl.DummyServiceImpl;
import com.mysample.utils.JsonUtils;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import java.io.IOException;
import java.util.List;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.*;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertThat;

/**
 * An example of Parameterized unit test.
 */
@RunWith(MockitoJUnitRunner.class)
public class DummyServiceTest {

    private static final String CLUB_PATH = "$.eventPayload.club";
    private static final String FROM_PATH = "$.eventPayload.from";
    private static final String TO_PATH = "$.eventPayload.to";
    private static final String POST_CODE_PATH = "$.eventPayload.postCode";

    @ClassRule
    public static final SpringClassRule SCR = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    @InjectMocks
    private DummyServiceImpl dummyService;

    @Test
    public void testWithGoldenJson() throws IOException {
        // Given - Read the input json file
        final String inputJson = new JsonUtils.InputJsonBuilder().build();

        // Convert input json canonical json
        final String transformedJson = dummyService.transform(inputJson);

        // Then check the json path is not present in the transformed json
        verifyPathsAreAbsent(transformedJson, asList(FROM_PATH, TO_PATH, POST_CODE_PATH));
    }

    @Test
    public void testWithJsonMissingEventPayloadClub() throws IOException {
        // Given - Read the input json file
        final String inputJson = new JsonUtils.InputJsonBuilder().removePath("eventPayload.club").build();

        // Convert input json canonical json
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