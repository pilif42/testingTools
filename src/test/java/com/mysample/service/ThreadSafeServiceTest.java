package com.mysample.service;

import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;

/**
 * Comparing this to NonThreadSafeServiceTest, this test always passes even when NUMBER_OF_TASKS_TO_RUN is huge compared
 * to THREAD_POOL_SIZE.
 *
 * It usually takes longer to run than NonThreadSafeServiceTest.
 *
 *
 * Note that the @DirtiesContext is not required here. I have put it here as a reminder that it solved for me an issue
 * encountered while testing with Wiremock: tests were running ok inside IntelliJ, ie all tests from one class at a time.
 * But, on the  * command line (./gradlew clean build), tests were failing due to requests to urls served by WireMock failing.
 *
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class ThreadSafeServiceTest {
    private final static int THREAD_POOL_SIZE = 5;
    private final static int NUMBER_OF_TASKS_TO_RUN = 1000000;

    @Test
    public void testIncrement() throws InterruptedException, ExecutionException {
        long startingTime = System.currentTimeMillis();

        ThreadSafeService threadSafeService = new ThreadSafeService();

        Callable<ThreadSafeService> task = () -> {
            threadSafeService.increment();
            // Do assertions here if required
            return threadSafeService;
        };

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        List<Future<ThreadSafeService>> serviceFutureList = new ArrayList<>();
        for(int j = 0; j < NUMBER_OF_TASKS_TO_RUN; j++) {
            serviceFutureList.add(executor.submit(task));
        }

        // waiting for all the tasks to be finished
        assertEquals(NUMBER_OF_TASKS_TO_RUN, serviceFutureList.size());
        for(Future<ThreadSafeService> serviceFuture: serviceFutureList)      {
            serviceFuture.get();
        }

        long finishingTime = System.currentTimeMillis();
        System.out.print("It took milliseconds: ");
        System.out.println(finishingTime - startingTime);

        assertEquals(NUMBER_OF_TASKS_TO_RUN, threadSafeService.getCount());
    }
}
