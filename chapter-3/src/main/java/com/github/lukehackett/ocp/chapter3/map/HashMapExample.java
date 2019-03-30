package com.github.lukehackett.ocp.chapter3.map;

import java.util.HashMap;
import java.util.Map;

public class HashMapExample {

    public static void main(String[] args) {
        Map<String, String> foods = new HashMap<>();
        foods.put("koala", "bamboo");
        foods.put("lion", "meat");
        foods.put("giraffe", "leaf");

        // Adds the key/value pair if the key does not already exist
        foods.putIfAbsent("lion", "zebra");

        // Merges the values into the given key, based upon the given merge
        // function. If the merge function returns null, then the key/value
        // pair is removed from the map.
        foods.merge("bear", "salmon", (existing, replacement) -> "fish");
        foods.merge("koala", "crunchy bamboos", (existing, replacement) -> replacement);
        foods.merge("lion", "zebra", (existing, replacement) -> null);

        System.out.println(foods);   // prints {koala=crunchy bamboos, giraffe=leaf, bear=salmon}
    }

}
