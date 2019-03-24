package com.github.lukehackett.ocp.chapter7.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class OutputExecutor {
    private static AtomicInteger monkey1 = new AtomicInteger(0);
    private static AtomicLong monkey2 = new AtomicLong(0);

    public static void main(String[] args) {
        ExecutorService service = null;

        try {
            service = Executors.newSingleThreadExecutor();

            for (int i = 0; i < 100; i++)
                service.submit(() -> monkey1.getAndIncrement());

            for (int i = 0; i < 100; i++)
                service.submit(() -> monkey2.incrementAndGet());

            // At this point the output is not known, as the service could be
            // still handling any number of the previous increment operations.
            // Remember the main method will run in it's own thread, and not
            // wait for the service executors to complete.
            // At some point in the future, it will print "100 100"
            System.out.println(monkey1 + " " + monkey2);
            System.out.println(monkey1 + " " + monkey2);
            System.out.println(monkey1 + " " + monkey2);
            System.out.println(monkey1 + " " + monkey2);

        } finally {
            if (service != null)
                service.shutdown();
        }
    }
}
