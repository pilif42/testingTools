package com.mysample.service;

public class ThreadSafeService {
    int count = 0;

    public int getCount() {
        return count;
    }

    synchronized public void increment() {
        count = count + 1;
    }
}
