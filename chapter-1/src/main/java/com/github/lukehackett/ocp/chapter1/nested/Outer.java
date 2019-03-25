package com.github.lukehackett.ocp.chapter1.nested;

public class Outer {
    private String greeting = "Hello World!";

    protected class Inner {
        private int repeat = 3;

        public void go() {
            for (int i = 0; i < repeat; i++)
                System.out.println(greeting);
        }
    }

    public void callInner() {
        Inner inner = new Inner();
        System.out.println("Repeating greeting " + inner.repeat + " times.");
        inner.go();
    }

    public static void main(String[] args) {
        Outer outer = new Outer();
        outer.callInner();

        // Alternative way of initializing an inner class
        Inner inner = outer.new Inner();
        inner.go();
    }

}
