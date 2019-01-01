package com.github.lukehackett.ocp.chapter8;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HighLevelObjectStream extends IOBaseClass {

    public static class Animal implements Serializable{
        String name;
        int age = 0;
        String type = "UNKNOWN";
        transient int enclosureNumber = -1;

        public Animal() {}

        public Animal(String name, int age, String type, int enclosureNumber) {
            this.name = name;
            this.age = age;
            this.type = type;
            this.enclosureNumber = enclosureNumber;
        }

        @Override
        public String toString() {
            return String.format("{ name: %s, age: %s, type: %s, enclosureNumber: %s }", name, age, type, enclosureNumber);
        }
    }

    public void serialize(String destinationPath, Animal... animals) {
        File destination = new File(resourceDirectory, destinationPath);

        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(destination)))) {

            for (Animal animal : animals) {
                oos.writeObject(animal);
                oos.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Animal> deserialize(String sourcePath) {
        File source = new File(resourceDirectory, sourcePath);
        List<Animal> animals = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(source)))) {

            while (true) {
                try {
                    Object obj = ois.readObject();

                    if (obj instanceof Animal) {
                        animals.add((Animal) obj);
                    }
                } catch (EOFException eof) {
                    System.out.println("Read all data from: " + sourcePath);
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return animals;
    }

    public static void main(String[] args) {
        HighLevelObjectStream hlos = new HighLevelObjectStream();
        hlos.serialize("animals.data",
                new Animal("Terry", 7, "TIGER", 4),
                new Animal("Peter", 9, "PENGUIN", 16)
        );

        List<Animal> animals = hlos.deserialize("animals.data");

        System.out.println(animals);
    }

}
