package com.github.lukehackett.ocp.chapter3.sets;

import java.util.TreeSet;
import java.util.stream.IntStream;

public class TreeSetExample {

    public static void main(String[] args) {
        // Generate a set of numbers from 1 to 20
        TreeSet<Integer> numbers = IntStream.rangeClosed(1, 20)
                .collect(TreeSet::new, TreeSet::add, TreeSet::addAll);

        System.out.println(numbers.lower(10));      // 9 (less than)
        System.out.println(numbers.floor(10));      // 10 (less than or equal to)
        System.out.println(numbers.ceiling(20));    // 20 (greater than or equal to)
        System.out.println(numbers.higher(20));     // null (greater than)
    }

}
