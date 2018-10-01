package com.github.lukehackett.ocp.chapter4.func;

import java.util.function.Function;

public class MyFunction implements Function<String, Integer> {

    @Override
    public Integer apply(String value) {
        return value.length();
    }

    public static void main(String[] args) {
        Function<String, Integer> stringLength = new MyFunction();

        System.out.println(stringLength.apply("foobar"));
    }

}
