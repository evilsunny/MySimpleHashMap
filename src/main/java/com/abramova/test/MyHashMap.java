package com.abramova.test;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class MyHashMap {
    private Entry[] buckets;
    private int size;
    private  int DEFAULT_INIT_CAPACITY = 16;
    private  float DEFAULT_LOAD_FACTOR = 0.75f;
    static final int MAXIMUM_CAPACITY = 1 << 30;
    private int threshold;

    public MyHashMap() {
        buckets = new Entry[DEFAULT_INIT_CAPACITY];
        size = 0;
        threshold = (int)(DEFAULT_LOAD_FACTOR*DEFAULT_LOAD_FACTOR);
    }

    int indexOf(int key,int length) {
        return Math.abs(key)%length;
        }

    public long put(int key, long value) {
        int index = indexOf(key,buckets.length);
        for (Entry e = buckets[index]; e!=null;e = e.next){
            if(e.key == key){
                long oldValue = e.value;
                e.value = value;
                return  oldValue;
            }
        }

        addEntry(key,value,index);
        return value;
    }

    void  addEntry(int key, long value, int bucketIndex) {
        Entry e = buckets[bucketIndex];
        buckets[bucketIndex] = new Entry(key, value, e);
        if (size++ >= threshold)
            resize(2 * buckets.length);

    }

    void  resize(int newCapacity) {
        Entry[] oldTable = buckets;
        int oldCapacity = oldTable.length;
        if (oldCapacity == MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return;
        }
        Entry[] newTable = new Entry[newCapacity];
        transfer(newTable);
        buckets = newTable;
        threshold = (int)(newCapacity * DEFAULT_LOAD_FACTOR);
    }

    void  transfer(Entry[] newTable) {
        Entry[] src = buckets;
        int newCapacity = newTable.length;
        for (int j = 0; j < src.length; j++) {
            Entry e = src[j];
            if (e != null) {
                src[j] = null;
                do {
                    Entry next = e.next;
                    int i = indexOf(e.hashCode(), newCapacity);
                    e.next = newTable[i];
                    newTable[i] = e;
                    e = next;
                } while (e != null);
            }
        }
    }



    public long get(int key) {
        int index = indexOf(key,buckets.length);
        long value = 0;
        if(buckets[index]==null){
           throw new NoSuchElementException();
        }else{
            for(Entry e = buckets[index]; e!=null; e = e.next){
                if(key==e.key){
                    value = e.value;
                }
            }
            return value;
        }
    }

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
            if(e.value != myHashMap.get(e.key)){
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

    class Entry{
        long value;
        final int key;
        Entry next;

        public Entry(int key, long value,Entry next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public int hashCode() {
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