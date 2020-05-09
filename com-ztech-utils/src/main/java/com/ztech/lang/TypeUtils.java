/*
package com.ztech.lang;

import com.ztech.core.TableMap;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Map;

public class TypeUtils {
    public TypeUtils() {
    }

    public static Class<?> getClass(Type type) {
        if (null != type) {
            if (type instanceof Class) {
                return (Class)type;
            }

            if (type instanceof ParameterizedType) {
                return (Class)((ParameterizedType)type).getRawType();
            }

            if (type instanceof TypeVariable) {
                return (Class)((TypeVariable)type).getBounds()[0];
            }

            if (type instanceof WildcardType) {
                Type[] upperBounds = ((WildcardType)type).getUpperBounds();
                if (upperBounds.length == 1) {
                    return getClass(upperBounds[0]);
                }
            }
        }

        return null;
    }

    public static Type getType(Field field) {
        if (null == field) {
            return null;
        } else {
            Type type = field.getGenericType();
            if (null == type) {
                type = field.getType();
            }

            return (Type)type;
        }
    }

    public static Class<?> getClass(Field field) {
        return null == field ? null : field.getType();
    }

    public static Type getFirstParamType(Method method) {
        return getParamType(method, 0);
    }

    public static Class<?> getFirstParamClass(Method method) {
        return getParamClass(method, 0);
    }

    public static Type getParamType(Method method, int index) {
        Type[] types = getParamTypes(method);
        return null != types && types.length > index ? types[index] : null;
    }

    public static Class<?> getParamClass(Method method, int index) {
        Class<?>[] classes = getParamClasses(method);
        return null != classes && classes.length > index ? classes[index] : null;
    }

    public static Type[] getParamTypes(Method method) {
        return null == method ? null : method.getGenericParameterTypes();
    }

    public static Class<?>[] getParamClasses(Method method) {
        return null == method ? null : method.getParameterTypes();
    }

    public static Type getReturnType(Method method) {
        return null == method ? null : method.getGenericReturnType();
    }

    public static Class<?> getReturnClass(Method method) {
        return null == method ? null : method.getReturnType();
    }

    public static Type getTypeArgument(Type type) {
        return getTypeArgument(type, 0);
    }

    public static Type getTypeArgument(Type type, int index) {
        Type[] typeArguments = getTypeArguments(type);
        return null != typeArguments && typeArguments.length > index ? typeArguments[index] : null;
    }

    public static Type[] getTypeArguments(Type type) {
        if (null == type) {
            return null;
        } else {
            ParameterizedType parameterizedType = toParameterizedType(type);
            return null == parameterizedType ? null : parameterizedType.getActualTypeArguments();
        }
    }

    public static ParameterizedType toParameterizedType(Type type) {
        if (type instanceof ParameterizedType) {
            return (ParameterizedType)type;
        } else {
            return type instanceof Class ? toParameterizedType(((Class)type).getGenericSuperclass()) : null;
        }
    }

    public static Type[] getActualTypes(Type actualType, Class<?> typeDefineClass, Type... typeVariables) {
        if (!typeDefineClass.isAssignableFrom(getClass(actualType))) {
            throw new IllegalArgumentException("Parameter [superClass] must be assignable from [clazz]");
        } else {
            TypeVariable<?>[] typeVars = typeDefineClass.getTypeParameters();
            if (ArrayUtils.isEmpty(typeVars)) {
                return null;
            } else {
                Type[] actualTypeArguments = getTypeArguments(actualType);
                if (ArrayUtils.isEmpty(actualTypeArguments)) {
                    return null;
                } else {
                    int size = Math.min(actualTypeArguments.length, typeVars.length);
                    Map<TypeVariable<?>, Type> tableMap = new TableMap(typeVars, actualTypeArguments);
                    Type[] result = new Type[size];

                    for(int i = 0; i < typeVariables.length; ++i) {
                        result[i] = typeVariables[i] instanceof TypeVariable ? (Type)tableMap.get(typeVariables[i]) : typeVariables[i];
                    }

                    return result;
                }
            }
        }
    }

    public static Type getActualType(Type actualType, Class<?> typeDefineClass, Type typeVariable) {
        Type[] types = getActualTypes(actualType, typeDefineClass, typeVariable);
        return ArrayUtils.isNotEmpty(types) ? types[0] : null;
    }

    public static boolean isUnknow(Type type) {
        return null == type || type instanceof TypeVariable;
    }

    public static boolean hasTypeVeriable(Type... types) {
        Type[] var1 = types;
        int var2 = types.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Type type = var1[var3];
            if (type instanceof TypeVariable) {
                return true;
            }
        }

        return false;
    }
}

*/
