package com.abramova.test;

/**
 * Created by alina on 24.02.15.
 */
public class Runner {
    public static void main(String[] args) {
        MyHashMap myHashMap = new MyHashMap();
        System.out.println(myHashMap);
        myHashMap.put(12, 1);
        myHashMap.put(19, 2);

        System.out.println(myHashMap);
        System.out.println("Size " + myHashMap.size());
        myHashMap.put(8, 3);
        myHashMap.put(11, 2);
        myHashMap.put(15, 2);
        myHashMap.put(1, 2);
        myHashMap.put(9, 2);
        System.out.println(myHashMap);
        System.out.println("Size "+myHashMap.size());

        System.out.println("For key = 12 value = " + myHashMap.get(12));
        System.out.println("For key = 19 value =  "+myHashMap.get(19));
        System.out.println("For key = 8 value = "+myHashMap.get(8));

    }
}
