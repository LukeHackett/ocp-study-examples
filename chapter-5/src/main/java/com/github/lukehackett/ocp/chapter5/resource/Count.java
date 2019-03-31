package com.github.lukehackett.ocp.chapter5.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.ResourceBundle;

public class Count extends ListResourceBundle {
    private int count = 0;

    @Override
    protected Object[][] getContents() {
        return new Object[][] {
            { "count", count++ },
            { "counter", new Count() },
            { "numbers", new ArrayList() }
        };
    }

    private int getAndIncrement() {
        return count++;
    }

    public static void main(String[] args) {
        // Obtain the resource bundle
        ResourceBundle rb = ResourceBundle.getBundle("com.github.lukehackett.ocp.chapter5.resource.Count");

        // Prints 0 0
        System.out.println(rb.getObject("count") + " " + rb.getObject("count"));

        // Prints 0 1
        System.out.println(
                ((Count) rb.getObject("counter")).getAndIncrement()
                + " " +
                ((Count) rb.getObject("counter")).getAndIncrement()
        );

        // Prints 0 0 (we are referencing two different objects)
        System.out.println(rb.getObject("count") + " " + rb.getObject("count"));

        // Obtain the array list and add some numbers to the list
        List<Integer> numbers = (List<Integer>) rb.getObject("numbers");
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);

        // Size is three, because we got a reference to the arraylist
        System.out.println("Array Size: " + ((List<Integer>) rb.getObject("numbers")).size());
    }

}
