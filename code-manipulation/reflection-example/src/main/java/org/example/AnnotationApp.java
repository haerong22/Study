package org.example;

import java.util.Arrays;

public class AnnotationApp {

    public static void main(String[] args) {

        // 원하는 필드의 애노테이션의 값 가져오기
        Arrays.stream(Book.class.getDeclaredFields()).forEach(f -> {
            Arrays.stream(f.getAnnotations()).forEach(a -> {
                if (a instanceof MyAnnotation) {
                    MyAnnotation myAnnotation = (MyAnnotation) a;
                    System.out.println(myAnnotation.name());
                    System.out.println(myAnnotation.number());
                }
            });
        });


//        // 필드에 사용한 애노테이션 가져오기
//        Arrays.stream(Book.class.getDeclaredFields()).forEach(f -> {
//            Arrays.stream(f.getAnnotations()).forEach(System.out::println);
//        });


//        // 클래스에 있는 애노테이션 가져오기
//        Arrays.stream(Book.class.getAnnotations()).forEach(System.out::println);
    }
}
