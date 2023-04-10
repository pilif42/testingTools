package com.mysample.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Several options to make it thread-safe:
 *
 * Option 1 : make count a AtomicInteger and use count.incrementAndGet(); in increment()
 * Option 2 : synchronized
 * Option 3 : a Lock:ReentrantLock, ReadWriteLock, StampedLock
 * Option 4 : a java.util.concurrent.Semaphore
 */
@Service
public class ThreadSafeService {

    private int count = 0;

    private ReentrantLock lock = new ReentrantLock();

    public int getCount() {
        return count;
    }

    /**
     * Option 2
     * The lock behind the synchronized methods and blocks is a reentrant. This means the current thread
     * can acquire the same synchronized lock over and over again while holding it.
     *
     * public synchronized void increment() {
     * count = count + 1;
     * }
     */

    /**
     * Option 3
     */
    public void increment() {
        lock.lock();

        try {
            count = count + 1;
        } finally {
            // important to wrap your code into a try/finally block to ensure unlocking in case of exceptions.
            lock.unlock();
        }
    }
}
