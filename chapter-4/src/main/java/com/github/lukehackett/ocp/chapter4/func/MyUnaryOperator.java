package com.github.lukehackett.ocp.chapter4.func;

import java.util.function.UnaryOperator;

public class MyUnaryOperator implements UnaryOperator<Integer> {

    @Override
    public Integer apply(Integer value) {
        return value * 3 * 3;
    }

    public static void main(String[] args) {
        UnaryOperator<Integer> tripleIt = new MyUnaryOperator();

        System.out.println(tripleIt.apply(3));
    }

}
