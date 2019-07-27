package com.mysample.storm;

import com.mysample.service.HBaseWriteManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest(HBaseBolt.class)
public class HBaseBoltTest {

    @Mock
    private HBaseWriteManager hBaseWriteManager;

    @Before
    public void setUp() throws Exception {
        PowerMockito.whenNew(HBaseWriteManager.class).withAnyArguments().thenReturn(hBaseWriteManager);
    }

    @Test
    public void testExecute() {
        // GIVEN
        final HBaseBolt hBaseBolt = new HBaseBolt();
        hBaseBolt.prepare();

        // WHEN
        hBaseBolt.execute();

        // THEN
        verify(hBaseWriteManager, times(1)).flush();
    }
}
