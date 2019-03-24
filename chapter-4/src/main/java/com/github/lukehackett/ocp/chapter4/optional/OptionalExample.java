package com.github.lukehackett.ocp.chapter4.optional;

import java.util.OptionalDouble;
import java.util.stream.DoubleStream;

public class OptionalExample {

    public static void main(String[] args) {
        DoubleStream ds = DoubleStream.empty();
        OptionalDouble opt = ds.findAny();

        System.out.println(opt.orElse(0));
        System.out.println(opt.orElseGet(() -> 1));
        System.out.println(opt.getAsDouble());  // Throws NoSuchElement when empty
    }

}
