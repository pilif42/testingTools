package com.mysample.service;

import com.mysample.domain.Case;
import com.mysample.domain.repository.CaseRepository;
import com.mysample.service.impl.DummyServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.Collection;

import static com.mysample.service.impl.DummyServiceImpl.ZERO;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class DummyServiceParameterizedTest {
    @Rule
    public final MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private CaseRepository caseRepository;

    @InjectMocks
    private DummyServiceImpl dummyService;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "input", "input_Transformed" }, { "anotherStr", "anotherStr_Transformed" }
        });
    }

    @Parameterized.Parameter // first data value (0) is default
    public /* NOT private */ String inputString;

    @Parameterized.Parameter(1)
    public /* NOT private */ String outputString;

    @Test
    public void test() {
        when(caseRepository.updateCase(any(Case.class))).thenReturn(ZERO);

        assertEquals(outputString, dummyService.transform(inputString));
    }
}
