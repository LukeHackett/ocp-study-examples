package com.github.lukehackett.ocp.chapter7.streams;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

public class BasicExample {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        // Run test in serial fashion
        LocalTime start1 = LocalTime.now();
        numbers.stream().map(BasicExample::processRecord).count();
        LocalTime end1 = LocalTime.now();

        // Run test in parallel fashion
        LocalTime start2 = LocalTime.now();
        numbers.parallelStream().map(BasicExample::processRecord).count();
        LocalTime end2 = LocalTime.now();

        // Print out statistics
        System.out.println("Time taken to process serial = " + ChronoUnit.MILLIS.between(start1, end1));
        System.out.println("Time taken to process parallel = " + ChronoUnit.MILLIS.between(start2, end2));
    }

    private static int processRecord(int i) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // handle exception
        }

        return i;
    }

}
