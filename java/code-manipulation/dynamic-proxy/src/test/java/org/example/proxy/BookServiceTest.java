package org.example.proxy;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class BookServiceTest {

//    BookService bookService = new BookServiceProxy(new DefaultBookService());
    BookService bookService = (BookService) Proxy.newProxyInstance(
        BookService.class.getClassLoader(), new Class[]{BookService.class},
        new InvocationHandler() {
            BookService bookService = new DefaultBookService();
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getName().equals("rent")) {
                    System.out.println("로깅!!");
                    Object invoke = method.invoke(bookService, args);
                    System.out.println("다른기능!!");
                    return invoke;
                }
                return method.invoke(bookService, args);
            }
        });

    @Test
    public void proxy() {

        Book book = new Book();
        book.setTitle("spring");

        bookService.rent(book);
        bookService.returnBook(book);
    }
}