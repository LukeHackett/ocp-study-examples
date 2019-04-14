package com.github.lukehackett.ocp.chapter7.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class RunnableCallableExecutor {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = null;

        try {
            executorService = Executors.newSingleThreadExecutor();
            List<Future<?>> futures = new ArrayList<>();

            executorService.execute(runnable("I'm an executed runnable"));

            futures.add(executorService.submit(runnable("I'm a submitted runnable")));
            futures.add(executorService.submit(callable("I'm a submitted callable")));

            // prints null (for the runnable), and the message for the callable
            for (Future<?> future : futures) {
                System.out.println("Submit Response: " + future.get());
            }

        } finally {
            if (executorService != null)
                executorService.shutdown();
        }
    }

    public static Runnable runnable(final String message) {
        return () -> System.out.println("[Runnable] " + message);
    }

    public static Callable<String> callable(final String message) {
        return () -> {
            System.out.println("[Callable] " + message);
            return message;
        };
    }

}
