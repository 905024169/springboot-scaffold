package com.ztech.core;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import java.io.Serializable;
import java.util.Enumeration;
import java.util.Iterator;

public class EnumerationIter<E> implements Iterator<E>, Iterable<E>, Serializable {
    private static final long serialVersionUID = 1L;
    private final Enumeration<E> e;

    public EnumerationIter(Enumeration<E> enumeration) {
        this.e = enumeration;
    }

    public boolean hasNext() {
        return this.e.hasMoreElements();
    }

    public E next() {
        return this.e.nextElement();
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

    public Iterator<E> iterator() {
        return this;
    }
}

