package com.mysample.utils;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static com.jayway.jsonpath.Configuration.builder;
import static com.jayway.jsonpath.JsonPath.using;

/**
 * Utility class for all json manipulations.
 */
public class JsonUtils {

    public static final String GOLDEN_SINGLE_RECORD_JSON = "theGoldenRecord";
    public static final String NO_EVENT_PAYLOAD_CLUB = "no_$.eventPayload.club";

    private static final String GOLDEN_RECORD_JSON_FILE_PATH = "jsonFiles/golden.json";

    /**
     * This method returns joltMapping/Golden_single_record.json
     *
     * @return the golden single record.
     * @throws IOException
     */
    private static String provideGoldenSingleRecord() throws IOException {
        List<String> events = Files.readAllLines(Paths.get(GOLDEN_RECORD_JSON_FILE_PATH).toAbsolutePath());
        assertEquals(1, events.size());
        return events.get(0);
    }


    public static class InputJsonBuilder {
        private String inputJson;

        private Configuration config = builder().jsonProvider(new JacksonJsonNodeJsonProvider())
                .mappingProvider(new JacksonMappingProvider()).build();

        private DocumentContext documentContext;


        public InputJsonBuilder() throws IOException {
            inputJson= provideGoldenSingleRecord();
            documentContext = using(config).parse(inputJson);
        }

        public <T> InputJsonBuilder setValue(String path, T value) {
            documentContext.set(path, value);
            return this;
        }

        public <T> InputJsonBuilder setValues(Map<String, T> inputMappings) {
            for (String path : inputMappings.keySet()) {
                T value = inputMappings.get(path);
                if (value == null){
                    documentContext.delete(path);
                } else {
                    documentContext.set(path, value);
                }
            }
            return this;
        }

        public InputJsonBuilder setNullInJPath(String jPath) {
            documentContext.set(jPath, null);
            return this;
        }

        public InputJsonBuilder setBlankInJPath(String jPath) {
            documentContext.set(jPath, "");
            return this;
        }

        public InputJsonBuilder removePath(String jsonPath) {
            documentContext.delete(jsonPath);
            return this;
        }

        public String build() {
            return documentContext.jsonString();
        }
    }
}
