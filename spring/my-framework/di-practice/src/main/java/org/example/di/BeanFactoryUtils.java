package org.example.di;

import org.example.annotation.Inject;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.util.Set;

public class BeanFactoryUtils {

    public static Constructor<?> getInjectedConstructor(Class<?> clazz) {
        Set<Constructor> injectedConstructor = ReflectionUtils.getAllConstructors(clazz, ReflectionUtils.withAnnotation(Inject.class));

        if (injectedConstructor.isEmpty()) {
            return null;
        }

        return injectedConstructor.iterator().next();
    }
}
