package org.example;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@Inherited
public @interface MyAnnotation {

    String name() default "kim"; // 기본값을 주지 않으면 애노테이션 사용시 반드시 값을 넣어야 한다.

    int number() default 100;
}
