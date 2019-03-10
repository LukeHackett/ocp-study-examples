package com.github.lukehackett.ocp.chapter7.fork;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class MaximumNumberAction extends RecursiveAction {
    private double[] values;
    private int start;
    private int end;

    public MaximumNumberAction(double[] values, int start, int end) {
        this.values = values;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if (end - start <= 1) {
            double largest = getLargest(values[start], values[end]);

            System.out.printf("Comparing %s to %s, and %s is larger\n", values[start], values[end], largest);

        } else {
            int middle = start + ((end - start) / 2);

            invokeAll(
                    new MaximumNumberAction(values, start, middle),
                    new MaximumNumberAction(values, middle, end)
            );
        }
    }

    private Double getLargest(double value1, double value2) {
        return value1 > value2 ? value1 : value2;
    }

    public static void main(String[] args) {
        double[] values = { 1.4, 5.7, 3.6, 7.7, 4.3, 2.8, 3.4 };
        MaximumNumberAction task = new MaximumNumberAction(values, 0, values.length - 1);

        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(task);
    }

}
