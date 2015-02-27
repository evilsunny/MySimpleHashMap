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
     */
    private Entry[] buckets;
    /**
     * The number of entries in this MyHashMap
     */
    private int size;
    /**
     * Default number of slots in the buckets
     */
    private int DEFAULT_INIT_CAPACITY = 3;

    /**
     * The number of slots in the buckets
     */
    private int maxSize;


    /**
     * Constructs an empty MyHashMap with
     *
     * @param capacity the total number of slots in the buckets
     */
    public MyHashMap(int capacity) {
        int newCapacity = capacity;
        if (!isPrime(capacity)) {
            newCapacity = getNextSize(capacity);
        }
        maxSize = capacity;
        buckets = new Entry[newCapacity];
        size = 0;
    }

    /**
     * Constructs an empty MyHashMap with default values of capacity .
     */
    public MyHashMap() {
        buckets = new Entry[DEFAULT_INIT_CAPACITY];
        maxSize = DEFAULT_INIT_CAPACITY;
        size = 0;
    }

    /**
     * Returns index for hash code
     */
    int indexOf(int hash, int length) {
        return Math.abs(hash) % length;
    }

    /**
     * Calculates the step for
     * @param key of value for input
     * */
    int stepOf(int key) {
        return 5 - key % 5;
    }

    /**
     * Calculates new size for resizing, this size should be a first prime number after
     * @param min
     * for to avoid an infinite loop.
     * */
    private int getNextSize(int min) {
        for (int j = min + 1; true; j++) {
            if (isPrime(j))
                return j;
        }
    }

    /**
     * Checks is
     * @param num a prime number;
     * */
    private boolean isPrime(int num) {
        for (int j = 2; (j * j <= num); j++) {
            if (num % j == 0) {
                return false;
            }
        }
        return true;

    }

    /**
     * Put pair (key,value) to this MyHashMap.If the map  contains a value for the key, the old value is replaced
     *
     * @param key   used with computed hashes to access values
     * @param value - value for this key
     */
    public long put(int key, long value) {
        int index = indexOf(key, maxSize);
        int step = stepOf(key);
        int count = 0;

        while (buckets[index] != null && buckets[index].getKey() != key) {
            index = (index + step) % maxSize;

        }

        if (buckets[index] != null) {
            long oldValue = buckets[index].getValue();
            buckets[index] = new Entry(key, value);
            return oldValue;
        }
        buckets[index] = new Entry(key, value);
        if (++size >= maxSize)
            resize(getNextSize(maxSize));
        return value;
    }


    /**
     * Rehashes the contents of this map into a new array with larger capacity.
     */
    void resize(int newCapacity) {

        MyHashMap tmp = new MyHashMap(newCapacity);

        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] != null) {
                tmp.put(buckets[i].getKey(), buckets[i].getValue());
            }
        }
        maxSize = newCapacity;
        buckets = tmp.buckets;
    }


    /**
     * Returns the value to which the specified key is mapped
     *
     * @param key key of element
     * @return value for this key
     * @throws java.util.NoSuchElementException if the specified key not found.
     */
    public long get(int key) {
        int index = indexOf(key, maxSize);
        int step = stepOf(key);

        int indexForInput = index;
        do {
            if (buckets[index] == null)
                throw new NoSuchElementException();
            else if (buckets[index].getKey() == key)
                return buckets[index].getValue();
            index = (index + step) % maxSize;
        } while (indexForInput != index);
        throw new NoSuchElementException();
    }


    /**
     * Return the number of entries in this MyHashMap
     */
    public int size() {
        return size;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MyHashMap)) return false;

        MyHashMap myHashMap = (MyHashMap) o;

        if (size() != myHashMap.size()) return false;

        for (int i = 0; i < maxSize; i++) {
            if (buckets[i] != null) {
            if(buckets[i].getValue()!=myHashMap.get(buckets[i].getKey()))
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int h = 0;
        for (int i = 0; i < maxSize; i++) {
            if (buckets[i] != null) {
                h += buckets[i].hashCode();
            }
        }
        return h;
    }

    @Override
    public String toString() {
        String h = "";

        for (int i = 0; i < maxSize; i++) {
            if (buckets[i] != null) {
                h += "(" + buckets[i].getKey() + ", " + buckets[i].getValue() + ");";
            }
        }
        return h;
    }

    static class Entry {
        private long value;
        private final int key;
        Entry next;

        public Entry(int key, long value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public int hashCode() {
            return 37 * key + 8;
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



    }
