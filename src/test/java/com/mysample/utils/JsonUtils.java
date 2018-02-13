package com.mysample.utils;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Utility class for all json manipulations.
 */
public class JsonUtils {

    public static final String GOLDEN_SINGLE_RECORD_JSON = "theGoldenRecord";
    public static final String NO_EVENT_PAYLOAD_TRAN_FROM = "no_$.eventPayload.tran.from";
    public static final String EMPTY_STRING_VALUE = "emptyString";
    public static final String NULL_VALUE = "nullValue";

    private static final String GOLDEN_RECORD_JSON_FILE_PATH = "jsonFiles/golden.json";
    private static final String NEGATIVE_PREFIX = "no_";

    /**
     * This method provides the input json based on the provided jsonName. Convention is as follow:
     *      - GOLDEN_SINGLE_RECORD_JSON for the golden single record
     *      - NO_EVENT_PAYLOAD_TRAN_ICC for the golden single record in which we delete the element at $.eventPayload.tran.icc
     *      - $.eventPayload.tran.avsResult.ntv,W for the golden single record in which we set $.eventPayload.tran.avsResult.ntv to "W"
     *
     * @param jsonName
     * @return
     * @throws IOException
     */
    public static String provideInputJson(String jsonName) throws IOException {
        String result;

        if (jsonName.equalsIgnoreCase(GOLDEN_SINGLE_RECORD_JSON)) {
            result = provideGoldenSingleRecord();
        } else {
            if (jsonName.startsWith(NEGATIVE_PREFIX)) {
                result = jsonGeneratorDelete(jsonName.substring(NEGATIVE_PREFIX.length()));
            } else {
                // amend mode, ie jsonName is a comma separated list of (path,value) pairs, See TODO below
                // as for now we assume that values are Strings.
                List<String> initialList = Arrays.asList(jsonName.split("\\s*,\\s*"));
                Map<String, String> pathValueMap = new HashMap<>();
                for (int i = 0; i < initialList.size(); i++) {
                    if ((i%2) == 0) {
                        pathValueMap.put(initialList.get(i), initialList.get(i + 1));
                    }
                }
                result = provideInputJson(pathValueMap);
            }
        }

        return result;
    }


    /**
     * This method reads joltMapping/Golden_single_record.json and amends value(s) of element(s).
     *
     * @param pathValueMap the map containing keys = paths to elements, values = new values for these elements
     * @return a Json String
     */
// TODO Make it more generic when required, ie values should be Object not String only.
    private static String provideInputJson(Map<String, String> pathValueMap) throws IOException {
        String goldenJson = provideGoldenSingleRecord();

        Configuration config = Configuration.builder().jsonProvider(new JacksonJsonNodeJsonProvider())
                .mappingProvider(new JacksonMappingProvider()).build();

        DocumentContext documentContext = JsonPath.using(config).parse(goldenJson);
        for (String path : pathValueMap.keySet()) {
            String value = pathValueMap.get(path);
            if (value != null && value.equalsIgnoreCase(EMPTY_STRING_VALUE)) {
                value = "";
            } else if (value != null && value.equalsIgnoreCase(NULL_VALUE)) {
                value = null;
            }
            documentContext.set(path, value);
        }

        return documentContext.json().toString();
    }

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


    /**
     * This method reads joltMapping/Golden_single_record.json and removes the elementPath .
     *
     * @param path path to the element we want to remove
     * @return
     */
    private static String jsonGeneratorDelete(String path) throws IOException {
        String goldenJson = provideGoldenSingleRecord();

        Configuration config = Configuration.builder().jsonProvider(new JacksonJsonNodeJsonProvider())
                .mappingProvider(new JacksonMappingProvider()).build();
        return JsonPath.using(config).parse(goldenJson).delete(path).json().toString();
    }
}
