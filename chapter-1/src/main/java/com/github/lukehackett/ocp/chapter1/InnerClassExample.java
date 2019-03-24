package com.github.lukehackett.ocp.chapter1;

public class InnerClassExample {
    public class A {}

    protected class B {}

    private class C {}

    class D {}

    static class E {}

    public static void main(String[] args) {
        InnerClassExample example = new InnerClassExample();

        // compiles because it is public
        A a1 = example.new A();
        InnerClassExample.A a2 = example.new A();
        InnerClassExample.A a3 = example.new InnerClassExample.A();

        // compiles because it is protected
        B b1 = example.new B();
        InnerClassExample.B b2 = example.new B();
        InnerClassExample.B b3 = example.new InnerClassExample.B();

        // complies because we are referencing private modifier within it's
        // own class
        C c1 = example.new C();
        InnerClassExample.C c2 = example.new C();
        InnerClassExample.C c3 = example.new InnerClassExample.C();

        // compiles because it is package-default
        D d1 = example.new D();
        InnerClassExample.D d2 = example.new D();
        InnerClassExample.D d3 = example.new InnerClassExample.D();

        // Does not require a instance of "example" to create an instance as
        // it's static
        E e1 = new E();
        InnerClassExample.E e2 = new E();
        InnerClassExample.E e3 = new InnerClassExample.E();
    }
}

