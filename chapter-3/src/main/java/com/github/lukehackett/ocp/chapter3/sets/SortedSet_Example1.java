package com.github.lukehackett.ocp.chapter3.sets;

import java.util.Set;
import java.util.TreeSet;

class Animal {
    public String name;
    public int age;

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

public class SortedSet_Example1 {

    public static void main(String[] args) {
        // TreeSet will use the given comparator when sorting the set
        Set<String> names1 = new TreeSet<>((a,b) -> a.length() - b.length());
        names1.add("david");
        names1.add("ben");
        names1.add("graham");
        System.out.println(names1);

        // TreeSet will call object's compareTo method if a comparator is
        // not given
        Set<String> names2 = new TreeSet<>();
        names2.add("geoff");
        names2.add("sam");
        names2.add("peter");
        System.out.println(names2);

        // Throws a ClassCastException when adding an Animal.
        // "Animal cannot be cast to java.lang.Comparable".
        Set<Animal> animals = new TreeSet<>();
        animals.add(new Animal("geoff", 4));
        animals.add(new Animal("sam", 2));
        animals.add(new Animal("peter", 7));
        System.out.println(animals);
    }

}
