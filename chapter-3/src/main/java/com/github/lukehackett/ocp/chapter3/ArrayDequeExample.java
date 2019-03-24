package com.github.lukehackett.ocp.chapter3;

import java.util.ArrayDeque;

public class ArrayDequeExample {

    public static void main(String[] args) {
        // ArrayDeque are double-ended queues
        // ArrayDeque are more efficient then LinkedLists
        ArrayDeque<Number> orderNumbers = new ArrayDeque<>();

        // offer() and add() both add to the back of the queue
        orderNumbers.offer(10);             // [10]
        orderNumbers.offer(20);             // [10,20]
        orderNumbers.add(30);                  // [10,20,30]

        // push will add to the front of the queue
        orderNumbers.push(40);              // [40,10,20,30]

        // element() returns the element or throws an exception if empty queue
        // peek() returns the next element or null
        // nether method removes the element from the queue
        orderNumbers.element();                // 40
        orderNumbers.peek();                   // 40

        // pop() removes and returns the element or throws an exception if empty queue
        // poll() removes and returns the next element or null
        orderNumbers.pop();                    // 40
        orderNumbers.poll();                   // 10

        // print the order numbers
        System.out.println(orderNumbers);      // [20,30]
    }

}
