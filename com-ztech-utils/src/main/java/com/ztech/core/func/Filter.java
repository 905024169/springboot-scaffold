package com.ztech.core.func;

@FunctionalInterface
public interface Filter<T> {
    boolean accept(T var1);
}
