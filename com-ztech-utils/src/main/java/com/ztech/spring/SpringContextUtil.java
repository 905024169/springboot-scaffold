/*
package com.ztech.spring;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//



import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import com.ztech.reflect.ReflectUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtil implements ApplicationContextAware, DisposableBean {
    private static Logger logger = LoggerFactory.getLogger(SpringContextUtil.class);
    public static ApplicationContext context;
    public static ApplicationHome applicationHome;

    public SpringContextUtil() {
    }

    public static ApplicationContext getContext() {
        return context;
    }

    public static void setContext(ApplicationContext context) {
        context = context;
    }

    public static <T> T getBean(String name) {
        assertContextInjected();
        return context.getBean(name);
    }

    public static Object getBeanObj(String name) {
        assertContextInjected();
        return context.getBean(name);
    }

    public static <T> boolean containsBean(Class<T> requiredType) {
        List<T> beans = getBeansOfTypeL(requiredType);
        if (beans != null) {
            return beans.size() > 0;
        } else {
            return false;
        }
    }

    public static <T> T getBean(Class<T> requiredType) {
        assertContextInjected();
        return context.getBean(requiredType);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> requiredType) {
        assertContextInjected();
        return context.getBeansOfType(requiredType);
    }

    public static <T> List<T> getBeansOfTypeL(Class<T> requiredType) {
        Map<String, T> map = getBeansOfType(requiredType);
        List<T> list = new ArrayList();
        map.forEach((k, v) -> {
            list.add(v);
        });
        return list;
    }

    public static <T> T[] getBeansOfTypeA(Class<T> requiredType) {
        List<T> list = getBeansOfTypeL(requiredType);
        return (Object[])list.toArray();
    }

    public static boolean containsBean(String name) {
        assertContextInjected();
        return context.containsBean(name);
    }

    public static boolean isSingleton(String name) {
        assertContextInjected();
        return context.isSingleton(name);
    }

    public static Class getType(String name) {
        assertContextInjected();
        return context.getType(name);
    }

    public static String[] getAliases(String name) {
        assertContextInjected();
        return context.getAliases(name);
    }

    public static void clearHolder() {
        if (logger.isDebugEnabled()) {
            logger.debug("清除SpringContextUtil中的context:" + context);
        }

        context = null;
    }

    private static void assertContextInjected() {
        Validate.validState(context != null, "context属性未注入, 请在applicationContext.xml中定义SpringContextUtil.", new Object[0]);
    }

    public void destroy() throws Exception {
        clearHolder();
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        setContext(applicationContext);
    }

    public static ApplicationHome getApplicationHome() {
        if (applicationHome == null) {
            applicationHome = new ApplicationHome((Class)null);
        }

        return applicationHome;
    }

    public static Class<?> getStartClass() {
        return (Class) ReflectUtils.invoke(getApplicationHome(), "getStartClass", new Object[0]);
    }
}
*/
