package com.github.lukehackett.ocp.chapter3.compare;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class ComparableHashSet {

    static class Panda {
        String name;
        Panda(String name) { this.name = name; }

        // equals is used to determine whether or not two objects are the
        // same, and hence whether or not the object can be added to a Set.
        public boolean equals(Object obj) {
            if (obj == null)
                return false;

            if (obj instanceof Panda) {
                Panda other = (Panda) obj;
                return this.name.equalsIgnoreCase(other.name);
            }

            return false;
        }

        // hashCode() determines which "bucket" the object will reside in
        // the HashSet (which is a HashMap underneath).
        public int hashCode() { return 21; }

        public String toString() { return "" + name; }
    }

    public static void main(String[] args) {
        Set pandas = new HashSet<>();
        pandas.add(new Panda("Bao Bao"));
        pandas.add(new Panda("Mei Xiang"));
        pandas.add(new Panda("bao bao"));

        System.out.println(pandas);
    }

}
