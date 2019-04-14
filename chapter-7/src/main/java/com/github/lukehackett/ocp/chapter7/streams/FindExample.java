package com.github.lukehackett.ocp.chapter7.streams;

import java.util.Arrays;
import java.util.List;

public class FindExample {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        Integer i1 = numbers.parallelStream()
                .findAny()
                .get();

        Integer i2 = numbers.stream()
                .parallel()
                .findFirst()
                .get();

        System.out.println(i1 + " " + i2);

        numbers.stream()
                .parallel()
                .skip(3)
                .limit(2)
                .forEach(FindExample::print); // skip, limit and findFirst behave the same on serial and parallel streams

    }

    private static void print(int number) {
        System.out.print(number + " ");
    }
}
