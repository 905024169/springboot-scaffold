/*
package com.ztech.core;

import com.ztech.collect.CollectionUtils;
import com.ztech.lang.ArrayUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class TableMap<K, V> implements Map<K, V>, Serializable {
    private static final long serialVersionUID = 1L;
    private List<K> keys;
    private List<V> values;

    public TableMap(int size) {
        this.keys = new ArrayList(size);
        this.values = new ArrayList(size);
    }

    public TableMap(K[] keys, V[] values) {
        this.keys = CollectionUtils.toList(keys);
        this.values = CollectionUtils.toList(values);
    }

    public int size() {
        return this.keys.size();
    }

    public boolean isEmpty() {
        return ArrayUtils.isEmpty(this.keys);
    }

    public boolean containsKey(Object key) {
        return this.keys.contains(key);
    }

    public boolean containsValue(Object value) {
        return this.values.contains(value);
    }

    public V get(Object key) {
        int index = this.keys.indexOf(key);
        return index > -1 && index < this.values.size() ? this.values.get(index) : null;
    }

    public V put(K key, V value) {
        this.keys.add(key);
        this.values.add(value);
        return null;
    }

    public V remove(Object key) {
        int index = this.keys.indexOf(key);
        if (index > -1) {
            this.keys.remove(index);
            if (index < this.values.size()) {
                this.values.remove(index);
            }
        }

        return null;
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        Iterator var2 = m.entrySet().iterator();

        while(var2.hasNext()) {
            java.util.Map.Entry<? extends K, ? extends V> entry = (java.util.Map.Entry)var2.next();
            this.put(entry.getKey(), entry.getValue());
        }

    }

    public void clear() {
        this.keys.clear();
        this.values.clear();
    }

    public Set<K> keySet() {
        return new HashSet(this.keys);
    }

    public Collection<V> values() {
        return new HashSet(this.values);
    }

    public Set<java.util.Map.Entry<K, V>> entrySet() {
        HashSet<java.util.Map.Entry<K, V>> hashSet = new HashSet();

        for(int i = 0; i < this.size(); ++i) {
            hashSet.add(new TableMap.Entry(this.keys.get(i), this.values.get(i)));
        }

        return hashSet;
    }

    private static class Entry<K, V> implements java.util.Map.Entry<K, V> {
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return this.key;
        }

        public V getValue() {
            return this.value;
        }

        public V setValue(V value) {
            throw new UnsupportedOperationException("setValue not supported.");
        }

        public final boolean equals(Object o) {
            if (o == this) {
                return true;
            } else if (!(o instanceof java.util.Map.Entry)) {
                return false;
            } else {
                java.util.Map.Entry<?, ?> e = (java.util.Map.Entry)o;
                return Objects.equals(this.key, e.getKey()) && Objects.equals(this.value, e.getValue());
            }
        }

        public int hashCode() {
            return Objects.hashCode(this.key) ^ Objects.hashCode(this.value);
        }
    }
}

*/
