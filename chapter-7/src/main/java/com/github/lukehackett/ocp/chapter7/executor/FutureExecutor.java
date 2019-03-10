package com.github.lukehackett.ocp.chapter7.executor;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FutureExecutor {

    public static void main(String[] args) {

        ExecutorService executorService = null;

        try {
            executorService = Executors.newFixedThreadPool(4);

            Collection<Callable<Integer>> tasks = IntStream.iterate(1, i -> ++i)
                    .mapToObj(number -> (Callable<Integer>) () -> number * 2)
                    .limit(10)
                    .collect(Collectors.toList());

            List<Future<Integer>> results = executorService.invokeAll(tasks);

            for (Future<Integer> result : results) {
                System.out.println(result.get(5, TimeUnit.SECONDS));
            }

        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            if (executorService != null) {
                executorService.shutdown();
            }
        }


    }

}
