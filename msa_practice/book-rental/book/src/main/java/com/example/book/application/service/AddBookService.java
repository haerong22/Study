package com.example.book.application.service;

import com.example.book.application.port.in.AddBookUseCase;
import com.example.book.application.port.in.command.AddBookCommand;
import com.example.book.application.port.out.BookPort;
import com.example.book.domain.model.Book;
import com.example.book.domain.model.vo.Classification;
import com.example.book.domain.model.vo.Location;
import com.example.book.domain.model.vo.Source;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AddBookService implements AddBookUseCase {

    private final BookPort bookPort;

    @Override
    public Book addBook(AddBookCommand command) {
        Book book = Book.enterBook(
                command.getTitle(),
                command.getAuthor(),
                command.getIsbn(),
                command.getDescription(),
                command.getPublicationDate(),
                Source.valueOf(command.getSource()),
                Classification.valueOf(command.getClassification()),
                Location.valueOf(command.getLocation())
        );

        return bookPort.save(book);
    }
}
