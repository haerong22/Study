package org.example.grpcserver.domain.books;

import bookstore.BookServiceGrpc;
import bookstore.Bookstore;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.example.grpcserver.utils.TimestampConverter;

import java.util.List;

@GrpcService
@RequiredArgsConstructor
public class BookGrpcService extends BookServiceGrpc.BookServiceImplBase {

    private final BookService bookService;

    @Override
    public void addBook(Bookstore.AddBookRequest request, StreamObserver<Bookstore.Book> responseObserver) {
        Book newBook = new Book();
        newBook.setTitle(request.getTitle());
        newBook.setPublisher(request.getPublisher());
        newBook.setPublishedDate(TimestampConverter.fromProto(request.getPublishedDate()));

        Book saved = bookService.saveBook(newBook);
        responseObserver.onNext(Bookstore.Book.newBuilder()
                .setId(saved.getId())
                .setTitle(saved.getTitle())
                .setPublisher(saved.getPublisher())
                .setPublishedDate(TimestampConverter.toProto(saved.getPublishedDate()))
                .build()
        );

        responseObserver.onCompleted();
    }

    @Override
    public void getBookDetails(Bookstore.GetBookDetailsRequest request, StreamObserver<Bookstore.Book> responseObserver) {
        Bookstore.Book bookDetail = bookService.findById(request.getBookId())
                .map(book -> Bookstore.Book.newBuilder()
                        .setId(book.getId())
                        .setTitle(book.getTitle())
                        .setPublishedDate(TimestampConverter.toProto(book.getPublishedDate()))
                        .setPublisher(book.getPublisher())
                        .build())
                .orElseThrow();

        responseObserver.onNext(bookDetail);

        responseObserver.onCompleted();
    }

    @Override
    public void listBooks(Bookstore.ListBooksRequest request, StreamObserver<Bookstore.Book> responseObserver) {
        List<Book> books = bookService.findAll();

        for (Book book : books) {
            responseObserver.onNext(
                    Bookstore.Book.newBuilder()
                            .setId(book.getId())
                            .setTitle(book.getTitle())
                            .setPublishedDate(TimestampConverter.toProto(book.getPublishedDate()))
                            .setPublisher(book.getPublisher())
                            .build()
            );
        }

        responseObserver.onCompleted();
    }

    @Override
    public void searchBooksByAuthor(Bookstore.SearchBooksByAuthorRequest request, StreamObserver<Bookstore.Book> responseObserver) {
        List<Book> books = bookService.findByAuthorName(request.getAuthorName());

        for (Book book : books) {
            responseObserver.onNext(Bookstore.Book.newBuilder()
                    .setId(book.getId())
                    .setTitle(book.getTitle())
                    .setPublishedDate(TimestampConverter.toProto(book.getPublishedDate()))
                    .setPublisher(book.getPublisher())
                    .build());
        }

        responseObserver.onCompleted();
    }
}
