package com.example.best.web;

import com.example.best.domain.model.BestBook;
import com.example.best.domain.service.BestBookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BestBookController.class)
class BestBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private BestBookService bestBookService;

    @Test
    @DisplayName("베스트 도서리스트를 조회한다.")
    void getAllBooks() throws Exception {
        // given

        when(bestBookService.getAllBooks()).thenReturn(List.of());

        // when

        // then
        mockMvc.perform(
                        get("/api/v1/books")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
        ;

        verify(bestBookService, times(1)).getAllBooks();
    }

    @Test
    @DisplayName("도서를 조회한다.")
    void getBookById() throws Exception {
        // given
        when(bestBookService.getBookById(anyString()))
                .thenReturn(Optional.of(BestBook.builder().build()));

        // when

        // then
        mockMvc.perform(
                        get("/api/v1/books/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
        ;

        verify(bestBookService, times(1)).getBookById("1");
    }

    @Test
    @DisplayName("도서 조회시 도서가 없으면 404 리턴한다.")
    void getBookByIdNotFound() throws Exception {
        // given
        when(bestBookService.getBookById(anyString()))
                .thenReturn(Optional.empty());

        // when

        // then
        mockMvc.perform(
                        get("/api/v1/books/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist())
        ;

        verify(bestBookService, times(1)).getBookById("1");
    }

    @Test
    @DisplayName("베스트 도서를 등록한다.")
    void createBook() throws Exception {
        // given
        BestBook request = BestBook.builder()
                .build();

        when(bestBookService.saveBook(any(BestBook.class)))
                .thenReturn(BestBook.builder().build());

        // when

        // then
        mockMvc.perform(
                        post("/api/v1/books")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").exists())
        ;

        verify(bestBookService, times(1)).saveBook(any(BestBook.class));
    }

    @Test
    @DisplayName("베스트 도서를 수정한다.")
    void updateBook() throws Exception {
        // given
        BestBook request = BestBook.builder()
                .build();

        when(bestBookService.update(anyString(), any(BestBook.class)))
                .thenReturn(BestBook.builder().build());

        // when

        // then
        mockMvc.perform(
                        put("/api/v1/books/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
        ;

        verify(bestBookService, times(1)).update(anyString(), any(BestBook.class));
    }

    @Test
    @DisplayName("베스트 도서를 수정시 도서가 없으면 404 리턴한다..")
    void updateBookNotExist() throws Exception {
        // given
        BestBook request = BestBook.builder()
                .build();

        when(bestBookService.update(anyString(), any(BestBook.class)))
                .thenReturn(null);

        // when

        // then
        mockMvc.perform(
                        put("/api/v1/books/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist())
        ;

        verify(bestBookService, times(1)).update(anyString(), any(BestBook.class));
    }
}