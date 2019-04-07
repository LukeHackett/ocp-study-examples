package com.github.lukehackett.ocp.chapter4.streams.primitive;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MappingExample {

    public static void main(String[] args) {
        IntStream s1 = IntStream.empty();
        IntStream s2 = IntStream.of(66, 77, 88);

        Stream.of(s1, s2)
                .flatMapToInt(x -> x)  // cannot be flatMap as we are dealing with PrimitiveStreams
                .forEach(System.out::println);
    }
}
