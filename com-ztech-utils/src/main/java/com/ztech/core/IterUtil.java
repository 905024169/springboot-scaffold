/*
package com.ztech.core;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.ztech.core.func.Filter;
import com.ztech.framework.commons.utils.lang.ArrayUtils;
import com.ztech.framework.commons.utils.lang.StringUtils;
import com.ztech.framework.commons.utils.reflect.ReflectUtils;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class IterUtil {
    public IterUtil() {
    }

    public static boolean isEmpty(Iterable<?> iterable) {
        return null == iterable || isEmpty(iterable.iterator());
    }

    public static boolean isEmpty(Iterator<?> Iterator) {
        return null == Iterator || !Iterator.hasNext();
    }

    public static boolean isNotEmpty(Iterable<?> iterable) {
        return null != iterable && isNotEmpty(iterable.iterator());
    }

    public static boolean isNotEmpty(Iterator<?> Iterator) {
        return null != Iterator && Iterator.hasNext();
    }

    public static boolean hasNull(Iterable<?> iter) {
        return hasNull(null == iter ? null : iter.iterator());
    }

    public static boolean hasNull(Iterator<?> iter) {
        if (null == iter) {
            return true;
        } else {
            do {
                if (!iter.hasNext()) {
                    return false;
                }
            } while(null != iter.next());

            return true;
        }
    }

    public static boolean isAllNull(Iterable<?> iter) {
        return isAllNull(null == iter ? null : iter.iterator());
    }

    public static boolean isAllNull(Iterator<?> iter) {
        if (null == iter) {
            return true;
        } else {
            do {
                if (!iter.hasNext()) {
                    return true;
                }
            } while(null == iter.next());

            return false;
        }
    }

    public static <T> Map<T, Integer> countMap(Iterable<T> iter) {
        return countMap(null == iter ? null : iter.iterator());
    }

    public static <T> Map<T, Integer> countMap(Iterator<T> iter) {
        HashMap<T, Integer> countMap = new HashMap();
        if (null != iter) {
            while(iter.hasNext()) {
                T t = iter.next();
                Integer count = (Integer)countMap.get(t);
                if (null == count) {
                    countMap.put(t, 1);
                } else {
                    countMap.put(t, count + 1);
                }
            }
        }

        return countMap;
    }

    public static <K, V> Map<K, V> fieldValueMap(Iterable<V> iter, String fieldName) {
        return fieldValueMap(null == iter ? null : iter.iterator(), fieldName);
    }

    public static <K, V> Map<K, V> fieldValueMap(Iterator<V> iter, String fieldName) {
        Map<K, V> result = new HashMap();
        if (null != iter) {
            while(iter.hasNext()) {
                V value = iter.next();
                result.put(ReflectUtils.getFieldValue(value, fieldName), value);
            }
        }

        return result;
    }

    public static <K, V> Map<K, V> fieldValueAsMap(Iterable<?> iterable, String fieldNameForKey, String fieldNameForValue) {
        return fieldValueAsMap(null == iterable ? null : iterable.iterator(), fieldNameForKey, fieldNameForValue);
    }

    public static <K, V> Map<K, V> fieldValueAsMap(Iterator<?> iter, String fieldNameForKey, String fieldNameForValue) {
        Map<K, V> result = new HashMap();
        if (null != iter) {
            while(iter.hasNext()) {
                Object value = iter.next();
                result.put(ReflectUtils.getFieldValue(value, fieldNameForKey), ReflectUtils.getFieldValue(value, fieldNameForValue));
            }
        }

        return result;
    }

    public static <V> List<Object> fieldValueList(Iterable<V> iterable, String fieldName) {
        return fieldValueList(null == iterable ? null : iterable.iterator(), fieldName);
    }

    public static <V> List<Object> fieldValueList(Iterator<V> iter, String fieldName) {
        List<Object> result = new ArrayList();
        if (null != iter) {
            while(iter.hasNext()) {
                V value = iter.next();
                result.add(ReflectUtils.getFieldValue(value, fieldName));
            }
        }

        return result;
    }

    public static <T> String join(Iterable<T> iterable, CharSequence conjunction) {
        return null == iterable ? null : join(iterable.iterator(), conjunction);
    }

    public static <T> String join(Iterable<T> iterable, CharSequence conjunction, String prefix, String suffix) {
        return null == iterable ? null : join(iterable.iterator(), conjunction, prefix, suffix);
    }

    public static <T> String join(Iterator<T> iterator, CharSequence conjunction) {
        return join((Iterator)iterator, conjunction, (String)null, (String)null);
    }

    public static <T> String join(Iterator<T> iterator, CharSequence conjunction, String prefix, String suffix) {
        if (null == iterator) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            boolean isFirst = true;

            while(iterator.hasNext()) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    sb.append(conjunction);
                }

                T item = iterator.next();
                if (ArrayUtils.isArray(item)) {
                    sb.append(ArrayUtils.join(ArrayUtils.wrap(item), conjunction, prefix, suffix));
                } else if (item instanceof Iterable) {
                    sb.append(join((Iterable)item, conjunction, prefix, suffix));
                } else if (item instanceof Iterator) {
                    sb.append(join((Iterator)item, conjunction, prefix, suffix));
                } else {
                    sb.append(StringUtils.wrap(String.valueOf(item), prefix, suffix));
                }
            }

            return sb.toString();
        }
    }

    public static <K, V> HashMap<K, V> toMap(Iterable<Entry<K, V>> entryIter) {
        HashMap<K, V> map = new HashMap();
        if (isNotEmpty(entryIter)) {
            Iterator var2 = entryIter.iterator();

            while(var2.hasNext()) {
                Entry<K, V> entry = (Entry)var2.next();
                map.put(entry.getKey(), entry.getValue());
            }
        }

        return map;
    }

    public static <K, V> Map<K, V> toMap(Iterable<K> keys, Iterable<V> values) {
        return toMap(keys, values);
    }

    public static <K, V> Map<K, V> toMap(Iterator<K> keys, Iterator<V> values) {
        return toMap(keys, values);
    }

    public static <E> List<E> toList(Iterable<E> iter) {
        return null == iter ? null : toList(iter.iterator());
    }

    public static <E> List<E> toList(Iterator<E> iter) {
        ArrayList list = new ArrayList();

        while(iter.hasNext()) {
            list.add(iter.next());
        }

        return list;
    }

    public static <E> Iterator<E> asIterator(Enumeration<E> e) {
        return new EnumerationIter(e);
    }

    public static <E> Iterable<E> asIterable(Iterator<E> iter) {
        return () -> {
            return iter;
        };
    }

    public static <T> T getFirst(Iterable<T> iterable) {
        return null == iterable ? null : getFirst(iterable.iterator());
    }

    public static <T> T getFirst(Iterator<T> iterator) {
        return null != iterator && iterator.hasNext() ? iterator.next() : null;
    }

    public static Class<?> getElementType(Iterable<?> iterable) {
        if (null != iterable) {
            Iterator<?> iterator = iterable.iterator();
            return getElementType(iterator);
        } else {
            return null;
        }
    }

    public static Class<?> getElementType(Iterator<?> iterator) {
        Iterator<?> iter2 = new CopiedIter(iterator);
        if (iter2.hasNext()) {
            Object t = iter2.next();
            if (null != t) {
                return t.getClass();
            }
        }

        return null;
    }

    public static <T extends Iterable<E>, E> T filter(T iter, Filter<E> filter) {
        if (null == iter) {
            return null;
        } else {
            filter(iter.iterator(), filter);
            return iter;
        }
    }

    public static <E> Iterator<E> filter(Iterator<E> iter, Filter<E> filter) {
        if (null != iter && null != filter) {
            while(iter.hasNext()) {
                if (!filter.accept(iter.next())) {
                    iter.remove();
                }
            }

            return iter;
        } else {
            return iter;
        }
    }
}

*/
