package org.example.mvc;

import org.example.mvc.annotation.Controller;
import org.example.mvc.annotation.RequestMapping;
import org.example.mvc.controller.HandlerKey;
import org.example.mvc.controller.RequestMethod;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AnnotationHandlerMapping implements HandlerMapping {

    private final Object[] basePackage;

    private Map<HandlerKey, AnnotationHandler> handlers = new HashMap<>();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void init() {
        Reflections reflections = new Reflections(basePackage);

        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Controller.class);

        classes.forEach(clazz -> {
            Arrays.stream(clazz.getDeclaredMethods()).forEach(method -> {
                RequestMapping requestMapping = method.getDeclaredAnnotation(RequestMapping.class);

                Arrays.stream(getRequestMethods(requestMapping))
                        .forEach(requestMethod -> handlers.put(
                                new HandlerKey(requestMethod, requestMapping.value()), new AnnotationHandler(clazz, method)
                        ));
            });
        });
    }

    private RequestMethod[] getRequestMethods(RequestMapping requestMapping) {
        return requestMapping.method();
    }

    @Override
    public Object findHandler(HandlerKey handlerKey) {
        return handlers.get(handlerKey);
    }
}
