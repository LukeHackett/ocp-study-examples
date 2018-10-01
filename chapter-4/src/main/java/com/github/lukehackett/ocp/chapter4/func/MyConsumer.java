package com.github.lukehackett.ocp.chapter4.func;

import java.util.function.Consumer;

public class MyConsumer implements Consumer<Integer> {

    @Override
    public void accept(Integer value) {
        System.out.println(value * 2);
    }

    public static void main(String[] args) {
        Consumer<Integer> doubleIt = new MyConsumer();
        Consumer<String> printIt = System.out::println;

        doubleIt.accept(7);
        printIt.accept("Hello World!");
    }

}
