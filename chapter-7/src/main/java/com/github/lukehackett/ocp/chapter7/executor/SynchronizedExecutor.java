package com.github.lukehackett.ocp.chapter7.executor;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SynchronizedExecutor {
    private static final int THREAD_COUNT = 4;
    private static final int TASK_COUNT = 10;

    private static final Random random = new Random();
    private static int count = 0;

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void increment() {
        System.out.print(++count + " ");
        sleep(random.nextInt(500));
    }

    public synchronized static void incrementSync() {
        System.out.print(++count + " ");
        sleep(random.nextInt(500));
    }

    public static void execute(ExecutorService executorService, boolean sync) {
        // Create some tasks
        for (int i = 0; i < TASK_COUNT; i++) {
            if (sync) {
                executorService.execute(SynchronizedExecutor::incrementSync);
            } else {
                executorService.execute(SynchronizedExecutor::increment);
            }
        }

        // Shutdown the executor service and wait for upto 5 minutes
        try {
            executorService.shutdown();
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("\nResetting count to 0 \n");
            count = 0;
        }
    }

    public static void main(String[] args) {
        System.out.println("Executing with newFixedThreadPool (sync=false)");
        execute(Executors.newFixedThreadPool(THREAD_COUNT), false);

        System.out.println("Executing with newFixedThreadPool (sync=true)");
        execute(Executors.newFixedThreadPool(THREAD_COUNT), true);

        System.out.println("Executing with newSingleThreadExecutor (sync=false)");
        execute(Executors.newSingleThreadExecutor(), false);

        System.out.println("Executing with newSingleThreadExecutor (sync=true)");
        execute(Executors.newSingleThreadExecutor(), true);
    }

}
