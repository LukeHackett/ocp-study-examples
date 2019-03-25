package com.github.lukehackett.ocp.chapter1.nested;

public class InnerClassExample {
    public class A {
        String foo = "bar";
        void say(String message) {  }

        // Does not compile - inner nested cannot have static fields
        // static int count = 5;

        // Does not compile  - inner nested cannot have static declarations
        // static void hello(){ }
    }

    protected class B {
        String foo = "bar";
        void say(String message) {  }

        // Does not compile - inner nested cannot have static fields
        // static int count = 5;

        // Does not compile  - inner nested cannot have static declarations
        // static void hello(){ }
    }

    private class C {
        String foo = "bar";
        void say(String message) {  }

        // Does not compile - inner nested cannot have static fields
        // static int count = 5;

        // Does not compile  - inner nested cannot have static declarations
        // static void hello(){ }
    }

    class D {
        String foo = "bar";
        void say(String message) {  }

        // Does not compile - inner nested cannot have static fields
        // static int count = 5;

        // Does not compile  - inner nested cannot have static declarations
        // static void hello(){ }
    }

    static class E {
        String foo = "bar";
        void say(String message) {  }

        static int count = 5;
        static void hello(){ }
    }

    public static void main(String[] args) {
        InnerClassExample example = new InnerClassExample();

        // compiles because it is public
        A a1 = example.new A();
        InnerClassExample.A a2 = example.new A();

        // compiles because it is protected
        B b1 = example.new B();
        InnerClassExample.B b2 = example.new B();

        // complies because we are referencing private modifier within it's
        // own class
        C c1 = example.new C();
        InnerClassExample.C c2 = example.new C();

        // compiles because it is package-default
        D d1 = example.new D();
        InnerClassExample.D d2 = example.new D();

        // Does not require a instance of "example" to create an instance as
        // it's static
        E e1 = new E();
        InnerClassExample.E e2 = new E();
    }
}

