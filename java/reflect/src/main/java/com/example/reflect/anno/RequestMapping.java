package com.example.reflect.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD}) // 어노테이션 동작위치
@Retention(RetentionPolicy.RUNTIME) // 어노테이션 동작시기
public @interface RequestMapping {
    String value();
}
