package com.github.lukehackett.ocp.chapter7.collections;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConcurrentModificationExamples {

    public static void main(String[] args) {
        List<Integer> list1 = new ArrayList<>(Arrays.asList(1,2,3));
        List<Integer> list2 = new CopyOnWriteArrayList<>(Arrays.asList(1,2,3));
        Deque<Integer> list3 = new ArrayDeque<>(Arrays.asList(1,2,3));
        List<Integer> list4 = Collections.synchronizedList(new ArrayList<>(Arrays.asList(1,2,3)));
        Map<Integer,Integer> map1 = new ConcurrentHashMap<>();
        map1.put(1, 2);
        map1.put(3, 3);

        try {
            for(Integer item : list1)
                list1.add(item * 2);
        } catch (ConcurrentModificationException e) {
            System.out.println("Unable to modify ArrayList");
        }

        try {
            for(Integer item : list2)
                list2.add(item * 2);
        } catch (ConcurrentModificationException e) {
            System.out.println("Unable to modify CopyOnWriteArrayList");
        }

        try {
            for(Integer item : list3)
                list3.offerFirst(item * 2);
        } catch (ConcurrentModificationException e) {
            System.out.println("Unable to modify ArrayDeque (offerFirst)");
        }

        try {
            for(Integer item : list3)
                list3.offer(item * 3);
        } catch (ConcurrentModificationException e) {
            System.out.println("Unable to modify ArrayDeque (offer)");
        }

        try {
            for(Integer item : list4)
                list4.add(item);
        } catch (ConcurrentModificationException e) {
            System.out.println("Unable to modify synchronizedList");
        }

        try {
            for(Integer key : map1.keySet())
                map1.remove(key);
        } catch (ConcurrentModificationException e) {
            System.out.println("Unable to modify ConcurrentHashMap");
        }

        System.out.println("list1 size " + list1.size());
        System.out.println("list2 size " + list2.size());
        System.out.println("list3 size " + list3.size());
        System.out.println("list4 size " + list4.size());
        System.out.println("map1  size " + map1.size());
    }

}
