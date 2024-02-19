package org.example.proxy;

public class BookServiceProxy implements BookService {

    BookService bookService;

    public BookServiceProxy(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void rent(Book book) {
        System.out.println("로깅!!");
        bookService.rent(book);
        System.out.println("다른기능!!");
    }

    @Override
    public void returnBook(Book book) {

    }
}
