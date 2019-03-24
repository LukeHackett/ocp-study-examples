package com.github.lukehackett.ocp.chapter7.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DeadlockExecutor {

    public static void main(String[] args) throws Exception {
        Object o1 = new Object();
        Object o2 = new Object();

        ExecutorService service = null;

        try {
            service = Executors.newSingleThreadExecutor();

            Future<?> f1 = service.submit(() -> {
                synchronized (o1) {
                    synchronized (o2) { System.out.println("Fox"); }
                }
            });

            Future<?> f2 = service.submit(() -> {
                synchronized (o2) {
                    synchronized (o1) { System.out.println("Hound"); }
                }
            });

            // This will always print out Fox then Hound as we are using a single threaded
            // executor, and hence the order of execution is guaranteed. Deadlock can never
            // occur because only one thread will be running at once.
            // If a non-single executor thread was used, then deadlock may occur.
            f1.get();
            f2.get();

        } finally {
            if (service != null)
                service.shutdown();
        }

    }

}
