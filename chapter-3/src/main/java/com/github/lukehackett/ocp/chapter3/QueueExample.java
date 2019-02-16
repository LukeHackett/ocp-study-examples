package com.github.lukehackett.ocp.chapter3;

import java.util.ArrayDeque;

public class QueueExample {

    public static void main(String[] args) {
        ArrayDeque<Integer> orderNumbers = new ArrayDeque<>();

        orderNumbers.offer(1);  // [1]
        orderNumbers.offer(2);  // [1, 2]

        orderNumbers.push(3);   // [3, 1, 2]

        orderNumbers.peek();       // returns 3  list=[3, 1, 2]
        orderNumbers.peek();       // returns 3  list=[3, 1, 2]

        orderNumbers.poll();       // returns 3  list=[1, 2]
        orderNumbers.poll();       // returns 1  list=[2]
        orderNumbers.poll();       // returns 2  list=[]
        orderNumbers.poll();       // returns null  list=[]
    }

}
