package com.abramova.test;

/**
 * Created by alina on 24.02.15.
 */
public class Runner {
    public static void main(String[] agrs) {
        MyHashMap myHashMap = new MyHashMap();
        myHashMap.put(12,1);
        myHashMap.put(19, 2);
        System.out.println("size " + myHashMap.size());
        myHashMap.put(8, 3);
        System.out.println(myHashMap.get(12));
        System.out.println(myHashMap.get(19));
        System.out.println(myHashMap.get(8));
        System.out.println("size "+myHashMap.size());

    }
}
