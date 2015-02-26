package com.abramova.test;

import java.util.NoSuchElementException;

/**
 * Simple Hash Map implementation.
 * Store pairs of elements (int,long).
 *
 * @author Alina Abramova
 * */
public class MyHashMap {
    /**
     * The array of entries
     * */
    private Entry[] buckets;
    /**
     * The number of entries in this MyHashMap
     * */
    private int size;
    /**
     * Default number of slots in the buckets
     * */
    private  int DEFAULT_INIT_CAPACITY = 16;

    /**
     * The number of slots in the buckets
     * */
    private int maxSize;


    /**
     * Constructs an empty MyHashMap with
     * @param capacity  the total number of slots in the buckets
     * */
    public MyHashMap(int capacity){
        maxSize = capacity;
        buckets = new Entry[capacity];
        size = 0;
    }
    /**
     * Constructs an empty MyHashMap with default values of capacity .
     * */
    public MyHashMap() {
        buckets = new Entry[DEFAULT_INIT_CAPACITY];
        maxSize = DEFAULT_INIT_CAPACITY;
        size = 0;
    }

    /**
     * Returns index for hash code
     * */
    int indexOf(int hash,int length) {
        return Math.abs(hash)%length;
        }

    /**
     * Put pair (key,value) to this MyHashMap.If the map  contains a value for the key, the old value is replaced
     * @param key used with computed hashes to access values
     * @param value - value for this key
     * */
    public long put(int key, long value){
        int index = indexOf(key,maxSize);
        int count = 0;
        if (size++ >= maxSize)
            resize(2 * maxSize);

        while(buckets[index] != null && buckets[index].getKey() != key){
            index = (index + 1) % size ;
            if(count == size)
                throw new NoSuchElementException("Table full");
            count++;
        }
        if (buckets[index]!=null){
            long oldValue = buckets[index].getValue();
            buckets[index]=new Entry(key,value);
            return oldValue;
        }
        buckets[index]=new Entry(key,value);
        return value;
    }




    /**
     * Rehashes the contents of this map into a new array with larger capacity.
     * */
    void  resize(int newCapacity){

        MyHashMap tmp = new MyHashMap(newCapacity);

        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] != null) {
               tmp.put(buckets[i].getKey(),buckets[i].getValue());
            }
        }
        maxSize = newCapacity;
        buckets = tmp.buckets;
    }


    /**
     * Returns the value to which the specified key is mapped
     * @param key key of element
     * @return value for this key
     * @throws java.util.NoSuchElementException if the specified key not found.
     * */
    public long get(int key) {
        int index = indexOf(key,buckets.length);
        int indexForInput = index;
        do {
            if (buckets[index] == null)
               throw new NoSuchElementException();
            else if (buckets[index].getKey() == key)
                return buckets[index].getValue();
            index = (index + 1) % buckets.length;
        } while (indexForInput != index);
        throw new NoSuchElementException();
        }


    /**
     * Return the number of entries in this MyHashMap
     * */
    public int size(){
        return size;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MyHashMap)) return false;

        MyHashMap myHashMap = (MyHashMap) o;

        if (size() != myHashMap.size()) return false;

        EntryIterator entryIterator = new EntryIterator();
        while (entryIterator.hasNext()){
            Entry e= entryIterator.next();
            if(e.getValue() != myHashMap.get(e.getKey())){
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
       int h = 0;
        EntryIterator entryIterator = new EntryIterator();
        while (entryIterator.hasNext()){
            h+=entryIterator.next().hashCode();
        }
        return h;
    }

    @Override
    public String toString() {
        String h = "";
        Entry e;
        EntryIterator entryIterator = new EntryIterator();
        while (entryIterator.hasNext()) {
            e = entryIterator.next();
            h += "( " + e.getKey() + ", " + e.getValue() + " );";
        }
        return h;

    }

    static class Entry{
     private long value;
        private final int key;
        Entry next;

    public Entry(int key, long value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public int hashCode() {
        return 37*key+8;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public int getKey() {
        return key;
    }
}



final class EntryIterator{
        Entry next;
        Entry current;
        int index;
        int count;


        EntryIterator(){
            count = size;
            Entry[] t = buckets;
            current = next = null;
            index = 0;
            if (t != null && size > 0) {
                do {} while (index < t.length && (next = t[index++]) == null);
            }
        }


            public  final boolean hasNext(){
                return next!=null;
            }

            final Entry next() {
                Entry[] t;
                Entry e = next;
                if (e == null)
                    throw new NoSuchElementException();
                current = e;
                next = e.next;
                if (next == null && (t = buckets) != null) {
                    do {} while (index < t.length && (next = t[index++]) == null);
                }
                return e;
            }
        }
    }
