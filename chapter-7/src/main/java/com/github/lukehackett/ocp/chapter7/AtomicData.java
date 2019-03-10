package com.github.lukehackett.ocp.chapter7;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicData {
    public static AtomicInteger integer = new AtomicInteger(0);

    // Atomic Arrays can be initialised with an existing array or with the desired size of the array
    public static AtomicIntegerArray intArray = new AtomicIntegerArray(10);

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {

            int number = integer.incrementAndGet();

            intArray.getAndSet(i, number * 2);

            int intArrayNumber = intArray.incrementAndGet(i);

            System.out.println(number + " " + intArrayNumber);
        }
    }
}
