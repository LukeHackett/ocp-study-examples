package com.github.lukehackett.ocp.chapter1.nested;

public class Enclosing {
    static class Nested {
        private int price = 6;
        private static int weight = 346;
    }

    public static void main(String[] args) {
        Nested nested = new Nested();
        System.out.println(nested.price);
        System.out.println(nested.weight);

        System.out.println(Nested.weight);
        System.out.println(Enclosing.Nested.weight);
    }
}
