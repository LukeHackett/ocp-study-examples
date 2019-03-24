package com.github.lukehackett.ocp.chapter4.streams;

import java.util.IntSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class PrimitiveStreams {

    public static void main(String[] args) {
        // Inline Statistics
        OptionalDouble avg = IntStream.rangeClosed(1, 10).average();
        OptionalInt min = IntStream.rangeClosed(1, 10).min();
        OptionalInt max = IntStream.rangeClosed(1, 10).max();
        int sum = IntStream.rangeClosed(1, 10).sum();
        long count = IntStream.rangeClosed(1, 10).count();

        System.out.println(avg.getAsDouble());
        System.out.println(min.getAsInt());
        System.out.println(max.getAsInt());
        System.out.println(sum);
        System.out.println(count);

        // Summary Statistics
        IntSummaryStatistics statistics = IntStream.rangeClosed(1, 10).summaryStatistics();
        System.out.println(statistics.getAverage());
        System.out.println(statistics.getMin());
        System.out.println(statistics.getMax());
        System.out.println(statistics.getSum());
        System.out.println(statistics.getCount());

        // Double to Integer Example #1
        DoubleStream.of(1, 2, 3)
                .map(i -> i * i)
                .filter(i -> i > 5)
                .map(i -> (int) i)  // requires an explicit cast, otherwise does not compile
                .forEach(System.out::println);

        // Double to Integer Example #2
        DoubleStream.of(1, 2, 3)
                .map(i -> i * i)
                .filter(i -> i > 5)
                .mapToObj(i -> ((Double) i).intValue())  // requires an explicit cast, otherwise does not compile
                .forEach((i) -> System.out.println(i.getClass() + " " + i));


    }
}
