package com.rk.reflection;

import com.rk.annotation.Run;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Starter {

    public <T> void run(T object) throws InvocationTargetException, IllegalAccessException {
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            Run run = method.getDeclaredAnnotation(Run.class);
            if (run != null) {
                method.setAccessible(true);
                method.invoke(object);
                method.setAccessible(false);
            }
        }
    }
}
