package com.mysample.service;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;

/**
 * The service NonThreadSafeService is on purpose NOT thread safe. The test below proves it:
 * -1) The assertion assertEquals(1, countAtEnd - countAtStart) often fails especially when NUMBER_OF_TASKS_TO_RUN is high compared to the THREAD_POOL_SIZE.
 * -2) Instead of having a final count of 10000, the actual result varies with every execution. The reason is that we share a mutable variable upon different threads without synchronizing the access to this variable which results in a race condition.
 */
public class NonThreadSafeServiceTest {

    private final static int THREAD_POOL_SIZE = 5;
    private final static int NUMBER_OF_TASKS_TO_RUN = 100000;

    @Test
    public void testIncrement() throws InterruptedException, ExecutionException {
        NonThreadSafeService nonThreadSafeService = new NonThreadSafeService();

        Callable<NonThreadSafeService> task = () -> {
            int countAtStart = nonThreadSafeService.getCount();
            nonThreadSafeService.increment();
            int countAtEnd = nonThreadSafeService.getCount();
            assertEquals(1, countAtEnd - countAtStart);

            return nonThreadSafeService;
        };

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        List<Future<NonThreadSafeService>> serviceFutureList = new ArrayList<>();
        for(int j = 0; j < NUMBER_OF_TASKS_TO_RUN; j++) {
            serviceFutureList.add(executor.submit(task));
        }

        // waiting for all the tasks to be finished
        assertEquals(NUMBER_OF_TASKS_TO_RUN, serviceFutureList.size());
        for(Future<NonThreadSafeService> serviceFuture: serviceFutureList)      {
            serviceFuture.get();
        }

        System.out.println("Final count is " + nonThreadSafeService.getCount());
    }
}
