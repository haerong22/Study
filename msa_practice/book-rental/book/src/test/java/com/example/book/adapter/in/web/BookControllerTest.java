package com.example.book.adapter.in.web;

import com.example.book.application.port.in.AddBookUseCase;
import com.example.book.application.port.in.command.AddBookCommand;
import com.example.book.domain.model.Book;
import com.example.book.domain.model.vo.BookDesc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static com.example.book.domain.model.vo.BookStatus.ENTERED;
import static com.example.book.domain.model.vo.Classification.COMPUTER;
import static com.example.book.domain.model.vo.Location.JEONGJA;
import static com.example.book.domain.model.vo.Source.SUPPLY;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @MockBean
    private AddBookUseCase addBookUseCase;

    @Test
    @DisplayName("도서를 등록한다.")
    void addBook() throws Exception {
        // given
        AddBookCommand request = AddBookCommand.builder()
                .title("test")
                .description("test")
                .author("test")
                .isbn("test")
                .publicationDate(LocalDate.of(2023, 1, 1))
                .source("SUPPLY")
                .classification("ART")
                .location("PANGYO")
                .build();

        when(addBookUseCase.addBook(any(AddBookCommand.class))).thenReturn(createTestBook());

        // when

        // then
        mockMvc.perform(
                        post("/api/v1/book")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isCreated())
        ;

        verify(addBookUseCase).addBook(any(AddBookCommand.class));
    }

    public Book createTestBook() {
        return Book.builder()
                .title("엔터프라이즈 아키텍처 패턴")
                .desc(BookDesc.builder()
                        .description("엔터프라이즈 아키텍처 패턴을 잘 설명해주는 도서")
                        .author("마틴파울러")
                        .isbn("978-89-954321-0-5")
                        .publicationDate(LocalDate.of(2023, 1, 1))
                        .source(SUPPLY)
                        .build())
                .classification(COMPUTER)
                .bookStatus(ENTERED)
                .location(JEONGJA)
                .build();
    }
}