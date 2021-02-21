package org.example.proxy;

import org.junit.Test;

public class BookServiceTest {

    BookService bookService = new BookServiceProxy(new DefaultBookService());

    @Test
    public void proxy() {

        Book book = new Book();
        book.setTitle("spring");

        bookService.rent(book);
    }
}