/*
package com.ztech.collect;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import com.ztech.core.ArrayIter;
import com.ztech.core.EnumerationIter;
import com.ztech.framework.commons.utils.core.IterUtil;
import com.ztech.framework.commons.utils.core.convert.ConverterRegistry;
import com.ztech.framework.commons.utils.core.exception.UtilException;
import com.ztech.framework.commons.utils.lang.ArrayUtils;
import com.ztech.framework.commons.utils.lang.ClassUtils;
import com.ztech.framework.commons.utils.lang.StringUtils;
import com.ztech.framework.commons.utils.lang.TypeUtils;
import java.lang.reflect.Type;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Function;
import java.util.function.Predicate;
import org.springframework.cglib.core.ReflectUtils;

public class CollectionUtils extends org.apache.commons.collections.CollectionUtils {
    public CollectionUtils() {
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap();
        return (t) -> {
            return seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
        };
    }

    public static <K, V> HashMap<K, V> newHashMap() {
        return MapUtils.newHashMap();
    }

    public static <K, V> HashMap<K, V> newHashMap(int size) {
        return MapUtils.newHashMap(size);
    }

    @SafeVarargs
    public static <T> HashSet<T> newHashSet(T... ts) {
        return newHashSet(false, ts);
    }

    @SafeVarargs
    public static <T> LinkedHashSet<T> newLinkedHashSet(T... ts) {
        return (LinkedHashSet)newHashSet(true, ts);
    }

    @SafeVarargs
    public static <T> HashSet<T> newHashSet(boolean isSorted, T... ts) {
        if (null == ts) {
            return (HashSet)(isSorted ? new LinkedHashSet() : new HashSet());
        } else {
            int initialCapacity = Math.max((int)((float)ts.length / 0.75F) + 1, 16);
            HashSet<T> set = isSorted ? new LinkedHashSet(initialCapacity) : new HashSet(initialCapacity);
            Collections.addAll((Collection)set, ts);
            return (HashSet)set;
        }
    }

    public static <T> HashSet<T> newHashSet(Collection<T> collection) {
        return newHashSet(false, collection);
    }

    public static <T> HashSet<T> newHashSet(boolean isSorted, Collection<T> collection) {
        return (HashSet)(isSorted ? new LinkedHashSet(collection) : new HashSet(collection));
    }

    public static <T> HashSet<T> newHashSet(boolean isSorted, Iterator<T> iter) {
        if (null == iter) {
            return newHashSet(isSorted, (Object[])null);
        } else {
            Object set = isSorted ? new LinkedHashSet() : new HashSet();

            while(iter.hasNext()) {
                ((HashSet)set).add(iter.next());
            }

            return (HashSet)set;
        }
    }

    public static <T> HashSet<T> newHashSet(boolean isSorted, Enumeration<T> enumeration) {
        if (null == enumeration) {
            return newHashSet(isSorted, (Object[])null);
        } else {
            Object set = isSorted ? new LinkedHashSet() : new HashSet();

            while(enumeration.hasMoreElements()) {
                ((HashSet)set).add(enumeration.nextElement());
            }

            return (HashSet)set;
        }
    }

    public static <T> List<T> list(boolean isLinked) {
        return (List)(isLinked ? new LinkedList() : new ArrayList());
    }

    @SafeVarargs
    public static <T> List<T> list(boolean isLinked, T... values) {
        if (ArrayUtils.isEmpty(values)) {
            return list(isLinked);
        } else {
            List<T> arrayList = isLinked ? new LinkedList() : new ArrayList(values.length);
            Collections.addAll((Collection)arrayList, values);
            return (List)arrayList;
        }
    }

    public static <T> List<T> list(boolean isLinked, Collection<T> collection) {
        if (null == collection) {
            return list(isLinked);
        } else {
            return (List)(isLinked ? new LinkedList(collection) : new ArrayList(collection));
        }
    }

    public static <T> List<T> list(boolean isLinked, Iterable<T> iterable) {
        return null == iterable ? list(isLinked) : list(isLinked, iterable.iterator());
    }

    public static <T> List<T> list(boolean isLinked, Iterator<T> iter) {
        List<T> list = list(isLinked);
        if (null != iter) {
            while(iter.hasNext()) {
                list.add(iter.next());
            }
        }

        return list;
    }

    public static <T> List<T> list(boolean isLinked, Enumeration<T> enumration) {
        List<T> list = list(isLinked);
        if (null != enumration) {
            while(enumration.hasMoreElements()) {
                list.add(enumration.nextElement());
            }
        }

        return list;
    }

    @SafeVarargs
    public static <T> ArrayList<T> newArrayList(T... values) {
        return (ArrayList)list(false, values);
    }

    @SafeVarargs
    public static <T> ArrayList<T> toList(T... values) {
        return newArrayList(values);
    }

    public static <T> ArrayList<T> newArrayList(Collection<T> collection) {
        return (ArrayList)list(false, collection);
    }

    public static <T> ArrayList<T> newArrayList(Iterable<T> iterable) {
        return (ArrayList)list(false, iterable);
    }

    public static <T> ArrayList<T> newArrayList(Iterator<T> iter) {
        return (ArrayList)list(false, iter);
    }

    public static <T> ArrayList<T> newArrayList(Enumeration<T> enumration) {
        return (ArrayList)list(false, enumration);
    }

    @SafeVarargs
    public static <T> LinkedList<T> newLinkedList(T... values) {
        return (LinkedList)list(true, values);
    }

    public static <T> CopyOnWriteArrayList<T> newCopyOnWriteArrayList(Collection<T> collection) {
        return null == collection ? new CopyOnWriteArrayList() : new CopyOnWriteArrayList(collection);
    }

    public static <T> BlockingQueue<T> newBlockingQueue(int capacity, boolean isLinked) {
        BlockingQueue<T> queue = new ArrayBlockingQueue(capacity);
        return queue;
    }

    public static <T> BlockingQueue<T> newLinkedBlockingQueue(int capacity, boolean isLinked) {
        BlockingQueue<T> queue = new LinkedBlockingDeque(capacity);
        return queue;
    }

    public static <T> Collection<T> create(Class<?> collectionType) {
        Object list;
        if (collectionType.isAssignableFrom(AbstractCollection.class)) {
            list = new ArrayList();
        } else if (collectionType.isAssignableFrom(HashSet.class)) {
            list = new HashSet();
        } else if (collectionType.isAssignableFrom(LinkedHashSet.class)) {
            list = new LinkedHashSet();
        } else if (collectionType.isAssignableFrom(TreeSet.class)) {
            list = new TreeSet();
        } else if (collectionType.isAssignableFrom(EnumSet.class)) {
            list = EnumSet.noneOf(ClassUtils.getTypeArgument(collectionType));
        } else if (collectionType.isAssignableFrom(ArrayList.class)) {
            list = new ArrayList();
        } else if (collectionType.isAssignableFrom(LinkedList.class)) {
            list = new LinkedList();
        } else {
            try {
                list = (Collection)ReflectUtils.newInstance(collectionType);
            } catch (Exception var3) {
                throw new UtilException(var3);
            }
        }

        return (Collection)list;
    }

    public static <K, V> Map<K, V> createMap(Class<?> mapType) {
        return MapUtils.createMap(mapType);
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return MapUtils.isNotEmpty(map);
    }

    public static boolean isNotEmpty(Iterable<?> iterable) {
        return IterUtil.isNotEmpty(iterable);
    }

    public static boolean isNotEmpty(Iterator<?> Iterator) {
        return IterUtil.isNotEmpty(Iterator);
    }

    public static boolean isNotEmpty(Enumeration<?> enumeration) {
        return null != enumeration && enumeration.hasMoreElements();
    }

    public static boolean hasNull(Iterable<?> iterable) {
        return IterUtil.hasNull(iterable);
    }

    public static <T> String join(Iterable<T> iterable, CharSequence conjunction) {
        return IterUtil.join(iterable, conjunction);
    }

    public static <T> String join(Iterator<T> iterator, CharSequence conjunction) {
        return IterUtil.join(iterator, conjunction);
    }

    public static <E> Collection<E> toCollection(Iterable<E> iterable) {
        return (Collection)(iterable instanceof Collection ? (Collection)iterable : newArrayList(iterable.iterator()));
    }

    public static <K, V> Map<K, List<V>> toListMap(Iterable<? extends Map<K, V>> mapList) {
        return MapUtils.toListMap(mapList);
    }

    public static <T> ArrayList<T> distinct(Collection<T> collection) {
        if (isEmpty(collection)) {
            return new ArrayList();
        } else {
            return collection instanceof Set ? new ArrayList(collection) : new ArrayList(new LinkedHashSet(collection));
        }
    }

    public static <T> List<T> sub(List<T> list, int start, int end) {
        return sub((List)list, start, end, 1);
    }

    public static <T> List<T> sub(List<T> list, int start, int end, int step) {
        if (list == null) {
            return null;
        } else if (list.isEmpty()) {
            return new ArrayList(0);
        } else {
            int size = list.size();
            if (start < 0) {
                start += size;
            }

            if (end < 0) {
                end += size;
            }

            if (start == size) {
                return new ArrayList(0);
            } else {
                if (start > end) {
                    int tmp = start;
                    start = end;
                    end = tmp;
                }

                if (end > size) {
                    if (start >= size) {
                        return new ArrayList(0);
                    }

                    end = size;
                }

                if (step <= 1) {
                    return list.subList(start, end);
                } else {
                    List<T> result = new ArrayList();

                    for(int i = start; i < end; i += step) {
                        result.add(list.get(i));
                    }

                    return result;
                }
            }
        }
    }

    public static <T> List<T> sub(Collection<T> collection, int start, int end) {
        return sub((Collection)collection, start, end, 1);
    }

    public static <T> List<T> sub(Collection<T> list, int start, int end, int step) {
        return list != null && !list.isEmpty() ? sub((List)(new ArrayList(list)), start, end, step) : null;
    }

    public static <T> Collection<T> addAll(Collection<T> collection, Object value, Type elementType) {
        if (null != collection && null != value) {
            if (TypeUtils.isUnknow((Type)elementType)) {
                elementType = Object.class;
            }

            Object iter;
            if (value instanceof Iterator) {
                iter = (Iterator)value;
            } else if (value instanceof Iterable) {
                iter = ((Iterable)value).iterator();
            } else if (value instanceof Enumeration) {
                iter = new EnumerationIter((Enumeration)value);
            } else if (ArrayUtils.isArray(value)) {
                iter = new ArrayIter(value);
            } else if (value instanceof CharSequence) {
                String ArrayStr = StringUtils.unWrap((CharSequence)value, '[', ']');
                iter = StringUtils.splitTrim(ArrayStr, ',').iterator();
            } else {
                iter = newArrayList(value).iterator();
            }

            ConverterRegistry convert = ConverterRegistry.getInstance();

            while(((Iterator)iter).hasNext()) {
                collection.add(convert.convert((Type)elementType, ((Iterator)iter).next()));
            }

            return collection;
        } else {
            return collection;
        }
    }

    public static <T> List<T> setOrAppend(List<T> list, int index, T element) {
        if (index < list.size()) {
            list.set(index, element);
        } else {
            list.add(element);
        }

        return list;
    }

    public static <T> List<T> getAny(Collection<T> collection, int... indexes) {
        int size = collection.size();
        ArrayList<T> result = new ArrayList();
        int[] var5;
        int var6;
        int var7;
        int index;
        if (collection instanceof List) {
            List<T> list = (List)collection;
            var5 = indexes;
            var6 = indexes.length;

            for(var7 = 0; var7 < var6; ++var7) {
                index = var5[var7];
                if (index < 0) {
                    index += size;
                }

                result.add(list.get(index));
            }
        } else {
            Object[] array = collection.toArray();
            var5 = indexes;
            var6 = indexes.length;

            for(var7 = 0; var7 < var6; ++var7) {
                index = var5[var7];
                if (index < 0) {
                    index += size;
                }

                result.add(array[index]);
            }
        }

        return result;
    }

    public static <T> T getFirst(Iterable<T> iterable) {
        return IterUtil.getFirst(iterable);
    }

    public static <T> T getFirst(Iterator<T> iterator) {
        return IterUtil.getFirst(iterator);
    }

    public static boolean contains(Collection<?> collection, Object value) {
        return collection != null && collection.contains(value);
    }

    public static boolean containsAll(Collection<?> coll1, Collection<?> coll2) {
        if (!isEmpty(coll1) && !isEmpty(coll2) && coll1.size() >= coll2.size()) {
            Iterator var2 = coll2.iterator();

            Object object;
            do {
                if (!var2.hasNext()) {
                    return true;
                }

                object = var2.next();
            } while(coll1.contains(object));

            return false;
        } else {
            return false;
        }
    }

    public static <T> Map<T, Integer> countMap(Iterable<T> collection) {
        return IterUtil.countMap(collection);
    }
}
*/
