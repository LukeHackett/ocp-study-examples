package com.github.lukehackett.ocp.chapter7.collections;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class BlockingDequeExample {

    public static void main(String[] args) {
        try {
            BlockingDeque<String> deque = new LinkedBlockingDeque<>();

            deque.offer("fox");
            deque.push("badger");

            deque.offerFirst("bear", 5, TimeUnit.SECONDS);
            deque.offerLast("tiger", 5, TimeUnit.MINUTES);

            System.out.println(deque.poll());                                  // prints bear
            System.out.println(deque.pollLast(1, TimeUnit.MINUTES));   // prints tiger
            System.out.println(deque.pollFirst(5, TimeUnit.HOURS));    // prints badger
            System.out.println(deque.poll());                                  // prints fox

            System.out.println(deque.pollLast(5, TimeUnit.SECONDS));   // prints null (after waiting 5 seconds)
            System.out.println(deque.pollFirst(5, TimeUnit.SECONDS));  // prints null (after waiting 5 seconds)

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
