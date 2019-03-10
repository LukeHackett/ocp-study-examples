package com.github.lukehackett.ocp.chapter7.fork;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class MaximumNumberTask extends RecursiveTask<Double> {
    private double[] values;
    private int start;
    private int end;

    public MaximumNumberTask(double[] values, int start, int end) {
        this.values = values;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Double compute() {
        if (end - start <= 1) {
            return getLargest(values[start], values[end]);
        } else {
            int middle = start + ((end - start) / 2);

            MaximumNumberTask task1 = new MaximumNumberTask(values, start, middle);
            task1.fork();

            MaximumNumberTask task2 = new MaximumNumberTask(values, middle, end);
            double value2 = task2.compute();

            return getLargest(task1.join(), value2);
        }
    }

    private Double getLargest(double value1, double value2) {
        return value1 > value2 ? value1 : value2;
    }

    public static void main(String[] args) {
        double[] values = { 1.4, 5.7, 3.6, 7.7, 4.3, 2.8, 3.4 };
        RecursiveTask<Double> task = new MaximumNumberTask(values, 0, values.length - 1);

        ForkJoinPool pool = new ForkJoinPool();
        double max = pool.invoke(task);

        System.out.println("Maximum number is: " + max);
    }

}
