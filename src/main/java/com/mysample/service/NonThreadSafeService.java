package com.mysample.service;

import org.springframework.stereotype.Service;

@Service
public class NonThreadSafeService {
    int count = 0;

    public int getCount() {
        return count;
    }

    public void increment() {
        count = count + 1;
    }
}
