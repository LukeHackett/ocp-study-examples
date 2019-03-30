package com.github.lukehackett.ocp.chapter3.compare;

import java.util.Arrays;
import java.util.Comparator;

public class SortExample_2 implements Comparator<String> {

    public int compare(String a, String b) {
        return b.toLowerCase().compareTo(a.toLowerCase());
    }


    public static void main(String[] args) {
        String[] values = {"123", "Abb", "aab"};

        Arrays.sort(values, new SortExample_2());

        // prints Abb aab 123
        for (String s : values)
            System.out.print(s + " ");
    }

}
