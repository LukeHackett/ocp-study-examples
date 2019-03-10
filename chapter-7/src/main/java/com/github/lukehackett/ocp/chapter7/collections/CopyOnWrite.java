package com.github.lukehackett.ocp.chapter7.collections;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWrite {

    public static void main(String[] args) {
        List<Integer> data = Arrays.asList(1, 2, 3, 4);
        List<Integer> list = new CopyOnWriteArrayList<>(data);

        for (int number : list) {
            System.out.println(number);
            list.add(number * 2);
        }

        for (int number : list) {
            System.out.println(number);
        }
    }
}
