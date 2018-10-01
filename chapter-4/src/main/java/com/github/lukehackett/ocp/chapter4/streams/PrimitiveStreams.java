package com.github.lukehackett.ocp.chapter4.streams;

import java.util.OptionalDouble;
import java.util.stream.IntStream;

public class PrimitiveStreams {

    public static void main(String[] args) {

        IntStream stream = IntStream.rangeClosed(1, 10);
        OptionalDouble optional = stream.average();

        System.out.println(optional.getAsDouble());
        System.out.println(optional.orElseGet(() -> Double.NaN));


    }
}
