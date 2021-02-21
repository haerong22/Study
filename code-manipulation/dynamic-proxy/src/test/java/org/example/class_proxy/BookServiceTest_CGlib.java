package org.example.class_proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;

import java.lang.reflect.Method;

public class BookServiceTest_CGlib {

    @Test
    public void proxy_cglib() {
        MethodInterceptor handler = new MethodInterceptor() {
            BookService bookService = new BookService();
            @Override
            public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                if (method.getName().equals("rent")){
                    System.out.println("로깅!!");
                    Object invoke = method.invoke(bookService, args);
                    System.out.println("다른 작업!!");
                    return invoke;
                }
                return method.invoke(bookService, args);
            }
        };
        BookService bookService = (BookService) Enhancer.create(BookService.class, handler);

        Book book = new Book();
        book.setTitle("spring");

        bookService.rent(book);
        bookService.returnBook(book);

    }
}