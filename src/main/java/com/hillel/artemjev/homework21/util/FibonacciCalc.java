package com.hillel.artemjev.homework21.util;

public class FibonacciCalc {

    public long calc(int n) {
        long a = 0;
        long b = 1;

        for (int i = 2; i <= n; i++) {
            long next = a + b;
            a = b;
            b = next;
        }
        return b;
    }
}
