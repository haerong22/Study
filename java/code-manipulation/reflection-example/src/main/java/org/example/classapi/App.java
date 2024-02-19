package org.example.classapi;

import java.lang.reflect.Modifier;
import java.util.Arrays;

public class App {
    public static void main( String[] args ) throws ClassNotFoundException {


        /**
         * Class<T> 에 접근하여 정보 가져오기
         */

        // 메소드
        Arrays.stream(Book.class.getDeclaredMethods()).forEach(m -> {
            int modifiers = m.getModifiers();
            System.out.println(m);
            System.out.println(Modifier.isPrivate(modifiers));
            System.out.println(m.getReturnType());
        });



//        // 접근지시자
//        Arrays.stream(Book.class.getDeclaredFields()).forEach(f -> {
//            int modifiers = f.getModifiers();
//            System.out.println(f);
//            System.out.println(Modifier.isPrivate(modifiers));
//            System.out.println(Modifier.isStatic(modifiers));
//        });


//        // 메소드 가져오기
//        Class<Book> bookClass = Book.class;
//        System.out.println("getMethods() : Object 에서 상속받은 메소드도 가져온다.");
//        Arrays.stream(bookClass.getMethods()).forEach(System.out::println);
//
//        System.out.println("===============================================");
//
//        System.out.println("getDeclaredMethods() : 인스턴스에 있는 메소드만 가져온다.");
//        Arrays.stream(bookClass.getDeclaredMethods()).forEach(System.out::println);
//
//        System.out.println("===============================================");
//
//        System.out.println("getDeclaredConstructors() : 인스턴스에 있는 생성자를 가져온다.");
//        Arrays.stream(bookClass.getDeclaredConstructors()).forEach(System.out::println);
//
//        System.out.println("===============================================");
//
//        System.out.println("getSuperclass() : 상속받은 부모클래스를 가져온다.");
//        System.out.println(MyBook.class.getSuperclass());
//
//        System.out.println("===============================================");
//
//        System.out.println("getInterfaces() : 상속받은 인터페이스를 가져온다.");
//        Arrays.stream(MyBook.class.getInterfaces()).forEach(System.out::println);


//        // field 의 값을 가져오려면 생성한 인스턴스가 있어야 한다.
//        Book book = new Book();
//        Arrays.stream(book.getClass().getDeclaredFields())
//                .forEach(f -> {
//                    try {
//                        f.setAccessible(true); // 모든 필드에 접근 가능
//                        System.out.printf("%s, %s\n", f, f.get(book));
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    }
//                });

//        // 필드 가져오기
//        Class<Book> bookClass = Book.class;
//
//        // getFields() -> public 만 접근
//        Field[] fields = bookClass.getFields();
//        Arrays.stream(fields).forEach(System.out::println);
//
//        System.out.println("===============================================");
//
//        // getDeclaredFields() -> 모든 필드 접근
//        Field[] declaredFields = bookClass.getDeclaredFields();
//        Arrays.stream(declaredFields).forEach(System.out::println);


        /**
         * Class<T> 에 접근하기
         */


//        // 클래시 로딩시 생성된 인스턴스로 접근
//        Class<Book> bookClass = Book.class;
//        System.out.println(bookClass.getName());
//        System.out.println("============================");
//
//        // 생성한 인스턴스로 접근
//        Book book = new Book();
//        Class<? extends Book> bookClass1 = book.getClass();
//        System.out.println(bookClass1.getName());
//        System.out.println("============================");
//
//        // 문자열로 접근
//        Class<?> bookClass2 = Class.forName("org.example.annotation.Book");
//        System.out.println(bookClass2.getName());
    }
}
