/*
package com.ztech.lang;


import com.ztech.collect.MapUtils;
import com.ztech.core.CompareUtils;
import com.ztech.core.IterUtil;
import com.ztech.core.convert.Convert;
import com.ztech.core.exception.UtilException;
import com.ztech.io.IOUtils;
import com.ztech.io.IoUtil;
import com.ztech.reflect.ClassUtil;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.BooleanUtils;
import org.nustaq.serialization.FSTConfiguration;
import org.springframework.beans.BeanUtils;
import org.springframework.core.NamedThreadLocal;
import org.springframework.util.FastByteArrayOutputStream;

public class ObjectUtil extends org.apache.commons.lang3.ObjectUtils {
    private static ThreadLocal<FSTConfiguration> fst = new NamedThreadLocal<FSTConfiguration>("FSTConfiguration") {
        public FSTConfiguration initialValue() {
            return FSTConfiguration.createDefaultConfiguration();
        }
    };

    public ObjectUtils() {
    }

    public static boolean equal(Object obj1, Object obj2) {
        return Objects.equals(obj1, obj2);
    }

    public static boolean notEqual(Object obj1, Object obj2) {
        return !equal(obj1, obj2);
    }

    public static boolean isNullOrEmpty(Object obj) {
        try {
            if (obj == null) {
                return true;
            } else if (obj instanceof CharSequence) {
                return ((CharSequence)obj).length() == 0;
            } else if (obj instanceof Number) {
                return ((Number)obj).intValue() == 0;
            } else if (obj instanceof Collection) {
                return ((Collection)obj).isEmpty();
            } else if (obj instanceof Map) {
                return ((Map)obj).isEmpty();
            } else if (!(obj instanceof Object[])) {
                return false;
            } else {
                Object[] object = (Object[])((Object[])obj);
                if (object.length == 0) {
                    return true;
                } else {
                    boolean empty = true;

                    for(int i = 0; i < object.length; ++i) {
                        if (!isNullOrEmpty(object[i])) {
                            empty = false;
                            break;
                        }
                    }

                    return empty;
                }
            }
        } catch (Exception var4) {
            return true;
        }
    }

    public static Double toDouble(Object val) {
        if (val == null) {
            return 0.0D;
        } else {
            try {
                return NumberUtils.toDouble(StringUtils.trim(val.toString()));
            } catch (Exception var2) {
                return 0.0D;
            }
        }
    }

    public static Float toFloat(Object val) {
        return toDouble(val).floatValue();
    }

    public static Long toLong(Object val) {
        return toDouble(val).longValue();
    }

    public static Integer toInteger(Object val) {
        return toLong(val).intValue();
    }

    public static Boolean toBoolean(Object val) {
        return val == null ? false : BooleanUtils.toBoolean(val.toString()) || "1".equals(val.toString());
    }

    public static String toString(Object obj, String defaultVal) {
        return obj == null ? defaultVal : obj.toString();
    }

    public static String toStringIgnoreNull(Object val) {
        return toStringIgnoreNull(val, "");
    }

    public static String toStringIgnoreNull(Object val, String defaultVal) {
        String str = toString(val);
        return !"".equals(str) && !"null".equals(str.trim().toLowerCase()) ? str : defaultVal;
    }

    public static Object copyBean(Object source, String... ignoreProperties) {
        if (source == null) {
            return null;
        } else {
            Object target = BeanUtils.instantiate(source.getClass());
            BeanUtils.copyProperties(source, target, ignoreProperties);
            return target;
        }
    }

    public static void annotationToObject(Object annotation, Object object) {
        if (annotation != null && object != null) {
            Class<?> annotationClass = annotation.getClass();
            Class<?> objectClass = object.getClass();
            Method[] var4 = objectClass.getMethods();
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Method m = var4[var6];
                if (StringUtils.startsWith(m.getName(), "set")) {
                    try {
                        String s = StringUtils.uncapitalize(StringUtils.substring(m.getName(), 3));
                        Object obj = annotationClass.getMethod(s).invoke(annotation);
                        if (obj != null && !"".equals(obj.toString())) {
                            m.invoke(object, obj);
                        }
                    } catch (Exception var10) {
                        ;
                    }
                }
            }
        }

    }

    public static Object unserialize(byte[] bytes) {
        if (bytes == null) {
            return null;
        } else {
            long beginTime = System.currentTimeMillis();
            Object object = null;
            ByteArrayInputStream bais = null;
            ObjectInputStream ois = null;

            try {
                if (bytes.length > 0) {
                    bais = new ByteArrayInputStream(bytes);
                    ois = new ObjectInputStream(bais);
                    object = ois.readObject();
                }
            } catch (Exception var10) {
                var10.printStackTrace();
            } finally {
                IOUtils.closeQuietly(ois);
                IOUtils.closeQuietly(bais);
            }

            long totalTime = System.currentTimeMillis() - beginTime;
            if (totalTime > 3000L) {
                System.out.println("Unserialize time: " + TimeUtils.formatDateAgo(totalTime));
            }

            return object;
        }
    }

    public static byte[] serializeFst(Object object) {
        if (object == null) {
            return null;
        } else {
            long beginTime = System.currentTimeMillis();
            byte[] bytes = ((FSTConfiguration)fst.get()).asByteArray(object);
            long totalTime = System.currentTimeMillis() - beginTime;
            if (totalTime > 3000L) {
                System.out.println("Fst serializer time: " + TimeUtils.formatDateAgo(totalTime));
            }

            return bytes;
        }
    }

    public static Object unserializeFst(byte[] bytes) {
        if (bytes == null) {
            return null;
        } else {
            long beginTime = System.currentTimeMillis();
            Object object = ((FSTConfiguration)fst.get()).asObject(bytes);
            long totalTime = System.currentTimeMillis() - beginTime;
            if (totalTime > 3000L) {
                System.out.println("Fst unserialize time: " + TimeUtils.formatDateAgo(totalTime));
            }

            return object;
        }
    }

    public static Object cloneBean(Object source) {
        if (source == null) {
            return null;
        } else {
            byte[] bytes = serializeFst(source);
            Object target = unserializeFst(bytes);
            return target;
        }
    }

    public static <T> byte[] serialize(T obj) {
        if (!(obj instanceof Serializable)) {
            return null;
        } else {
            FastByteArrayOutputStream byteOut = new FastByteArrayOutputStream();
            ObjectOutputStream oos = null;

            try {
                oos = new ObjectOutputStream(byteOut);
                oos.writeObject(obj);
                oos.flush();
            } catch (Exception var7) {
                throw new UtilException(var7);
            } finally {
                IoUtil.close(oos);
            }

            return byteOut.toByteArray();
        }
    }

    public static <T> T deserialize(byte[] bytes) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception var3) {
            throw new UtilException(var3);
        }
    }

    public static boolean isBasicType(Object object) {
        return ClassUtil.isBasicType(object.getClass());
    }

    public static boolean isValidIfNumber(Object obj) {
        return obj instanceof Number ? NumberUtils.isValidNumber((Number)obj) : true;
    }

    public static <T extends Comparable<? super T>> int compare(T c1, T c2) {
        return CompareUtils.compare(c1, c2);
    }

    public static <T extends Comparable<? super T>> int compare(T c1, T c2, boolean nullGreater) {
        return CompareUtils.compare(c1, c2, nullGreater);
    }

    public static Class<?> getTypeArgument(Object obj) {
        return getTypeArgument(obj, 0);
    }

    public static Class<?> getTypeArgument(Object obj, int index) {
        return ClassUtil.getTypeArgument(obj.getClass(), index);
    }

    public static String toString(Object obj) {
        if (null == obj) {
            return "null";
        } else {
            return obj instanceof Map ? obj.toString() : Convert.toStr(obj);
        }
    }

    public static int emptyCount(Object... objs) {
        return ArrayUtils.emptyCount(objs);
    }

    public static boolean hasEmpty(Object... objs) {
        return ArrayUtils.hasEmpty(objs);
    }

    public static boolean isAllEmpty(Object... objs) {
        return ArrayUtils.isAllEmpty(objs);
    }

    public static boolean isAllNotEmpty(Object... objs) {
        return ArrayUtils.isAllNotEmpty(objs);
    }

    public static boolean isNull(Object obj) {
        return null == obj || obj.equals((Object)null);
    }

    public static boolean isNotNull(Object obj) {
        return null != obj && !obj.equals((Object)null);
    }

    public static boolean isEmpty(Object obj) {
        if (null == obj) {
            return true;
        } else if (obj instanceof CharSequence) {
            return StringUtils.isEmpty((CharSequence)obj);
        } else if (obj instanceof Map) {
            return MapUtils.isEmpty((Map)obj);
        } else if (obj instanceof Iterable) {
            return IterUtil.isEmpty((Iterable)obj);
        } else if (obj instanceof Iterator) {
            return IterUtil.isEmpty((Iterator)obj);
        } else {
            return ArrayUtils.isArray(obj) ? ArrayUtils.isEmpty(obj) : false;
        }
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }
}

*/
