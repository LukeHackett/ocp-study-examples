package com.github.lukehackett.ocp.chapter4.streams.collectors;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CombiningExample {

    public static void main(String[] args) {
        Stream<String> lower = Stream.of("a", "b", "c");
        Stream<String> upper = Stream.of("A", "B", "C");

        List<String> values = Stream.of(lower, upper)
                .flatMap(i -> i)
                .parallel()
                .collect(Collectors.toList());

        System.out.println(values);
    }

}
