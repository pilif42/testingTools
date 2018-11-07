package com.mysample.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Several options to make it thread-safe:
 *
 * Option 1 with synchronized
 * Option 2 with a Lock:ReentrantLock, ReadWriteLock, StampedLock
 * Option 3 with a Semaphore
 */
@Service
public class ThreadSafeService {

    private int count = 0;

    private ReentrantLock lock = new ReentrantLock();

    public int getCount() {
        return count;
    }

    /**
     * Option 1
     *
     * synchronized public void increment() {
     * count = count + 1;
     * }
     */

    /**
     * Option 2
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
