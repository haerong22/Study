package com.example.book.application.port.in;

import com.example.book.application.port.in.command.AddBookCommand;
import com.example.book.domain.model.Book;

public interface AddBookUseCase {

    Book addBook(AddBookCommand command);
}
