package org.example.class_proxy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.matcher.ElementMatchers;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class BookServiceTest_ByteBuddy {

    @Test
    public void proxy_byteBuddy() throws Exception {
        Class<? extends BookService> proxyClass = new ByteBuddy().subclass(BookService.class)
                .method(ElementMatchers.named("rent")).intercept(InvocationHandlerAdapter.of(new InvocationHandler() {
                    BookService bookService = new BookService();
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("로깅!!");
                        Object invoke = method.invoke(bookService, args);
                        System.out.println("다른 작업!!");
                        return invoke;
                    }
                }))
                .make().load(BookService.class.getClassLoader()).getLoaded();
        BookService bookService = proxyClass.getConstructor(null).newInstance();

        Book book = new Book();
        book.setTitle("spring");

        bookService.rent(book);
        bookService.returnBook(book);

    }
}