package com.github.lukehackett.ocp.chapter3.lists;

import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ListExample {

    public static void main(String[] args) {
        // Generate a set of numbers from 1 to 5
        List<Integer> numbers = IntStream.rangeClosed(1, 5)
                .boxed()
                .collect(Collectors.toList());

        numbers.replaceAll(i -> i * 2);

        System.out.println(numbers);
    }

}
