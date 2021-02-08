package com.example.book.web;

import com.example.book.domain.Book;
import com.example.book.domain.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 통합테스트 ( 모든 Bean 들을 IoC에 올리고 테스트 하는 것 )
 * WebEnvironment.MOCK = 실제 톰캣이 아닌 다른 톰캣으로 테스트
 * WebEnvironment.RANDOM_PORT = 실제 톰캣으로 테스트
 * @AutoConfigureMockMvc = MockMvc 를 IoC에 등록
 * @Transactional = 각 테스트 함수가 종료되면 트랜잭션을 rollback
 *
 */

@Slf4j
@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class BookControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    public void init() {
        entityManager.createNativeQuery("alter table book alter column id restart with 1").executeUpdate();
//        entityManager.createNativeQuery("alter table book auto_increment=1").executeUpdate();
    }

    // BDDMockito 패턴 given, when, then
    @Test
    public void save_테스트() throws Exception {
        log.info("save 테스트 시작 ==================================");
        // given ( 테스트를 하기 위한 준비 )
        Book book = new Book(null, "스프링", "kim");
        String content = new ObjectMapper().writeValueAsString(book);

        // when ( 테스트 실행 )
        ResultActions resultActions = mockMvc.perform(post("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON_UTF8));

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
        books.add(new Book(null, "스프링", "kim"));
        books.add(new Book(null, "리액트", "kim"));
        books.add(new Book(null, "JUnit", "kim"));

        bookRepository.saveAll(books);

        // when
        ResultActions resultActions = mockMvc.perform(get("/book")
                .accept(MediaType.APPLICATION_JSON_UTF8));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                .andExpect(jsonPath("$.[0].title").value("스프링"))
                .andDo(print());

    }

    @Test
    public void findById_테스트() throws Exception {
        // given
        Long id = 2L;
        List<Book> books = new ArrayList<>();
        books.add(new Book(null, "스프링", "kim"));
        books.add(new Book(null, "리액트", "kim"));
        books.add(new Book(null, "JUnit", "kim"));

        bookRepository.saveAll(books);

        // when
        ResultActions resultActions = mockMvc.perform(get("/book/{id}", id)
                .accept(MediaType.APPLICATION_JSON_UTF8));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("리액트"))
                .andDo(print());
    }

    @Test
    public void update_테스트() throws Exception {
        // given
        List<Book> books = new ArrayList<>();
        books.add(new Book(null, "스프링", "kim"));
        books.add(new Book(null, "리액트", "kim"));
        books.add(new Book(null, "JUnit", "kim"));

        bookRepository.saveAll(books);

        Long id = 2L;
        Book book = new Book(null, "SQL", "kim");
        String content = new ObjectMapper().writeValueAsString(book);

        // when
        ResultActions resultActions = mockMvc.perform(put("/book/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content)
                .accept(MediaType.APPLICATION_JSON_UTF8));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.title").value("SQL"))
                .andDo(print());
    }

    @Test
    public void delete_테스트() throws Exception {
        // given
        List<Book> books = new ArrayList<>();
        books.add(new Book(null, "스프링", "kim"));
        books.add(new Book(null, "리액트", "kim"));
        books.add(new Book(null, "JUnit", "kim"));

        bookRepository.saveAll(books);

        Long id = 1L;

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
