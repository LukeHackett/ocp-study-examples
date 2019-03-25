package com.github.lukehackett.ocp.chapter1;

import java.util.Comparator;
import java.util.List;

public class InstanceOfExample {

    interface Furry { }

    class Chipmunk { }

    class FurryChipmunk implements Furry { }

    public static void main(String[] args) {
        Chipmunk c = new InstanceOfExample().new Chipmunk();

        // This compiles because all interfaces can be implemented at run time,
        // and thus Chipmunk could become Furry at runtime.
        if (c instanceof Furry)
            System.out.println("is a furry");

        // A List is an Interface so this compiles!
        if (c instanceof List)
            System.out.println("is a List");

        // Likewise, it could also become another instance of a random interface
        if (c instanceof Comparator)
            System.out.println("is a comparator");

        if (c instanceof Chipmunk)
            System.out.println("is a chipmunk");

        if (c instanceof Object)
            System.out.println("is an object");

        // fails to compile, as Chipmunk class is not a child or parent of FurryChipmunk
        // if (c instanceof FurryChipmunk)
        //     System.out.println("is a number");

        if (null instanceof FurryChipmunk)  // null instanceof anything is always false
            System.out.println("is a furry chipmunk");

    }

}
