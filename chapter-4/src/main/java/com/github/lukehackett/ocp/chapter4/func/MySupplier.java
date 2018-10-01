package com.github.lukehackett.ocp.chapter4.func;

import java.util.Random;
import java.util.function.Supplier;

public class MySupplier implements Supplier<Integer> {

    @Override
    public Integer get() {
        Random random = new Random();
        return random.nextInt();
    }

    public static void main(String[] args) {
        Supplier<Integer> integerSupplier = new MySupplier();
        Supplier<Double> doubleSupplier = Math::random;

        System.out.println("Random Integer = " + integerSupplier.get());
        System.out.println("Random Double = " + doubleSupplier.get());
    }

}
