package org.example.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ModelMapper {

    public <R, T> T map(R resource, Class<T> target) {
        T instance = null;
        try {
            instance = target.getConstructor().newInstance();
            Field[] declaredFields = resource.getClass().getDeclaredFields();
            Method[] declaredMethods = target.getDeclaredMethods();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                for (Method method : declaredMethods) {
                    String setter = setterMethod(field.getName());
                    if (method.getName().equals(setter)) {
                        try {
                            method.invoke(instance, field.get(resource));
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    private String setterMethod(String fieldName) {
        return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }
}
