package com.github.lukehackett.ocp.chapter4.streams;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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


        Queue<Integer> q = new LinkedList<>();
        q.add(new Integer(6));
        q.add(new Integer(6));
        System.out.println(q.size() + " " + q.contains(6L));

    }



}
