package com.github.lukehackett.ocp.chapter3.compare;

import java.util.Set;
import java.util.TreeSet;

public class ComparableTreeSet {

    static class Panda implements Comparable<Panda> {
        String name;
        Panda(String name) { this.name = name; }

        // A SortedSet (e.g. TreeSet and ConcurrentSkipListSet) requires all
        // elements to implement the Comparable interface or pass a comparator
        // when initializing the TreeSet.
        // A runtime exception is thrown if not implemented "Panda cannot be
        // cast to java.lang.Comparable"
        public int compareTo(Panda o) {
            return name.compareTo(o.name);
        }
    }

    public static void main(String[] args) {
        Set pandas = new TreeSet<>();
        pandas.add(new Panda("Bao Bao"));
        pandas.add(new Panda("Mei Xiang"));
        pandas.add(new Panda("Bao Bao"));

        System.out.println("Total number of Pandas: " + pandas.size());
    }

}
