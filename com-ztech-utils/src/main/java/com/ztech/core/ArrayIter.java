package com.ztech.core;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIter<E> implements Iterator<E>, Iterable<E>, Serializable {
    private static final long serialVersionUID = 1L;
    private final Object array;
    private int startIndex;
    private int endIndex;
    private int index;

    public ArrayIter(Object array) {
        this(array, 0);
    }

    public ArrayIter(Object array, int startIndex) {
        this(array, startIndex, -1);
    }

    public ArrayIter(Object array, int startIndex, int endIndex) {
        this.endIndex = Array.getLength(array);
        if (endIndex > 0 && endIndex < this.endIndex) {
            this.endIndex = endIndex;
        }

        if (startIndex >= 0 && startIndex < this.endIndex) {
            this.startIndex = startIndex;
        }

        this.array = array;
        this.index = this.startIndex;
    }

    public boolean hasNext() {
        return this.index < this.endIndex;
    }

    public E next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        } else {
           // return Array.get(this.array, this.index++);
            return null;
        }
    }

    public void remove() {
        throw new UnsupportedOperationException("remove() method is not supported");
    }

    public Object getArray() {
        return this.array;
    }

    public void reset() {
        this.index = this.startIndex;
    }

    public Iterator<E> iterator() {
        return this;
    }
}
