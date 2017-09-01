package com.util;

public class RateQueue<T> {
    public void add(T t) {
        add(t, 0.5);
    }

    public void add(T t, double rate) {
        if (rate >= 1) {
            throw new IllegalArgumentException("Rate must be from 0 to 1");
        }
    }
}
