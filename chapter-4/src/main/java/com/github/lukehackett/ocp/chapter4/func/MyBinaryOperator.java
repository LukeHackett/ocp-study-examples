package com.github.lukehackett.ocp.chapter4.func;

import java.util.function.BinaryOperator;

public class MyBinaryOperator implements BinaryOperator<Integer> {

    @Override
    public Integer apply(Integer v1, Integer v2) {
        return (v1 + v1) * 3 * 3;
    }

    public static void main(String[] args) {
        BinaryOperator<Integer> addAndTriple = new MyBinaryOperator();

        System.out.println(addAndTriple.apply(3, 3));
    }

}
