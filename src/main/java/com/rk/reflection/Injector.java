package com.rk.reflection;

import com.rk.annotation.Inject;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class Injector {

    public <T> void InjectFields(T object) throws Exception {
        Class<?> clazz = object.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            Inject annotation = declaredField.getAnnotation(Inject.class);
            if (annotation != null) {
                Object objectFromAnnotation;
                if (annotation.value().getTypeName().equals(Void.class.getTypeName())) {
                    declaredField.setAccessible(true);
                    objectFromAnnotation = Class.forName(declaredField.getGenericType().getTypeName()).getDeclaredConstructor().newInstance();
                    declaredField.set(object,objectFromAnnotation);
                    declaredField.setAccessible(false);
                } else {
                    declaredField.setAccessible(true);
                    objectFromAnnotation= annotation.value().getDeclaredConstructor().newInstance();
                    declaredField.set(object,objectFromAnnotation);
                    declaredField.setAccessible(false);
                }
            }
        }
    }
}
