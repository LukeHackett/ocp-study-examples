package com.github.lukehackett.ocp.chapter4.func;

import java.util.function.BiFunction;

public class MyBiFunction implements BiFunction<String, String, Integer> {

    @Override
    public Integer apply(String a, String b) {
        return a.length() + b.length();
    }

    public static void main(String[] args) {
        BiFunction<String, String, Integer> stringLength = new MyBiFunction();

        System.out.println(stringLength.apply("foobar", "baz"));
    }

}
