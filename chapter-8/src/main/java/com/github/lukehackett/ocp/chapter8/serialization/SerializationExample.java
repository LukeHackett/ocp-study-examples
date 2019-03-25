package com.github.lukehackett.ocp.chapter8.serialization;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Mammal {
    protected transient String name;
    protected int age;

    public Mammal() {
        this.name = "Roger";
    }

    public void setName(String name) { this.name = name; }
    public String getName() { return this.name; }

    public void setAge(int age) { this.age = age; }
    public int getAge() { return this.age; }
}

class Whale extends Mammal implements Serializable {
    private String food;

    { this.name = "Lisa"; }

    public Whale() {
        this.name = "Moby";
    }

    public void setFood(String food) { this.food = food; }
    public String getFood() { return this.food; }
}

public class SerializationExample {

    public static void main(String[] args) throws Exception {
        try (OutputStream os = new FileOutputStream("whales.data");
             ObjectOutputStream oos = new ObjectOutputStream(os)) {

            // Create a Whale Instance
            Whale w1 = new Whale();
            w1.setName("geoff");
            w1.setAge(45);
            w1.setFood("Plankton");

            // Serialize whale Disk
            oos.writeObject(w1);
        }

        // Deserialize from Disk
        List<Whale> whales = new ArrayList<>();
        try (InputStream is = new FileInputStream("whales.data");
             ObjectInputStream ois = new ObjectInputStream(is)) {

            Object obj = null;
            while ((obj = ois.readObject()) != null) {
                if (obj instanceof Whale) {
                    Whale whale = (Whale) obj;
                    whales.add(whale);
                }
            }
        } catch (EOFException e) {
            System.out.println("End of File Reached!");
        }

        // Print out the serialized result
        // Will always be "roger" as Java will call the first non-serializable
        // no-argument parent class (Mammal) and will call the constructor. It
        // does not call the serializable's constructor or default initializers
        System.out.println("Name: " + whales.get(0).getName());

        // Age was not marked as "transient" but was not serialized to disk
        // because it's class - Mammal - did not implement serializable interface
        System.out.println("Age: " + whales.get(0).getAge());

        // Age was serialized to disk because it's class - Whale - implemented the
        // serializable interface
        System.out.println("Favourite Food: " + whales.get(0).getFood());
    }
}
