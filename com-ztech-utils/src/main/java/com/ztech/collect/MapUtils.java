/*
package com.ztech.collect;

import com.ztech.core.TypeReference;
import com.ztech.core.convert.Convert;
import com.ztech.core.func.Editor;
import com.ztech.core.func.Filter;
import com.ztech.core.map.CamelCaseLinkedMap;
import com.ztech.core.map.CamelCaseMap;
import com.ztech.lang.ArrayUtils;
import com.ztech.lang.ObjectUtils;
import com.ztech.lang.StringUtil;
import java.lang.reflect.InvocationTargetException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.cglib.core.ReflectUtils;

public class MapUtils extends org.apache.commons.collections.MapUtils {
    public MapUtils() {
    }

    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap();
    }

    public static <K, V> HashMap<K, V> newHashMap(int initialCapacity) {
        return new HashMap(initialCapacity);
    }

    public static <K, V> HashMap<K, V> newHashMap(Map<? extends K, ? extends V> map) {
        return new HashMap(map);
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
        return new LinkedHashMap();
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(Map<? extends K, ? extends V> map) {
        return new LinkedHashMap(map);
    }

    public static <K, V> ConcurrentMap<K, V> newConcurrentMap() {
        return new ConcurrentHashMap();
    }

    public static <K extends Comparable, V> TreeMap<K, V> newTreeMap() {
        return new TreeMap();
    }

    public static <K, V> TreeMap<K, V> newTreeMap(SortedMap<K, ? extends V> map) {
        return new TreeMap(map);
    }

    public static <C, K extends C, V> TreeMap<K, V> newTreeMap(Comparator<C> comparator) {
        return new TreeMap(comparator);
    }

    public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(Class<K> type) {
        return new EnumMap(type);
    }

    public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(Map<K, ? extends V> map) {
        return new EnumMap(map);
    }

    public static <K, V> IdentityHashMap<K, V> newIdentityHashMap() {
        return new IdentityHashMap();
    }

    public static <T, V> List<T> toObjectList(Class<T> clazz, List<Map<String, V>> list) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        List<T> retList = new ArrayList();
        if (list != null && !list.isEmpty()) {
            Iterator var3 = list.iterator();

            while(var3.hasNext()) {
                Map<String, V> m = (Map)var3.next();
                retList.add(toObject(clazz, m));
            }
        }

        return retList;
    }

    public static <T, V> T toObject(Class<T> clazz, Map<String, V> map) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        T object = clazz.newInstance();
        return toObject(object, map);
    }

    public static <T, V> T toObject(Class<T> clazz, Map<String, V> map, boolean toCamelCase) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        T object = clazz.newInstance();
        return toObject(object, map, toCamelCase);
    }

    public static <T, V> T toObject(T object, Map<String, V> map) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        return toObject(object, map, false);
    }

    public static <T, V> T toObject(T object, Map<String, V> map, boolean toCamelCase) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        if (toCamelCase) {
            map = toCamelCaseMap(map);
        }

        BeanUtils.populate(object, map);
        return object;
    }

    public static Map<String, String> toMap(Object object) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return BeanUtils.describe(object);
    }

    public static Map<String, Object> toNavMap(Object object) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return PropertyUtils.describe(object);
    }

    public static <T> Collection<Map<String, String>> toMapList(Collection<T> collection) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        List<Map<String, String>> retList = new ArrayList();
        if (collection != null && !collection.isEmpty()) {
            Iterator var2 = collection.iterator();

            while(var2.hasNext()) {
                Object object = var2.next();
                Map<String, String> map = toMap(object);
                retList.add(map);
            }
        }

        return retList;
    }

    public static <T> Collection<Map<String, String>> toMapListForFlat(Collection<T> collection) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        List<Map<String, String>> retList = new ArrayList();
        if (collection != null && !collection.isEmpty()) {
            Iterator var2 = collection.iterator();

            while(var2.hasNext()) {
                Object object = var2.next();
                Map<String, String> map = toMapForFlat(object);
                retList.add(map);
            }
        }

        return retList;
    }

    public static Map<String, String> toMapForFlat(Object object) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Map<String, String> map = toMap(object);
        return toUnderlineStringMap(map);
    }

    public static <K, V> Map<K, V> toCamelCaseMap(Map<K, V> map) {
        return (Map)(map instanceof LinkedHashMap ? new CamelCaseLinkedMap(map) : new CamelCaseMap(map));
    }

    public static <V> Map<String, V> toUnderlineStringMap(Map<String, V> map) {
        Map<String, V> newMap = new HashMap();
        Iterator var2 = map.keySet().iterator();

        while(var2.hasNext()) {
            String key = (String)var2.next();
            newMap.put(StringUtils.uncamelCase(key), map.get(key));
        }

        return newMap;
    }

    public static <K, V> Map<K, V> emptyIfNull(Map<K, V> set) {
        return null == set ? Collections.emptyMap() : set;
    }

    public static <T extends Map<K, V>, K, V> T defaultIfEmpty(T map, T defaultMap) {
        return isEmpty(map) ? defaultMap : map;
    }

    public static <K, V> Map<K, V> createMap(Class<?> mapType) {
        return (Map)(mapType.isAssignableFrom(AbstractMap.class) ? new HashMap() : (Map)ReflectUtils.newInstance(mapType));
    }

    public static <K, V> Map<K, List<V>> toListMap(Iterable<? extends Map<K, V>> mapList) {
        HashMap<K, List<V>> resultMap = new HashMap();
        if (mapList == null) {
            return resultMap;
        } else {
            Iterator var3 = mapList.iterator();

            while(var3.hasNext()) {
                Map<K, V> map = (Map)var3.next();
                Set<Entry<K, V>> entrySet = map.entrySet();
                Iterator var7 = entrySet.iterator();

                while(var7.hasNext()) {
                    Entry<K, V> entry = (Entry)var7.next();
                    K key = entry.getKey();
                    List<V> valueList = (List)resultMap.get(key);
                    if (null == valueList) {
                        List<V> valueList = CollectionUtils.newArrayList(new Object[]{entry.getValue()});
                        resultMap.put(key, valueList);
                    } else {
                        valueList.add(entry.getValue());
                    }
                }
            }

            return resultMap;
        }
    }

    public static <K, V> List<Map<K, V>> toMapList(Map<K, ? extends Iterable<V>> listMap) {
        List<Map<K, V>> resultList = new ArrayList();
        if (isEmpty(listMap)) {
            return resultList;
        } else {
            int index = 0;

            boolean isEnd;
            do {
                isEnd = true;
                Map<K, V> map = new HashMap();
                Iterator var7 = listMap.entrySet().iterator();

                while(var7.hasNext()) {
                    Entry<K, ? extends Iterable<V>> entry = (Entry)var7.next();
                    List<V> vList = CollectionUtils.newArrayList((Iterable)entry.getValue());
                    int vListSize = vList.size();
                    if (index < vListSize) {
                        map.put(entry.getKey(), vList.get(index));
                        if (index != vListSize - 1) {
                            isEnd = false;
                        }
                    }
                }

                if (!map.isEmpty()) {
                    resultList.add(map);
                }

                ++index;
            } while(!isEnd);

            return resultList;
        }
    }

    public static String getStr(Map<?, ?> map, Object key) {
        return (String)get(map, key, String.class);
    }

    public static Integer getInt(Map<?, ?> map, Object key) {
        return (Integer)get(map, key, Integer.class);
    }

    public static Boolean getBool(Map<?, ?> map, Object key) {
        return (Boolean)get(map, key, Boolean.class);
    }

    public static Character getChar(Map<?, ?> map, Object key) {
        return (Character)get(map, key, Character.class);
    }

    public static Date getDate(Map<?, ?> map, Object key) {
        return (Date)get(map, key, Date.class);
    }

    public static <T> T get(Map<?, ?> map, Object key, Class<T> type) {
        return null == map ? null : Convert.convert(type, map.get(key));
    }

    public static <T> T get(Map<?, ?> map, Object key, TypeReference<T> type) {
        return null == map ? null : Convert.convert(type, map.get(key));
    }

    public static <K, V> Map<K, V> filter(Map<K, V> map, Editor<Entry<K, V>> editor) {
        if (null != map && null != editor) {
            Map<K, V> map2 = (Map)ObjectUtils.clone(map);
            if (isEmpty(map2)) {
                return map2;
            } else {
                map2.clear();
                Iterator var4 = map.entrySet().iterator();

                while(var4.hasNext()) {
                    Entry<K, V> entry = (Entry)var4.next();
                    Entry<K, V> modified = (Entry)editor.edit(entry);
                    if (null != modified) {
                        map2.put(modified.getKey(), modified.getValue());
                    }
                }

                return map2;
            }
        } else {
            return map;
        }
    }

    public static <K, V> Map<K, V> filter(Map<K, V> map, Filter<Entry<K, V>> filter) {
        if (null != map && null != filter) {
            Map<K, V> map2 = (Map)ObjectUtils.clone(map);
            if (isEmpty(map2)) {
                return map2;
            } else {
                map2.clear();
                Iterator var3 = map.entrySet().iterator();

                while(var3.hasNext()) {
                    Entry<K, V> entry = (Entry)var3.next();
                    if (filter.accept(entry)) {
                        map2.put(entry.getKey(), entry.getValue());
                    }
                }

                return map2;
            }
        } else {
            return map;
        }
    }

    public static <K, V> Map<K, V> filter(Map<K, V> map, K... keys) {
        Map<K, V> map2 = (Map)ObjectUtils.clone(map);
        if (isEmpty(map2)) {
            return map2;
        } else {
            map2.clear();
            Object[] var3 = keys;
            int var4 = keys.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                K key = var3[var5];
                if (map.containsKey(key)) {
                    map2.put(key, map.get(key));
                }
            }

            return map2;
        }
    }

    public static <T> Map<T, T> reverse(Map<T, T> map) {
        return filter(map, (t) -> {
            return new Entry<T, T>() {
                public T getKey() {
                    return t.getValue();
                }

                public T getValue() {
                    return t.getKey();
                }

                public T setValue(T value) {
                    throw new UnsupportedOperationException("Unsupported setValue method !");
                }
            };
        });
    }

    public static <K, V> Map<K, V> getAny(Map<K, V> map, K... keys) {
        return filter(map, (entry) -> {
            return ArrayUtils.contains(keys, entry.getKey());
        });
    }
}

*/
