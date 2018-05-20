package com.mysample.service;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;

/**
 * The service NonThreadSafeService is on purpose NOT thread safe. The test below proves it. Remove @Ignore for manual run only.
 *
 * Instead of always having a final count of NUMBER_OF_TASKS_TO_RUN, the actual result varies with every execution.
 * The reason is that we share a mutable variable upon different threads without synchronizing the access to this variable
 * which results in a race condition.
 */
public class NonThreadSafeServiceTest {

    private final static int THREAD_POOL_SIZE = 5;
    private final static int NUMBER_OF_TASKS_TO_RUN = 1000000;

    @Test @Ignore
    public void testIncrement() throws InterruptedException, ExecutionException {
        long startingTime = System.currentTimeMillis();

        NonThreadSafeService nonThreadSafeService = new NonThreadSafeService();

        Callable<NonThreadSafeService> task = () -> {
            nonThreadSafeService.increment();
            // Do assertions here if required
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

        long finishingTime = System.currentTimeMillis();
        System.out.print("It took milliseconds: ");
        System.out.println(finishingTime - startingTime);

        assertEquals(NUMBER_OF_TASKS_TO_RUN, nonThreadSafeService.getCount());
    }
}
