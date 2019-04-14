package com.github.lukehackett.ocp.chapter7.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicNumberArray {

    public static void main(String[] args) {
        AtomicIntegerArray array = new AtomicIntegerArray(4);

        array.set(0, 10);
        array.set(1, 20);

        int a1 = array.getAndAccumulate(0, 5, (int a, int b) -> a + b);
        int a2 = array.accumulateAndGet(0, 5, (int a, int b) -> a + b);
        System.out.println(a1 + " " + a2);  // prints 10 20

        int a3 = array.addAndGet(0, -10);
        System.out.println(a3);  // prints 10
    }

}
