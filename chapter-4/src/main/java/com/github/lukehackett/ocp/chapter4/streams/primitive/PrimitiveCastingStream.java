package com.github.lukehackett.ocp.chapter4.streams.primitive;

import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class PrimitiveCastingStream {

    public static void main(String[] args) {
        // Double to Integer Example #1
        DoubleStream.of(1, 2, 3)
                .map(d -> d * d)
                .filter(d -> d > 5)
                .mapToInt(d -> (int) d)  // requires an explicit cast, otherwise does not compile
                .forEach(System.out::println);

        // Double to Integer Example #2
        DoubleStream.of(1, 2, 3)
                .map(d -> d * d)
                .filter(d -> d > 5)
                .mapToObj(d -> ((Double) d).intValue())  // requires an explicit cast, otherwise does not compile
                .forEach(d -> System.out.println(d.getClass() + " " + d));

        // Unboxing Example
        Stream.of(1, 2, 3)
                .mapToDouble(i -> i)  // ToDoubleFunction<Integer>
                .forEach(System.out::println);
    }
}
