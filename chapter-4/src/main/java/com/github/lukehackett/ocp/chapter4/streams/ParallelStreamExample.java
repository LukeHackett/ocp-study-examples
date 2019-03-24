package com.github.lukehackett.ocp.chapter4.streams;

import java.util.Arrays;

public class ParallelStreamExample {

    public static void main(String[] args) {
        // Always finds the first element in the stream
        Integer i1 = Arrays.asList(1,2,3,4,5)
                .stream()
                .findAny()
                .get();

        // sorted() has no effect on a parallel stream, and hence any
        // value may be returned
        Integer i2 = Arrays.asList(6,7,8,9,10)
                .parallelStream()
                .sorted()
                .findAny()
                .get();

        System.out.println("i1 " + i1 + ", i2 " + i2);
    }

}
