package com.mysample.service;

import com.mysample.config.TestConfiguration;
import com.mysample.utils.JsonUtils;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static com.mysample.utils.JsonUtils.GOLDEN_SINGLE_RECORD_JSON;
import static com.mysample.utils.JsonUtils.NO_EVENT_PAYLOAD_TRAN_FROM;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.*;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertThat;

/**
 * An example of Parameterized unit test.
 * TODO Have it green bar
 */
@SpringBootTest(classes = {TestConfiguration.class})
@RunWith(Parameterized.class)
public class DummyServiceTest {

    private static final String TRANS_FROM_PATH = "$.payload.trans.from";
    private static final String TRANS_TO_PATH = "$.payload.trans.to";
    private static final String TRANS_POST_CODE_PATH = "$.payload.trans.postCode";

    @ClassRule
    public static final SpringClassRule SCR = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    @Autowired
    private DummyService dummyService;

    @Parameterized.Parameters(name = "{index}: Input json ''{0}'', paths {1} should not be present after transformation.")
    public static Collection<Object[]> data() {
        return asList(new Object[] [] {
                {GOLDEN_SINGLE_RECORD_JSON,
                        asList(TRANS_FROM_PATH, TRANS_TO_PATH, TRANS_POST_CODE_PATH)},
                {NO_EVENT_PAYLOAD_TRAN_FROM,
                        asList(TRANS_FROM_PATH, TRANS_TO_PATH, TRANS_POST_CODE_PATH)}});
    }

    @Parameterized.Parameter(0)
    public String inputJsonName;

    @Parameterized.Parameter(1)
    public List<String> paths;

    @Test
    public void shouldTestJsonTransformation() throws IOException {
        // Given - Read the input json file
        String inputJson = JsonUtils.provideInputJson(inputJsonName);

        // Convert input json canonical json
        final String transformedJson = dummyService.transform(inputJson);

        // Then check the json path is not present in the transformed json
        for (String aPath : paths) {
            assertThat(transformedJson, isJson(withoutJsonPath(aPath)));
        }
    }
}