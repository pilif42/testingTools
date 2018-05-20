package com.mysample.service;

public class NonThreadSafeService {
    int count = 0;

    public int getCount() {
        return count;
    }

    public void increment() {
        count = count + 1;
    }
}
