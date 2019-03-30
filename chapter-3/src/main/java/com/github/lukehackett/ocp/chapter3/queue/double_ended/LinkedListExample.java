package com.github.lukehackett.ocp.chapter3.queue.double_ended;

import java.util.Deque;
import java.util.LinkedList;

public class LinkedListExample {

    public static void main(String[] args) {
        // LinkedLists are double-ended queues
        // LinkedLists are not as efficient as a "pure" queue, such as ArrayDeque
        Deque<Number> orderNumbers = new LinkedList<>();

        // offer() and add() both add to the back of the queue
        orderNumbers.offer(1);             // [1]
        orderNumbers.offer(2);             // [1,2]
        orderNumbers.add(3);                  // [1,2,3]

        // push will add to the front of the queue
        orderNumbers.push(4);              // [4,1,2,3]

        // element() returns the element or throws an exception if empty queue
        // peek() returns the next element or null
        // nether method removes the element from the queue
        orderNumbers.element();               // 4
        orderNumbers.peek();                  // 4

        // pop() removes and returns the element or throws an exception if empty queue
        // poll() removes and returns the next element or null
        orderNumbers.pop();                   // 4
        orderNumbers.poll();                  // 1

        // print the order numbers
        System.out.println(orderNumbers);     // [2,3]
    }

}
