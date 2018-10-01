package com.github.lukehackett.ocp.chapter4.func;

import java.util.function.BiConsumer;

public class MyBiConsumer implements BiConsumer<Integer, String> {

    @Override
    public void accept(Integer value, String message) {
        System.out.println(String.format(message, value, value * 2));
    }

    public static void main(String[] args) {
        BiConsumer<Integer, String> printMessage = new MyBiConsumer();

        printMessage.accept(4, "When you double %s, you will get %s!");
    }

}
