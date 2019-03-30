package com.github.lukehackett.ocp.chapter3.stack;

import java.util.ArrayDeque;
import java.util.Deque;

public class StackExample {

    public static void main(String[] args) {
        Deque<Integer> orderNumbers = new ArrayDeque<>();

        orderNumbers.push(1);  // [1]
        orderNumbers.push(2);  // [2, 1]

        orderNumbers.peek();      // returns 2  list=[2, 1]
        orderNumbers.peek();      // returns 2  list=[2, 1]

        orderNumbers.pop();       // returns 2  list=[2, 1]
        orderNumbers.pop();       // returns 1  list=[1]

        orderNumbers.pop();       // throws NoSuchElementException
    }

}
