package com.github.lukehackett.ocp.chapter7.collections;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueExample {

    public static void main(String[] args) {
        try {
            BlockingQueue<String> queue = new LinkedBlockingQueue<>();

            queue.offer("foo");
            queue.offer("bar", 5, TimeUnit.SECONDS);

            System.out.println(queue.poll());  // prints foo
            System.out.println(queue.poll(5, TimeUnit.HOURS));    // prints bar
            System.out.println(queue.poll(2, TimeUnit.SECONDS));  // prints null (after waiting 2 seconds)

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
