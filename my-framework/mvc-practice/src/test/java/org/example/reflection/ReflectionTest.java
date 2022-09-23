package org.example.reflection;

import org.example.reflection.annotation.Controller;
import org.example.reflection.annotation.Service;
import org.example.reflection.model.User;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

public class ReflectionTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    void controllerScan() {
        Set<Class<?>> beans = getTypeAnnotationWith(List.of(Controller.class, Service.class));

        logger.debug("beans: [{}]", beans);
    }

    @Test
    void showClass() {
        Class<User> clazz = User.class;

        logger.debug(clazz.getName());

        logger.debug("User all declared fields: [{}]", Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList()));
        logger.debug("User all declared constructor: [{}]", Arrays.stream(clazz.getDeclaredConstructors()).collect(Collectors.toList()));
        logger.debug("User all declared method: [{}]", Arrays.stream(clazz.getDeclaredMethods()).collect(Collectors.toList()));
    }

    @Test
    void load() throws ClassNotFoundException {
        Class<User> clazz = User.class;

        User user = new User("1", "test");
        Class<? extends User> clazz2 = user.getClass();

        Class<?> clazz3 = Class.forName("org.example.reflection.model.User");

        logger.debug("clazz: [{}]", clazz);
        logger.debug("clazz2: [{}]", clazz2);
        logger.debug("clazz3: [{}]", clazz3);

        assertThat(clazz == clazz2).isTrue();
        assertThat(clazz == clazz3).isTrue();
        assertThat(clazz2 == clazz3).isTrue();
    }

    private static Set<Class<?>> getTypeAnnotationWith(List<Class<? extends Annotation>> annotations) {
        Reflections reflections = new Reflections("org.example");

        Set<Class<?>> beans = new HashSet<>();

        annotations.forEach(annotation -> beans.addAll(reflections.getTypesAnnotatedWith(annotation)));

        return beans;
    }
}
