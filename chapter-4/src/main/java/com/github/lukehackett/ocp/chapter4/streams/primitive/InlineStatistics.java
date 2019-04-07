package com.github.lukehackett.ocp.chapter4.streams.primitive;

import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class InlineStatistics {

    public static void main(String[] args) {
        // Inline Statistics
        OptionalDouble avg = IntStream.rangeClosed(1, 10).average();
        OptionalInt min = IntStream.rangeClosed(1, 10).min();
        OptionalInt max = IntStream.rangeClosed(1, 10).max();
        int sum = IntStream.rangeClosed(1, 10).sum();
        long count = IntStream.rangeClosed(1, 10).count();

        // Obtaining Statistics inline will return optionals apart from sum and count
        System.out.println(avg.getAsDouble());
        System.out.println(min.getAsInt());
        System.out.println(max.getAsInt());
        System.out.println(sum);
        System.out.println(count);
    }
}
