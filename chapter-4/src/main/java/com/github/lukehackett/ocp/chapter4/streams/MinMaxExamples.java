package com.github.lukehackett.ocp.chapter4.streams;

import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MinMaxExamples {

    public static void main(String[] args) {
        // Object streams require an comparator when calling min()
        Stream.of('c', 'b', 'a')
                .min((a,b) -> a - b)
                .ifPresent(printObject("min character ="));  // Returns the minimum value, a

        // Object streams require an comparator when calling max()
        Stream.of('c', 'b', 'a')
                .max((a,b) -> a - b)
                .ifPresent(printObject("max character ="));  // Returns the maximum value, c

        // Primitive streams do not accept a comparator when calling min()
        IntStream.rangeClosed(0, 4)
                .min()
                .ifPresent(printInteger("min integer ="));  // Returns the minimum value, 0

        // Primitive streams do not accept a comparator when calling max()
        IntStream.rangeClosed(0, 4)
                .max()
                .ifPresent(printInteger("max integer ="));  // Returns the minimum value, 0
    }

    private static Consumer<Object> printObject(String message) {
        return (object) -> System.out.println(message + " " + object);
    }

    private static IntConsumer printInteger(String message) {
        return (object) -> System.out.println(message + " " + object);
    }

}
