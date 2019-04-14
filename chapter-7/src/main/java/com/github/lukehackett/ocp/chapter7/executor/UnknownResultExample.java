package com.github.lukehackett.ocp.chapter7.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UnknownResultExample {
    private static int counter = 0;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = null;

        try {
            // When using not using a singleThreadPool, the order in which items are executed are not guaranteed.
            service = Executors.newCachedThreadPool();
            List<Future<?>> futures = new ArrayList<>();

            for (int i = 0; i < 100; i++)
                futures.add(service.submit(() -> UnknownResultExample.counter++));

            for (Future<?> future : futures) {
                System.out.print(future.get() + " ");
            }

        } finally {
            if (service != null)
                service.shutdown();
        }
    }
}
