package com.github.lukehackett.ocp.chapter3.compare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortExample {

    static class Rabbit {
        int id;
        String name;
        public Rabbit(int id, String name) { this.id = id; this.name = name; }
        public String toString() { return "" + name; }
    }

    public static void main(String[] args) {
        List<Rabbit> bunnies = new ArrayList<>();
        bunnies.add(new Rabbit(1, "Snowball"));
        bunnies.add(new Rabbit(2, "Thumper"));
        bunnies.add(new Rabbit(3, "Bon Bon"));

        // Does not compile, as sort() accepts a List of Objects to which each
        // much implement the comparable interface
        // Collections.sort(bunnies);  // DOES NOT COMPILE

        // sort() accepts an optional comparator that will be used to sort the
        // elements.
        Collections.sort(bunnies, (a,b) -> b.id - a.id);

        // prints [Bon Bon, Thumper, Snowball]
        System.out.println(bunnies);

    }

}
