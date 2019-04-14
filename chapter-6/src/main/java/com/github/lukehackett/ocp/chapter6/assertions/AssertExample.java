package com.github.lukehackett.ocp.chapter6.assertions;

import java.util.function.Supplier;

public class AssertExample {

    static class Dog {
        String name;
        Dog(String name) { this.name = name; }
        public String toString() { return "My name is " + name; }
    }

    static Supplier<Dog> doggie = () -> new Dog("toby");

    public static int checkHomework(int choices) {

        assert choices++==10 : 1;

        assert true!=false : new StringBuilder("Answer 2");

        assert (null == null) : new Object();

        assert ++choices==11 : doggie.get();

        return choices;
    }

    public static void main(String[] args) {
        try {
            checkHomework(10);
        } catch (Error e) {
            System.out.println("A problem has occurred: " + e.getMessage());
            throw e;
        }
    }

}
