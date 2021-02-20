package org.example.classapi2;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class App {

    public static void main(String[] args) throws Exception {
        /**
         * Class API : 클래스 정보 수정, 실행 하기
         */


        // 메소드 실행하기
        Class<?> bookClass = Class.forName("org.example.classapi2.Book");
        Book book = (Book) bookClass.getConstructor().newInstance();

        Method c = Book.class.getDeclaredMethod("c");
        c.setAccessible(true);
        c.invoke(book);

        Method sum = Book.class.getDeclaredMethod("sum", int.class, int.class);
        int invoke = (int) sum.invoke(book, 1, 2);
        System.out.println(invoke);


//        // 필드 값 접근/수정하기
//        Field a = Book.class.getDeclaredField("A");
//        System.out.println(a.get(null)); // static 필드 이므로 null
//        a.set(null, "AAAAAA");
//        System.out.println(a.get(null));
//
//
//        Class<?> bookClass = Class.forName("org.example.classapi2.Book");
//        Book book = (Book) bookClass.getConstructor().newInstance();
//
//        Field b = Book.class.getDeclaredField("B");
//        b.setAccessible(true); // private 필드 이므로 접근 할 수 있도록 변경
//        System.out.println(b.get(book)); // 특정 인스턴스가 필요
//        b.set(book, "BBBBBB");
//        System.out.println(b.get(book));


//        // 생성자로 인스턴스 만들기
//        Class<?> bookClass = Class.forName("org.example.classapi2.Book");
//        Constructor<?> constructor = bookClass.getConstructor();
//        Constructor<?> constructor1 = bookClass.getConstructor(String.class);
//        Book book = (Book) constructor.newInstance();
//        Book book1 = (Book) constructor1.newInstance("myBook");
//
//        System.out.println(book);
//        System.out.println(book1);
    }
}
