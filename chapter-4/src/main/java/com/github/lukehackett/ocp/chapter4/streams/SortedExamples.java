package com.github.lukehackett.ocp.chapter4.streams;

import java.util.function.Consumer;
import java.util.stream.Stream;

public class SortedExamples {

    public static void main(String[] args) {
        Stream.of('c', 'b', 'a')
                .sorted()
                .findAny()
                .ifPresent(print("findAny() ="));

        Stream.of('c', 'b', 'a')
                .sorted()
                .findFirst()
                .ifPresent(print("findFirst() ="));
    }

    private static Consumer<Object> print(String message) {
        return (object) -> System.out.println(message + " " + object);
    }

}
