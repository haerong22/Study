package com.example.book.web;

import com.example.book.domain.Book;
import com.example.book.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// 단위테스트 ( Controller 관련 로직만 테스트 - Filter, ControllerAdvice , ... )

@Slf4j
@WebMvcTest
class BookControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean // IoC 환경에 등록
    private BookService bookService;

    // BDDMockito 패턴 given, when, then
    @Test
    public void save_테스트() throws Exception {
        // given ( 테스트를 하기 위한 준비 )
        Book book = new Book(null, "스프링", "kim");
        String content = new ObjectMapper().writeValueAsString(book);
        when(bookService.저장하기(book)).thenReturn(new Book(1L, "스프링", "kim"));

        // when ( 테스트 실행 )
        ResultActions resultActions = mockMvc.perform(post("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON));

        // then ( 검증 )
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("스프링"))
                .andDo(print());
    }

    @Test
    public void findAll_테스트() throws Exception {
        // given
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "스프링", "kim"));
        books.add(new Book(2L, "리액트", "kim"));
        when(bookService.모두가져오기()).thenReturn(books);

        // when
        ResultActions resultActions = mockMvc.perform(get("/book")
                .accept(MediaType.APPLICATION_JSON_UTF8));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.[0].title").value("스프링"))
                .andDo(print());

   }

   @Test
   public void findById_테스트() throws Exception {
       // given
       Long id = 1L;
       when(bookService.한건가져오기(id)).thenReturn(new Book(1L, "자바", "kim"));

       // when
       ResultActions resultActions = mockMvc.perform(get("/book/{id}", id)
               .accept(MediaType.APPLICATION_JSON_UTF8));

       // then
       resultActions
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.title").value("자바"))
               .andDo(print());
   }

   @Test
   public void update_테스트() throws Exception {
       // given
       Long id = 1L;
       Book book = new Book(null, "SQL", "kim");
       String content = new ObjectMapper().writeValueAsString(book);
       when(bookService.수정하기(id, book)).thenReturn(new Book(1L, "SQL", "kim"));

       // when
       ResultActions resultActions = mockMvc.perform(put("/book/{id}", id)
               .contentType(MediaType.APPLICATION_JSON_UTF8)
               .content(content)
               .accept(MediaType.APPLICATION_JSON_UTF8));

       // then
       resultActions
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.title").value("SQL"))
               .andDo(print());
   }

   @Test
   public void delete_테스트() throws Exception {
       // given
       Long id = 1L;
       when(bookService.삭제하기(id)).thenReturn("ok");

       // when
       ResultActions resultActions = mockMvc.perform(delete("/book/{id}", id)
               .accept(MediaType.TEXT_PLAIN));

       // then
       resultActions
               .andExpect(status().isOk())
               .andDo(print());

       String result = resultActions.andReturn()
               .getResponse().getContentAsString();

       Assertions.assertEquals("ok", result);
   }
}