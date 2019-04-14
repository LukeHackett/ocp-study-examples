package com.github.lukehackett.ocp.chapter7.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class FuturesListExample {
    private static AtomicInteger monkey = new AtomicInteger(0);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = null;

        try {
            service = Executors.newSingleThreadExecutor();
            List<Future<?>> futures = new ArrayList<>();

            for (int i = 0; i < 100; i++)
                futures.add(service.submit(() -> monkey.getAndIncrement()));

            for (Future<?> future : futures) {
                System.out.print(future.get() + " ");
            }

        } finally {
            if (service != null)
                service.shutdown();
        }
    }
}
