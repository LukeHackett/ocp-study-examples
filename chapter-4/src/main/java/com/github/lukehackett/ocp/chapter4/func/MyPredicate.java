package com.github.lukehackett.ocp.chapter4.func;

import java.util.function.Predicate;

public class MyPredicate implements Predicate<Integer> {

    @Override
    public boolean test(Integer value) {
        return value > 5;
    }

    public static void main(String[] args) {
        Predicate<Integer> isGreaterThanFive = new MyPredicate();

        System.out.println("3 is greater than 5 (negated) = " + isGreaterThanFive.negate().test(3));
        System.out.println("3 is not greater than 5 = " + isGreaterThanFive.test(3));
    }

}
