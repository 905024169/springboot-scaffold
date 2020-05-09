/*
package com.ztech.lang;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import com.ztech.collect.CollectionUtils;
import com.ztech.core.IterUtil;
import com.ztech.core.exception.UtilException;
import com.ztech.core.func.Editor;
import com.ztech.framework.commons.utils.core.func.Filter;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class ArrayUtils {
    public static final int INDEX_NOT_FOUND = -1;

    public ArrayUtils() {
    }

    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    public static <T> T[] defaultIfEmpty(T[] array, T[] defaultArray) {
        return isEmpty(array) ? defaultArray : array;
    }

    public static boolean isEmpty(Object array) {
        if (null == array) {
            return true;
        } else if (isArray(array)) {
            return 0 == Array.getLength(array);
        } else {
            throw new UtilException("Object to provide is not a Array !");
        }
    }

    public static boolean isEmpty(long[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(int[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(short[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(char[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(byte[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(double[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(float[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(boolean[] array) {
        return array == null || array.length == 0;
    }

    public static <T> boolean isNotEmpty(T[] array) {
        return array != null && array.length != 0;
    }

    public static boolean isNotEmpty(Object array) {
        return !isEmpty(array);
    }

    public static boolean isNotEmpty(long[] array) {
        return !isEmpty(array);
    }

    public static boolean isNotEmpty(int[] array) {
        return !isEmpty(array);
    }

    public static boolean isNotEmpty(short[] array) {
        return !isEmpty(array);
    }

    public static boolean isNotEmpty(char[] array) {
        return !isEmpty(array);
    }

    public static boolean isNotEmpty(byte[] array) {
        return !isEmpty(array);
    }

    public static boolean isNotEmpty(double[] array) {
        return !isEmpty(array);
    }

    public static boolean isNotEmpty(float[] array) {
        return !isEmpty(array);
    }

    public static boolean isNotEmpty(boolean[] array) {
        return !isEmpty(array);
    }

    public static <T> boolean hasNull(T... array) {
        if (isNotEmpty(array)) {
            Object[] var1 = array;
            int var2 = array.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                T element = var1[var3];
                if (null == element) {
                    return true;
                }
            }
        }

        return false;
    }

    public static <T> T firstNonNull(T... array) {
        if (isNotEmpty(array)) {
            Object[] var1 = array;
            int var2 = array.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                T val = var1[var3];
                if (null != val) {
                    return val;
                }
            }
        }

        return null;
    }

    public static <T> T[] newArray(Class<?> componentType, int newSize) {
        return (Object[])((Object[])Array.newInstance(componentType, newSize));
    }

    public static Object[] newArray(int newSize) {
        return new Object[newSize];
    }

    public static Class<?> getComponentType(Object array) {
        return null == array ? null : array.getClass().getComponentType();
    }

    public static Class<?> getComponentType(Class<?> arrayClass) {
        return null == arrayClass ? null : arrayClass.getComponentType();
    }

    public static Class<?> getArrayType(Class<?> componentType) {
        return Array.newInstance(componentType, 0).getClass();
    }

    public static Object[] cast(Class<?> type, Object arrayObj) throws NullPointerException, IllegalArgumentException {
        if (null == arrayObj) {
            throw new NullPointerException("Argument [arrayObj] is null !");
        } else if (!arrayObj.getClass().isArray()) {
            throw new IllegalArgumentException("Argument [arrayObj] is not array !");
        } else if (null == type) {
            return (Object[])((Object[])arrayObj);
        } else {
            Class<?> componentType = type.isArray() ? type.getComponentType() : type;
            Object[] array = (Object[])((Object[])arrayObj);
            Object[] result = newArray(componentType, array.length);
            System.arraycopy(array, 0, result, 0, array.length);
            return result;
        }
    }

    @SafeVarargs
    public static <T> T[] append(T[] buffer, T... newElements) {
        return isEmpty(buffer) ? newElements : insert(buffer, buffer.length, newElements);
    }

    @SafeVarargs
    public static <T> Object append(Object array, T... newElements) {
        return isEmpty(array) ? newElements : insert(array, length(array), newElements);
    }

    public static <T> T[] setOrAppend(T[] buffer, int index, T value) {
        if (index < buffer.length) {
            Array.set(buffer, index, value);
            return buffer;
        } else {
            return append(buffer, value);
        }
    }

    public static Object setOrAppend(Object array, int index, Object value) {
        if (index < length(array)) {
            Array.set(array, index, value);
            return array;
        } else {
            return append(array, value);
        }
    }

    public static <T> T[] insert(T[] buffer, int index, T... newElements) {
        return (Object[])((Object[])insert((Object)buffer, index, newElements));
    }

    public static <T> Object insert(Object array, int index, T... newElements) {
        if (isEmpty(newElements)) {
            return array;
        } else if (isEmpty(array)) {
            return newElements;
        } else {
            int len = length(array);
            if (index < 0) {
                index = index % len + len;
            }

            T[] result = newArray(array.getClass().getComponentType(), Math.max(len, index) + newElements.length);
            System.arraycopy(array, 0, result, 0, Math.min(len, index));
            System.arraycopy(newElements, 0, result, index, newElements.length);
            if (index < len) {
                System.arraycopy(array, index, result, index + newElements.length, len - index);
            }

            return result;
        }
    }

    public static <T> T[] resize(T[] data, int newSize, Class<?> componentType) {
        if (newSize < 0) {
            return data;
        } else {
            T[] newArray = newArray(componentType, newSize);
            if (newSize > 0 && isNotEmpty(data)) {
                System.arraycopy(data, 0, newArray, 0, Math.min(data.length, newSize));
            }

            return newArray;
        }
    }

    public static Object resize(Object array, int newSize) {
        if (newSize < 0) {
            return array;
        } else if (null == array) {
            return null;
        } else {
            int length = length(array);
            Object newArray = Array.newInstance(array.getClass().getComponentType(), newSize);
            if (newSize > 0 && isNotEmpty(array)) {
                System.arraycopy(array, 0, newArray, 0, Math.min(length, newSize));
            }

            return newArray;
        }
    }

    public static byte[] resize(byte[] bytes, int newSize) {
        if (newSize < 0) {
            return bytes;
        } else {
            byte[] newArray = new byte[newSize];
            if (newSize > 0 && isNotEmpty(bytes)) {
                System.arraycopy(bytes, 0, newArray, 0, Math.min(bytes.length, newSize));
            }

            return newArray;
        }
    }

    public static <T> T[] resize(T[] buffer, int newSize) {
        return resize(buffer, newSize, buffer.getClass().getComponentType());
    }

    @SafeVarargs
    public static <T> T[] addAll(T[]... arrays) {
        if (arrays.length == 1) {
            return arrays[0];
        } else {
            int length = 0;
            Object[][] var2 = arrays;
            int var3 = arrays.length;

            int var4;
            for(var4 = 0; var4 < var3; ++var4) {
                T[] array = var2[var4];
                if (null != array) {
                    length += array.length;
                }
            }

            T[] result = newArray(arrays.getClass().getComponentType().getComponentType(), length);
            length = 0;
            Object[][] var8 = arrays;
            var4 = arrays.length;

            for(int var9 = 0; var9 < var4; ++var9) {
                T[] array = var8[var9];
                if (null != array) {
                    System.arraycopy(array, 0, result, length, array.length);
                    length += array.length;
                }
            }

            return result;
        }
    }

    public static byte[] addAll(byte[]... arrays) {
        if (arrays.length == 1) {
            return arrays[0];
        } else {
            int length = 0;
            byte[][] var2 = arrays;
            int var3 = arrays.length;

            int var4;
            for(var4 = 0; var4 < var3; ++var4) {
                byte[] array = var2[var4];
                if (null != array) {
                    length += array.length;
                }
            }

            byte[] result = new byte[length];
            length = 0;
            byte[][] var8 = arrays;
            var4 = arrays.length;

            for(int var9 = 0; var9 < var4; ++var9) {
                byte[] array = var8[var9];
                if (null != array) {
                    System.arraycopy(array, 0, result, length, array.length);
                    length += array.length;
                }
            }

            return result;
        }
    }

    public static Object copy(Object src, int srcPos, Object dest, int destPos, int length) {
        System.arraycopy(src, srcPos, dest, destPos, length);
        return dest;
    }

    public static Object copy(Object src, Object dest, int length) {
        System.arraycopy(src, 0, dest, 0, length);
        return dest;
    }

    public static <T> T[] clone(T[] array) {
        return array == null ? null : (Object[])array.clone();
    }

    public static <T> T clone(T obj) {
        if (null == obj) {
            return null;
        } else if (!isArray(obj)) {
            return null;
        } else {
            Class<?> componentType = obj.getClass().getComponentType();
            Object result;
            if (componentType.isPrimitive()) {
                int length = Array.getLength(obj);
                result = Array.newInstance(componentType, length);

                while(length-- > 0) {
                    Array.set(result, length, Array.get(obj, length));
                }
            } else {
                result = ((Object[])((Object[])obj)).clone();
            }

            return result;
        }
    }

    public static int[] range(int excludedEnd) {
        return range(0, excludedEnd, 1);
    }

    public static int[] range(int includedStart, int excludedEnd) {
        return range(includedStart, excludedEnd, 1);
    }

    public static int[] range(int includedStart, int excludedEnd, int step) {
        int deviation;
        if (includedStart > excludedEnd) {
            deviation = includedStart;
            includedStart = excludedEnd;
            excludedEnd = deviation;
        }

        if (step <= 0) {
            step = 1;
        }

        deviation = excludedEnd - includedStart;
        int length = deviation / step;
        if (deviation % step != 0) {
            ++length;
        }

        int[] range = new int[length];

        for(int i = 0; i < length; ++i) {
            range[i] = includedStart;
            includedStart += step;
        }

        return range;
    }

    public static byte[][] split(byte[] array, int len) {
        int x = array.length / len;
        int y = array.length % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }

        byte[][] arrays = new byte[x + z][];

        for(int i = 0; i < x + z; ++i) {
            byte[] arr = new byte[len];
            if (i == x + z - 1 && y != 0) {
                System.arraycopy(array, i * len, arr, 0, y);
            } else {
                System.arraycopy(array, i * len, arr, 0, len);
            }

            arrays[i] = arr;
        }

        return arrays;
    }

    public static <T> T[] filter(T[] array, Editor<T> editor) {
        ArrayList<T> list = new ArrayList(array.length);
        Object[] var4 = array;
        int var5 = array.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            T t = var4[var6];
            T modified = editor.edit(t);
            if (null != modified) {
                list.add(modified);
            }
        }

        return list.toArray(Arrays.copyOf(array, list.size()));
    }

    public static <T> T[] filter(T[] array, Filter<T> filter) {
        if (null == filter) {
            return array;
        } else {
            ArrayList<T> list = new ArrayList(array.length);
            Object[] result = array;
            int var4 = array.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                T t = result[var5];
                if (filter.accept(t)) {
                    list.add(t);
                }
            }

            result = newArray(array.getClass().getComponentType(), list.size());
            return list.toArray(result);
        }
    }

    public static <T> T[] removeNull(T[] array) {
        return filter(array, (t) -> {
            return t;
        });
    }

    public static <T extends CharSequence> T[] removeEmpty(T[] array) {
        return (CharSequence[])filter(array, (Filter)((t) -> {
            return !StringUtils.isEmpty(t);
        }));
    }

    public static <T extends CharSequence> T[] removeBlank(T[] array) {
        return (CharSequence[])filter(array, (Filter)((t) -> {
            return !StringUtils.isBlank(t);
        }));
    }

    public static String[] nullToEmpty(String[] array) {
        return (String[])filter(array, (Editor)((t) -> {
            return null == t ? "" : t;
        }));
    }

    public static <K, V> Map<K, V> zip(K[] keys, V[] values) {
        return zip(keys, values);
    }

    public static <T> int indexOf(T[] array, Object value) {
        if (null != array) {
            for(int i = 0; i < array.length; ++i) {
                if (ObjectUtils.equal(value, array[i])) {
                    return i;
                }
            }
        }

        return -1;
    }

    public static int indexOfIgnoreCase(CharSequence[] array, CharSequence value) {
        if (null != array) {
            for(int i = 0; i < array.length; ++i) {
                if (StringUtils.equalsIgnoreCase(array[i], value)) {
                    return i;
                }
            }
        }

        return -1;
    }

    public static <T> int lastIndexOf(T[] array, Object value) {
        if (null != array) {
            for(int i = array.length - 1; i >= 0; --i) {
                if (ObjectUtils.equal(value, array[i])) {
                    return i;
                }
            }
        }

        return -1;
    }

    public static <T> boolean contains(T[] array, T value) {
        return indexOf(array, value) > -1;
    }

    public static <T> boolean containsAny(T[] array, T... values) {
        Object[] var2 = values;
        int var3 = values.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            T value = var2[var4];
            if (contains(array, value)) {
                return true;
            }
        }

        return false;
    }

    public static boolean containsIgnoreCase(CharSequence[] array, CharSequence value) {
        return indexOfIgnoreCase(array, value) > -1;
    }

    public static int indexOf(long[] array, long value) {
        if (null != array) {
            for(int i = 0; i < array.length; ++i) {
                if (value == array[i]) {
                    return i;
                }
            }
        }

        return -1;
    }

    public static int lastIndexOf(long[] array, long value) {
        if (null != array) {
            for(int i = array.length - 1; i >= 0; --i) {
                if (value == array[i]) {
                    return i;
                }
            }
        }

        return -1;
    }

    public static boolean contains(long[] array, long value) {
        return indexOf(array, value) > -1;
    }

    public static int indexOf(int[] array, int value) {
        if (null != array) {
            for(int i = 0; i < array.length; ++i) {
                if (value == array[i]) {
                    return i;
                }
            }
        }

        return -1;
    }

    public static int lastIndexOf(int[] array, int value) {
        if (null != array) {
            for(int i = array.length - 1; i >= 0; --i) {
                if (value == array[i]) {
                    return i;
                }
            }
        }

        return -1;
    }

    public static boolean contains(int[] array, int value) {
        return indexOf(array, value) > -1;
    }

    public static int indexOf(short[] array, short value) {
        if (null != array) {
            for(int i = 0; i < array.length; ++i) {
                if (value == array[i]) {
                    return i;
                }
            }
        }

        return -1;
    }

    public static int lastIndexOf(short[] array, short value) {
        if (null != array) {
            for(int i = array.length - 1; i >= 0; --i) {
                if (value == array[i]) {
                    return i;
                }
            }
        }

        return -1;
    }

    public static boolean contains(short[] array, short value) {
        return indexOf(array, value) > -1;
    }

    public static int indexOf(char[] array, char value) {
        if (null != array) {
            for(int i = 0; i < array.length; ++i) {
                if (value == array[i]) {
                    return i;
                }
            }
        }

        return -1;
    }

    public static int lastIndexOf(char[] array, char value) {
        if (null != array) {
            for(int i = array.length - 1; i >= 0; --i) {
                if (value == array[i]) {
                    return i;
                }
            }
        }

        return -1;
    }

    public static boolean contains(char[] array, char value) {
        return indexOf(array, value) > -1;
    }

    public static int indexOf(byte[] array, byte value) {
        if (null != array) {
            for(int i = 0; i < array.length; ++i) {
                if (value == array[i]) {
                    return i;
                }
            }
        }

        return -1;
    }

    public static int lastIndexOf(byte[] array, byte value) {
        if (null != array) {
            for(int i = array.length - 1; i >= 0; --i) {
                if (value == array[i]) {
                    return i;
                }
            }
        }

        return -1;
    }

    public static boolean contains(byte[] array, byte value) {
        return indexOf(array, value) > -1;
    }

    public static int indexOf(double[] array, double value) {
        if (null != array) {
            for(int i = 0; i < array.length; ++i) {
                if (value == array[i]) {
                    return i;
                }
            }
        }

        return -1;
    }

    public static int lastIndexOf(double[] array, double value) {
        if (null != array) {
            for(int i = array.length - 1; i >= 0; --i) {
                if (value == array[i]) {
                    return i;
                }
            }
        }

        return -1;
    }

    public static boolean contains(double[] array, double value) {
        return indexOf(array, value) > -1;
    }

    public static int indexOf(float[] array, float value) {
        if (null != array) {
            for(int i = 0; i < array.length; ++i) {
                if (value == array[i]) {
                    return i;
                }
            }
        }

        return -1;
    }

    public static int lastIndexOf(float[] array, float value) {
        if (null != array) {
            for(int i = array.length - 1; i >= 0; --i) {
                if (value == array[i]) {
                    return i;
                }
            }
        }

        return -1;
    }

    public static boolean contains(float[] array, float value) {
        return indexOf(array, value) > -1;
    }

    public static int indexOf(boolean[] array, boolean value) {
        if (null != array) {
            for(int i = 0; i < array.length; ++i) {
                if (value == array[i]) {
                    return i;
                }
            }
        }

        return -1;
    }

    public static int lastIndexOf(boolean[] array, boolean value) {
        if (null != array) {
            for(int i = array.length - 1; i >= 0; --i) {
                if (value == array[i]) {
                    return i;
                }
            }
        }

        return -1;
    }

    public static boolean contains(boolean[] array, boolean value) {
        return indexOf(array, value) > -1;
    }

    public static Integer[] wrap(int... values) {
        if (null == values) {
            return null;
        } else {
            int length = values.length;
            if (0 == length) {
                return new Integer[0];
            } else {
                Integer[] array = new Integer[length];

                for(int i = 0; i < length; ++i) {
                    array[i] = values[i];
                }

                return array;
            }
        }
    }

    public static int[] unWrap(Integer... values) {
        if (null == values) {
            return null;
        } else {
            int length = values.length;
            if (0 == length) {
                return new int[0];
            } else {
                int[] array = new int[length];

                for(int i = 0; i < length; ++i) {
                    array[i] = values[i];
                }

                return array;
            }
        }
    }

    public static Long[] wrap(long... values) {
        if (null == values) {
            return null;
        } else {
            int length = values.length;
            if (0 == length) {
                return new Long[0];
            } else {
                Long[] array = new Long[length];

                for(int i = 0; i < length; ++i) {
                    array[i] = values[i];
                }

                return array;
            }
        }
    }

    public static long[] unWrap(Long... values) {
        if (null == values) {
            return null;
        } else {
            int length = values.length;
            if (0 == length) {
                return new long[0];
            } else {
                long[] array = new long[length];

                for(int i = 0; i < length; ++i) {
                    array[i] = values[i];
                }

                return array;
            }
        }
    }

    public static Character[] wrap(char... values) {
        if (null == values) {
            return null;
        } else {
            int length = values.length;
            if (0 == length) {
                return new Character[0];
            } else {
                Character[] array = new Character[length];

                for(int i = 0; i < length; ++i) {
                    array[i] = values[i];
                }

                return array;
            }
        }
    }

    public static char[] unWrap(Character... values) {
        if (null == values) {
            return null;
        } else {
            int length = values.length;
            if (0 == length) {
                return new char[0];
            } else {
                char[] array = new char[length];

                for(int i = 0; i < length; ++i) {
                    array[i] = values[i];
                }

                return array;
            }
        }
    }

    public static Byte[] wrap(byte... values) {
        if (null == values) {
            return null;
        } else {
            int length = values.length;
            if (0 == length) {
                return new Byte[0];
            } else {
                Byte[] array = new Byte[length];

                for(int i = 0; i < length; ++i) {
                    array[i] = values[i];
                }

                return array;
            }
        }
    }

    public static byte[] unWrap(Byte... values) {
        if (null == values) {
            return null;
        } else {
            int length = values.length;
            if (0 == length) {
                return new byte[0];
            } else {
                byte[] array = new byte[length];

                for(int i = 0; i < length; ++i) {
                    array[i] = (Byte)ObjectUtils.defaultIfNull(values[i], 0);
                }

                return array;
            }
        }
    }

    public static Short[] wrap(short... values) {
        if (null == values) {
            return null;
        } else {
            int length = values.length;
            if (0 == length) {
                return new Short[0];
            } else {
                Short[] array = new Short[length];

                for(int i = 0; i < length; ++i) {
                    array[i] = values[i];
                }

                return array;
            }
        }
    }

    public static short[] unWrap(Short... values) {
        if (null == values) {
            return null;
        } else {
            int length = values.length;
            if (0 == length) {
                return new short[0];
            } else {
                short[] array = new short[length];

                for(int i = 0; i < length; ++i) {
                    array[i] = (Short)ObjectUtils.defaultIfNull(values[i], Short.valueOf((short)0));
                }

                return array;
            }
        }
    }

    public static Float[] wrap(float... values) {
        if (null == values) {
            return null;
        } else {
            int length = values.length;
            if (0 == length) {
                return new Float[0];
            } else {
                Float[] array = new Float[length];

                for(int i = 0; i < length; ++i) {
                    array[i] = values[i];
                }

                return array;
            }
        }
    }

    public static float[] unWrap(Float... values) {
        if (null == values) {
            return null;
        } else {
            int length = values.length;
            if (0 == length) {
                return new float[0];
            } else {
                float[] array = new float[length];

                for(int i = 0; i < length; ++i) {
                    array[i] = (Float)ObjectUtils.defaultIfNull(values[i], 0.0F);
                }

                return array;
            }
        }
    }

    public static Double[] wrap(double... values) {
        if (null == values) {
            return null;
        } else {
            int length = values.length;
            if (0 == length) {
                return new Double[0];
            } else {
                Double[] array = new Double[length];

                for(int i = 0; i < length; ++i) {
                    array[i] = values[i];
                }

                return array;
            }
        }
    }

    public static double[] unWrap(Double... values) {
        if (null == values) {
            return null;
        } else {
            int length = values.length;
            if (0 == length) {
                return new double[0];
            } else {
                double[] array = new double[length];

                for(int i = 0; i < length; ++i) {
                    array[i] = (Double)ObjectUtils.defaultIfNull(values[i], 0.0D);
                }

                return array;
            }
        }
    }

    public static Boolean[] wrap(boolean... values) {
        if (null == values) {
            return null;
        } else {
            int length = values.length;
            if (0 == length) {
                return new Boolean[0];
            } else {
                Boolean[] array = new Boolean[length];

                for(int i = 0; i < length; ++i) {
                    array[i] = values[i];
                }

                return array;
            }
        }
    }

    public static boolean[] unWrap(Boolean... values) {
        if (null == values) {
            return null;
        } else {
            int length = values.length;
            if (0 == length) {
                return new boolean[0];
            } else {
                boolean[] array = new boolean[length];

                for(int i = 0; i < length; ++i) {
                    array[i] = (Boolean)ObjectUtils.defaultIfNull(values[i], false);
                }

                return array;
            }
        }
    }

    public static Object[] wrap(Object obj) {
        if (null == obj) {
            return null;
        } else if (isArray(obj)) {
            try {
                return (Object[])((Object[])obj);
            } catch (Exception var5) {
                String className = obj.getClass().getComponentType().getName();
                byte var4 = -1;
                switch(className.hashCode()) {
                    case -1325958191:
                        if (className.equals("double")) {
                            var4 = 7;
                        }
                        break;
                    case 104431:
                        if (className.equals("int")) {
                            var4 = 1;
                        }
                        break;
                    case 3039496:
                        if (className.equals("byte")) {
                            var4 = 4;
                        }
                        break;
                    case 3052374:
                        if (className.equals("char")) {
                            var4 = 3;
                        }
                        break;
                    case 3327612:
                        if (className.equals("long")) {
                            var4 = 0;
                        }
                        break;
                    case 64711720:
                        if (className.equals("boolean")) {
                            var4 = 5;
                        }
                        break;
                    case 97526364:
                        if (className.equals("float")) {
                            var4 = 6;
                        }
                        break;
                    case 109413500:
                        if (className.equals("short")) {
                            var4 = 2;
                        }
                }

                switch(var4) {
                    case 0:
                        return wrap((long[])((long[])obj));
                    case 1:
                        return wrap((int[])((int[])obj));
                    case 2:
                        return wrap((short[])((short[])obj));
                    case 3:
                        return wrap((char[])((char[])obj));
                    case 4:
                        return wrap((byte[])((byte[])obj));
                    case 5:
                        return wrap((boolean[])((boolean[])obj));
                    case 6:
                        return wrap((float[])((float[])obj));
                    case 7:
                        return wrap((double[])((double[])obj));
                    default:
                        throw new UtilException(var5);
                }
            }
        } else {
            throw new UtilException(StringUtils.format("[{}] is not Array!", new Object[]{obj.getClass()}));
        }
    }

    public static boolean isArray(Object obj) {
        return null == obj ? false : obj.getClass().isArray();
    }

    public static <T> T get(Object array, int index) {
        if (null == array) {
            return null;
        } else {
            if (index < 0) {
                index += Array.getLength(array);
            }

            try {
                return Array.get(array, index);
            } catch (ArrayIndexOutOfBoundsException var3) {
                return null;
            }
        }
    }

    public static <T> T[] getAny(Object array, int... indexes) {
        if (null == array) {
            return null;
        } else {
            T[] result = newArray(array.getClass().getComponentType(), indexes.length);
            int[] var3 = indexes;
            int var4 = indexes.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                int i = var3[var5];
                result[i] = get(array, i);
            }

            return result;
        }
    }

    public static <T> T[] sub(T[] array, int start, int end) {
        int length = length(array);
        if (start < 0) {
            start += length;
        }

        if (end < 0) {
            end += length;
        }

        if (start == length) {
            return newArray(array.getClass().getComponentType(), 0);
        } else {
            if (start > end) {
                int tmp = start;
                start = end;
                end = tmp;
            }

            if (end > length) {
                if (start >= length) {
                    return newArray(array.getClass().getComponentType(), 0);
                }

                end = length;
            }

            return Arrays.copyOfRange(array, start, end);
        }
    }

    public static byte[] sub(byte[] array, int start, int end) {
        int length = length(array);
        if (start < 0) {
            start += length;
        }

        if (end < 0) {
            end += length;
        }

        if (start == length) {
            return new byte[0];
        } else {
            if (start > end) {
                int tmp = start;
                start = end;
                end = tmp;
            }

            if (end > length) {
                if (start >= length) {
                    return new byte[0];
                }

                end = length;
            }

            return Arrays.copyOfRange(array, start, end);
        }
    }

    public static int[] sub(int[] array, int start, int end) {
        int length = length(array);
        if (start < 0) {
            start += length;
        }

        if (end < 0) {
            end += length;
        }

        if (start == length) {
            return new int[0];
        } else {
            if (start > end) {
                int tmp = start;
                start = end;
                end = tmp;
            }

            if (end > length) {
                if (start >= length) {
                    return new int[0];
                }

                end = length;
            }

            return Arrays.copyOfRange(array, start, end);
        }
    }

    public static long[] sub(long[] array, int start, int end) {
        int length = length(array);
        if (start < 0) {
            start += length;
        }

        if (end < 0) {
            end += length;
        }

        if (start == length) {
            return new long[0];
        } else {
            if (start > end) {
                int tmp = start;
                start = end;
                end = tmp;
            }

            if (end > length) {
                if (start >= length) {
                    return new long[0];
                }

                end = length;
            }

            return Arrays.copyOfRange(array, start, end);
        }
    }

    public static short[] sub(short[] array, int start, int end) {
        int length = length(array);
        if (start < 0) {
            start += length;
        }

        if (end < 0) {
            end += length;
        }

        if (start == length) {
            return new short[0];
        } else {
            if (start > end) {
                int tmp = start;
                start = end;
                end = tmp;
            }

            if (end > length) {
                if (start >= length) {
                    return new short[0];
                }

                end = length;
            }

            return Arrays.copyOfRange(array, start, end);
        }
    }

    public static char[] sub(char[] array, int start, int end) {
        int length = length(array);
        if (start < 0) {
            start += length;
        }

        if (end < 0) {
            end += length;
        }

        if (start == length) {
            return new char[0];
        } else {
            if (start > end) {
                int tmp = start;
                start = end;
                end = tmp;
            }

            if (end > length) {
                if (start >= length) {
                    return new char[0];
                }

                end = length;
            }

            return Arrays.copyOfRange(array, start, end);
        }
    }

    public static double[] sub(double[] array, int start, int end) {
        int length = length(array);
        if (start < 0) {
            start += length;
        }

        if (end < 0) {
            end += length;
        }

        if (start == length) {
            return new double[0];
        } else {
            if (start > end) {
                int tmp = start;
                start = end;
                end = tmp;
            }

            if (end > length) {
                if (start >= length) {
                    return new double[0];
                }

                end = length;
            }

            return Arrays.copyOfRange(array, start, end);
        }
    }

    public static float[] sub(float[] array, int start, int end) {
        int length = length(array);
        if (start < 0) {
            start += length;
        }

        if (end < 0) {
            end += length;
        }

        if (start == length) {
            return new float[0];
        } else {
            if (start > end) {
                int tmp = start;
                start = end;
                end = tmp;
            }

            if (end > length) {
                if (start >= length) {
                    return new float[0];
                }

                end = length;
            }

            return Arrays.copyOfRange(array, start, end);
        }
    }

    public static boolean[] sub(boolean[] array, int start, int end) {
        int length = length(array);
        if (start < 0) {
            start += length;
        }

        if (end < 0) {
            end += length;
        }

        if (start == length) {
            return new boolean[0];
        } else {
            if (start > end) {
                int tmp = start;
                start = end;
                end = tmp;
            }

            if (end > length) {
                if (start >= length) {
                    return new boolean[0];
                }

                end = length;
            }

            return Arrays.copyOfRange(array, start, end);
        }
    }

    public static Object[] sub(Object array, int start, int end) {
        return sub(array, start, end, 1);
    }

    public static Object[] sub(Object array, int start, int end, int step) {
        int length = length(array);
        if (start < 0) {
            start += length;
        }

        if (end < 0) {
            end += length;
        }

        if (start == length) {
            return new Object[0];
        } else {
            if (start > end) {
                int tmp = start;
                start = end;
                end = tmp;
            }

            if (end > length) {
                if (start >= length) {
                    return new Object[0];
                }

                end = length;
            }

            if (step <= 1) {
                step = 1;
            }

            ArrayList<Object> list = new ArrayList();

            for(int i = start; i < end; i += step) {
                list.add(get(array, i));
            }

            return list.toArray();
        }
    }

    public static String toString(Object obj) {
        if (null == obj) {
            return null;
        } else if (obj instanceof long[]) {
            return Arrays.toString((long[])((long[])obj));
        } else if (obj instanceof int[]) {
            return Arrays.toString((int[])((int[])obj));
        } else if (obj instanceof short[]) {
            return Arrays.toString((short[])((short[])obj));
        } else if (obj instanceof char[]) {
            return Arrays.toString((char[])((char[])obj));
        } else if (obj instanceof byte[]) {
            return Arrays.toString((byte[])((byte[])obj));
        } else if (obj instanceof boolean[]) {
            return Arrays.toString((boolean[])((boolean[])obj));
        } else if (obj instanceof float[]) {
            return Arrays.toString((float[])((float[])obj));
        } else if (obj instanceof double[]) {
            return Arrays.toString((double[])((double[])obj));
        } else {
            if (isArray(obj)) {
                try {
                    return Arrays.deepToString((Object[])((Object[])obj));
                } catch (Exception var2) {
                    ;
                }
            }

            return obj.toString();
        }
    }

    public static int length(Object array) throws IllegalArgumentException {
        return null == array ? 0 : Array.getLength(array);
    }

    public static <T> String join(T[] array, CharSequence conjunction) {
        return join(array, conjunction, (String)null, (String)null);
    }

    public static <T> String join(T[] array, CharSequence conjunction, String prefix, String suffix) {
        if (null == array) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            boolean isFirst = true;
            Object[] var6 = array;
            int var7 = array.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                T item = var6[var8];
                if (isFirst) {
                    isFirst = false;
                } else {
                    sb.append(conjunction);
                }

                if (isArray(item)) {
                    sb.append(join(wrap(item), conjunction, prefix, suffix));
                } else if (item instanceof Iterable) {
                    sb.append(IterUtil.join((Iterable)item, conjunction, prefix, suffix));
                } else if (item instanceof Iterator) {
                    sb.append(IterUtil.join((Iterator)item, conjunction, prefix, suffix));
                } else {
                    sb.append(StringUtils.wrap(StringUtils.toString(item), prefix, suffix));
                }
            }

            return sb.toString();
        }
    }

    public static String join(long[] array, CharSequence conjunction) {
        if (null == array) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            boolean isFirst = true;
            long[] var4 = array;
            int var5 = array.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                long item = var4[var6];
                if (isFirst) {
                    isFirst = false;
                } else {
                    sb.append(conjunction);
                }

                sb.append(item);
            }

            return sb.toString();
        }
    }

    public static String join(int[] array, CharSequence conjunction) {
        if (null == array) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            boolean isFirst = true;
            int[] var4 = array;
            int var5 = array.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                int item = var4[var6];
                if (isFirst) {
                    isFirst = false;
                } else {
                    sb.append(conjunction);
                }

                sb.append(item);
            }

            return sb.toString();
        }
    }

    public static String join(short[] array, CharSequence conjunction) {
        if (null == array) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            boolean isFirst = true;
            short[] var4 = array;
            int var5 = array.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                short item = var4[var6];
                if (isFirst) {
                    isFirst = false;
                } else {
                    sb.append(conjunction);
                }

                sb.append(item);
            }

            return sb.toString();
        }
    }

    public static String join(char[] array, CharSequence conjunction) {
        if (null == array) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            boolean isFirst = true;
            char[] var4 = array;
            int var5 = array.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                char item = var4[var6];
                if (isFirst) {
                    isFirst = false;
                } else {
                    sb.append(conjunction);
                }

                sb.append(item);
            }

            return sb.toString();
        }
    }

    public static String join(byte[] array, CharSequence conjunction) {
        if (null == array) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            boolean isFirst = true;
            byte[] var4 = array;
            int var5 = array.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                byte item = var4[var6];
                if (isFirst) {
                    isFirst = false;
                } else {
                    sb.append(conjunction);
                }

                sb.append(item);
            }

            return sb.toString();
        }
    }

    public static String join(boolean[] array, CharSequence conjunction) {
        if (null == array) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            boolean isFirst = true;
            boolean[] var4 = array;
            int var5 = array.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                boolean item = var4[var6];
                if (isFirst) {
                    isFirst = false;
                } else {
                    sb.append(conjunction);
                }

                sb.append(item);
            }

            return sb.toString();
        }
    }

    public static String join(float[] array, CharSequence conjunction) {
        if (null == array) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            boolean isFirst = true;
            float[] var4 = array;
            int var5 = array.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                float item = var4[var6];
                if (isFirst) {
                    isFirst = false;
                } else {
                    sb.append(conjunction);
                }

                sb.append(item);
            }

            return sb.toString();
        }
    }

    public static String join(double[] array, CharSequence conjunction) {
        if (null == array) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            boolean isFirst = true;
            double[] var4 = array;
            int var5 = array.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                double item = var4[var6];
                if (isFirst) {
                    isFirst = false;
                } else {
                    sb.append(conjunction);
                }

                sb.append(item);
            }

            return sb.toString();
        }
    }

    public static String join(Object array, CharSequence conjunction) {
        if (isArray(array)) {
            Class<?> componentType = array.getClass().getComponentType();
            if (componentType.isPrimitive()) {
                String componentTypeName = componentType.getName();
                byte var5 = -1;
                switch(componentTypeName.hashCode()) {
                    case -1325958191:
                        if (componentTypeName.equals("double")) {
                            var5 = 7;
                        }
                        break;
                    case 104431:
                        if (componentTypeName.equals("int")) {
                            var5 = 1;
                        }
                        break;
                    case 3039496:
                        if (componentTypeName.equals("byte")) {
                            var5 = 4;
                        }
                        break;
                    case 3052374:
                        if (componentTypeName.equals("char")) {
                            var5 = 3;
                        }
                        break;
                    case 3327612:
                        if (componentTypeName.equals("long")) {
                            var5 = 0;
                        }
                        break;
                    case 64711720:
                        if (componentTypeName.equals("boolean")) {
                            var5 = 5;
                        }
                        break;
                    case 97526364:
                        if (componentTypeName.equals("float")) {
                            var5 = 6;
                        }
                        break;
                    case 109413500:
                        if (componentTypeName.equals("short")) {
                            var5 = 2;
                        }
                }

                switch(var5) {
                    case 0:
                        return join((long[])((long[])array), conjunction);
                    case 1:
                        return join((int[])((int[])array), conjunction);
                    case 2:
                        return join((short[])((short[])array), conjunction);
                    case 3:
                        return join((char[])((char[])array), conjunction);
                    case 4:
                        return join((byte[])((byte[])array), conjunction);
                    case 5:
                        return join((boolean[])((boolean[])array), conjunction);
                    case 6:
                        return join((float[])((float[])array), conjunction);
                    case 7:
                        return join((double[])((double[])array), conjunction);
                    default:
                        throw new UtilException("Unknown primitive type: [{}]", new Object[]{componentTypeName});
                }
            } else {
                return join((Object[])((Object[])array), conjunction);
            }
        } else {
            throw new UtilException(StringUtils.format("[{}] is not a Array!", new Object[]{array.getClass()}));
        }
    }

    public static byte[] toArray(ByteBuffer bytebuffer) {
        if (!bytebuffer.hasArray()) {
            int oldPosition = bytebuffer.position();
            bytebuffer.position(0);
            int size = bytebuffer.limit();
            byte[] buffers = new byte[size];
            bytebuffer.get(buffers);
            bytebuffer.position(oldPosition);
            return buffers;
        } else {
            return Arrays.copyOfRange(bytebuffer.array(), bytebuffer.position(), bytebuffer.limit());
        }
    }

    public static <T> T[] toArray(Iterator<T> iterator, Class<T> componentType) {
        return toArray((Collection)CollectionUtils.newArrayList(iterator), componentType);
    }

    public static <T> T[] toArray(Iterable<T> iterable, Class<T> componentType) {
        return toArray(CollectionUtils.toCollection(iterable), componentType);
    }

    public static <T> T[] toArray(Collection<T> collection, Class<T> componentType) {
        T[] array = newArray(componentType, collection.size());
        return collection.toArray(array);
    }

    public static <T> T[] remove(T[] array, int index) throws IllegalArgumentException {
        return (Object[])((Object[])remove((Object)array, index));
    }

    public static long[] remove(long[] array, int index) throws IllegalArgumentException {
        return (long[])((long[])remove((Object)array, index));
    }

    public static int[] remove(int[] array, int index) throws IllegalArgumentException {
        return (int[])((int[])remove((Object)array, index));
    }

    public static short[] remove(short[] array, int index) throws IllegalArgumentException {
        return (short[])((short[])remove((Object)array, index));
    }

    public static char[] remove(char[] array, int index) throws IllegalArgumentException {
        return (char[])((char[])remove((Object)array, index));
    }

    public static byte[] remove(byte[] array, int index) throws IllegalArgumentException {
        return (byte[])((byte[])remove((Object)array, index));
    }

    public static double[] remove(double[] array, int index) throws IllegalArgumentException {
        return (double[])((double[])remove((Object)array, index));
    }

    public static float[] remove(float[] array, int index) throws IllegalArgumentException {
        return (float[])((float[])remove((Object)array, index));
    }

    public static boolean[] remove(boolean[] array, int index) throws IllegalArgumentException {
        return (boolean[])((boolean[])remove((Object)array, index));
    }

    public static Object remove(Object array, int index) throws IllegalArgumentException {
        if (null == array) {
            return null;
        } else {
            int length = length(array);
            if (index >= 0 && index < length) {
                Object result = Array.newInstance(array.getClass().getComponentType(), length - 1);
                System.arraycopy(array, 0, result, 0, index);
                if (index < length - 1) {
                    System.arraycopy(array, index + 1, result, index, length - index - 1);
                }

                return result;
            } else {
                return array;
            }
        }
    }

    public static <T> T[] removeEle(T[] array, T element) throws IllegalArgumentException {
        return remove(array, indexOf(array, element));
    }

    public static long[] removeEle(long[] array, long element) throws IllegalArgumentException {
        return remove(array, indexOf(array, element));
    }

    public static int[] removeEle(int[] array, int element) throws IllegalArgumentException {
        return remove(array, indexOf(array, element));
    }

    public static short[] removeEle(short[] array, short element) throws IllegalArgumentException {
        return remove(array, indexOf(array, element));
    }

    public static char[] removeEle(char[] array, char element) throws IllegalArgumentException {
        return remove(array, indexOf(array, element));
    }

    public static byte[] removeEle(byte[] array, byte element) throws IllegalArgumentException {
        return remove(array, indexOf(array, element));
    }

    public static double[] removeEle(double[] array, double element) throws IllegalArgumentException {
        return remove(array, indexOf(array, element));
    }

    public static float[] removeEle(float[] array, float element) throws IllegalArgumentException {
        return remove(array, indexOf(array, element));
    }

    public static boolean[] removeEle(boolean[] array, boolean element) throws IllegalArgumentException {
        return remove(array, indexOf(array, element));
    }

    public static <T> T[] reverse(T[] array, int startIndexInclusive, int endIndexExclusive) {
        if (isEmpty(array)) {
            return array;
        } else {
            int i = Math.max(startIndexInclusive, 0);

            for(int j = Math.min(array.length, endIndexExclusive) - 1; j > i; ++i) {
                T tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                --j;
            }

            return array;
        }
    }

    public static <T> T[] reverse(T[] array) {
        return reverse((Object[])array, 0, array.length);
    }

    public static long[] reverse(long[] array, int startIndexInclusive, int endIndexExclusive) {
        if (isEmpty(array)) {
            return array;
        } else {
            int i = Math.max(startIndexInclusive, 0);

            for(int j = Math.min(array.length, endIndexExclusive) - 1; j > i; ++i) {
                long tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                --j;
            }

            return array;
        }
    }

    public static long[] reverse(long[] array) {
        return reverse((long[])array, 0, array.length);
    }

    public static int[] reverse(int[] array, int startIndexInclusive, int endIndexExclusive) {
        if (isEmpty(array)) {
            return array;
        } else {
            int i = Math.max(startIndexInclusive, 0);

            for(int j = Math.min(array.length, endIndexExclusive) - 1; j > i; ++i) {
                int tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                --j;
            }

            return array;
        }
    }

    public static int[] reverse(int[] array) {
        return reverse((int[])array, 0, array.length);
    }

    public static short[] reverse(short[] array, int startIndexInclusive, int endIndexExclusive) {
        if (isEmpty(array)) {
            return array;
        } else {
            int i = Math.max(startIndexInclusive, 0);

            for(int j = Math.min(array.length, endIndexExclusive) - 1; j > i; ++i) {
                short tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                --j;
            }

            return array;
        }
    }

    public static short[] reverse(short[] array) {
        return reverse((short[])array, 0, array.length);
    }

    public static char[] reverse(char[] array, int startIndexInclusive, int endIndexExclusive) {
        if (isEmpty(array)) {
            return array;
        } else {
            int i = Math.max(startIndexInclusive, 0);

            for(int j = Math.min(array.length, endIndexExclusive) - 1; j > i; ++i) {
                char tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                --j;
            }

            return array;
        }
    }

    public static char[] reverse(char[] array) {
        return reverse((char[])array, 0, array.length);
    }

    public static byte[] reverse(byte[] array, int startIndexInclusive, int endIndexExclusive) {
        if (isEmpty(array)) {
            return array;
        } else {
            int i = Math.max(startIndexInclusive, 0);

            for(int j = Math.min(array.length, endIndexExclusive) - 1; j > i; ++i) {
                byte tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                --j;
            }

            return array;
        }
    }

    public static byte[] reverse(byte[] array) {
        return reverse((byte[])array, 0, array.length);
    }

    public static double[] reverse(double[] array, int startIndexInclusive, int endIndexExclusive) {
        if (isEmpty(array)) {
            return array;
        } else {
            int i = Math.max(startIndexInclusive, 0);

            for(int j = Math.min(array.length, endIndexExclusive) - 1; j > i; ++i) {
                double tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                --j;
            }

            return array;
        }
    }

    public static double[] reverse(double[] array) {
        return reverse((double[])array, 0, array.length);
    }

    public static float[] reverse(float[] array, int startIndexInclusive, int endIndexExclusive) {
        if (isEmpty(array)) {
            return array;
        } else {
            int i = Math.max(startIndexInclusive, 0);

            for(int j = Math.min(array.length, endIndexExclusive) - 1; j > i; ++i) {
                float tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                --j;
            }

            return array;
        }
    }

    public static float[] reverse(float[] array) {
        return reverse((float[])array, 0, array.length);
    }

    public static boolean[] reverse(boolean[] array, int startIndexInclusive, int endIndexExclusive) {
        if (isEmpty(array)) {
            return array;
        } else {
            int i = Math.max(startIndexInclusive, 0);

            for(int j = Math.min(array.length, endIndexExclusive) - 1; j > i; ++i) {
                boolean tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                --j;
            }

            return array;
        }
    }

    public static boolean[] reverse(boolean[] array) {
        return reverse((boolean[])array, 0, array.length);
    }

    public static <T extends Comparable<? super T>> T min(T[] numberArray) {
        if (isEmpty((Object[])numberArray)) {
            throw new IllegalArgumentException("Number array must not empty !");
        } else {
            T min = numberArray[0];
            Comparable[] var2 = numberArray;
            int var3 = numberArray.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                T t = var2[var4];
                if (ObjectUtils.compare(min, t) > 0) {
                    min = t;
                }
            }

            return min;
        }
    }

    public static long min(long... numberArray) {
        if (isEmpty(numberArray)) {
            throw new IllegalArgumentException("Number array must not empty !");
        } else {
            long min = numberArray[0];

            for(int i = 1; i < numberArray.length; ++i) {
                if (min > numberArray[i]) {
                    min = numberArray[i];
                }
            }

            return min;
        }
    }

    public static int min(int... numberArray) {
        if (isEmpty(numberArray)) {
            throw new IllegalArgumentException("Number array must not empty !");
        } else {
            int min = numberArray[0];

            for(int i = 1; i < numberArray.length; ++i) {
                if (min > numberArray[i]) {
                    min = numberArray[i];
                }
            }

            return min;
        }
    }

    public static short min(short... numberArray) {
        if (isEmpty(numberArray)) {
            throw new IllegalArgumentException("Number array must not empty !");
        } else {
            short min = numberArray[0];

            for(int i = 1; i < numberArray.length; ++i) {
                if (min > numberArray[i]) {
                    min = numberArray[i];
                }
            }

            return min;
        }
    }

    public static char min(char... numberArray) {
        if (isEmpty(numberArray)) {
            throw new IllegalArgumentException("Number array must not empty !");
        } else {
            char min = numberArray[0];

            for(int i = 1; i < numberArray.length; ++i) {
                if (min > numberArray[i]) {
                    min = numberArray[i];
                }
            }

            return min;
        }
    }

    public static byte min(byte... numberArray) {
        if (isEmpty(numberArray)) {
            throw new IllegalArgumentException("Number array must not empty !");
        } else {
            byte min = numberArray[0];

            for(int i = 1; i < numberArray.length; ++i) {
                if (min > numberArray[i]) {
                    min = numberArray[i];
                }
            }

            return min;
        }
    }

    public static double min(double... numberArray) {
        if (isEmpty(numberArray)) {
            throw new IllegalArgumentException("Number array must not empty !");
        } else {
            double min = numberArray[0];

            for(int i = 1; i < numberArray.length; ++i) {
                if (min > numberArray[i]) {
                    min = numberArray[i];
                }
            }

            return min;
        }
    }

    public static float min(float... numberArray) {
        if (isEmpty(numberArray)) {
            throw new IllegalArgumentException("Number array must not empty !");
        } else {
            float min = numberArray[0];

            for(int i = 1; i < numberArray.length; ++i) {
                if (min > numberArray[i]) {
                    min = numberArray[i];
                }
            }

            return min;
        }
    }

    public static <T extends Comparable<? super T>> T max(T[] numberArray) {
        if (isEmpty((Object[])numberArray)) {
            throw new IllegalArgumentException("Number array must not empty !");
        } else {
            T max = numberArray[0];

            for(int i = 1; i < numberArray.length; ++i) {
                if (ObjectUtils.compare(max, numberArray[i]) < 0) {
                    max = numberArray[i];
                }
            }

            return max;
        }
    }

    public static long max(long... numberArray) {
        if (isEmpty(numberArray)) {
            throw new IllegalArgumentException("Number array must not empty !");
        } else {
            long max = numberArray[0];

            for(int i = 1; i < numberArray.length; ++i) {
                if (max < numberArray[i]) {
                    max = numberArray[i];
                }
            }

            return max;
        }
    }

    public static int max(int... numberArray) {
        if (isEmpty(numberArray)) {
            throw new IllegalArgumentException("Number array must not empty !");
        } else {
            int max = numberArray[0];

            for(int i = 1; i < numberArray.length; ++i) {
                if (max < numberArray[i]) {
                    max = numberArray[i];
                }
            }

            return max;
        }
    }

    public static short max(short... numberArray) {
        if (isEmpty(numberArray)) {
            throw new IllegalArgumentException("Number array must not empty !");
        } else {
            short max = numberArray[0];

            for(int i = 1; i < numberArray.length; ++i) {
                if (max < numberArray[i]) {
                    max = numberArray[i];
                }
            }

            return max;
        }
    }

    public static char max(char... numberArray) {
        if (isEmpty(numberArray)) {
            throw new IllegalArgumentException("Number array must not empty !");
        } else {
            char max = numberArray[0];

            for(int i = 1; i < numberArray.length; ++i) {
                if (max < numberArray[i]) {
                    max = numberArray[i];
                }
            }

            return max;
        }
    }

    public static byte max(byte... numberArray) {
        if (isEmpty(numberArray)) {
            throw new IllegalArgumentException("Number array must not empty !");
        } else {
            byte max = numberArray[0];

            for(int i = 1; i < numberArray.length; ++i) {
                if (max < numberArray[i]) {
                    max = numberArray[i];
                }
            }

            return max;
        }
    }

    public static double max(double... numberArray) {
        if (isEmpty(numberArray)) {
            throw new IllegalArgumentException("Number array must not empty !");
        } else {
            double max = numberArray[0];

            for(int i = 1; i < numberArray.length; ++i) {
                if (max < numberArray[i]) {
                    max = numberArray[i];
                }
            }

            return max;
        }
    }

    public static float max(float... numberArray) {
        if (isEmpty(numberArray)) {
            throw new IllegalArgumentException("Number array must not empty !");
        } else {
            float max = numberArray[0];

            for(int i = 1; i < numberArray.length; ++i) {
                if (max < numberArray[i]) {
                    max = numberArray[i];
                }
            }

            return max;
        }
    }

    public static int[] swap(int[] array, int index1, int index2) {
        if (isEmpty(array)) {
            throw new IllegalArgumentException("Number array must not empty !");
        } else {
            int tmp = array[index1];
            array[index1] = array[index2];
            array[index2] = tmp;
            return array;
        }
    }

    public static long[] swap(long[] array, int index1, int index2) {
        if (isEmpty(array)) {
            throw new IllegalArgumentException("Number array must not empty !");
        } else {
            long tmp = array[index1];
            array[index1] = array[index2];
            array[index2] = tmp;
            return array;
        }
    }

    public static double[] swap(double[] array, int index1, int index2) {
        if (isEmpty(array)) {
            throw new IllegalArgumentException("Number array must not empty !");
        } else {
            double tmp = array[index1];
            array[index1] = array[index2];
            array[index2] = tmp;
            return array;
        }
    }

    public static float[] swap(float[] array, int index1, int index2) {
        if (isEmpty(array)) {
            throw new IllegalArgumentException("Number array must not empty !");
        } else {
            float tmp = array[index1];
            array[index1] = array[index2];
            array[index2] = tmp;
            return array;
        }
    }

    public static boolean[] swap(boolean[] array, int index1, int index2) {
        if (isEmpty(array)) {
            throw new IllegalArgumentException("Number array must not empty !");
        } else {
            boolean tmp = array[index1];
            array[index1] = array[index2];
            array[index2] = tmp;
            return array;
        }
    }

    public static byte[] swap(byte[] array, int index1, int index2) {
        if (isEmpty(array)) {
            throw new IllegalArgumentException("Number array must not empty !");
        } else {
            byte tmp = array[index1];
            array[index1] = array[index2];
            array[index2] = tmp;
            return array;
        }
    }

    public static char[] swap(char[] array, int index1, int index2) {
        if (isEmpty(array)) {
            throw new IllegalArgumentException("Number array must not empty !");
        } else {
            char tmp = array[index1];
            array[index1] = array[index2];
            array[index2] = tmp;
            return array;
        }
    }

    public static short[] swap(short[] array, int index1, int index2) {
        if (isEmpty(array)) {
            throw new IllegalArgumentException("Number array must not empty !");
        } else {
            short tmp = array[index1];
            array[index1] = array[index2];
            array[index2] = tmp;
            return array;
        }
    }

    public static <T> T[] swap(T[] array, int index1, int index2) {
        if (isEmpty(array)) {
            throw new IllegalArgumentException("Array must not empty !");
        } else {
            T tmp = array[index1];
            array[index1] = array[index2];
            array[index2] = tmp;
            return array;
        }
    }

    public static Object swap(Object array, int index1, int index2) {
        if (isEmpty(array)) {
            throw new IllegalArgumentException("Array must not empty !");
        } else {
            Object tmp = get(array, index1);
            Array.set(array, index1, Array.get(array, index2));
            Array.set(array, index2, tmp);
            return array;
        }
    }

    public static int emptyCount(Object... args) {
        int count = 0;
        if (isNotEmpty(args)) {
            Object[] var2 = args;
            int var3 = args.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Object element = var2[var4];
                if (ObjectUtils.isEmpty(element)) {
                    ++count;
                }
            }
        }

        return count;
    }

    public static boolean hasEmpty(Object... args) {
        if (isNotEmpty(args)) {
            Object[] var1 = args;
            int var2 = args.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Object element = var1[var3];
                if (ObjectUtils.isEmpty(element)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isAllEmpty(Object... args) {
        return emptyCount(args) == args.length;
    }

    public static boolean isAllNotEmpty(Object... args) {
        return !hasEmpty(args);
    }

    public static <T> T[] distinct(T[] array) {
        if (isEmpty(array)) {
            return array;
        } else {
            Set<T> set = new LinkedHashSet(array.length, 1.0F);
            Collections.addAll(set, array);
            return toArray((Collection)set, getComponentType((Object)array));
        }
    }
}

*/
