package com.github.lukehackett.ocp.chapter3.queue;

import java.util.LinkedList;
import java.util.Queue;

public class Queue_Example {

    public static void main(String[] args) {
        // ArrayDeque are single-ended (Queue) and double-ended queues (Deque)
        // ArrayDeque are more efficient then LinkedLists
        Queue<Number> orderNumbers = new LinkedList<>();

        // offer() and add() both add to the back of the queue
        orderNumbers.offer(10);             // [10]
        orderNumbers.offer(20);             // [10,20]
        orderNumbers.add(30);                  // [10,20,30]

        // element() returns the element or throws an exception if empty queue
        // peek() returns the next element or null
        // nether method removes the element from the queue
        orderNumbers.element();                // 10
        orderNumbers.peek();                   // 10

        // pop() removes and returns the element or throws an exception if empty queue
        // poll() removes and returns the next element or null
        orderNumbers.poll();                   // 10

        // print the order numbers
        System.out.println(orderNumbers);      // [20,30]
    }
}
